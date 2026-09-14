package org.mage.test.cards.single.cmd;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * Archangel of Strife {5}{W}{W} 6/6
 * As Archangel of Strife enters the battlefield, each player chooses war or peace.
 * Creatures controlled by players who chose war get +3/+0.
 * Creatures controlled by players who chose peace get +0/+3.
 * <p>
 * Each boost is filtered per creature by what that creature's *controller* chose, which no other
 * card does.
 *
 * @author notgreat, Claude Opus 5
 */
public class ArchangelOfStrifeTest extends CardTestPlayerBase {

    /**
     * Flickering the Archangel makes both players choose again, so the same board swaps boosts, and
     * a creature cast after that still picks the new choice up.
     */
    @Test
    public void test_bothChoicesApplyAndAreRemadeOnReentry() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 8); // {5}{W}{W} plus Cloudshift's {W}
        addCard(Zone.HAND, playerA, "Archangel of Strife");
        addCard(Zone.HAND, playerA, "Cloudshift");
        addCard(Zone.HAND, playerA, "Memnite"); // {0} 1/1
        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion"); // 2/2
        addCard(Zone.BATTLEFIELD, playerB, "Grizzly Bears");   // 2/2

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Archangel of Strife");
        setChoice(playerA, "war");
        setChoice(playerB, "peace");

        checkPT("A chose war", 1, PhaseStep.BEGIN_COMBAT, playerA, "Silvercoat Lion", 2 + 3, 2);
        checkPT("B chose peace", 1, PhaseStep.BEGIN_COMBAT, playerB, "Grizzly Bears", 2, 2 + 3);

        // it enters again, so both players choose again -- this time the other way round
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Cloudshift", "Archangel of Strife", true);
        setChoice(playerA, "peace");
        setChoice(playerB, "war");

        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Memnite");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPowerToughness(playerA, "Silvercoat Lion", 2, 2 + 3);
        assertPowerToughness(playerA, "Memnite", 1, 1 + 3); // cast after the choices changed
        assertPowerToughness(playerA, "Archangel of Strife", 6, 6 + 3);
        assertPowerToughness(playerB, "Grizzly Bears", 2 + 3, 2);
    }
}
