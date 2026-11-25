package org.mage.test.cards.single.khm;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author JayDi85
 */
public class ShowdownOfTheSkaldsTest extends CardTestPlayerBase {

    @Test
    public void test_ChapterI_MayPlayUntilEndOfYourNextTurn() {
        skipInitShuffling();

        // I — Exile the top four cards of your library. Until the end of your next turn, you may play those cards.
        addCard(Zone.HAND, playerA, "Showdown of the Skalds"); // {2}{R}{W}
        addCard(Zone.BATTLEFIELD, playerA, "Plateau", 4 + 1);
        //
        addCard(Zone.LIBRARY, playerA, "Lightning Bolt"); // top card, {R}

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Showdown of the Skalds");

        checkPlayableAbility("opponent turn", 2, PhaseStep.POSTCOMBAT_MAIN, playerA, "Cast Lightning Bolt", true);
        checkPlayableAbility("end of your next turn", 3, PhaseStep.END_TURN, playerA, "Cast Lightning Bolt", true);
        checkPlayableAbility("after your next turn", 5, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Lightning Bolt", false);

        setStrictChooseMode(true);
        setStopAt(5, PhaseStep.END_TURN);
        execute();

        assertExileCount(playerA, 4);
    }
}
