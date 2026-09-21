package com.atlas.bank.shared.exception;

import com.atlas.bank.account.exception.AccountNotFoundException;
import com.atlas.bank.transaction.exception.AccountNotActiveException;
import com.atlas.bank.transaction.exception.InsufficientFundsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
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

}
