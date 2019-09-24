package com.myself.miaosha.repository;

import com.myself.miaosha.dataobject.PromoDO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @Description: TODO
 * @Author: wanghailin
 * @Date: 2019/9/24
 */
@Repository
public interface PromoDORepository extends JpaRepository<PromoDO, Integer> {

    PromoDO findPromoDOByItemId(Integer id);

}
