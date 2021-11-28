package org.mage.test.serverside.base.impl;

import mage.MageObject;
import mage.Mana;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.cards.Card;
import mage.cards.decks.Deck;
import mage.cards.decks.DeckCardLists;
import mage.cards.decks.importer.DeckImporter;
import mage.cards.repository.CardInfo;
import mage.cards.repository.CardRepository;
import mage.cards.repository.CardScanner;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.Filter;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.game.*;
import mage.game.command.CommandObject;
import mage.game.match.MatchOptions;
import mage.game.permanent.Permanent;
import mage.game.permanent.PermanentCard;
import mage.player.ai.ComputerPlayer7;
import mage.player.ai.ComputerPlayerMCTS;
import mage.players.ManaPool;
import mage.players.Player;
import mage.server.game.GameSessionPlayer;
import mage.server.util.SystemUtil;
import mage.util.CardUtil;
import mage.view.GameView;
import org.junit.Assert;
import org.junit.Before;
import org.mage.test.player.PlayerAction;
import org.mage.test.player.TestPlayer;
import org.mage.test.serverside.base.CardTestAPI;
import org.mage.test.serverside.base.CardTestCodePayload;
import org.mage.test.serverside.base.MageTestPlayerBase;

import java.io.FileNotFoundException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static org.junit.Assert.assertTrue;

/**
 * API for test initialization and asserting the test results.
 *
 * @author ayratn, JayDi85
 */
public abstract class CardTestPlayerAPIImpl extends MageTestPlayerBase implements CardTestAPI {

    // DEBUG only, enable it to fast startup tests without database create (delete \db\ folder to force db recreate)
    private static final boolean FAST_SCAN_WITHOUT_DATABASE_CREATE = false;

    private static final boolean SHOW_EXECUTE_TIME_PER_TEST = false;

    public static final String ALIAS_PREFIX = "@"; // don't change -- it uses in user's tests
    public static final String CHECK_PARAM_DELIMETER = "#";
    public static final String CHECK_PREFIX = "check:"; // prefix for all check commands
    public static final String SHOW_PREFIX = "show:"; // prefix for all show commands
    public static final String AI_PREFIX = "ai:"; // prefix for all ai commands
    public static final String RUN_PREFIX = "run:"; // prefix for all run commands

    static {
        // aliases can be used in check commands, so all prefixes and delimeters must be unique
        // already uses by targets: ^ $ [ ]
        Assert.assertFalse("prefix must be unique", CHECK_PARAM_DELIMETER.contains(ALIAS_PREFIX));
        Assert.assertFalse("prefix must be unique", CHECK_PREFIX.contains(ALIAS_PREFIX));
        Assert.assertFalse("prefix must be unique", ALIAS_PREFIX.contains(CHECK_PREFIX));
    }

    // prefix for activate commands
    // can be called with alias, example: @card_ref ability text
    public static final String ACTIVATE_ABILITY = "activate:";
    public static final String ACTIVATE_PLAY = "activate:Play ";
    public static final String ACTIVATE_CAST = "activate:Cast ";
    public static final String ACTIVATE_MANA = "manaActivate:";

    // commands for AI
    public static final String AI_COMMAND_PLAY_PRIORITY = "play priority";
    public static final String AI_COMMAND_PLAY_STEP = "play step";

    // commands for run
    public static final String RUN_COMMAND_CODE = "code";

    static {
        // cards can be played/casted by activate ability command too
        assertTrue("musts contains activate ability part", ACTIVATE_PLAY.startsWith(ACTIVATE_ABILITY));
        assertTrue("musts contains activate ability part", ACTIVATE_CAST.startsWith(ACTIVATE_ABILITY));
    }

    // TODO: add target player param to commands
    public static final String CHECK_COMMAND_PT = "PT";
    public static final String CHECK_COMMAND_DAMAGE = "DAMAGE";
    public static final String CHECK_COMMAND_LIFE = "LIFE";
    public static final String CHECK_COMMAND_ABILITY = "ABILITY";
    public static final String CHECK_COMMAND_PLAYABLE_ABILITY = "PLAYABLE_ABILITY";
    public static final String CHECK_COMMAND_PERMANENT_COUNT = "PERMANENT_COUNT";
    public static final String CHECK_COMMAND_PERMANENT_TAPPED = "PERMANENT_TAPPED";
    public static final String CHECK_COMMAND_PERMANENT_COUNTERS = "PERMANENT_COUNTERS";
    public static final String CHECK_COMMAND_CARD_COUNTERS = "CARD_COUNTERS";
    public static final String CHECK_COMMAND_EXILE_COUNT = "EXILE_COUNT";
    public static final String CHECK_COMMAND_GRAVEYARD_COUNT = "GRAVEYARD_COUNT";
    public static final String CHECK_COMMAND_LIBRARY_COUNT = "LIBRARY_COUNT";
    public static final String CHECK_COMMAND_HAND_COUNT = "HAND_COUNT";
    public static final String CHECK_COMMAND_HAND_CARD_COUNT = "HAND_CARD_COUNT";
    public static final String CHECK_COMMAND_COMMAND_CARD_COUNT = "COMMAND_CARD_COUNT";
    public static final String CHECK_COMMAND_COLOR = "COLOR";
    public static final String CHECK_COMMAND_TYPE = "TYPE";
    public static final String CHECK_COMMAND_SUBTYPE = "SUBTYPE";
    public static final String CHECK_COMMAND_MANA_POOL = "MANA_POOL";
    public static final String CHECK_COMMAND_ALIAS_ZONE = "ALIAS_ZONE";
    public static final String CHECK_COMMAND_PLAYER_IN_GAME = "PLAYER_IN_GAME";
    public static final String CHECK_COMMAND_STACK_SIZE = "STACK_SIZE";
    public static final String CHECK_COMMAND_STACK_OBJECT = "STACK_OBJECT";

    // TODO: add target player param to commands
    public static final String SHOW_COMMAND_LIBRARY = "LIBRARY";
    public static final String SHOW_COMMAND_HAND = "HAND";
    public static final String SHOW_COMMAND_COMMAND = "COMMAND";
    public static final String SHOW_COMMAND_BATTLEFIELD = "BATTLEFIELD";
    public static final String SHOW_COMMAND_GRAVEYEARD = "GRAVEYARD";
    public static final String SHOW_COMMAND_EXILE = "EXILE";
    public static final String SHOW_COMMAND_AVAILABLE_ABILITIES = "AVAILABLE_ABILITIES";
    public static final String SHOW_COMMAND_AVAILABLE_MANA = "AVAILABLE_MANA";
    public static final String SHOW_COMMAND_ALIASES = "ALIASES";
    public static final String SHOW_COMMAND_STACK = "STACK";

    // TODO: add target player param to commands
    public static final String ALIAS_COMMAND_ADD = "ADD";

    protected GameOptions gameOptions;

    protected String deckNameA;
    protected String deckNameB;
    protected String deckNameC;
    protected String deckNameD;

    private int rollbackBlock = 0; // used to handle actions that have to be added aufter a rollback
    private boolean rollbackBlockActive = false;
    private TestPlayer rollbackPlayer = null;

    protected enum ExpectedType {
        TURN_NUMBER,
        RESULT,
        LIFE,
        BATTLEFIELD,
        GRAVEYARD,
        UNKNOWN
    }

    public CardTestPlayerAPIImpl() {
        // load all cards to db from class list
        ArrayList<String> errorsList = new ArrayList<>();
        if (FAST_SCAN_WITHOUT_DATABASE_CREATE && CardRepository.instance.findCard("XLN", "272") != null) {
            CardScanner.scanned = true;
        }
        CardScanner.scan(errorsList);

        if (errorsList.size() > 0) {
            Assert.fail("Found errors on card loading: " + '\n' + errorsList.stream().collect(Collectors.joining("\n")));
        }
    }

    /**
     * Default game initialization params for red player (that plays with
     * Mountains)
     */
    @Override
    public void useRedDefault() {
        // *** ComputerA ***
        // battlefield:ComputerA:Mountain:5
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 5);
        // hand:ComputerA:Mountain:4
        addCard(Zone.HAND, playerA, "Mountain", 5);
        // library:ComputerA:clear:0
        removeAllCardsFromLibrary(playerA);
        // library:ComputerA:Mountain:10
        addCard(Zone.LIBRARY, playerA, "Mountain", 10);

