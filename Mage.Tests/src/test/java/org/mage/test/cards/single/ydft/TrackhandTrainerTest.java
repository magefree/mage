package org.mage.test.cards.single.ydft;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class TrackhandTrainerTest extends CardTestPlayerBase {

    @Test
    public void testDrawWithoutTrainingGrounds() {
        addCard(Zone.BATTLEFIELD, playerA, "Trackhand Trainer");
        addCard(Zone.BATTLEFIELD, playerA, "Island", 5);
        skipInitShuffling();
        addCard(Zone.LIBRARY, playerA, "Grizzly Bears");

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{3}{U}{U}: Draw a card");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertHandCount(playerA, "Grizzly Bears", 1);
        assertPowerToughness(playerA, "Trackhand Trainer", 1, 2);
    }

    @Test
    public void testConjuredTrainingGroundsReducesDrawCost() {
        addCard(Zone.BATTLEFIELD, playerA, "Trackhand Trainer");
        addCard(Zone.BATTLEFIELD, playerA, "Island", 4);
        skipInitShuffling();
        addCard(Zone.LIBRARY, playerA, "Grizzly Bears");

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Exhaust");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{3}{U}{U}: Draw a card");
        checkPlayableAbility("exhaust cannot repeat", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Exhaust", false);
        checkPlayableAbility("exhaust stays used", 3, PhaseStep.PRECOMBAT_MAIN, playerA, "Exhaust", false);

        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, "Training Grounds", 1);
        assertPermanentCount(playerB, "Training Grounds", 0);
        assertHandCount(playerA, "Grizzly Bears", 1);
    }

    @Test
    public void testDrawStillRequiresTwoBlueMana() {
        addCard(Zone.BATTLEFIELD, playerA, "Trackhand Trainer");
        addCard(Zone.BATTLEFIELD, playerA, "Island");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 4);

        checkPlayableAbility("one blue is insufficient", 1, PhaseStep.PRECOMBAT_MAIN, playerA,
                "{3}{U}{U}: Draw a card", false);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertHandCount(playerA, 0);
    }
}
