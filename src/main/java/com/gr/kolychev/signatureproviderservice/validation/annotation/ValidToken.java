package com.gr.kolychev.signatureproviderservice.validation.annotation;

import com.gr.kolychev.signatureproviderservice.validation.validator.TokenValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = {TokenValidator.class})
@Target({ElementType.PARAMETER, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidToken {

    String message() default "Provided token is invalid.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
