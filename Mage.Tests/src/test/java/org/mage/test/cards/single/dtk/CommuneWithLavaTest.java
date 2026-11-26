package org.mage.test.cards.single.dtk;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author JayDi85
 */
public class CommuneWithLavaTest extends CardTestPlayerBase {

    @Test
    public void test_MayPlayUntilEndOfYourNextTurn() {
        skipInitShuffling();

        // Exile the top X cards of your library. Until the end of your next turn, you may play those cards.
        addCard(Zone.HAND, playerA, "Commune with Lava"); // {X}{R}{R}
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 3 + 1);
        //
        addCard(Zone.LIBRARY, playerA, "Lightning Bolt"); // top card, {R}

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Commune with Lava");
        setChoice(playerA, "X=1");

        checkPlayableAbility("this turn", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Cast Lightning Bolt", true);
        checkPlayableAbility("opponent turn", 2, PhaseStep.POSTCOMBAT_MAIN, playerA, "Cast Lightning Bolt", true);
        checkPlayableAbility("end of your next turn", 3, PhaseStep.END_TURN, playerA, "Cast Lightning Bolt", true);
        checkPlayableAbility("after your next turn", 5, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Lightning Bolt", false);

        setStrictChooseMode(true);
        setStopAt(5, PhaseStep.END_TURN);
        execute();

        assertExileCount(playerA, "Lightning Bolt", 1);
    }
}
