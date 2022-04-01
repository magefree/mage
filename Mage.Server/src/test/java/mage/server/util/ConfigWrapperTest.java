package mage.server.util;

import mage.server.util.config.GamePlugin;
import mage.server.util.config.Plugin;
import mage.server.util.config.Server;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static mage.server.util.Comparators.gamePluginComparator;
import static mage.server.util.Comparators.pluginComparator;
import static org.assertj.core.api.Assertions.assertThat;

public class ConfigWrapperTest {

    private Builders.ConfigBuilder baseConfigBuilder() {
        return new Builders.ConfigBuilder();
    }

    private final String expectedString = RandomStringUtils.randomAlphanumeric(15);
    private final int expectedPositiveInt = RandomUtils.nextInt(0, Integer.MAX_VALUE);


    @TestFactory
    @DisplayName("should return from server")
    Stream<DynamicTest> assignmentFromServer() {
        return Stream.of(
                testString("server address", c -> c.serverAddress = expectedString, ConfigWrapper::getServerAddress),
                testString("server name", c -> c.serverName = expectedString, ConfigWrapper::getServerName),
                testInt("port", c -> c.port = expectedPositiveInt, ConfigWrapper::getPort),
                testInt("secondary bind port", c -> c.secondaryBindPort = expectedPositiveInt, ConfigWrapper::getSecondaryBindPort),
                testInt("lease period", c -> c.leasePeriod = expectedPositiveInt, ConfigWrapper::getLeasePeriod),
                testInt("socket write timeout", c -> c.socketWriteTimeout = expectedPositiveInt, ConfigWrapper::getSocketWriteTimeout),
                testInt("max pool size", c -> c.maxPoolSize = expectedPositiveInt, ConfigWrapper::getMaxPoolSize),
                testInt("number of accept threads", c -> c.numAcceptThreads = expectedPositiveInt, ConfigWrapper::getNumAcceptThreads),
                testInt("backlog size", c -> c.backlogSize = expectedPositiveInt, ConfigWrapper::getBacklogSize),
                testInt("max game threads", c -> c.maxGameThreads = expectedPositiveInt, ConfigWrapper::getMaxGameThreads),
                testInt("max seconds idle", c -> c.maxSecondsIdle = expectedPositiveInt, ConfigWrapper::getMaxSecondsIdle),
                testInt("min username length", c -> c.minUsernameLength = expectedPositiveInt, ConfigWrapper::getMinUserNameLength),
                testInt("max username length", c -> c.maxUsernameLength = expectedPositiveInt, ConfigWrapper::getMaxUserNameLength),
                testString("invalid username pattern", c -> c.invalidUsernamePattern = expectedString, ConfigWrapper::getInvalidUserNamePattern),
                testInt("min password length", c -> c.minPasswordLength = expectedPositiveInt, ConfigWrapper::getMinPasswordLength),
                testInt("max password length", c -> c.maxPasswordLength = expectedPositiveInt, ConfigWrapper::getMaxPasswordLength),
                testString("max AI opponents", c -> c.maxAiOpponents = expectedString, ConfigWrapper::getMaxAiOpponents),
                testTrue("save game activated", c -> c.saveGameActivated = true, ConfigWrapper::isSaveGameActivated),
                testTrue("authentication activated", c -> c.authenticationActivated = true, ConfigWrapper::isAuthenticationActivated),
                testTrue("multihome", c -> c.multihome = true, ConfigWrapper::isMultiHome),
                testString("google account", c -> c.googleAccount = expectedString, ConfigWrapper::getGoogleAccount),
                testString("mailgun api key", c -> c.mailgunApiKey = expectedString, ConfigWrapper::getMailgunApiKey),
                testString("mailgun domain", c -> c.mailgunDomain = expectedString, ConfigWrapper::getMailgunDomain),
                testString("mail smtp host", c -> c.mailSmtpHost = expectedString, ConfigWrapper::getMailSmtpHost),
                testString("mail smtp port", c -> c.mailSmtpPort = expectedString, ConfigWrapper::getMailSmtpPort),
                testString("mail from address", c -> c.mailFromAddress = expectedString, ConfigWrapper::getMailFromAddress),
                testString("mail user", c -> c.mailUser = expectedString, ConfigWrapper::getMailUser),
                testString("mail password", c -> c.mailPassword = expectedString, ConfigWrapper::getMailPassword)
        );
    }

    private DynamicTest testString(String description, Consumer<Builders.ConfigBuilder> builderSetter, Function<ConfigWrapper, Object> valueExtractor) {
        return testTemplate(description, builderSetter, valueExtractor, expectedString);
    }

