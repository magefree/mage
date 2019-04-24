package org.mage.test.serverside.base.impl;

import mage.MageInt;
import mage.Mana;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.cards.Card;
import mage.cards.decks.Deck;
import mage.cards.decks.importer.DeckImporterUtil;
import mage.cards.repository.CardInfo;
import mage.cards.repository.CardRepository;
import mage.cards.repository.CardScanner;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.Filter;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.game.ExileZone;
import mage.game.Game;
import mage.game.GameException;
import mage.game.GameOptions;
import mage.game.command.CommandObject;
import mage.game.permanent.Permanent;
import mage.game.permanent.PermanentCard;
import mage.players.ManaPool;
import mage.players.Player;
import org.junit.Assert;
import org.junit.Before;
import org.mage.test.player.TestPlayer;
import org.mage.test.serverside.base.CardTestAPI;
import org.mage.test.serverside.base.MageTestPlayerBase;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * API for test initialization and asserting the test results.
 *
 * @author ayratn
 */
public abstract class CardTestPlayerAPIImpl extends MageTestPlayerBase implements CardTestAPI {

    // Defines the constant if for activate ability is not target but a ability on the stack to define
    public static final String NO_TARGET = "NO_TARGET";

    public static final String CHECK_COMMAND_PT = "PT";
    public static final String CHECK_COMMAND_ABILITY = "ABILITY";
    public static final String CHECK_COMMAND_PERMANENT_COUNT = "PERMANENT_COUNT";
    public static final String CHECK_COMMAND_HAND_COUNT = "HAND_COUNT";
    public static final String CHECK_COMMAND_COLOR = "COLOR";
    public static final String CHECK_COMMAND_SUBTYPE = "SUBTYPE";
    public static final String CHECK_COMMAND_MANA_POOL = "MANA_POOL";

    protected GameOptions gameOptions;

    protected String deckNameA;
    protected String deckNameB;
    protected String deckNameC;
    protected String deckNameD;

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

    @Before
    public void reset() throws GameException, FileNotFoundException {
        if (currentGame != null) {
            logger.debug("Resetting previous game and creating new one!");
            currentGame = null;
        }

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

    }

    abstract protected Game createNewGameAndPlayers() throws GameException, FileNotFoundException;

    protected TestPlayer createPlayer(Game game, TestPlayer player, String name) throws GameException {
        return createPlayer(game, player, name, "RB Aggro.dck");
    }

    protected TestPlayer createPlayer(Game game, TestPlayer player, String name, String deckName) throws GameException {
        player = createNewPlayer(name, game.getRangeOfInfluence());
        player.setTestMode(true);
        logger.debug("Loading deck...");
        Deck deck = Deck.load(DeckImporterUtil.importDeck(deckName), false, false);
        logger.debug("Done!");
        if (deck.getCards().size() < 40) {
            throw new IllegalArgumentException("Couldn't load deck, deck size=" + deck.getCards().size());
        }
        game.loadCards(deck.getCards(), player.getId());
        game.loadCards(deck.getSideboard(), player.getId());
        game.addPlayer(player, deck);

        return player;
    }

