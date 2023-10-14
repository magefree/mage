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
 * @author Susucr
 */
public class UntilEndCombatYourNextTurnTest extends CardTestPlayerBase {

    public interface AdditionalSetup {
        void init(UntilEndCombatYourNextTurnTest test);
    }

    public void doTest(AdditionalSetup additionalSetup, int endTurnNum, PhaseStep endPhaseStep, boolean stillActive) {
        addCustomCardWithAbility(
                "tester", playerA,
                new SimpleActivatedAbility(new BoostSourceEffect(
                        1, 1, Duration.UntilEndCombatOfYourNextTurn
                ), new ManaCostsImpl<>("{0}")), null,
                CardType.CREATURE, "", Zone.BATTLEFIELD
        );

        if(additionalSetup != null){
            additionalSetup.init(this);
        }

        activateAbility(1, PhaseStep.UPKEEP, playerA, "{0}");

        setStrictChooseMode(true);
        setStopAt(endTurnNum, endPhaseStep);
        execute();

        int powerToughness = stillActive ? 2 : 1;
        assertPowerToughness(playerA, "tester", powerToughness, powerToughness);
    }

    @Test
    public void testSameTurnPre() {
        doTest(null, 1, PhaseStep.PRECOMBAT_MAIN, true);
    }

    @Test
    public void testSameTurnPost() {
        doTest(null,1, PhaseStep.POSTCOMBAT_MAIN, true);
    }

    @Test
    public void testOppTurnPre() {
        doTest(null, 2, PhaseStep.PRECOMBAT_MAIN, true); }

    @Test
    public void testOppTurnPost() {
        doTest(null, 2, PhaseStep.PRECOMBAT_MAIN, true);
    }

    @Test
    public void testTurnCyclePre() {
        doTest(null, 3, PhaseStep.PRECOMBAT_MAIN, true);
    }

    @Test
    public void testTurnCycleFalse() {

        doTest(null, 3, PhaseStep.POSTCOMBAT_MAIN, false);
    }

    // Relevant rulings:
    //
    // 614.10. An effect that causes a player to skip an event, step, phase, or turn
    // is a replacement effect. “Skip [something]” is the same as “Instead of doing
    // [something], do nothing.” Once a step, phase, or turn has started, it can no
    // longer be skipped—any skip effects will wait until the next occurrence.
    //
    // 614.10a Anything scheduled for a skipped step, phase, or turn won’t happen.
    // Anything scheduled for the “next” occurrence of something waits for the first
    // occurrence that isn’t skipped. If two effects each cause a player to skip
    // their next occurrence, that player must skip the next two; one effect will
    // be satisfied in skipping the first occurrence, while the other will remain
    // until another occurrence can be skipped
    private static void timeStopOn3(UntilEndCombatYourNextTurnTest test) {
        // End the turn.
        test.addCard(Zone.HAND, test.playerA, "Time Stop");
        test.addCard(Zone.BATTLEFIELD, test.playerA, "Island", 6);
        test.castSpell(3,PhaseStep.PRECOMBAT_MAIN,test.playerA,"Time Stop");
    }

    @Test
    public void testTimeStopTurnCyclePre() {

        doTest(test -> timeStopOn3(test), 3, PhaseStep.PRECOMBAT_MAIN, true);
    }

    @Test
    public void testTimeStopTurnCycleFalse() {

        doTest(test -> timeStopOn3(test), 3, PhaseStep.CLEANUP, true);
    }

    @Test
    public void testTimeStop2TurnCyclePre() {

        doTest(test -> timeStopOn3(test), 5, PhaseStep.PRECOMBAT_MAIN, true);
    }

    @Test
    public void testTimeStop2TurnCycleFalse() {
        doTest(test -> timeStopOn3(test), 5, PhaseStep.POSTCOMBAT_MAIN, true);
    }

}
