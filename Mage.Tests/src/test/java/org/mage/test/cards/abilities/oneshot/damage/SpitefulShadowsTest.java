package org.mage.test.cards.abilities.oneshot.damage;

import mage.Constants;
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
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Glistener Elf");
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Swamp", 2);
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Mountain", 2);
        addCard(Constants.Zone.HAND, playerA, "Spiteful Shadows");
        addCard(Constants.Zone.HAND, playerA, "Lightning Bolt");
        
        castSpell(1, Constants.PhaseStep.PRECOMBAT_MAIN, playerA, "Spiteful Shadows", "Glistener Elf");
        castSpell(1, Constants.PhaseStep.POSTCOMBAT_MAIN, playerA, "Lightning Bolt", "Glistener Elf");

        setStopAt(1, Constants.PhaseStep.END_TURN);
        execute();
        
        assertLife(playerA, 20);
        assertLife(playerB, 20);
        assertCounterCount(playerA, CounterType.POISON, 3);
    }

    @Test
    public void testCard1() {
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Craw Wurm");
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Swamp", 2);
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Mountain", 2);
        addCard(Constants.Zone.HAND, playerA, "Spiteful Shadows");
        addCard(Constants.Zone.HAND, playerA, "Lightning Bolt");
        
        castSpell(1, Constants.PhaseStep.PRECOMBAT_MAIN, playerA, "Spiteful Shadows", "Craw Wurm");
        castSpell(1, Constants.PhaseStep.POSTCOMBAT_MAIN, playerA, "Lightning Bolt", "Craw Wurm");

        setStopAt(1, Constants.PhaseStep.END_TURN);
        execute();
        
        assertLife(playerA, 17);
        assertLife(playerB, 20);
        assertCounterCount(playerA, CounterType.POISON, 0);
    }

}
