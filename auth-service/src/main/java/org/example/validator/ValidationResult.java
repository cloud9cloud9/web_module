package org.example.validator;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ValidationResult {

    private final boolean isValid;
}
