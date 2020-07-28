package org.mage.test.cards.single.m21;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class ChandrasMagmuttTest extends CardTestPlayerBase {

    @Test
    public void pingPlayer(){
        addCard(Zone.BATTLEFIELD, playerA, "Chandra's Magmutt");

        activateAbility(3, PhaseStep.PRECOMBAT_MAIN, playerA, "Chandra's Magmutt", playerB);

        setStopAt(3, PhaseStep.POSTCOMBAT_MAIN);
        setStrictChooseMode(true);
        execute();
        assertAllCommandsUsed();

        assertLife(playerB, 19);
    }

    @Test
    public void pingPlanesWalker(){
        addCard(Zone.BATTLEFIELD, playerA, "Chandra's Magmutt");
        addCard(Zone.BATTLEFIELD, playerB, "Basri Ket");

        activateAbility(3, PhaseStep.PRECOMBAT_MAIN, playerA, "Chandra's Magmutt", "Basri Ket");
        setStopAt(3, PhaseStep.POSTCOMBAT_MAIN);
        setStrictChooseMode(true);
        execute();
        assertAllCommandsUsed();

        assertCounterCount("Basri Ket", CounterType.LOYALTY, 2);
    }
}
