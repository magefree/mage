package mage.server.util;

import com.google.common.collect.ImmutableList;
import mage.server.util.config.Config;
import mage.utils.ValidationResult;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThat;

public class ConfigValidatorTest {

    private static Config defaultConfig;
    private static Config multihomeConfig;

    @BeforeAll
    static void setUpClass() {
        defaultConfig = ConfigFactory.loadFromFile(Paths.get("src", "test", "resources", "default_config.xml").toString());
        multihomeConfig = ConfigFactory.loadFromFile(Paths.get("src", "test", "resources", "multihome_config.xml").toString());
    }

    @Test
    @DisplayName("default config should be valid")
    void defaultIsValid() {
        assertThat(ConfigValidator.validate(defaultConfig).isValid()).isTrue();
    }

    @Test
    @DisplayName("multiple home config should be valid")
    void multihomeIsValid() {
        assertThat(ConfigValidator.validate(multihomeConfig).isValid()).isTrue();
    }

    @Test
    @DisplayName("multihome with no homes should be invalid")
    void multihomeNoHomesInvalid() {
        final ValidationResult result = ConfigValidator.validate(baseBuilder().with(c -> {
            c.multihome = true;
            c.homes = ImmutableList.of();
        }).build());

        assertThat(result.isValid()).isFalse();
        assertThat(result.getValidationMessages()).containsExactly("Multihome enabled config should contain at least one home");
    }


    private Builders.ConfigBuilder baseBuilder() {
        return new Builders.ConfigBuilder();
    }

    @Test
    @DisplayName("no multihome with homes should be invalid")
    void noMultihomeWithHomesInvalid() {
        final ValidationResult result = ConfigValidator.validate(baseBuilder().with(c -> {
            c.multihome = false;
            c.homes = ImmutableList.of(homeBuilder().build());
        }).build());

        assertThat(result.isValid()).isFalse();
        assertThat(result.getValidationMessages()).containsExactly("Non-multihome config should not contain any home");
    }

    @Test
    @DisplayName("multihome with one home should be valid")
    void multihomWithOneHomeValid() {
        final ValidationResult result = ConfigValidator.validate(baseBuilder().with(c -> {
            c.multihome = true;
            c.homes = ImmutableList.of(homeBuilder().build());
        }).build());

        assertThat(result.isValid()).isTrue();
    }

    private Builders.HomeBuilder homeBuilder() {
        return new Builders.HomeBuilder().with(h -> {
            h.external = RandomStringUtils.randomAlphanumeric(15);
            h.internal = RandomStringUtils.randomAlphanumeric(15);
            h.port = RandomUtils.nextInt(1000, 65000);
            h.externalPort = RandomUtils.nextInt(1000, 65000);
            h.secondaryPort = RandomUtils.nextInt(1000, 65000);
        });
    }

}
