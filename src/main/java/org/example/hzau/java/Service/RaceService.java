package org.example.hzau.java.Service;

import org.example.hzau.java.Entity.Race;
import org.example.hzau.java.Entity.RaceMessage;
import org.example.hzau.java.Entity.RaceResult;
import org.example.hzau.java.Mapper.RaceMapper;

import java.util.List;

public interface RaceService {

    List<Race> SelectRace(Race race);
    List<RaceResult> SelectRaceResult(RaceResult raceResult);

}
