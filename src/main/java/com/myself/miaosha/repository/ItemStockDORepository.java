package com.myself.miaosha.repository;

import com.myself.miaosha.dataobject.ItemStockDO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Description: TODO
 * @Author: wanghailin
 * @Date: 2019/9/23
 */
@Repository
public interface ItemStockDORepository extends JpaRepository<ItemStockDO, Integer> {

    ItemStockDO findItemStockDOByItemId(Integer itemId);

    //减少库存
    @Modifying
    @Transactional
    @Query(value = "update item_stock set stock = stock-:amount where item_id = :itemId and stock >= :amount",nativeQuery = true)
    int decreaseStock(@Param("itemId")Integer itemId, @Param("amount")Integer amount);

}
