package org.mage.test.cards.single.msc;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestCommander4Players;

/**
 * @author DarioBarrabinoDev
 */
public class FightForTheThroneTest extends CardTestCommander4Players {

    // Put a +1/+1 counter on target creature you control. Then it fights target creature an opponent controls.
    // When the creature an opponent controls dies this turn, if you control your commander, you become the monarch.
    private static final String fight = "Fight for the Throne"; // {1}{G} instant

    private static final String commander = "Balduvian Bears"; // {1}{G} 2/2
    private static final String lion = "Silvercoat Lion"; // 2/2
    private static final String bears = "Grizzly Bears"; // 2/2
    private static final String wurm = "Craw Wurm"; // 6/4
    private static final String bolt = "Lightning Bolt";

    @Test
    public void test_OpponentCreatureDiesInFight_ControlCommander_BecomeMonarch() {
        // Player order: A -> D -> C -> B
        setStrictChooseMode(true);

        addCard(Zone.COMMAND, playerA, commander);
        addCard(Zone.HAND, playerA, fight);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 4);
        addCard(Zone.BATTLEFIELD, playerA, lion);
        addCard(Zone.BATTLEFIELD, playerB, bears);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, commander, true);
        checkMonarch("no monarch before", 1, PhaseStep.PRECOMBAT_MAIN, playerA, null);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, fight, lion + "^" + bears);
        checkMonarch("monarch after", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, playerA);

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertCounterCount(playerA, lion, CounterType.P1P1, 1);
        assertDamageReceived(playerA, lion, 2);
        assertGraveyardCount(playerB, bears, 1);
    }

    @Test
    public void test_OpponentCreatureDiesInFight_CommanderNotOnBattlefield_NoMonarch() {
        setStrictChooseMode(true);

        addCard(Zone.COMMAND, playerA, commander);
        addCard(Zone.HAND, playerA, fight);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2);
        addCard(Zone.BATTLEFIELD, playerA, lion);
        addCard(Zone.BATTLEFIELD, playerB, bears);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, fight, lion + "^" + bears);
        checkMonarch("no monarch", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, null);

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertCounterCount(playerA, lion, CounterType.P1P1, 1);
        assertGraveyardCount(playerB, bears, 1);
        assertCommandZoneCount(playerA, commander, 1);
    }

    @Test
    public void test_OpponentCreatureSurvivesFight_DiesLaterThisTurn_BecomeMonarch() {
        setStrictChooseMode(true);

        addCard(Zone.COMMAND, playerA, commander);
        addCard(Zone.HAND, playerA, fight);
        addCard(Zone.HAND, playerA, bolt);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 4);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 3);
        addCard(Zone.BATTLEFIELD, playerA, lion);
        addCard(Zone.BATTLEFIELD, playerB, wurm);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, commander, true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, fight, lion + "^" + wurm, true);
        // own creature dying in the fight doesn't matter
        checkGraveyardCount("lion died", 1, PhaseStep.PRECOMBAT_MAIN, playerA, lion, 1);
        checkMonarch("wurm survived", 1, PhaseStep.PRECOMBAT_MAIN, playerA, null);

        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, bolt, wurm);
        checkMonarch("wurm died", 1, PhaseStep.END_TURN, playerA, playerA);

        setStopAt(1, PhaseStep.CLEANUP);
        execute();

        assertGraveyardCount(playerB, wurm, 1);
    }

    @Test
    public void test_OpponentCreatureDiesNextTurn_NoMonarch() {
        setStrictChooseMode(true);

        addCard(Zone.COMMAND, playerA, commander);
        addCard(Zone.HAND, playerA, fight);
        addCard(Zone.HAND, playerA, bolt, 2);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 4);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 4);
        addCard(Zone.BATTLEFIELD, playerA, lion);
        addCard(Zone.BATTLEFIELD, playerB, wurm);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, commander, true);
        castSpell(1, PhaseStep.END_TURN, playerA, fight, lion + "^" + wurm);

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerA, bolt, wurm);
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerA, bolt, wurm);
        checkGraveyardCount("wurm died", 2, PhaseStep.POSTCOMBAT_MAIN, playerB, wurm, 1);
        checkMonarch("no monarch", 2, PhaseStep.POSTCOMBAT_MAIN, playerD, null);

        setStopAt(2, PhaseStep.END_TURN);
        execute();
    }

    @Test
    public void test_OwnCreatureIllegalTarget_NoFight_StillWatchesOpponentCreature() {
        setStrictChooseMode(true);

        addCard(Zone.COMMAND, playerA, commander);
        addCard(Zone.HAND, playerA, fight);
        addCard(Zone.HAND, playerA, bolt, 2);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 4);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 4);
        addCard(Zone.BATTLEFIELD, playerA, lion);
        addCard(Zone.BATTLEFIELD, playerB, bears);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, commander, true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, fight, lion + "^" + bears);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, bolt, lion, fight);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkPermanentCount("bears not fought", 1, PhaseStep.PRECOMBAT_MAIN, playerA, playerB, bears, 1);
        checkMonarch("bears alive", 1, PhaseStep.PRECOMBAT_MAIN, playerA, null);

        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, bolt, bears);
        checkMonarch("bears died", 1, PhaseStep.END_TURN, playerA, playerA);

        setStopAt(1, PhaseStep.CLEANUP);
        execute();

        assertGraveyardCount(playerA, lion, 1);
        assertGraveyardCount(playerB, bears, 1);
    }

    @Test
    public void test_OpponentCreatureIllegalTarget_CounterButNoFight_NoMonarch() {
        setStrictChooseMode(true);

        addCard(Zone.COMMAND, playerA, commander);
        addCard(Zone.HAND, playerA, fight);
        addCard(Zone.HAND, playerA, "Languish"); // {2}{B}{B} sorcery, all creatures get -4/-4 until end of turn
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 4);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 4);
        addCard(Zone.BATTLEFIELD, playerA, lion);
        addCard(Zone.BATTLEFIELD, playerB, bears);
        // {G} instant: target creature you control gets +1/+1 and gains hexproof until end of turn
        addCard(Zone.HAND, playerB, "Ranger's Guile");
        addCard(Zone.BATTLEFIELD, playerB, "Forest", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, commander, true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, fight, lion + "^" + bears);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Ranger's Guile", bears, fight);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkPermanentCounters("counter on lion", 1, PhaseStep.PRECOMBAT_MAIN, playerA, lion, CounterType.P1P1, 1);
        checkDamage("lion not fought", 1, PhaseStep.PRECOMBAT_MAIN, playerA, lion, 0);
        checkDamage("bears not fought", 1, PhaseStep.PRECOMBAT_MAIN, playerB, bears, 0);

        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Languish");
        setChoice(playerA, true); // commander to command zone
        waitStackResolved(1, PhaseStep.POSTCOMBAT_MAIN);
        checkGraveyardCount("bears died", 1, PhaseStep.POSTCOMBAT_MAIN, playerB, bears, 1);
        checkMonarch("no monarch", 1, PhaseStep.END_TURN, playerA, null);

        setStopAt(1, PhaseStep.CLEANUP);
        execute();
    }

    @Test
    public void test_TakeMonarchFromAnotherOpponent() {
        // Player order: A -> D -> C -> B
        setStrictChooseMode(true);

        addCard(Zone.COMMAND, playerA, commander);
        addCard(Zone.HAND, playerA, fight);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 4);
        addCard(Zone.BATTLEFIELD, playerA, lion);
        addCard(Zone.BATTLEFIELD, playerB, bears);
        // When Thorn of the Black Rose enters, you become the monarch.
        addCard(Zone.HAND, playerD, "Thorn of the Black Rose");
        addCard(Zone.BATTLEFIELD, playerD, "Swamp", 4);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, commander);

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerD, "Thorn of the Black Rose", true);
        checkMonarch("D is monarch", 2, PhaseStep.PRECOMBAT_MAIN, playerD, playerD);
        castSpell(2, PhaseStep.POSTCOMBAT_MAIN, playerA, fight, lion + "^" + bears);
        checkMonarch("A took monarch", 2, PhaseStep.END_TURN, playerD, playerA);

        setStopAt(2, PhaseStep.CLEANUP);
        execute();

        assertGraveyardCount(playerB, bears, 1);
    }
}
