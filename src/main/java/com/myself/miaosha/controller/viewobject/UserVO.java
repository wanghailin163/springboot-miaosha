package com.myself.miaosha.controller.viewobject;

import lombok.Data;

/**
 * @Description: TODO 供前端视图展示用的数据模型
 * @Author: wanghailin
 * @Date: 2019/9/10
 */
@Data
public class UserVO {

    private Integer id;
    private String name;
    private Byte gender;
    private Integer age;
    private String telphone;

}
