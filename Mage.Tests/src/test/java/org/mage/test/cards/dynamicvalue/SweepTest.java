package org.mage.test.cards.dynamicvalue;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.player.TestPlayer;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author BetaSteward
 */
public class SweepTest extends CardTestPlayerBase {


    /**
     * Plow Through Reito
     * 1W
     * Instant -- Arcane
     * Sweep -- Return any number of Plains you control to their owner's hand.
     * Target creature gets +1/+1 until end of turn for each Plains returned this way.
     */
    @Test
    public void testSweep1x() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 5);
        addCard(Zone.BATTLEFIELD, playerA, "Raging Goblin");
        addCard(Zone.HAND, playerA, "Plow Through Reito");

        setStrictChooseMode(true);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Plow Through Reito");
        addTarget(playerA, "Raging Goblin"); // target to boost
        addTarget(playerA, "Plains"); // targets to sweep

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Raging Goblin", 1);
        assertPermanentCount(playerA, "Plains", 4);
        assertPowerToughness(playerA, "Raging Goblin", 2, 2);
    }

    @Test
    public void testSweep2x() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 5);
        addCard(Zone.BATTLEFIELD, playerA, "Raging Goblin");
        addCard(Zone.HAND, playerA, "Plow Through Reito");

        setStrictChooseMode(true);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Plow Through Reito");
        addTarget(playerA, "Raging Goblin"); // target to boost
        addTarget(playerA, "Plains^Plains"); // targets to sweep

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Raging Goblin", 1);
        assertPermanentCount(playerA, "Plains", 3);
        assertPowerToughness(playerA, "Raging Goblin", 3, 3);
    }

    @Test
    public void testSweep3x() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 5);
        addCard(Zone.BATTLEFIELD, playerA, "Raging Goblin");
        addCard(Zone.HAND, playerA, "Plow Through Reito");

        setStrictChooseMode(true);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Plow Through Reito");
        addTarget(playerA, "Raging Goblin"); // target to boost
        addTarget(playerA, "Plains^Plains^Plains"); // targets to sweep

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Raging Goblin", 1);
        assertPermanentCount(playerA, "Plains", 2);
        assertPowerToughness(playerA, "Raging Goblin", 4, 4);
    }

    @Test
    public void testSweep0x() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 5);
        addCard(Zone.BATTLEFIELD, playerA, "Raging Goblin");
        addCard(Zone.HAND, playerA, "Plow Through Reito");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Plow Through Reito");
        // addTarget(playerA, "Raging Goblin"); // Autochosen (target to boost)
        addTarget(playerA, TestPlayer.TARGET_SKIP); // targets to sweep (zero)

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Raging Goblin", 1);
        assertPermanentCount(playerA, "Plains", 5);
        assertPowerToughness(playerA, "Raging Goblin", 1, 1);
    }

}
