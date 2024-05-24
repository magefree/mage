package org.mage.test.cards.single.mh3;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author Susucr
 */
public class MarionetteApprenticeTest extends CardTestPlayerBase {

    /**
     * {@link mage.cards.m.MarionetteApprentice} {1}{B}
     * Creature — Human Artificer
     * Fabricate 1
     * Whenever another creature or artifact you control is put into a graveyard from the battlefield, each opponent loses 1 life.
     */
    private static final String apprentice = "Marionette Apprentice";

    @Test
    public void test_Trigger() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, apprentice);
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears", 2);
        addCard(Zone.BATTLEFIELD, playerB, "Grizzly Bears", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Mox Ruby", 2);
        addCard(Zone.BATTLEFIELD, playerB, "Mox Ruby", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Sylvan Library", 2);
        addCard(Zone.BATTLEFIELD, playerB, "Sylvan Library", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 6);
        addCard(Zone.HAND, playerA, "Akroma's Vengeance"); // Destroy all artifacts, creatures, and enchantments.

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Akroma's Vengeance");
        setChoice(playerA, "Whenever", 3); // stack triggers, there are 4 total.

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, 8);
        assertGraveyardCount(playerB, 6);
        assertLife(playerB, 20 - 4);
    }
}
