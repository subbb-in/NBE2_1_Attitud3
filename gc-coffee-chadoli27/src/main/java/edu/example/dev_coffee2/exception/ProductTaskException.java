package edu.example.dev_coffee2.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ProductTaskException extends RuntimeException {
    private String message;
    private int code;
}
