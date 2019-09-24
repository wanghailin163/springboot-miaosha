package com.myself.miaosha.repository;

import com.myself.miaosha.dataobject.ItemDO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Description: TODO
 * @Author: wanghailin
 * @Date: 2019/9/23
 */
@Repository
public interface ItemDORepository extends JpaRepository<ItemDO, Integer> {

    @Query(value = "select * from item order by price",nativeQuery = true)
    List<ItemDO> listItem();

    ItemDO findItemDOById(Integer id);

    //增加销量
    @Modifying
    @Transactional
    @Query(value = "update item set sales = sales + :amount where id = :itemId",nativeQuery = true)
    void increaseSales(@Param("itemId")Integer itemId, @Param("amount")Integer amount);

}
