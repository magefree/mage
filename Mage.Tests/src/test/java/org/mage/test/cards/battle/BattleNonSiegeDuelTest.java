package org.mage.test.cards.battle;

import mage.abilities.common.DealtDamageToSourceTriggeredAbility;
import mage.abilities.effects.common.GainLifeEffect;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Ignore;
import org.junit.Test;

/**
 * Battles without the Siege subtype (CR 20260807, see #15875)
 *
 * @author DarioBarrabinoDev
 */
public class BattleNonSiegeDuelTest extends BattleBaseTest {

    @Test
    @Ignore("TODO: 310.9a - a battle without battle types must be protected by its controller, but engine chooses an opponent")
    public void test_NoBattleType_ControllerIsProtector() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 1);
        addTypelessBattleToHand(playerA, 3, null);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, typelessBattle);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertBattle(playerA, playerA, typelessBattle);
        assertCounterCount(typelessBattle, CounterType.DEFENSE, 3);
    }

    @Test
    @Ignore("TODO: 310.9b - opponent must be able to attack a battle protected by its controller (depends on 310.9a)")
    public void test_NoBattleType_OpponentCanAttack() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 1);
        addCard(Zone.BATTLEFIELD, playerB, bear);
        addTypelessBattleToHand(playerA, 3, null);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, typelessBattle);

        attack(2, playerB, bear, typelessBattle);

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.END_TURN);
        execute();

        assertBattle(playerA, playerA, typelessBattle);
        assertLife(playerA, 20);
        assertCounterCount(typelessBattle, CounterType.DEFENSE, 3 - 2);
    }

    @Test
    @Ignore("TODO: 704.5y - after control change only the new controller can protect a battle without battle types")
    public void test_NoBattleType_ChangeControl() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Island", 6);
        addCard(Zone.HAND, playerB, confiscate);
        addTypelessBattleToHand(playerA, 3, null);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, typelessBattle);

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, confiscate, typelessBattle);

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.END_TURN);
        execute();

        assertBattle(playerB, playerB, typelessBattle);
    }

    @Test
    public void test_DefenseZero_GoesToGraveyard() {
        // 310.8, 704.5w - non-Siege battle with 0 defense is put into its owner's graveyard
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);
        addCard(Zone.HAND, playerA, "Lightning Bolt");
        addTypelessBattleToHand(playerA, 3, null);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, typelessBattle);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", typelessBattle);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, typelessBattle, 0);
        assertGraveyardCount(playerA, typelessBattle, 1);
        assertExileCount(playerA, typelessBattle, 0);
    }

    @Test
    @Ignore("TODO: 704.5w - unlike 704.5v (Siege), non-Siege battle with 0 defense dies even with its own waiting triggers")
    public void test_DefenseZero_GoesToGraveyardWithWaitingTrigger() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);
        addCard(Zone.HAND, playerA, "Lightning Bolt");
        addTypelessBattleToHand(playerA, 3, new DealtDamageToSourceTriggeredAbility(new GainLifeEffect(1), false));

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, typelessBattle);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", typelessBattle);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, playerA, true);
        // own trigger is on the stack now
        checkStackSize("trigger on stack", 1, PhaseStep.PRECOMBAT_MAIN, playerA, 1);
        checkPermanentCount("battle died before trigger resolved", 1, PhaseStep.PRECOMBAT_MAIN, playerA, typelessBattle, 0);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerA, typelessBattle, 1);
        assertLife(playerA, 20 + 1);
    }
}
