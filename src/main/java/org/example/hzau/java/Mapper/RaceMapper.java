package org.example.hzau.java.Mapper;

import lombok.Data;
import org.apache.ibatis.annotations.*;
import org.example.hzau.java.Entity.*;

import java.util.List;

@Mapper
public interface RaceMapper {
    /*查询50米男子组相应比赛系列详细信息*/
    @Select("select * from 50mb")
    List<RaceMessage> SelectRace50mb();
    /*查询50米女子组相应比赛系列详细信息*/
    @Select("select * from 50mg")
    List<RaceMessage> SelectRace50mg();
    /*查询100米男子组相应比赛系列详细信息*/
    @Select("select * from 100mb")
    List<RaceMessage> SelectRace100mb();
    /*查询100米女子组相应比赛系列详细信息*/
    @Select("select * from 100mg")
    List<RaceMessage> SelectRace100mg();
    /*查询200米男子组相应比赛系列详细信息*/
    @Select("select * from 200mb")
    List<RaceMessage> SelectRace200mb();
    /*查询200米女子组相应比赛系列详细信息*/
    @Select("select * from 50mb")
    List<RaceMessage> SelectRace200mg();
    @Select("SELECT * from race where RaceName=#{RaceName}")
    List<Race> SelectRace(Race race);
    @Select("select * from raceresult where RaceName=#{RaceName}")
    List<RaceResult> SelectRaceResult(RaceResult raceResult);
    @Select("SELECT * from race")
    List<Race> SelectAll();

    @Delete("delete from race where RaceName=#{RaceName} and RaceLevel=#{RaceLevel} and RaceTime=#{RaceTime}")
    int DeleteRace(Race race);
    @Update("update  race set RaceTime=#{RaceTime} ,RacePlace=#{RacePlace} where RaceName=#{RaceName} and RaceLevel =#{RaceLevel}")
    int UpdateRace(Race race);
    @Insert("INSERT INTO Racer (RacerName,RacerSex,XueYuanName,HaoMa,UserAccount,RaceName) VALUES (#{RacerName},#{RacerSex},#{XueYuanName},#{HaoMa},#{UserAccount},#{RaceName})")
    int InsertRacer(Racer racer);
    @Select ("SELECT * from Racer where RaceName=#{RaceName}")
    List<Racer> SelectRacer(String RaceName);
    @Select("SELECT * from Racer")
    List<Racer> SelectRacer1();
    @Update("update Racer set HaoMa=#{HaoMa} where RaceName=#{RaceName} and RacerName=#{RacerName}")
    int UpdateRacer(Racer racer);
    @Update("update Racer set GGroup=#{GGroup},Ftrack=#{Ftrack} where RacerName=#{RacerName} and HaoMa=#{HaoMa}" )
    int Update(Racer racer);
    @Update("UPDATE Racer set Grade=#{Grade} where RaceName=#{RaceName} and RacerName=#{RacerName} and UserAccount=#{UserAccount} and HaoMa=#{HaoMa}")
    int UpdateRaceGrade(Racer racer);
    @Update("UPDATE Racer set LGrade=#{LGrade} where RaceName=#{RaceName} and RacerName=#{RacerName} and UserAccount=#{UserAccount} and HaoMa=#{HaoMa}")
    int UpdateRaceGrade1(Racer racer);


    @Select("SELECT * FROM racer WHERE RaceName = #{RaceName} ORDER BY grade ASC;")
    List<Racer> Winner(Winner winner);
    @Select("SELECT * FROM racer WHERE RaceName = #{RaceName} ORDER BY grade DESC;")
    List<Racer> Winner2(Winner winner);
    @Insert("INSERT INTO lracer (RacerName,RacerSex,XueYuanName,HaoMa,UserAccount,RaceName) VALUES (#{RacerName},#{RacerSex},#{XueYuanName},#{HaoMa},#{UserAccount},#{RaceName}")
    int InsertLracer(Racer racer);
    @Select("SELECT * FROM racer WHERE RaceName = #{RaceName} and LGrade is not null ORDER BY LGrade DESC;")
    List<Racer> Winner3(Race race);
    @Select("SELECT * FROM racer WHERE RaceName = #{RaceName} and LGrade is not null ORDER BY LGrade ASC;")
    List<Racer> Winner4(Race race);


    @Select("SELECT * FROM racer WHERE RaceName = #{RaceName} and LGrade is not null ORDER BY LGrade ASC;")
    List<Racer> Winner5(Racer racer);

    @Update("UPDATE Racer set Strack=#{Strack} where RaceName=#{RaceName} and RacerName=#{RacerName} and UserAccount=#{UserAccount} and HaoMa=#{HaoMa}")
    int UpdateRaceGrade2(Racer racer);



}