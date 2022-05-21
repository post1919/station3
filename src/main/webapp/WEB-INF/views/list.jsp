<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<div class="basket-process-wrap">
    <div class="common-tit m-hide">검색</div>

    <div class="search-area">
        <div class="search-box">
            <button type="button">검색</button>
            <input type="text">
        </div>
    </div>

    <div class="period-search-box mb-1">
        <div class="period-bt-group">
            <button type="button">최근 1달</button>
            <button type="button" class="active">최근 3달</button>
            <button type="button">최근 6달</button>
            <button type="button">최근 1년</button>
        </div>

        <div class="ymd-group">
            <div class="ymd-area">
                <div class="ymd-box"><input type="text" class="ymd" readonly="" value="2019.11.18"> <button type="button" class="btn-cal">달력보기</button></div>
                <span class="dot">~</span>
                <div class="ymd-box"><input type="text" class="ymd" readonly="" value="2019.11.18"> <button type="button" class="btn-cal">달력보기</button></div>
            </div>

            <div class="period-search">
                <button type="button">조회 <i class="bt-search"></i></button>
            </div>
        </div>
    </div>

    <div class="paylist-tbl m-hide">
        <table>
            <caption class="hidden">1:1 문의테이블</caption>
            <colgroup>
                <col width="10%">
                <col >
                <col width="14%">
                <col width="11%">
                <col width="12%">
                <col width="10%">
            </colgroup>
            <thead>
            <tr class="m-hide">
                <th scope="col">등록일시</th>
                <th scope="col">판매자명</th>
                <th scope="col">상품</th>
                <th scope="col">제목</th>
                <th scope="col">주문상세</th>
                <th scope="col">상태</th>
            </tr>
            </thead>
            <tbody>
            <tr>
                <td>2022-01-26</td>
                <td>모던오피스</td>
                <td><span class="tt-elip">더블에이 복사용지 더블에이 복사용지</span></td>
                <td class="toggle">
                    <a href="#">
                        <i class="ico-new">new</i>
                        상품이 누락되어 배송되었습니다.
                    </a>
                </td>
                <td class="m-hide"><button class="paylist-tbl-bt btn-go">바로가기</button></td>
                <td class="stat-wait">답변대기</td>
            </tr>
            </tbody>
        </table>
    </div>

    <div class="paging mt2">
        <a href="#" class="page-prev">이전 페이지</a>
        <a href="#">1</a>
        <a href="#">2</a>
        <a href="#">3</a>
        <a href="#">4</a>
        <span class="current">5</span>
        <a href="#">6</a>
        <a href="#">7</a>
        <a href="#" class="page-more">. . .</a>
        <a href="#">2214</a>
        <a href="#" class="page-next">다음 페이지</a>
    </div>
</div>
