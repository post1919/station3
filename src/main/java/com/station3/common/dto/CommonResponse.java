package com.station3.common.dto;

import com.station3.common.PaginationInfo;
import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Getter
public class CommonResponse<T> extends BasicResponse implements Serializable {
    private Integer code;
    private String message;
    private String detail;
    private Integer count;
    private String status;
    private T data;
    private PaginationInfo paginationInfo;


    public CommonResponse(){
        this.count = 1;
    }

    public CommonResponse(T data){
        this.data = data;
        if(Objects.isNull(data) ) {
            this.count = 0;
        } else if(data instanceof List){
            this.count = ((List<?>)data).size();
        } else {
            this.count = 1;
        }
    }

    /*@Builder
    public CommonResponse(Integer code, String message, String detail, int count, String status, T data){
        this(data);
        this.code = code;
        this.message = message;
        this.detail = detail;
        this.status = status;
    }*/

    @Builder
    public CommonResponse(Integer code, String message, String detail, Integer count, String status, T data, PaginationInfo paginationInfo){
        this(data);
        this.code = code;
        this.message = message;
        this.detail = detail;
        this.paginationInfo = paginationInfo;

        //count 파라미터 값 설정 안된경우(없는경우) data의 사이즈로 count사용하도록 처리
        if( Objects.nonNull(count) ) {
            this.count = count;
        }

        this.status = status;
    }
}
