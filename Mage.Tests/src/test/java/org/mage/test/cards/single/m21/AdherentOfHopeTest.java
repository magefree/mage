package org.mage.test.cards.single.m21;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class AdherentOfHopeTest extends CardTestPlayerBase {




    @Test
    public void test_basri_in_play_controllerturn(){
        // At the beginning of combat on your turn, if you control a Basri planeswalker, put a +1/+1 counter on Adherent of Hope.
        addCard(Zone.BATTLEFIELD, playerA, "Adherent of Hope", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Basri Ket", 1);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertCounterCount(playerA, "Adherent of Hope", CounterType.P1P1, 1);

    }

    @Test
    public void test_basri_in_play_opponentturn(){
        // At the beginning of combat on your turn, if you control a Basri planeswalker, put a +1/+1 counter on Adherent of Hope.
        addCard(Zone.BATTLEFIELD, playerA, "Adherent of Hope", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Basri Ket", 1);


        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertCounterCount(playerA, "Adherent of Hope", CounterType.P1P1, 1);

    }

    @Test
    public void test_no_basri_in_play_controllerturn(){
        // At the beginning of combat on your turn, if you control a Basri planeswalker, put a +1/+1 counter on Adherent of Hope.
        addCard(Zone.BATTLEFIELD, playerA, "Adherent of Hope", 1);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertCounterCount(playerA, "Adherent of Hope", CounterType.P1P1, 0);

    }
}
