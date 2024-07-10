package org.example.hzau.java.Mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Service;

@Mapper
public interface AdminMapper {
    @Select("SELECT count(*) from Admin where AdminAccount=#{AdminAccount} and AdminPassword=#{AdminPassword}")
    public int SelectAdmin(String AdminAccount,String AdminPassword);

}
