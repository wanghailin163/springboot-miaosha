package com.myself.miaosha.dataobject;

import lombok.Data;
import javax.persistence.*;

/**
 * @Description: TODO 数据对象模型 与mysql中表一一对应
 * @Author: wanghailin
 * @Date: 2019/9/9
 */
@Data
@Entity
@Table(name="user_info")
public class UserDO {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    @Column(name = "name",nullable = false,length = 20)
    private String name;

    @Column(name = "gender",nullable = false)
    private Byte gender;

    @Column(name = "age",nullable = false)
    private Integer age;

    @Column(name = "telphone",unique =true,nullable = false,length = 20)//telphone是唯一
    private String telphone;

    @Column(name = "register_mode",nullable = false,length = 10)
    private String registerMode;

    @Column(name = "third_party_id",length = 30)
    private String thirdPartyId;

}
