package org.mage.test.cards.single.one;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.player.TestPlayer;
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
        // Landfall — Whenever a land you control enters,
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

    @Test
    public void test_EleshNorn_DoNotStopDiesBatchTriggerOfDiedPermanent() {
        // possible bug: game error on batch event usage (Wrong code usage. Must search value from a getEvents list)

        // Permanents entering the battlefield don't cause abilities of permanents your opponents control to trigger.
        addCard(Zone.BATTLEFIELD, playerB, "Elesh Norn, Mother of Machines"); // mana value 5
        //
        // Whenever one or more other creatures die, scry 1.
        addCard(Zone.BATTLEFIELD, playerA, "Great Fierce Bee"); // mana value 3
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears"); // mana value 2
        //
        // Destroy all creatures with converted mana cost 3 or less.
        addCard(Zone.HAND, playerA, "Ritual of Soot"); // {2}{B}{B}
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 4);

        // bee and bears die together, bee must trigger from the graveyard (look back in time)
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Ritual of Soot");
        addTarget(playerA, TestPlayer.TARGET_SKIP); // scry: keep on top

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerA, "Great Fierce Bee", 1);
        assertGraveyardCount(playerA, "Grizzly Bears", 1);
        assertPermanentCount(playerB, "Elesh Norn, Mother of Machines", 1);
    }

    @Test
    public void test_EleshNorn_DoNotStopDamageBatchTriggerOfInitiative() {
        // possible bug: game error on batch event usage (Wrong code usage. Must search value from a getEvents list)
        removeAllCardsFromLibrary(playerA);
        removeAllCardsFromLibrary(playerB);
        addCard(Zone.LIBRARY, playerA, "Island", 3);
        addCard(Zone.LIBRARY, playerB, "Plains", 3);

        // When Aarakocra Sneak enters the battlefield, you take the initiative.
        addCard(Zone.HAND, playerA, "Aarakocra Sneak"); // {3}{U}
        addCard(Zone.BATTLEFIELD, playerA, "Island", 4);
        //
        // Permanents entering the battlefield don't cause abilities of permanents your opponents control to trigger.
        addCard(Zone.HAND, playerB, "Elesh Norn, Mother of Machines"); // {4}{W}
        addCard(Zone.BATTLEFIELD, playerB, "Plains", 5);
        addCard(Zone.BATTLEFIELD, playerB, "Grizzly Bears", 1);

        // A takes the initiative
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Aarakocra Sneak");
        addTarget(playerA, "Island"); // venture: Secret Entrance

        // B steals the initiative by combat damage with Elesh Norn on the battlefield
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Elesh Norn, Mother of Machines");
        attack(2, playerB, "Grizzly Bears", playerA);
        addTarget(playerB, "Plains"); // venture: Secret Entrance

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 20 - 2);
        Assert.assertEquals("initiative must be taken by combat damage", playerB.getId(), currentGame.getInitiativeId());
    }

    @Test
    public void test_EleshNorn_PreventOneOrMoreTokensEnter() {
        // Permanents entering the battlefield don't cause abilities of permanents your opponents control to trigger.
        addCard(Zone.BATTLEFIELD, playerB, "Elesh Norn, Mother of Machines");
        //
        // Whenever one or more tokens enter the battlefield under your control, put that many +1/+1 counters on Woodland Champion.
        addCard(Zone.BATTLEFIELD, playerA, "Woodland Champion"); // 2/2
        //
        // Create two 1/1 white Soldier creature tokens.
        addCard(Zone.HAND, playerA, "Raise the Alarm"); // {1}{W}
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Raise the Alarm");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Soldier Token", 2);
        assertPowerToughness(playerA, "Woodland Champion", 2, 2);
    }
}
