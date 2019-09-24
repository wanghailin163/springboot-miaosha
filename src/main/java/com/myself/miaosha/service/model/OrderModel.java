package com.myself.miaosha.service.model;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @Description: TODO
 * @Author: wanghailin
 * @Date: 2019/9/24
 */
@Data
public class OrderModel {

    private String id;
    private Integer userId;
    private Integer itemId;
    private BigDecimal itemPrice;
    private Integer amount;
    private BigDecimal orderPrice;

    //若非空，表示以秒杀方式下单
    private Integer promoId;

}
