package com.station3.room.type;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonValue;
import com.station3.common.enumeration.CommonEnum;
import com.station3.exception.type.ErrorResponseEnumDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
@Getter
@RequiredArgsConstructor
public enum RoomType implements CommonEnum {
    TOTAL("", "TOTAL"),
    ONE("ONE", "ONE"),
    TWO("TWO", "TWO"),
    THREE("THREE", "THREE")
    ;

    private String code;
    private String desc;

    private RoomType(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    @JsonValue
    public String getCode() {
        return code;
    }

    public static RoomType getByCode(String code ) {
        for ( RoomType type : values()) {
            if( type.code.equals(code) ) {
                return type;
            }
        }
        throw new IllegalArgumentException( "RoomType No Data Found.." );
    }

    public static RoomType getByDesc(String desc) {
        for ( RoomType type : values()) {
            if( type.desc.equals(desc) ) {
                return type;
            }
        }
        throw new IllegalArgumentException( "RoomType No Data Found.." );
    }
}
