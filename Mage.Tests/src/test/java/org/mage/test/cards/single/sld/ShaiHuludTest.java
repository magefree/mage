package org.mage.test.cards.single.sld;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestCommander4Players;

public class ShaiHuludTest extends CardTestCommander4Players {

    // When this creature enters, destroy all artifacts and enchantments. For each player who
    // controlled a permanent destroyed this way, search your library for a basic land card or a
    // Desert card. Put those cards onto the battlefield tapped, then shuffle.
    private static final String SHAI_HULUD = "Shai-Hulud";

    @Test
    public void testDistinctControllersAndIndestructible() {
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 9);
        addCard(Zone.HAND, playerA, SHAI_HULUD);
        addCard(Zone.BATTLEFIELD, playerA, "Sol Ring");
        addCard(Zone.BATTLEFIELD, playerA, "Arcane Signet");
        addCard(Zone.BATTLEFIELD, playerB, "Howling Mine");
        addCard(Zone.BATTLEFIELD, playerB, "Stranglehold");
        // Whenever an opponent searches their library, that player sacrifices a creature and loses 10 life.
        // The life loss verifies that finding both cards involves one search.
        addCard(Zone.BATTLEFIELD, playerB, "Ob Nixilis, Unshackled");
        addCard(Zone.BATTLEFIELD, playerC, "Darksteel Citadel");
        addCard(Zone.LIBRARY, playerA, "Plains");
        addCard(Zone.LIBRARY, playerA, "Desert");
        addCard(Zone.LIBRARY, playerA, "Island");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, SHAI_HULUD);
        addTarget(playerA, "Plains");
        addTarget(playerA, "Desert");
        setChoice(playerA, SHAI_HULUD);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerA, "Sol Ring", 1);
        assertGraveyardCount(playerA, "Arcane Signet", 1);
        assertGraveyardCount(playerB, "Howling Mine", 1);
        assertGraveyardCount(playerB, "Stranglehold", 1);
        assertPermanentCount(playerC, "Darksteel Citadel", 1);
        assertPermanentCount(playerA, "Plains", 1);
        assertPermanentCount(playerA, "Desert", 1);
        assertTapped("Plains", true);
        assertTapped("Desert", true);
        assertLibraryCount(playerA, "Island", 1);
        assertLife(playerA, 10);
        assertGraveyardCount(playerA, SHAI_HULUD, 1);
    }

    @Test
    public void testNoPermanentsDestroyed() {
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 9);
        addCard(Zone.HAND, playerA, SHAI_HULUD);
        addCard(Zone.BATTLEFIELD, playerB, "Darksteel Citadel");
        // Whenever an opponent searches their library, that player sacrifices a creature and loses 10 life.
        // The unchanged life total verifies that no search occurs.
        addCard(Zone.BATTLEFIELD, playerB, "Ob Nixilis, Unshackled");
        addCard(Zone.LIBRARY, playerA, "Plains");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, SHAI_HULUD);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerB, "Darksteel Citadel", 1);
        assertPermanentCount(playerA, "Plains", 0);
        assertLibraryCount(playerA, "Plains", 1);
        assertLife(playerA, 20);
    }

    @Test
    public void testCountsControllerNotOwner() {
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 9);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Sol Ring");
        addCard(Zone.BATTLEFIELD, playerB, "Ornithopter");
        addCard(Zone.HAND, playerA, "Act of Treason");
        addCard(Zone.HAND, playerA, SHAI_HULUD);
        addCard(Zone.LIBRARY, playerA, "Plains");
        addCard(Zone.LIBRARY, playerA, "Island");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Act of Treason", "Ornithopter");
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, SHAI_HULUD);
        addTarget(playerA, "Plains");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerA, "Sol Ring", 1);
        assertGraveyardCount(playerB, "Ornithopter", 1);
        assertPermanentCount(playerA, "Plains", 1);
        assertTapped("Plains", true);
        assertLibraryCount(playerA, "Island", 1);
    }
}
