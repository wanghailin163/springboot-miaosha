package com.myself.miaosha.dataobject;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @Description: TODO
 * @Author: wanghailin
 * @Date: 2019/9/24
 */
@Data
@Entity
@Table(name="sequence")
public class SequenceDO {

    @Id
    @Column(name = "name",nullable = false,unique=true,length = 30)
    private String name;

    @Column(name = "currentvalue",nullable = false)
    private Integer currentValue;

    @Column(name = "step",nullable = false)
    private Integer step;

}
