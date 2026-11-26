package org.mage.test.cards.single.msc;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author JayDi85
 */
public class MarvelBoyNohVarrTest extends CardTestPlayerBase {

    /**
     * {@link mage.cards.m.MarvelBoyNohVarr Marvel Boy, Noh-Varr} {1}{R/W}
     * Legendary Creature - Kree Soldier Hero 1/1
     * Whenever another creature you control enters and whenever you activate a power-up ability, put a +1/+1 counter
     * on Marvel Boy.
     */
    private static final String marvelBoy = "Marvel Boy, Noh-Varr";

    @Test
    public void test_PowerUpActivated_Counter() {
        addCard(Zone.BATTLEFIELD, playerA, marvelBoy);
        //
        addCard(Zone.BATTLEFIELD, playerA, "Brave Brawler"); // Power-up - {4}{W}: Put two +1/+1 counters on this creature.
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 5);

        // turn 3 - power-up costs less on the turn the creature entered
        activateAbility(3, PhaseStep.PRECOMBAT_MAIN, playerA, "Power-up &mdash; {4}{W}");

        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertCounterCount(playerA, marvelBoy, CounterType.P1P1, 1);
        assertCounterCount(playerA, "Brave Brawler", CounterType.P1P1, 2);
    }

    @Test
    public void test_OtherAbilityActivated_NoCounter() {
        addCard(Zone.BATTLEFIELD, playerA, marvelBoy);
        //
        addCard(Zone.BATTLEFIELD, playerA, "Prodigal Pyromancer"); // {T}: This creature deals 1 damage to any target.

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: {this} deals", playerB);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertLife(playerB, 20 - 1);
        assertCounterCount(playerA, marvelBoy, CounterType.P1P1, 0);
    }
}
