package org.mage.test.serverside.base;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import mage.cards.Card;
import mage.cards.repository.CardInfo;
import mage.cards.repository.CardRepository;
import mage.constants.PhaseStep;
import mage.constants.RangeOfInfluence;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.match.MatchType;
import mage.game.permanent.PermanentCard;
import mage.game.tournament.TournamentType;
import mage.player.ai.ComputerPlayer;
import mage.players.Player;
import mage.server.game.GameFactory;
import mage.server.util.ConfigSettings;
import mage.server.util.PluginClassLoader;
import mage.server.util.config.GamePlugin;
import mage.server.util.config.Plugin;
import mage.util.Copier;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.BeforeClass;
import org.mage.test.player.TestPlayer;

/**
 * Base class for all tests.
 *
 * @author ayratn
 */
public abstract class MageTestPlayerBase {

    protected static Logger logger = Logger.getLogger(MageTestPlayerBase.class);

    public static PluginClassLoader classLoader = new PluginClassLoader();

    private static final String pluginFolder = "plugins";

    protected Pattern pattern = Pattern.compile("([a-zA-Z]*):([\\w]*):([a-zA-Z ,\\-.!'\\d]*):([\\d]*)(:\\{tapped\\})?");

    protected Map<TestPlayer, List<Card>> handCards = new HashMap<>();
    protected Map<TestPlayer, List<PermanentCard>> battlefieldCards = new HashMap<>();
    protected Map<TestPlayer, List<Card>> graveyardCards = new HashMap<>();
    protected Map<TestPlayer, List<Card>> libraryCards = new HashMap<>();

    protected Map<TestPlayer, Map<Zone, String>> commands = new HashMap<>();

    protected TestPlayer playerA;
    protected TestPlayer playerB;
    protected TestPlayer playerC;
    protected TestPlayer playerD;

    /**
     * Game instance initialized in load method.
     */
    protected static Game currentGame = null;

    /**
     * Player thats starts the game first. By default, it is ComputerA.
     */
    protected static Player activePlayer = null;

    protected Integer stopOnTurn;

    protected PhaseStep stopAtStep = PhaseStep.UNTAP;

    protected enum ParserState {

        INIT,
        OPTIONS,
        EXPECTED
    }

    protected ParserState parserState;

    /**
     * Expected results of the test. Read from test case in {@link String} based
     * format:
     * <p/>
     * Example: turn:1 result:won:ComputerA life:ComputerA:20 life:ComputerB:0
     * battlefield:ComputerB:Tine Shrike:0 graveyard:ComputerB:Tine Shrike:1
     */
    protected List<String> expectedResults = new ArrayList<>();

    protected static final String TESTS_PATH = "tests" + File.separator;

    @BeforeClass
    public static void init() {
        Logger.getRootLogger().setLevel(Level.DEBUG);
        logger.debug("Starting MAGE tests");
        logger.debug("Logging level: " + logger.getLevel());
        deleteSavedGames();
        ConfigSettings config = ConfigSettings.instance;
        for (GamePlugin plugin : config.getGameTypes()) {
            GameFactory.instance.addGameType(plugin.getName(), loadGameType(plugin), loadPlugin(plugin));
        }
        Copier.setLoader(classLoader);
    }

    private static Class<?> loadPlugin(Plugin plugin) {
        try {
            classLoader.addURL(new File(pluginFolder + '/' + plugin.getJar()).toURI().toURL());
            logger.info("Loading plugin: " + plugin.getClassName());
            return Class.forName(plugin.getClassName(), true, classLoader);
        } catch (ClassNotFoundException ex) {
            logger.warn("Plugin not Found:" + plugin.getJar() + " - check plugin folder");
        } catch (Exception ex) {
            logger.fatal("Error loading plugin " + plugin.getJar(), ex);
        }
        return null;
    }

    private static MatchType loadGameType(GamePlugin plugin) {
        try {
            classLoader.addURL(new File(pluginFolder + '/' + plugin.getJar()).toURI().toURL());
            logger.info("Loading game type: " + plugin.getClassName());
            return (MatchType) Class.forName(plugin.getTypeName(), true, classLoader).getConstructor().newInstance();
        } catch (ClassNotFoundException ex) {
            logger.warn("Game type not found:" + plugin.getJar() + " - check plugin folder");
        } catch (Exception ex) {
            logger.fatal("Error loading game type " + plugin.getJar(), ex);
        }
        return null;
    }

    private static TournamentType loadTournamentType(GamePlugin plugin) {
        try {
            classLoader.addURL(new File(pluginFolder + '/' + plugin.getJar()).toURI().toURL());
            logger.info("Loading tournament type: " + plugin.getClassName());
            return (TournamentType) Class.forName(plugin.getTypeName(), true, classLoader).getConstructor().newInstance();
        } catch (ClassNotFoundException ex) {
            logger.warn("Tournament type not found:" + plugin.getJar() + " - check plugin folder");
        } catch (Exception ex) {
            logger.fatal("Error loading game type " + plugin.getJar(), ex);
        }
        return null;
    }

    private static void deleteSavedGames() {
        File directory = new File("saved/");
        if (!directory.exists()) {
            directory.mkdirs();
        }
        File[] files = directory.listFiles(
                (dir, name) -> name.endsWith(".game")
        );
        for (File file : files) {
            file.delete();
        }
    }

