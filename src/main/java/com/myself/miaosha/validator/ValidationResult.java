package com.myself.miaosha.validator;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description: TODO
 * @Author: wanghailin
 * @Date: 2019/9/10
 */
@Data
public class ValidationResult {

    //检验结果是否有错
    private boolean hasErrors = false;
    //存放错误信息的map
    private Map<String,String> errorMsgMap = new HashMap<>();

    //判断是否有error

    //实现通用的通过格式化字符串信息获取错误结果的msg方法
    public String getErrMsg(){
        return StringUtils.join(errorMsgMap.values().toArray(),",");
    }

}
