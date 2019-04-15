package com.product.fcm.client.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.product.fcm.client.shared.exception.CustomHystrixBadRequestException;
import com.product.fcm.shared.dto.Envelope;
import feign.Response;
import feign.codec.ErrorDecoder;
import org.springframework.stereotype.Component;

import javax.validation.ValidationException;
import java.io.IOException;

@Component
public class CustomErrorDecoder implements ErrorDecoder {

    private final ErrorDecoder delegate = new Default();

    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public Exception decode(String methodKey, Response response) {

        Envelope<?> data;
        try {
            data = mapper.readValue(response.body().asInputStream(), Envelope.class);
        } catch (IOException e) {
            throw new ValidationException("Não foi possível decodificar o erro.", e);
        }

        if (!data.getErrors().isEmpty()) {
            return new CustomHystrixBadRequestException(data.getErrors());
        }

        return delegate.decode(methodKey, response);
    }
}