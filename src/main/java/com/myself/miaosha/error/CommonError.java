package com.myself.miaosha.error;

/**
 * @Description: TODO
 * @Author: wanghailin
 * @Date: 2019/9/9
 */
public interface CommonError {

    public int getErrCode();
    public String getErrMsg();
    public CommonError setErrMsg(String errMsg);
}
