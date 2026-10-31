package org.mage.test.cards.battle;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;

/**
 * @author TheElk801, JayDi85
 */
public class BattleDuelTest extends BattleBaseTest {

    @Test
    public void testRegularCastAndTrigger() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 3);
        addCard(Zone.HAND, playerA, belenon);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, belenon);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertBattle(playerA, playerB, belenon);
        assertPermanentCount(playerA, "Knight Token", 1);
    }

    @Test
    public void testAttackBattle() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 3);
        addCard(Zone.BATTLEFIELD, playerA, bear);
        addCard(Zone.HAND, playerA, belenon);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, belenon);

        attack(1, playerA, bear, belenon);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertBattle(playerA, playerB, belenon);
        assertPermanentCount(playerA, "Knight Token", 1);
        assertTapped(bear, true);
        assertLife(playerB, 20);
        assertCounterCount(belenon, CounterType.DEFENSE, 5 - 2);
    }

    @Test
    public void testAttackBattleBlock() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 3);
        addCard(Zone.BATTLEFIELD, playerA, bear);
        addCard(Zone.BATTLEFIELD, playerB, bear);
        addCard(Zone.HAND, playerA, belenon);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, belenon);

        attack(1, playerA, bear, belenon);
        block(1, playerB, bear, bear);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertBattle(playerA, playerB, belenon);
        assertPermanentCount(playerA, "Knight Token", 1);
        assertPermanentCount(playerA, bear, 0);
        assertGraveyardCount(playerA, bear, 1);
        assertPermanentCount(playerB, bear, 0);
        assertGraveyardCount(playerB, bear, 1);
        assertLife(playerB, 20);
        assertCounterCount(belenon, CounterType.DEFENSE, 5);
    }

    @Test
    public void testCantAttackBattleYouProtect() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 3);
        addCard(Zone.BATTLEFIELD, playerB, bear);
        addCard(Zone.HAND, playerA, belenon);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, belenon);

        attack(2, playerB, bear, belenon);

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.END_TURN);
        execute();

        assertBattle(playerA, playerB, belenon);
        assertPermanentCount(playerA, "Knight Token", 1);
        assertTapped(bear, false);
        assertLife(playerB, 20);
        assertCounterCount(belenon, CounterType.DEFENSE, 5);
    }

    @Test
    public void testChangeControl() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 3);
        addCard(Zone.BATTLEFIELD, playerB, "Island", 6);
        addCard(Zone.HAND, playerB, confiscate);
        addCard(Zone.HAND, playerA, belenon);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, belenon);

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, confiscate, belenon);

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.END_TURN);
        execute();

        assertBattle(playerB, playerA, belenon);
        assertPermanentCount(playerA, "Knight Token", 1);
    }

    @Test
    public void testDefeated() {
        addCard(Zone.BATTLEFIELD, playerA, "Plateau", 3 + 6);
        addCard(Zone.HAND, playerA, belenon);
        addCard(Zone.HAND, playerA, impact);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, belenon);

        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, impact, belenon);
        setChoice(playerA, true); // yes to cast it transformed

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, belenon, 0);
        assertPermanentCount(playerA, warAnthem, 1);
        assertPermanentCount(playerA, "Knight Token", 1);
        assertPowerToughness(playerA, "Knight Token", 2 + 1, 2 + 1);
    }

    @Test
    public void testDefeatedStifle() {
        addCard(Zone.BATTLEFIELD, playerA, "Volcanic Island", 2 + 6 + 1);
        addCard(Zone.HAND, playerA, kaladesh);
        addCard(Zone.HAND, playerA, impact);
        addCard(Zone.HAND, playerA, stifle);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, kaladesh);

        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, impact, kaladesh);
        waitStackResolved(1, PhaseStep.POSTCOMBAT_MAIN, playerA, true);

        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, stifle, "stack ability");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, kaladesh, 0);
        assertGraveyardCount(playerA, kaladesh, 1);
        assertPermanentCount(playerA, "Thopter Token", 1);
    }

    @Test
    public void testSpellCardTypeTrigger() {
        addCard(Zone.BATTLEFIELD, playerA, "Plateau", 3 + 6);
        addCard(Zone.BATTLEFIELD, playerA, "Oketra's Monument");
        addCard(Zone.BATTLEFIELD, playerA, "Deeproot Champion");
        addCard(Zone.HAND, playerA, "Invasion of Dominaria");
        addCard(Zone.HAND, playerA, impact);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Invasion of Dominaria");

        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, impact, "Invasion of Dominaria");
        setChoice(playerA, true); // yes to cast it transformed

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Serra Faithkeeper", 1);
        assertPermanentCount(playerA, "Warrior Token", 1);
        assertPowerToughness(playerA, "Deeproot Champion", 3, 3);
    }

    @Test
    public void test_AI_CastBattle() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 3);
        addCard(Zone.HAND, playerA, belenon);

        aiPlayPriority(1, PhaseStep.PRECOMBAT_MAIN, playerA);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertBattle(playerA, playerB, belenon);
        assertPermanentCount(playerA, "Knight Token", 1);
    }

    @Test
    public void test_AI_AttackPriority_TargetBattleInsteadPlayer() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 3);
        addCard(Zone.BATTLEFIELD, playerA, bear);
        addCard(Zone.HAND, playerA, belenon);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, belenon);

        // ai must attack planeswalker instead player
        aiPlayStep(1, PhaseStep.DECLARE_ATTACKERS, playerA);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertBattle(playerA, playerB, belenon);
        assertPermanentCount(playerA, "Knight Token", 1);
        assertTapped(bear, true);
        assertLife(playerB, 20);
        assertCounterCount(belenon, CounterType.DEFENSE, 5 - 2);
    }

    @Test
    public void test_AI_AttackPriority_TargetPlaneswalkerInsteadBattle() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 3);
        addCard(Zone.BATTLEFIELD, playerA, bear);
        addCard(Zone.BATTLEFIELD, playerB, fayden); // 3
        addCard(Zone.HAND, playerA, belenon);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, belenon);

        // ai must attack planeswalker instead battle/player
        aiPlayStep(1, PhaseStep.DECLARE_ATTACKERS, playerA);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertBattle(playerA, playerB, belenon);
        assertPermanentCount(playerA, "Knight Token", 1);
        assertTapped(bear, true);
        assertLife(playerB, 20);
        assertCounterCount(belenon, CounterType.DEFENSE, 5);
        assertCounterCount(fayden, CounterType.LOYALTY, 3 - 2);
    }

    @Test
    public void test_AI_MustNotAttackMultipleTargets() {
        // bug with multiple targets for single attacker: https://github.com/magefree/mage/issues/7434
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 3);
        addCard(Zone.BATTLEFIELD, playerA, bearWithFlyingAndVigilance);
        addCard(Zone.BATTLEFIELD, playerB, fayden); // 3
        addCard(Zone.HAND, playerA, belenon);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, belenon);

        // ai must attack planeswalker instead battle/player
        // ai must attack only single target
        aiPlayStep(1, PhaseStep.DECLARE_ATTACKERS, playerA);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertBattle(playerA, playerB, belenon);
        assertPermanentCount(playerA, "Knight Token", 1);
        assertTapped(bearWithFlyingAndVigilance, false); // vigilance
        assertLife(playerB, 20);
        assertCounterCount(belenon, CounterType.DEFENSE, 5);
        assertCounterCount(fayden, CounterType.LOYALTY, 3 - 2);
    }

    @Test
    public void test_Evasion_AllAbilitiesMustBeSatisfied() {
        // 509.1b - evasion abilities are cumulative, also for creatures attacking a battle
        // flying + menace attacker can't be blocked by a flyer and a non-flyer
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Dimir Spybug"); // 1/1 flying, menace
        addCard(Zone.BATTLEFIELD, playerB, bearWithFlyingAndVigilance);
        addCard(Zone.BATTLEFIELD, playerB, bear);
        addCard(Zone.HAND, playerA, belenon);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, belenon);

        attack(1, playerA, "Dimir Spybug", belenon);
        block(1, playerB, bearWithFlyingAndVigilance, "Dimir Spybug");
        block(1, playerB, bear, "Dimir Spybug");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertBattle(playerA, playerB, belenon);
        assertPermanentCount(playerA, "Dimir Spybug", 1);
        assertCounterCount(belenon, CounterType.DEFENSE, 5 - 1);
    }

    @Test
    public void test_Evasion_AllAbilitiesSatisfied() {
        // 509.1b - two flyers can block a flying + menace attacker
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Dimir Spybug"); // 1/1 flying, menace
        addCard(Zone.BATTLEFIELD, playerB, bearWithFlyingAndVigilance);
        addCard(Zone.BATTLEFIELD, playerB, "Wind Drake");
        addCard(Zone.HAND, playerA, belenon);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, belenon);

        attack(1, playerA, "Dimir Spybug", belenon);
        block(1, playerB, bearWithFlyingAndVigilance, "Dimir Spybug");
        block(1, playerB, "Wind Drake", "Dimir Spybug");
        setChoiceAmount(playerA, 1, 0); // assign damage to blockers

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertBattle(playerA, playerB, belenon);
        assertGraveyardCount(playerA, "Dimir Spybug", 1);
        assertCounterCount(belenon, CounterType.DEFENSE, 5);
    }

    @Test
    public void test_ChangeControlDuringCombat_RemovedFromCombat() {
        // 506.4 - a permanent is removed from combat if its controller or protector changes
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 3);
        addCard(Zone.BATTLEFIELD, playerA, bear);
        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 5);
        addCard(Zone.HAND, playerB, "Word of Seizing"); // gain control of target permanent until end of turn
        addCard(Zone.HAND, playerA, belenon);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, belenon);

        attack(1, playerA, bear, belenon);
        castSpell(1, PhaseStep.DECLARE_ATTACKERS, playerB, "Word of Seizing", belenon);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_COMBAT);
        execute();

        // new controller can't protect a Siege (704.5y), so it chooses its opponent
        assertBattle(playerB, playerA, belenon);
        // battle left combat, so attacker deals no damage to it
        assertCounterCount(belenon, CounterType.DEFENSE, 5);
        assertLife(playerB, 20);
    }
}
