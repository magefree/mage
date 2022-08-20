package org.mage.test.cards.targets;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */

public class TargetRestrictionsTest extends CardTestPlayerBase {

    @Test
    public void testDreamLeashWorks() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 6);
        // Enchant permanent
        // You can't choose an untapped permanent as Dream Leash's target as you cast Dream Leash.
        // You control enchanted permanent.        
        addCard(Zone.HAND, playerA, "Dream Leash"); // Enchantment {3}{U}{U}
        
        // Tap target creature. It doesn't untap during its controller's next untap step.
        addCard(Zone.HAND, playerA, "Take into Custody"); // Instant {U}
        
        addCard(Zone.BATTLEFIELD, playerB, "Sejiri Merfolk");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Take into Custody", "Sejiri Merfolk");
        
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Dream Leash", "Sejiri Merfolk");

        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Dream Leash", 1);
        assertPermanentCount(playerA, "Sejiri Merfolk", 1);
        
        assertLife(playerA, 20);
        assertLife(playerB, 20);
    }
    
    @Test
    public void testDreamLeashUntappingAsResponseToCast() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 6);
        // Enchant permanent
        // You can't choose an untapped permanent as Dream Leash's target as you cast Dream Leash.
        // You control enchanted permanent.        
        addCard(Zone.HAND, playerA, "Dream Leash"); // Enchantment {3}{U}{U}
        
        // Tap target creature. It doesn't untap during its controller's next untap step.
        addCard(Zone.HAND, playerA, "Take into Custody"); // Instant {U}
        
        // Untap target creature. It gets +1/+3 until end of turn.
        addCard(Zone.HAND, playerB, "Ornamental Courage"); // Instant {G}       

        addCard(Zone.BATTLEFIELD, playerB, "Forest");
        addCard(Zone.BATTLEFIELD, playerB, "Sejiri Merfolk");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Take into Custody", "Sejiri Merfolk");
        
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Dream Leash", "Sejiri Merfolk");
        
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Ornamental Courage", "Sejiri Merfolk", "Dream Leash");

        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.BEGIN_COMBAT);
        execute();


        assertGraveyardCount(playerA, "Take into Custody", 1);
        assertGraveyardCount(playerB, "Ornamental Courage", 1);
        assertPermanentCount(playerA, "Dream Leash", 1);
        assertPermanentCount(playerA, "Sejiri Merfolk", 1);
        assertPowerToughness(playerA, "Sejiri Merfolk", 2, 1);
        
        assertLife(playerA, 20);
        assertLife(playerB, 20);
    }
}