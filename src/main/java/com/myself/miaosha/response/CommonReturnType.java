package com.myself.miaosha.response;

import lombok.Data;

/**
 * @Description: TODO
 * @Author: wanghailin
 * @Date: 2019/9/10
 */
@Data
public class CommonReturnType {

    //表明对应请求的返回处理结果 "success"或"fail"
    private String status;
    //若status=success,则data内返回前端需要的json数据
    //若status=fail,则data内使用通用的错误码格式
    private Object data;

    //定义一个通用的创建方法（成功）
    public static CommonReturnType create(Object result){
        return CommonReturnType.create(result,"success");
    }

    //status 成功 or 失败
    public static CommonReturnType create(Object result, String status){
        CommonReturnType type = new CommonReturnType();
        type.setStatus(status);
        type.setData(result);
        return type;
    }

}
