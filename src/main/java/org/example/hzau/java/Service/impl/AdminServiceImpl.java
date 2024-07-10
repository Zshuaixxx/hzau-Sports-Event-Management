package org.example.hzau.java.Service.impl;

import org.example.hzau.java.Entity.*;
import org.example.hzau.java.Mapper.AdminMapper;
import org.example.hzau.java.Mapper.RaceMapper;
import org.example.hzau.java.Mapper.SeatMapper;
import org.example.hzau.java.Mapper.VolunteerMapper;
import org.example.hzau.java.Service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    AdminMapper adminMapper;
    @Autowired
    RaceMapper raceMapper;
    @Autowired
    VolunteerMapper volunteerMapper;
    @Autowired
    SeatMapper seatMapper;
    @Override
    public int AdminLogin(String AdminAccount, String AdminPassword) {
        return adminMapper.SelectAdmin(AdminAccount,AdminPassword);
    }
    @Override
    public List<Race> SelectRace() {
        return raceMapper.SelectAll();
    }

    @Override
    public int DeleteRace(Race race) {
        return raceMapper.DeleteRace(race);
    }
    @Override
    public int  ChangeRace(Race race) {
        return raceMapper.UpdateRace(race);
    }
    @Override
    public List<Volunteer> SelectVolunteer() {
        return volunteerMapper.SelectVolunteer();
    }
    @Override
    public int VolunteerChange(Volunteer volunteer) {
        return volunteerMapper.VolunteerChange(volunteer);
    }
    @Override
    public List<VolunteerTime> SelectVolunteerTime(){
        return volunteerMapper.SelectVolunteerTime();
    }
    @Override
    public int ChangeTime(VolunteerTime volunteerTime) {
        return volunteerMapper.ChangeTime(volunteerTime);
    }

    @Override
    public List<SeatMessage> SelectMessage(){
        return seatMapper.SelectSeatList();
    }

    @Override
    public int ChangeWhere(SeatMessage seatMessage) {
        return seatMapper.ChangeSeat(seatMessage);
    }
    @Override
    public int InsertRacer(Racer racer){
        return raceMapper.InsertRacer(racer);
    }
    @Override
    public List<Racer> SetHaoMa(String RaceName){
        return raceMapper.SelectRacer(RaceName);
    }
    @Override
    public List<Racer> SetHaoMa1(){
        return raceMapper.SelectRacer1();
    }
    @Override
    public int UpdateRacer(Racer racer){
        return raceMapper.UpdateRacer(racer);
    }

    @Override
    public int UpdateRaceGrade(List<Racer> racers){
        int i=0;
        int sum=racers.size();
        for(i=0;i<sum;i++){
            raceMapper.UpdateRaceGrade(racers.get(i));
        }
    return sum;
    }
}
