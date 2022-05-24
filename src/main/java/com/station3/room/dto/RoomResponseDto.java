package com.station3.room.dto;

import com.station3.room.domain.room.Room;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

@Getter
@ApiModel(description = "방 Response DTO")
public class RoomResponseDto {
    @ApiModelProperty(notes = "", value = "")
    protected Integer roomSeq;

    @ApiModelProperty(notes = "", value = "")
    protected String roomType;

    public RoomResponseDto(Room room){
        this.roomSeq = room.getRoomSeq();
        this.roomType = room.getRoomType();
    }
}
