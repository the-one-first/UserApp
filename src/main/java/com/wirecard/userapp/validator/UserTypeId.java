package com.wirecard.userapp.validator;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.wirecard.userapp.validator.impl.UserTypeIdValidator;

@Target({ FIELD })
@Retention(RUNTIME)
@Constraint(validatedBy = UserTypeIdValidator.class)
public @interface UserTypeId {

    String message() default "is not allowed.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
