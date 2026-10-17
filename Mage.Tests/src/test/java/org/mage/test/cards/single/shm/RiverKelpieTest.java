package org.mage.test.cards.single.shm;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author JayDi85
 */
public class RiverKelpieTest extends CardTestPlayerBase {

    @Test
    public void test_PutOntoBattlefieldFromGraveyard() {
        // Whenever River Kelpie or another permanent is put onto the battlefield from a graveyard, draw a card.
        addCard(Zone.BATTLEFIELD, playerA, "River Kelpie");
        //
        // Put target creature card from a graveyard onto the battlefield under your control.
        // You lose life equal to its mana value.
        addCard(Zone.HAND, playerA, "Reanimate"); // {B}
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 1);
        addCard(Zone.GRAVEYARD, playerA, "Grizzly Bears", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Reanimate", "Grizzly Bears");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Grizzly Bears", 1);
        assertHandCount(playerA, 1);
    }

    @Test
    public void test_Panharmonicon_TriggersAdditionalTime() {
        // Whenever River Kelpie or another permanent is put onto the battlefield from a graveyard, draw a card.
        addCard(Zone.BATTLEFIELD, playerA, "River Kelpie");
        //
        // If an artifact or creature entering the battlefield causes a triggered ability of a permanent you control to trigger, that ability triggers an additional time.
        addCard(Zone.BATTLEFIELD, playerA, "Panharmonicon");
        //
        // Put target creature card from a graveyard onto the battlefield under your control.
        // You lose life equal to its mana value.
        addCard(Zone.HAND, playerA, "Reanimate"); // {B}
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 1);
        addCard(Zone.GRAVEYARD, playerA, "Grizzly Bears", 1);

        // creature put onto the battlefield from a graveyard - one trigger + one additional trigger
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Reanimate", "Grizzly Bears");
        setChoice(playerA, "Whenever {this} or another permanent enters"); // two triggers order

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Grizzly Bears", 1);
        assertHandCount(playerA, 2);
    }
}
