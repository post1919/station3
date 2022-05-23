package com.station3.room.dto;

import com.station3.common.dto.CommonDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@Data
@ToString
@ApiModel(description = "방 DTO")
public class RoomDto extends CommonDto {
    @ApiModelProperty(notes = "", value = "")
    protected Integer roomSeq;

    @ApiModelProperty(notes = "", value = "")
    protected String roomType;
}
