package com.product.fcm.shared.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Envelope<T> implements Serializable {

    private T data;
    private List<ErrorDTO> errors;

    public void setError(ErrorDTO error) {
        this.errors = new ArrayList<>();
        this.errors.add(error);
    }

}
