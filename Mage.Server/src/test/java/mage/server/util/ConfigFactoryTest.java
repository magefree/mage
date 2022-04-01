package mage.server.util;

import mage.server.util.config.Config;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

public class ConfigFactoryTest {

    private static Config defaultConfig;
    private static Config multihomeConfig;
    private final Builders.ServerBuilder commonServerBuilder = new Builders.ServerBuilder().with(s -> {
        s.serverAddress = "address";
        s.serverName = "name";
        s.port = 1;
        s.secondaryBindPort = 2;
        s.backlogSize = 3;
        s.numAcceptThreads = 4;
        s.maxPoolSize = 5;
        s.leasePeriod = 6;
        s.socketWriteTimeout = 7;
        s.maxGameThreads = 8;
        s.maxSecondsIdle = 9;
        s.minUsernameLength = 10;
        s.maxUsernameLength = 11;
        s.invalidUsernamePattern = "pattern";
        s.minPasswordLength = 12;
        s.maxPasswordLength = 13;
        s.maxAiOpponents = "14";
        s.saveGameActivated = true;
        s.authenticationActivated = true;
        s.googleAccount = "account";
        s.mailgunApiKey = "apikey";
        s.mailgunDomain = "domain";
        s.mailSmtpHost = "smtphost";
        s.mailSmtpPort = "smtpport";
        s.mailUser = "user";
        s.mailPassword = "password";
        s.mailFromAddress = "fromaddress";
    });

    @BeforeAll
    static void setUpClass() {
        defaultConfig = ConfigFactory.loadFromFile(Paths.get("src", "test", "resources", "default_config.xml").toString());
        multihomeConfig = ConfigFactory.loadFromFile(Paths.get("src", "test", "resources", "multihome_config.xml").toString());
    }

    @Test
    @DisplayName("should unmarshal default configuration from file")
    void loadDefaultConfig() {
        assertThat(defaultConfig.getServer())
                .usingComparator(Comparators.serverComparator)
                .isEqualTo(commonServerBuilder.with(s -> {
                    s.home = Collections.emptyList();
                    s.multihome = false;
                }).build());
    }

    @Test
    @DisplayName("should unmarshal configuration with multihome")
    void loadMultihomeConfig() {
        assertThat(multihomeConfig.getServer())
                .usingComparator(Comparators.serverComparator)
                .isEqualTo(commonServerBuilder.with(s -> {
                    s.multihome = true;
                    s.home = Arrays.asList(new Builders.HomeBuilder().with(h -> {
                        h.internal = "internal_home_1";
                        h.external = "external_home_1";
                        h.port = 15;
                        h.externalPort = null;
                        h.secondaryPort = -1;
                    }).build(), new Builders.HomeBuilder().with(h -> {
                        h.internal = "internal_home_2";
                        h.external = "external_home_2";
                        h.port = 16;
                        h.externalPort = 17;
                        h.secondaryPort = 18;
                    }).build());
                }).build());
    }

    @Test
    @DisplayName("should fail if config is malformed")
    void failOnMalformed() {
        assertThatExceptionOfType(ConfigurationException.class)
                .isThrownBy(() -> ConfigFactory.loadFromFile(Paths.get("src", "test", "data", "config_error.xml").toString()));
    }

    @Test
    @DisplayName("should fail if file does not exist")
    void failOnNotFound() {
        assertThatExceptionOfType(ConfigurationException.class)
                .isThrownBy(() -> ConfigFactory.loadFromFile("does not exist"));
    }
}
