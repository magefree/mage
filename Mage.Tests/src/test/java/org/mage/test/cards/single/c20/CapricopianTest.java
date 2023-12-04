package org.mage.test.cards.single.c20;

import org.junit.Test;
import org.mage.test.serverside.base.CardTestCommander4Players;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;

/**
 * @author Xanderhall
 */
public class CapricopianTest extends CardTestCommander4Players {
    
    /**
     * Capricopian enters the battlefield with X +1/+1 counters on it.
     * {2}: Put a +1/+1 counter on Capricopian, then you may reselect which player Capricopian is attacking. 
     * Only the player Capricopian is attacking may activate this ability and only during the declare attackers step. (It can’t attack its controller.)
     */
    private static final String CAPRICOPIAN = "Capricopian";
    private static final String ANTHEM = "Gaea's Anthem";
    
    @Test
    public void testMultipleActivations() {
        addCard(Zone.BATTLEFIELD, playerA, ANTHEM);
        addCard(Zone.BATTLEFIELD, playerA, CAPRICOPIAN);
        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 2);
        addCard(Zone.BATTLEFIELD, playerC, "Mountain", 2);

        // Player A attacks Player B
        attack(1, playerA, CAPRICOPIAN, playerB);

        // Player B activates Capricopian, targetting Player C
        activateAbility(1, PhaseStep.DECLARE_ATTACKERS, playerB, "{2}");
        addTarget(playerB, playerC);
        waitStackResolved(1, PhaseStep.DECLARE_ATTACKERS);

        // Player C activates Capricopian, targetting Player B
        activateAbility(1, PhaseStep.DECLARE_ATTACKERS, playerC, "{2}");
        addTarget(playerC, playerB);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, CAPRICOPIAN, 1);
        assertCounterCount(playerA, CAPRICOPIAN, CounterType.P1P1, 2);
        assertLife(playerB, 17);
    }
}
