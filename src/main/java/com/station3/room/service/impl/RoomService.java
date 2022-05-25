package com.station3.room.service.impl;

import com.station3.room.dto.RoomRequestDto;
import com.station3.room.dto.RoomResponseDto;

import java.util.List;

public interface RoomService {
    RoomResponseDto getRoom(Integer roomSeq);

    List<RoomResponseDto> getRoomList(RoomRequestDto roomRequestDto);

    Integer save(RoomRequestDto roomRequestDto);

    Integer update(Integer roomSeq, RoomRequestDto roomRequestDto);

    void delete(RoomRequestDto roomRequestDto);
}