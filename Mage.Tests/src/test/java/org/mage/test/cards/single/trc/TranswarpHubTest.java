package org.mage.test.cards.single.trc;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.player.TestPlayer;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author DarioBarrabinoDev
 */
public class TranswarpHubTest extends CardTestPlayerBase {

    @Test
    public void test_EntersTrigger_PutTwoArtifactCardsInHand() {
        skipInitShuffling();

        // When this Spacecraft enters, mill five cards.
        // You may put up to two artifact cards from among them into your hand.
        addCard(Zone.HAND, playerA, "Transwarp Hub"); // {2}{U}
        addCard(Zone.BATTLEFIELD, playerA, "Island", 3);
        //
        // library, last added on top
        addCard(Zone.LIBRARY, playerA, "Hill Giant"); // sixth card, not milled
        addCard(Zone.LIBRARY, playerA, "Lightning Bolt");
        addCard(Zone.LIBRARY, playerA, "Grizzly Bears");
        addCard(Zone.LIBRARY, playerA, "Ornithopter");
        addCard(Zone.LIBRARY, playerA, "Memnite");
        addCard(Zone.LIBRARY, playerA, "Sol Ring");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Transwarp Hub");
        setChoice(playerA, "Sol Ring"); // first artifact card put into hand
        setChoice(playerA, "Memnite"); // second artifact card put into hand

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertHandCount(playerA, "Sol Ring", 1);
        assertHandCount(playerA, "Memnite", 1);
        assertGraveyardCount(playerA, "Ornithopter", 1);
        assertGraveyardCount(playerA, "Grizzly Bears", 1);
        assertGraveyardCount(playerA, "Lightning Bolt", 1);
        assertGraveyardCount(playerA, 3);
        assertLibraryCount(playerA, "Hill Giant", 1);
    }

    @Test
    public void test_EntersTrigger_PutOneArtifactCardInHand() {
        skipInitShuffling();

        // When this Spacecraft enters, mill five cards.
        // You may put up to two artifact cards from among them into your hand.
        addCard(Zone.HAND, playerA, "Transwarp Hub"); // {2}{U}
        addCard(Zone.BATTLEFIELD, playerA, "Island", 3);
        //
        // library, last added on top
        addCard(Zone.LIBRARY, playerA, "Hill Giant"); // sixth card, not milled
        addCard(Zone.LIBRARY, playerA, "Lightning Bolt");
        addCard(Zone.LIBRARY, playerA, "Grizzly Bears");
        addCard(Zone.LIBRARY, playerA, "Ornithopter");
        addCard(Zone.LIBRARY, playerA, "Memnite");
        addCard(Zone.LIBRARY, playerA, "Sol Ring");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Transwarp Hub");
        setChoice(playerA, "Sol Ring"); // the only artifact card put into hand
        setChoice(playerA, TestPlayer.CHOICE_SKIP); // take no second card

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertHandCount(playerA, "Sol Ring", 1);
        assertGraveyardCount(playerA, "Memnite", 1);
        assertGraveyardCount(playerA, 4);
        assertLibraryCount(playerA, "Hill Giant", 1);
    }

    @Test
    public void test_EntersTrigger_PutNoCardInHand() {
        skipInitShuffling();

        // When this Spacecraft enters, mill five cards.
        // You may put up to two artifact cards from among them into your hand.
        addCard(Zone.HAND, playerA, "Transwarp Hub"); // {2}{U}
        addCard(Zone.BATTLEFIELD, playerA, "Island", 3);
        //
        // library, last added on top
        addCard(Zone.LIBRARY, playerA, "Hill Giant"); // sixth card, not milled
        addCard(Zone.LIBRARY, playerA, "Lightning Bolt");
        addCard(Zone.LIBRARY, playerA, "Grizzly Bears");
        addCard(Zone.LIBRARY, playerA, "Ornithopter");
        addCard(Zone.LIBRARY, playerA, "Memnite");
        addCard(Zone.LIBRARY, playerA, "Sol Ring");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Transwarp Hub");
        setChoice(playerA, TestPlayer.CHOICE_SKIP); // take no card at all

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertHandCount(playerA, 0);
        assertGraveyardCount(playerA, "Sol Ring", 1);
        assertGraveyardCount(playerA, "Memnite", 1);
        assertGraveyardCount(playerA, 5);
        assertLibraryCount(playerA, "Hill Giant", 1);
    }

    @Test
    public void test_Station_PutChargeCountersEqualToPower() {
        // Station (Tap another creature you control: Put charge counters equal to its power on this Spacecraft.
        // Station only as a sorcery.)
        addCard(Zone.BATTLEFIELD, playerA, "Transwarp Hub");
        addCard(Zone.BATTLEFIELD, playerA, "Hill Giant"); // 3/3

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Station");
        setChoice(playerA, "Hill Giant"); // creature tapped to station

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertTapped("Hill Giant", true);
        assertCounterCount(playerA, "Transwarp Hub", CounterType.CHARGE, 3);
    }

    @Test
    public void test_Station3_AbilityOnlyAtThreshold() {
        // STATION 3+
        // {T}: Target creature can't be blocked this turn.
        addCard(Zone.BATTLEFIELD, playerA, "Transwarp Hub");
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears"); // 2/2
        addCard(Zone.BATTLEFIELD, playerA, "Memnite"); // 1/1

        // two charge counters, still below the threshold
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Station");
        setChoice(playerA, "Grizzly Bears"); // creature tapped to station
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkPlayableAbility("2 counters", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Target creature", false);

        // one more charge counter, the threshold is reached
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Station");
        setChoice(playerA, "Memnite"); // creature tapped to station
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkPlayableAbility("3 counters", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Target creature", true);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertCounterCount(playerA, "Transwarp Hub", CounterType.CHARGE, 2 + 1);
    }

    @Test
    public void test_Station3_TargetCreatureCantBeBlocked() {
        // STATION 3+
        // {T}: Target creature can't be blocked this turn.
        addCard(Zone.BATTLEFIELD, playerA, "Transwarp Hub");
        addCard(Zone.BATTLEFIELD, playerA, "Hill Giant"); // 3/3, tapped to station
        addCard(Zone.BATTLEFIELD, playerA, "Elite Vanguard"); // 2/1
        //
        addCard(Zone.BATTLEFIELD, playerB, "Silvercoat Lion"); // 2/2

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Station");
        setChoice(playerA, "Hill Giant"); // creature tapped to station
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Target creature", "Elite Vanguard");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        attack(1, playerA, "Elite Vanguard", playerB);
        block(1, playerB, "Silvercoat Lion", "Elite Vanguard"); // illegal block, must be ignored

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerB, 20 - 2);
        assertPermanentCount(playerB, "Silvercoat Lion", 1);
        assertTapped("Transwarp Hub", true);
    }
}
