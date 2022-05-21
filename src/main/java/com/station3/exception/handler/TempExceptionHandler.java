package com.station3.exception.handler;

/*
import com.lotteon.focommon.v1.config.FOMessageException;
import com.lotteon.focommon.v1.config.advice.FORestResponseAdvice;
import com.lotteon.focommon.v1.model.CommonResponseModel;
import com.lotteon.product.base.biz.log.entity.PdErrLogEntity;
import com.lotteon.product.base.biz.log.service.PdLogService;
import com.lotteon.product.base.redis.RedisProductManager;
import com.lotteon.product.common.util.DateUtil;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
*/
/*@Slf4j
@Order(0)
@RestControllerAdvice(annotations = RestController.class)*/
public class TempExceptionHandler /*extends FORestResponseAdvice*/ {

    /*@Autowired
    private PdLogService pdLogService;

    @Autowired
    private RedisProductManager<String> redisProductManager;

    @ExceptionHandler({
            ProductNotHandledStoreException.class})
    @ResponseStatus(HttpStatus.OK)
    public CommonResponseModel<Object> badRequestSuccessMessageException(WebRequest request, Throwable t) {
        RequestInfo requestInfo = getRequestInfo(request);
        log.warn(String.format("badRequestSuccessException Path : %s, mallno : %s", requestInfo.getClientInfo(), requestInfo.getMallCd()), t);
        CommonResponseModel<Object> rv = makeResponseModel(String.valueOf(HttpStatus.BAD_REQUEST.value()),
                t.getMessage(), new String[]{t.getMessage()}, 0);

        saveErrorLog(rv, t, requestInfo.getClientInfo());
        return rv;
    }

    @ExceptionHandler({
            RequestParamNotFoundException.class})
    @ResponseStatus(HttpStatus.OK)
    public CommonResponseModel<Object> badRequestSuccessException(WebRequest request, Throwable t) {
        RequestInfo requestInfo = getRequestInfo(request);
        log.warn(String.format("badRequestSuccessException Path : %s, mallno : %s", requestInfo.getClientInfo(), requestInfo.getMallCd()), t);
        CommonResponseModel<Object> rv = makeResponseModel(String.valueOf(HttpStatus.BAD_REQUEST.value()),
                "상품정보가 없습니다.", new String[]{t.getMessage()}, 0);

        saveErrorLog(rv, t, requestInfo.getClientInfo());
        return rv;
    }

    @ExceptionHandler({
            RequestValidationException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public CommonResponseModel<Object> badRequestException(WebRequest request, Throwable t) {
        RequestInfo requestInfo = getRequestInfo(request);
        log.error(String.format("badRequestException Path : %s, mallno : %s", requestInfo.getClientInfo(), requestInfo.getMallCd()), t);
        CommonResponseModel<Object> rv = makeResponseModel(String.valueOf(HttpStatus.BAD_REQUEST.value()),
                FOMessageException.VALIDATION_FAIL_MSG, new String[]{t.getMessage()}, 0);

        saveErrorLog(rv, t, requestInfo.getClientInfo());
        return rv;
    }

    @ExceptionHandler({
            IllegalArgumentException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public CommonResponseModel<Object> badDetailRequestException(WebRequest request, Throwable t) {
        RequestInfo requestInfo = getRequestInfo(request);
        log.error(String.format("badDetailRequestException Path : %s, mallno : %s", requestInfo.getClientInfo(), requestInfo.getMallCd()), t);
        CommonResponseModel<Object> rv = makeResponseModel(String.valueOf(HttpStatus.BAD_REQUEST.value()),
                "상품정보가 없습니다.", new String[]{t.getMessage()}, 0);

        saveErrorLog(rv, t, requestInfo.getClientInfo());
        return rv;
    }

    @ExceptionHandler({
            ProductBusinessException.class,
            IndexOutOfBoundsException.class
    })
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public CommonResponseModel<Object> productBusinessException(WebRequest request, Throwable t) {
        RequestInfo requestInfo = getRequestInfo(request);
        log.error(String.format("productBusinessException Path : %s, mallno : %s", requestInfo.getClientInfo(), requestInfo.getMallCd()), t);
        CommonResponseModel<Object> rv = makeResponseModel(FOMessageException.ERROR_RETURN_CODE,
                t.getMessage(), null, 0);

        saveErrorLog(rv, t, requestInfo.getClientInfo());
        return rv;
    }

    @ExceptionHandler({Exception.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public CommonResponseModel<Object> handleUncachedException(WebRequest request, Throwable t) {
        RequestInfo requestInfo = getRequestInfo(request);
        log.error(String.format("handleUncachedException Path : %s, mallno : %s", requestInfo.getClientInfo(), requestInfo.getMallCd()), t);
        CommonResponseModel<Object> rv = makeResponseModel(FOMessageException.ERROR_RETURN_CODE,
                t.getMessage(), null, null);

        saveErrorLog(rv, t, requestInfo.getClientInfo());
        return rv;
    }

    private RequestInfo getRequestInfo(WebRequest request) {
        return new RequestInfo(request.getDescription (true), request.getParameter("mall_no"));
    }

    private CommonResponseModel<Object> makeResponseModel(String returnCode, String message, String[] subMessages, Integer dataCount) {
        CommonResponseModel<Object> rv = new CommonResponseModel();
        rv.setReturnCode(returnCode);
        rv.setMessage(message);
        rv.setSubMessages(subMessages);
        rv.setDataCount(dataCount);

        return rv;
    }

    private void saveErrorLog(CommonResponseModel<Object> rv, Throwable t, String clientInfo) {
        try {
            if(searchSwitch()){
                String objNo = StringUtils.isBlank(rv.getReturnCode()) ? "" : rv.getReturnCode();
                pdLogService.insertPdErrorLog(new PdErrLogEntity(objNo, "bff.product", clientInfo, t.getMessage()));
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    public boolean searchSwitch() {
        return StringUtils.equals(StringUtils.defaultString(redisProductManager.get("pd:bff:log_switch"),"N"), "Y");
    }

    @Getter
    public class RequestInfo {
        private String clientInfo;
        private String mallCd;

        RequestInfo() {

        }

        RequestInfo(String clientInfo, String mallCd) {
            this.clientInfo = clientInfo;
            this.mallCd = mallCd;
        }
    }*/
}
