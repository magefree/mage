package org.mage.test.cards.replacement;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * Torpor Orb:
 *   Creatures entering the battlefield don't cause abilities to trigger.
 *
 * @author noxx
 */
public class TorporOrbTest extends CardTestPlayerBase {

    @Test
    public void testWallOfOmens() {
        addCard(Zone.BATTLEFIELD, playerA, "Torpor Orb");
        addCard(Zone.HAND, playerA, "Wall of Omens");
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Wall of Omens");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);

        assertPermanentCount(playerA, "Wall of Omens", 1);
        assertPermanentCount(playerA, "Torpor Orb", 1);
        assertHandCount(playerA, 0);
    }

    /**
     * Treacherous Pit-Dweller doesnt function properly with Torpor Orb and Hushwing Gryff
     */
    @Test
    public void testPitTweller() {
        addCard(Zone.BATTLEFIELD, playerB, "Torpor Orb");
        addCard(Zone.BATTLEFIELD, playerB, "Treacherous Pit-Dweller");  // 4/3

        addCard(Zone.HAND, playerA, "Lightning Bolt");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);
        
        attack(2, playerB, "Treacherous Pit-Dweller");        
        castSpell(2, PhaseStep.DECLARE_ATTACKERS, playerA, "Lightning Bolt", "Treacherous Pit-Dweller");

        setStopAt(2, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);

        assertGraveyardCount(playerA, "Lightning Bolt", 1);
        
        assertPermanentCount(playerB, "Treacherous Pit-Dweller", 1);
        assertPowerToughness(playerB, "Treacherous Pit-Dweller", 5,4);
    }
    
}
