package org.mage.test.cards.single.cmr;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author JayDi85
 */

public class SphinxOfTheSecondSunTest extends CardTestPlayerBase {

    @Test
    public void test_Playable_OneCard() {
        // bug: card generating infinite amount of extra steps

        // At the beginning of your postcombat main phase, you get an additional beginning phase after this phase.
        // (The beginning phase includes the untap, upkeep, and draw steps.)
        addCard(Zone.BATTLEFIELD, playerA, "Sphinx of the Second Sun", 1);
        //
        // At the beginning of your upkeep, each opponent loses 1 life. You gain life equal to the life lost this way.
        addCard(Zone.BATTLEFIELD, playerA, "Agent of Masks", 1);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        // 2x upkeep phases (1x normal + 1x from sphinx)
        assertLife(playerA, 20 + 2);
        assertLife(playerB, 20 - 2);
    }

    @Test
    public void test_Playable_TwoCards() {
        // bug: card generating infinite amount of extra steps

        // At the beginning of your postcombat main phase, you get an additional beginning phase after this phase.
        // (The beginning phase includes the untap, upkeep, and draw steps.)
        addCard(Zone.BATTLEFIELD, playerA, "Sphinx of the Second Sun", 2);
        //
        // At the beginning of your upkeep, each opponent loses 1 life. You gain life equal to the life lost this way.
        addCard(Zone.BATTLEFIELD, playerA, "Agent of Masks", 1);

        setChoice(playerA, "At the beginning"); // 2x triggers

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        // 3x upkeep phases (1x normal + 2x from sphinx)
        assertLife(playerA, 20 + 3);
        assertLife(playerB, 20 - 3);
    }
}
