package org.mage.test.cards.single.clb;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class DisorderInTheCourtTest extends CardTestPlayerBase {

    // {X}{W}{U} Instant
    // Exile X target creatures, then investigate X times.
    // Return the exiled cards to the battlefield tapped under their owners’ control at the beginning of the next end step.
    private static final String disorder = "Disorder in the Court";
    private static final String leonin = "Leonin Elder"; // Whenever an artifact enters the battlefield, you may gain 1 life.
    private static final String lizard = "Lagac Lizard";


    @Test
    public void testDisorder() {
        addCard(Zone.BATTLEFIELD, playerA, "Tundra", 4);
        addCard(Zone.BATTLEFIELD, playerA, leonin);
        addCard(Zone.BATTLEFIELD, playerB, lizard);
        addCard(Zone.HAND, playerA, disorder);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, disorder);
        setChoice(playerA, "X=2");
        addTarget(playerA, leonin);
        addTarget(playerA, lizard);

        checkExileCount("Exiled", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, leonin, 1);
        checkExileCount("Exiled", 1, PhaseStep.POSTCOMBAT_MAIN, playerB, lizard, 1);
        checkPermanentCount("Clue tokens", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Clue Token", 2);

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.UPKEEP);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);
        assertTapped(leonin, true);
        assertPermanentCount(playerB, lizard, 1);
    }

}