        // *** ComputerB ***
        // battlefield:ComputerB:Plains:2
        addCard(Zone.BATTLEFIELD, playerB, "Plains", 2);
        // hand:ComputerB:Plains:2
        addCard(Zone.HAND, playerB, "Plains", 2);
        // library:ComputerB:clear:0
        removeAllCardsFromLibrary(playerB);
        // library:ComputerB:Plains:10
        addCard(Zone.LIBRARY, playerB, "Plains", 10);
    }

    /**
     * Default game initialization params for white player (that plays with
     * Plains)
     */
    public void useWhiteDefault() {
        // *** ComputerA ***
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 5);
        addCard(Zone.HAND, playerA, "Plains", 5);
        removeAllCardsFromLibrary(playerA);
        addCard(Zone.LIBRARY, playerA, "Plains", 10);

        // *** ComputerB ***
        addCard(Zone.BATTLEFIELD, playerB, "Plains", 2);
        addCard(Zone.HAND, playerB, "Plains", 2);
        removeAllCardsFromLibrary(playerB);
        addCard(Zone.LIBRARY, playerB, "Plains", 10);
    }

    /**
     * @throws GameException
     * @throws FileNotFoundException
     */
    @Before
    public void reset() throws GameException, FileNotFoundException {
        if (currentGame != null) {
            logger.debug("Resetting previous game and creating new one!");
            currentGame = null;
        }

        // prepare fake match (needs for testing some client-server code)
        // always 4 seats
        MatchOptions matchOptions = new MatchOptions("test match", "test game type", true, 4);
        currentMatch = new FreeForAllMatch(matchOptions);
        currentGame = createNewGameAndPlayers();

        activePlayer = playerA;
        stopOnTurn = 2;
        stopAtStep = PhaseStep.UNTAP;

        for (Player player : currentGame.getPlayers().values()) {
            TestPlayer testPlayer = (TestPlayer) player;
            getCommands(testPlayer).clear();
            getLibraryCards(testPlayer).clear();
            getHandCards(testPlayer).clear();
            getBattlefieldCards(testPlayer).clear();
            getGraveCards(testPlayer).clear();
            // Reset the turn counter for tests
            ((TestPlayer) player).setInitialTurns(0);
        }

        gameOptions = new GameOptions();

        rollbackBlock = 0;
        rollbackBlockActive = false;

    }

    abstract protected Game createNewGameAndPlayers() throws GameException, FileNotFoundException;

    protected TestPlayer createPlayer(Game game, TestPlayer player, String name) throws GameException {
        return createPlayer(game, player, name, "RB Aggro.dck");
    }

    protected TestPlayer createPlayer(Game game, TestPlayer player, String name, String deckName) throws GameException {
        player = createNewPlayer(name, game.getRangeOfInfluence());
        player.setTestMode(true);

        logger.debug("Loading deck...");
        DeckCardLists list;
        if (loadedDecks.containsKey(deckName)) {
            list = loadedDecks.get(deckName);
        } else {
            list = DeckImporter.importDeckFromFile(deckName, true);
            loadedDecks.put(deckName, list);
        }
        Deck deck = Deck.load(list, false, false, loadedCardInfo);
        logger.debug("Done!");
        if (deck.getCards().size() < 40) {
            throw new IllegalArgumentException("Couldn't load deck, deck size=" + deck.getCards().size());
        }

        game.loadCards(deck.getCards(), player.getId());
        game.loadCards(deck.getSideboard(), player.getId());
        game.addPlayer(player, deck);
        currentMatch.addPlayer(player, deck); // fake match

        return player;
    }

    public void execute() throws IllegalStateException {
        if (currentGame == null || activePlayer == null) {
            throw new IllegalStateException("Game is not initialized. Use load method to load a test case and initialize a game.");
        }

        // check stop command
        int maxTurn = 1;
        int maxPhase = 0;
        for (Player player : currentGame.getPlayers().values()) {
            if (player instanceof TestPlayer) {
                TestPlayer testPlayer = (TestPlayer) player;
                for (PlayerAction action : testPlayer.getActions()) {
                    assertTrue("Wrong turn in action " + action.getTurnNum(), action.getTurnNum() >= 1);
                    int curTurn = action.getTurnNum();
                    int curPhase = action.getStep().getIndex();
                    if ((curTurn > maxTurn) || (curTurn == maxTurn && curPhase > maxPhase)) {
                        maxTurn = curTurn;
                        maxPhase = curPhase;
                    }
                }
            }
        }
        Assert.assertFalse("Wrong stop command on " + this.stopOnTurn + " / " + this.stopAtStep + " (" + this.stopAtStep.getIndex() + ")"
                        + " (found actions after stop on " + maxTurn + " / " + maxPhase + ")",
                (maxTurn > this.stopOnTurn) || (maxTurn == this.stopOnTurn && maxPhase > this.stopAtStep.getIndex()));

        if (!currentGame.isPaused()) {
            // workaround to fill range info (cause real range fills after game start, but some cheated cards needs range on ETB)
            for (Player player : currentGame.getPlayers().values()) {
                player.updateRange(currentGame);
            }
            // add cards to game
            for (Player player : currentGame.getPlayers().values()) {
                TestPlayer testPlayer = (TestPlayer) player;
                currentGame.cheat(testPlayer.getId(), getCommands(testPlayer));
                currentGame.cheat(testPlayer.getId(), getLibraryCards(testPlayer), getHandCards(testPlayer),
                        getBattlefieldCards(testPlayer), getGraveCards(testPlayer), getCommandCards(testPlayer));
            }
        }

        long t1 = System.nanoTime();

        gameOptions.testMode = true;
        gameOptions.stopOnTurn = stopOnTurn;
        gameOptions.stopAtStep = stopAtStep;
        currentGame.setGameOptions(gameOptions);
        if (currentGame.isPaused()) {
            currentGame.resume();// needed if execute() is performed multiple times
        }
        currentGame.start(activePlayer.getId());
        currentGame.setGameStopped(true); // used for rollback handling
        long t2 = System.nanoTime();
        logger.debug("Winner: " + currentGame.getWinner());
        if (SHOW_EXECUTE_TIME_PER_TEST) {
            logger.info(Thread.currentThread().getStackTrace()[2].getMethodName() + " has been executed. Execution time: " + (t2 - t1) / 1000000 + " ms");
        }

        // TODO: 01.12.2018, JayDi85 - uncomment and fix MANY broken tests with wrong commands
        //assertAllCommandsUsed();
    }

    protected TestPlayer createNewPlayer(String playerName, RangeOfInfluence rangeOfInfluence) {
        return createPlayer(playerName, rangeOfInfluence);
    }

    protected Player getPlayerFromName(String playerName, String line) {
        Player player = null;
        switch (playerName) {
            case "ComputerA":
                player = currentGame.getPlayer(playerA.getId());
                break;
            case "ComputerB":
                player = currentGame.getPlayer(playerB.getId());
                break;
            case "ComputerC":
                player = currentGame.getPlayer(playerC.getId());
                break;
            case "ComputerD":
                player = currentGame.getPlayer(playerD.getId());
                break;
            default:
                throw new IllegalArgumentException("Wrong player in 'battlefield' line, player=" + player + ", line=" + line);
        }
        return player;
    }

    private void addPlayerAction(TestPlayer player, int turnNum, PhaseStep step, String action) {
        PlayerAction playerAction = new PlayerAction("", turnNum, step, action);
        addPlayerAction(player, playerAction);
    }

    private void addPlayerAction(TestPlayer player, String actionName, int turnNum, PhaseStep step, String action) {
        PlayerAction playerAction = new PlayerAction(actionName, turnNum, step, action);
        addPlayerAction(player, playerAction);
    }

    private void addPlayerAction(TestPlayer player, PlayerAction playerAction) {
        if (rollbackBlockActive) {
            rollbackPlayer.getRollbackActions()
                    .computeIfAbsent(rollbackBlock, block -> new HashMap<>())
                    .computeIfAbsent(player.getId(), playerId -> new ArrayList<>())
                    .add(playerAction);
        } else {
            player.addAction(playerAction);
        }
    }

    // check commands
    private void check(String checkName, int turnNum, PhaseStep step, TestPlayer player, String command, String... params) {
        String res = CHECK_PREFIX + command;
        for (String param : params) {
            res += CHECK_PARAM_DELIMETER + param;
        }
        addPlayerAction(player, checkName, turnNum, step, res);
    }

    /**
     * Execute custom code under player's priority. You can stop debugger on it.
     * <p>
     * Example 1: check some conditions in the middle of the test
     * Example 2: make game modifications (if you don't want to use custom abilities)
     * Example 3: stop debugger in the middle of the game
     *
     * @param info
     * @param turnNum
     * @param step
     * @param player
     * @param codePayload code to execute
     */
    public void runCode(String info, int turnNum, PhaseStep step, TestPlayer player, CardTestCodePayload codePayload) {
        PlayerAction playerAction = new PlayerAction(info, turnNum, step, RUN_PREFIX + RUN_COMMAND_CODE, codePayload);
        addPlayerAction(player, playerAction);
    }

    public void checkPT(String checkName, int turnNum, PhaseStep step, TestPlayer player, String permanentName, Integer power, Integer toughness) {
        //Assert.assertNotEquals("", permanentName);
        check(checkName, turnNum, step, player, CHECK_COMMAND_PT, permanentName, power.toString(), toughness.toString());
    }

    public void checkDamage(String checkName, int turnNum, PhaseStep step, TestPlayer player, String permanentName, Integer damage) {
        //Assert.assertNotEquals("", permanentName);
        check(checkName, turnNum, step, player, CHECK_COMMAND_DAMAGE, permanentName, damage.toString());
    }

    public void checkLife(String checkName, int turnNum, PhaseStep step, TestPlayer player, Integer life) {
        check(checkName, turnNum, step, player, CHECK_COMMAND_LIFE, life.toString());
    }

    public void checkPlayerInGame(String checkName, int turnNum, PhaseStep step, TestPlayer player, TestPlayer targetPlayer, Boolean mustBeInGame) {
        check(checkName, turnNum, step, player, CHECK_COMMAND_PLAYER_IN_GAME, targetPlayer.getId().toString(), mustBeInGame.toString());
    }

    public void checkAbility(String checkName, int turnNum, PhaseStep step, TestPlayer player, String permanentName, Class<?> abilityClass, Boolean mustHave) {
        //Assert.assertNotEquals("", permanentName);
        check(checkName, turnNum, step, player, CHECK_COMMAND_ABILITY, permanentName, abilityClass.getName(), mustHave.toString());
    }

    public void checkPlayableAbility(String checkName, int turnNum, PhaseStep step, TestPlayer player, String abilityStartText, Boolean mustHave) {
        check(checkName, turnNum, step, player, CHECK_COMMAND_PLAYABLE_ABILITY, abilityStartText, mustHave.toString());
    }

    public void checkPermanentCount(String checkName, int turnNum, PhaseStep step, TestPlayer player, String permanentName, Integer count) {
        //Assert.assertNotEquals("", permanentName);
        checkPermanentCount(checkName, turnNum, step, player, player, permanentName, count);
    }

    public void checkPermanentCount(String checkName, int turnNum, PhaseStep step, TestPlayer player, TestPlayer targetPlayer, String permanentName, Integer count) {
        //Assert.assertNotEquals("", permanentName);
        check(checkName, turnNum, step, player, CHECK_COMMAND_PERMANENT_COUNT, targetPlayer.getId().toString(), permanentName, count.toString());
    }

    public void checkPermanentTapped(String checkName, int turnNum, PhaseStep step, TestPlayer player, String permanentName, Boolean tapped, Integer count) {
        checkPermanentTapped(checkName, turnNum, step, player, player, permanentName, tapped, count);
    }

    public void checkPermanentTapped(String checkName, int turnNum, PhaseStep step, TestPlayer player, TestPlayer targetPlayer, String permanentName, Boolean tapped, Integer count) {
        check(checkName, turnNum, step, player, CHECK_COMMAND_PERMANENT_TAPPED, targetPlayer.getId().toString(), permanentName, tapped.toString(), count.toString());
    }

    public void checkPermanentCounters(String checkName, int turnNum, PhaseStep step, TestPlayer player, String permanentName, CounterType counterType, Integer count) {
        check(checkName, turnNum, step, player, CHECK_COMMAND_PERMANENT_COUNTERS, permanentName, counterType.toString(), count.toString());
    }

    public void checkCardCounters(String checkName, int turnNum, PhaseStep step, TestPlayer player, String cardName, CounterType counterType, Integer count) {
        check(checkName, turnNum, step, player, CHECK_COMMAND_CARD_COUNTERS, cardName, counterType.toString(), count.toString());
    }

    public void checkExileCount(String checkName, int turnNum, PhaseStep step, TestPlayer player, String permanentName, Integer count) {
        //Assert.assertNotEquals("", permanentName);
        check(checkName, turnNum, step, player, CHECK_COMMAND_EXILE_COUNT, permanentName, count.toString());
    }

    public void checkGraveyardCount(String checkName, int turnNum, PhaseStep step, TestPlayer player, String permanentName, Integer count) {
        //Assert.assertNotEquals("", permanentName);
        check(checkName, turnNum, step, player, CHECK_COMMAND_GRAVEYARD_COUNT, permanentName, count.toString());
    }

    public void checkLibraryCount(String checkName, int turnNum, PhaseStep step, TestPlayer player, String permanentName, Integer count) {
        //Assert.assertNotEquals("", permanentName);
        check(checkName, turnNum, step, player, CHECK_COMMAND_LIBRARY_COUNT, permanentName, count.toString());
    }

    public void checkHandCount(String checkName, int turnNum, PhaseStep step, TestPlayer player, Integer count) {
        check(checkName, turnNum, step, player, CHECK_COMMAND_HAND_COUNT, count.toString());
    }

    public void checkHandCardCount(String checkName, int turnNum, PhaseStep step, TestPlayer player, String cardName, Integer count) {
        //Assert.assertNotEquals("", cardName);
        check(checkName, turnNum, step, player, CHECK_COMMAND_HAND_CARD_COUNT, cardName, count.toString());
    }

    public void checkCommandCardCount(String checkName, int turnNum, PhaseStep step, TestPlayer player, String cardName, Integer count) {
        //Assert.assertNotEquals("", cardName);
        check(checkName, turnNum, step, player, CHECK_COMMAND_COMMAND_CARD_COUNT, cardName, count.toString());
    }

    public void checkColor(String checkName, int turnNum, PhaseStep step, TestPlayer player, String permanentName, String colors, Boolean mustHave) {
        //Assert.assertNotEquals("", permanentName);
        check(checkName, turnNum, step, player, CHECK_COMMAND_COLOR, permanentName, colors, mustHave.toString());
    }

    public void checkType(String checkName, int turnNum, PhaseStep step, TestPlayer player, String permanentName, CardType type, Boolean mustHave) {
        //Assert.assertNotEquals("", permanentName);
        check(checkName, turnNum, step, player, CHECK_COMMAND_TYPE, permanentName, type.toString(), mustHave.toString());
    }

    public void checkSubType(String checkName, int turnNum, PhaseStep step, TestPlayer player, String permanentName, SubType subType, Boolean mustHave) {
        //Assert.assertNotEquals("", permanentName);
        check(checkName, turnNum, step, player, CHECK_COMMAND_SUBTYPE, permanentName, subType.toString(), mustHave.toString());
    }

    public void checkManaPool(String checkName, int turnNum, PhaseStep step, TestPlayer player, String colors, Integer amount) {
        check(checkName, turnNum, step, player, CHECK_COMMAND_MANA_POOL, colors, amount.toString());
    }

    public void checkAliasZone(String checkName, int turnNum, PhaseStep step, TestPlayer player, String alias, Zone zone) {
        checkAliasZone(checkName, turnNum, step, player, alias, zone, true);
    }

    public void checkAliasZone(String checkName, int turnNum, PhaseStep step, TestPlayer player, String alias, Zone zone, Boolean mustHave) {
        check(checkName, turnNum, step, player, CHECK_COMMAND_ALIAS_ZONE, alias, zone.toString(), mustHave.toString());
    }

    public void checkStackSize(String checkName, int turnNum, PhaseStep step, TestPlayer player, Integer needStackSize) {
        check(checkName, turnNum, step, player, CHECK_COMMAND_STACK_SIZE, needStackSize.toString());
    }

    public void checkStackObject(String checkName, int turnNum, PhaseStep step, TestPlayer player, String spellAbilityOnStack, Integer needAmount) {
        check(checkName, turnNum, step, player, CHECK_COMMAND_STACK_OBJECT, spellAbilityOnStack, needAmount.toString());
    }

    // show commands
    private void show(String showName, int turnNum, PhaseStep step, TestPlayer player, String command, String... params) {
        String res = "show:" + command;
        for (String param : params) {
            res += CHECK_PARAM_DELIMETER + param;
        }
        addPlayerAction(player, showName, turnNum, step, res);
    }

    public void showLibrary(String showName, int turnNum, PhaseStep step, TestPlayer player) {
        show(showName, turnNum, step, player, SHOW_COMMAND_LIBRARY);
    }

    public void showHand(String showName, int turnNum, PhaseStep step, TestPlayer player) {
        show(showName, turnNum, step, player, SHOW_COMMAND_HAND);
    }

    public void showCommand(String showName, int turnNum, PhaseStep step, TestPlayer player) {
        show(showName, turnNum, step, player, SHOW_COMMAND_COMMAND);
    }

    public void showBattlefield(String showName, int turnNum, PhaseStep step, TestPlayer player) {
        show(showName, turnNum, step, player, SHOW_COMMAND_BATTLEFIELD);
    }

    public void showGraveyard(String showName, int turnNum, PhaseStep step, TestPlayer player) {
        show(showName, turnNum, step, player, SHOW_COMMAND_GRAVEYEARD);
    }

    public void showExile(String showName, int turnNum, PhaseStep step, TestPlayer player) {
        show(showName, turnNum, step, player, SHOW_COMMAND_EXILE);
    }

    public void showAvailableAbilities(String showName, int turnNum, PhaseStep step, TestPlayer player) {
        show(showName, turnNum, step, player, SHOW_COMMAND_AVAILABLE_ABILITIES);
    }

    public void showAvailableMana(String showName, int turnNum, PhaseStep step, TestPlayer player) {
        show(showName, turnNum, step, player, SHOW_COMMAND_AVAILABLE_MANA);
    }

    public void showAliases(String showName, int turnNum, PhaseStep step, TestPlayer player) {
        show(showName, turnNum, step, player, SHOW_COMMAND_ALIASES);
    }

    public void showStack(String showName, int turnNum, PhaseStep step, TestPlayer player) {
        show(showName, turnNum, step, player, SHOW_COMMAND_STACK);
    }

    /**
     * Removes all cards from player's library from the game. Usually this
     * should be used once before initialization to form the library in certain
     * order.
     * <p>
     * Warning, if you doesn't add cards to hand then player will lose the game
     * on draw and test return unused actions (game ended too early)
     *
     * @param player {@link Player} to remove all library cards from.
     */
    @Override
    public void removeAllCardsFromLibrary(TestPlayer player) {
        getCommands(player).put(Zone.LIBRARY, "clear");
    }

    /**
     * Removes all cards from player's hand from the game. Usually this should
     * be used once before initialization to set the players hand.
     *
     * @param player {@link Player} to remove all cards from hand.
     */
    @Deprecated // TODO: remove, cause test games don't use starting draws
    public void removeAllCardsFromHand(TestPlayer player) {
        getCommands(player).put(Zone.HAND, "clear");
    }

    /**
     * Disable auto-payment from mana pool, you must manually fill pool by
     * activateManaAbility and unlock color by setChoice Use it for pay color
     * order testing (e.g. simulate user clicks on mana pool to pay)
     *
     * @param player
     */
    public void disableManaAutoPayment(TestPlayer player) {
        player.getManaPool().setAutoPayment(false);
    }

    /**
     * Add a card to specified zone of specified player.
     * <p>
     * Zone.LIBRARY - adding by put on top (e.g. last added card goes to top of the library)
     *
     * @param gameZone {@link mage.constants.Zone} to add cards to.
     * @param player   {@link Player} to add cards for. Use either playerA or
     *                 playerB.
     * @param cardName Card name in string format.
     */
    @Override
    public void addCard(Zone gameZone, TestPlayer player, String cardName) {
        addCard(gameZone, player, cardName, 1, false);
    }

    /**
     * Add any amount of cards to specified zone of specified player.
     *
     * @param gameZone {@link mage.constants.Zone} to add cards to.
     * @param player   {@link Player} to add cards for. Use either playerA or
     *                 playerB.
     * @param cardName Card name in string format.
     * @param count    Amount of cards to be added.
     */
    @Override
    public void addCard(Zone gameZone, TestPlayer player, String cardName, int count) {
        addCard(gameZone, player, cardName, count, false);
    }

    /**
     * Add any amount of cards to specified zone of specified player.
     *
     * @param gameZone {@link mage.constants.Zone} to add cards to.
     * @param player   {@link Player} to add cards for. Use either playerA or
     *                 playerB.
     * @param cardName Card name in string format.
     * @param count    Amount of cards to be added.
     * @param tapped   In case gameZone is Battlefield, determines whether
     *                 permanent should be tapped. In case gameZone is other
     *                 than Battlefield, {@link IllegalArgumentException} is
     *                 thrown
     */
    @Override
    public void addCard(Zone gameZone, TestPlayer player, String cardName, int count, boolean tapped) {

        // aliases for mage objects
        String aliasName = "";
        boolean useAliasMultiNames = (count != 1);
        if (cardName.contains(ALIAS_PREFIX)) {
            aliasName = cardName.substring(cardName.indexOf(ALIAS_PREFIX) + ALIAS_PREFIX.length());
            cardName = cardName.substring(0, cardName.indexOf(ALIAS_PREFIX));
        }

        // one card = one aliase, massive adds can use auto-name
        if (!useAliasMultiNames && !aliasName.isEmpty() && player.getAliasByName(aliasName) != null) {
            Assert.fail("Can't add card " + cardName + " - alias " + aliasName + " already exists for " + player.getName());
        }

        // game tests don't need cards from a specific set, so it can be from any set
        CardInfo cardInfo = CardRepository.instance.findCard(cardName, true);
        if (cardInfo == null) {
            throw new IllegalArgumentException("[TEST] Couldn't find a card: " + cardName);
        }

        if (gameZone == Zone.BATTLEFIELD) {
            for (int i = 0; i < count; i++) {
                Card newCard = cardInfo.getCard();
                Card permCard = CardUtil.getDefaultCardSideForBattlefield(currentGame, newCard);

                PermanentCard p = new PermanentCard(permCard, player.getId(), currentGame);
                p.setTapped(tapped);
                getBattlefieldCards(player).add(p);

                if (!aliasName.isEmpty()) {
                    player.addAlias(player.generateAliasName(aliasName, useAliasMultiNames, i + 1), p.getId());
                }
            }
        } else {
            if (tapped) {
                throw new IllegalArgumentException("Parameter tapped=true can be used only for Zone.BATTLEFIELD.");
            }
            List<Card> cards = getCardList(gameZone, player);
            for (int i = 0; i < count; i++) {
                Card newCard = cardInfo.getCard();
                cards.add(newCard);
                if (!aliasName.isEmpty()) {
                    player.addAlias(player.generateAliasName(aliasName, useAliasMultiNames, i + 1), newCard.getId());
                }
            }
        }
    }

    public void addPlane(Player player, Planes plane) {
        assertTrue("Can't put plane to game: " + plane.getClassName(), SystemUtil.putPlaneToGame(currentGame, player, plane.getClassName()));
    }

    /**
     * Returns card list container for specified game zone and player.
     *
     * @param gameZone
     * @param player
     * @return
     */
    private List<Card> getCardList(Zone gameZone, TestPlayer player) {
        switch (gameZone) {
            case HAND:
                return getHandCards(player);
            case GRAVEYARD:
                return getGraveCards(player);
            case LIBRARY:
                return getLibraryCards(player);
            case COMMAND:
                return getCommandCards(player);
            default:
                break;
        }

        throw new AssertionError("Zone is not supported by test framework: " + gameZone);
    }

    /**
     * Set player's initial life count.
     *
     * @param player {@link Player} to set life count for.
     * @param life   Life count to set.
     */
    @Override
    public void setLife(TestPlayer player, int life) {
        getCommands(player).put(Zone.OUTSIDE, "life:" + life);
    }

    /**
     * Define turn number to stop the game on.
     *
     * @param turn
     */
    @Override
    public void setStopOnTurn(int turn) {
        setStopAt(turn, PhaseStep.UNTAP);
    }

    /**
     * Define turn number and step to stop the game on. The game stops after
     * executing the step
     *
     * @param turn
     * @param step
     */
    @Override
    public void setStopAt(int turn, PhaseStep step) {
        assertTrue("Wrong turn " + turn, turn >= 1);
        stopOnTurn = turn;
        stopAtStep = step;
    }

    /**
     * Assert turn number after test execution.
     *
     * @param turn Expected turn number to compare with. 1-based.
     */
    @Override
    public void assertTurn(int turn) throws AssertionError {
        Assert.assertEquals("Turn numbers are not equal", turn, currentGame.getTurnNum());
    }

    /**
     * Assert game result after test execution.
     *
     * @param result Expected {@link GameResult} to compare with.
     */
    @Override
    public void assertResult(Player player, GameResult result) throws AssertionError {
        if (player.equals(playerA)) {
            GameResult actual = CardTestAPI.GameResult.DRAW;
            switch (currentGame.getWinner()) {
                case "Player PlayerA is the winner":
                    actual = CardTestAPI.GameResult.WON;
                    break;
                case "Player PlayerB is the winner":
                    actual = CardTestAPI.GameResult.LOST;
                    break;
            }
            Assert.assertEquals("Game results are not equal", result, actual);
        } else if (player.equals(playerB)) {
            GameResult actual = CardTestAPI.GameResult.DRAW;
            switch (currentGame.getWinner()) {
                case "Player PlayerB is the winner":
                    actual = CardTestAPI.GameResult.WON;
                    break;
                case "Player PlayerA is the winner":
                    actual = CardTestAPI.GameResult.LOST;
                    break;
            }
            Assert.assertEquals("Game results are not equal", result, actual);
        }
    }

    /**
     * Assert player's life count after test execution.
     *
     * @param player {@link Player} to get life for comparison.
     * @param life   Expected player's life to compare with.
     */
    @Override
    public void assertLife(Player player, int life) throws AssertionError {
        int actual = currentGame.getPlayer(player.getId()).getLife();
        Assert.assertEquals("Life amounts are not equal for player " + player.getName(), life, actual);
    }

    /**
     * Assert creature's power and toughness by card name.
     * <p/>
     * Throws {@link AssertionError} in the following cases: 1. no such player
     * 2. no such creature under player's control 3. depending on comparison
     * scope: 3a. any: no creature under player's control with the specified p\t
     * params 3b. all: there is at least one creature with the cardName with the
     * different p\t params
     *
     * @param player    {@link Player} to get creatures for comparison.
     * @param cardName  Card name to compare with.
     * @param power     Expected power to compare with.
     * @param toughness Expected toughness to compare with.
     * @param scope     {@link mage.filter.Filter.ComparisonScope} Use ANY, if
     *                  you want "at least one creature with given name should
     *                  have specified p\t" Use ALL, if you want "all creature
     *                  with gived name should have specified p\t"
     */
    @Override
    public void assertPowerToughness(Player player, String cardName, int power, int toughness, Filter.ComparisonScope scope)
            throws AssertionError {
        //Assert.assertNotEquals("", cardName);
        int count = 0;
        int fit = 0;
        int foundPower = 0;
        int foundToughness = 0;
        int found = 0;
        for (Permanent permanent : currentGame.getBattlefield().getAllPermanents()) {
            if (isObjectHaveTargetNameOrAlias(player, permanent, cardName) && permanent.getControllerId().equals(player.getId())) {
                count++;
                if (scope == Filter.ComparisonScope.All) {
                    Assert.assertEquals("Power is not the same (" + power + " vs. " + permanent.getPower().getValue() + ')',
                            power, permanent.getPower().getValue());
                    Assert.assertEquals("Toughness is not the same (" + toughness + " vs. " + permanent.getToughness().getValue() + ')',
                            toughness, permanent.getToughness().getValue());
                } else if (scope == Filter.ComparisonScope.Any) {
                    if (power == permanent.getPower().getValue() && toughness == permanent.getToughness().getValue()) {
                        fit++;
                        break;
                    }
                    found++;
                    foundPower = permanent.getPower().getValue();
                    foundToughness = permanent.getToughness().getValue();
                }
            }
        }

        assertTrue("There is no such permanent under player's control, player=" + player.getName()
                + ", cardName=" + cardName, count > 0);

        if (scope == Filter.ComparisonScope.Any) {
            assertTrue("There is no such creature under player's control with specified p/t of " + power + '/' + toughness + ", player=" + player.getName()
                    + ", cardName=" + cardName + " (found similar: " + found + ", one of them: power=" + foundPower + " toughness=" + foundToughness + ')', fit > 0);
        }
    }

    /**
     * See
     * {@link #assertPowerToughness(mage.players.Player, String, int, int, mage.filter.Filter.ComparisonScope)}
     *
     * @param player
     * @param cardName
     * @param power
     * @param toughness
     */
    public void assertPowerToughness(Player player, String cardName, int power, int toughness) {
        assertPowerToughness(player, cardName, power, toughness, Filter.ComparisonScope.Any);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void assertAbilities(Player player, String cardName, List<Ability> abilities)
            throws AssertionError {
        //Assert.assertNotEquals("", cardName);
        int count = 0;
        Permanent found = null;
        for (Permanent permanent : currentGame.getBattlefield().getAllActivePermanents(player.getId())) {
            if (isObjectHaveTargetNameOrAlias(player, permanent, cardName)) {
                found = permanent;
                count++;
            }
        }

        Assert.assertNotNull("There is no such permanent under player's control, player=" + player.getName()
                + ", cardName=" + cardName, found);

        assertTrue("There is more than one such permanent under player's control, player=" + player.getName()
                + ", cardName=" + cardName, count == 1);

        for (Ability ability : abilities) {
            assertTrue("No such ability=" + ability.toString() + ", player=" + player.getName()
                    + ", cardName" + cardName, found.getAbilities().contains(ability));
        }
    }

    public void assertAbility(Player player, String cardName, Ability ability, boolean mustHave) throws AssertionError {
        assertAbility(player, cardName, ability, mustHave, 1);
    }

    /**
     * @param player
     * @param cardName
     * @param ability
     * @param mustHave true if creature should contain ability, false if it
     *                 should NOT contain it instead
     * @param count    number of permanents with that ability
     * @throws AssertionError
     */
    public void assertAbility(Player player, String cardName, Ability ability, boolean mustHave, int count) throws AssertionError {
        //Assert.assertNotEquals("", cardName);
        int foundCount = 0;
        Permanent found = null;
        for (Permanent permanent : currentGame.getBattlefield().getAllActivePermanents(player.getId())) {
            if (isObjectHaveTargetNameOrAlias(player, permanent, cardName)) {
                found = permanent;
                foundCount++;
            }
        }

        Assert.assertNotNull("There is no such permanent under player's control, player=" + player.getName()
                + ", cardName=" + cardName, found);

        assertTrue("There is another number (" + foundCount + ") as defined (" + count + ") of such permanents under player's control, player=" + player.getName()
                + ", cardName=" + cardName, count == foundCount);

        if (mustHave) {
            assertTrue("No such ability=" + ability.toString() + ", player=" + player.getName()
                    + ", cardName" + cardName, found.getAbilities(currentGame).containsRule(ability));
        } else {
            Assert.assertFalse("Card shouldn't have such ability=" + ability.toString() + ", player=" + player.getName()
                    + ", cardName" + cardName, found.getAbilities(currentGame).containsRule(ability));
        }
    }

    public void assertAbilityCount(Player player, String cardName, Class<? extends Ability> searchedAbility, int amount) {
        Permanent found = null;
        for (Permanent permanent : currentGame.getBattlefield().getAllActivePermanents(player.getId())) {
            if (isObjectHaveTargetNameOrAlias(player, permanent, cardName)) {
                found = permanent;
                break;
            }
        }
        Assert.assertNotNull("There is no such permanent under player's control, player=" + player.getName()
                + ", cardName=" + cardName, found);

        Assert.assertEquals(amount, found.getAbilities(currentGame).stream()
                .filter(a -> searchedAbility.isAssignableFrom(a.getClass())).collect(Collectors.toList()).size());
    }

    /**
     * Assert permanent count under player's control.
     *
     * @param player {@link Player} which permanents should be counted.
     * @param count  Expected count.
     */
    @Override
    public void assertPermanentCount(Player player, int count) throws AssertionError {
        int actualCount = 0;
        for (Permanent permanent : currentGame.getBattlefield().getAllPermanents()) {
            if (permanent.getControllerId().equals(player.getId())) {
                actualCount++;
            }
        }
        Assert.assertEquals("(Battlefield) Card counts are not equal ", count, actualCount);
    }

    /**
     * Assert permanent count under player's control.
     *
     * @param player   {@link Player} which permanents should be counted.
     * @param cardName Name of the cards that should be counted.
     * @param count    Expected count.
     */
    @Override
    public void assertPermanentCount(Player player, String cardName, int count) throws AssertionError {
        //Assert.assertNotEquals("", cardName);
        int actualCount = 0;
        for (Permanent permanent : currentGame.getBattlefield().getAllActivePermanents()) {
            if (permanent.getControllerId().equals(player.getId())) {
                if (isObjectHaveTargetNameOrAlias(player, permanent, cardName)) {
                    actualCount++;
                }
            }
        }
        Assert.assertEquals("(Battlefield) Permanents counts for " + player.getName() + " are not equal (" + cardName + ')', count, actualCount);
    }

    @Override
    public void assertCommandZoneCount(Player player, String commandZoneObjectName, int count) throws AssertionError {
        //Assert.assertNotEquals("", commandZoneObjectName);
        int actualCount = 0;
        for (CommandObject commandObject : currentGame.getState().getCommand()) {
            if (commandObject.getControllerId().equals(player.getId()) && isObjectHaveTargetNameOrAlias(player, commandObject, commandZoneObjectName)) {
                actualCount++;
            }
        }
        Assert.assertEquals("(Command Zone) Card counts are not equal (" + commandZoneObjectName + ')', count, actualCount);
    }

    /**
     * Assert emblem count under player's control
     *
     * @param player
     * @param count
     * @throws AssertionError
     */
    @Override
    public void assertEmblemCount(Player player, int count) throws AssertionError {
        int actualCount = 0;
        for (CommandObject commandObject : currentGame.getState().getCommand()) {
            if (commandObject.getControllerId().equals(player.getId())) {
                actualCount++;
            }
        }
        Assert.assertEquals("Emblem counts are not equal", count, actualCount);
    }

    /**
     * Assert counter count on a permanent
     *
     * @param cardName Name of the cards that should be counted.
     * @param type     Type of the counter that should be counted.
     * @param count    Expected count.
     */
    public void assertCounterCount(String cardName, CounterType type, int count) throws AssertionError {
        this.assertCounterCount(null, cardName, type, count);
    }

    public void assertCounterCount(Player player, String cardName, CounterType type, int count) throws AssertionError {
        //Assert.assertNotEquals("", cardName);
        Permanent found = null;
        for (Permanent permanent : currentGame.getBattlefield().getAllActivePermanents()) {
            if (isObjectHaveTargetNameOrAlias(player, permanent, cardName) && (player == null || permanent.getControllerId().equals(player.getId()))) {
                found = permanent;
                break;
            }
        }
        Assert.assertNotNull("There is no such permanent " + (player == null ? "" : "for player " + player.getName()) + " on the battlefield, cardName=" + cardName, found);
        Assert.assertEquals("(Battlefield) Counter counts are not equal (" + cardName + ':' + type + ')', count, found.getCounters(currentGame).getCount(type));
    }

    /**
     * Assert counter count on a card in exile
     *
     * @param cardName Name of the cards that should be counted.
     * @param type     Type of the counter that should be counted.
     * @param count    Expected count.
     */
    public void assertCounterOnExiledCardCount(String cardName, CounterType type, int count) throws AssertionError {
        //Assert.assertNotEquals("", cardName);
        Card found = null;

        if (found == null) {
            for (Card card : currentGame.getExile().getAllCards(currentGame)) {
                if (CardUtil.haveSameNames(card.getName(), cardName, true)) {
                    found = card;
                    break;
                }
            }

        }
        Assert.assertNotNull("There is no such card in the exile, cardName=" + cardName, found);
        Assert.assertEquals("(Exile) Counter counts are not equal (" + cardName + ':' + type + ')', count, found.getCounters(currentGame).getCount(type));
    }

    /**
     * Assert counter count on a player
     *
     * @param player The player whos counters should be counted.
     * @param type   Type of the counter that should be counted.
     * @param count  Expected count.
     */
    public void assertCounterCount(Player player, CounterType type, int count) throws AssertionError {
        Assert.assertEquals("(Battlefield) Counter counts are not equal (" + player.getName() + ':' + type + ')', count, player.getCounters().getCount(type));
    }

    /**
     * Assert whether a permanent is a specified type or not
     *
     * @param cardName Name of the permanent that should be checked.
     * @param type     A type to test for
     * @param mustHave true if creature should have type, false if it should not
     */
    public void assertType(String cardName, CardType type, boolean mustHave) throws AssertionError {
        //Assert.assertNotEquals("", cardName);
        assertAliaseSupportInActivateCommand(cardName, false);
        Permanent found = null;
        for (Permanent permanent : currentGame.getBattlefield().getAllActivePermanents()) {
            if (permanent.getName().equals(cardName)) {
                found = permanent;
                break;
            }
        }

        Assert.assertNotNull("There is no such permanent on the battlefield, cardName=" + cardName, found);

        assertTrue("(Battlefield) card type " + (mustHave ? "not " : "")
                + "found (" + cardName + ':' + type + ')', (found.getCardType(currentGame).contains(type) == mustHave));

    }

    /**
     * Assert whether a permanent is a specified type
     *
     * @param cardName Name of the permanent that should be checked.
     * @param type     A type to test for
     * @param subType  a subtype to test for
     */
    public void assertType(String cardName, CardType type, SubType subType) throws AssertionError {
        //Assert.assertNotEquals("", cardName);
        Permanent found = getPermanent(cardName);
        assertTrue("(Battlefield) card type not found (" + cardName + ':' + type + ')', found.getCardType(currentGame).contains(type));
        if (subType != null) {
            assertTrue("(Battlefield) card sub-type not equal (" + cardName + ':' + subType.getDescription() + ')', found.hasSubtype(subType, currentGame));
        }
    }

    /**
     * Assert whether a permanent is not a specified type
     *
     * @param cardName Name of the permanent that should be checked.
     * @param type     A type to test for
     */
    public void assertNotType(String cardName, CardType type) throws AssertionError {
        //Assert.assertNotEquals("", cardName);
        Permanent found = getPermanent(cardName);
        Assert.assertFalse("(Battlefield) card type found (" + cardName + ':' + type + ')', found.getCardType(currentGame).contains(type));
    }

    /**
     * Assert whether a permanent is not a specified subtype
     *
     * @param cardName Name of the permanent that should be checked.
     * @param subType  a subtype to test for
     */
    public void assertNotSubtype(String cardName, SubType subType) throws AssertionError {
        //Assert.assertNotEquals("", cardName);
        Permanent found = getPermanent(cardName);
        if (subType != null) {
            Assert.assertFalse("(Battlefield) card sub-type equal (" + cardName + ':' + subType.getDescription() + ')', found.hasSubtype(subType, currentGame));
        }
    }

    /**
     * Assert whether a permanent is a specified subtype
     *
     * @param cardName Name of the permanent that should be checked.
     * @param subType  a subtype to test for
     */
    public void assertSubtype(String cardName, SubType subType) throws AssertionError {
        //Assert.assertNotEquals("", cardName);
        Permanent found = getPermanent(cardName);
        if (subType != null) {
            assertTrue("(Battlefield) card sub-type equal (" + cardName + ':' + subType.getDescription() + ')', found.hasSubtype(subType, currentGame));
        }
    }

    /**
     * Assert permanent color
     *
     * @param player       player to check
     * @param cardName     card name on battlefield from player
     * @param searchColors colors list with searchable values
     * @param mustHave     must or not must have that colors
     */
    public void assertColor(Player player, String cardName, ObjectColor searchColors, boolean mustHave) {
        //Assert.assertNotEquals("", cardName);
        Assert.assertNotEquals("must setup colors to search", 0, searchColors.getColorCount());

        Permanent card = getPermanent(cardName, player);
        ObjectColor cardColor = card.getColor(currentGame);

        List<ObjectColor> colorsHave = new ArrayList<>();
        List<ObjectColor> colorsDontHave = new ArrayList<>();

        for (ObjectColor searchColor : searchColors.getColors()) {
            if (cardColor.shares(searchColor)) {
                colorsHave.add(searchColor);
            } else {
                colorsDontHave.add(searchColor);
            }
        }

        if (mustHave) {
            Assert.assertEquals("must contain colors [" + searchColors + "] but found only [" + cardColor.toString() + "]", 0, colorsDontHave.size());
        } else {
            Assert.assertEquals("must not contain colors [" + searchColors + "] but found [" + cardColor.toString() + "]", 0, colorsHave.size());
        }
    }

    public void assertColor(Player player, String cardName, String searchColors, boolean mustHave) {
        assertColor(player, cardName, new ObjectColor(searchColors), mustHave);
    }

    /**
     * Assert whether a permanent is tapped or not
     *
     * @param cardName Name of the permanent that should be checked.
     * @param tapped   Whether the permanent is tapped or not
     */
    public void assertTapped(String cardName, boolean tapped) throws AssertionError {
        //Assert.assertNotEquals("", cardName);
        assertAliaseSupportInActivateCommand(cardName, true);
        Permanent found = null;
        for (Permanent permanent : currentGame.getBattlefield().getAllActivePermanents()) {
            // workaround to find player
            if (isObjectHaveTargetNameOrAlias(currentGame.getPlayer(permanent.getControllerId()), permanent, cardName)) {
                if (found == null) {
                    found = permanent;
                } else if (tapped != found.isTapped()) { // try to find a not correct permanent
                    found = permanent;
                    break;
                }
            }
        }

        Assert.assertNotNull("There is no such permanent on the battlefield, cardName=" + cardName, found);

        Assert.assertEquals("(Battlefield) Tapped state is not equal (" + cardName + ')', tapped, found.isTapped());
    }

    /**
     * Assert whether X permanents of the same name are tapped or not.
     *
     * @param cardName Name of the permanent that should be checked.
     * @param tapped   Whether the permanent is tapped or not
     * @param count    The amount of this permanents that should be tapped
     */
    public void assertTappedCount(String cardName, boolean tapped, int count) throws AssertionError {
        //Assert.assertNotEquals("", cardName);
        assertAliaseSupportInActivateCommand(cardName, false);
        int tappedAmount = 0;
        Permanent found = null;
        for (Permanent permanent : currentGame.getBattlefield().getAllActivePermanents()) {
            if (permanent.getName().equals(cardName)) {
                if (permanent.isTapped() == tapped) {
                    tappedAmount++;
                }
                found = permanent;
            }
        }
        Assert.assertNotNull("There is no such permanent on the battlefield, cardName=" + cardName, found);
        Assert.assertEquals("(Battlefield) " + count + " permanents (" + cardName + ") are not " + ((tapped) ? "" : "un") + "tapped.", count, tappedAmount);
    }

    /**
     * Assert whether a permanent is attacking or not
     *
     * @param cardName  Name of the permanent that should be checked.
     * @param attacking Whether the permanent is attacking or not
     */
    public void assertAttacking(String cardName, boolean attacking) throws AssertionError {
        //Assert.assertNotEquals("", cardName);
        assertAliaseSupportInActivateCommand(cardName, false);
        Permanent found = null;
        for (Permanent permanent : currentGame.getBattlefield().getAllActivePermanents()) {
            if (permanent.getName().equals(cardName)) {
                found = permanent;
            }
        }

        Assert.assertNotNull("There is no such permanent on the battlefield, cardName=" + cardName, found);

        Assert.assertEquals("(Battlefield) Attacking state is not equal (" + cardName + ')', attacking, found.isAttacking());
    }

    /**
     * Assert card count in player's hand.
     *
     * @param player {@link Player} who's hand should be counted.
     * @param count  Expected count.
     */
    public void assertHandCount(Player player, int count) throws AssertionError {
        int actual = currentGame.getPlayer(player.getId()).getHand().size();
        Assert.assertEquals("(Hand " + player.getName() + ") Card counts are not equal ", count, actual);
    }

    /**
     * Assert card count in player's hand.
     *
     * @param player   {@link Player} who's hand should be counted.
     * @param cardName Name of the cards that should be counted.
     * @param count    Expected count.
     */
    public void assertHandCount(Player player, String cardName, int count) throws AssertionError {
        //Assert.assertNotEquals("", cardName);
        int actual;
        if (cardName.contains("//")) { // special logic for cheched split cards, because in game logic of card name filtering is different than for test
            actual = 0;
            for (Card card : currentGame.getPlayer(player.getId()).getHand().getCards(currentGame)) {
                if (CardUtil.haveSameNames(card.getName(), cardName, true)) {
                    actual++;
                }
            }
        } else {
            FilterCard filter = new FilterCard();
            filter.add(new NamePredicate(cardName, true)); // must find any cards even without names
            actual = currentGame.getPlayer(player.getId()).getHand().count(filter, player.getId(), currentGame);
        }
        Assert.assertEquals("(Hand) Card counts for card " + cardName + " for " + player.getName() + " are not equal ", count, actual);
    }

    public void assertManaPool(Player player, ManaType color, int amount) {
        ManaPool manaPool = currentGame.getPlayer(player.getId()).getManaPool();
        switch (color) {
            case COLORLESS:
                Assert.assertEquals(amount, manaPool.getColorless() + manaPool.getConditionalMana().stream().mapToInt(Mana::getColorless).sum());
                break;
            case RED:
                Assert.assertEquals(amount, manaPool.getRed() + manaPool.getConditionalMana().stream().mapToInt(Mana::getRed).sum());
                break;
            case BLUE:
                Assert.assertEquals(amount, manaPool.getBlue() + manaPool.getConditionalMana().stream().mapToInt(Mana::getBlue).sum());
                break;
            case WHITE:
                Assert.assertEquals(amount, manaPool.getWhite() + manaPool.getConditionalMana().stream().mapToInt(Mana::getWhite).sum());
                break;
            case GREEN:
                Assert.assertEquals(amount, manaPool.getGreen() + manaPool.getConditionalMana().stream().mapToInt(Mana::getGreen).sum());
                break;
            case BLACK:
                Assert.assertEquals(amount, manaPool.getBlack() + manaPool.getConditionalMana().stream().mapToInt(Mana::getBlack).sum());
                break;
        }
    }

    /**
     * Assert card count in player's graveyard.
     *
     * @param player {@link Player} who's graveyard should be counted.
     * @param count  Expected count.
     */
    public void assertGraveyardCount(Player player, int count) throws AssertionError {
        int actual = currentGame.getPlayer(player.getId()).getGraveyard().size();
        Assert.assertEquals("(Graveyard) Card counts are not equal ", count, actual);
    }

    /**
     * Assert card subtype in exile.
     *
     * @param cardName Name of the card.
     * @param subType  Expected subtype.
     */
    public void assertExiledCardSubtype(String cardName, SubType subType) throws AssertionError {
        boolean found = false;
        for (ExileZone exile : currentGame.getExile().getExileZones()) {
            for (Card card : exile.getCards(currentGame)) {
                if (CardUtil.haveSameNames(card.getName(), cardName, true) && card.hasSubtype(subType, currentGame)) {
                    found = true;
                }
            }
        }

        assertTrue("There is no card named " + cardName + " found in exile, with subtype " + subType, found);
    }

    /**
     * Assert card count in exile.
     *
     * @param cardName Name of the cards that should be counted.
     * @param count    Expected count.
     */
    public void assertExileCount(String cardName, int count) throws AssertionError {
        //Assert.assertNotEquals("", cardName);
        int actualCount = 0;
        for (ExileZone exile : currentGame.getExile().getExileZones()) {
            for (Card card : exile.getCards(currentGame)) {
                if (CardUtil.haveSameNames(card.getName(), cardName, true)) {
                    actualCount++;
                }
            }
        }

        Assert.assertEquals("(Exile) Card counts are not equal (" + cardName + ')', count, actualCount);
    }

    /**
     * Assert card count in exile by owner.
     *
     * @param owner player that owns the cards.
     * @param count Expected count.
     */
    public void assertExileCount(Player owner, int count) throws AssertionError {
        int actualCount = 0;
        for (ExileZone exile : currentGame.getExile().getExileZones()) {
            for (Card card : exile.getCards(currentGame)) {
                if (card.isOwnedBy(owner.getId())) {
                    actualCount++;
                }
            }
        }

        Assert.assertEquals("(Exile) Card counts for player " + owner.getName() + " is not equal.", count, actualCount);
    }

    /**
     * Assert card count in player's exile.
     *
     * @param owner    {@link Player} who's exile should be counted.
     * @param cardName Name of the cards that should be counted.
     * @param count    Expected count.
     */
    public void assertExileCount(Player owner, String cardName, int count) throws AssertionError {
        //Assert.assertNotEquals("", cardName);
        assertAliaseSupportInActivateCommand(cardName, false);
        int actualCount = 0;
        for (ExileZone exile : currentGame.getExile().getExileZones()) {
            for (Card card : exile.getCards(currentGame)) {
                if (card.isOwnedBy(owner.getId()) && card.getName().equals(cardName)) {
                    actualCount++;
                }
            }
        }
        Assert.assertEquals("(Exile " + owner.getName() + ") Card counts are not equal (" + cardName + ')', count, actualCount);
    }

    /**
     * Assert card count in player's graveyard.
     *
     * @param player   {@link Player} who's graveyard should be counted.
     * @param cardName Name of the cards that should be counted.
     * @param count    Expected count.
     */
    public void assertGraveyardCount(Player player, String cardName, int count) throws AssertionError {
        assertAliaseSupportInActivateCommand(cardName, true);
        //Assert.assertNotEquals("", cardName);
        int actualCount = 0;
        for (Card card : player.getGraveyard().getCards(currentGame)) {
            if (isObjectHaveTargetNameOrAlias(player, card, cardName)) {
                actualCount++;
            }
        }

        Assert.assertEquals("(Graveyard " + player.getName() + ") Card counts are not equal (" + cardName + ')', count, actualCount);
    }

    /**
     * Assert library card count.
     *
     * @param player {@link Player} who's library should be counted.
     * @param count  Expected count.
     */
    public void assertLibraryCount(Player player, int count) throws AssertionError {
        List<Card> libraryList = player.getLibrary().getCards(currentGame);
        int actualCount = libraryList != null && !libraryList.isEmpty() ? libraryList.size() : 0;
        Assert.assertEquals("(Library " + player.getName() + ") counts are not equal", count, actualCount);
    }

    /**
     * Assert specific card count in player's library.
     *
     * @param player   {@link Player} who's library should be counted.
     * @param cardName Name of the cards that should be counted.
     * @param count    Expected count.
     */
    public void assertLibraryCount(Player player, String cardName, int count) throws AssertionError {
        //Assert.assertNotEquals("", cardName);
        int actualCount = 0;
        for (Card card : player.getLibrary().getCards(currentGame)) {
            if (CardUtil.haveSameNames(card.getName(), cardName, true)) {
                actualCount++;
            }
        }

        Assert.assertEquals("(Library " + player.getName() + ") Card counts are not equal (" + cardName + ')', count, actualCount);
    }

    public void assertActionsCount(TestPlayer player, int count) throws AssertionError {
        Assert.assertEquals("(Actions of " + player.getName() + ") Count are not equal (found ["
                + player.getActions().stream().map(PlayerAction::getAction).collect(Collectors.joining(", "))
                + "])", count, player.getActions().size());
    }

    public void assertActionsMustBeEmpty(TestPlayer player) throws AssertionError {
        if (!player.getActions().isEmpty()) {
            System.out.println("Remaining actions for " + player.getName() + " (" + player.getActions().size() + "):");
            player.getActions().stream().forEach(a -> {
                System.out.println("* turn " + a.getTurnNum() + " - " + a.getStep() + ": " + (a.getActionName().isEmpty() ? a.getAction() : a.getActionName()));
            });
            Assert.fail("Player " + player.getName() + " must have 0 actions but found " + player.getActions().size());
        }
    }

    public void assertChoicesCount(TestPlayer player, int count) throws AssertionError {
        String mes = String.format(
                "(Choices of %s) Count are not equal (found %s). Some inner choose dialogs can be set up only in strict mode.",
                player.getName(),
                player.getChoices()
        );
        Assert.assertEquals(mes, count, player.getChoices().size());
    }

    public void assertTargetsCount(TestPlayer player, int count) throws AssertionError {
        String mes = String.format(
                "(Targets of %s) Count are not equal (found %s). Some inner choose dialogs can be set up only in strict mode.",
                player.getName(),
                player.getTargets()
        );
        Assert.assertEquals(mes, count, player.getTargets().size());
    }

    /**
     * Raise error on any unused commands, choices or targets If you want to
     * test that ability can't be activated then use call checkPlayableAbility()
     *
     * @throws AssertionError
     */
    public void assertAllCommandsUsed() throws AssertionError {
        for (Player player : currentGame.getPlayers().values()) {
            TestPlayer testPlayer = (TestPlayer) player;
            assertActionsMustBeEmpty(testPlayer);
            assertChoicesCount(testPlayer, 0);
            assertTargetsCount(testPlayer, 0);
        }
    }

    public void assertActivePlayer(TestPlayer player) {
        Assert.assertEquals("message", currentGame.getState().getActivePlayerId(), player.getId());
    }

    public void assertTopCardRevealed(TestPlayer player, boolean isRevealed) {
        Assert.assertEquals(isRevealed, player.isTopCardRevealed());
    }

    public void assertIsAttachedTo(TestPlayer thePlayer, String theAttachment, String thePermanent) {

        List<Permanent> permanents = currentGame.getBattlefield().getAllActivePermanents().stream()
                .filter(permanent -> permanent.isControlledBy(thePlayer.getId()))
                .filter(permanent -> permanent.getName().equals(thePermanent))
                .collect(Collectors.toList());
       assertTrue(theAttachment + " was not attached to " + thePermanent,
               permanents.stream()
               .anyMatch(permanent -> permanent.getAttachments()
                       .stream()
                       .map(id -> currentGame.getCard(id))
                       .map(MageObject::getName)
                       .collect(Collectors.toList()).contains(theAttachment)));


    }

    public Permanent getPermanent(String cardName, UUID controller) {
        assertAliaseSupportInActivateCommand(cardName, false);
        Permanent found = null;
        Pattern indexedName = Pattern.compile("^([\\w| ]+):(\\d+)$"); // Ends with <:number>
        Matcher indexedMatcher = indexedName.matcher(cardName);
        int index = 0;
        int count = 0;
        if (indexedMatcher.matches()) {
            cardName = indexedMatcher.group(1);
            index = Integer.valueOf(indexedMatcher.group(2));
        }
        for (Permanent permanent : currentGame.getBattlefield().getAllActivePermanents()) {
            if (permanent.getName().equals(cardName)) {
                if (controller == null || permanent.getControllerId().equals(controller)) {
                    found = permanent;
                    if (count != index) {
                        count++;
                    }
                }
            }
        }
        Assert.assertNotNull("Couldn't find a card with specified name: " + cardName, found);
        Assert.assertEquals("Only " + count + " permanents were found and " + cardName + ":" + index + " was requested", index, count);
        return found;
    }

    public Permanent getPermanent(String cardName, Player player) {
        return getPermanent(cardName, player.getId());
    }

    public Permanent getPermanent(String cardName) {
        return getPermanent(cardName, (UUID) null);
    }

    public void playLand(int turnNum, PhaseStep step, TestPlayer player, String cardName) {
        //Assert.assertNotEquals("", cardName);
        assertAliaseSupportInActivateCommand(cardName, false);
        addPlayerAction(player, turnNum, step, ACTIVATE_PLAY + cardName);
    }

    public void castSpell(int turnNum, PhaseStep step, TestPlayer player, String cardName) {
        //Assert.assertNotEquals("", cardName);
        assertAliaseSupportInActivateCommand(cardName, false);
        addPlayerAction(player, turnNum, step, ACTIVATE_CAST + cardName);
    }

    public void castSpell(int turnNum, PhaseStep step, TestPlayer player, String cardName, Player target) {
        //Assert.assertNotEquals("", cardName);
        // warning, target in spell cast command setups without choose target call
        assertAliaseSupportInActivateCommand(cardName, false);
        addPlayerAction(player, turnNum, step, ACTIVATE_CAST + cardName + "$targetPlayer=" + target.getName());
    }

    public void castSpell(int turnNum, PhaseStep step, TestPlayer player, String cardName, Player target, int manaInPool) {
        //Assert.assertNotEquals("", cardName);
        assertAliaseSupportInActivateCommand(cardName, false);
        addPlayerAction(player, turnNum, step, ACTIVATE_CAST + cardName + "$targetPlayer=" + target.getName() + "$manaInPool=" + manaInPool);
    }

    /**
     * AI play one PRIORITY with multi game simulations like real game
     * (calcs and play ONE best action, can be called with stack)
     * All choices must be made by AI (e.g.strict mode possible)
     *
     * @param turnNum
     * @param step
     * @param player
     */
    public void aiPlayPriority(int turnNum, PhaseStep step, TestPlayer player) {
        assertAiPlayAndGameCompatible(player);
        addPlayerAction(player, createAIPlayerAction(turnNum, step, AI_COMMAND_PLAY_PRIORITY));
    }

    /**
     * AI play STEP to the end with multi game simulations (calcs and play best
     * actions until step ends, can be called in the middle of the step) All
     * choices must be made by AI (e.g. strict mode possible)
     */
    public void aiPlayStep(int turnNum, PhaseStep step, TestPlayer player) {
        assertAiPlayAndGameCompatible(player);
        addPlayerAction(player, createAIPlayerAction(turnNum, step, AI_COMMAND_PLAY_STEP));
    }

    public PlayerAction createAIPlayerAction(int turnNum, PhaseStep step, String aiCommand) {
        // AI commands must disable and enable real game simulation and strict mode
        return new PlayerAction("", turnNum, step, AI_PREFIX + aiCommand) {
            @Override
            public void onActionRemovedLater(Game game, TestPlayer player) {
                player.setAIRealGameSimulation(false);
            }
        };
    }

    private void assertAiPlayAndGameCompatible(TestPlayer player) {
        boolean aiCompatible = (player.getComputerPlayer() instanceof ComputerPlayer7 || player.getComputerPlayer() instanceof ComputerPlayerMCTS);
        if (player.isAIPlayer() || !aiCompatible) {
            Assert.fail("AI commands supported by CardTestPlayerBaseWith***AIHelps only");
        }
    }

    public void waitStackResolved(int turnNum, PhaseStep step, TestPlayer player) {
        waitStackResolved(1, step, player, false);
    }

    public void waitStackResolved(int turnNum, PhaseStep step, TestPlayer player, boolean skipOneStackObjectOnly) {
        String command = "waitStackResolved" + (skipOneStackObjectOnly ? ":1" : "");
        addPlayerAction(player, turnNum, step, command);
    }

    /**
     * Rollback the number of given turns: 0 = rollback to the start of the
     * current turn. Use the commands rollbackAfterActionsStart() and
     * rollbackAfterActionsEnd() to define a block of actions, that will be
     * added and executed after the rollback.
     *
     * @param turnNum
     * @param step
     * @param player
     * @param turns
     */
    public void rollbackTurns(int turnNum, PhaseStep step, TestPlayer player, int turns) {
        rollbackBlock++;
        addPlayerAction(player, turnNum, step, "playerAction:Rollback" + "$turns=" + turns + "$rollbackBlock=" + rollbackBlock);
        rollbackPlayer = player;
    }

    /**
     * Adds a number of actions that will be added to the to the start of the
     * list of actions of the players but only after the rollback is executed
     * because otherwis the actions are executed to early and would lead to
     * invalid actions (e.g. casting the same spell twice).
     */
    public void rollbackAfterActionsStart() throws IllegalStateException {
        if (rollbackPlayer == null || rollbackBlock < 1) {
            throw new IllegalStateException("There was no rollback action defined before. You can use this command only after a rollback action.");
        }
        rollbackBlockActive = true;
    }

    /**
     * Ends a block of actions to be added after an rollback action
     */
    public void rollbackAfterActionsEnd() throws IllegalStateException {
        if (rollbackBlockActive = false || rollbackPlayer == null) {
            throw new IllegalStateException("There was no rollback action defined before or no rollback block started. You can use this command only after a rollback action.");
        }
        rollbackBlockActive = false;
        rollbackPlayer = null;
    }

    /**
     * The player concedes at the given turn and phase
     *
     * @param turnNum
     * @param step
     * @param player
     */
    public void concede(int turnNum, PhaseStep step, TestPlayer player) {
        addPlayerAction(player, turnNum, step, "playerAction:Concede");
    }

    /**
     * @param turnNum
     * @param step
     * @param player
     * @param cardName
     * @param targetName for modes you can add "mode=3" before target name;
     *                   multiple targets can be seperated by ^;
     *                   no target marks as TestPlayer.NO_TARGET;
     *                   warning, do not support cards with target adjusters - use addTarget instead
     */
    public void castSpell(int turnNum, PhaseStep step, TestPlayer player, String cardName, String targetName) {
        //Assert.assertNotEquals("", cardName);
        assertAliaseSupportInActivateCommand(cardName, true);
        assertAliaseSupportInActivateCommand(targetName, true);
        addPlayerAction(player, turnNum, step, ACTIVATE_CAST + cardName + "$target=" + targetName);
    }

    public enum StackClause {
        WHILE_ON_STACK,
        WHILE_COPY_ON_STACK,
        WHILE_NOT_ON_STACK
    }

    /**
     * Spell will only be cast, if a spell / ability with the given name is on
     * the stack
     *
     * @param turnNum
     * @param step
     * @param player
     * @param cardName
     * @param targetName   for modal spells add the mode to the name e.g.
     *                     "mode=2SilvercoatLion^mode3=PillarfieldOx"
     * @param spellOnStack
     */
    public void castSpell(int turnNum, PhaseStep step, TestPlayer player, String cardName, String targetName, String spellOnStack) {
        castSpell(turnNum, step, player, cardName, targetName, spellOnStack, StackClause.WHILE_ON_STACK);
    }

    /**
     * Spell will only be cast, if a spell / ability with the given name IS or
     * IS NOT on the stack
     *
     * @param turnNum
     * @param step
     * @param player
     * @param cardName
     * @param targetName
     * @param spellOnStack
     * @param clause
     */
    public void castSpell(int turnNum, PhaseStep step, TestPlayer player, String cardName, String targetName, String spellOnStack, StackClause clause) {
        //Assert.assertNotEquals("", cardName);
        assertAliaseSupportInActivateCommand(cardName, true);
        assertAliaseSupportInActivateCommand(targetName, true);
        assertAliaseSupportInActivateCommand(spellOnStack, false);

        String targetInfo = (targetName != null && targetName.startsWith("target") ? targetName : "target=" + targetName);
        switch (clause) {
            case WHILE_ON_STACK:
                addPlayerAction(player, turnNum, step, ACTIVATE_CAST + cardName
                        + '$' + targetInfo
                        + "$spellOnStack=" + spellOnStack);
                break;
            case WHILE_COPY_ON_STACK:
                addPlayerAction(player, turnNum, step, ACTIVATE_CAST + cardName
                        + '$' + targetInfo
                        + "$spellCopyOnStack=" + spellOnStack);
                break;
            case WHILE_NOT_ON_STACK:
                addPlayerAction(player, turnNum, step, ACTIVATE_CAST + cardName
                        + '$' + targetInfo
                        + "$!spellOnStack=" + spellOnStack);
                break;
            default:
                throw new IllegalArgumentException("Unknown stack clause " + clause);
        }
    }

    public void castSpell(int turnNum, PhaseStep step, TestPlayer player, String cardName, String targetName, String spellOnStack, String spellOnTopOfStack) {
        //Assert.assertNotEquals("", cardName);
        assertAliaseSupportInActivateCommand(cardName, false);
        assertAliaseSupportInActivateCommand(targetName, false);
        assertAliaseSupportInActivateCommand(spellOnStack, false);
        assertAliaseSupportInActivateCommand(spellOnTopOfStack, false);
        String action = ACTIVATE_CAST + cardName + "$target=" + targetName;
        if (spellOnStack != null && !spellOnStack.isEmpty()) {
            action += "$spellOnStack=" + spellOnStack;
        }
        if (spellOnTopOfStack != null && !spellOnTopOfStack.isEmpty()) {
            action += "$spellOnTopOfStack=" + spellOnTopOfStack;
        }
        addPlayerAction(player, turnNum, step, action);
    }

    public void activateManaAbility(int turnNum, PhaseStep step, TestPlayer player, String ability) {
        activateManaAbility(turnNum, step, player, ability, 1);
    }

    public void activateManaAbility(int turnNum, PhaseStep step, TestPlayer player, String ability, int timesToActivate) {
        for (int i = 0; i < timesToActivate; i++) {
            addPlayerAction(player, turnNum, step, ACTIVATE_MANA + ability);
        }
    }

    public void activateAbility(int turnNum, PhaseStep step, TestPlayer player, String ability) {
        // TODO: it's uses computerPlayer to execute, only ability target will work, but choices and targets commands aren't
        assertAliaseSupportInActivateCommand(ability, true);
        addPlayerAction(player, turnNum, step, ACTIVATE_ABILITY + ability);
    }

    public void activateAbility(int turnNum, PhaseStep step, TestPlayer player, String ability, Player target) {
        // TODO: it's uses computerPlayer to execute, only ability target will work, but choices and targets commands aren't
        assertAliaseSupportInActivateCommand(ability, true);
        addPlayerAction(player, turnNum, step, ACTIVATE_ABILITY + ability + "$targetPlayer=" + target.getName());
    }

    public void activateAbility(int turnNum, PhaseStep step, TestPlayer player, String ability, String... targetNames) {
        // TODO: it's uses computerPlayer to execute, only ability target will work, but choices and targets commands aren't
        assertAliaseSupportInActivateCommand(ability, true);
        Arrays.stream(targetNames).forEach(n -> {
            assertAliaseSupportInActivateCommand(n, true);
        });
        addPlayerAction(player, turnNum, step, ACTIVATE_ABILITY + ability + "$target=" + String.join("^", targetNames));
    }

    /**
     * @param turnNum
     * @param step
     * @param player
     * @param ability
     * @param targetName   use NO_TARGET if there is no target to set
     * @param spellOnStack
     */
    public void activateAbility(int turnNum, PhaseStep step, TestPlayer player, String ability, String targetName, String spellOnStack) {
        // TODO: it's uses computerPlayer to execute, only ability target will work, but choices and targets commands aren't
        this.activateAbility(turnNum, step, player, ability, targetName, spellOnStack, StackClause.WHILE_ON_STACK);
    }

    /**
     * @param turnNum
     * @param step
     * @param player
     * @param ability
     * @param targetName   if not target has to be defined use the constant
     *                     NO_TARGET
     * @param spellOnStack
     * @param clause
     */
    public void activateAbility(int turnNum, PhaseStep step, TestPlayer player, String ability, String targetName, String spellOnStack, StackClause clause) {
        assertAliaseSupportInActivateCommand(ability, true);
        assertAliaseSupportInActivateCommand(targetName, true);
        StringBuilder sb = new StringBuilder(ACTIVATE_ABILITY).append(ability);
        if (targetName != null && !targetName.isEmpty()) {
            sb.append("$target=").append(targetName);
        }
        if (spellOnStack != null && !spellOnStack.isEmpty()) {
            sb.append('$');
            switch (clause) {
                case WHILE_ON_STACK:
                    sb.append("spellOnStack=");
                    break;
                case WHILE_NOT_ON_STACK:
                    sb.append("!spellOnStack=");
                    break;
                case WHILE_COPY_ON_STACK:
                    sb.append("spellCopyOnStack=");
                    break;
            }
            sb.append(spellOnStack);
        }
        addPlayerAction(player, turnNum, step, sb.toString());
    }

    public void addCounters(int turnNum, PhaseStep step, TestPlayer player, String cardName, CounterType type, int count) {
        //Assert.assertNotEquals("", cardName);
        addPlayerAction(player, turnNum, step, "addCounters:" + cardName + '$' + type.getName() + '$' + count);
    }

    public void attack(int turnNum, TestPlayer player, String attacker) {
        //Assert.assertNotEquals("", attacker);
        assertAliaseSupportInActivateCommand(attacker, false); // it uses old special notation like card_name:index
        addPlayerAction(player, turnNum, PhaseStep.DECLARE_ATTACKERS, "attack:" + attacker);
    }

    public void attack(int turnNum, TestPlayer player, String attacker, TestPlayer defendingPlayer) {
        //Assert.assertNotEquals("", attacker);
        assertAliaseSupportInActivateCommand(attacker, false); // it uses old special notation like card_name:index
        addPlayerAction(player, turnNum, PhaseStep.DECLARE_ATTACKERS, "attack:" + attacker + "$defendingPlayer=" + defendingPlayer.getName());
    }

    public void attack(int turnNum, TestPlayer player, String attacker, String planeswalker) {
        //Assert.assertNotEquals("", attacker);
        assertAliaseSupportInActivateCommand(attacker, false); // it uses old special notation like card_name:index
        assertAliaseSupportInActivateCommand(planeswalker, false);
        addPlayerAction(player, turnNum, PhaseStep.DECLARE_ATTACKERS, new StringBuilder("attack:").append(attacker).append("$planeswalker=").append(planeswalker).toString());
    }

    public void attackSkip(int turnNum, TestPlayer player) {
        attack(turnNum, player, TestPlayer.ATTACK_SKIP);
    }

    public void block(int turnNum, TestPlayer player, String blocker, String attacker) {
        //Assert.assertNotEquals("", blocker);
        //Assert.assertNotEquals("", attacker);
        assertAliaseSupportInActivateCommand(blocker, false); // it uses old special notation like card_name:index
        assertAliaseSupportInActivateCommand(attacker, false);
        addPlayerAction(player, turnNum, PhaseStep.DECLARE_BLOCKERS, "block:" + blocker + '$' + attacker);
    }

    public void blockSkip(int turnNum, TestPlayer player) {
        block(turnNum, player, TestPlayer.BLOCK_SKIP, "");
    }

    /**
     * For use choices set "Yes" or "No" the the choice string or use boolean.<br>
     * For X values set "X=[xValue]" example: for X=3 set choice string to
     * "X=3".<br>
     * For ColorChoice use "Red", "Green", "Blue", "Black" or "White"<br>
     * use command setModeChoice if you have to set a mode from modal
     * ability<br>
     *
     * @param player
     * @param choice
     */
    public void setChoice(TestPlayer player, boolean choice) {
        setChoice(player, choice, 1);
    }

    public void setChoice(TestPlayer player, boolean choice, int timesToChoose) {
        setChoice(player, choice ? "Yes" : "No", timesToChoose);
    }

    public void setChoice(TestPlayer player, String choice) {
        setChoice(player, choice, 1);
    }

    public void setChoice(TestPlayer player, String choice, int timesToChoose) {
        for (int i = 0; i < timesToChoose; i++) {
            player.addChoice(choice);
        }
    }

    /**
     * Setup amount choices.
     * <p>
     * Multi amount choices uses WUBRG order (so use 1,2,3,4,5 values list)
     *
     * @param player
     * @param amountList
     */
    public void setChoiceAmount(TestPlayer player, int... amountList) {
        for (int amount : amountList) {
            setChoice(player, "X=" + amount);
        }
    }

    /**
     * Set the modes for modal spells
     *
     * @param player
     * @param choice starting with "1" for mode 1, "2" for mode 2 and so on (to
     *               set multiple modes call the command multiple times). If a
     *               spell mode can be used only once like Demonic Pact, the
     *               value has to be set to the number of the remaining modes
     *               (e.g. if only 2 are left the number need to be 1 or 2).
     */
    public void setModeChoice(TestPlayer player, String choice) {
        player.addModeChoice(choice);
    }

    /**
     * Set next result of next flipCoin's try (one flipCoin event can call multiple tries under some effects)
     * TestPlayer/ComputerPlayer always selects Heads in good winable events
     *
     * @param player
     * @param result
     */
    public void setFlipCoinResult(TestPlayer player, boolean result) {
        player.addChoice(result ? TestPlayer.FLIPCOIN_RESULT_TRUE : TestPlayer.FLIPCOIN_RESULT_FALSE);
    }

    /**
     * Set next result of next die roll (uses for both normal or planar rolls)
     *
     * For planar rolls:
     * 1..2 - chaos
     * 3..7 - blank
     * 8..9 - planar
     *
     * @param player
     * @param result
     */
    public void setDieRollResult(TestPlayer player, int result) {
        player.addChoice(TestPlayer.DIE_ROLL + result);
    }

    /**
     * Set target permanents
     *
     * @param player
     * @param target you can add multiple targets by separating them by the "^"
     *               character e.g. "creatureName1^creatureName2" you can
     *               qualify the target additional by setcode e.g.
     *               "creatureName-M15" you can add [no copy] to the end of the
     *               target name to prohibit targets that are copied you can add
     *               [only copy] to the end of the target name to allow only
     *               targets that are copies. For modal spells use a prefix with
     *               the mode number: mode=1Lightning Bolt^mode=2Silvercoat Lion
     */
    // TODO: mode options doesn't work here (see BrutalExpulsionTest)
    public void addTarget(TestPlayer player, String target) {
        addTarget(player, target, 1);
    }

    public void addTarget(TestPlayer player, String target, int timesToChoose) {
        for (int i = 0; i < timesToChoose; i++) {
            assertAliaseSupportInActivateCommand(target, true);
            player.addTarget(target);
        }
    }

    /**
     * Sets a player as target
     *
     * @param player
     * @param targetPlayer
     */
    public void addTarget(TestPlayer player, TestPlayer targetPlayer) {
        addTarget(player, targetPlayer, 1);
    }

    public void addTarget(TestPlayer player, TestPlayer targetPlayer, int timesToChoose) {
        for (int i = 0; i < timesToChoose; i++) {
            player.addTarget("targetPlayer=" + targetPlayer.getName());
        }
    }

    /**
     * @param player
     * @param target use TestPlayer.TARGET_SKIP to 0 targets selects or to stop
     *               "up two xxx" selection
     * @param amount
     */
    public void addTargetAmount(TestPlayer player, String target, int amount) {
        if (target.equals(TestPlayer.TARGET_SKIP)) {
            player.addTarget(target);
        } else {
            player.addTarget(target + "^X=" + amount);
        }
    }

    public void addTargetAmount(TestPlayer player, Player targetPlayer, int amount) {
        addTargetAmount(player, "targetPlayer=" + targetPlayer.getName(), amount);
    }

    public void addTargetAmount(TestPlayer player, String target) {
        assertTrue("Only skip command allows here", target.equals(TestPlayer.TARGET_SKIP));
        addTargetAmount(player, target, 0);
    }

    public void setDecknamePlayerA(String deckname) {
        deckNameA = deckname;
    }

    public void setDecknamePlayerB(String deckname) {
        deckNameB = deckname;
    }

    public void setDecknamePlayerC(String deckname) {
        deckNameC = deckname;
    }

    public void setDecknamePlayerD(String deckname) {
        deckNameD = deckname;
    }

    protected void skipInitShuffling() {
        gameOptions.skipInitShuffling = true;
    }

    public void assertDamageReceived(Player player, String cardName, int expected) {
        //Assert.assertNotEquals("", cardName);
        Permanent p = getPermanent(cardName, player);
        if (p != null) {
            Assert.assertEquals("Wrong damage received: ", expected, p.getDamage());
        }
    }

    public void waitStackResolved(int turnNum, PhaseStep step) {
        waitStackResolved(turnNum, step, false);
    }

    public void waitStackResolved(int turnNum, PhaseStep step, boolean skipOneStackObjectOnly) {
        if (playerA != null) {
            waitStackResolved(turnNum, step, playerA, skipOneStackObjectOnly);
        }
        if (playerB != null) {
            waitStackResolved(turnNum, step, playerB, skipOneStackObjectOnly);
        }
        if (playerC != null) {
            waitStackResolved(turnNum, step, playerC, skipOneStackObjectOnly);
        }
        if (playerD != null) {
            waitStackResolved(turnNum, step, playerD, skipOneStackObjectOnly);
        }
    }

    private void assertAliaseSupportInActivateCommand(String targetName, boolean methodSupportAliases) {
        // TODO: add alias support for all false methods (replace name compare by isObjectHaveTargetNameOrAliase in activate code)
        if (!methodSupportAliases) {
            if (targetName != null && targetName.contains(ALIAS_PREFIX)) {
                Assert.fail("That activate command do not support aliases, but found " + targetName);
            }
        }
    }

    private boolean isObjectHaveTargetNameOrAlias(Player player, MageObject object, String nameOrAlias) {
        TestPlayer testPlayer = (TestPlayer) player;
        if (player != null) { // TODO: remove null check and replace all null-player calls in tests by player
            return testPlayer.hasObjectTargetNameOrAlias(object, nameOrAlias);
        } else {
            return object.getName().equals(nameOrAlias);
        }
    }


    public void assertWonTheGame(Player player) {

        assertTrue(player.getName() + " has not won the game.", player.hasWon());
    }

    public void assertHasNotWonTheGame(Player player) {

        Assert.assertFalse(player.getName() + " has won the game.", player.hasWon());
    }

    public void assertLostTheGame(Player player) {

        assertTrue(player.getName() + " has not lost the game.", player.hasLost());
    }

    public void assertHasNotLostTheGame(Player player) {

        Assert.assertFalse(player.getName() + " has lost the game.", player.hasLost());
    }

    public GameView getGameView(Player player) {
        // prepare client-server data for tests
        return GameSessionPlayer.prepareGameView(currentGame, player.getId(), null);
    }
}
