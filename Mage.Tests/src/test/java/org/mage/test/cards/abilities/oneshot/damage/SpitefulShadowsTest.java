package org.mage.test.cards.abilities.oneshot.damage;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * also tests regenerate and tests that permanents with protection can be
 * sacrificed
 *
 * @author BetaSteward
 */
public class SpitefulShadowsTest extends CardTestPlayerBase {

    @Test
    public void SpitefulShadowsPoisonTest() {
        // Infect (This creature deals damage to creatures in the form of -1/-1 counters and to players in the form of poison counters.)
        addCard(Zone.BATTLEFIELD, playerA, "Glistener Elf");
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);
        // Enchant creature
        // Whenever enchanted creature is dealt damage, it deals that much damage to its controller.
        addCard(Zone.HAND, playerA, "Spiteful Shadows");
        addCard(Zone.HAND, playerA, "Lightning Bolt");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Spiteful Shadows", "Glistener Elf");
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Lightning Bolt", "Glistener Elf");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, currentGame.getStartingLife());
        assertLife(playerB, currentGame.getStartingLife());
        assertCounterCount(playerA, CounterType.POISON, 3);
    }

    @Test
    public void SpitefulShadowsRegularTest() {
        addCard(Zone.BATTLEFIELD, playerA, "Craw Wurm"); // Creature 6/4
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);
        // Enchant creature
        // Whenever enchanted creature is dealt damage, it deals that much damage to its controller.
        addCard(Zone.HAND, playerA, "Spiteful Shadows");
        addCard(Zone.HAND, playerA, "Lightning Bolt");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Spiteful Shadows", "Craw Wurm");
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Lightning Bolt", "Craw Wurm");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, currentGame.getStartingLife() - 3);
        assertLife(playerB, currentGame.getStartingLife());
        assertCounterCount(playerA, CounterType.POISON, 0);
    }

    @Test
    public void SpitefulShadowsMultiDamageTest() {
        addCard(Zone.BATTLEFIELD, playerA, "Craw Wurm"); // Creature 6/4

        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);
        addCard(Zone.BATTLEFIELD, playerB, "Memnite", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Agent of Stromgald", 1);

        // Whenever an opponent is dealt noncombat damage, Chandra’s Spitfire gets +3/+0 until end of turn.
        addCard(Zone.BATTLEFIELD, playerB, "Chandra's Spitfire", 1);

        // Enchant creature
        // Whenever enchanted creature is dealt damage, it deals that much damage to its controller.
        addCard(Zone.HAND, playerA, "Spiteful Shadows");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Spiteful Shadows", "Craw Wurm");

        attack(1, playerA, "Craw Wurm", playerB);
        block(1, playerB, "Memnite", "Craw Wurm");
        block(1, playerB, "Agent of Stromgald", "Craw Wurm");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, currentGame.getStartingLife() - 2);
        assertLife(playerB, currentGame.getStartingLife());
        assertCounterCount(playerA, CounterType.POISON, 0);

        // Since Spiteful Shadows should have only triggered once, so should have chandra's spitfire.
        assertPowerToughness(playerB, "Chandra's Spitfire", 4, 3);
    }

}
