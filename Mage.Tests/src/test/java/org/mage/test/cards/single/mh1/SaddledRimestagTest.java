package org.mage.test.cards.single.mh1;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author notgreat
 */
public class SaddledRimestagTest extends CardTestPlayerBase {

    // Saddled Rimestag {1}{G}
    // Snow Creature - Elk 2/2
    // This creature gets +2/+2 as long as you had another creature enter the battlefield under your control this turn.

    /**
     * An earlier instance of the Rimestag itself is a different object, so it counts as another creature.
     * Garruk's Uprising draws a card only if it sees a 4/4 entering, so the boost must apply as it enters.
     */
    @Test
    public void test_DetectSelf() {
        addCard(Zone.BATTLEFIELD, playerA, "Savannah", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Garruk's Uprising");
        addCard(Zone.HAND, playerA, "Saddled Rimestag"); // {1}{G}
        addCard(Zone.HAND, playerA, "Cloudshift"); // {W}

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Saddled Rimestag", true);
        checkPT("only creature to enter", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Saddled Rimestag", 2, 2);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cloudshift", "Saddled Rimestag", true);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertGraveyardCount(playerA, "Cloudshift", 1);
        assertPermanentCount(playerA, "Saddled Rimestag", 1);
        assertPowerToughness(playerA, "Saddled Rimestag", 4, 4);
        assertHandCount(playerA, 1); // Garruk's Uprising triggered on the returning 4/4
    }

    /**
     * Tokens enter the battlefield from outside the game, not from another zone, so they need counting too.
     */
    @Test
    public void test_DetectToken() {
        addCard(Zone.BATTLEFIELD, playerA, "Taiga", 3);
        addCard(Zone.HAND, playerA, "Sprout"); // {G}, creates a 1/1 Saproling
        addCard(Zone.HAND, playerA, "Saddled Rimestag"); // {1}{G}

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Sprout", true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Saddled Rimestag", true);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, "Saproling Token", 1);
        assertPowerToughness(playerA, "Saddled Rimestag", 4, 4);
    }

    /**
     * A creature that entered the battlefield earlier this turn still counts after it dies.
     */
    @Test
    public void test_DetectDead() {
        addCard(Zone.BATTLEFIELD, playerA, "Taiga", 3);
        addCard(Zone.HAND, playerA, "Memnite"); // {0} 1/1
        addCard(Zone.HAND, playerA, "Lightning Bolt"); // {R}
        addCard(Zone.HAND, playerA, "Saddled Rimestag"); // {1}{G}

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Memnite", true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", "Memnite", true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Saddled Rimestag", true);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertGraveyardCount(playerA, "Memnite", 1);
        assertPermanentCount(playerA, "Saddled Rimestag", 1);
        assertPowerToughness(playerA, "Saddled Rimestag", 4, 4);
    }
}
