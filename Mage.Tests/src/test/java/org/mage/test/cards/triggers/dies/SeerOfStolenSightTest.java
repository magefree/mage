package org.mage.test.cards.triggers.dies;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author JayDi85
 */

public class SeerOfStolenSightTest extends CardTestPlayerBase {

    @Test
    public void test_SingleDie() {
        skipInitShuffling();
        addCustomEffect_TargetDestroy(playerA);

        // Whenever one or more artifacts and/or creatures you control are put into a graveyard from the battlefield, surveil 1.
        addCard(Zone.BATTLEFIELD, playerA, "Seer of Stolen Sight");
        addCard(Zone.LIBRARY, playerA, "Swamp", 2);
        //
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears");

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "target destroy", "Grizzly Bears");
        addTarget(playerA, "Swamp"); // surveil

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerA, "Grizzly Bears", 1);
        assertGraveyardCount(playerA, "Swamp", 1);
    }

    @Test
    public void test_MultipleDies() {
        skipInitShuffling();
        addCustomEffect_TargetDestroy(playerA, 2);

        // Whenever one or more artifacts and/or creatures you control are put into a graveyard from the battlefield, surveil 1.
        addCard(Zone.BATTLEFIELD, playerA, "Seer of Stolen Sight");
        addCard(Zone.LIBRARY, playerA, "Swamp", 2);
        //
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears", 2);

        // must catch only one time trigger
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "target destroy", "Grizzly Bears^Grizzly Bears");
        addTarget(playerA, "Swamp"); // surveil

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerA, "Grizzly Bears", 2);
        assertGraveyardCount(playerA, "Swamp", 1);
    }

    @Test
    public void test_OwnDie() {
        skipInitShuffling();
        addCustomEffect_TargetDestroy(playerA);

        // Whenever one or more artifacts and/or creatures you control are put into a graveyard from the battlefield, surveil 1.
        addCard(Zone.BATTLEFIELD, playerA, "Seer of Stolen Sight");
        addCard(Zone.LIBRARY, playerA, "Swamp", 2);
        //
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears", 1);

        // must trigger on own die
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "target destroy", "Seer of Stolen Sight");
        addTarget(playerA, "Swamp"); // surveil

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerA, "Seer of Stolen Sight", 1);
        assertGraveyardCount(playerA, "Swamp", 1);
    }
}
