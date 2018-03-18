package com.juraszek.algorithm.utils.xmcda;

public class XMCDAMessageParser {

    public static String getMessage(String message, Throwable throwable) {
        return message + getMessage(throwable);
    }

    private static String getMessage(Throwable throwable) {
        if (throwable.getMessage() != null)
            return throwable.getMessage();
        if (throwable.getCause() != null && throwable.getCause().getMessage() != null)
            return throwable.getCause().getMessage();
        return "unknown";
    }

}
