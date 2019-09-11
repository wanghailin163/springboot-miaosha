package com.myself.miaosha.controller;

import com.myself.miaosha.error.BussinessException;
import com.myself.miaosha.error.EmBusinessError;
import com.myself.miaosha.response.CommonReturnType;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description: TODO
 * @Author: wanghailin
 * @Date: 2019/9/10
 */

public class BaseController {

    public static final String CONTENT_TYPE_FORMED="application/x-www-form-urlencoded";

    public Object handlerException(HttpServletRequest request, Exception ex){
        Map<String,Object> responseData = new HashMap<>();
        if(ex instanceof BussinessException){
            //把异常强转为bussinessException
            BussinessException bussinessException = (BussinessException)ex;
            responseData.put("errCode", bussinessException.getErrCode());
            responseData.put("errMsg", bussinessException.getErrMsg());
        }else{
            responseData.put("errCode", EmBusinessError.UNKNOW_ERROR.getErrCode());
            responseData.put("errMsg", EmBusinessError.UNKNOW_ERROR.getErrMsg());
        }
        CommonReturnType commonReturnType = new CommonReturnType();
        commonReturnType.setStatus("fail");
        commonReturnType.setData(responseData);
        return CommonReturnType.create(responseData, "fail");
    }

}
