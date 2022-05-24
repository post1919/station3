package com.station3.room.dto;

import com.station3.room.domain.room.Room;
import io.swagger.annotations.ApiModel;
import lombok.*;

@Getter
@NoArgsConstructor
@ToString(callSuper = true)
@ApiModel(description = "방 Request DTO")
public class RoomRequestDto {
    private String roomType;
    private String delFlag;

    @Builder
    public RoomRequestDto(String roomType, String delFlag){
        this.roomType = roomType;
        this.delFlag = delFlag;
    }

    public Room toEntity(){
        return Room.builder()
                .roomType(roomType)
                .delFlag(delFlag)
                .build();
    }
}
