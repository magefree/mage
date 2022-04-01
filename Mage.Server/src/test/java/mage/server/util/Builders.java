package mage.server.util;

import mage.server.util.config.*;
import mage.utils.FluentBuilder;

import java.math.BigInteger;
import java.util.Collections;
import java.util.List;

public final class Builders {

    private Builders() {
    }

    public static class ConfigBuilder extends FluentBuilder<Config, Builders.ConfigBuilder> {

        public boolean multihome;
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
        public List<Server.Home> homes = Collections.emptyList();

        public ConfigBuilder() {
            super(Builders.ConfigBuilder::new);
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
            server.setMultihome(multihome);
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
            homes.forEach(h -> server.getHome().add(h));
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

    public static class ServerBuilder extends FluentBuilder<Server, Builders.ServerBuilder> {

        public boolean multihome;
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
        public List<Server.Home> home = Collections.emptyList();

        public ServerBuilder() {
            super(Builders.ServerBuilder::new);
        }

        @Override
        protected Server makeValue() {
            final Server server = new Server();
            server.setMultihome(multihome);
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
            home.forEach(h -> server.getHome().add(h));
            return server;
        }

        private BigInteger bi(int value) {
            return BigInteger.valueOf(value);
        }
    }

    public static class HomeBuilder extends FluentBuilder<Server.Home, HomeBuilder> {

        public String internal;
        public String external;
        public Integer port;
        public Integer externalPort;
        public Integer secondaryPort;

        public HomeBuilder() {
            super(HomeBuilder::new);
        }

        @Override
        protected Server.Home makeValue() {
            final Server.Home result = new Server.Home();
            result.setInternal(internal);
            result.setExternal(external);
            result.setPort(port == null ? null : BigInteger.valueOf(port));
            result.setExternalport(externalPort == null ? null : BigInteger.valueOf(externalPort));
            result.setSecondaryport(secondaryPort == null ? null : BigInteger.valueOf(secondaryPort));
            return result;
        }
    }

    public static class PluginBuilder extends FluentBuilder<Plugin, Builders.PluginBuilder> {

        public String name;
        public String jar;
        public String className;

        public PluginBuilder() {
            super(Builders.PluginBuilder::new);
        }

        @Override
        protected Plugin makeValue() {
            final Plugin plugin = new Plugin();
            plugin.setName(name);
            plugin.setJar(jar);
            plugin.setClassName(className);
            return plugin;
        }
    }

    public static class GamePluginBuilder extends FluentBuilder<GamePlugin, Builders.GamePluginBuilder> {

        public String name;
        public String jar;
        public String className;
        public String typeName;

        public GamePluginBuilder() {
            super(Builders.GamePluginBuilder::new);
        }

        @Override
        protected GamePlugin makeValue() {
            final GamePlugin plugin = new GamePlugin();
            plugin.setName(name);
            plugin.setJar(jar);
            plugin.setClassName(className);
            plugin.setTypeName(typeName);
            return plugin;
        }
    }
}
