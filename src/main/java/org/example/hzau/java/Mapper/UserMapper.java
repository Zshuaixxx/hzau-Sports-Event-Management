package org.example.hzau.java.Mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.example.hzau.java.Entity.User;

@Mapper
public interface UserMapper {
    /*登录查询*/
    @Select("select * from user where UserAccount=#{UserAccount} and UserPassword=#{UserPassword}")
    User UserLogin(User user);
    /*查询个人信息*/
    @Select("select * from user where UserAccount=#{UserAccount}")
    User UserMessageSelect(String UserAccount);

}
