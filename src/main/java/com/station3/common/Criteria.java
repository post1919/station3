package com.station3.common;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
/*
* 해당클래스 CommonDto로 내용 옮김
* 사용안함 20211228 신동아
 */
@Getter
@Setter
public class Criteria {

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

    public Criteria() {
        this.currentPageNo = 1;
        this.recordsPerPage = 10;
        this.pageSize = 10;
    }

    public int getStartPage() {
        return (currentPageNo - 1) * recordsPerPage;
    }

    public String makeQueryString(int pageNo) {

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

}

