package com.station3.room.domain.room;

import com.station3.room.dto.RoomRequestDto;
import com.station3.room.type.TradeType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoomInfoRepository extends JpaRepository<RoomInfo, Integer> {

}
