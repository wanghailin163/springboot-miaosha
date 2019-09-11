package com.myself.miaosha.dataobject;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;

/**
 * @Description: TODO
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

    @Column(name = "name")
    private String name;

    @Column(name = "gender")
    private Byte gender;

    @Column(name = "age")
    private Integer age;

    @Column(name = "telphone")
    private String telphone;

    @Column(name = "register_mode")
    private String registerMode;

    @Column(name = "third_party_id")
    private String thirdPartyId;

}
