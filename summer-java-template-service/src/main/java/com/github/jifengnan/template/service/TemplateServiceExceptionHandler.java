package com.github.jifengnan.template.service;

import com.github.jifengnan.template.api.exception.TopLevelBizException;
import com.github.jifengnan.template.api.model.TemplateHttpResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.async.AsyncRequestTimeoutException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * An unified exception handler on controller level.<p/>
 *
 * @author jifengnan 2018-12-02
 */
@Slf4j
@ControllerAdvice
public class TemplateServiceExceptionHandler {

    /**
     * Handle the timeout exception
     *
     * @param e       the exception
     * @param request current HTTP request
     * @return a standard HTTP result
     */
    @ResponseBody
    @ExceptionHandler(AsyncRequestTimeoutException.class)
    public TemplateHttpResult handleTimeoutException(Exception e, HttpServletRequest request) {
        log.error(e.getMessage(), e);
        return TemplateHttpResult.createForFailure(HttpStatus.SERVICE_UNAVAILABLE.value(),
                "Time out. error message: " + e.getMessage(),
                request.getServletPath());
    }

    /**
     * Handle the exceptions which can be processed by the users.<p/>
     * <p>
     * For example:<br/>
     * Case 1, users forgot to enter a required parameter.<br/>
     * Case 2, the balance is not enough.
     *
     * @param e       the exception
     * @param request current HTTP request
     * @return a standard HTTP result
     */
    @ResponseBody
    @ExceptionHandler({TopLevelBizException.class,
            HttpMessageConversionException.class,
            ServletException.class,
            TypeMismatchException.class,
            MethodArgumentNotValidException.class,
            BindException.class,
            IllegalArgumentException.class}
    )
    public TemplateHttpResult handleBizException(Exception e, HttpServletRequest request) {
        log.info(e.getMessage(), e);
        HttpStatus status = EXCEPTION_CODE_MAPPING.get(e.getClass());
        if (status == null) {
            status = HttpStatus.BAD_REQUEST;
        }
        return TemplateHttpResult.createForFailure(status.value(), e.getMessage(), request.getServletPath());
    }

    /**
     * Handle exceptions which are not expected to happen during the runtime.<p/>
     * <p>
     * Generally, it means developers can do nothing when the exceptions happened because<br/>
     * 1. There's a bug, developers have to provide a new release to fix it.<br/>
     * 2. There's a hardware issue
     *
     * @param e       the exception
     * @param request current HTTP request
     * @return a standard HTTP result
     */
    @ResponseBody
    @ExceptionHandler(Exception.class)
    public TemplateHttpResult handleUnexpectedException(Exception e, HttpServletRequest request) {
        log.error(e.getMessage(), e);
        return TemplateHttpResult.createForFailure(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Sorry, an error occurred, please retry or contact the customer service. error message: " + e.getMessage(),
                request.getServletPath());
    }

    private final Map<Class, HttpStatus> EXCEPTION_CODE_MAPPING;

    public TemplateServiceExceptionHandler() {
        EXCEPTION_CODE_MAPPING = new HashMap<>();
        EXCEPTION_CODE_MAPPING.put(HttpRequestMethodNotSupportedException.class, HttpStatus.METHOD_NOT_ALLOWED);
        EXCEPTION_CODE_MAPPING.put(HttpMediaTypeNotSupportedException.class, HttpStatus.UNSUPPORTED_MEDIA_TYPE);
        EXCEPTION_CODE_MAPPING.put(HttpMediaTypeNotAcceptableException.class, HttpStatus.NOT_ACCEPTABLE);
        EXCEPTION_CODE_MAPPING.put(MissingPathVariableException.class, HttpStatus.INTERNAL_SERVER_ERROR);
        EXCEPTION_CODE_MAPPING.put(MissingServletRequestParameterException.class, HttpStatus.BAD_REQUEST);
        EXCEPTION_CODE_MAPPING.put(ServletRequestBindingException.class, HttpStatus.BAD_REQUEST);
        EXCEPTION_CODE_MAPPING.put(ConversionNotSupportedException.class, HttpStatus.SERVICE_UNAVAILABLE);
        EXCEPTION_CODE_MAPPING.put(TypeMismatchException.class, HttpStatus.BAD_REQUEST);
        EXCEPTION_CODE_MAPPING.put(HttpMessageNotReadableException.class, HttpStatus.BAD_REQUEST);
        EXCEPTION_CODE_MAPPING.put(HttpMessageNotWritableException.class, HttpStatus.SERVICE_UNAVAILABLE);
        EXCEPTION_CODE_MAPPING.put(MethodArgumentNotValidException.class, HttpStatus.BAD_REQUEST);
        EXCEPTION_CODE_MAPPING.put(MissingServletRequestPartException.class, HttpStatus.BAD_REQUEST);
        EXCEPTION_CODE_MAPPING.put(BindException.class, HttpStatus.BAD_REQUEST);
        EXCEPTION_CODE_MAPPING.put(NoHandlerFoundException.class, HttpStatus.NOT_FOUND);
    }
}
