package org.mage.test.cards.single.m21;

import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class BasriDevotedPaladinTest extends CardTestPlayerBase {

    private static final String basriDevotedPaladin = "Basri, Devoted Paladin";
    // +1: Put a +1/+1 counter on up to one target creature. It gains vigilance until end of turn.
    // −1: Whenever a creature attacks this turn, put a +1/+1 counter on it.
    // −6: Creatures you control get +2/+2 and gain flying until end of turn.

    @Test
    public void testAddCounter(){
        // Loyalty 4
        // +1: Put a +1/+1 counter on up to one target creature. It gains vigilance until end of turn.
        addCard(Zone.BATTLEFIELD, playerA, basriDevotedPaladin,1);
        addCard(Zone.BATTLEFIELD, playerA, "Savannah Lions");

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "+1: ");
        addTarget(playerA, "Savannah Lions");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertAbility(playerA, "Savannah Lions", VigilanceAbility.getInstance(), true);
        assertCounterCount(playerA, "Savannah Lions", CounterType.P1P1, 1);
    }

    @Test
    public void testAttackTrigger(){
        // Loyalty 4
        // −1: Whenever a creature attacks this turn, put a +1/+1 counter on it.
        addCard(Zone.BATTLEFIELD, playerA, basriDevotedPaladin,1);
        addCard(Zone.BATTLEFIELD, playerA, "Savannah Lions");

        activateAbility(3, PhaseStep.PRECOMBAT_MAIN, playerA, "-1: ");
        attack(3, playerA, "Savannah Lions");

        setStrictChooseMode(true);
        setStopAt(5, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertLife(playerB, 17);
        assertCounterCount(playerA, "Savannah Lions", CounterType.P1P1, 1);
    }

    @Test
    public void testUltimate(){
        // Loyalty 4
        // −6: Creatures you control get +2/+2 and gain flying until end of turn.
        addCard(Zone.BATTLEFIELD, playerA, basriDevotedPaladin,1);
        addCard(Zone.BATTLEFIELD, playerA, "Savannah Lions");

        //turn 1, Loyalty = 5
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "+1: ");
        addTarget(playerA, "Savannah Lions");

        // turn 3, Loyalty = 6
        activateAbility(3, PhaseStep.PRECOMBAT_MAIN, playerA, "+1: ");
        addTarget(playerA, "Savannah Lions");

        activateAbility(5, PhaseStep.PRECOMBAT_MAIN, playerA, "-6: ");

        setStrictChooseMode(true);
        setStopAt(5, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPowerToughness(playerA, "Savannah Lions", 6, 5);
        assertAbility(playerA, "Savannah Lions", FlyingAbility.getInstance(), true);
    }

}
