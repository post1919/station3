package com.station3.room.service.impl.impl;

import com.station3.room.domain.room.Room;
import com.station3.room.domain.room.RoomRepository;
import com.station3.room.dto.RoomReqResDto;
import com.station3.room.dto.RoomResponseDto;
import com.station3.room.service.impl.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @content :
 * @name :
 * @date : 2021-10-11
 * @author : 신동아
 * @return :
 **/
@RequiredArgsConstructor
@Service
public class RoomServiceImpl implements RoomService {

	private final RoomRepository roomRepository;

	@Override
	public RoomResponseDto getRoom(Integer roomSeq){
		Room room = roomRepository.findById(roomSeq).orElseThrow(()->new IllegalArgumentException("정보를 찾을 수 없습니다."));
		return new RoomResponseDto(room);
	}
}
