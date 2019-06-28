/*
Navicat MySQL Data Transfer

Source Server         : 10.8.131.12--test
Source Server Version : 50721
Source Host           : 10.8.131.12:3306
Source Database       : cowell_stockcenter_0

Target Server Type    : MYSQL
Target Server Version : 50721
File Encoding         : 65001

Date: 2019-06-17 15:44:04
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `stock_goods_trade_000`
-- ----------------------------
DROP TABLE IF EXISTS `stock_goods_trade_000`;
CREATE TABLE `stock_goods_trade_000` (
  `id` bigint(20) NOT NULL COMMENT '主键生成器 自增 + orderId后十位的前五位 + 随机数5位',
  `order_id` bigint(20) NOT NULL COMMENT '订单id',
  `business_id` bigint(20) NOT NULL COMMENT '连锁id',
  `store_id` bigint(20) NOT NULL COMMENT '门店id',
  `sku_merchant_code` varchar(64) NOT NULL COMMENT 'mdm高济唯一码',
  `batch_code` varchar(32) DEFAULT '-1' COMMENT '批次号',
  `batch_no` varchar(32) DEFAULT '-1' COMMENT '批号',
  `charge_back_id` varchar(32) DEFAULT '-1' COMMENT '退货单id',
  `quantity` decimal(14,3) DEFAULT '0.000' COMMENT '购买数量(大)',
  `piece_quantity` decimal(14,3) DEFAULT '0.000' COMMENT '拆零购买数量(小)',
  `version` int(11) DEFAULT '1' COMMENT '版本号',
  `extend` varchar(2048) DEFAULT NULL COMMENT '扩展字段1',
  `type` tinyint(4) DEFAULT NULL COMMENT '操作状态 0：减 货品库存 1:加货品库存 库存类型 ',
  `status` tinyint(4) DEFAULT NULL COMMENT '1: 下单 2:已支付 3：退款退货 4：取消订单',
  `created_by` varchar(100) DEFAULT NULL COMMENT '创建人',
  `gmt_create` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `last_modified_by` varchar(100) DEFAULT NULL COMMENT '修改人',
  `gmt_update` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uniq_store_order_merchant_batch_charge_back_id` (`order_id`,`store_id`,`sku_merchant_code`,`batch_code`,`batch_no`,`charge_back_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='货品批次库存交易表';

-- ----------------------------
-- Records of stock_goods_trade_000
-- ----------------------------

-- ----------------------------
-- Table structure for `stock_goods_trade_001`
-- ----------------------------
DROP TABLE IF EXISTS `stock_goods_trade_001`;
CREATE TABLE `stock_goods_trade_001` (
  `id` bigint(20) NOT NULL COMMENT '主键生成器 自增 + orderId后十位的前五位 + 随机数5位',
  `order_id` bigint(20) NOT NULL COMMENT '订单id',
  `business_id` bigint(20) NOT NULL COMMENT '连锁id',
  `store_id` bigint(20) NOT NULL COMMENT '门店id',
  `sku_merchant_code` varchar(64) NOT NULL COMMENT 'mdm高济唯一码',
  `batch_code` varchar(32) DEFAULT '-1' COMMENT '批次号',
  `batch_no` varchar(32) DEFAULT '-1' COMMENT '批号',
  `charge_back_id` varchar(32) DEFAULT '-1' COMMENT '退货单id',
  `quantity` decimal(14,3) DEFAULT '0.000' COMMENT '购买数量(大)',
  `piece_quantity` decimal(14,3) DEFAULT '0.000' COMMENT '拆零购买数量(小)',
  `version` int(11) DEFAULT '1' COMMENT '版本号',
  `extend` varchar(2048) DEFAULT NULL COMMENT '扩展字段1',
  `type` tinyint(4) DEFAULT NULL COMMENT '操作状态 0：减 货品库存 1:加货品库存 库存类型 ',
  `status` tinyint(4) DEFAULT NULL COMMENT '1: 下单 2:已支付 3：退款退货 4：取消订单',
  `created_by` varchar(100) DEFAULT NULL COMMENT '创建人',
  `gmt_create` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `last_modified_by` varchar(100) DEFAULT NULL COMMENT '修改人',
  `gmt_update` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uniq_store_order_merchant_batch_charge_back_id` (`order_id`,`store_id`,`sku_merchant_code`,`batch_code`,`batch_no`,`charge_back_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='货品批次库存交易表';

-- ----------------------------
-- Records of stock_goods_trade_001
-- ----------------------------
