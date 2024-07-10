package org.example.hzau.java.Service;

import org.example.hzau.java.Entity.SeatMessage;
import org.example.hzau.java.Entity.User;
import org.example.hzau.java.Entity.Volunteer;
import org.example.hzau.java.Entity.VolunteerTime;

import java.util.List;

public interface UserService {
    /*用户登录*/
    int Login(User user);
    /*用户个人信息展示*/
    User UserMessageSelect(String UserAccount);
    /*用户查询座位图*/
    SeatMessage SelectSeat(SeatMessage seatMessage);
    /*用户搜索志愿时间点*/
    List<VolunteerTime> SelectTime();

    int VolunteerJoin(Volunteer user);

    List<Volunteer> SelectVolunteer(String UserAccount);
}
