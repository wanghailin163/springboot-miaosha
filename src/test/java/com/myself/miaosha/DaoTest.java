package com.myself.miaosha;

import com.myself.miaosha.dataobject.ItemDO;
import com.myself.miaosha.dataobject.ItemStockDO;
import com.myself.miaosha.repository.ItemDORepository;
import com.myself.miaosha.repository.ItemStockDORepository;
import com.myself.miaosha.repository.PromoDORepository;
import com.myself.miaosha.repository.UserDORepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

/**
 * @Description: TODO
 * @Author: wanghailin
 * @Date: 2019/9/18
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@EnableAutoConfiguration
public class DaoTest {

    @Autowired
    private UserDORepository userDORepository;

    @Autowired
    private ItemDORepository itemDORepository;

    @Autowired
    private ItemStockDORepository itemStockDORepository;

    @Autowired
    private PromoDORepository promoDORepository;

    @Test
    public void testDao(){

        /*ItemDO itemDO = new ItemDO();
        itemDO.setTitle("iphone 6");
        itemDO.setDescription("好用的手机");
        itemDO.setPrice(new BigDecimal(5288.00));
        itemDO.setSales(30000);
        itemDO.setImgUrl("www.xxx.com");
        itemDORepository.save(itemDO);*/

        /*ItemStockDO itemStockDO = new ItemStockDO();
        itemStockDO.setItemId(46);
        itemStockDO.setStock(100);
        itemStockDORepository.save(itemStockDO);*/

        System.out.println(promoDORepository.findPromoDOByItemId(1));



        //itemDORepository.increaseSales(45,123);

        //System.out.println(userDORepository.findUserDOByTelphone("13908148456"));
        //int userId=1;
        //System.out.println(userDORepository.selectAll());
    }

}
