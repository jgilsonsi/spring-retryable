package com.jjdev.retry.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class ContinueException extends Exception {

    public ContinueException(String message) {
        super(message);
    }
}