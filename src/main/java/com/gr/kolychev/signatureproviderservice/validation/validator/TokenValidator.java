package com.gr.kolychev.signatureproviderservice.validation.validator;

import com.gr.kolychev.signatureproviderservice.exception.InvalidTokenException;
import com.gr.kolychev.signatureproviderservice.validation.annotation.ValidToken;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@ConfigurationProperties(prefix = "security.authentication")
@Setter
public class TokenValidator implements ConstraintValidator<ValidToken, String> {

    private String validToken;

    @Override
    public boolean isValid(String token, ConstraintValidatorContext constraintValidatorContext) {
        if (StringUtils.hasText(token) && token.equals(validToken)) {
            return true;
        }
        throw new InvalidTokenException(constraintValidatorContext.getDefaultConstraintMessageTemplate());
    }
}
