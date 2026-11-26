package org.mage.test.cards.single.trk;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author DejviFiala
 */
public class TemporalConquerorTest extends CardTestPlayerBase {

    /*
     * Temporal Conqueror {2}{G}
     * Creature — Na'kuhl Warrior
     * When this creature enters, mill three cards. Then return a land card from your graveyard to your hand.
     * If you can't, create a Treasure token.
     * 2/3
     */
    private static final String conqueror = "Temporal Conqueror";
    private static final String treasure = "Treasure Token";
    private static final String bears = "Grizzly Bears";
    private static final String bolt = "Lightning Bolt";

    private void setupLibrary(String... cards) {
        removeAllCardsFromLibrary(playerA);
        for (String card : cards) {
            addCard(Zone.LIBRARY, playerA, card);
        }
    }

    @Test
    public void testReturnLandThatWasNotMilled() {
        setupLibrary(bears, "Mountain", bears);
        addCard(Zone.GRAVEYARD, playerA, "Island");
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 3);
        addCard(Zone.HAND, playerA, conqueror);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, conqueror);
        // the returned land can be any land card in the graveyard, not only a milled one
        setChoice(playerA, "Island");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, conqueror, 1);
        assertLibraryCount(playerA, 0);
        assertHandCount(playerA, 1);
        assertHandCount(playerA, "Island", 1);
        assertGraveyardCount(playerA, "Mountain", 1);
        assertGraveyardCount(playerA, bears, 2);
        assertPermanentCount(playerA, treasure, 0);
    }

    @Test
    public void testReturnMilledLand() {
        setupLibrary(bears, bears, "Mountain");
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 3);
        addCard(Zone.HAND, playerA, conqueror);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, conqueror);
        setChoice(playerA, "Mountain");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertHandCount(playerA, 1);
        assertHandCount(playerA, "Mountain", 1);
        assertGraveyardCount(playerA, bears, 2);
        assertPermanentCount(playerA, treasure, 0);
    }

    @Test
    public void testNoLandCreatesTreasure() {
        setupLibrary(bears, bolt, bears);
        addCard(Zone.GRAVEYARD, playerA, bolt);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 3);
        addCard(Zone.HAND, playerA, conqueror);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, conqueror);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertHandCount(playerA, 0);
        assertGraveyardCount(playerA, 4);
        assertPermanentCount(playerA, treasure, 1);
    }

    @Test
    public void testEmptyLibraryNoLandCreatesTreasure() {
        removeAllCardsFromLibrary(playerA);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 3);
        addCard(Zone.HAND, playerA, conqueror);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, conqueror);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, conqueror, 1);
        assertHandCount(playerA, 0);
        assertPermanentCount(playerA, treasure, 1);
    }

    @Test
    public void testLandInOpponentGraveyardNotReturned() {
        setupLibrary(bears, bears, bears);
        addCard(Zone.GRAVEYARD, playerB, "Island");
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 3);
        addCard(Zone.HAND, playerA, conqueror);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, conqueror);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertHandCount(playerA, 0);
        assertGraveyardCount(playerB, "Island", 1);
        assertPermanentCount(playerA, treasure, 1);
    }

    @Test
    public void testSourceRemovedTriggerStillResolves() {
        setupLibrary(bears, bears, bears);
        addCard(Zone.GRAVEYARD, playerA, "Island");
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 3);
        addCard(Zone.HAND, playerA, conqueror);
        addCard(Zone.BATTLEFIELD, playerB, "Mountain");
        addCard(Zone.HAND, playerB, bolt);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, conqueror);
        // resolve the creature spell only, the enters trigger stays on the stack
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, bolt, conqueror);
        setChoice(playerA, "Island");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertGraveyardCount(playerA, conqueror, 1);
        assertGraveyardCount(playerA, bears, 3);
        assertHandCount(playerA, 1);
        assertHandCount(playerA, "Island", 1);
        assertPermanentCount(playerA, treasure, 0);
    }

    @Test
    public void testRestInPeaceLandAlreadyInGraveyard() {
        // If a card or token would be put into a graveyard from anywhere, exile it instead.
        addCard(Zone.BATTLEFIELD, playerB, "Rest in Peace");
        setupLibrary(bears, "Mountain", bears);
        addCard(Zone.GRAVEYARD, playerA, "Island");
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 3);
        addCard(Zone.HAND, playerA, conqueror);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, conqueror);
        // the milled Mountain is exiled, so only the Island can be returned
        setChoice(playerA, "Island");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertExileCount(playerA, 3);
        assertExileCount(playerA, "Mountain", 1);
        assertHandCount(playerA, 1);
        assertHandCount(playerA, "Island", 1);
        assertPermanentCount(playerA, treasure, 0);
    }
}
