package com.atlas.bank.transaction.validation;

import jakarta.validation.Constraint;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = DifferentAccountsValidator.class)
public @interface DifferentAccounts {
  String message() default "Same accounts are not allowed";

  Class<?>[] groups() default {};

  Class<? extends jakarta.validation.Payload>[] payload() default {};
}
