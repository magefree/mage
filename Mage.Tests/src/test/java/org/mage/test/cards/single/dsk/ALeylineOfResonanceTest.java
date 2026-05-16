package org.mage.test.cards.single.dsk;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class ALeylineOfResonanceTest extends CardTestPlayerBase {

    private static final String LEYLINE = "A-Leyline of Resonance";
    private static final String GROWTH = "Giant Growth";
    private static final String BEARS = "Grizzly Bears";

    @Test
    public void testCopiesSingleTargetSpellWhenCostIsPaid() {
        setStrictChooseMode(true);

        // Scenario: A-Leyline should copy a qualifying instant or sorcery only if its controller
        // pays the extra {1}. Keeping the copy on the original target should apply Giant Growth
        // twice to the single controlled creature.
        addCard(Zone.BATTLEFIELD, playerA, LEYLINE);
        addCard(Zone.BATTLEFIELD, playerA, BEARS);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2);
        addCard(Zone.HAND, playerA, GROWTH);

        // Step 1: Cast Giant Growth targeting only a single creature controlled by player A.
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, GROWTH, BEARS);

        // Step 2: Pay A-Leyline's {1} cost and keep the copy on the same target.
        setChoice(playerA, true);
        setChoice(playerA, false);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        // Step 3: Grizzly Bears received +6/+6 total, proving that A-Leyline copied the spell.
        assertPowerToughness(playerA, BEARS, 8, 8);
        assertGraveyardCount(playerA, GROWTH, 1);
    }

    @Test
    public void testDoesNotCopyWhenCostIsDeclined() {
        setStrictChooseMode(true);

        // Scenario: Declining the optional {1} payment should leave only the original spell to
        // resolve, so Giant Growth applies once.
        addCard(Zone.BATTLEFIELD, playerA, LEYLINE);
        addCard(Zone.BATTLEFIELD, playerA, BEARS);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2);
        addCard(Zone.HAND, playerA, GROWTH);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, GROWTH, BEARS);
        setChoice(playerA, false);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPowerToughness(playerA, BEARS, 5, 5);
        assertGraveyardCount(playerA, GROWTH, 1);
    }
}
