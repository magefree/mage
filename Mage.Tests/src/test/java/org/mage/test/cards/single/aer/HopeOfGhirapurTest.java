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

        setStrictChooseMode(true);

        attack(1, playerA, "Hope of Ghirapur");
        activateAbility(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Sacrifice", playerB);

        checkPlayableAbility("Can't Shock", 2, PhaseStep.PRECOMBAT_MAIN, playerB, "Shock", false);

        setStopAt(2, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 19);
        assertPermanentCount(playerA, "Hope of Ghirapur", 0);
    }

    /**
     * Test that ability cannot be activated if after damage Hope of Ghirapur was removed
     * from the battlefield and returned back.
     */
    @Test
    public void testWhenHopeOfGhirapurWasRemovedAndReturnedBack() {
        // Flying
        // Sacrifice Hope of Ghirapur: Until your next turn, target player who was dealt combat damage by Hope of Ghirapur this turn can't cast noncreature spells.
        addCard(Zone.BATTLEFIELD, playerA, "Hope of Ghirapur");
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 1);
        // Exile target creature you control, then return that card to the battlefield under your control.
        addCard(Zone.HAND, playerA, "Cloudshift");

        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 1);
        addCard(Zone.HAND, playerB, "Shock");

        attack(1, playerA, "Hope of Ghirapur");
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Cloudshift", "Hope of Ghirapur");
        checkPlayableAbility("Hope of Ghirapur died", 1, PhaseStep.END_TURN, playerA, "Sacrifice", false);

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Shock", playerA);

        setStopAt(2, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerA, 18);
        assertLife(playerB, 19);
        assertPermanentCount(playerA, "Hope of Ghirapur", 1);
    }
}
