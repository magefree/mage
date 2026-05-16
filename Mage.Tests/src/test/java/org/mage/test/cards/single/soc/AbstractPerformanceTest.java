package org.mage.test.cards.single.soc;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class AbstractPerformanceTest extends CardTestPlayerBase {

    private static final String abstractPerformance = "Abstract Performance";
    private static final String crawWurm = "Craw Wurm";

    @Test
    public void testOpponentChoosesFaceUpPileForGraveyard() {
        skipInitShuffling();

        addCard(Zone.HAND, playerA, abstractPerformance);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 6);
        addCard(Zone.LIBRARY, playerA, "Forest"); // eighth from top
        addCard(Zone.LIBRARY, playerA, "Plains");
        addCard(Zone.LIBRARY, playerA, "Mountain");
        addCard(Zone.LIBRARY, playerA, "Swamp");
        addCard(Zone.LIBRARY, playerA, "Island");
        addCard(Zone.LIBRARY, playerA, "Island");
        addCard(Zone.LIBRARY, playerA, "Island");
        addCard(Zone.LIBRARY, playerA, crawWurm); // top of library, in the face-down pile

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, abstractPerformance);
        setChoice(playerB, false); // choose Face-up, putting the revealed pile into the graveyard
        setChoice(playerA, true); // cast Craw Wurm without paying its mana cost

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, crawWurm, 1);
        assertHandCount(playerA, 3);
        assertGraveyardCount(playerA, 5); // Abstract Performance plus the chosen four-card pile
        assertExileCount(playerA, 0);
    }

    @Test
    public void testOpponentChoosesFaceDownPileForGraveyard() {
        skipInitShuffling();

        addCard(Zone.HAND, playerA, abstractPerformance);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 6);
        addCard(Zone.LIBRARY, playerA, "Forest"); // eighth from top
        addCard(Zone.LIBRARY, playerA, "Plains");
        addCard(Zone.LIBRARY, playerA, "Mountain");
        addCard(Zone.LIBRARY, playerA, crawWurm); // fifth from top, in the face-up pile
        addCard(Zone.LIBRARY, playerA, "Island");
        addCard(Zone.LIBRARY, playerA, "Island");
        addCard(Zone.LIBRARY, playerA, "Island");
        addCard(Zone.LIBRARY, playerA, "Island"); // top of library, in the face-down pile

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, abstractPerformance);
        setChoice(playerB, true); // choose Face-down, putting the hidden pile into the graveyard
        setChoice(playerA, true); // cast Craw Wurm without paying its mana cost

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, crawWurm, 1);
        assertHandCount(playerA, 3);
        assertGraveyardCount(playerA, 5); // Abstract Performance plus the chosen four-card pile
        assertExileCount(playerA, 0);
    }
}
