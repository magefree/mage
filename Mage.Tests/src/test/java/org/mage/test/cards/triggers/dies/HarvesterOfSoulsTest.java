package org.mage.test.cards.triggers.dies;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author noxx
 *
 *   Whenever another nontoken creature dies, you may draw a card.
 */
public class HarvesterOfSoulsTest extends CardTestPlayerBase {

    /**
     * Tests creature on any side would trigger effect
     * Also tests that tokens don't cause trigger to happen
     */
    @Test
    public void testDisabledEffectOnChangeZone() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 4);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 3);
        addCard(Zone.HAND, playerA, "Day of Judgment", 1);        
        addCard(Zone.HAND, playerA, "Thatcher Revolt", 1);

        // Creature - Demon  5/5   {4}{B}{B}
        // Deathtouch
        // Whenever another nontoken creature dies, you may draw a card.
        addCard(Zone.BATTLEFIELD, playerA, "Harvester of Souls", 1);
        
        // Creature - Wurm  6/4   {4}{G}{G}
        addCard(Zone.BATTLEFIELD, playerA, "Craw Wurm", 1);
        // Whenever Arrogant Bloodlord blocks or becomes blocked by a creature with power 1 or less, destroy Arrogant Bloodlord at end of combat.
        addCard(Zone.BATTLEFIELD, playerB, "Arrogant Bloodlord", 1);
        
        // Put three 1/1 red Human creature tokens with haste onto the battlefield. Sacrifice those tokens at the beginning of the next end step.
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Thatcher Revolt", true);
        // Destroy all creatures.
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Day of Judgment");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);

        assertHandCount(playerA, 2); // draw a card for Harvester and Craw Wurm
        assertHandCount(playerB, 0);
    }
}
