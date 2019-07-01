/*
Navicat MySQL Data Transfer

Source Server         : 127.0.0.1
Source Server Version : 50722
Source Host           : 127.0.0.1:3306
Source Database       : cowell_stockcenter_0

Target Server Type    : MYSQL
Target Server Version : 50722
File Encoding         : 65001

Date: 2019-06-30 19:12:34
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `item_base`
-- ----------------------------
DROP TABLE IF EXISTS `item_base`;
CREATE TABLE `item_base` (
  `item_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `business_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '连锁id',
  `store_id` bigint(20) NOT NULL COMMENT '门店id',
  `item_name` varchar(1000) NOT NULL COMMENT '商品标题',
  `goods_no` varchar(64) NOT NULL DEFAULT '' COMMENT '商家编号',
  `stock` int(11) NOT NULL DEFAULT '1' COMMENT '商品库存',
  `sold` int(11) NOT NULL DEFAULT '0' COMMENT '商品销量',
  `is_soldout` tinyint(4) NOT NULL DEFAULT '0' COMMENT '商品售罄状态：0,未售罄 1售罄',
  PRIMARY KEY (`item_id`),
  KEY `IDX_BUSINESS_STORE_ITEM_TYPE_MERCHANT_CODE` (`business_id`,`store_id`,`goods_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='商品基础表';

-- ----------------------------
-- Records of item_base
-- ----------------------------
