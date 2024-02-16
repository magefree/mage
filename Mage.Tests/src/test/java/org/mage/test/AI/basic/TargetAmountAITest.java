package org.mage.test.AI.basic;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBaseWithAIHelps;

/**
 * @author JayDi85
 */
public class TargetAmountAITest extends CardTestPlayerBaseWithAIHelps {

    @Test
    public void test_AI_ChooseTargets() {
        // Distribute four +1/+1 counters among any number of target creatures.
        addCard(Zone.HAND, playerA, "Blessings of Nature", 1); // {4}{G}
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 5);
        //
        addCard(Zone.BATTLEFIELD, playerA, "Balduvian Bears", 4); // 2/2
        addCard(Zone.BATTLEFIELD, playerB, "Balduvian Bears", 4); // 2/2

        // ai must choose by special dialog, not full simulation
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Blessings of Nature");

        setStopAt(1, PhaseStep.END_TURN);
        //setStrictChooseMode(true); // ai must choose
        execute();

        assertPowerToughness(playerA, "Balduvian Bears", 2 + 4, 2 + 4); // boost one creature (it's just a choose code, so can be different from simulation results)
    }

    @Test
    public void test_AI_SimulateTargets() {
        // Distribute four +1/+1 counters among any number of target creatures.
        addCard(Zone.HAND, playerA, "Blessings of Nature", 1); // {4}{G}
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 5);
        //
        addCard(Zone.BATTLEFIELD, playerA, "Balduvian Bears", 4); // 2/2
        addCard(Zone.BATTLEFIELD, playerB, "Balduvian Bears", 4); // 2/2

        // AI must put creatures on own permanents (all in one creature to boost it)
        aiPlayPriority(1, PhaseStep.PRECOMBAT_MAIN, playerA);

        setStopAt(1, PhaseStep.END_TURN);
        setStrictChooseMode(true);
        execute();

        assertPowerToughness(playerA, "Balduvian Bears", 2 + 1, 2 + 1); // boost each possible creatures
        assertPowerToughness(playerB, "Balduvian Bears", 2, 2); // no boost for enemy
    }
}
