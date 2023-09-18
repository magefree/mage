package org.mage.test.cards.single.m21;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class BasrisAegisTest extends CardTestPlayerBase {

    @Test
    public void castBasrisAegis(){
        addCard(Zone.HAND, playerA, "Basri's Aegis");
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 4);
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears");
        addCard(Zone.BATTLEFIELD, playerA, "Savannah Lions");
        addCard(Zone.LIBRARY, playerA, "Basri, Devoted Paladin");

        // Put a +1/+1 counter on each of up to two target creatures.
        // You may search your library and/or graveyard for a card named Basri, Devoted Paladin, reveal it, and put it into your hand. If you search your library this way, shuffle it.
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Basri's Aegis");
        addTarget(playerA, "Grizzly Bears^Savannah Lions");
        setChoice(playerA, true);
        addTarget(playerA, "Basri, Devoted Paladin");
        setStrictChooseMode(true);
        execute();

        assertHandCount(playerA, 1);
        assertCounterCount(playerA, "Grizzly Bears", CounterType.P1P1, 1);
        assertCounterCount(playerA, "Savannah Lions", CounterType.P1P1, 1);
    }
}
