package org.mage.test.cards.single.wth;

import mage.constants.PhaseStep;
import mage.constants.Zone;
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
    public void testActivations() {
        addCard(Zone.BATTLEFIELD, playerA, well);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);
        addCard(Zone.BATTLEFIELD, playerB, "Island", 2);

        checkPlayableAbility("", 1, PhaseStep.PRECOMBAT_MAIN, playerA, ability, false);
        checkPlayableAbility("", 1, PhaseStep.PRECOMBAT_MAIN, playerB, ability, false);

        checkPlayableAbility("", 2, PhaseStep.DRAW, playerA, ability, false);
        checkPlayableAbility("", 2, PhaseStep.DRAW, playerB, ability, true);

        checkPlayableAbility("", 3, PhaseStep.DRAW, playerA, ability, true);
        checkPlayableAbility("", 3, PhaseStep.DRAW, playerB, ability, false);

        activateAbility(3, PhaseStep.DRAW, playerA, ability);

        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertHandCount(playerA, 2); // draw step and well
        assertHandCount(playerB, 1); // draw step

    }

}