    public void execute() throws IllegalStateException {
        if (currentGame == null || activePlayer == null) {
            throw new IllegalStateException("Game is not initialized. Use load method to load a test case and initialize a game.");
        }

        for (Player player : currentGame.getPlayers().values()) {
            TestPlayer testPlayer = (TestPlayer) player;
            currentGame.cheat(player.getId(), getCommands(testPlayer));
            currentGame.cheat(player.getId(), getLibraryCards(testPlayer), getHandCards(testPlayer),
                    getBattlefieldCards(testPlayer), getGraveCards(testPlayer));
        }

        long t1 = System.nanoTime();

        gameOptions.testMode = true;
        gameOptions.stopOnTurn = stopOnTurn;
        gameOptions.stopAtStep = stopAtStep;
        currentGame.setGameOptions(gameOptions);
        currentGame.start(activePlayer.getId());
        long t2 = System.nanoTime();
        logger.debug("Winner: " + currentGame.getWinner());
        logger.info("Test has been executed. Execution time: " + (t2 - t1) / 1000000 + " ms");

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

    private void check(String checkName, int turnNum, PhaseStep step, TestPlayer player, String command, String... params) {
        String res = "check:" + command;
        for (String param : params) {
            res += "@" + param;
        }
        player.addAction(checkName, turnNum, step, res);
    }

    public void checkPT(String checkName, int turnNum, PhaseStep step, TestPlayer player, String permanentName, Integer power, Integer toughness) {
        check(checkName, turnNum, step, player, CHECK_COMMAND_PT, permanentName, power.toString(), toughness.toString());
    }

    public void checkAbility(String checkName, int turnNum, PhaseStep step, TestPlayer player, String permanentName, Class<?> abilityClass, Boolean mustHave) {
        check(checkName, turnNum, step, player, CHECK_COMMAND_ABILITY, permanentName, abilityClass.getName(), mustHave.toString());
    }

    public void checkPermanentCount(String checkName, int turnNum, PhaseStep step, TestPlayer player, String permanentName, Integer count) {
        check(checkName, turnNum, step, player, CHECK_COMMAND_PERMANENT_COUNT, permanentName, count.toString());
    }

    public void checkHandCount(String checkName, int turnNum, PhaseStep step, TestPlayer player, Integer count) {
        check(checkName, turnNum, step, player, CHECK_COMMAND_HAND_COUNT, count.toString());
    }

    public void checkColor(String checkName, int turnNum, PhaseStep step, TestPlayer player, String permanentName, String colors, Boolean mustHave) {
        check(checkName, turnNum, step, player, CHECK_COMMAND_COLOR, permanentName, colors, mustHave.toString());
    }

    public void checkSubType(String checkName, int turnNum, PhaseStep step, TestPlayer player, String permanentName, SubType subType, Boolean mustHave) {
        check(checkName, turnNum, step, player, CHECK_COMMAND_SUBTYPE, permanentName, subType.toString(), mustHave.toString());
    }

    public void checkManaPool(String checkName, int turnNum, PhaseStep step, TestPlayer player, String colors, Integer amount) {
        check(checkName, turnNum, step, player, CHECK_COMMAND_MANA_POOL, colors, amount.toString());
    }

    /**
     * Removes all cards from player's library from the game. Usually this
     * should be used once before initialization to form the library in certain
     * order.
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
    public void removeAllCardsFromHand(TestPlayer player) {
        getCommands(player).put(Zone.HAND, "clear");
    }

    /**
     * Add a card to specified zone of specified player.
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
     *                 permanent should be tapped. In case gameZone is other than Battlefield,
     *                 {@link IllegalArgumentException} is thrown
     */
    @Override
    public void addCard(Zone gameZone, TestPlayer player, String cardName, int count, boolean tapped) {

        if (gameZone == Zone.BATTLEFIELD) {
            for (int i = 0; i < count; i++) {
                CardInfo cardInfo = CardRepository.instance.findCard(cardName);
                Card card = cardInfo != null ? cardInfo.getCard() : null;
                if (card == null) {
                    throw new IllegalArgumentException("[TEST] Couldn't find a card: " + cardName);
                }
                PermanentCard p = new PermanentCard(card.copy(), player.getId(), currentGame);
                p.setTapped(tapped);
                getBattlefieldCards(player).add(p);
            }
        } else {
            if (tapped) {
                throw new IllegalArgumentException("Parameter tapped=true can be used only for Zone.BATTLEFIELD.");
            }
            List<Card> cards = getCardList(gameZone, player);
            for (int i = 0; i < count; i++) {
                CardInfo cardInfo = CardRepository.instance.findCard(cardName);
                Card card = cardInfo != null ? cardInfo.getCard() : null;
                if (card == null) {
                    throw new AssertionError("Couldn't find a card: " + cardName);
                }
                cards.add(card);
            }
        }
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
        getCommands(player).put(Zone.OUTSIDE, "life:" + String.valueOf(life));
    }

    /**
     * Define turn number to stop the game on.
     *
     * @param turn
     */
    @Override
    public void setStopOnTurn(int turn) {
        stopOnTurn = turn == -1 ? null : turn;
        stopAtStep = PhaseStep.UNTAP;
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
        stopOnTurn = turn == -1 ? null : turn;
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
     * @param scope     {@link mage.filter.Filter.ComparisonScope} Use ANY, if you
     *                  want "at least one creature with given name should have specified p\t"
     *                  Use ALL, if you want "all creature with gived name should have specified
     *                  p\t"
     */
    @Override
    public void assertPowerToughness(Player player, String cardName, int power, int toughness, Filter.ComparisonScope scope)
            throws AssertionError {
        int count = 0;
        int fit = 0;
        int foundPower = 0;
        int foundToughness = 0;
        int found = 0;
        for (Permanent permanent : currentGame.getBattlefield().getAllPermanents()) {

            if (permanent.getName().equals(cardName) && permanent.getControllerId().equals(player.getId())) {
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

        Assert.assertTrue("There is no such permanent under player's control, player=" + player.getName()
                + ", cardName=" + cardName, count > 0);

        if (scope == Filter.ComparisonScope.Any) {
            Assert.assertTrue("There is no such creature under player's control with specified p/t of " + power + '/' + toughness + ", player=" + player.getName()
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
        int count = 0;
        Permanent found = null;
        for (Permanent permanent : currentGame.getBattlefield().getAllActivePermanents(player.getId())) {
            if (permanent.getName().equals(cardName)) {
                found = permanent;
                count++;
            }
        }

        Assert.assertNotNull("There is no such permanent under player's control, player=" + player.getName()
                + ", cardName=" + cardName, found);

        Assert.assertTrue("There is more than one such permanent under player's control, player=" + player.getName()
                + ", cardName=" + cardName, count == 1);

        for (Ability ability : abilities) {
            Assert.assertTrue("No such ability=" + ability.toString() + ", player=" + player.getName()
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
     * @param mustHave true if creature should contain ability, false if it should
     *                 NOT contain it instead
     * @param count    number of permanents with that ability
     * @throws AssertionError
     */
    public void assertAbility(Player player, String cardName, Ability ability, boolean mustHave, int count) throws AssertionError {
        int foundCount = 0;
        Permanent found = null;
        for (Permanent permanent : currentGame.getBattlefield().getAllActivePermanents(player.getId())) {
            if (permanent.getName().equals(cardName)) {
                found = permanent;
                foundCount++;
            }
        }

        Assert.assertNotNull("There is no such permanent under player's control, player=" + player.getName()
                + ", cardName=" + cardName, found);

        Assert.assertTrue("There is another number (" + foundCount + ") as defined (" + count + ") of such permanents under player's control, player=" + player.getName()
                + ", cardName=" + cardName, count == foundCount);

        if (mustHave) {
            Assert.assertTrue("No such ability=" + ability.toString() + ", player=" + player.getName()
                    + ", cardName" + cardName, found.getAbilities(currentGame).containsRule(ability));
        } else {
            Assert.assertFalse("Card shouldn't have such ability=" + ability.toString() + ", player=" + player.getName()
                    + ", cardName" + cardName, found.getAbilities(currentGame).containsRule(ability));
        }
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
        int actualCount = 0;
        for (Permanent permanent : currentGame.getBattlefield().getAllActivePermanents()) {
            if (permanent.getControllerId().equals(player.getId())) {
                if (permanent.getName().equals(cardName)) {
                    actualCount++;
                }
            }
        }
        Assert.assertEquals("(Battlefield) Permanents counts for " + player.getName() + " are not equal (" + cardName + ')', count, actualCount);
    }

    @Override
    public void assertCommandZoneCount(Player player, String commandZoneObjectName, int count) throws AssertionError {
        int actualCount = 0;
        for (CommandObject commandObject : currentGame.getState().getCommand()) {
            if (commandObject.getControllerId().equals(player.getId()) && commandObject.getName().equals(commandZoneObjectName)) {
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
        Permanent found = null;
        for (Permanent permanent : currentGame.getBattlefield().getAllActivePermanents()) {
            if (permanent.getName().equals(cardName) && (player == null || permanent.getControllerId().equals(player.getId()))) {
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
        Card found = null;

        if (found == null) {
            for (Card card : currentGame.getExile().getAllCards(currentGame)) {
                if (card.getName().equals(cardName)) {
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
        Permanent found = null;
        for (Permanent permanent : currentGame.getBattlefield().getAllActivePermanents()) {
            if (permanent.getName().equals(cardName)) {
                found = permanent;
                break;
            }
        }

        Assert.assertNotNull("There is no such permanent on the battlefield, cardName=" + cardName, found);

        Assert.assertTrue("(Battlefield) card type not found (" + cardName + ':' + type + ')', (found.getCardType().contains(type) == mustHave));

    }

    /**
     * Assert whether a permanent is a specified type
     *
     * @param cardName Name of the permanent that should be checked.
     * @param type     A type to test for
     * @param subType  a subtype to test for
     */
    public void assertType(String cardName, CardType type, SubType subType) throws AssertionError {
        Permanent found = getPermanent(cardName);
        Assert.assertTrue("(Battlefield) card type not found (" + cardName + ':' + type + ')', found.getCardType().contains(type));
        if (subType != null) {
            Assert.assertTrue("(Battlefield) card sub-type not equal (" + cardName + ':' + subType.getDescription() + ')', found.getSubtype(currentGame).contains(subType));
        }
    }

    /**
     * Assert whether a permanent is not a specified type
     *
     * @param cardName Name of the permanent that should be checked.
     * @param type     A type to test for
     */
    public void assertNotType(String cardName, CardType type) throws AssertionError {
        Permanent found = getPermanent(cardName);
        Assert.assertFalse("(Battlefield) card type found (" + cardName + ':' + type + ')', found.getCardType().contains(type));
    }

    /**
     * Assert whether a permanent is not a specified subtype
     *
     * @param cardName Name of the permanent that should be checked.
     * @param subType  a subtype to test for
     */
    public void assertNotSubtype(String cardName, SubType subType) throws AssertionError {
        Permanent found = getPermanent(cardName);
        if (subType != null) {
            Assert.assertFalse("(Battlefield) card sub-type equal (" + cardName + ':' + subType.getDescription() + ')', found.getSubtype(currentGame).contains(subType));
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
            Assert.assertEquals("must contain colors [" + searchColors.toString() + "] but found only [" + cardColor.toString() + "]", 0, colorsDontHave.size());
        } else {
            Assert.assertEquals("must not contain colors [" + searchColors.toString() + "] but found [" + cardColor.toString() + "]", 0, colorsHave.size());
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
        Permanent found = null;
        for (Permanent permanent : currentGame.getBattlefield().getAllActivePermanents()) {
            if (permanent.getName().equals(cardName)) {
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
        int actual;
        if (cardName.contains("//")) { // special logic for cheched split cards, because in game logic of card name filtering is different than for test
            actual = 0;
            for (Card card : currentGame.getPlayer(player.getId()).getHand().getCards(currentGame)) {
                if (card.getName().equals(cardName)) {
                    actual++;
                }
            }
        } else {
            FilterCard filter = new FilterCard();
            filter.add(new NamePredicate(cardName));
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
     * Assert card count in exile.
     *
     * @param cardName Name of the cards that should be counted.
     * @param count    Expected count.
     */
    public void assertExileCount(String cardName, int count) throws AssertionError {
        int actualCount = 0;
        for (ExileZone exile : currentGame.getExile().getExileZones()) {
            for (Card card : exile.getCards(currentGame)) {
                if (card.getName().equals(cardName)) {
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
        int actualCount = 0;
        for (Card card : player.getGraveyard().getCards(currentGame)) {
            if (card.getName().equals(cardName)) {
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
        int actualCount = 0;
        for (Card card : player.getLibrary().getCards(currentGame)) {
            if (card.getName().equals(cardName)) {
                actualCount++;
            }
        }

        Assert.assertEquals("(Library " + player.getName() + ") Card counts are not equal (" + cardName + ')', count, actualCount);
    }

    /**
     * Asserts added actions count. Useful to make sure that all actions were
     * executed.
     *
     * @param player
     * @param count
     */
    public void assertActionCount(TestPlayer player, int count) {
        Assert.assertEquals("Actions left are not equal: ", count, player.getActionCount());
    }

    public void assertActivePlayer(TestPlayer player) {
        Assert.assertEquals("message", currentGame.getState().getActivePlayerId(), player.getId());
    }

    public Permanent getPermanent(String cardName, UUID controller) {
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
        player.addAction(turnNum, step, "activate:Play " + cardName);
    }

    public void castSpell(int turnNum, PhaseStep step, TestPlayer player, String cardName) {
        player.addAction(turnNum, step, "activate:Cast " + cardName);
    }

    public void castSpell(int turnNum, PhaseStep step, TestPlayer player, String cardName, Player target) {
        player.addAction(turnNum, step, "activate:Cast " + cardName + "$targetPlayer=" + target.getName());
    }

    public void castSpell(int turnNum, PhaseStep step, TestPlayer player, String cardName, Player target, int manaInPool) {
        player.addAction(turnNum, step, "activate:Cast " + cardName + "$targetPlayer=" + target.getName() + "$manaInPool=" + manaInPool);
    }

    /**
     * Rollback the number of given turns: 0 = rollback to the start of the
     * current turn
     *
     * @param turnNum
     * @param step
     * @param player
     * @param turns
     */
    public void rollbackTurns(int turnNum, PhaseStep step, TestPlayer player, int turns) {
        player.addAction(turnNum, step, "playerAction:Rollback" + "$turns=" + turns);
    }

    /**
     * The player concedes at the given turn and phase
     *
     * @param turnNum
     * @param step
     * @param player
     */
    public void concede(int turnNum, PhaseStep step, TestPlayer player) {
        player.addAction(turnNum, step, "playerAction:Concede");
    }

    /**
     * @param turnNum
     * @param step
     * @param player
     * @param cardName
     * @param targetName for modes you can add "mode=3" before target name,
     *                   multiple targets can be seperated by ^
     */
    public void castSpell(int turnNum, PhaseStep step, TestPlayer player, String cardName, String targetName) {
        player.addAction(turnNum, step, "activate:Cast " + cardName + "$target=" + targetName);
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
        if (StackClause.WHILE_ON_STACK == clause) {
            player.addAction(turnNum, step, "activate:Cast " + cardName
                    + '$' + (targetName != null && targetName.startsWith("target") ? targetName : "target=" + targetName)
                    + "$spellOnStack=" + spellOnStack);
        } else {
            player.addAction(turnNum, step, "activate:Cast " + cardName
                    + '$' + (targetName != null && targetName.startsWith("target") ? targetName : "target=" + targetName)
                    + "$!spellOnStack=" + spellOnStack);
        }
    }

    public void castSpell(int turnNum, PhaseStep step, TestPlayer player, String cardName, String targetName, String spellOnStack, String spellOnTopOfStack) {
        String action = "activate:Cast " + cardName + "$target=" + targetName;
        if (spellOnStack != null && !spellOnStack.isEmpty()) {
            action += "$spellOnStack=" + spellOnStack;
        }
        if (spellOnTopOfStack != null && !spellOnTopOfStack.isEmpty()) {
            action += "$spellOnTopOfStack=" + spellOnTopOfStack;
        }
        player.addAction(turnNum, step, action);
    }

    public void activateManaAbility(int turnNum, PhaseStep step, TestPlayer player, String ability) {
        player.addAction(turnNum, step, "manaActivate:" + ability);
    }

    public void activateAbility(int turnNum, PhaseStep step, TestPlayer player, String ability) {
        player.addAction(turnNum, step, "activate:" + ability);
    }

    public void activateAbility(int turnNum, PhaseStep step, TestPlayer player, String ability, Player target) {
        player.addAction(turnNum, step, "activate:" + ability + "$targetPlayer=" + target.getName());
    }

    public void activateAbility(int turnNum, PhaseStep step, TestPlayer player, String ability, String... targetNames) {
        player.addAction(turnNum, step, "activate:" + ability + "$target=" + String.join("^", targetNames));
    }

    public void activateAbility(int turnNum, PhaseStep step, TestPlayer player, String ability, String targetName, String spellOnStack) {
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
        StringBuilder sb = new StringBuilder("activate:").append(ability);
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
        player.addAction(turnNum, step, sb.toString());
    }

    public void addCounters(int turnNum, PhaseStep step, TestPlayer player, String cardName, CounterType type, int count) {
        player.addAction(turnNum, step, "addCounters:" + cardName + '$' + type.getName() + '$' + count);
    }

    public void attack(int turnNum, TestPlayer player, String attacker) {
        player.addAction(turnNum, PhaseStep.DECLARE_ATTACKERS, "attack:" + attacker);
    }

    public void attack(int turnNum, TestPlayer player, String attacker, TestPlayer defendingPlayer) {
        player.addAction(turnNum, PhaseStep.DECLARE_ATTACKERS, "attack:" + attacker + "$defendingPlayer=" + defendingPlayer.getName());
    }

    public void attack(int turnNum, TestPlayer player, String attacker, String planeswalker) {
        player.addAction(turnNum, PhaseStep.DECLARE_ATTACKERS, new StringBuilder("attack:").append(attacker).append("$planeswalker=").append(planeswalker).toString());
    }

    public void block(int turnNum, TestPlayer player, String blocker, String attacker) {
        player.addAction(turnNum, PhaseStep.DECLARE_BLOCKERS, "block:" + blocker + '$' + attacker);
    }

    /**
     * For use choices set "Yes" or "No" the the choice string. For X values set
     * "X=[xValue]" example: for X=3 set choice string to "X=3".
     *
     * @param player
     * @param choice
     */
    public void setChoice(TestPlayer player, String choice) {
        player.addChoice(choice);
    }

    /**
     * Set the modes for modal spells
     *
     * @param player
     * @param choice starting with "1" for mode 1, "2" for mode 2 and so on (to
     *               set multiple modes call the command multiple times). If a spell mode can
     *               be used only once like Demonic Pact, the value has to be set to the
     *               number of the remaining modes (e.g. if only 2 are left the number need to
     *               be 1 or 2).
     */
    public void setModeChoice(TestPlayer player, String choice) {
        player.addModeChoice(choice);
    }

    /**
     * Set target permanents
     *
     * @param player
     * @param target you can add multiple targets by separating them by the "^"
     *               character e.g. "creatureName1^creatureName2" you can qualify the target
     *               additional by setcode e.g. "creatureName-M15" you can add [no copy] to
     *               the end of the target name to prohibit targets that are copied you can
     *               add [only copy] to the end of the target name to allow only targets that
     *               are copies. For modal spells use a prefix with the mode number:
     *               mode=1Lightning Bolt^mode=2Silvercoat Lion
     */
    // TODO: mode options doesn't work here (see BrutalExpulsionTest)
    public void addTarget(TestPlayer player, String target) {
        player.addTarget(target);
    }

    /**
     * Sets a player as target
     *
     * @param player
     * @param targetPlayer
     */
    public void addTarget(TestPlayer player, TestPlayer targetPlayer) {
        player.addTarget("targetPlayer=" + targetPlayer.getName());
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

    protected void checkPermanentPT(Player player, String cardName, int power, int toughness, Filter.ComparisonScope scope) {
        if (currentGame == null) {
            throw new IllegalStateException("Current game is null");
        }
        if (scope == Filter.ComparisonScope.All) {
            throw new UnsupportedOperationException("ComparisonScope.All is not implemented.");
        }

        for (Permanent permanent : currentGame.getBattlefield().getAllActivePermanents(player.getId())) {
            if (permanent.getName().equals(cardName)) {
                Assert.assertEquals("Power is not the same", power, permanent.getPower().getValue());
                Assert.assertEquals("Toughness is not the same", toughness, permanent.getToughness().getValue());
                break;
            }
        }
    }

    public void assertDamageReceived(Player player, String cardName, int expected) {
        Permanent p = getPermanent(cardName, player.getId());
        if (p != null) {
            Assert.assertEquals("Wrong damage received: ", expected, p.getDamage());
        }
    }
}
