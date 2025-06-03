package com.finances.exception;

import com.finances.dto.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NonExistentEntityException.class)
    public ResponseEntity<ApiResponse> handleNonExistentEntity(NonExistentEntityException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse(e.getMessage(), false));
    }

    @ExceptionHandler(AttributeAlreadyExistsException.class)
    public ResponseEntity<ApiResponse> handleAttributeAlreadyExistsException(Exception e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse(e.getMessage(), false));
    }
        @ExceptionHandler(ShortAttributeException.class)
    public ResponseEntity<ApiResponse> handleShortAttributeException(Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse(e.getMessage(), false));
    }
        @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse> handleGeneralException(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("Something went wrong", false));
    }

}
