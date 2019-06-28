package com.gold.wang.sharding.jdbc.common.util;

public class IdUtils {


    public static String getLastNumber(String id, int length) {
        int size = Math.min(id.length(), length);
        id = id.substring(id.length() - size);
        return String.format("%0" + length + "d", Long.valueOf(id));
    }


    public static String getFirstNumber(String id, int length) {
        if (length > id.length()) {
            throw new RuntimeException("截取长度超过总长度");
        } else {
            id = id.substring(0, length);
            return String.format("%0" + length + "d", Long.valueOf(id));
        }
    }


    public static IdUtils.ShardingDO getStoreIdSharingValue(String storeId) {
        String index = getFirstNumber(getLastNumber(storeId, 10), 5);
        return new IdUtils.ShardingDO(Long.parseLong(getFirstNumber(index, 2)), Long.parseLong(getLastNumber(index, 3)));
    }


    public static class ShardingDO {
        private Long dbShardValue;
        private Long tableShardValue;


        public ShardingDO(Long dbShardValue, Long tableShardValue) {
            this.dbShardValue = dbShardValue;
            this.tableShardValue = tableShardValue;
        }

        public Long getDbShardValue() {
            return this.dbShardValue;
        }

        public void setDbShardValue(Long dbShardValue) {
            this.dbShardValue = dbShardValue;
        }

        public Long getTableShardValue() {
            return this.tableShardValue;
        }

        public void setTableShardValue(Long tableShardValue) {
            this.tableShardValue = tableShardValue;
        }

    }
}
