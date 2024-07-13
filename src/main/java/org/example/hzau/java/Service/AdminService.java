package org.example.hzau.java.Service;

import org.example.hzau.java.Entity.*;

import java.util.List;

public interface AdminService {
    int AdminLogin(String AdminAccount,String AdminPassword);
    List<Race> SelectRace();
    int DeleteRace(Race race);
    int ChangeRace(Race race);

    List<Volunteer> SelectVolunteer();

    int VolunteerChange(Volunteer volunteer);

    List<VolunteerTime> SelectVolunteerTime();

    int ChangeTime(VolunteerTime volunteerTime);

    List<SeatMessage> SelectMessage();

    int ChangeWhere(SeatMessage seatMessage);

    int InsertRacer(Racer racer);
    List<Racer> SetHaoMa(String RaceName);
    List<Racer> SetHaoMa1();

    int UpdateRacer(Racer race);

    int UpdateRaceGrade(List<Racer> racers);
    int UpdateRaceGrade2(List<Racer> racers);


    List<Racer> Winner(Winner winner);

    List<Racer> Winner2(Winner winner);

    int Update(Racer racer);

    List SuccessGroup(Race race);
}
