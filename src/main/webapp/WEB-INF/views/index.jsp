<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<!DOCTYPE html>
<html lang="ko">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <title>스테이션3</title>

    <meta name="_csrf" content="${_csrf.token}"/>
    <meta name="_csrf_header" content="${_csrf.headerName}"/>

    <script type="text/javascript" src="<c:out value='${PATH_JS}'/>/jquery-3.3.1.min.js"></script>
    <script type="text/javascript" src="<c:out value='${PATH_JS}'/>/jquery-ui.js"></script>
    <script type="text/javascript" src="<c:out value='${PATH_JS}'/>/lodash.js"></script>
    <script src="/castingn/common/commonFunction.js"></script>

    <link rel="stylesheet" href="<c:out value='${PATH_CSS}'/>/layout.css?ver=${DATE_yyyyMMddHHmmss}">
    <link rel="stylesheet" href="<c:out value='${PATH_CSS}'/>/jquery-ui.css" type="text/css" />
</head>

<body>
    <div class="wrapper">
        <div id="container">
            <%@ include file="/WEB-INF/views/list.jsp" %>

            <%--<%@ include file="/WEB-INF/views/regist.jsp" %>

            <%@ include file="/WEB-INF/views/detail.jsp" %>--%>
        </div>
    </div>
</body>
</html>



