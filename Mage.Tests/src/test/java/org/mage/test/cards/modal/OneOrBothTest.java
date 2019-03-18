
package org.mage.test.cards.modal;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class OneOrBothTest extends CardTestPlayerBase {

    @Test
    public void testSubtleStrikeFirstMode() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 2);
        // Choose one or both —
        // • Target creature gets -1/-1 until end of turn.
        // • Put a +1/+1 counter on target creature.
        addCard(Zone.HAND, playerA, "Subtle Strike"); // Instant {1}{B}

        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion");
        addCard(Zone.BATTLEFIELD, playerB, "Pillarfield Ox");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Subtle Strike", "Pillarfield Ox");
        setModeChoice(playerA, "1");
        setModeChoice(playerA, null);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPowerToughness(playerA, "Silvercoat Lion", 2, 2);
        assertPowerToughness(playerB, "Pillarfield Ox", 1, 3);
    }

    @Test
    public void testSubtleStrikeSecondMode() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 2);
        // Choose one or both —
        // • Target creature gets -1/-1 until end of turn.
        // • Put a +1/+1 counter on target creature.
        addCard(Zone.HAND, playerA, "Subtle Strike"); // Instant {1}{B}

        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion");
        addCard(Zone.BATTLEFIELD, playerB, "Pillarfield Ox");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Subtle Strike", "Pillarfield Ox");
        setModeChoice(playerA, "2");
        setModeChoice(playerA, null);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPowerToughness(playerA, "Silvercoat Lion", 2, 2);
        assertPowerToughness(playerB, "Pillarfield Ox", 3, 5);
    }

    @Test
    public void testSubtleStrikeBothModes() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 2);
        // Choose one or both —
        // • Target creature gets -1/-1 until end of turn.
        // • Put a +1/+1 counter on target creature.
        addCard(Zone.HAND, playerA, "Subtle Strike"); // Instant {1}{B}

        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion");
        addCard(Zone.BATTLEFIELD, playerB, "Pillarfield Ox");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Subtle Strike", "Pillarfield Ox");
        addTarget(playerA, "Silvercoat Lion");
        setModeChoice(playerA, "1");
        setModeChoice(playerA, "2");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPowerToughness(playerB, "Pillarfield Ox", 1, 3);
        assertPowerToughness(playerA, "Silvercoat Lion", 3, 3);
    }
}
