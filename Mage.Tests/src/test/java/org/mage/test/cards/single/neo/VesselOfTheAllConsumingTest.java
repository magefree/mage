package org.mage.test.cards.single.neo;

import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;

/**
 * {@link mage.cards.v.VesselOfTheAllConsuming Vessel of the All-Consuming}
 * Enchantment Creature — Ogre Shaman 
 * Trample
 * Whenever Vessel of the All-Consuming deals damage, put a +1/+1 counter on it.
 * Whenever Vessel of the All-Consuming deals damage to a player, if it has dealt 10 or more damage to that player this turn, they lose the game.
 * 3/3
 *
 * @author alexander-novo
 */
public class VesselOfTheAllConsumingTest extends CardTestPlayerBase {

    private static final String vessel = "Vessel of the All-Consuming";
    private static final String hidetsugu = "Hidetsugu Consumes All";

    /**
     * Reported bug: https://github.com/magefree/mage/issues/10283
     * 
     * Vessel doesn't seem to be tracking damage properly. It should work with 5 power and double strike.
     */
    @Test
    public void doubleStrike() {
        String conviction = "True Conviction";

        // Cards necessary for test
        addCard(Zone.BATTLEFIELD, playerA, hidetsugu);
        addCard(Zone.BATTLEFIELD, playerA, conviction);

        // Flip hidetsugu
        addCounters(1, PhaseStep.PRECOMBAT_MAIN, playerA, hidetsugu, CounterType.LORE, 3);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, 1);
        checkPermanentCount("post flip", 1, PhaseStep.PRECOMBAT_MAIN, playerA, vessel, 1);

        // Make vessel a 5/5
        addCounters(1, PhaseStep.PRECOMBAT_MAIN, playerA, vessel, CounterType.P1P1, 2);
        checkPT("precombat", 1, PhaseStep.PRECOMBAT_MAIN, playerA, vessel, 5, 5);

        // Wait until turn 3 because no haste
        attack(3, playerA, vessel, playerB);

        // -5 -6 because it gets a +1/+1 on the first strike damage
        checkLife("post damage", 3, PhaseStep.COMBAT_DAMAGE, playerB, 20 - 5 - 6);
        checkStackObject("post damage", 3, PhaseStep.COMBAT_DAMAGE, playerA,
                "Whenever {this} deals damage to a player", 1);

        setStopAt(3, PhaseStep.COMBAT_DAMAGE);
        execute();

        // Player B should have lost the game because they took 11 damage from vessel this turn
        assertLostTheGame(playerB);
    }
}
