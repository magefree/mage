package org.mage.test.cards.single.trk;

import mage.constants.CardType;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author JayDi85
 */
public class DeepSpace9Test extends CardTestPlayerBase {

    @Test
    public void test_EntersTrigger_PutPermanentCardsOntoBattlefield() {
        skipInitShuffling();

        // When Deep Space 9 enters and at the beginning of your upkeep, exile the top four cards of your library.
        // Put all permanent cards exiled this way onto the battlefield.
        addCard(Zone.HAND, playerA, "Deep Space 9"); // {9}
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 9);
        //
        // library, last added on top
        addCard(Zone.LIBRARY, playerA, "Hill Giant"); // fifth card
        addCard(Zone.LIBRARY, playerA, "Grizzly Bears");
        addCard(Zone.LIBRARY, playerA, "Lightning Bolt");
        addCard(Zone.LIBRARY, playerA, "Plains");
        addCard(Zone.LIBRARY, playerA, "Divination");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Deep Space 9");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Grizzly Bears", 1);
        assertPermanentCount(playerA, "Plains", 1);
        assertExileCount(playerA, "Lightning Bolt", 1);
        assertExileCount(playerA, "Divination", 1);
        assertLibraryCount(playerA, "Hill Giant", 1);
    }

    @Test
    public void test_UpkeepTrigger_YourUpkeepOnly() {
        skipInitShuffling();

        // When Deep Space 9 enters and at the beginning of your upkeep, exile the top four cards of your library.
        // Put all permanent cards exiled this way onto the battlefield.
        addCard(Zone.BATTLEFIELD, playerA, "Deep Space 9");
        //
        // library, last added on top
        addCard(Zone.LIBRARY, playerA, "Swamp"); // draw on turn 3
        // turn 3 upkeep
        addCard(Zone.LIBRARY, playerA, "Hill Giant");
        addCard(Zone.LIBRARY, playerA, "Divination");
        addCard(Zone.LIBRARY, playerA, "Island");
        addCard(Zone.LIBRARY, playerA, "Elite Vanguard");
        // turn 1 upkeep
        addCard(Zone.LIBRARY, playerA, "Grizzly Bears");
        addCard(Zone.LIBRARY, playerA, "Lightning Bolt");
        addCard(Zone.LIBRARY, playerA, "Plains");
        addCard(Zone.LIBRARY, playerA, "Shock");

        // turn 1 - your upkeep
        checkPermanentCount("turn 1", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Grizzly Bears", 1);
        checkPermanentCount("turn 1", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Plains", 1);
        checkExileCount("turn 1", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", 1);
        checkExileCount("turn 1", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Shock", 1);

        // turn 2 - opponent's upkeep, no trigger
        checkLibraryCount("turn 2", 2, PhaseStep.END_TURN, playerA, "Hill Giant", 1);
        checkLibraryCount("turn 2", 2, PhaseStep.END_TURN, playerA, "Divination", 1);

        // turn 3 - your upkeep
        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, "Hill Giant", 1);
        assertPermanentCount(playerA, "Island", 1);
        assertPermanentCount(playerA, "Elite Vanguard", 1);
        assertExileCount(playerA, "Divination", 1);
        assertExileCount(playerA, 2 + 1);
        assertHandCount(playerA, "Swamp", 1); // the draw step is after the upkeep
    }

    @Test
    public void test_Station9_CreaturesYouControlGetBoost() {
        // Station (Tap another creature you control: Put charge counters equal to its power on this Spacecraft.
        // Station only as a sorcery.)
        // STATION 9+
        // Creatures you control get +3/+3.
        addCard(Zone.BATTLEFIELD, playerA, "Deep Space 9");
        addCard(Zone.BATTLEFIELD, playerA, "Quilled Slagwurm"); // 8/8
        addCard(Zone.BATTLEFIELD, playerA, "Memnite"); // 1/1
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears"); // 2/2
        //
        addCard(Zone.BATTLEFIELD, playerB, "Silvercoat Lion"); // 2/2

        // 8 counters - no boost
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Station");
        setChoice(playerA, "Quilled Slagwurm");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkPT("8 counters", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Grizzly Bears", 2, 2);

        // 8 + 1 counters - boost
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Station");
        setChoice(playerA, "Memnite");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertCounterCount(playerA, "Deep Space 9", CounterType.CHARGE, 8 + 1);
        assertPowerToughness(playerA, "Grizzly Bears", 2 + 3, 2 + 3);
        assertPowerToughness(playerA, "Quilled Slagwurm", 8 + 3, 8 + 3);
        assertPowerToughness(playerB, "Silvercoat Lion", 2, 2);
        assertType("Deep Space 9", CardType.CREATURE, false);
    }

    @Test
    public void test_EntersTrigger_ExileHappensBeforePermanentsEnter() {
        skipInitShuffling();

        // When Deep Space 9 enters and at the beginning of your upkeep, exile the top four cards of your library.
        // Put all permanent cards exiled this way onto the battlefield.
        addCard(Zone.HAND, playerA, "Deep Space 9"); // {9}
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 9);
        //
        // library, last added on top
        // Whenever one or more cards are put into exile during your turn, put a +1/+1 counter on this creature.
        // This ability triggers only once each turn.
        addCard(Zone.LIBRARY, playerA, "Stonebinder's Familiar"); // 1/1
        addCard(Zone.LIBRARY, playerA, "Lightning Bolt");
        addCard(Zone.LIBRARY, playerA, "Plains");
        addCard(Zone.LIBRARY, playerA, "Divination");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Deep Space 9");

        // the exile is a separate step, the familiar wasn't on the battlefield yet and must not see it
        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Stonebinder's Familiar", 1);
        assertCounterCount(playerA, "Stonebinder's Familiar", CounterType.P1P1, 0);
    }
}
