package org.mage.test.cards.single.aer;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author Quercitron
 */
public class HopeOfGhirapurTest extends CardTestPlayerBase {

    @Test
    public void testThatNoncreatureSpellsCannotBeCast() {
        addCard(Zone.BATTLEFIELD, playerA, "Hope of Ghirapur");

        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 1);
        addCard(Zone.HAND, playerB, "Shock");

        attack(1, playerA, "Hope of Ghirapur");
        activateAbility(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Sacrifice", playerB);

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Shock", playerA);

        setStopAt(2, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 19);
        assertPermanentCount(playerA, "Hope of Ghirapur", 0);
    }

    // Test that ability cannot be activated if after damage Hope of Ghirapur was removed
    // from the battlefield and returned back.
    @Test
    public void testWhenHopeOfGhirapurWasRemovedAndReturnedBack() {
        addCard(Zone.BATTLEFIELD, playerA, "Hope of Ghirapur");
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 1);
        addCard(Zone.HAND, playerA, "Cloudshift");

        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 1);
        addCard(Zone.HAND, playerB, "Shock");

        attack(1, playerA, "Hope of Ghirapur");
        castSpell(1, PhaseStep.END_COMBAT, playerA, "Cloudshift", "Hope of Ghirapur");
        activateAbility(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Sacrifice", playerB);

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Shock", playerA);

        setStopAt(2, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerA, 18);
        assertLife(playerB, 19);
        assertPermanentCount(playerA, "Hope of Ghirapur", 1);
    }

}
