package org.mage.test.serverside.base.impl;

import mage.abilities.Ability;
import mage.cards.Card;
import mage.cards.repository.CardInfo;
import mage.cards.repository.CardRepository;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.filter.Filter;
import mage.game.permanent.Permanent;
import mage.game.permanent.PermanentCard;
import mage.players.Player;
import org.junit.Assert;
import org.mage.test.player.TestPlayer;
import org.mage.test.serverside.base.CardTestAPI;
import org.mage.test.serverside.base.MageTestBase;

import java.util.List;
import java.util.UUID;

/**
 * API for test initialization and asserting the test results.
 *
 * @author ayratn
 */
public abstract class CardTestAPIImpl extends MageTestBase implements CardTestAPI {

    /**
     * Default game initialization params for red player (that plays with Mountains)
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
     * Removes all cards from player's library from the game.
     * Usually this should be used once before initialization to form the library in certain order.
     *
     * @param player {@link Player} to remove all library cards from.
     */
    public void removeAllCardsFromLibrary(Player player) {
        if (player.equals(playerA)) {
            commandsA.put(Zone.LIBRARY, "clear");
        } else if (player.equals(playerB)) {
            commandsB.put(Zone.LIBRARY, "clear");
        }
    }

    /**
     * Add a card to specified zone of specified player.
     *
     * @param gameZone {@link mage.constants.Zone} to add cards to.
     * @param player   {@link Player} to add cards for. Use either playerA or playerB.
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
     * @param player   {@link Player} to add cards for. Use either playerA or playerB.
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
     * @param player   {@link Player} to add cards for. Use either playerA or playerB.
     * @param cardName Card name in string format.
     * @param count    Amount of cards to be added.
     * @param tapped   In case gameZone is Battlefield, determines whether permanent should be tapped.
     *                 In case gameZone is other than Battlefield, {@link IllegalArgumentException} is thrown
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
                PermanentCard p = new PermanentCard(card, null, currentGame);
                p.setTapped(tapped);
                if (player.equals(playerA)) {
                    battlefieldCardsA.add(p);
                } else if (player.equals(playerB)) {
                    battlefieldCardsB.add(p);
                }
            }
        } else {
            if (tapped) {
                throw new IllegalArgumentException("Parameter tapped=true can be used only for Zone.BATTLEFIELD.");
            }
            List<Card> cards = getCardList(gameZone, player);
            for (int i = 0; i < count; i++) {
                CardInfo cardInfo = CardRepository.instance.findCard(cardName);
                Card card = cardInfo != null ? cardInfo.getCard() : null;
                cards.add(card);
            }
        }
    }

    /**
     * Returns card list containter for specified game zone and player.
     *
     * @param gameZone
     * @param player
     * @return
     */
    private List<Card> getCardList(Zone gameZone, Player player) {
        if (player.equals(playerA)) {
            if (gameZone == Zone.HAND) {
                return handCardsA;
            } else if (gameZone == Zone.GRAVEYARD) {
                return graveyardCardsA;
            } else if (gameZone == Zone.LIBRARY) {
                return libraryCardsA;
            }
        } else if (player.equals(playerB)) {
            if (gameZone == Zone.HAND) {
                return handCardsB;
            } else if (gameZone == Zone.GRAVEYARD) {
                return graveyardCardsB;
            } else if (gameZone == Zone.LIBRARY) {
                return libraryCardsB;
            }
        }
        return null;
    }

    /**
     * Set player's initial life count.
     *
     * @param player {@link Player} to set life count for.
     * @param life   Life count to set.
     */
    @Override
    public void setLife(TestPlayer player, int life) {
        if (player.equals(playerA)) {
            commandsA.put(Zone.OUTSIDE, "life:" + life);
        } else if (player.equals(playerB)) {
            commandsB.put(Zone.OUTSIDE, "life:" + life);
        }
    }

