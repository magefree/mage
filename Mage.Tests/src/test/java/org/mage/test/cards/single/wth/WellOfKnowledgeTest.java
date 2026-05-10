package org.mage.test.cards.single.wth;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author xenohedron
 */
public class WellOfKnowledgeTest extends CardTestPlayerBase {

    // {2}: Draw a card. Any player may activate this ability but only during their draw step.
    private static final String well = "Well of Knowledge";
    private static final String ability = "{2}: Draw";

    @Test
    public void testController() {
        addCard(Zone.BATTLEFIELD, playerA, well);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);
        addCard(Zone.BATTLEFIELD, playerB, "Island", 2);

        activateAbility(3, PhaseStep.DRAW, playerA, ability);

        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertHandCount(playerA, 2); // draw step and well
        assertHandCount(playerB, 1); // draw step

    }

    @Test
    public void testNotController() {
        addCard(Zone.BATTLEFIELD, playerA, well);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);
        addCard(Zone.BATTLEFIELD, playerB, "Island", 2);

        activateAbility(2, PhaseStep.DRAW, playerB, ability);

        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertHandCount(playerA, 1); // draw step
        assertHandCount(playerB, 2); // draw step and well
    }

    @Test
    public void testControllerNotActive() {
        addCard(Zone.BATTLEFIELD, playerA, well);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);
        addCard(Zone.BATTLEFIELD, playerB, "Island", 2);

        activateAbility(2, PhaseStep.DRAW, playerA, ability);

        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.PRECOMBAT_MAIN);
        try {
            execute();
        } catch (AssertionError e) {
            Assert.assertEquals(
                    "Correctly can't activate",
                    "Can't find ability to activate command: " + ability, e.getMessage()
            );
            return;
        }
        Assert.fail("Was able to activate ability, but shouldn't");
    }

    @Test
    public void testNotControllerNotActive() {
        addCard(Zone.BATTLEFIELD, playerA, well);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);
        addCard(Zone.BATTLEFIELD, playerB, "Island", 2);

        activateAbility(3, PhaseStep.DRAW, playerB, ability);

        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.PRECOMBAT_MAIN);
        try {
            execute();
        } catch (AssertionError e) {
            Assert.assertEquals(
                    "Correctly can't activate",
                    "Can't find ability to activate command: " + ability, e.getMessage()
            );
            return;
        }
        Assert.fail("Was able to activate ability, but shouldn't");
    }

    @Test
    public void testControllerNotDrawStep() {
        addCard(Zone.BATTLEFIELD, playerA, well);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);
        addCard(Zone.BATTLEFIELD, playerB, "Island", 2);

        activateAbility(3, PhaseStep.UPKEEP, playerA, ability);

        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.PRECOMBAT_MAIN);
        try {
            execute();
        } catch (AssertionError e) {
            Assert.assertEquals(
                    "Correctly can't activate",
                    "Can't find ability to activate command: " + ability, e.getMessage()
            );
            return;
        }
        Assert.fail("Was able to activate ability, but shouldn't");
    }

}
