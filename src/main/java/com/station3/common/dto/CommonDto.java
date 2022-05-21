package com.station3.common.dto;

import lombok.Data;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public abstract class CommonDto implements Serializable {

    protected LocalDateTime regDate;

    protected String regId;

    protected LocalDateTime modDate;

    protected String modId;

    protected String delFlag;

    //페이징[s]
    /** 현재 페이지 번호 */
    private int currentPageNo;

    /** 페이지당 출력할 데이터 개수 */
    private int recordsPerPage;

    /** 화면 하단에 출력할 페이지 사이즈 */
    private int pageSize;

    /** 검색 키워드 */
    private String searchKeyword;

    /** 검색 유형 */
    private String fromDate;
    private String toDate;

    protected CommonDto() {
        this.currentPageNo = 1;
        this.recordsPerPage = 10;
        this.pageSize = 10;
    }

    protected int getStartPage() {
        return (currentPageNo - 1) * recordsPerPage;
    }

    protected String makeQueryString(int pageNo) {

        UriComponents uriComponents = UriComponentsBuilder.newInstance()
                .queryParam("currentPageNo", pageNo)
                .queryParam("recordsPerPage", recordsPerPage)
                .queryParam("pageSize", pageSize)
                .queryParam("fromDate", fromDate)
                .queryParam("toDate", toDate)
                .queryParam("searchKeyword", searchKeyword)
                .build()
                .encode();

        return uriComponents.toUriString();
    }
    //페이징[e]
}

