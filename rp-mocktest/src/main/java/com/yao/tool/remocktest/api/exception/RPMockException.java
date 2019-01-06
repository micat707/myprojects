package com.yao.tool.remocktest.api.exception;

public class RPMockException extends RuntimeException {

    /**
     * serialVersionUID:
     *
     */
    private static final long serialVersionUID = 2852312032256214462L;

    public RPMockException() {
        super();
    }

    public RPMockException(String msg) {

        super(msg);
    }

    public RPMockException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public RPMockException(Throwable cause) {
        super(cause);
    }

}