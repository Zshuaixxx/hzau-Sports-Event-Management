package org.example.hzau.java.Service.impl;

import org.example.hzau.java.Entity.*;
import org.example.hzau.java.Mapper.RaceMapper;
import org.example.hzau.java.Mapper.SeatMapper;
import org.example.hzau.java.Mapper.UserMapper;
import org.example.hzau.java.Mapper.VolunteerMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements org.example.hzau.java.Service.UserService , org.example.hzau.java.Service.RaceService{
    @Autowired
    UserMapper userMapper;
    @Autowired
    SeatMapper seatMapper;
    @Autowired
    VolunteerMapper volunteerMapper;
    @Autowired
    RaceMapper raceMapper;
    @Override
    /*用户登录功能*/
    public int Login(User user) {
        User ljs= userMapper.UserLogin(user);
        if(ljs!=null){
            return 1;
        }
        else return 0;
    }
    /*用户个人信息收集*/
    @Override
    public User UserMessageSelect(String UserAccount){
        return userMapper.UserMessageSelect(UserAccount);
    }
    @Override
    public SeatMessage SelectSeat(SeatMessage seatMessage){
        return seatMapper.SelectSeat(seatMessage);
    }

    @Override
    public List<VolunteerTime> SelectTime(){
        return volunteerMapper.SelectTime();
    }
    @Override
    public int VolunteerJoin(Volunteer user){
        volunteerMapper.InsertVolunteer(user);
        return 1;
    }
    @Override
    public List<Race> SelectRace(Race race){
        return raceMapper.SelectRace(race);
    }
    @Override
    public List<RaceResult> SelectRaceResult(RaceResult raceResult){
        return raceMapper.SelectRaceResult(raceResult);
    }

    @Override
    public List<Volunteer> SelectVolunteer(String UserAccount){
        return volunteerMapper.SelectVolunteer1(UserAccount);
    }


    @Override
    public List<Racer> SelectRacer(Race race){
        return raceMapper.SelectRacer(race.RaceName);
    }
    @Override
    public List<Racer> SelectRacer1(Race race){
        return raceMapper.Winner4(race);
    }
    @Override
    public List<Racer> SelectRacer2(Race race){
        return raceMapper.Winner3(race);
    }
}
