package org.mage.test.cards.single.zen;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * Cobra Trap
 * {4}{G}{G}
 * Instant — Trap
 * If a noncreature permanent under your control was destroyed this turn by a spell or ability an opponent controlled,
 * you may pay {G} rather than pay this spell’s mana cost.
 * Create four 1/1 green Snake creature tokens.
 *
 * @author BetaSteward
 */
public class CobraTrapTest extends CardTestPlayerBase {

    /**
     * Cast using the alternative cost.
     */
    @Test
    public void testCard() {
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2);
        addCard(Zone.HAND, playerA, "Cobra Trap");
        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 3);
        addCard(Zone.HAND, playerB, "Stone Rain");

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Stone Rain", "Forest");
        castSpell(2, PhaseStep.POSTCOMBAT_MAIN, playerA, "Cobra Trap");

        setStopAt(2, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);
        assertPermanentCount(playerA, "Forest", 1);
        assertPermanentCount(playerA, "Snake Token", 4);
    }

    /**
     * Check that the alternative cost can't be paid if the condition isn't met.
     */
    @Test
    public void testCardNegative() {
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2);
        addCard(Zone.HAND, playerA, "Cobra Trap");
        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 3);
        addCard(Zone.HAND, playerB, "Stone Rain");

        checkPlayableAbility("Not enough mana", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Cast Cobra", false);

        setStopAt(2, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Forest", 2);
        assertPermanentCount(playerA, "Snake Token", 0);
    }

}
