package org.mage.test.cards.single.clb;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.player.TestPlayer;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author JayDi85
 */
public class NefariousImpTest extends CardTestPlayerBase {

    @Test
    public void test_TwoPermanentsLeave_OneTrigger() {
        // Whenever one or more permanents you control leave the battlefield, scry 1.
        addCard(Zone.BATTLEFIELD, playerA, "Nefarious Imp");
        //
        // Return target nonland permanent and all other permanents with the same name as that permanent to their owners' hands.
        addCard(Zone.HAND, playerA, "Echoing Truth"); // {1}{U}
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears", 2);

        // return 2 creatures at once
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Echoing Truth", "Grizzly Bears");
        addTarget(playerA, TestPlayer.TARGET_SKIP); // scry 1: keep on top, must be used only once

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertHandCount(playerA, "Grizzly Bears", 2);
    }

    @Test
    public void test_OpponentPermanentsLeave_NoTrigger() {
        // Whenever one or more permanents you control leave the battlefield, scry 1.
        addCard(Zone.BATTLEFIELD, playerA, "Nefarious Imp");
        //
        // Return target nonland permanent and all other permanents with the same name as that permanent to their owners' hands.
        addCard(Zone.HAND, playerA, "Echoing Truth"); // {1}{U}
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);
        addCard(Zone.BATTLEFIELD, playerB, "Grizzly Bears", 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Echoing Truth", "Grizzly Bears");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertHandCount(playerB, "Grizzly Bears", 2);
    }

    @Test
    public void test_PermanentsLeaveToDifferentZones_OneTrigger() {
        // Whenever one or more permanents you control leave the battlefield, scry 1.
        addCard(Zone.BATTLEFIELD, playerA, "Nefarious Imp");
        //
        // Unearth {B}
        addCard(Zone.GRAVEYARD, playerA, "Dregscape Zombie");
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears");
        //
        // Each player sacrifices two creatures.
        addCard(Zone.HAND, playerA, "Barter in Blood"); // {2}{B}{B}
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 5);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Unearth");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        // sacrifice 2 creatures at once: unearthed goes to exile, other to graveyard
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Barter in Blood");
        setChoice(playerA, "Dregscape Zombie^Grizzly Bears");
        addTarget(playerA, TestPlayer.TARGET_SKIP); // scry 1: keep on top, must be used only once

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertExileCount(playerA, "Dregscape Zombie", 1);
        assertGraveyardCount(playerA, "Grizzly Bears", 1);
    }
}
