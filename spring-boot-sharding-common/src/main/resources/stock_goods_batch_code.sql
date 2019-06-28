/*
Navicat MySQL Data Transfer

Source Server         : 10.8.131.12--test
Source Server Version : 50721
Source Host           : 10.8.131.12:3306
Source Database       : cowell_stockcenter_0

Target Server Type    : MYSQL
Target Server Version : 50721
File Encoding         : 65001

Date: 2019-06-17 15:44:57
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `stock_goods_batch_code_000`
-- ----------------------------
DROP TABLE IF EXISTS `stock_goods_batch_code_000`;
CREATE TABLE `stock_goods_batch_code_000` (
  `id` bigint(20) NOT NULL COMMENT '主键生成器 自增 + storeId后十位的前五位 + 随机数5位',
  `business_id` bigint(20) NOT NULL COMMENT '连锁id',
  `store_id` bigint(20) NOT NULL COMMENT '门店id',
  `sku_merchant_code` varchar(64) NOT NULL COMMENT 'mdm高济唯一码',
  `batch_code` varchar(32) DEFAULT '-1' COMMENT '批次号',
  `batch_no` varchar(32) DEFAULT '-1' COMMENT '批号',
  `piece_unit` decimal(14,3) DEFAULT '0.000' COMMENT '拆零因子',
  `stock` decimal(14,3) NOT NULL DEFAULT '0.000' COMMENT '总库存',
  `buy_stock` decimal(14,3) NOT NULL DEFAULT '0.000' COMMENT '可卖库存(大)',
  `piece_buy_stock` decimal(14,3) NOT NULL DEFAULT '0.000' COMMENT '拆零可卖库存(小)',
  `waitting_area_stock` decimal(14,3) NOT NULL DEFAULT '0.000' COMMENT '待验收区库存(大)',
  `piece_waitting_area_stock` decimal(14,3) NOT NULL DEFAULT '0.000' COMMENT '待验收区拆零库存(小)',
  `unqualified_area_stock` decimal(14,3) NOT NULL DEFAULT '0.000' COMMENT '非合格品库存(大)',
  `piece_unqualified_area_stock` decimal(14,3) NOT NULL DEFAULT '0.000' COMMENT '非合格品拆零库存(小)',
  `wait_stock` decimal(14,3) NOT NULL DEFAULT '0.000' COMMENT '待出库库存',
  `transit_stock` decimal(14,3) NOT NULL DEFAULT '0.000' COMMENT '在途库存',
  `unit` varchar(20) DEFAULT NULL COMMENT '单位',
  `expire_date` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '有效期',
  `produce_date` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '生产日期',
  `produce_company` varchar(200) DEFAULT NULL COMMENT '生产企业',
  `supply` varchar(64) DEFAULT NULL COMMENT '供应商',
  `locked` smallint(2) DEFAULT '0' COMMENT '是否锁定 0 未锁定  1 已锁定',
  `sync_date` datetime DEFAULT NULL COMMENT '同步时间',
  `apply_type` tinyint(4) DEFAULT NULL COMMENT '0:sap同步 1:店采',
  `version` int(11) DEFAULT '1' COMMENT '版本号',
  `extend` varchar(2048) DEFAULT NULL COMMENT '扩展字段1',
  `created_by` varchar(100) DEFAULT NULL COMMENT '创建人',
  `gmt_create` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `last_modified_by` varchar(100) DEFAULT NULL COMMENT '修改人',
  `gmt_update` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uniq_store_merchant_batch_code` (`store_id`,`sku_merchant_code`,`batch_no`,`batch_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='货品批次库存交易表';


-- ----------------------------
-- Table structure for `stock_goods_batch_code_001`
-- ----------------------------
DROP TABLE IF EXISTS `stock_goods_batch_code_001`;
CREATE TABLE `stock_goods_batch_code_001` (
  `id` bigint(20) NOT NULL COMMENT '主键生成器 自增 + storeId后十位的前五位 + 随机数5位',
  `business_id` bigint(20) NOT NULL COMMENT '连锁id',
  `store_id` bigint(20) NOT NULL COMMENT '门店id',
  `sku_merchant_code` varchar(64) NOT NULL COMMENT 'mdm高济唯一码',
  `batch_code` varchar(32) DEFAULT '-1' COMMENT '批次号',
  `batch_no` varchar(32) DEFAULT '-1' COMMENT '批号',
  `piece_unit` decimal(14,3) DEFAULT '0.000' COMMENT '拆零因子',
  `stock` decimal(14,3) NOT NULL DEFAULT '0.000' COMMENT '总库存',
  `buy_stock` decimal(14,3) NOT NULL DEFAULT '0.000' COMMENT '可卖库存(大)',
  `piece_buy_stock` decimal(14,3) NOT NULL DEFAULT '0.000' COMMENT '拆零可卖库存(小)',
  `waitting_area_stock` decimal(14,3) NOT NULL DEFAULT '0.000' COMMENT '待验收区库存(大)',
  `piece_waitting_area_stock` decimal(14,3) NOT NULL DEFAULT '0.000' COMMENT '待验收区拆零库存(小)',
  `unqualified_area_stock` decimal(14,3) NOT NULL DEFAULT '0.000' COMMENT '非合格品库存(大)',
  `piece_unqualified_area_stock` decimal(14,3) NOT NULL DEFAULT '0.000' COMMENT '非合格品拆零库存(小)',
  `wait_stock` decimal(14,3) NOT NULL DEFAULT '0.000' COMMENT '待出库库存',
  `transit_stock` decimal(14,3) NOT NULL DEFAULT '0.000' COMMENT '在途库存',
  `unit` varchar(20) DEFAULT NULL COMMENT '单位',
  `expire_date` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '有效期',
  `produce_date` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '生产日期',
  `produce_company` varchar(200) DEFAULT NULL COMMENT '生产企业',
  `supply` varchar(64) DEFAULT NULL COMMENT '供应商',
  `locked` smallint(2) DEFAULT '0' COMMENT '是否锁定 0 未锁定  1 已锁定',
  `sync_date` datetime DEFAULT NULL COMMENT '同步时间',
  `apply_type` tinyint(4) DEFAULT NULL COMMENT '0:sap同步 1:店采',
  `version` int(11) DEFAULT '1' COMMENT '版本号',
  `extend` varchar(2048) DEFAULT NULL COMMENT '扩展字段1',
  `created_by` varchar(100) DEFAULT NULL COMMENT '创建人',
  `gmt_create` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `last_modified_by` varchar(100) DEFAULT NULL COMMENT '修改人',
  `gmt_update` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uniq_store_merchant_batch_code` (`store_id`,`sku_merchant_code`,`batch_no`,`batch_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='货品批次库存交易表';
