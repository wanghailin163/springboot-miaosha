package com.myself.miaosha.service.impl;

import com.myself.miaosha.dataobject.PromoDO;
import com.myself.miaosha.repository.PromoDORepository;
import com.myself.miaosha.service.PromoService;
import com.myself.miaosha.service.model.PromoModel;
import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Description: TODO
 * @Author: wanghailin
 * @Date: 2019/9/24
 */
@Service
public class PromoServiceImpl implements PromoService {

    @Autowired
    private PromoDORepository promoDORepository;


    @Override
    public PromoModel getPromoById(Integer itemId) {
        //获取对应商品的秒杀信息
        PromoDO promoDO = promoDORepository.findPromoDOByItemId(itemId);
        PromoModel promoModel = convertFromDataObject(promoDO);
        if(promoModel == null){
            return null;
        }

        //判断秒杀活动的当前状态
        DateTime now = new DateTime();
        if(promoModel.getStartDate().isAfterNow()){
            promoModel.setStatus(1);//开始时间在当前时间之后(秒杀未开始)
        }else if(promoModel.getEndDate().isBeforeNow()){
            promoModel.setStatus(3);//结束时间在当前时间之前(秒杀已结束)
        }else{
            promoModel.setStatus(2);//秒杀正在进行中
        }
        return promoModel;
    }

    private PromoModel convertFromDataObject(PromoDO promoDO){
        if(promoDO == null) {
            return null;
        }
        PromoModel promoModel = new PromoModel();
        BeanUtils.copyProperties(promoDO,promoModel);

        promoModel.setStartDate(new DateTime(promoDO.getStartDate()));
        promoModel.setEndDate(new DateTime(promoDO.getEndDate()));

        return promoModel;
    }


}
