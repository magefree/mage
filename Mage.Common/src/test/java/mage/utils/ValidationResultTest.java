package mage.utils;

import com.google.common.collect.ImmutableList;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static mage.utils.ValidationResult.invalid;
import static mage.utils.ValidationResult.ok;
import static org.assertj.core.api.Assertions.assertThat;

public class ValidationResultTest {

    @Test
    @DisplayName("invalid should be invalid")
    void invalidIsInvalid() {
        assertThat(invalid(RandomStringUtils.randomAlphanumeric(15)).isValid()).isFalse();
    }

    @Test
    @DisplayName("ok should be valid")
    void okIsValid() {
        assertThat(ok().isValid()).isTrue();
    }

    @Test
    @DisplayName("invalid should contain all validation messages")
    void allMessagesInInvalid() {
        final String message1 = RandomStringUtils.randomAlphanumeric(15),
                message2 = RandomStringUtils.randomAlphanumeric(16);

        assertThat(invalid(message1, message2).getValidationMessages()).containsExactly(message1, message2);
    }

    @Test
    @DisplayName("ok should contain no validation messages")
    void noMessagesInOk() {
        assertThat(ok().getValidationMessages()).isEmpty();
    }

    @Test
    @DisplayName("ok combined with ok should be ok")
    void okAndOkIsOk() {
        assertThat(ok().combine(ok()).isValid()).isTrue();
    }

    @Test
    @DisplayName("ok combined with invalid should be invalid")
    void okAndInvalidIsInvalid() {
        final String message1 = RandomStringUtils.randomAlphanumeric(15),
                message2 = RandomStringUtils.randomAlphanumeric(16),
                message3 = RandomStringUtils.randomAlphanumeric(16);
        assertThat(ok().combine(invalid(message2, message3)).getValidationMessages())
                .containsExactly(message2, message3);
        assertThat(invalid(message3, message1, message2).combine(ok()).getValidationMessages())
                .containsExactly(message3, message1, message2);
    }

    @Test
    @DisplayName("invalid and invalid should be invalid with all the messages")
    void invalidAndInvalidIsInvalid() {
        final String message1 = RandomStringUtils.randomAlphanumeric(15),
                message2 = RandomStringUtils.randomAlphanumeric(16),
                message3 = RandomStringUtils.randomAlphanumeric(16);
        assertThat(invalid(message3, message1, message2).combine(invalid(message2, message3)).getValidationMessages())
                .containsExactly(message3, message1, message2, message2, message3);
    }

    @Test
    @DisplayName("constructors should be immutable")
    void immutability() {
        final ValidationResult ok = ok(),
                invalid1 = invalid(RandomStringUtils.randomAlphanumeric(15)),
                invalid2 = invalid(RandomStringUtils.randomAlphanumeric(15), RandomStringUtils.randomAlphanumeric(15));
        final List<String> okList = ImmutableList.copyOf(ok.getValidationMessages()),
                invalid1List = ImmutableList.copyOf(invalid1.getValidationMessages()),
                invalid2List = ImmutableList.copyOf(invalid2.getValidationMessages());

        ok.combine(invalid1);
        invalid1.combine(invalid2);
        invalid2.combine(invalid1);

        assertThat(ok.getValidationMessages()).containsExactlyElementsOf(okList);
        assertThat(invalid1.getValidationMessages()).containsExactlyElementsOf(invalid1List);
        assertThat(invalid2.getValidationMessages()).containsExactlyElementsOf(invalid2List);
    }
}
