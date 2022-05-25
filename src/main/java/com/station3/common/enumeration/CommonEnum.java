package com.station3.common.enumeration;

/**
 * @author    : 신동아
 * @desc      : 공통 기본 Enum
 * @remark    :
 * @since     : 2022-05-25
 */
public interface CommonEnum {
    public static final String DEFAULT_GETTER_NAME = "getCode";


    String getCode();
    default public boolean equalsCode(String code) {
        return this.getCode().equals(code);
    }
}
