package com.myself.miaosha.repository;

import com.myself.miaosha.dataobject.UserPasswordDO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


/**
 * @Description: TODO
 * @Author: wanghailin
 * @Date: 2019/9/10
 */

@Repository
public interface UserPasswordDORepository extends JpaRepository<UserPasswordDO, Integer> {


    /*void deleteById(Integer id);

    int insert(UserPasswordDO record);

    int insertSelective(UserPasswordDO record);*/

    /*UserPasswordDO findById(Integer id);*/

    UserPasswordDO findByUserId(Integer userId);

    @Modifying
    @Query(value = "update user_password set encrpt_password = :encrpt_password where user_id = :user_id",nativeQuery = true)
    void updateByIdSelective(@Param("encrpt_password") String encrpt_password, @Param("user_id") String user_id);

    @Modifying
    @Query(value = "update user_password set encrpt_password = :encrpt_password where id = :id",nativeQuery = true)
    void updateById(@Param("encrpt_password") String encrpt_password, @Param("id") String id);


}

