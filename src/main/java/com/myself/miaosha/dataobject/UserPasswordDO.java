package com.myself.miaosha.dataobject;

import lombok.Data;

import javax.persistence.*;

/**
 * @Description: TODO
 * @Author: wanghailin
 * @Date: 2019/9/9
 */
@Data
@Entity
@Table(name="user_password")
public class UserPasswordDO {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    @Column(name = "user_id",unique =true,nullable = false)
    private Integer userId;

    @Column(name = "encrpt_password",nullable = false,length = 255)
    private String encrptPassword;

}
