package org.example.hzau.java.Mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.example.hzau.java.Entity.SeatMessage;

import java.util.List;

@Mapper
public interface SeatMapper {
    @Select("SELECT * from seatmessage where XueYuanName=#{XueYuanName}")
    SeatMessage SelectSeat(SeatMessage seatMessage);
    @Select("SELECT * from seatmessage ")
    List<SeatMessage> SelectSeatList();

    @Update("UPDATE seatmessage set Message=#{Message} where XueYuanName=#{XueYuanName}")
    int ChangeSeat(SeatMessage seatMessage);
}
