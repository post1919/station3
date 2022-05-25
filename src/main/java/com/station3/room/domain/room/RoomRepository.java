package com.station3.room.domain.room;

import com.station3.room.dto.RoomRequestDto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoomRepository extends JpaRepository<Room, Integer> {
    List<Room> findByRoomType(RoomRequestDto requestDto);
    List<Room> findByKeyword(RoomRequestDto requestDto);
}
