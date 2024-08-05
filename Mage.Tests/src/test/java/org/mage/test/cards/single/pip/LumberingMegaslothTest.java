package org.mage.test.cards.single.pip;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author Susucr
 */
public class LumberingMegaslothTest extends CardTestPlayerBase {

    /**
     * {@link mage.cards.l.LumberingMegasloth Lumbering Megasloth} {10}{G}{G}
     * Creature — Sloth Mutant
     * This spell costs {1} less to cast for each counter among players and permanents.
     * Trample
     * Lumbering Megasloth enters the battlefield tapped.
     * <p>
     * 8/8
     */
    private static final String sloth = "Lumbering Megasloth";

    @Test
    public void test_Reduction() {
        setStrictChooseMode(true);

        addCard(Zone.HAND, playerA, sloth);
        addCard(Zone.BATTLEFIELD, playerA, "Adaptive Shimmerer"); // enters with 3 +1/+1
        addCard(Zone.HAND, playerA, "Aether Herder"); // {3}{G}, etb: you get {E}{E}
        addCard(Zone.BATTLEFIELD, playerB, "Arlinn Kord"); // PW with 3 loyalty counters
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 4 + 4); // 4 for Herder, then 4 for sloth as 9 counters total.

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Aether Herder", true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, sloth);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, sloth, 1);
        assertTappedCount("Forest", true, 8);
    }
}
