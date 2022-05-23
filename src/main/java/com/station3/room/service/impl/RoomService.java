package com.station3.room.service.impl;

import com.station3.room.dto.RoomResponseDto;

public interface RoomService {
    RoomResponseDto getRoom(Integer roomSeq);
}