    /**
     * Define turn number to stop the game on.
     */
    @Override
    public void setStopOnTurn(int turn) {
        stopOnTurn = turn == -1 ? null : turn;
        stopAtStep = PhaseStep.UNTAP;
    }

    /**
     * Define turn number and step to stop the game on.
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
        Assert.assertEquals("Life amounts are not equal", life, actual);
    }

    /**
     * Assert creature's power and toughness by card name.
     * <p/>
     * Throws {@link AssertionError} in the following cases:
     * 1. no such player
     * 2. no such creature under player's control
     * 3. depending on comparison scope:
     * 3a. any: no creature under player's control with the specified p\t params
     * 3b. all: there is at least one creature with the cardName with the different p\t params
     *
     * @param player    {@link Player} to get creatures for comparison.
     * @param cardName  Card name to compare with.
     * @param power     Expected power to compare with.
     * @param toughness Expected toughness to compare with.
     * @param scope     {@link mage.filter.Filter.ComparisonScope} Use ANY, if you want "at least one creature with given name should have specified p\t"
     *                  Use ALL, if you want "all creature with gived name should have specified p\t"
     */
    @Override
    public void assertPowerToughness(Player player, String cardName, int power, int toughness, Filter.ComparisonScope scope)
            throws AssertionError {
        int count = 0;
        int fit = 0;
        for (Permanent permanent : currentGame.getBattlefield().getAllActivePermanents(player.getId())) {
            if (permanent.getName().equals(cardName)) {
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
                }
            }
        }

        Assert.assertTrue("There is no such permanent under player's control, player=" + player.getName() +
                ", cardName=" + cardName, count > 0);

        if (scope == Filter.ComparisonScope.Any) {
            Assert.assertTrue("There is no such creature under player's control with specified power&toughness, player=" + player.getName() +
                    ", cardName=" + cardName, fit > 0);
        }
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
            }
        }

        Assert.assertNotNull("There is no such permanent under player's control, player=" + player.getName() +
                ", cardName=" + cardName, found);

        Assert.assertTrue("There is more than one such permanent under player's control, player=" + player.getName() +
                ", cardName=" + cardName, count == 1);

        for (Ability ability : abilities) {
            Assert.assertTrue("No such ability=" + ability.toString() + ", player=" + player.getName() +
                    ", cardName=" + cardName, found.getAbilities().contains(ability));
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
        for (Permanent permanent : currentGame.getBattlefield().getAllPermanents()) {
            if (permanent.getControllerId().equals(player.getId())) {
                if (permanent.getName().equals(cardName)) {
                    actualCount++;
                }
            }
        }
        Assert.assertEquals("(Battlefield) Card counts are not equal (" + cardName + ')', count, actualCount);
    }

    public Permanent getPermanent(String cardName, UUID controller) {
        Permanent permanent0 = null;
        int count = 0;
        for (Permanent permanent : currentGame.getBattlefield().getAllPermanents()) {
            if (permanent.getControllerId().equals(controller)) {
                if (permanent.getName().equals(cardName)) {
                    permanent0 = permanent;
                    count++;
                }
            }
        }
        Assert.assertNotNull("Couldn't find a card with specified name: " + cardName, permanent0);
        Assert.assertEquals("More than one permanent was found: " + cardName + '(' + count + ')', 1, count);
        return permanent0;
    }

    public void playLand(Player player, String cardName) {
        player.addAction("play:" + cardName);
    }

    public void castSpell(Player player, String cardName) {
        player.addAction("cast:" + cardName);
    }

    public void addFixedTarget(Player player, String cardName, Player target) {
        player.addAction("cast:" + cardName + ";name=" + target.getName());
    }

    public void addFixedTarget(Player player, String cardName, String targetName) {
        player.addAction("cast:" + cardName + ";name=" + targetName);
    }

    public void useAbility(Player player, String cardName) {
    }

    public void attack(Player player, String cardName) {
        player.addAction("attack:" + cardName);
    }
}
