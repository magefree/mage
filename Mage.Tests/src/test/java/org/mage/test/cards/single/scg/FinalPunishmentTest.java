package org.mage.test.cards.single.scg;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class FinalPunishmentTest extends CardTestPlayerBase {

    final String finalPunishment = "Final Punishment";
    final String shock = "Shock";
    final String bob = "Dark Confidant";

    /**
     * Ensure it works for damage from spells.
     */
    @Test
    public void lifelossBecauseOfDirectDamage() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 5);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);
        addCard(Zone.HAND, playerA, finalPunishment);
        addCard(Zone.HAND, playerA, shock);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, shock, playerB);
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, finalPunishment, playerB);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerB, 16);
    }

    /**
     * Ensure it works for damage from combat.
     */
    @Test
    public void lifelossBecauseOfCombat() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 5);
        addCard(Zone.HAND, playerA, finalPunishment);
        addCard(Zone.BATTLEFIELD, playerA, bob);

        attack(1, playerA, bob, playerB);
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, finalPunishment, playerB);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerB, 16);
    }

    /**
     * Ensure it does not count damage that occured in the previous turns.
     */
    @Test
    public void nolifelossInNextTurn() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 5);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);
        addCard(Zone.HAND, playerA, finalPunishment);
        addCard(Zone.HAND, playerA, shock);

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerA, shock, playerB);
        castSpell(3, PhaseStep.POSTCOMBAT_MAIN, playerA, finalPunishment, playerB);
        setStopAt(3, PhaseStep.END_TURN);
        execute();

        assertLife(playerB, 18);
    }
}
