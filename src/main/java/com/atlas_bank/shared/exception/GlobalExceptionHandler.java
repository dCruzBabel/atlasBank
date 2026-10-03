package com.atlas_bank.shared.exception;

import com.atlas_bank.account.exception.AccountNotFoundException;
import com.atlas_bank.transaction.exception.AccountNotActiveException;
import com.atlas_bank.transaction.exception.InsufficientFundsException;
import com.atlas_bank.transaction.service.exception.FraudCheckException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(AccountNotFoundException.class)
  public ProblemDetail handleAccountNotFoundException(AccountNotFoundException ex) {
    ProblemDetail problemDetail = ProblemDetail.forStatus(404);
    problemDetail.setTitle("Account Not Found");
    problemDetail.setDetail(ex.getMessage());
    return problemDetail;
  }

  @ExceptionHandler(InsufficientFundsException.class)
  public ProblemDetail handleInsufficientFundsException(InsufficientFundsException ex) {
    ProblemDetail problemDetail = ProblemDetail.forStatus(422);
    problemDetail.setTitle("Insufficient Funds");
    problemDetail.setDetail(ex.getMessage());
    return problemDetail;
  }

  @ExceptionHandler(AccountNotActiveException.class)
  public ProblemDetail handleAccountNotActiveException(AccountNotActiveException ex) {
    ProblemDetail problemDetail = ProblemDetail.forStatus(422);
    problemDetail.setTitle("Account Not Active");
    problemDetail.setDetail(ex.getMessage());
    return problemDetail;
  }

  @ExceptionHandler(Exception.class)
  public ProblemDetail handleGeneralException(Exception ex) {
    ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.INTERNAL_SERVER_ERROR);
    problemDetail.setTitle("Internal Server Error");
    return problemDetail;
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ProblemDetail handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
    ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
    problemDetail.setTitle("Validation Failed");

    List<String> errors = new ArrayList<>();
    ex.getBindingResult().getFieldErrors()
        .forEach(error -> errors.add(error.getField() + ": " + error.getDefaultMessage()));

    ex.getBindingResult().getGlobalErrors()
        .forEach(error -> errors.add(error.getObjectName() + ": " + error.getDefaultMessage()));

    problemDetail.setProperty("errors", errors);

    return problemDetail;
  }


  @ExceptionHandler(FraudCheckException.class)
  public ProblemDetail handleFraudCheckException(FraudCheckException ex) {
    ProblemDetail problemDetail = ProblemDetail.forStatus(422);
    problemDetail.setTitle("Fraud Check Failed");
    problemDetail.setDetail(ex.getMessage());
    return problemDetail;
  }

}
