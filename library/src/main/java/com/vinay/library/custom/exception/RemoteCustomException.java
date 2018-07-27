package com.vinay.library.custom.exception;

public class RemoteCustomException extends RuntimeException {
    private int mCode;

    public RemoteCustomException(int code, String message) {
        super(message);
        mCode = code;
    }


    public int getCode() {
        return mCode;
    }

    public void setCode(int code) {
        this.mCode = code;
    }
}
