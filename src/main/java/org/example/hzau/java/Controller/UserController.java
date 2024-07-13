package org.example.hzau.java.Controller;

import org.example.hzau.java.Entity.*;
import org.example.hzau.java.Service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserServiceImpl userServiceimpl;
    @Autowired
    RedisTemplate redisTemplate;

    @PostMapping("/login")
    /*实现用户登录功能，前端发送给后端账号密码，成功1，失败0*/
    public int login(@RequestBody User user) {
        return userServiceimpl.Login(user);
    }

    /*实现用户个人中心信息展示功能*/
    @PostMapping("/UserMessage/Select")
    public User MessageSelect(@RequestBody User user) {
        String UserAccount = user.getUserAccount();
        return userServiceimpl.UserMessageSelect(UserAccount);
    }

    @PostMapping("/Volunteer/Join")
    public int VolunteerJoin(@RequestBody VolunteerMessage user) {
        String UserAccount = user.getUserAccount()+user.getDataTime();
        System.out.print(UserAccount);
        Jedis jedis = new Jedis("localhost", 6379);
        // 检查key 也就是学号 是否存在
        boolean keyExists = jedis.exists(UserAccount);
        if (keyExists) {
            return 0;
        }
        /*不存在则加入并且设置过期时长为10天*/
        else {
            redisTemplate.opsForValue().set(UserAccount, "Volunteer", 10, TimeUnit.DAYS);
            User ljs=userServiceimpl.UserMessageSelect(user.getUserAccount());
            System.out.println(ljs);
            Volunteer volunteer=new Volunteer();
            volunteer.UserAccount= ljs.getUserAccount();
            volunteer.UserName=ljs.getUserName();
            volunteer.UserPhoneNumber=ljs.getUserPhoneNumber();
            volunteer.VolunteerTime=user.getDataTime();
            userServiceimpl.VolunteerJoin(volunteer);
            return 1;
        }

    }
    /*实现座次查询功能*/
    @PostMapping("/ZuoWei")
    public SeatMessage SelectMessage(@RequestBody SeatMessage seatMessage) {
        System.out.println(seatMessage);
        String XueYuanName = seatMessage.getXueYuanName();
        return userServiceimpl.SelectSeat(seatMessage);
    }
    /*实现志愿时间查询功能*/
    @PostMapping("/Volunteer/Time")
    public List<VolunteerTime> SelectTime(){
        return userServiceimpl.SelectTime();
    }
    @PostMapping("/Race/Message")
    public List<Race> SelectRace(@RequestBody Race race){
        return userServiceimpl.SelectRace(race);
    }
    @PostMapping("/Race/Result")
    public List<RaceResult> SelectRaceResult(@RequestBody RaceResult raceResult){
        System.out.println(raceResult);
        System.out.println(userServiceimpl.SelectRaceResult(raceResult));
        return userServiceimpl.SelectRaceResult(raceResult);
    }
    /*个人界面展示详细报名后信息*/
    @PostMapping("/User/Select/BaoMing")
    public List<Volunteer> selectBaoMing(@RequestBody Volunteer volunteer){
        return userServiceimpl.SelectVolunteer(volunteer.getUserAccount());
    }
    /*用户小程序界面展示每个项目比赛的结果（初赛，决赛）*/
    @PostMapping("/Race/Result1")
    public List<Racer> SelectRaceResult(@RequestBody Race race){
        if(race.RaceLevel.equals("初赛")){
        return userServiceimpl.SelectRacer(race);}else{
            System.out.println("sssss");
            String RaceName=race.getRaceName();
            Set<String> raceNames = new HashSet<>(Arrays.asList(
                    "50mB", "50mG", "100mB", "100mG", "200mB", "200mG",
                    "400mB", "400mG", "800mG", "800mB", "1000mB", "1000mG",
                    "1500mB", "1500mG", "2000mB", "2000mG", "5000mB", "5000mG",
                    "20-50mB", "20-50mG", "4-100mB", "4-100mG"
            ));
            if(raceNames.contains(RaceName)){
                List<Racer> racers= userServiceimpl.SelectRacer1(race);
                System.out.println(racers);
                return racers;
            }else {
                System.out.println(race);
                return userServiceimpl.SelectRacer2(race);
            }
        }
    }
}
