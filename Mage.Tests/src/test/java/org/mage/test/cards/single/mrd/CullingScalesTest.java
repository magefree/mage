package org.mage.test.cards.single.mrd;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author cg5
 */
public class CullingScalesTest extends CardTestPlayerBase {
    
    @Test
    public void testCullingScalesBasic() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain"); // CMC = 0, but not a nonland permanent
        addCard(Zone.BATTLEFIELD, playerA, "Culling Scales"); // CMC = 3
        addCard(Zone.BATTLEFIELD, playerB, "Siege Rhino"); // CMC = 4
        setStopAt(1, PhaseStep.PRECOMBAT_MAIN);
        execute();
        
        assertPermanentCount(playerA, "Mountain", 1);
        assertPermanentCount(playerA, "Culling Scales", 0);
        assertPermanentCount(playerB, "Siege Rhino", 1);
    }
    
    @Test
    public void testCullingScalesPlusHexproof() {
        addCard(Zone.BATTLEFIELD, playerB, "Bassara Tower Archer"); // CMC = 2, hexproof
        addCard(Zone.BATTLEFIELD, playerA, "Culling Scales"); // CMC = 3
        addCard(Zone.BATTLEFIELD, playerB, "Siege Rhino"); // CMC = 4
        setStopAt(1, PhaseStep.PRECOMBAT_MAIN);
        execute();
        
        // Nothing happens since no valid targets
        // (the only nonland permanent with the lowest CMC has hexproof)
        assertPermanentCount(playerB, "Bassara Tower Archer", 1);
        assertPermanentCount(playerA, "Culling Scales", 1);
        assertPermanentCount(playerB, "Siege Rhino", 1);
    }
    
    @Test
    public void testCullingScalesFizzleByMakingLowerCostedPermanent() {
        // Gatherer ruling: If the targeted permanent doesn't have the lowest converted mana cost
        // when the ability resolves, the ability is countered and the permanent isn't destroyed.
        
        addCard(Zone.HAND, playerB, "Raise the Alarm"); // Make 2 tokens
        addCard(Zone.BATTLEFIELD, playerB, "Elvish Visionary"); // CMC = 2
        addCard(Zone.BATTLEFIELD, playerB, "Plains", 5);
        addCard(Zone.BATTLEFIELD, playerA, "Culling Scales"); // CMC = 3
        
        // On upkeep Culling Scales targets Elvish Visionary
        addTarget(playerA, "Elvish Visionary");
        
        // Cast Raise the Alarm in response
        castSpell(1, PhaseStep.UPKEEP, playerB, "Raise the Alarm", null, "At the beginning of");
        setStopAt(1, PhaseStep.PRECOMBAT_MAIN);
        execute();
        
        // Culling Scales trigger fizzles since the Visionary no longer has the lowest CMC
        assertPermanentCount(playerB, "Soldier Token", 2);
        assertPermanentCount(playerB, "Elvish Visionary", 1);
        assertPermanentCount(playerB, "Plains", 5);
        assertPermanentCount(playerA, "Culling Scales", 1);
    }
    
}
