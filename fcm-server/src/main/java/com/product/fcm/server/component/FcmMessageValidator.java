package com.product.fcm.server.component;


import com.product.fcm.server.model.FcmMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import java.util.HashSet;
import java.util.Set;

@Component
public class FcmMessageValidator {

    private Validator validator;

    @Autowired
    public FcmMessageValidator(Validator validator) {
        this.validator = validator;
    }

    public void validate(FcmMessage smsMessage) {

        Set<ConstraintViolation<FcmMessage>> violations = validator.validate(smsMessage);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(new HashSet<>(violations));
        }

    }

}
