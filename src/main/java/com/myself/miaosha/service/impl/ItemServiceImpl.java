package com.myself.miaosha.service.impl;

import com.myself.miaosha.dataobject.ItemDO;
import com.myself.miaosha.dataobject.ItemStockDO;
import com.myself.miaosha.error.BussinessException;
import com.myself.miaosha.error.EmBusinessError;
import com.myself.miaosha.repository.ItemDORepository;
import com.myself.miaosha.repository.ItemStockDORepository;
import com.myself.miaosha.service.ItemService;
import com.myself.miaosha.service.PromoService;
import com.myself.miaosha.service.model.ItemModel;
import com.myself.miaosha.service.model.PromoModel;
import com.myself.miaosha.validator.ValidationResult;
import com.myself.miaosha.validator.ValidatorImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Description: TODO
 * @Author: wanghailin
 * @Date: 2019/9/23
 */
@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private ValidatorImpl validator;
    @Autowired
    private ItemDORepository itemDORepository;
    @Autowired
    private ItemStockDORepository itemStockDORepository;
    @Autowired
    private PromoService promoService;

    private ItemDO convertItemDOFromItemModel(ItemModel itemModel){
        if(itemModel == null) {
            return null;
        }
        ItemDO itemDO = new ItemDO();
        BeanUtils.copyProperties(itemModel,itemDO);
        return itemDO;
    }

    private ItemStockDO convertItemStockDOFromItemModel(ItemModel itemModel){
        if(itemModel == null) {
            return null;
        }
        ItemStockDO itemStockDO = new ItemStockDO();
        BeanUtils.copyProperties(itemModel,itemStockDO);
        return itemStockDO;
    }

    private ItemModel convertModelFromDataObject(ItemDO itemDO,ItemStockDO itemStockDO){
        ItemModel itemModel = new ItemModel();
        BeanUtils.copyProperties(itemDO,itemModel);
        itemModel.setStock(itemStockDO.getStock());
        return itemModel;
    }


    @Override
    @Transactional(rollbackFor=Exception.class)
    public ItemModel createItem(ItemModel itemModel) throws BussinessException {
        //校验入参
        ValidationResult result = validator.validate(itemModel);
        if(result.isHasErrors()){
            throw new BussinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,result.getErrMsg());
        }
        //转化itemMode-》dataobject
        ItemDO itemDO = this.convertItemDOFromItemModel(itemModel);
        //写入数据库
        itemDORepository.save(itemDO);
        itemModel.setId(itemDO.getId());

        ItemStockDO itemStockDO = this.convertItemStockDOFromItemModel(itemModel);
        itemStockDORepository.save(itemStockDO);

        //返回创建完成的对象，这里要返回存入数据库的对象
        return this.getItemById(itemModel.getId());
    }

    @Override
    public List<ItemModel> listItem() {
        List<ItemDO> itemDOList = itemDORepository.listItem();
        List<ItemModel> itemModelList = itemDOList.stream().map(itemDO -> {
            ItemStockDO itemStockDO = itemStockDORepository.findItemStockDOByItemId(itemDO.getId());
            ItemModel itemModel = this.convertModelFromDataObject(itemDO,itemStockDO);
            return itemModel;
        }).collect(Collectors.toList());

        return itemModelList;
    }

    @Override
    public ItemModel getItemById(Integer id) {
        ItemDO itemDO = itemDORepository.findItemDOById(id);
        if(itemDO == null) {
            return null;
        }
        //操作获得库存数量
        ItemStockDO itemStockDO = itemStockDORepository.findItemStockDOByItemId(itemDO.getId());
        //将dataobject转换成model
        ItemModel itemModel = this.convertModelFromDataObject(itemDO,itemStockDO);

        //获取商品秒杀活动信息
        PromoModel promoModel = promoService.getPromoById(itemModel.getId());
        if(promoModel != null && promoModel.getStatus().intValue() != 3) {
            itemModel.setPromoModel(promoModel);
        }

        return itemModel ;
    }

    @Override
    public boolean decreaseStock(Integer itemId, Integer amount) {
        int affectedRow = itemStockDORepository.decreaseStock(itemId,amount);
        if(affectedRow > 0){
            return true;
        }else {
            return false;
        }
    }

    @Override
    public void increaseSales(Integer itemId, Integer amount){
        itemDORepository.increaseSales(itemId,amount);
    }
}
