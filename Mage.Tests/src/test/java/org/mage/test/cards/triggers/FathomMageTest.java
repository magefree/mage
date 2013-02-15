package org.mage.test.cards.triggers;

import mage.Constants;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author LevelX2
 */
public class FathomMageTest extends CardTestPlayerBase {

    /**
     * Fathom Mage - Creature — Human Wizard 1/1, 2UG
     *
     * Evolve (Whenever a creature enters the battlefield under your control, if that creature has greater power or toughness than this creature, put a +1/+1 counter on this creature.)
     * Whenever a +1/+1 counter is placed on Fathom Mage, you may draw a card.
     *

     */
    @Test
    public void testDrawCardsAddedCounters() {
         // card draw triggered ability will trigger once for each of those counters from Blessings of Nature.

        addCard(Constants.Zone.HAND, playerA, "Blessings of Nature");
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Fathom Mage", 1);
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Forest", 5);

        castSpell(1, Constants.PhaseStep.PRECOMBAT_MAIN, playerA, "Blessings of Nature", "Fathom Mage");

        setStopAt(1, Constants.PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Fathom Mage", 1);
        assertPowerToughness(playerA, "Fathom Mage", 5, 5);
        assertHandCount(playerA, 4);
    }

    @Test
    public void testDrawCardsEntersTheBattlefield() {
         // card draw triggered ability will trigger once for each of those counters from Master Biomancer.

        addCard(Constants.Zone.HAND, playerA, "Fathom Mage");
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Master Biomancer", 1);
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Forest", 4);
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Island", 4);

        castSpell(1, Constants.PhaseStep.PRECOMBAT_MAIN, playerA, "Fathom Mage");

        setStopAt(1, Constants.PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Fathom Mage", 1);
        assertPowerToughness(playerA, "Fathom Mage", 3, 3);
        assertHandCount(playerA, 2);
    }
}
