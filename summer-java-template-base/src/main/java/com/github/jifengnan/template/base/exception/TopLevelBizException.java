package com.github.jifengnan.template.base.exception;

/**
 * This class represents a top level business exception for a project.<p/>
 * <p>
 * For example:<br/>
 * Assume a project for payment, this class can be named to PaymentException.
 * @author jifengnan 2018-12-01
 */
public class TopLevelBizException extends Exception {
    public TopLevelBizException() {
        super();
    }

    public TopLevelBizException(String message) {
        super(message);
    }

    public TopLevelBizException(String message, Throwable cause) {
        super(message, cause);
    }

    public TopLevelBizException(Throwable cause) {
        super(cause);
    }
}
