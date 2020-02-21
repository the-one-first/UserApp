package com.wirecard.userapp.errorhandler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.transaction.TransactionTimedOutException;
import org.springframework.transaction.UnexpectedRollbackException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.wirecard.userapp.enumerator.ErrorEnum;
import com.wirecard.userapp.enumerator.ResponseEnum;
import com.wirecard.userapp.response.error.CodeDescError;
import com.wirecard.userapp.response.error.ResponseError;

@ControllerAdvice
public class CustomGlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({ ConstraintViolationException.class })
    public ResponseEntity<Object> handleConstraintViolation(ConstraintViolationException ex, WebRequest request) {

        List<CodeDescError> details = new ArrayList<>();

        for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
            details.add(new CodeDescError(ErrorEnum.ERR_CONSTRAINT_VIOLATION.getCode(),
                    violation.getRootBeanClass().getName() + " " + violation.getPropertyPath() + ": "
                            + violation.getMessage()));
        }

        ResponseError responseError = new ResponseError(ResponseEnum.ERROR_STATUS.getCode(), details);

        return new ResponseEntity<>(responseError, new HttpHeaders(), HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler({ MethodArgumentTypeMismatchException.class })
    public ResponseEntity<Object> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex,
            WebRequest request) {

        ResponseError responseError = new ResponseError(ResponseEnum.ERROR_STATUS.getCode(), Collections
                .singletonList(new CodeDescError(ErrorEnum.ERR_METHOD_ARG_TYP_MISMATCH.getCode(), ex.getMessage())));

        return new ResponseEntity<>(responseError, new HttpHeaders(), HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler({ UnexpectedRollbackException.class })
    public ResponseEntity<Object> handleUnexpectedRollbackException(UnexpectedRollbackException ex,
            WebRequest request) {

        ResponseError responseError = new ResponseError(ResponseEnum.ERROR_STATUS.getCode(),
                Collections.singletonList(new CodeDescError(ErrorEnum.ERR_UNEXPECTED_ROLLBACK.getCode(),
                        ErrorEnum.ERR_UNEXPECTED_ROLLBACK.getDesc())));

        return new ResponseEntity<>(responseError, new HttpHeaders(), HttpStatus.NOT_FOUND);

    }

    @ExceptionHandler({ TransactionTimedOutException.class })
    public ResponseEntity<Object> handleTransactionTimedOutException(TransactionTimedOutException ex,
            WebRequest request) {

        ResponseError responseError = new ResponseError(ResponseEnum.ERROR_STATUS.getCode(), Collections.singletonList(
                new CodeDescError(ErrorEnum.ERR_TIMEDOUT_OUT.getCode(), ErrorEnum.ERR_TIMEDOUT_OUT.getDesc())));

        return new ResponseEntity<>(responseError, new HttpHeaders(), HttpStatus.NOT_FOUND);

    }

    @ExceptionHandler({ DataIntegrityViolationException.class })
    public ResponseEntity<Object> handleDataIntegrityViolationException(DataIntegrityViolationException ex,
            WebRequest request) {

        ResponseError responseError = new ResponseError(ResponseEnum.ERROR_STATUS.getCode(), Collections.singletonList(
                new CodeDescError(ErrorEnum.ERR_DATA_INTEGRATION.getCode(), ErrorEnum.ERR_DATA_INTEGRATION.getDesc())));

        return new ResponseEntity<>(responseError, new HttpHeaders(), HttpStatus.NOT_FOUND);

    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
            HttpHeaders headers, HttpStatus status, WebRequest request) {

        ResponseError responseError = new ResponseError(ResponseEnum.ERROR_STATUS.getCode(), Collections
                .singletonList(new CodeDescError(ErrorEnum.ERR_MSG_NOT_READABLE.getCode(), ex.getMessage())));

        return handleExceptionInternal(ex, responseError, headers, status, request);

    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
            HttpHeaders headers, HttpStatus status, WebRequest request) {

        List<CodeDescError> details = new ArrayList<>();

        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            details.add(new CodeDescError(ErrorEnum.ERR_FIELD.getCode(),
                    error.getField() + " " + error.getDefaultMessage()));
        }

        for (ObjectError error : ex.getBindingResult().getGlobalErrors()) {
            details.add(new CodeDescError(ErrorEnum.ERR_OBJECT.getCode(),
                    error.getObjectName() + " " + error.getDefaultMessage()));
        }

        ResponseError responseError = new ResponseError(ResponseEnum.ERROR_STATUS.getCode(), details);

        return handleExceptionInternal(ex, responseError, headers, status, request);

    }

}
