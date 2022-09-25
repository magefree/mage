package org.mage.test.cards.single.m21;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class ChandrasMagmuttTest extends CardTestPlayerBase {

    @Test
    public void pingPlayer(){
        // {T}: Chandra's Magmutt deals 1 damage to target player or planeswalker.<        
        addCard(Zone.BATTLEFIELD, playerA, "Chandra's Magmutt");

        activateAbility(3, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: {this} deals", playerB);

        setStopAt(3, PhaseStep.POSTCOMBAT_MAIN);
        setStrictChooseMode(true);
        execute();

        assertLife(playerB, 19);
    }

    @Test
    public void pingPlanesWalker(){
        // {T}: Chandra's Magmutt deals 1 damage to target player or planeswalker.<        
        addCard(Zone.BATTLEFIELD, playerA, "Chandra's Magmutt");
        // +1: Put a +1/+1 counter on up to one target creature. It gains indestructible until end of turn.
        // −2: Whenever one or more nontoken creatures attack this turn, create that many 1/1 white Soldier creature tokens that are tapped and attacking.
        // −6: You get an emblem with "At the beginning of combat on your turn, create a 1/1 white Soldier creature token, then put a +1/+1 counter on each creature you control."        
        addCard(Zone.BATTLEFIELD, playerB, "Basri Ket");

        activateAbility(3, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: {this} deals", "Basri Ket");
        setStopAt(3, PhaseStep.POSTCOMBAT_MAIN);
        setStrictChooseMode(true);
        execute();

        assertCounterCount("Basri Ket", CounterType.LOYALTY, 2);
    }
}
