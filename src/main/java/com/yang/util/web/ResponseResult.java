package com.yang.util.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * ajax返回结果
 */
public class ResponseResult {
    private static Logger logger = LoggerFactory.getLogger(ResponseResult.class);

    /**
     * 是否执行成功
     */
    private boolean isSuccess = true;
    /**
     * 结果
     */
    private Object data;
    /**
     * 返回给前端的信息（例如错误信息）
     */
    private String msg = "";

    public ResponseResult() {
    }

    public ResponseResult(Object data) {
        this.data = data;
    }

    public ResponseResult(Object data, String errormessage) {
        this.isSuccess = false;
        this.data = data;
        this.msg = errormessage;
    }

    /**
     * 封装ajax返回结果
     *
     * @param request
     * @param data
     * @return
     */
    public static Object getReturnResult(HttpServletRequest request, Object data) {
        ResponseResult responseResult = new ResponseResult(data);

        //处理jsonp请求
        return handleJsonpReturnResult(request, responseResult);
    }

    /**
     * 封装ajax返回结果（出现异常时）
     *
     * @param request
     * @param e
     * @return
     */
    public static Object getReturnResultError(HttpServletRequest request, Exception e) {
        //记录日志
        logger.error(e.getMessage(), e);

        ResponseResult responseResult = new ResponseResult("");
        //把错误信息输出到前端
        responseResult.setMsg(e.getMessage());
        responseResult.setSuccess(false);

        //处理jsonp请求
        return handleJsonpReturnResult(request, responseResult);
    }

    /**
     * 封装ajax返回结果（出现异常时）
     *
     * @param request
     * @param errorMsg
     * @return
     */
    public static Object getReturnResultError(HttpServletRequest request, String errorMsg) {
        //记录日志
        logger.error(errorMsg);

        ResponseResult responseResult = new ResponseResult("");
        //把错误信息输出到前端
        responseResult.setMsg(errorMsg);
        responseResult.setSuccess(false);

        //处理jsonp请求
        return handleJsonpReturnResult(request, responseResult);
    }

    /**
     * 处理jsonp请求
     *
     * @param request
     * @param responseResult
     * @return
     */
    private static Object handleJsonpReturnResult(HttpServletRequest request, ResponseResult responseResult) {
        String callback = "";
        if (request.getParameter("callback") != null)
            callback = request.getParameter("callback").toString();

        if (callback != null && !StringUtils.isEmpty(callback)) {
            MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(responseResult);
            mappingJacksonValue.setJsonpFunction(callback);
            return mappingJacksonValue;

        }
        return responseResult;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
