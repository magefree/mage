package org.mage.test.cards.single.one;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author Susucr
 */
public class EleshNornMotherOfMachinesTest extends CardTestPlayerBase {

    @Test
    public void test_EleshNorn_PreventEtb() {
        // Elesh Norn, Mother of Machines
        // Vigilance
        // If a permanent entering the battlefield causes a triggered ability of a
        // permanent you control to trigger, that ability triggers an additional time.
        //
        // Permanents entering the battlefield don't cause abilities of permanents
        // your opponents control to trigger.
        addCard(Zone.BATTLEFIELD, playerB, "Elesh Norn, Mother of Machines");

        // Arashin Cleric - {1}[W}
        // When Arashin Cleric enters the battlefield, you gain 3 life.
        addCard(Zone.HAND, playerA, "Arashin Cleric");
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Arashin Cleric");

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        setStrictChooseMode(true);
        execute();

        assertLife(playerA, 20);
    }


    @Test
    public void test_EleshNorn_DoNotStopBloodghast() {
        // Elesh Norn, Mother of Machines
        //
        // Vigilance
        // If a permanent entering the battlefield causes a triggered ability of a
        // permanent you control to trigger, that ability triggers an additional time.
        //
        // Permanents entering the battlefield don't cause abilities of permanents
        // your opponents control to trigger.
        addCard(Zone.BATTLEFIELD, playerB, "Elesh Norn, Mother of Machines");

        // Bloodghast
        //
        // Bloodghast can't block.
        // Bloodghast has haste as long as an opponent has 10 or less life.
        // Landfall — Whenever a land enters the battlefield under your control,
        // you may return Bloodghast from your graveyard to the battlefield.
        addCard(Zone.GRAVEYARD, playerA, "Bloodghast");
        addCard(Zone.HAND, playerA, "Forest");

        playLand(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Forest");
        setChoice(playerA, true); // Yes to the bloodghast trigger.

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        setStrictChooseMode(true);
        execute();

        assertPermanentCount(playerA, "Bloodghast", 1);
    }

}
