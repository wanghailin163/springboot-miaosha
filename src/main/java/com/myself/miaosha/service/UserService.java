package com.myself.miaosha.service;

import com.myself.miaosha.error.BussinessException;
import com.myself.miaosha.service.model.UserModel;

/**
 * @Description: TODO
 * @Author: wanghailin
 * @Date: 2019/9/9
 */
public interface UserService {

    //通过对象id获取对象
    UserModel getUserById(Integer id);
    //用户注册
    void register(UserModel userModel) throws BussinessException;

    /**
     * 用户登陆服务，检验用户登录是否合法
     * @param telphone 用户注册的手机号
     * @param encrptPassword 用户加密后的密码
     * @return
     * @throws BussinessException
     */
    UserModel validateLogin(String telphone,String encrptPassword) throws BussinessException;




}
