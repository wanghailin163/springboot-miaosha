package com.myself.miaosha.dataobject;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * @Description: TODO 商品对象模型
 * @Author: wanghailin
 * @Date: 2019/9/23
 */
@Data
@Entity
@Table(name="item")
public class ItemDO {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    @Column(name = "title",nullable = false,length = 50)
    private String title;

    //精度为10，小数点位数为2位
    @Column(name = "price",nullable = false,precision = 10, scale = 2)
    private BigDecimal price;

    @Column(name = "description",length = 255)
    private String description;

    @Column(name = "sales")
    private Integer sales;

    @Column(name = "img_url",length = 255)
    private String imgUrl;



}