    private DynamicTest testTemplate(String description, Consumer<Builders.ConfigBuilder> builderSetter, Function<ConfigWrapper, Object> valueExtractor, Object expectedValue) {
        return DynamicTest.dynamicTest(description, () -> assertThat(valueExtractor.apply(makeTestee(baseConfigBuilder().with(builderSetter)))).isEqualTo(expectedValue));
    }

    private ConfigWrapper makeTestee(Builders.ConfigBuilder builder) {
        return new ConfigWrapper(builder.build());
    }

    private DynamicTest testInt(String description, Consumer<Builders.ConfigBuilder> builderSetter, Function<ConfigWrapper, Object> valueExtractor) {
        return testTemplate(description, builderSetter, valueExtractor, expectedPositiveInt);
    }

    private DynamicTest testTrue(String description, Consumer<Builders.ConfigBuilder> builderSetter, Function<ConfigWrapper, Object> valueExtractor) {
        return testTemplate(description, builderSetter, valueExtractor, true);
    }

    private final List<Plugin> randomPlugins = IntStream.range(0, RandomUtils.nextInt(1, 10))
            .mapToObj(i -> makePlugin(
                    RandomStringUtils.randomAlphanumeric(15),
                    RandomStringUtils.randomAlphanumeric(16),
                    RandomStringUtils.randomAlphanumeric(17))
            ).collect(Collectors.toList());
    private final List<GamePlugin> randomGamePlugins = IntStream.range(0, RandomUtils.nextInt(1, 10))
            .mapToObj(i -> makeGamePlugin(
                    RandomStringUtils.randomAlphanumeric(15),
                    RandomStringUtils.randomAlphanumeric(16),
                    RandomStringUtils.randomAlphanumeric(17),
                    RandomStringUtils.randomAlphanumeric(18))
            ).collect(Collectors.toList());

    private Plugin makePlugin(String name, String jar, String className) {
        return new Builders.PluginBuilder().with(p -> {
            p.name = name;
            p.jar = jar;
            p.className = className;
        }).build();
    }

    private GamePlugin makeGamePlugin(String name, String jar, String className, String typeName) {
        return new Builders.GamePluginBuilder().with(p -> {
            p.name = name;
            p.jar = jar;
            p.className = className;
            p.typeName = typeName;
        }).build();
    }

    @TestFactory
    @DisplayName("should extract")
    Stream<DynamicTest> pluginsExtraction() {
        return Stream.of(
                pluginTest("playerTypes from playerTypes", c -> c.playerTypes = randomPlugins, ConfigWrapper::getPlayerTypes),
                gamePluginTest("gameTypes from gameTypes", c -> c.gameTypes = randomGamePlugins, ConfigWrapper::getGameTypes),
                gamePluginTest("tournamentTypes from tournamentTypes", c -> c.tournamentTypes = randomGamePlugins, ConfigWrapper::getTournamentTypes),
                pluginTest("draftCubes from draftCubes", c -> c.draftCubes = randomPlugins, ConfigWrapper::getDraftCubes),
                pluginTest("deckTypes from deckTypes", c -> c.deckTypes = randomPlugins, ConfigWrapper::getDeckTypes)
        );
    }

    private DynamicTest pluginTest(String description,
                                   Consumer<Builders.ConfigBuilder> builderSetter,
                                   Function<ConfigWrapper, List<Plugin>> listExtractor) {
        return testTemplateForLists(description, builderSetter, listExtractor, randomPlugins, pluginComparator);
    }

    private DynamicTest gamePluginTest(String description,
                                       Consumer<Builders.ConfigBuilder> builderSetter,
                                       Function<ConfigWrapper, List<GamePlugin>> listExtractor) {
        return testTemplateForLists(description, builderSetter, listExtractor, randomGamePlugins, gamePluginComparator);
    }

    private <T> DynamicTest testTemplateForLists(String description,
                                                 Consumer<Builders.ConfigBuilder> builderSetter,
                                                 Function<ConfigWrapper, List<T>> listExtractor,
                                                 List<T> expectedValue,
                                                 Comparator<T> comparator) {
        return DynamicTest.dynamicTest(description, () ->
                assertThat(listExtractor.apply(makeTestee(baseConfigBuilder().with(builderSetter))))
                        .usingElementComparator(comparator)
                        .containsExactlyElementsOf(expectedValue)
        );
    }

    @Test
    @DisplayName("should extract secondary bind ports from homes")
    void secondaryBindPorts() {
        final int secondary = RandomUtils.nextInt(1000, 65000);
        assertThat(makeTestee(baseConfigBuilder().with(c ->
                c.homes = Arrays.asList(makeHomeWithSecondaryPort(-1), makeHomeWithSecondaryPort(secondary))
        )).secondaryPorts()).containsExactly(-1, secondary);
    }

    private Server.Home makeHomeWithSecondaryPort(int secondaryPort) {
        return new Builders.HomeBuilder().with(h -> h.secondaryPort = secondaryPort).build();
    }
}
