package com.myself.miaosha.validator;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

/**
 * @Description: TODO
 * @Author: wanghailin
 * @Date: 2019/9/10
 */
@Component
public class ValidatorImpl implements InitializingBean {

    //包装出Validator类
    private Validator validator;

    //实现校验方法并返回校验结果
    public ValidationResult validate(Object bean){
        ValidationResult result = new ValidationResult();

        Set<ConstraintViolation<Object>> constraintViolationSet = validator.validate(bean);
        if (constraintViolationSet.size() > 0){
            //有错误
            result.setHasErrors(true);
            //遍历constraintViolationSet
            constraintViolationSet.forEach(constraintViolation->{
                String errMsg = constraintViolation.getMessage();//错误信息
                String propertyName = constraintViolation.getPropertyPath().toString();//错误的字段名
                result.getErrorMsgMap().put(propertyName, errMsg);
            });
        }
        return result;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        // 将hibernate validator通过工厂的初始化方式使其实例化
        this.validator = Validation.buildDefaultValidatorFactory().getValidator();//校验器
    }
}
