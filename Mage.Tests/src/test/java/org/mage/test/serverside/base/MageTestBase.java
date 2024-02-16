package org.mage.test.serverside.base;

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
import mage.players.Player;
import mage.players.PlayerType;
import mage.server.game.GameFactory;
import mage.server.game.PlayerFactory;
import mage.server.managers.ConfigSettings;
import mage.server.tournament.TournamentFactory;
import mage.server.util.ConfigFactory;
import mage.server.util.ConfigWrapper;
import mage.server.util.PluginClassLoader;
import mage.server.util.config.GamePlugin;
import mage.server.util.config.Plugin;
import mage.util.CardUtil;
import mage.util.Copier;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.BeforeClass;
import org.mage.test.player.TestPlayer;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Base class for all tests.
 *
 * @author ayratn
 */
public abstract class MageTestBase {

    protected static Logger logger = Logger.getLogger(MageTestBase.class);

    public static PluginClassLoader classLoader = new PluginClassLoader();

    private static final String PLUGIN_FOLDER = "plugins";

    protected Pattern pattern = Pattern.compile("([a-zA-Z]*):([\\w]*):([a-zA-Z ,\\-.!'\\d]*):([\\d]*)(:\\{tapped\\})?");

    protected List<Card> handCardsA = new ArrayList<>();
    protected List<Card> handCardsB = new ArrayList<>();
    protected List<PermanentCard> battlefieldCardsA = new ArrayList<>();
    protected List<PermanentCard> battlefieldCardsB = new ArrayList<>();
    protected List<Card> graveyardCardsA = new ArrayList<>();
    protected List<Card> graveyardCardsB = new ArrayList<>();
    protected List<Card> libraryCardsA = new ArrayList<>();
    protected List<Card> libraryCardsB = new ArrayList<>();

    protected Map<Zone, String> commandsA = new HashMap<>();
    protected Map<Zone, String> commandsB = new HashMap<>();

    protected TestPlayer playerA;
    protected TestPlayer playerB;

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
     * <p></p>
     * Example: turn:1 result:won:ComputerA life:ComputerA:20 life:ComputerB:0
     * battlefield:ComputerB:Tine Shrike:0 graveyard:ComputerB:Tine Shrike:1
     */
    protected List<String> expectedResults = new ArrayList<>();

    protected static final String TESTS_PATH = "tests" + File.separator;

    @BeforeClass
    public static void init() {
        Logger.getRootLogger().setLevel(Level.DEBUG);

        // one time init for all tests
        if (GameFactory.instance.getGameTypes().isEmpty()) {
            deleteSavedGames();
            ConfigSettings config = new ConfigWrapper(ConfigFactory.loadFromFile("config/config.xml"));
            config.getGameTypes().forEach((gameType) -> {
                GameFactory.instance.addGameType(gameType.getName(), loadGameType(gameType), loadPlugin(gameType));
            });
            config.getTournamentTypes().forEach((tournamentType) -> {
                TournamentFactory.instance.addTournamentType(tournamentType.getName(), loadTournamentType(tournamentType), loadPlugin(tournamentType));
            });
            config.getPlayerTypes().forEach((playerType) -> {
                PlayerFactory.instance.addPlayerType(playerType.getName(), loadPlugin(playerType));
            });
//        for (Plugin plugin : config.getDeckTypes()) {
//            DeckValidatorFactory.getInstance().addDeckType(plugin.getName(), loadPlugin(plugin));
//        }
            Copier.setLoader(classLoader);
        }
    }

    @SuppressWarnings("UseSpecificCatch")
    private static Class<?> loadPlugin(Plugin plugin) {
        try {
            classLoader.addURL(new File(PLUGIN_FOLDER + '/' + plugin.getJar()).toURI().toURL());
            logger.debug("Loading plugin: " + plugin.getClassName());
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
            classLoader.addURL(new File(PLUGIN_FOLDER + '/' + plugin.getJar()).toURI().toURL());
            logger.debug("Loading game type: " + plugin.getClassName());
            return (MatchType) Class.forName(plugin.getTypeName(), true, classLoader).getConstructor().newInstance();
        } catch (ClassNotFoundException ex) {
            logger.warn("Game type not found:" + plugin.getJar() + " - check plugin folder", ex);
        } catch (Exception ex) {
            logger.fatal("Error loading game type " + plugin.getJar(), ex);
        }
        return null;
    }

    private static TournamentType loadTournamentType(GamePlugin plugin) {
        try {
            classLoader.addURL(new File(PLUGIN_FOLDER + '/' + plugin.getJar()).toURI().toURL());
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

            if (nickname.equals("ComputerA") || nickname.equals("ComputerB")) {
                List<Card> cards = null;
                List<PermanentCard> perms = null;
                Zone gameZone;
                if ("hand".equalsIgnoreCase(zone)) {
                    gameZone = Zone.HAND;
                    cards = nickname.equals("ComputerA") ? handCardsA : handCardsB;
                } else if ("battlefield".equalsIgnoreCase(zone)) {
                    gameZone = Zone.BATTLEFIELD;
                    perms = nickname.equals("ComputerA") ? battlefieldCardsA : battlefieldCardsB;
                } else if ("graveyard".equalsIgnoreCase(zone)) {
                    gameZone = Zone.GRAVEYARD;
                    cards = nickname.equals("ComputerA") ? graveyardCardsA : graveyardCardsB;
                } else if ("library".equalsIgnoreCase(zone)) {
                    gameZone = Zone.LIBRARY;
                    cards = nickname.equals("ComputerA") ? libraryCardsA : libraryCardsB;
                } else if ("player".equalsIgnoreCase(zone)) {
                    String command = m.group(3);
                    if ("life".equals(command)) {
                        if (nickname.equals("ComputerA")) {
                            commandsA.put(Zone.OUTSIDE, "life:" + m.group(4));
                        } else {
                            commandsB.put(Zone.OUTSIDE, "life:" + m.group(4));
                        }
                    }
                    return;
                } else {
                    return; // go parse next line
                }

                String cardName = m.group(3);
                Integer amount = Integer.parseInt(m.group(4));
                boolean tapped = m.group(5) != null && m.group(5).equals(":{tapped}");

                if (cardName.equals("clear")) {
                    if (nickname.equals("ComputerA")) {
                        commandsA.put(gameZone, "clear");
                    } else {
                        commandsB.put(gameZone, "clear");
                    }
                } else {
                    for (int i = 0; i < amount; i++) {
                        CardInfo cardInfo = CardRepository.instance.findCard(cardName);
                        Card newCard = cardInfo != null ? cardInfo.getCard() : null;
                        if (newCard != null) {
                            if (gameZone == Zone.BATTLEFIELD) {
                                Card permCard = CardUtil.getDefaultCardSideForBattlefield(currentGame, newCard);
                                PermanentCard p = new PermanentCard(permCard, null, currentGame);
                                p.setTapped(tapped);
                                perms.add(p);
                            } else {
                                cards.add(newCard);
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

    protected Player createPlayer(String name, PlayerType playerType) {
        Optional<Player> playerOptional = PlayerFactory.instance.createPlayer(playerType, name, RangeOfInfluence.ALL, 5);
        return playerOptional.orElseThrow(() -> new NullPointerException("PlayerFactory error - player is not created"));
    }
}
