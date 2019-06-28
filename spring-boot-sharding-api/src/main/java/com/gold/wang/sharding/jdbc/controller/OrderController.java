package com.gold.wang.sharding.jdbc.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/order")
@Slf4j
@Api("OrderController--api")
public class OrderController {

    @ApiOperation(value = "创建库存", notes = "创建库存")
    @ApiImplicitParam(name =  "addOrder", value = "随机参数", paramType = "query", required = true, dataType = "Integer")
    @PostMapping(value = "/addOrder")
    public String addOrder(Long userId) {
        String result = "SUCCESS" ;
        try {
            System.out.printf("aaa");
        } catch (Exception e) {
            return "FAILURE";
        }
        return result;
    }


}