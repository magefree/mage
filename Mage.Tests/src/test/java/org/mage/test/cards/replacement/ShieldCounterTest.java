package org.mage.test.cards.replacement;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author weirddan455
 */
public class ShieldCounterTest extends CardTestPlayerBase {

    @Test
    public void testNoncombatDamage() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Disciplined Duelist");
        addCard(Zone.HAND, playerA, "Lightning Bolt", 2);
        setStrictChooseMode(true);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt");
        addTarget(playerA, "Disciplined Duelist");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertAllCommandsUsed();
        assertPermanentCount(playerA, "Disciplined Duelist", 1);
        assertCounterCount("Disciplined Duelist", CounterType.SHIELD, 0);
    }

    @Test
    public void testCombatDamage() {
        addCard(Zone.BATTLEFIELD, playerA, "Disciplined Duelist");
        addCard(Zone.BATTLEFIELD, playerB, "Hill Giant");
        setStrictChooseMode(true);

        attack(1, playerA, "Disciplined Duelist");
        block(1, playerB, "Hill Giant", "Disciplined Duelist");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertAllCommandsUsed();
        assertPermanentCount(playerA, "Disciplined Duelist", 1);
        assertCounterCount("Disciplined Duelist", CounterType.SHIELD, 0);
        assertGraveyardCount(playerB, "Hill Giant", 1);
    }

    @Test
    public void testDestroyEffect() {
        addCard(Zone.BATTLEFIELD, playerA, "Disciplined Duelist");
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 3);
        addCard(Zone.HAND, playerA, "Murder");
        setStrictChooseMode(true);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Murder");
        addTarget(playerA, "Disciplined Duelist");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertAllCommandsUsed();
        assertPermanentCount(playerA, "Disciplined Duelist", 1);
        assertCounterCount("Disciplined Duelist", CounterType.SHIELD, 0);
    }
}
