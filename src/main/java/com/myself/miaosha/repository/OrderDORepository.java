package com.myself.miaosha.repository;

import com.myself.miaosha.dataobject.OrderDO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @Description: TODO
 * @Author: wanghailin
 * @Date: 2019/9/24
 */
@Repository
public interface OrderDORepository extends JpaRepository<OrderDO,Integer> {

}
