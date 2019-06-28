package com.gold.wang.sharding.jdbc.util;


import com.gold.wang.sharding.jdbc.common.util.IdUtils;

public class IdGenerator {


    /**
     * 主键生成器 自增 + storeId 后十位的前五位 + 随机数5位
     *
     * @param paramId
     * @return
     */
    public static Long generate(Long paramId) {
        //前缀随机ID
        long generateId = (int) (Math.random() * 10000);

        String lastfive = IdUtils.getFirstNumber(IdUtils.getLastNumber(paramId + "", 10), 5);
        int intFlag = (int) (Math.random() * 1000000);
        String flag = String.valueOf(intFlag);
        if (flag.length() > 5) {
            flag = flag.substring(0, 5);
        } else {
            flag = String.format("%05d", Long.valueOf(flag));
        }
        Long id = Long.parseLong(String.valueOf(generateId).concat(lastfive).concat(flag));
        return id;
    }
}