    protected void parseScenario(String filename) throws FileNotFoundException {
        parserState = ParserState.INIT;
        File f = new File(filename);
        try (Scanner scanner = new Scanner(f)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                if (line == null || line.isEmpty() || line.startsWith("#")) {
                    continue;
                }
                if (line.startsWith("$include")) {
                    includeFrom(line);
                    continue;
                }
                if (line.startsWith("$expected")) {
                    parserState = ParserState.EXPECTED;
                    continue;
                }
                parseLine(line);
            }
        }
    }

    private void parseLine(String line) {
        if (parserState == ParserState.EXPECTED) {
            expectedResults.add(line); // just remember for future use
            return;
        }

        Matcher m = pattern.matcher(line);
        if (m.matches()) {

            String zone = m.group(1);
            String nickname = m.group(2);

            if (nickname.startsWith("Computer")) {
                List<Card> cards = null;
                List<PermanentCard> perms = null;
                Zone gameZone;
                if ("hand".equalsIgnoreCase(zone)) {
                    gameZone = Zone.HAND;
                    cards = getHandCards(getPlayer(nickname));
                } else if ("battlefield".equalsIgnoreCase(zone)) {
                    gameZone = Zone.BATTLEFIELD;
                    perms = getBattlefieldCards(getPlayer(nickname));
                } else if ("graveyard".equalsIgnoreCase(zone)) {
                    gameZone = Zone.GRAVEYARD;
                    cards = getGraveCards(getPlayer(nickname));
                } else if ("library".equalsIgnoreCase(zone)) {
                    gameZone = Zone.LIBRARY;
                    cards = getLibraryCards(getPlayer(nickname));
                } else if ("player".equalsIgnoreCase(zone)) {
                    String command = m.group(3);
                    if ("life".equals(command)) {
                        getCommands(getPlayer(nickname)).put(Zone.OUTSIDE, "life:" + m.group(4));
                    }
                    return;
                } else {
                    return; // go parse next line
                }

                String cardName = m.group(3);
                Integer amount = Integer.parseInt(m.group(4));
                boolean tapped = m.group(5) != null && m.group(5).equals(":{tapped}");

                if (cardName.equals("clear")) {
                    getCommands(getPlayer(nickname)).put(gameZone, "clear");
                } else {
                    for (int i = 0; i < amount; i++) {
                        CardInfo cardInfo = CardRepository.instance.findCard(cardName);
                        Card card = cardInfo != null ? cardInfo.getCard() : null;
                        if (card != null) {
                            if (gameZone == Zone.BATTLEFIELD) {
                                PermanentCard p = new PermanentCard(card, null, currentGame);
                                p.setTapped(tapped);
                                perms.add(p);
                            } else {
                                cards.add(card);
                            }
                        } else {
                            logger.fatal("Couldn't find a card: " + cardName);
                            logger.fatal("line: " + line);
                        }
                    }
                }
            } else {
                logger.warn("Unknown player: " + nickname);
            }
        } else {
            logger.warn("Init string wasn't parsed: " + line);
        }
    }

    private TestPlayer getPlayer(String name) {
        switch (name) {
            case "ComputerA":
                return playerA;
            case "ComputerB":
                return playerB;
            case "ComputerC":
                return playerC;
            case "ComputerD":
                return playerD;
        }
        throw new IllegalArgumentException("Couldn't find player for name=" + name);
    }

    protected List<Card> getHandCards(TestPlayer player) {
        if (handCards.containsKey(player)) {
            return handCards.get(player);
        }
        List<Card> hand = new ArrayList<>();
        handCards.put(player, hand);
        return hand;
    }

    protected List<Card> getGraveCards(TestPlayer player) {
        if (graveyardCards.containsKey(player)) {
            return graveyardCards.get(player);
        }
        List<Card> grave = new ArrayList<>();
        graveyardCards.put(player, grave);
        return grave;
    }

    protected List<Card> getLibraryCards(TestPlayer player) {
        if (libraryCards.containsKey(player)) {
            return libraryCards.get(player);
        }
        List<Card> library = new ArrayList<>();
        libraryCards.put(player, library);
        return library;
    }

    protected List<PermanentCard> getBattlefieldCards(TestPlayer player) {
        if (battlefieldCards.containsKey(player)) {
            return battlefieldCards.get(player);
        }
        List<PermanentCard> battlefield = new ArrayList<>();
        battlefieldCards.put(player, battlefield);
        return battlefield;
    }

    protected Map<Zone, String> getCommands(TestPlayer player) {
        if (commands.containsKey(player)) {
            return commands.get(player);
        }
        Map<Zone, String> command = new HashMap<>();
        commands.put(player, command);
        return command;
    }

    private void includeFrom(String line) throws FileNotFoundException {
        String[] params = line.split(" ");
        if (params.length == 2) {
            String paramName = params[1];
            if (!paramName.contains("..")) {
                String includePath = TESTS_PATH + paramName;
                File f = new File(includePath);
                if (f.exists()) {
                    parseScenario(includePath);
                } else {
                    logger.warn("Ignored (file doesn't exist): " + line);
                }
            } else {
                logger.warn("Ignored (wrong charactres): " + line);
            }
        } else {
            logger.warn("Ignored (wrong size): " + line);
        }
    }

    protected TestPlayer createPlayer(String name, RangeOfInfluence rangeOfInfluence) {
        return new TestPlayer(new ComputerPlayer(name, rangeOfInfluence));
    }

}
