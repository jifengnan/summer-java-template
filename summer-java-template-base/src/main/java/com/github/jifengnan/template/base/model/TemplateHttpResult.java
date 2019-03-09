package com.github.jifengnan.template.base.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * An unified HTTP result.<p/>
 * <p>
 * The status code is suggested to be the same as the HTTP's.
 *
 * @author jifengnan 2018-12-01
 */
@Getter
public class TemplateHttpResult {
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private String timestamp;
    private int status;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String error;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Object data;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String path;

    protected TemplateHttpResult(int status, String error, Object data, String path) {
        this.status = status;
        this.error = error;
        this.data = data;
        this.path = path;
        timestamp = DATE_TIME_FORMATTER.format(LocalDateTime.now());
    }

    /**
     * Sometimes, you just want to tell the client all are fine.
     *
     * @param status a success status
     * @return a HTTP result object
     */
    public static TemplateHttpResult createForSuccess(int status) {
        return new TemplateHttpResult(status, null, null, null);
    }

    /**
     * Generally, a result is expected if the request be processed successfully.<br/>
     * <p>
     * For example, the <code>data</code> will be the order information for a request to query an order.
     *
     * @param status a success status
     * @param data   the result
     * @return a HTTP result object
     */
    public static TemplateHttpResult createForSuccess(int status, Object data) {
        return new TemplateHttpResult(status, null, data, null);
    }

    /**
     * Generally, the error message should be provided if a request failed.
     *
     * @param status a failure status
     * @param error  the error message
     * @param path   current request path
     * @return a HTTP result object
     */
    public static TemplateHttpResult createForFailure(int status, String error, String path) {
        return new TemplateHttpResult(status, error, null, path);
    }
}
