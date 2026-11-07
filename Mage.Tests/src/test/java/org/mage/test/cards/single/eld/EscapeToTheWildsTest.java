package org.mage.test.cards.single.eld;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author JayDi85
 */
public class EscapeToTheWildsTest extends CardTestPlayerBase {

    @Test
    public void test_MayPlayUntilEndOfYourNextTurn_AndAdditionalLand() {
        skipInitShuffling();

        // Exile the top five cards of your library. You may play cards exiled this way until the end of your next turn.
        // You may play an additional land this turn.
        addCard(Zone.HAND, playerA, "Escape to the Wilds"); // {3}{R}{G}
        addCard(Zone.BATTLEFIELD, playerA, "Taiga", 5 + 1);
        //
        addCard(Zone.LIBRARY, playerA, "Lightning Bolt"); // top card, {R}
        addCard(Zone.HAND, playerA, "Forest", 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Escape to the Wilds");
        playLand(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Forest");
        playLand(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Forest");

        checkPlayableAbility("opponent turn", 2, PhaseStep.POSTCOMBAT_MAIN, playerA, "Cast Lightning Bolt", true);
        checkPlayableAbility("end of your next turn", 3, PhaseStep.END_TURN, playerA, "Cast Lightning Bolt", true);
        checkPlayableAbility("after your next turn", 5, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Lightning Bolt", false);

        setStrictChooseMode(true);
        setStopAt(5, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Forest", 2);
        assertExileCount(playerA, 5);
    }
}
