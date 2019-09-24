package com.myself.miaosha.service;

import com.myself.miaosha.error.BussinessException;
import com.myself.miaosha.service.model.OrderModel;

/**
 * @Description: TODO
 * @Author: wanghailin
 * @Date: 2019/9/24
 */
public interface OrderService {

    OrderModel createOrder(Integer userId, Integer itemId, Integer promoId, Integer amount) throws BussinessException;

}
