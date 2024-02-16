package org.mage.test.serverside.base;

import mage.abilities.Ability;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.filter.Filter;
import mage.players.Player;
import org.mage.test.player.TestPlayer;

import java.util.List;

/**
 * Interface for all test initialization and assertion operations.
 */
public interface CardTestAPI {

    /**
     * Types of game result.
     */
    public enum GameResult {

        WON,
        LOST,
        DRAW
    }

    /**
     * Removes all cards from player's library from the game. Usually this
     * should be used once before initialization to form the library in certain
     * order.
     *
     * @param player {@link Player} to remove all library cards from.
     */
    void removeAllCardsFromLibrary(TestPlayer player);

    /**
     * Add a card to specified zone of specified player.
     *
     * @param gameZone {@link mage.constants.Zone} to add cards to.
     * @param player {@link Player} to add cards for. Use either playerA or
     * playerB.
     * @param cardName Card name in string format.
     */
    void addCard(Zone gameZone, TestPlayer player, String cardName);

    /**
     * Add any amount of cards to specified zone of specified player.
     *
     * @param gameZone {@link mage.constants.Zone} to add cards to.
     * @param player {@link Player} to add cards for. Use either playerA or
     * playerB.
     * @param cardName Card name in string format.
     * @param count Amount of cards to be added.
     */
    void addCard(Zone gameZone, TestPlayer player, String cardName, int count);

    /**
     * Add any amount of cards to specified zone of specified player.
     *
     * @param gameZone {@link mage.constants.Zone} to add cards to.
     * @param player {@link Player} to add cards for. Use either playerA or
     * playerB.
     * @param cardName Card name in string format.
     * @param count Amount of cards to be added.
     * @param tapped In case gameZone is Battlefield, determines whether
     * permanent should be tapped. In case gameZone is other than Battlefield,
     * {@link IllegalArgumentException} is thrown
     */
    void addCard(Zone gameZone, TestPlayer player, String cardName, int count, boolean tapped);

    /**
     * Set player's initial life count.
     *
     * @param player {@link Player} to set life count for.
     * @param life Life count to set.
     */
    void setLife(TestPlayer player, int life);

    //******* GAME OPTIONS *******/
    /**
     * Define turn number to stop the game on.
     *
     * @param turn
     */
    void setStopOnTurn(int turn);

    /**
     * Define the turn number and step to stop the game on.
     *
     * @param turn
     * @param step
     */
    void setStopAt(int turn, PhaseStep step);

    //******* ASSERT METHODS *******/
    /**
     * Assert turn number after test execution.
     *
     * @param turn Expected turn number to compare with.
     */
    void assertTurn(int turn) throws AssertionError;

    /**
     * Assert game result for the player after test execution.
     *
     * @param player {@link Player} to get game result for.
     * @param result Expected {@link GameResult} to compare with.
     */
    void assertResult(Player player, GameResult result) throws AssertionError;

    /**
     * Assert player's life count after test execution.
     *
     * @param player {@link Player} to get life for comparison.
     * @param life Expected player's life to compare with.
     */
    void assertLife(Player player, int life) throws AssertionError;

    /**
     *
     * Assert creature's power and toughness by card name.
     * <p/>
     * Throws {@link AssertionError} in the following cases:
     * 1. no such player
     * 2. no such creature under player's control
     * 3. depending on comparison scope:
     *      3a. any: no creature under player's control with the specified p\t params
     *      3b. all: there is at least one creature with the cardName with the different p\t params
     *
     * @param player    {@link Player} to get creatures for comparison.
     * @param cardName  Card name to compare with.
     * @param power     Expected power to compare with.
     * @param toughness Expected toughness to compare with.
     * @param scope     {@link mage.filter.Filter.ComparisonScope} Use ANY, if
     *                  you want "at least one creature with given name should
     *                  have specified p\t" Use ALL, if you want "all creature
     *                  with given name should have specified p\t"
     * @param base      If true, the comparison looks that base power and toughness of the creature.
     *                  If false, the comparison looks at the final values.
     */
    public void assertPowerToughness(Player player, String cardName, int powerNeeded, int toughnessNeeded, Filter.ComparisonScope scope, boolean checkBaseValues) throws AssertionError;

    /**
     * Assert creature's abilities.
     * <p/>
     * Throws {@link AssertionError} in the following cases: 1. no such player
     * 2. no such creature under player's control 3. there is more than one
     * creature with such name
     *
     * @param player {@link Player} to get creatures for comparison.
     * @param cardName Card name to compare with.
     * @param abilities Expected abilities
     */
    void assertAbilities(Player player, String cardName, List<Ability> abilities)
            throws AssertionError;

    /**
     * Assert permanent count under player's control.
     *
     * @param player {@link Player} which permanents should be counted.
     * @param count Expected count.
     */
    void assertPermanentCount(Player player, int count) throws AssertionError;

    /**
     * Assert permanent count under player's control.
     *
     * @param player {@link Player} which permanents should be counted.
     * @param cardName Name of the cards that should be counted.
     * @param count Expected count.
     */
    void assertPermanentCount(Player player, String cardName, int count) throws AssertionError;

    /**
     * Assert token count under player's control.
     *
     * @param player {@link Player} which permanents should be counted.
     * @param tokenName Name of the tokens that should be counted.
     * @param count Expected count.
     */
    void assertTokenCount(Player player, String tokenName, int count) throws AssertionError;

    /**
     * Assert command zone object count in player's command zone
     *
     * @param player {@link Player} which permanents should be counted.
     * @param commandZoneObjectName Name of the commander or emblem that should
     * be counted.
     * @param count Expected count.
     */
    void assertCommandZoneCount(Player player, String commandZoneObjectName, int count) throws AssertionError;

    /**
     * Assert emblem count under player's control
     *
     * @param player
     * @param count
     * @throws AssertionError
     */
    void assertEmblemCount(Player player, int count) throws AssertionError;
}
