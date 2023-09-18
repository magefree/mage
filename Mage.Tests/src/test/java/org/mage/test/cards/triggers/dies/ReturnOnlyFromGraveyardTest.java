
package org.mage.test.cards.triggers.dies;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class ReturnOnlyFromGraveyardTest extends CardTestPlayerBase {

    /**
     * Effects like Gift of Immortality and Fool's Demise are able to return
     * Academy Rector to the battlefield after the exile trigger if they are put
     * on the stack after.
     */
    @Test
    public void testFoolsDemise() {
        // When Academy Rector dies, you may exile it. If you do, search your library for an enchantment card, put that card onto the battlefield, then shuffle your library.
        addCard(Zone.BATTLEFIELD, playerA, "Academy Rector", 1); // Creature 1/2
        addCard(Zone.BATTLEFIELD, playerA, "Island", 5);
        addCard(Zone.LIBRARY, playerA, "Primal Rage", 2);
        // Enchant creature
        // When enchanted creature dies, return that card to the battlefield under your control.
        // When Fool's Demise is put into a graveyard from the battlefield, return Fool's Demise to its owner's hand.
        addCard(Zone.HAND, playerA, "Fool's Demise"); // Enchantment {4}{U}

        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 1);
        addCard(Zone.HAND, playerB, "Lightning Bolt");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Fool's Demise", "Academy Rector");

        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerB, "Lightning Bolt", "Academy Rector");

        setChoice(playerA, "When enchanted creature dies"); // Select triggered ability to execute last
        setChoice(playerA, true);
        addTarget(playerA, "Primal Rage");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerB, "Lightning Bolt", 1);

        assertHandCount(playerA, "Fool's Demise", 1);
        assertPermanentCount(playerA, "Primal Rage", 1);
        assertExileCount("Academy Rector", 1);

    }

    @Test
    public void testGiftOfImmortality() {
        // When Academy Rector dies, you may exile it. If you do, search your library for an enchantment card, put that card onto the battlefield, then shuffle your library.
        addCard(Zone.BATTLEFIELD, playerA, "Academy Rector", 1); // Creature 1/2
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 3);
        addCard(Zone.LIBRARY, playerA, "Primal Rage", 2);

        // Enchant creature
        // When enchanted creature dies, return that card to the battlefield under its owner's control.
        // Return Gift of Immortality to the battlefield attached to that creature at the beginning of the next end step.
        addCard(Zone.HAND, playerA, "Gift of Immortality"); // Enchantment {2}{W}

        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 1);
        addCard(Zone.HAND, playerB, "Lightning Bolt");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Gift of Immortality", "Academy Rector");

        castSpell(1, PhaseStep.BEGIN_COMBAT, playerB, "Lightning Bolt", "Academy Rector");

        setChoice(playerA, "When enchanted creature dies"); // Select triggered ability to execute last
        setChoice(playerA, true); // May exile it
        addTarget(playerA, "Primal Rage");

        setStopAt(1, PhaseStep.END_COMBAT);
        execute();

        assertGraveyardCount(playerB, "Lightning Bolt", 1);

        assertGraveyardCount(playerA, "Gift of Immortality", 1);
        assertPermanentCount(playerA, "Primal Rage", 1);
        assertExileCount("Academy Rector", 1);

    }
}
