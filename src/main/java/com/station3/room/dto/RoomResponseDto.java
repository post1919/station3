package com.station3.room.dto;

import com.station3.room.domain.room.Room;
import com.station3.room.domain.room.RoomInfo;
import com.station3.room.type.RoomType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

import java.util.List;

@Getter
@ApiModel(description = "방 Response DTO")
public class RoomResponseDto {
    @ApiModelProperty(notes = "", value = "")
    private Integer roomSeq;

    @ApiModelProperty(notes = "", value = "")
    private RoomType roomType;

    @ApiModelProperty(notes = "", value = "")
    private String delFlag;

    private List<RoomInfo> roomInfo;

    public RoomResponseDto(Room room){
        this.roomSeq = room.getRoomSeq();
        this.roomType = room.getRoomType();
        this.roomInfo = room.getRoomInfos();
    }
}
