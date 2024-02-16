package org.mage.test.cards.single.m21;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class BasrisAcolyteTest extends CardTestPlayerBase {

    private static final String basrisAcolyte = "Basri's Acolyte";

    @Test
    public void checkETB(){
        addCard(Zone.HAND, playerA, basrisAcolyte);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 4);
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears");
        addCard(Zone.BATTLEFIELD, playerA, "Savannah Lions");

        // When Basri's Acolyte enters the battlefield, put a +1/+1 counter on each of up to two other target creatures you control.
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, basrisAcolyte);
        addTarget(playerA, "Grizzly Bears^Savannah Lions");
        setStrictChooseMode(true);
        execute();

        assertCounterCount(playerA, "Grizzly Bears", CounterType.P1P1, 1);
        assertCounterCount(playerA, "Savannah Lions", CounterType.P1P1, 1);
    }
}
