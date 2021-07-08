package mage.utils;

import com.google.common.collect.ImmutableList;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ValidationResult {
    private final List<String> validationMessages;

    private ValidationResult(final List<String> validationMessages) {
        this.validationMessages = ImmutableList.copyOf(validationMessages);
    }

    public List<String> getValidationMessages() {
        return ImmutableList.copyOf(validationMessages);
    }

    public boolean isValid() {
        return validationMessages.isEmpty();
    }

    public ValidationResult combine(ValidationResult other) {
        return new ValidationResult(Stream.concat(validationMessages.stream(), other.validationMessages.stream())
                .collect(Collectors.toList()));
    }

    public static ValidationResult ok() {
        return new ValidationResult(ImmutableList.of());
    }

    public static ValidationResult invalid(String message, String ... messages) {
        return new ValidationResult(Stream.concat(Stream.of(message), Stream.of(messages)).collect(Collectors.toList()));
    }
}
