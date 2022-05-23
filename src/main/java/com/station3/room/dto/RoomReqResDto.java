package com.station3.room.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@Data
@ToString
@ApiModel(description = "방 DTO")
public class RoomReqResDto extends RoomDto {
}
