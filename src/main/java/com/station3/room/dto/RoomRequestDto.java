package com.station3.room.dto;

import com.station3.room.domain.room.Room;
import com.station3.room.type.RoomType;
import com.station3.room.type.TradeType;
import io.swagger.annotations.ApiModel;
import lombok.*;

@Getter
@NoArgsConstructor
@ToString(callSuper = true)
@ApiModel(description = "방 Request DTO")
public class RoomRequestDto {
    private RoomType roomType;
    private TradeType tradeType;
    private String delFlag;
    private String keyword;

    @Builder
    public RoomRequestDto(RoomType roomType, TradeType tradeType, String keyword, String delFlag){
        this.roomType = roomType;
        this.tradeType = tradeType;
        this.keyword = keyword;
        this.delFlag = delFlag;
    }

    public Room toEntity(){
        return Room.builder()
                .roomType(roomType)
                .tradeType(tradeType)
                .delFlag(delFlag)
                .build();
    }
}
