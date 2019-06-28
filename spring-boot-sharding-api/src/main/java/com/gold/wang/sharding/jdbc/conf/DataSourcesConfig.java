package com.gold.wang.sharding.jdbc.conf;

import com.gold.wang.sharding.jdbc.conf.algorithm.MultipleKeysDatabaseAlgorithm;
import com.gold.wang.sharding.jdbc.conf.algorithm.MultipleKeysTableAlgorithm;
import io.shardingsphere.api.config.ShardingRuleConfiguration;
import io.shardingsphere.api.config.TableRuleConfiguration;
import io.shardingsphere.api.config.strategy.ComplexShardingStrategyConfiguration;
import io.shardingsphere.api.config.strategy.NoneShardingStrategyConfiguration;
import io.shardingsphere.shardingjdbc.api.ShardingDataSourceFactory;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.actuate.health.CompositeHealthIndicator;
import org.springframework.boot.actuate.health.HealthAggregator;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.actuate.jdbc.DataSourceHealthIndicator;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Configuration
@EnableTransactionManagement
@MapperScan(basePackages = {"com.gold.wang.sharding.jdbc.mapper"})
public class DataSourcesConfig {

    private final Logger log = LoggerFactory.getLogger(DataSourcesConfig.class);


    @Autowired
    private HealthAggregator healthAggregator;


    @Bean(name = "ds_0")
    @Qualifier("ds_0")
    @ConfigurationProperties(prefix = "spring.ds0")
    public DataSource zeroDatasource() {
        log.info("DataSourcesConfig|threeDatasource|获取 ds_0");
        try {
            return DataSourceBuilder.create().build();
        } catch (Exception e) {
            log.error("DataSourcesConfig|threeDatasource|获取 ds_0 失败", e);
        }
        return null;
    }

    /**
     * 获取配置文件数据源secondDatasource
     */
    @Bean(name = "ds_1")
    @Qualifier("ds_1")
    @ConfigurationProperties(prefix = "spring.ds1")
    public DataSource firstDatasource() {
        log.info("DataSourcesConfig|secondDatasource|获取 ds_1");
        try {
            return DataSourceBuilder.create().build();
        } catch (Exception e) {
            log.error("DataSourcesConfig|secondDatasource|获取 ds_1 失败", e);
        }
        return null;
    }


    public DataSource shardingDataSource() throws SQLException {
        ShardingRuleConfiguration shardingRuleConfig = new ShardingRuleConfiguration();
        Map<String, DataSource> dataSourceMap = new HashMap<>(2);
        dataSourceMap.put("ds_0", zeroDatasource());
        dataSourceMap.put("ds_1", firstDatasource());

        // 默认不分库分表
        shardingRuleConfig.setDefaultDataSourceName("ds_0");
        shardingRuleConfig.setDefaultDatabaseShardingStrategyConfig(new NoneShardingStrategyConfiguration());
        shardingRuleConfig.setDefaultTableShardingStrategyConfig(new NoneShardingStrategyConfiguration());

        Collection<TableRuleConfiguration> tableRuleConfigs = shardingRuleConfig.getTableRuleConfigs();
        tableRuleConfigs.add(getStockGoodsTradeTableRulesConfiguration());
        tableRuleConfigs.add(getStockGoodsBatchCodeTableRulesConfiguration());

        return ShardingDataSourceFactory.createDataSource(dataSourceMap,
                shardingRuleConfig, new ConcurrentHashMap<>(), null);
    }


    /**
     * stock_goods_trade表
     */
    TableRuleConfiguration getStockGoodsTradeTableRulesConfiguration() {
        TableRuleConfiguration result = new TableRuleConfiguration();
        result.setLogicTable("stock_goods_trade");
        result.setActualDataNodes(
                "ds_0.stock_goods_trade_00${0..1},ds_1.stock_goods_trade_00${0..1}");
        result.setDatabaseShardingStrategyConfig(new ComplexShardingStrategyConfiguration("store_id,id", new MultipleKeysDatabaseAlgorithm()));
        result.setTableShardingStrategyConfig(new ComplexShardingStrategyConfiguration("store_id,id", new MultipleKeysTableAlgorithm()));
        return result;
    }


    /**
     * stock_goods_batch_code表
     */
    TableRuleConfiguration getStockGoodsBatchCodeTableRulesConfiguration() {
        TableRuleConfiguration result = new TableRuleConfiguration();
        result.setLogicTable("stock_goods_batch_code");
        result.setActualDataNodes(
                "ds_0.stock_goods_batch_code_00${0..1},ds_1.stock_goods_batch_code_00${0..1}");
        result.setDatabaseShardingStrategyConfig(new ComplexShardingStrategyConfiguration("store_id,id", new MultipleKeysDatabaseAlgorithm()));
        result.setTableShardingStrategyConfig(new ComplexShardingStrategyConfiguration("store_id,id", new MultipleKeysTableAlgorithm()));
        return result;
    }


    @Bean
    public SqlSessionFactory sqlSessionFactoryBean() throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(getDataSource());
        sqlSessionFactoryBean.setConfigLocation(new ClassPathResource("mybatis/mybatis-config.xml"));
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        sqlSessionFactoryBean.setMapperLocations(resolver.getResources("classpath*:mybatis/mapper/**/*.xml"));
        return sqlSessionFactoryBean.getObject();
    }

    @Bean
    @Primary
    public PlatformTransactionManager platformTransactionManager() throws Exception {
        return new DataSourceTransactionManager(getDataSource());
    }

    @Bean
    public DataSource getDataSource() throws SQLException {
        return shardingDataSource();
    }

    @Bean(name = "dbHealthIndicator")
    public HealthIndicator dbHealthIndicator(@Qualifier("ds_0") DataSource zeroDatasource,
                                             @Qualifier("ds_1") DataSource firstDatasource) {
        Map<String, DataSource> dataSourceMap = new HashMap<>(8);
        dataSourceMap.put("ds_0", zeroDatasource);
        dataSourceMap.put("ds_1", firstDatasource);
        if (dataSourceMap.size() == 1) {
            return new DataSourceHealthIndicator(dataSourceMap.values().iterator().next());
        } else {
            CompositeHealthIndicator composite = new CompositeHealthIndicator(this.healthAggregator);
            Iterator var3 = dataSourceMap.entrySet().iterator();

            while (var3.hasNext()) {
                Map.Entry<String, DataSource> entry = (Map.Entry) var3.next();
                composite.addHealthIndicator(entry.getKey(), new DataSourceHealthIndicator(entry.getValue()));
            }
            return composite;
        }
    }
}
