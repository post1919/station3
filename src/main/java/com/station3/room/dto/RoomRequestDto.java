package com.station3.room.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@ToString(callSuper = true)
@ApiModel(description = "방 DTO")
public class RoomRequestDto extends RoomDto {
}
