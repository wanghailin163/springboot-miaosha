package com.myself.miaosha.service.model;

import lombok.Data;
import org.joda.time.DateTime;

import java.math.BigDecimal;

/**
 * @Description: TODO
 * @Author: wanghailin
 * @Date: 2019/9/24
 */
@Data
public class PromoModel {

    private Integer id;

    //秒杀活动状态，1表示还未开始，2表示进行中，3表示已结束
    private Integer status;

    //秒杀活动名称
    private String promoName;

    //秒杀开始时间
    private DateTime startDate;

    //秒杀结束时间
    private DateTime endDate;

    //参与秒杀活动的商品id
    private Integer itemid;

    //秒杀商品的价格
    private BigDecimal promoItemPrice;

}
