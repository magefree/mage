package org.mage.test.cards.single.acr;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class SigurdJarlOfRavensthorpeTest extends CardTestPlayerBase {

    /**
     * Whenever you put a lore counter on a Saga you control, put a +1/+1 counter on up to one other target creature.
     * Boast -- {1}: Put a lore counter on target Saga you control or remove one from it.
     */
    private static final String sigurd = "Sigurd, Jarl of Ravensthorpe";
    /**
     * Saga land
     */
    private static final String saga = "Urza's Saga";
    /**
     * 2/2 Green Creature
     */
    private static final String bear = "Bear Cub";

    @Test
    public void testSigurd() {
        setStrictChooseMode(true);
        addCard(Zone.BATTLEFIELD, playerA, sigurd);
        addCard(Zone.BATTLEFIELD, playerA, bear);
        addCard(Zone.HAND, playerA, saga);
        addCard(Zone.BATTLEFIELD, playerA, "Forest");

        // Saga entering triggers
        playLand(1, PhaseStep.PRECOMBAT_MAIN,playerA, saga);
        addTarget(playerA, bear);
        setChoice(playerA, "I - {this} gains"); // Stack Sigurd ability last
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkPermanentCounters("Saga entering counter", 1, PhaseStep.PRECOMBAT_MAIN, playerA, saga, CounterType.LORE, 1);
        checkPermanentCounters("Bear cub single counter", 1, PhaseStep.PRECOMBAT_MAIN, playerA, bear, CounterType.P1P1, 1);

        // Activate boast
        attack(1, playerA, sigurd);
        activateAbility(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Boast &mdash; {1}: ", saga);
        setChoice(playerA, true); // add counter
        setChoice(playerA, "II - {this} gains"); // Stack Sigurd ability last
        addTarget(playerA, bear);
        waitStackResolved(1, PhaseStep.POSTCOMBAT_MAIN);
        checkPermanentCounters("Saga boast counter", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, saga, CounterType.LORE, 2);
        checkPermanentCounters("Bear cub two counters", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, bear, CounterType.P1P1, 2);

        setStopAt(1, PhaseStep.END_TURN);
        execute();
    }
}