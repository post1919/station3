package com.station3.room.service.impl.impl;

import com.station3.room.domain.room.Room;
import com.station3.room.domain.room.RoomInfoRepository;
import com.station3.room.domain.room.RoomRepository;
import com.station3.room.dto.RoomRequestDto;
import com.station3.room.dto.RoomResponseDto;
import com.station3.room.service.impl.RoomService;
import com.station3.room.type.RoomType;
import com.station3.room.type.TradeType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
	private final RoomInfoRepository roomInfoRepository;

	@Override
	public RoomResponseDto getRoom(Integer roomSeq){
		Room room = roomRepository.findById(roomSeq).orElseThrow(()->new IllegalArgumentException("정보를 찾을 수 없습니다."));
		return new RoomResponseDto(room);
	}

	@Override
	public List<RoomResponseDto> getRoomList(RoomRequestDto roomRequestDto) {
		List<RoomResponseDto> roomResponseList = new ArrayList<>();
		List<Room> roomList = null;

		//검색조건 있는경우
		if( Objects.nonNull(roomRequestDto.getRoomType()) && roomRequestDto.getRoomType() != RoomType.TOTAL ) {
			roomList = roomRepository.findByRoomType(roomRequestDto);

		} else if( Objects.nonNull(roomRequestDto.getTradeType()) && roomRequestDto.getTradeType() != TradeType.TOTAL ) {
			roomList = roomInfoRepository.findByTradeType(roomRequestDto);

		} else if( Objects.nonNull(roomRequestDto.getKeyword()) ) {
			roomList = roomRepository.findByKeyword(roomRequestDto);

		} else {
			//Sort.by(Sort.Direction.ASC, "roomSeq")
			roomList = roomRepository.findAll();
		}


		roomList.stream().forEach(room->roomResponseList.add(new RoomResponseDto(room)));

		return roomResponseList;
	}

	@Override
	@Transactional
	public Integer update(Integer roomSeq, RoomRequestDto roomRequestDto) {
		Room room = roomRepository.findById(roomSeq).orElseThrow(()->new IllegalArgumentException("정보를 찾을 수 없습니다."));
		room.update(roomRequestDto.getRoomType(), roomRequestDto.getDelFlag());
		return roomSeq;
	}

	@Override
	public Integer save(RoomRequestDto roomRequestDto) {
		return roomRepository.save(roomRequestDto.toEntity()).getRoomSeq();
	}

	@Override
	public void delete(RoomRequestDto roomRequestDto) {
		roomRepository.delete(roomRequestDto.toEntity());
	}
}
