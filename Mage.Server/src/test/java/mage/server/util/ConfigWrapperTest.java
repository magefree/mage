package mage.server.util;

import mage.server.util.config.*;
import mage.utils.FluentBuilder;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;

import java.math.BigInteger;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public class ConfigWrapperTest {

    static class ConfigBuilder extends FluentBuilder<Config, ConfigBuilder> {

        public String serverAddress;
        public String serverName;
        public int port;
        public int secondaryBindPort;
        public int leasePeriod;
        public int socketWriteTimeout;
        public int maxPoolSize;
        public int numAcceptThreads;
        public int backlogSize;
        public int maxGameThreads;
        public int maxSecondsIdle;
        public int minUsernameLength;
        public int maxUsernameLength;
        public String invalidUsernamePattern;
        public int minPasswordLength;
        public int maxPasswordLength;
        public String maxAiOpponents;
        public boolean saveGameActivated;
        public boolean authenticationActivated;
        public String googleAccount;
        public String mailgunApiKey;
        public String mailgunDomain;
        public String mailSmtpHost;
        public String mailSmtpPort;
        public String mailUser;
        public String mailPassword;
        public String mailFromAddress;
        public List<Plugin> playerTypes = Collections.emptyList();
        public List<GamePlugin> gameTypes = Collections.emptyList();
        public List<GamePlugin> tournamentTypes = Collections.emptyList();
        public List<Plugin> draftCubes = Collections.emptyList();
        public List<Plugin> deckTypes = Collections.emptyList();

        private ConfigBuilder() {
            super(ConfigBuilder::new);
        }

        @Override
        protected Config makeValue() {
            final Config result = new Config();
            result.setServer(makeServer());
            result.setPlayerTypes(makePlayerTypes());
            result.setGameTypes(makeGameTypes());
            result.setTournamentTypes(makeTournamentTypes());
            result.setDraftCubes(makeDraftCubes());
            result.setDeckTypes(makeDeckTypes());
            return result;
        }

        private Server makeServer() {
            final Server server = new Server();
            server.setServerAddress(serverAddress);
            server.setServerName(serverName);
            server.setPort(BigInteger.valueOf(port));
            server.setSecondaryBindPort(bi(secondaryBindPort));
            server.setLeasePeriod(bi(leasePeriod));
            server.setSocketWriteTimeout(bi(socketWriteTimeout));
            server.setMaxPoolSize(bi(maxPoolSize));
            server.setNumAcceptThreads(bi(numAcceptThreads));
            server.setBacklogSize(bi(backlogSize));
            server.setMaxGameThreads(bi(maxGameThreads));
            server.setMaxSecondsIdle(bi(maxSecondsIdle));
            server.setMinUserNameLength(bi(minUsernameLength));
            server.setMaxUserNameLength(bi(maxUsernameLength));
            server.setInvalidUserNamePattern(invalidUsernamePattern);
            server.setMinPasswordLength(bi(minPasswordLength));
            server.setMaxPasswordLength(bi(maxPasswordLength));
            server.setMaxAiOpponents(maxAiOpponents);
            server.setSaveGameActivated(saveGameActivated);
            server.setAuthenticationActivated(authenticationActivated);
            server.setGoogleAccount(googleAccount);
            server.setMailgunApiKey(mailgunApiKey);
            server.setMailgunDomain(mailgunDomain);
            server.setMailSmtpHost(mailSmtpHost);
            server.setMailSmtpPort(mailSmtpPort);
            server.setMailUser(mailUser);
            server.setMailPassword(mailPassword);
            server.setMailFromAddress(mailFromAddress);
            return server;
        }

        private PlayerTypes makePlayerTypes() {
            final PlayerTypes playerTypes = new PlayerTypes();
            this.playerTypes.forEach(p -> playerTypes.getPlayerType().add(p));
            return playerTypes;
        }

        private GameTypes makeGameTypes() {
            final GameTypes gameTypes = new GameTypes();
            this.gameTypes.forEach(g -> gameTypes.getGameType().add(g));
            return gameTypes;
        }

        private TournamentTypes makeTournamentTypes() {
            final TournamentTypes tournamentTypes = new TournamentTypes();
            this.tournamentTypes.forEach(t -> tournamentTypes.getTournamentType().add(t));
            return tournamentTypes;
        }

        private DraftCubes makeDraftCubes() {
            final DraftCubes draftCubes = new DraftCubes();
            this.draftCubes.forEach(d -> draftCubes.getDraftCube().add(d));
            return draftCubes;
        }

        private DeckTypes makeDeckTypes() {
            final DeckTypes deckTypes = new DeckTypes();
            this.deckTypes.forEach(d -> deckTypes.getDeckType().add(d));
            return deckTypes;
        }

        private BigInteger bi(int value) {
            return BigInteger.valueOf(value);
        }
    }

    private ConfigBuilder baseConfigBuilder() {
        return new ConfigBuilder();
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

    private DynamicTest testString(String description, Consumer<ConfigBuilder> builderSetter, Function<ConfigWrapper, Object> valueExtractor) {
        return testTemplate(description, builderSetter, valueExtractor, expectedString);
    }

    private DynamicTest testTemplate(String description, Consumer<ConfigBuilder> builderSetter, Function<ConfigWrapper, Object> valueExtractor, Object expectedValue) {
        return DynamicTest.dynamicTest(description, () -> assertThat(valueExtractor.apply(makeTestee(baseConfigBuilder().with(builderSetter)))).isEqualTo(expectedValue));
    }

    private ConfigWrapper makeTestee(ConfigBuilder builder) {
        return new ConfigWrapper(builder.build());
    }

    private DynamicTest testInt(String description, Consumer<ConfigBuilder> builderSetter, Function<ConfigWrapper, Object> valueExtractor) {
        return testTemplate(description, builderSetter, valueExtractor, expectedPositiveInt);
    }

    private DynamicTest testTrue(String description, Consumer<ConfigBuilder> builderSetter, Function<ConfigWrapper, Object> valueExtractor) {
        return testTemplate(description, builderSetter, valueExtractor, true);
    }


    private final Comparator<Plugin> pluginComparator = (p1, p2) -> {
        if (Objects.equals(p1.getName(), p2.getName()) &&
                Objects.equals(p1.getJar(), p2.getJar()) &&
                Objects.equals(p1.getClassName(), p2.getClassName())) {
            return 0;
        } else {
            return -1;
        }
    };

    private final Comparator<GamePlugin> gamePluginComparator = (p1, p2) -> {
        if (Objects.equals(p1.getName(), p2.getName()) &&
                Objects.equals(p1.getJar(), p2.getJar()) &&
                Objects.equals(p1.getClassName(), p2.getClassName()) &&
                Objects.equals(p1.getTypeName(), p2.getTypeName())) {
            return 0;
        } else {
            return -1;
        }
    };

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
        final Plugin plugin = new Plugin();
        plugin.setName(name);
        plugin.setJar(jar);
        plugin.setClassName(className);
        return plugin;
    }

    private GamePlugin makeGamePlugin(String name, String jar, String className, String typeName) {
        final GamePlugin plugin = new GamePlugin();
        plugin.setName(name);
        plugin.setJar(jar);
        plugin.setClassName(className);
        plugin.setTypeName(typeName);
        return plugin;
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
                                   Consumer<ConfigBuilder> builderSetter,
                                   Function<ConfigWrapper, List<Plugin>> listExtractor) {
        return testTemplateForLists(description, builderSetter, listExtractor, randomPlugins, pluginComparator);
    }

    private DynamicTest gamePluginTest(String description,
                                       Consumer<ConfigBuilder> builderSetter,
                                       Function<ConfigWrapper, List<GamePlugin>> listExtractor) {
        return testTemplateForLists(description, builderSetter, listExtractor, randomGamePlugins, gamePluginComparator);
    }

    private <T> DynamicTest testTemplateForLists(String description,
                                                 Consumer<ConfigBuilder> builderSetter,
                                                 Function<ConfigWrapper, List<T>> listExtractor,
                                                 List<T> expectedValue,
                                                 Comparator<T> comparator) {
        return DynamicTest.dynamicTest(description, () ->
                assertThat(listExtractor.apply(makeTestee(baseConfigBuilder().with(builderSetter))))
                        .usingElementComparator(comparator)
                        .containsExactlyElementsOf(expectedValue)
        );
    }
}
