package org.mage.test.cards.single.msc;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * 702.12b
 * A permanent with indestructible can't be destroyed. Such permanents aren't destroyed by lethal damage, and they
 * ignore the lethal-damage state-based action.
 * <p>
 * 704.5g
 * If a creature has toughness greater than 0, and the total damage marked on it is greater than or equal to its
 * toughness, that creature has been dealt lethal damage and is destroyed.
 * <p>
 * 704.3
 * Whenever a player would get priority, the game checks for any of the listed conditions for state-based actions,
 * then performs all applicable state-based actions simultaneously as a single event. Then triggered abilities are put
 * on the stack. That is why a Hercules killed by the damage is already gone when his trigger resolves.
 *
 * @author DarioBarrabinoDev
 */
public class HerculesOlympianHeroTest extends CardTestPlayerBase {

    @Test
    public void test_Attacks_PutsCounterAndGainsIndestructible() {
        // Whenever Hercules attacks, put a +1/+1 counter on him. He gains indestructible until end of turn.
        addCard(Zone.BATTLEFIELD, playerA, "Hercules, Olympian Hero"); // 3/3
        //
        addCard(Zone.BATTLEFIELD, playerB, "Swamp", 2);
        addCard(Zone.HAND, playerB, "Doom Blade"); // {1}{B}

        attack(1, playerA, "Hercules, Olympian Hero");
        castSpell(1, PhaseStep.DECLARE_BLOCKERS, playerB, "Doom Blade", "Hercules, Olympian Hero"); // destroy the attacker

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertCounterCount(playerA, "Hercules, Olympian Hero", CounterType.P1P1, 1);
        assertPowerToughness(playerA, "Hercules, Olympian Hero", 4, 4);
        assertPermanentCount(playerA, "Hercules, Olympian Hero", 1); // indestructible, so it survives
        assertLife(playerB, 20 - 4);
    }

    @Test
    public void test_Attacks_SurvivesLethalDamage() {
        // Whenever Hercules attacks, put a +1/+1 counter on him. He gains indestructible until end of turn.
        addCard(Zone.BATTLEFIELD, playerA, "Hercules, Olympian Hero"); // 3/3
        //
        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 3);
        addCard(Zone.HAND, playerB, "Char"); // {1}{R}{R}, 4 damage to any target and 2 damage to you

        attack(1, playerA, "Hercules, Olympian Hero");
        castSpell(1, PhaseStep.DECLARE_BLOCKERS, playerB, "Char", "Hercules, Olympian Hero"); // lethal damage on the 4/4

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        // one counter from the attack trigger, four more from the damage trigger
        assertCounterCount(playerA, "Hercules, Olympian Hero", CounterType.P1P1, 1 + 4);
        assertPowerToughness(playerA, "Hercules, Olympian Hero", 8, 8);
        assertPermanentCount(playerA, "Hercules, Olympian Hero", 1);
        assertLife(playerB, 20 - 2 - 8); // Char hits its own controller, then the 8/8 connects
    }

    @Test
    public void test_DealtDamage_PutsThatManyCounters() {
        // Whenever Hercules is dealt damage for the first time each turn, put that many +1/+1 counters on him.
        addCard(Zone.BATTLEFIELD, playerA, "Hercules, Olympian Hero"); // 3/3
        //
        addCard(Zone.BATTLEFIELD, playerB, "Mountain");
        addCard(Zone.HAND, playerB, "Shock"); // {R}, 2 damage to any target

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Shock", "Hercules, Olympian Hero"); // 2 damage, not lethal

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertCounterCount(playerA, "Hercules, Olympian Hero", CounterType.P1P1, 2);
        assertPowerToughness(playerA, "Hercules, Olympian Hero", 5, 5);
        assertPermanentCount(playerA, "Hercules, Olympian Hero", 1);
    }

    @Test
    public void test_DealtDamage_OnlyFirstTimeEachTurn() {
        // Whenever Hercules is dealt damage for the first time each turn, put that many +1/+1 counters on him.
        addCard(Zone.BATTLEFIELD, playerA, "Hercules, Olympian Hero"); // 3/3
        //
        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 2);
        addCard(Zone.HAND, playerB, "Shock", 2); // {R}, 2 damage to any target

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Shock", "Hercules, Olympian Hero"); // first damage of the turn
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerB, "Shock", "Hercules, Olympian Hero"); // second damage, no trigger

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        // only the first damage gives counters, so 2 and not 4
        assertCounterCount(playerA, "Hercules, Olympian Hero", CounterType.P1P1, 2);
        assertPowerToughness(playerA, "Hercules, Olympian Hero", 5, 5);
        assertPermanentCount(playerA, "Hercules, Olympian Hero", 1); // 4 damage on a 5/5
    }

    @Test
    public void test_DealtDamage_DiesBeforeTheCounters() {
        // Whenever Hercules is dealt damage for the first time each turn, put that many +1/+1 counters on him.
        // (He must survive the damage to get the counters.)
        addCard(Zone.BATTLEFIELD, playerA, "Hercules, Olympian Hero"); // 3/3
        //
        addCard(Zone.BATTLEFIELD, playerB, "Mountain");
        addCard(Zone.HAND, playerB, "Lightning Bolt"); // {R}, 3 damage to any target

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Lightning Bolt", "Hercules, Olympian Hero"); // lethal damage

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Hercules, Olympian Hero", 0);
        assertGraveyardCount(playerA, "Hercules, Olympian Hero", 1); // destroyed before the trigger resolved
    }
}
