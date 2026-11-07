package org.mage.test.cards.single.iko;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author JayDi85
 */
public class RootingMolochTest extends CardTestPlayerBase {

    @Test
    public void test_MayPlayUntilEndOfYourNextTurn() {
        // When this creature enters, exile target card with a cycling ability from your graveyard. Until the end of
        // your next turn, you may play that card.
        addCard(Zone.HAND, playerA, "Rooting Moloch"); // {4}{R}
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 5);
        //
        // You gain 6 life. Cycling {1}{W}
        addCard(Zone.GRAVEYARD, playerA, "Renewed Faith"); // {2}{W}
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 3);

        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {R}", 5);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Rooting Moloch");
        addTarget(playerA, "Renewed Faith");

        checkPlayableAbility("opponent turn", 2, PhaseStep.POSTCOMBAT_MAIN, playerA, "Cast Renewed Faith", true);
        checkPlayableAbility("end of your next turn", 3, PhaseStep.END_TURN, playerA, "Cast Renewed Faith", true);
        checkPlayableAbility("after your next turn", 5, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Renewed Faith", false);

        setStrictChooseMode(true);
        setStopAt(5, PhaseStep.END_TURN);
        execute();

        assertExileCount(playerA, "Renewed Faith", 1);
    }
}
