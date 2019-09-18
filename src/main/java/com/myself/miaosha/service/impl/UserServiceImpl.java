package com.myself.miaosha.service.impl;

import com.myself.miaosha.dataobject.UserDO;
import com.myself.miaosha.dataobject.UserPasswordDO;
import com.myself.miaosha.error.BussinessException;
import com.myself.miaosha.error.EmBusinessError;
import com.myself.miaosha.repository.UserDORepository;
import com.myself.miaosha.repository.UserPasswordDORepository;
import com.myself.miaosha.service.UserService;
import com.myself.miaosha.service.model.UserModel;
import com.myself.miaosha.validator.ValidationResult;
import com.myself.miaosha.validator.ValidatorImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Description: TODO
 * @Author: wanghailin
 * @Date: 2019/9/10
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDORepository userDORepository;
    @Autowired
    private UserPasswordDORepository userPasswordDORepository;
    @Autowired
    private ValidatorImpl validator;


    @Override
    public UserModel getUserById(Integer id) {
        UserDO userDO = userDORepository.findUserDOById(id);
        if(userDO == null){
            return null;
        }
        //通过用户id获取对应用户加密的密码信息
        UserPasswordDO userPasswordDO = userPasswordDORepository.findByUserId(id);
        return convertFromDataObject(userDO, userPasswordDO);//返回给controller层
    }

    @Override
    @Transactional
    public void register(UserModel userModel) throws BussinessException {
        //校验
        if(userModel==null){
            throw new BussinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);
        }
        //优化校验
        ValidationResult result = validator.validate(userModel);
        if(result.isHasErrors()){
            throw new BussinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR, result.getErrMsg());
        }
        UserDO userDO = convertFromModel(userModel);
        //手机号重复注册发生异常
        try{
            userDORepository.save(userDO);
        }catch(DuplicateKeyException ex){
            throw new BussinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR, "手机号已注册");
        }
        userModel.setId(userDO.getId());
        UserPasswordDO userPasswordDO = convertPasswordFromModel(userModel);
        userPasswordDORepository.save(userPasswordDO);
    }

    @Override
    public UserModel validateLogin(String telphone, String encrptPassword) throws BussinessException {
        //通过用户手机号，获取用户信息
        UserDO userDO = userDORepository.findUserDOByTelphone(telphone);
        if(userDO == null){
            throw new BussinessException(EmBusinessError.USER_LOGIN_FAIL);
        }
        //再通过用户信息id,到用户密码表中拿到用户密码信息
        UserPasswordDO userPasswordDO = userPasswordDORepository.findByUserId(userDO.getId());
        UserModel userModel = convertFromDataObject(userDO,userPasswordDO);

        //比对用户加密密码是否和传递进来的密码匹配
        if(!StringUtils.equals(encrptPassword, userModel.getEncrptPassword())){
            throw new BussinessException(EmBusinessError.USER_LOGIN_FAIL);
        }else{
            return userModel;//如果用户登录成功，则将model返回controller层
        }
   }

    //实现model -> dataobject方法
    private UserDO convertFromModel(UserModel userModel){
        if(userModel ==  null){
            return null;
        }
        UserDO userDO = new UserDO();
        BeanUtils.copyProperties(userModel,userDO);
        return userDO;
    }

    private UserPasswordDO convertPasswordFromModel(UserModel userModel){
        if(userModel==null){
            return null;
        }
        UserPasswordDO  userPasswordDO = new UserPasswordDO();
        userPasswordDO.setEncrptPassword(userModel.getEncrptPassword());
        userPasswordDO.setUserId(userModel.getId());
        return userPasswordDO;
    }

    //将UserDO+password转换为UserModel
    private UserModel convertFromDataObject(UserDO userDo,UserPasswordDO userPasswordDO){
        if(userDo==null){
            return null;
        }
        UserModel userModel = new UserModel();
        BeanUtils.copyProperties(userDo,userModel);
        if(userPasswordDO!=null){
            userModel.setEncrptPassword(userPasswordDO.getEncrptPassword());
        }
        return userModel;
    }


}
