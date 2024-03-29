package com.myself.miaosha.controller;

import com.myself.miaosha.controller.viewobject.UserVO;
import com.myself.miaosha.error.BussinessException;
import com.myself.miaosha.error.EmBusinessError;
import com.myself.miaosha.response.CommonReturnType;
import com.myself.miaosha.service.UserService;
import com.myself.miaosha.service.model.UserModel;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.*;
import sun.misc.BASE64Encoder;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

/**
 * @Description: TODO
 * @Author: wanghailin
 * @Date: 2019/9/10
 */
@RestController
@RequestMapping("/user")
@CrossOrigin(allowCredentials = "true", allowedHeaders = "*")//跨域请求 保证session发挥作用
public class UserController extends BaseController {

    @Autowired
    private UserService userService;

    //接入HttpSession,httpServletRequest通过bean的方式注入
    @Autowired
    private HttpServletRequest httpServletRequest;//HttpServletRequest对象代表客户端的请求

    //用户登录接口
    @PostMapping(value = "login",consumes = {CONTENT_TYPE_FORMED})
    public CommonReturnType login(@RequestParam(name = "telphone",required=false)String telphone,
                                  @RequestParam(name = "password",required=false)String password) throws BussinessException, UnsupportedEncodingException, NoSuchAlgorithmException {
        //入参校验（手机号密码不能为空）
        if (org.apache.commons.lang3.StringUtils.isEmpty(telphone)
                || org.apache.commons.lang3.StringUtils.isEmpty(password)){
            throw new BussinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"电话号码或则密码不能为空");
        }

        //用户登录服务，校验用户登录是否合法。将手机号和加密后的密码传到Service层
        UserModel userModel = userService.validateLogin(telphone, this.EncodeByMD5(password));

        //将登陆凭证加入到用户登录成功的session中
        //如果用户的会话标识中有IS_LOGIN，则表示登录成功
        this.httpServletRequest.getSession().setAttribute("IS_LOGIN", true);//key:IS_LOGIN
        //将userModel放到对应的session里
        this.httpServletRequest.getSession().setAttribute("LOGIN_USER", userModel);

        UserVO userVO = convertFromModel(userModel);

        //返回前端一个正确的信息(通用对象）
        return CommonReturnType.create(userVO);//success
    }

    //用户注册接口
    @PostMapping(value = "register",consumes = {CONTENT_TYPE_FORMED})
    public CommonReturnType register(@RequestParam(name = "telphone")String telphone,
                                     @RequestParam(name = "otpCode")String otpCode,
                                     @RequestParam(name = "name")String name,
                                     @RequestParam(name = "gender")Integer gender,
                                     @RequestParam(name = "age")Integer age,
                                     @RequestParam(name = "password")String password) throws BussinessException, UnsupportedEncodingException, NoSuchAlgorithmException {
        //验证手机号和对应的otpcode相符合（otpcode放在httpServletRequest）
        String inSessionOtpCode = (String)this.httpServletRequest.getSession().getAttribute(telphone);//获取otpcode
        if (!StringUtils.equals(otpCode, inSessionOtpCode)){    //与前端传上来的otpCode进行对比
            throw new BussinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR, "短信验证码不符合");
        }

        //用户的注册流程
        UserModel userModel = new UserModel();
        userModel.setName(name);
        userModel.setGender(new Byte(String.valueOf(gender.intValue())));
        userModel.setAge(age);
        userModel.setTelphone(telphone);
        userModel.setRegisterMode("byphone");
        userModel.setEncrptPassword(this.EncodeByMD5(password));//将密码加密存入数据库

        try{
            userService.register(userModel);
        }catch (DataIntegrityViolationException ex){
            throw new BussinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR, "手机号已注册");
        }

        return CommonReturnType.create(null);
    }

    //用户获取otp短信接口
    @PostMapping(value = "getotp",consumes = {CONTENT_TYPE_FORMED})
    public CommonReturnType getOtp(@RequestParam(name = "telphone")String telphone){
        //需要按照一定的规则生成otp验证码
        Random random = new Random();
        int randomInt = random.nextInt(89999);
        randomInt += 10000;//此时随机数取值[0,99999)
        String otpCode = String.valueOf(randomInt);


        //将otp验证码同对应用户的手机号关联，
        //使用HTTP session的方式绑定手机号与otpCode(redis非常适用）
        httpServletRequest.getSession().setAttribute(telphone, otpCode);


        //将otp验证码通过短信通道发送给用户，省略
        System.out.println("telphone = "+telphone+" & optCode = " + otpCode);
        return CommonReturnType.create(null);
    }

    //调用service服务获取对应id对象返回给前端
    @GetMapping(value = "get")
    public CommonReturnType getUser(@RequestParam(name = "id") Integer id) throws BussinessException {
        UserModel userModel = userService.getUserById(id);

        //若获取的对应用户信息不存在，抛出异常
        if (userModel == null) {
            throw new BussinessException(EmBusinessError.USER_NOT_EXIST);
        }

        //将核心领域模型用户对象转化为可供UI使用的viewobject，减少password等字段
        UserVO userVO = convertFromModel(userModel);

        //返回userVO ---> 返回通用对象：status & data（两个字段）
        return CommonReturnType.create(userVO);
    }

    //将UserModel转化为UserVO，供前端可视化
    private UserVO convertFromModel(UserModel userModel){
        if (userModel == null){
            return null;
        }
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(userModel, userVO);
        return userVO;
    }


    public String EncodeByMD5(String str) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        // 确定计算方法
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        BASE64Encoder base64Encoder = new BASE64Encoder();
        //加密字符串
        String newstr = base64Encoder.encode(md5.digest(str.getBytes("utf-8")));
        return newstr;
    }

}
