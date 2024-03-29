package com.myself.miaosha.service.impl;

import com.myself.miaosha.dataobject.OrderDO;
import com.myself.miaosha.dataobject.SequenceDO;
import com.myself.miaosha.error.BussinessException;
import com.myself.miaosha.error.EmBusinessError;
import com.myself.miaosha.repository.OrderDORepository;
import com.myself.miaosha.repository.SequenceDORepository;
import com.myself.miaosha.service.ItemService;
import com.myself.miaosha.service.OrderService;
import com.myself.miaosha.service.UserService;
import com.myself.miaosha.service.model.ItemModel;
import com.myself.miaosha.service.model.OrderModel;
import com.myself.miaosha.service.model.UserModel;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @Description: TODO
 * @Author: wanghailin
 * @Date: 2019/9/24
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private ItemService itemService;
    @Autowired
    private UserService userService;
    @Autowired
    private OrderDORepository orderDORepository;
    @Autowired
    private SequenceDORepository sequenceDORepository;

    @Override
    @Transactional(rollbackFor=Exception.class)
    public OrderModel createOrder(Integer userId, Integer itemId, Integer promoId, Integer amount) throws BussinessException {
        //1.检验下单状态（用户是否合法，下单商品是否存在，购买数量是否正确）
        ItemModel itemModel = itemService.getItemById(itemId);
        if(itemModel == null){
            throw new BussinessException((EmBusinessError.PARAMETER_VALIDATION_ERROR),"商品不存在");
        }
        UserModel userModel = userService.getUserById(userId);
        if(userId == null){
            throw new BussinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"用户不存在");
        }
        if(amount <= 0 || amount > 99){
            throw new BussinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"商品数量信息不正确");
        }

        //校验活动信息
        if(promoId!=null){
            //1.校验对应活动是否存在对应商品
            if(promoId.intValue()!=itemModel.getPromoModel().getId()){
                throw new BussinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"活动信息不正确");
            }else if(itemModel.getPromoModel().getStatus().intValue() != 2){
                // 2.校验活动是否在进行中
                throw new BussinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"活动未开始");
            }
        }

        //2.落单减库存
        boolean result = itemService.decreaseStock(itemId,amount);
        if(!result) {
            throw new BussinessException(EmBusinessError.STOCK_NOT_ENOUGH);
        }

        //3.订单入库
        OrderModel orderModel = new OrderModel();
        orderModel.setUserId(userId);
        orderModel.setItemId(itemId);
        orderModel.setAmount(amount);
        if(promoId!=null){
            orderModel.setItemPrice(itemModel.getPromoModel().getPromoItemPrice());
        }else{
            orderModel.setItemPrice(itemModel.getPrice());
        }
        orderModel.setPromoId(promoId);
        orderModel.setOrderPrice(orderModel.getItemPrice().multiply(BigDecimal.valueOf(amount)));

        //生成交易流水号，订单号
        orderModel.setId(generatorOrderNo());
        OrderDO orderDO = convertFromOrderModel(orderModel);
        orderDORepository.save(orderDO);

        //商品销量增加
        itemService.increaseSales(itemId,amount);

        //返回前端
        return orderModel;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public String generatorOrderNo(){
        //1.订单号有16位
        StringBuilder stringBuilder = new StringBuilder();
        //前8位为时间信息，年月日
        LocalDateTime now = LocalDateTime.now();
        String nowDate = now.format(DateTimeFormatter.ISO_DATE).replace("-","");
        stringBuilder.append(nowDate);

        //2.中间六位为自增序列
        int sequence = 0;
        SequenceDO sequenceDO = sequenceDORepository.findSequenceDOByName("order_info");
        sequence = sequenceDO.getCurrentValue();//之前sequence
        sequenceDO.setCurrentValue(sequenceDO.getCurrentValue()+sequenceDO.getStep());//之前序列增加步长为当前序列
        sequenceDORepository.save(sequenceDO);//更新

        //凑足六位
        String sequenceStr = String.valueOf(sequence);
        for(int i = 0;i<6-sequenceStr.length();i++){
            stringBuilder.append(0);
        }
        stringBuilder.append(sequenceStr);

        //3.最后两位分库分表位
        stringBuilder.append("00");//先默认00 不分库分表
        return stringBuilder.toString();
    }

    private OrderDO convertFromOrderModel(OrderModel orderModel){
        if(orderModel==null){
            return null;
        }
        OrderDO orderDO = new OrderDO();
        BeanUtils.copyProperties(orderModel,orderDO);
        return orderDO;
    }
}
