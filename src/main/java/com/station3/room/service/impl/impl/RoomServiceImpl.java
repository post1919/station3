package com.station3.room.service.impl.impl;

import com.station3.room.domain.room.Room;
import com.station3.room.domain.room.RoomRepository;
import com.station3.room.dto.RoomRequestDto;
import com.station3.room.dto.RoomResponseDto;
import com.station3.room.service.impl.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @content :
 * @name :
 * @date : 2022-05-22
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

	@Override
	public List<RoomResponseDto> getRoomList(RoomRequestDto roomRequestDto) {
		List<RoomResponseDto> roomResponseList = new ArrayList<>();

		List<Room> roomList = roomRepository.findAll();
		roomList.stream().forEach(room->{
			roomResponseList.add(new RoomResponseDto(room));
		});

		return roomResponseList;
	}

	@Override
	@Transactional
	public Integer update(Integer roomSeq, RoomRequestDto roomRequestDto) {
		Room room = roomRepository.findById(roomSeq).orElseThrow(()->new IllegalArgumentException("정보를 찾을 수 없습니다."));
		room.update(roomRequestDto.getRoomType());
		return roomSeq;
	}

	@Override
	public Integer save(RoomRequestDto roomRequestDto) {
		return roomRepository.save(roomRequestDto.toEntity()).getRoomSeq();
	}


}
