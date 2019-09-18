package com.myself.miaosha;

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

    @Test
    public void testDao(){
        //int userId=1;
        System.out.println(userDORepository.selectAll());
    }

}
