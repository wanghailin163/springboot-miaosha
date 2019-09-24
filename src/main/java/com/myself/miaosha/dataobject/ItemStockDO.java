package com.myself.miaosha.dataobject;

import lombok.Data;

import javax.persistence.*;

/**
 * @Description: 商品库存对象模型
 * @Author: wanghailin
 * @Date: 2019/9/23
 */
@Data
@Entity
@Table(name="item_stock")
public class ItemStockDO {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    @Column(name = "stock")
    private Integer stock;

    @Column(name = "item_id")
    private Integer itemId;

}
