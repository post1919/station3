package com.station3.room.domain.room;

import com.station3.room.type.RoomType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoomRepository extends JpaRepository<Room, Integer> {
    List<Room> findByRoomType(RoomType roomType);
//    List<Room> findByTradeType(RoomInfo roomInfo);
    //List<Room> findByKeyword(RoomRequestDto requestDto);

    //List<Room> findAll(Specification<Room> spec, Pageable pageable);
}
