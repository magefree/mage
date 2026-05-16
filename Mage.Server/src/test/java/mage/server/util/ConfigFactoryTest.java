package mage.server.util;

import mage.players.PlayerType;
import mage.server.util.config.Config;
import mage.server.util.config.Plugin;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.tuple;

public class ConfigFactoryTest {

    @Test
    @DisplayName("should unmarshal configuration from file")
    void loadConfig() {
        final Config config = ConfigFactory.loadFromFile("config/config.xml");

        assertThat(config.getServer().getServerName()).isEqualTo("mage-server");
        assertThat(config.getServer().getPort()).isEqualTo(17171);
        assertThat(config.getPlayerTypes().getPlayerType())
                .extracting(Plugin::getName, Plugin::getClassName)
                .contains(tuple(PlayerType.COMPUTER_MAD.toString(), "mage.player.ai.ComputerPlayerControllableProxy"))
                .contains(tuple(PlayerType.COMPUTER_MAD_STRATEGIC.toString(), "mage.player.ai.ComputerPlayerMadStrategic"))
                .contains(tuple(PlayerType.COMPUTER_MONTE_CARLO.toString(), "mage.player.ai.ComputerPlayerMCTS"))
                .contains(tuple(PlayerType.COMPUTER_DRAFT_BOT.toString(), "mage.player.ai.ComputerDraftPlayer"));
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
