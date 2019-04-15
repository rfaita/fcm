package com.product.fcm.test.component;

import com.product.fcm.server.component.FcmMessageValidator;
import com.product.fcm.server.model.FcmTokenMessage;
import com.product.fcm.server.model.FcmTopicMessage;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.ConstraintViolationException;
import javax.validation.Validation;
import javax.validation.Validator;

import static com.product.fcm.test.helper.FcmTokenMessageBuilder.*;
import static com.product.fcm.test.helper.FcmTopicMessageBuilder.*;

@RunWith(SpringRunner.class)
public class FcmMessageValidatorTest {

    @TestConfiguration
    static class FcmMessageValidatorTestContextConfiguration {


        @Bean
        public Validator localValidatorFactoryBean() {
            return Validation.buildDefaultValidatorFactory().getValidator();
        }
    }

    @SpyBean
    private FcmMessageValidator validator;

    @Rule
    public ExpectedException exceptionExpect = ExpectedException.none();

    @Test
    public void validate_fcm_token_message_with_success() {

        FcmTokenMessage data = createBasicFcmTokenMessage("123");

        validator.validate(data);

    }

    @Test
    public void validate_fcm_token_message_without_body() {

        FcmTokenMessage data = createFailFcmTokenMessageWithoutBody();

        exceptionExpect.expect(ConstraintViolationException.class);

        validator.validate(data);

    }

    @Test
    public void validate_fcm_token_message_without_title() {

        FcmTokenMessage data = createFailFcmTokenMessageWithoutTitle();

        exceptionExpect.expect(ConstraintViolationException.class);

        validator.validate(data);

    }

    @Test
    public void validate_fcm_token_message_without_token() {

        FcmTokenMessage data = createFailFcmTokenMessageWithoutToken();

        exceptionExpect.expect(ConstraintViolationException.class);

        validator.validate(data);

    }

    @Test
    public void validate_fcm_token_message_without_organization() {

        FcmTokenMessage data = createFailFcmTokenMessageWithoutOrganization();

        exceptionExpect.expect(ConstraintViolationException.class);

        validator.validate(data);

    }


    @Test
    public void validate_fcm_topic_message_with_success() {

        FcmTopicMessage data = createBasicFcmTopicMessage("123");

        validator.validate(data);

    }


    @Test
    public void validate_fcm_topic_message_without_body() {

        FcmTopicMessage data = createFailFcmTopicMessageWithoutBody();

        exceptionExpect.expect(ConstraintViolationException.class);

        validator.validate(data);

    }

    @Test
    public void validate_fcm_topic_message_without_title() {

        FcmTopicMessage data = createFailFcmTopicMessageWithoutTitle();

        exceptionExpect.expect(ConstraintViolationException.class);

        validator.validate(data);

    }

    @Test
    public void validate_fcm_topic_message_without_topic() {

        FcmTopicMessage data = createFailFcmTopicMessageWithoutTopic();

        exceptionExpect.expect(ConstraintViolationException.class);

        validator.validate(data);

    }

    @Test
    public void validate_fcm_topic_message_without_organization() {

        FcmTopicMessage data = createFailFcmTopicMessageWithoutOrganization();

        exceptionExpect.expect(ConstraintViolationException.class);

        validator.validate(data);

    }
}
