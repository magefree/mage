
package org.mage.test.cards.single.clu;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author Susucr
 */
public class AmzuSwarmsHungerTest extends CardTestPlayerBase {

    /**
     * {@link mage.cards.a.AmzuSwarmsHunger Amzu, Swarm's Hunger} {3}{B}{G}
     * Legendary Creature — Insect Shaman
     * Flying, menace
     * Other Insects you control have menace.
     * Whenever one or more cards leave your graveyard, you may create a 1/1 black and green Insect creature token,
     * then put a number of +1/+1 counters on it equal to the greatest mana value among those cards.
     * Do this only once each turn.
     * 3/3
     */
    private static final String amzu = "Amzu, Swarm's Hunger";

    @Test
    public void testTwoCreatures() {

        addCard(Zone.BATTLEFIELD, playerA, amzu);
        addCard(Zone.BATTLEFIELD, playerA, "Zask, Skittering Swarmlord");
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 4);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 4);
        addCard(Zone.GRAVEYARD, playerA, "Battlefly Swarm");
        addCard(Zone.GRAVEYARD, playerA, "Saber Ants");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Saber Ants", true);
        setChoice(playerA, "Yes");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Battlefly Swarm", true);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Insect Token", 1);
        assertCounterCount(playerA, "Insect Token", CounterType.P1P1, 4);
    }

    @Test
    public void testGraveyardExile() {

        addCard(Zone.BATTLEFIELD, playerA, amzu);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);
        addCard(Zone.GRAVEYARD, playerA, "Living Hive");
        addCard(Zone.GRAVEYARD, playerA, "Saber Ants");
        addCard(Zone.GRAVEYARD, playerA, "Blasphemous Act"); // test non-permanent card type
        addCard(Zone.HAND, playerA, "Rakdos Charm");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Rakdos Charm", true);
        setModeChoice(playerA, "1"); // Exile target player’s graveyard.
        addTarget(playerA, playerA);
        setChoice(playerA, "Yes");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Insect Token", 1);
        assertCounterCount(playerA, "Insect Token", CounterType.P1P1, 9);
    }
}
