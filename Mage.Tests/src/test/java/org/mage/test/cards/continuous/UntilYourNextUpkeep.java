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
public class UntilYourNextUpkeep extends CardTestPlayerBase {

    public interface AdditionalSetup {
        void init(UntilYourNextUpkeep test);
    }

    public void doTest(AdditionalSetup additionalSetup,
                       int startTurnNum, PhaseStep startPhaseStep,
                       int endTurnNum, PhaseStep endPhaseStep,
                       boolean stillActive) {
        setStrictChooseMode(true);

        addCustomCardWithAbility(
                "tester", playerA,
                new SimpleActivatedAbility(new BoostSourceEffect(
                        1, 1, Duration.UntilYourNextUpkeepStep
                ), new ManaCostsImpl<>("{0}")), null,
                CardType.CREATURE, "", Zone.BATTLEFIELD
        );

        if(additionalSetup != null){
            additionalSetup.init(this);
        }

        activateAbility(startTurnNum, startPhaseStep, playerA, "{0}");

        setStopAt(endTurnNum, endPhaseStep);
        execute();

        int powerToughness = stillActive ? 2 : 1;
        assertPowerToughness(playerA, "tester", powerToughness, powerToughness);
    }

    @Test
    public void testSameTurn() {
        doTest(null, 1, PhaseStep.UPKEEP, 1, PhaseStep.PRECOMBAT_MAIN, true);
    }

    @Test
    public void testOppTurn() {
        doTest(null, 1, PhaseStep.UPKEEP, 2, PhaseStep.PRECOMBAT_MAIN, true);
    }

    @Test
    public void testTurnCycle() {
        doTest(null, 1, PhaseStep.UPKEEP, 3, PhaseStep.PRECOMBAT_MAIN, false);
    }

    private static void initParadoxHaze(UntilYourNextUpkeep test) {
        // At the beginning of enchanted player’s first upkeep each turn,
        // that player gets an additional upkeep step after this step.
        test.addCard(Zone.HAND, test.playerA, "Paradox Haze");
        test.addCard(Zone.BATTLEFIELD, test.playerA, "Island", 3);
        test.castSpell(1, PhaseStep.PRECOMBAT_MAIN, test.playerA, "Paradox Haze", test.playerA);
    }

    @Test
    public void testParadoxHazeOppSameTurn() {
        doTest(test -> initParadoxHaze(test), 2, PhaseStep.UPKEEP, 2, PhaseStep.PRECOMBAT_MAIN, true);
    }

    // Activating at first upkeep, at second upkeep the effect wears off.
    @Test
    public void testParadoxHazeSameTurn() {
        doTest(test -> initParadoxHaze(test), 3, PhaseStep.UPKEEP, 3, PhaseStep.PRECOMBAT_MAIN, false);
    }

    private static void initEonHub(UntilYourNextUpkeep test) {
        // Players skip their upkeep step.
        test.addCard(Zone.BATTLEFIELD, test.playerA, "Eon Hub");
    }

    @Test
    public void testEonHubSameTurn() {
        doTest(test -> initEonHub(test), 1, PhaseStep.PRECOMBAT_MAIN, 1, PhaseStep.POSTCOMBAT_MAIN, true);
    }

    @Test
    public void testEonHubCycleTurn() {
        doTest(test -> initEonHub(test), 1, PhaseStep.PRECOMBAT_MAIN, 3, PhaseStep.POSTCOMBAT_MAIN, true);
    }
}
