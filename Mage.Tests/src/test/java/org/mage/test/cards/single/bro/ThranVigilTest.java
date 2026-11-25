package org.mage.test.cards.single.bro;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author JayDi85
 */
public class ThranVigilTest extends CardTestPlayerBase {

    @Test
    public void test_CreatureReturnedByMarchesa() {
        // Whenever one or more artifact and/or creature cards leave your graveyard during your turn,
        // put a +1/+1 counter on target creature you control.
        addCard(Zone.BATTLEFIELD, playerA, "Thran Vigil");
        //
        // Whenever a creature you control with a +1/+1 counter on it dies, return that card to the battlefield
        // under your control at the beginning of the next end step.
        addCard(Zone.BATTLEFIELD, playerA, "Marchesa, the Black Rose");
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears", 1);
        //
        // Lightning Bolt deals 3 damage to any target.
        addCard(Zone.HAND, playerA, "Lightning Bolt"); // {R}
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);

        addCounters(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Grizzly Bears", CounterType.P1P1, 1);

        // kill creature with +1/+1 counter - it returns at the end step and leaves your graveyard
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", "Grizzly Bears");
        addTarget(playerA, "Marchesa, the Black Rose"); // Thran Vigil's counter

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.UPKEEP);
        execute();

        assertPermanentCount(playerA, "Grizzly Bears", 1);
        assertCounterCount(playerA, "Marchesa, the Black Rose", CounterType.P1P1, 1);
    }

    @Test
    public void test_Panharmonicon_CardEntersBattlefield_TriggersAdditionalTime() {
        // Whenever one or more artifact and/or creature cards leave your graveyard during your turn,
        // put a +1/+1 counter on target creature you control.
        addCard(Zone.BATTLEFIELD, playerA, "Thran Vigil");
        addCard(Zone.BATTLEFIELD, playerA, "Ornithopter", 1); // 0/2
        //
        // If an artifact or creature entering the battlefield causes a triggered ability of a permanent you control to trigger, that ability triggers an additional time.
        addCard(Zone.BATTLEFIELD, playerA, "Panharmonicon");
        //
        // Put target creature card from a graveyard onto the battlefield under your control.
        // You lose life equal to its mana value.
        addCard(Zone.HAND, playerA, "Reanimate"); // {B}
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 1);
        addCard(Zone.GRAVEYARD, playerA, "Grizzly Bears", 1);

        // creature card leaves graveyard by entering the battlefield - one trigger + one additional trigger
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Reanimate", "Grizzly Bears");
        setChoice(playerA, "Whenever one or more artifact and/or creature cards"); // two triggers order
        addTarget(playerA, "Ornithopter"); // Thran Vigil's counter
        addTarget(playerA, "Ornithopter"); // Thran Vigil's counter

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Grizzly Bears", 1);
        assertCounterCount(playerA, "Ornithopter", CounterType.P1P1, 2);
    }

    @Test
    public void test_Panharmonicon_CardExiled_NoAdditionalTrigger() {
        // Whenever one or more artifact and/or creature cards leave your graveyard during your turn,
        // put a +1/+1 counter on target creature you control.
        addCard(Zone.BATTLEFIELD, playerA, "Thran Vigil");
        addCard(Zone.BATTLEFIELD, playerA, "Ornithopter", 1); // 0/2
        //
        // If an artifact or creature entering the battlefield causes a triggered ability of a permanent you control to trigger, that ability triggers an additional time.
        addCard(Zone.BATTLEFIELD, playerA, "Panharmonicon");
        //
        // {T}: Target player exiles a card from their graveyard.
        addCard(Zone.BATTLEFIELD, playerA, "Relic of Progenitus");
        addCard(Zone.GRAVEYARD, playerA, "Grizzly Bears", 1);

        // creature card leaves graveyard to exile - normal trigger only
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Target player exiles", playerA);
        addTarget(playerA, "Grizzly Bears"); // card to exile
        addTarget(playerA, "Ornithopter"); // Thran Vigil's counter

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertExileCount(playerA, "Grizzly Bears", 1);
        assertCounterCount(playerA, "Ornithopter", CounterType.P1P1, 1);
    }
}
