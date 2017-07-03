package com.pragmatists.helpers;

import com.pragmatists.dto.OrderResult;

@FunctionalInterface
public interface ExceptionHandlerHelper {
    OrderResult handleException() throws IllegalArgumentException, IllegalStateException;
}
