package com.myself.miaosha.dataobject;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @Description: 秒杀数据对象模型
 * @Author: wanghailin
 * @Date: 2019/9/24
 */
@Data
@Entity
@Table(name="promo")
public class PromoDO {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    @Column(name = "promo_name",nullable = false,length = 20)
    private String promoName;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Temporal(TemporalType.TIMESTAMP)
    private Date startDate;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Temporal(TemporalType.TIMESTAMP)
    private Date endDate;

    @Column(name = "item_id",nullable = false,length = 20)
    private Integer itemId;

    @Column(name = "promo_price",nullable = false,precision = 10, scale = 2)
    private BigDecimal promoItemPrice;

}
