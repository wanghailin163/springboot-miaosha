package com.myself.miaosha.dataobject;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * @Description: TODO
 * @Author: wanghailin
 * @Date: 2019/9/24
 */
@Data
@Entity
@Table(name="order_info")
public class OrderDO {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    @Column(name = "user_id",nullable = false)
    private Integer userId;

    @Column(name = "item_id",nullable = false)
    private Integer itemId;

    @Column(name = "item_price",nullable = false,precision = 10, scale = 2)
    private BigDecimal itemPrice;

    @Column(name = "amount",nullable = false)
    private Integer amount;

    @Column(name = "order_price",nullable = false,precision = 10, scale = 2)
    private BigDecimal orderPrice;

    @Column(name = "promo_id")
    private Integer promoId;

}
