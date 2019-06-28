package com.gold.wang.sharding.jdbc.conf.algorithm;

import com.gold.wang.sharding.jdbc.common.util.IdUtils;
import io.shardingsphere.api.algorithm.sharding.ListShardingValue;
import io.shardingsphere.api.algorithm.sharding.ShardingValue;
import io.shardingsphere.api.algorithm.sharding.complex.ComplexKeysShardingAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.LinkedHashSet;

public class MultipleKeysDatabaseAlgorithm implements ComplexKeysShardingAlgorithm {

    private final Logger log = LoggerFactory.getLogger(MultipleKeysDatabaseAlgorithm.class);

    /**
     * 根据分片值计算分片结果名称集合.
     *
     * @param availableTargetNames 所有的可用目标名称集合, 一般是数据源或表名称
     * @param shardingValues       分片值集合
     * @return 分片后指向的目标名称集合, 一般是数据源或表名称
     */
    @Override
    public Collection<String> doSharding(Collection<String> availableTargetNames, Collection<ShardingValue> shardingValues) {
        Collection<String> result = new LinkedHashSet<>(availableTargetNames.size());
        for (ShardingValue shardingValue : shardingValues) {
            if (shardingValue instanceof ListShardingValue) {
                ListShardingValue value = (ListShardingValue) shardingValue;
                Collection<Long> values = value.getValues();
                for (Long val : values) {
                    for (String dbName : availableTargetNames) {
                        IdUtils.ShardingDO last5SharingValue = IdUtils.getStoreIdSharingValue(String.valueOf(val));
                        if (dbName.endsWith(last5SharingValue.getDbShardValue() % 2 + "")) {
                            result.add(dbName);
                            log.debug("<=================sharding3.0values DB rule route:{}", result);
                            return result;
                        }
                    }
                }

            }
        }
        return result;
    }

}
