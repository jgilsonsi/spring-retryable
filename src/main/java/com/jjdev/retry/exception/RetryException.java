package com.jjdev.retry.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class RetryException extends RuntimeException {

    public RetryException(String message) {
        super(message);
    }
}