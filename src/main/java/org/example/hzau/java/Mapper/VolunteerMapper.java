package org.example.hzau.java.Mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.example.hzau.java.Entity.User;
import org.example.hzau.java.Entity.Volunteer;
import org.example.hzau.java.Entity.VolunteerTime;

import java.util.List;

@Mapper
public interface VolunteerMapper {
    @Insert("INSERT INTO volunteer (UserAccount,UserName,UserPhoneNumber,VolunteerTime)" +
            "values" +
            "(#{UserAccount},#{UserName},#{UserPhoneNumber},#{VolunteerTime})")
    int InsertVolunteer(Volunteer user);
    @Select("select * from VolunteerTime")
    List<VolunteerTime> SelectTime();

    @Select("SELECT * from volunteer")
    List<Volunteer> SelectVolunteer();
    @Update("update volunteer set UserMessage=#{UserMessage} where UserAccount=#{UserAccount}")
    int VolunteerChange(Volunteer volunteer);
    @Select("SELECT * from volunteertime")
    List<VolunteerTime> SelectVolunteerTime();
    @Update("update volunteertime set text=#{text} where value=#{value}")
    int ChangeTime(VolunteerTime volunteerTime);
    @Select("SELECT * from volunteer where UserAccount=#{UserAccount}")
    List<Volunteer> SelectVolunteer1(String UserAccount);
}