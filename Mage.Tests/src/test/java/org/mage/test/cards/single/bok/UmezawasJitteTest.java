package org.mage.test.cards.single.bok;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author xenohedron
 */
public class UmezawasJitteTest extends CardTestPlayerBase {

    private static final String jitte = "Umezawa's Jitte";
    // Whenever equipped creature deals combat damage, put two charge counters on Umezawa’s Jitte.
    // Equip 2
    private static final String attacker = "Spiked Baloth"; // 4/2 trample
    private static final String defender1 = "Memnite"; // 1/1 vanilla
    private static final String defender2 = "Darksteel Myr"; // 0/1 indestructible

    @Test
    public void testTrampleSingleDamageTrigger() {
        // Reported bug: #10763

        addCard(Zone.BATTLEFIELD, playerA, jitte);
        addCard(Zone.BATTLEFIELD, playerA, attacker);
        addCard(Zone.BATTLEFIELD, playerB, defender1);
        addCard(Zone.BATTLEFIELD, playerB, defender2);
        addCard(Zone.BATTLEFIELD, playerA, "Wastes", 2);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Equip", attacker);

        attack(1, playerA, attacker, playerB);
        block(1, playerB, defender1, attacker);
        block(1, playerB, defender2, attacker);
        // Blockers are ordered in order added by the test framework
        setChoice(playerA, "X=1"); // 1 damage to first defender
        setChoice(playerA, "X=1"); // 1 damage to second defender

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertLife(playerB, 20 - 2);
        assertGraveyardCount(playerB, defender1, 1);
        assertPermanentCount(playerB, defender2, 1);
        assertCounterCount(playerA, jitte, CounterType.CHARGE, 2);

    }

}
