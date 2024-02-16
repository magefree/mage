package org.mage.test.cards.single.soi;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author escplan9 (Derek Monturo - dmontur1@gmail.com)
 */
public class SilverfurPartisanTest extends CardTestPlayerBase {
    
    /**
     *  
     */
    @Test
    public void playerTargetsSilverfurTest() {
        
        // Wolf Warrior - Trample {2}{G}
        // Whenever a Wolf or Werewolf you control becomes the target of an instant or sorcery spell, 
        // put a 2/2 green Wolf creature token onto the battlefield.
        addCard(Zone.BATTLEFIELD, playerA, "Silverfur Partisan", 1); // 2/2
        addCard(Zone.BATTLEFIELD, playerA, "Howlpack Wolf", 1); // 3/3 wolf
        addCard(Zone.HAND, playerA, "Giant Growth", 1); // {G} instant:  +3+3 to target creature
        addCard(Zone.HAND, playerA, "Enlarge", 1); // {3}{G}{G} sorcery: Target creature gets +7/+7 and gains trample until end of turn. 
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 6);
        
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Giant Growth", "Silverfur Partisan");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Enlarge", "Howlpack Wolf");
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();        
        
        assertGraveyardCount(playerA, "Giant Growth", 1);
        assertGraveyardCount(playerA, "Enlarge", 1);
        assertPowerToughness(playerA, "Silverfur Partisan", 5, 5); // +3+3 from Giant Growth
        assertPowerToughness(playerA, "Howlpack Wolf", 10, 10); // +7+7 from Enlarge
        assertPermanentCount(playerA, "Wolf Token", 2);
    }
    
    /**
     * Reported bug: Silverfur Partisan does not create Wolf Tokens when targetted
     * by instant or sorcery cast by an opponent.
     */
    @Test
    public void opponentTargetsSilverfurTest() {
        
        // Wolf Warrior - Trample {2}{G}
        // Whenever a Wolf or Werewolf you control becomes the target of an instant or sorcery spell, 
        // put a 2/2 green Wolf creature token onto the battlefield.
        addCard(Zone.BATTLEFIELD, playerA, "Silverfur Partisan", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Howlpack Wolf", 1);
        addCard(Zone.HAND, playerB, "Lightning Bolt", 1); // {R} instant
        addCard(Zone.HAND, playerB, "Arrow Storm", 1); // {3}{R}{R} sorcery
        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 6);

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Lightning Bolt", "Howlpack Wolf");
        waitStackResolved(2, PhaseStep.PRECOMBAT_MAIN);
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Arrow Storm", "Silverfur Partisan");
        setStopAt(2, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerB, "Lightning Bolt", 1);
        assertGraveyardCount(playerB, "Arrow Storm", 1);
        assertGraveyardCount(playerA, "Howlpack Wolf", 1);
        assertGraveyardCount(playerA, "Silverfur Partisan", 1);
        assertPermanentCount(playerA, "Wolf Token", 2);
    }
}
