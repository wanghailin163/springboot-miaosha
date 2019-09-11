package com.myself.miaosha.repository;

import com.myself.miaosha.dataobject.UserDO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * @Description: TODO
 * @Author: wanghailin
 * @Date: 2019/9/9
 */
@Repository
public interface UserDORepository extends JpaRepository<UserDO, Integer> {

    /*void deleteById(Integer id);

    int insert(UserDO record);

    int insertSelective(UserDO record);*/

    UserDO findUserDOById(Integer id);

    UserDO findUserDOByTelphone(String telphone);

    /*int updateByPrimaryKeySelective(UserDO record);

    int updateByPrimaryKey(UserDO record);*/

}
