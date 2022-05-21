<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<div class="basket-process-wrap">
    <div class="basket-tt m-hide"><span class="ico-pcs"></span>검색 <span class="num">(20)</span></div>

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

    <div class="common-tit <div class="basket-wrap2">
    <div class="basket-process-wrap">
        <div class="basket-tt m-hide ty2">1:1 문의작성</div>
        <div class="my-inquiry-area">
            <ul class="inquiry-list item1">
                <li>
                    <strong class="fst">문의종류</strong>
                    <div>
                        <ul class="rd-list">
                            <li>
                                <label class="rdbox">
                                    <input type="radio" name="radio" checked>
                                    <span>회원계정관련</span>
                                </label>
                            </li>
                            <li>
                                <label class="rdbox">
                                    <input type="radio" name="radio">
                                    <span>결제 관련</span>
                                </label>
                            </li>
                            <li>
                                <label class="rdbox">
                                    <input type="radio" name="radio">
                                    <span>구매/배송/환불/반품</span>
                                </label>
                            </li>
                            <li>
                                <label class="rdbox">
                                    <input type="radio" name="radio">
                                    <span>기타</span>
                                </label>
                            </li>
                        </ul>
                    </div>
                </li>
            </ul>
            <ul class="inquiry-list item3">
                <li>
                    <strong>제목</strong>
                    <div class="ty-row">
                        <input type="text" class="ipt-1" placeholder="제목을 입력해주세요.">
                    </div>
                </li>
                <li>
                    <strong>문의내용</strong>
                    <div class="ty-row">
                        <textarea name="inqCont" id="inqCont" cols="30" rows="10" placeholder="문의 내용을 입력해주세요." class="txta-1"></textarea>
                    </div>
                </li>
            </ul>
        </div>

        <div class="agr-area">
            <label class="rdbox">
                <input type="checkbox" name="checkbox">
                <span>개인정보보호정책에 동의합니다.</span>
            </label>
        </div>

        <div class="delivery-btn-area">
            <a href="#" class="btn-comp"><span>등록</span></a>
        </div>
    </div>
</div>