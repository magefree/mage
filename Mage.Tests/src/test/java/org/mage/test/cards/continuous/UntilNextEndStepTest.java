package org.mage.test.cards.continuous;

import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author TheElk801
 */
public class UntilNextEndStepTest extends CardTestPlayerBase {

    public void doTest(int startTurnNum, PhaseStep startPhaseStep, int endTurnNum, PhaseStep endPhaseStep, boolean stillActive) {
        addCustomCardWithAbility(
                "tester", playerA,
                new SimpleActivatedAbility(new BoostSourceEffect(
                        1, 1, Duration.UntilYourNextEndStep
                ), new ManaCostsImpl<>("{0}")), null,
                CardType.CREATURE, "", Zone.BATTLEFIELD
        );

        activateAbility(startTurnNum, startPhaseStep, playerA, "{0}");

        setStrictChooseMode(true);
        setStopAt(endTurnNum, endPhaseStep);
        execute();

        int powerToughness = stillActive ? 2 : 1;
        assertPowerToughness(playerA, "tester", powerToughness, powerToughness);
    }

    @Test
    public void testSameTurnTrue() {
        doTest(1, PhaseStep.PRECOMBAT_MAIN, 1, PhaseStep.POSTCOMBAT_MAIN, true);
    }

    @Test
    public void testSameTurnFalse() {
        doTest(1, PhaseStep.PRECOMBAT_MAIN, 1, PhaseStep.END_TURN, false);
    }

    @Test
    public void testNextTurnTrue() {
        doTest(1, PhaseStep.END_TURN, 2, PhaseStep.PRECOMBAT_MAIN, true);
    }

    @Test
    public void testNextTurnFalse() {
        doTest(1, PhaseStep.PRECOMBAT_MAIN, 2, PhaseStep.PRECOMBAT_MAIN, false);
    }

    @Test
    public void testTurnCycleTrue() {
        doTest(1, PhaseStep.END_TURN, 3, PhaseStep.PRECOMBAT_MAIN, true);
    }

    @Test
    public void testTurnCycleFalse() {
        doTest(1, PhaseStep.END_TURN, 3, PhaseStep.END_TURN, false);
    }

    @Test
    public void testOpponentTurnTrue() {
        doTest(2, PhaseStep.PRECOMBAT_MAIN, 3, PhaseStep.PRECOMBAT_MAIN, true);
    }

    @Test
    public void testOpponentTurnFalse() {
        doTest(2, PhaseStep.PRECOMBAT_MAIN, 3, PhaseStep.END_TURN, false);
    }
}
