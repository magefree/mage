package org.mage.test.cards.facedown;

import mage.cards.Card;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author BetaSteward
 */
public class BaneAlleyBrokerTest extends CardTestPlayerBase {

    /**
     * Bane Alley Broker Creature — Human Rogue 0/3, 1UB (3) {T}: Draw a card,
     * then exile a card from your hand face down. You may look at cards exiled
     * with Bane Alley Broker. {U}{B}, {T}: Return a card exiled with Bane Alley
     * Broker to its owner's hand.
     */
    // test that cards exiled using Bane Alley Broker are face down
    @Test
    public void testBaneAlleyBroker() {
        addCard(Zone.BATTLEFIELD, playerA, "Bane Alley Broker");
        addCard(Zone.HAND, playerA, "Goblin Roughrider");
        addCard(Zone.HAND, playerA, "Sejiri Merfolk");
        addCard(Zone.BATTLEFIELD, playerA, "Island");
        addCard(Zone.BATTLEFIELD, playerA, "Swamp");

        setStrictChooseMode(true);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}");
        addTarget(playerA, "Goblin Roughrider");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertHandCount(playerA, 2);
        assertHandCount(playerA, "Sejiri Merfolk", 1);
        assertHandCount(playerA, "Goblin Roughrider", 0);

        assertExileCount("Goblin Roughrider", 1);

        for (Card card : currentGame.getExile().getAllCards(currentGame)) {
            if (card.getName().equals("Goblin Roughrider")) {
                Assert.assertTrue("Exiled card is not face down", card.isFaceDown(currentGame));
            }
        }
    }

    @Test
    public void testBaneAlleyBrokerReturn() {
        addCard(Zone.BATTLEFIELD, playerA, "Bane Alley Broker");
        addCard(Zone.HAND, playerA, "Goblin Roughrider");
        addCard(Zone.HAND, playerA, "Sejiri Merfolk");
        addCard(Zone.BATTLEFIELD, playerA, "Island");
        addCard(Zone.BATTLEFIELD, playerA, "Swamp");

        setStrictChooseMode(true);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}");
        addTarget(playerA, "Goblin Roughrider");

        activateAbility(3, PhaseStep.PRECOMBAT_MAIN, playerA, "{U}{B}");
        addTarget(playerA, "Goblin Roughrider");

        setStopAt(3, PhaseStep.BEGIN_COMBAT);
        execute();

        assertHandCount(playerA, 4);
        assertHandCount(playerA, "Sejiri Merfolk", 1);
        assertHandCount(playerA, "Goblin Roughrider", 1);

        assertExileCount("Goblin Roughrider", 0);
    }

}
