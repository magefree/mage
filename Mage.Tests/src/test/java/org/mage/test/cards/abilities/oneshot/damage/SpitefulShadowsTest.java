package org.mage.test.cards.abilities.oneshot.damage;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * also tests regenerate and
 * tests that permanents with protection can be sacrificed
 * 
 * @author BetaSteward
 */
public class SpitefulShadowsTest extends CardTestPlayerBase {

    @Test
    public void testCard() {
        addCard(Zone.BATTLEFIELD, playerA, "Glistener Elf");
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);
        addCard(Zone.HAND, playerA, "Spiteful Shadows");
        addCard(Zone.HAND, playerA, "Lightning Bolt");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Spiteful Shadows", "Glistener Elf");
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Lightning Bolt", "Glistener Elf");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);
        assertCounterCount(playerA, CounterType.POISON, 3);
    }

    @Test
    public void testCard1() {
        addCard(Zone.BATTLEFIELD, playerA, "Craw Wurm");
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);
        addCard(Zone.HAND, playerA, "Spiteful Shadows");
        addCard(Zone.HAND, playerA, "Lightning Bolt");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Spiteful Shadows", "Craw Wurm");
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Lightning Bolt", "Craw Wurm");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 17);
        assertLife(playerB, 20);
        assertCounterCount(playerA, CounterType.POISON, 0);
    }

}
