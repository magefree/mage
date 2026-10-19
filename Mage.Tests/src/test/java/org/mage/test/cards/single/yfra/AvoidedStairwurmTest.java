package org.mage.test.cards.single.yfra;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class AvoidedStairwurmTest extends CardTestPlayerBase {

    @Test
    public void testCountsOnlyOwnLibraryCreaturesAndUpdatesAfterDraw() {
        removeAllCardsFromLibrary(playerA);
        skipInitShuffling();
        addCard(Zone.LIBRARY, playerA, "Divination");
        addCard(Zone.LIBRARY, playerA, "Forest");
        addCard(Zone.LIBRARY, playerA, "Grizzly Bears");
        addCard(Zone.LIBRARY, playerB, "Grizzly Bears", 3);
        addCard(Zone.GRAVEYARD, playerA, "Llanowar Elves");
        addCard(Zone.BATTLEFIELD, playerA, "Avoided Stairwurm");
        addCard(Zone.BATTLEFIELD, playerA, "Island");
        addCard(Zone.HAND, playerA, "Reach Through Mists");

        checkPT("one creature", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Avoided Stairwurm", 4, 4);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Reach Through Mists");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertHandCount(playerA, "Grizzly Bears", 1);
        assertPowerToughness(playerA, "Avoided Stairwurm", 5, 5);
    }

    @Test
    public void testFiveCreaturesGiveZeroToughness() {
        removeAllCardsFromLibrary(playerA);
        skipInitShuffling();
        addCard(Zone.LIBRARY, playerA, "Grizzly Bears");
        addCard(Zone.LIBRARY, playerA, "Grizzly Bears");
        addCard(Zone.LIBRARY, playerA, "Grizzly Bears");
        addCard(Zone.LIBRARY, playerA, "Grizzly Bears");
        addCard(Zone.LIBRARY, playerA, "Grizzly Bears");
        addCard(Zone.BATTLEFIELD, playerA, "Avoided Stairwurm");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, "Avoided Stairwurm", 0);
        assertGraveyardCount(playerA, "Avoided Stairwurm", 1);
    }
}
