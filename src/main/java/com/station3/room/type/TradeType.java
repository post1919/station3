package com.station3.room.type;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonValue;
import com.station3.common.enumeration.CommonEnum;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
@Getter
@RequiredArgsConstructor
public enum TradeType implements CommonEnum {
    TOTAL("", "TOTAL"),
    MONTHLY("MONTHLY", "MONTHLY"),
    CHARTER("CHARTER", "CHARTER")
    ;

    private String code;
    private String desc;

    private TradeType(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    @JsonValue
    public String getCode() {
        return code;
    }

    public static TradeType getByCode(String code ) {
        for ( TradeType type : values()) {
            if( type.code.equals(code) ) {
                return type;
            }
        }
        throw new IllegalArgumentException( "TradeType No Data Found.." );
    }

    public static TradeType getByDesc(String desc) {
        for ( TradeType type : values()) {
            if( type.desc.equals(desc) ) {
                return type;
            }
        }
        throw new IllegalArgumentException( "TradeType No Data Found.." );
    }
}
