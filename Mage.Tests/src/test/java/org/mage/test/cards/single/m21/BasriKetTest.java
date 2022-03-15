package org.mage.test.cards.single.m21;

import mage.abilities.keyword.IndestructibleAbility;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class BasriKetTest extends CardTestPlayerBase {

    private static final String basriKet = "Basri Ket";

    @Test
    public void addCounterOnCreature(){
        addCard(Zone.BATTLEFIELD, playerA, basriKet);
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears");

        // +1: Put a +1/+1 counter on up to one target creature. It gains indestructible until end of turn.
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "+1: ");
        addTarget(playerA, "Grizzly Bears");

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        setStrictChooseMode(true);
        execute();
        assertAllCommandsUsed();

        assertCounterCount(playerA, "Grizzly Bears", CounterType.P1P1, 1);
        assertAbility(playerA, "Grizzly Bears", IndestructibleAbility.getInstance(), true);
    }

    @Test
    public void attack(){
        addCard(Zone.BATTLEFIELD, playerA, basriKet);
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears");

        // −2: Whenever one or more nontoken creatures attack this turn, create that many 1/1 white Soldier creature tokens that are tapped and attacking.
        activateAbility(3, PhaseStep.PRECOMBAT_MAIN, playerA, "-2: ");
        attack(3, playerA, "Grizzly Bears");
        setStopAt(3, PhaseStep.POSTCOMBAT_MAIN);
        setStrictChooseMode(true);
        execute();
        assertAllCommandsUsed();

        assertLife(playerB, 17);
        assertPermanentCount(playerA, "Soldier Token", 1);
    }

    @Test
    public void emblem(){
        addCard(Zone.BATTLEFIELD, playerA, basriKet);
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears");

        // turn 1: loyalty 4
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "+1: ");
        addTarget(playerA, "Grizzly Bears");
        // turn 3: loyalty 5
        activateAbility(3, PhaseStep.PRECOMBAT_MAIN, playerA, "+1: ");
        addTarget(playerA, "Grizzly Bears");
        // turn 5: loyalty 6
        activateAbility(5, PhaseStep.PRECOMBAT_MAIN, playerA, "+1: ");
        addTarget(playerA, "Grizzly Bears");

        // −6: You get an emblem with "At the beginning of combat on your turn, create a 1/1 white Soldier creature token, then put a +1/+1 counter on each creature you control."
        activateAbility(7, PhaseStep.PRECOMBAT_MAIN, playerA, "-6: ");
        attack(7, playerA, "Grizzly Bears");
        setStopAt(7, PhaseStep.POSTCOMBAT_MAIN);
        setStrictChooseMode(true);
        execute();
        assertAllCommandsUsed();

        assertLife(playerB, 14);
        assertPermanentCount(playerA, "Soldier Token", 1);
    }

}
