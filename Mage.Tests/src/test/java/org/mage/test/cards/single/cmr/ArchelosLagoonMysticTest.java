package org.mage.test.cards.single.cmr;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author JayDi85
 */

public class ArchelosLagoonMysticTest extends CardTestPlayerBase {

    @Test
    public void test_Playable() {
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2 + 2); // add lands before Archelos, Lagoon Mystic to ignore ETB effects

        // As long as Archelos, Lagoon Mystic is tapped, other permanents enter the battlefield tapped.
        // As long as Archelos, Lagoon Mystic is untapped, other permanents enter the battlefield untapped.
        addCard(Zone.BATTLEFIELD, playerA, "Archelos, Lagoon Mystic", 1);
        //
        addCard(Zone.HAND, playerA, "Grizzly Bears", 1); // {1}{G}
        addCard(Zone.HAND, playerA, "Deranged Outcast", 1); // {1}{G}

        // first - untapped
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Grizzly Bears");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkPermanentTapped("untapped", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Archelos, Lagoon Mystic", false, 1);
        checkPermanentTapped("untapped", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Grizzly Bears", false, 1);

        // prepare tapped mystic
        attack(1, playerA, "Archelos, Lagoon Mystic", playerB);
        checkPermanentTapped("tapped", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Archelos, Lagoon Mystic", true, 1);

        // second - tapped
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Deranged Outcast");
        waitStackResolved(1, PhaseStep.POSTCOMBAT_MAIN);
        checkPermanentTapped("tapped", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Archelos, Lagoon Mystic", true, 1);
        checkPermanentTapped("tapped", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Deranged Outcast", true, 1);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
    }
}
