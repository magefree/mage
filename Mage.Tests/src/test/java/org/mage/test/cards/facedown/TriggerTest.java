
package org.mage.test.cards.facedown;

import mage.cards.Card;
import mage.constants.EmptyNames;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */

    

public class TriggerTest extends CardTestPlayerBase {

    /**
     * Midnight Reaper triggers when dies face down #7063
     * Ixidron has turned Midnight Reaper and Balduvian Bears face down:
     * 
     */
    
    // test that cards imprinted using Summoner's Egg are face down
    @Test
    public void testReaperDoesNotTriggerDiesTriggerFaceDown() {
        setStrictChooseMode(true);
        
        addCard(Zone.BATTLEFIELD, playerA, "Island", 5);
        // As Ixidron enters the battlefield, turn all other nontoken creatures face down.
        // Ixidron's power and toughness are each equal to the number of face-down creatures on the battlefield.        
        addCard(Zone.HAND, playerA, "Ixidron"); // Creature {3}{U}{U} (*/*)        
        // Whenever a nontoken creature you control dies, Midnight Reaper deals 1 damage to you and you draw a card.        
        addCard(Zone.BATTLEFIELD, playerA, "Midnight Reaper"); // Creature {2}{B}
        
        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 1);
        addCard(Zone.HAND, playerB, "Lightning Bolt"); // Instant 3 damage

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Ixidron");
        
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, playerA);
        
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Lightning Bolt", EmptyNames.FACE_DOWN_CREATURE.toString());
        
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertAllCommandsUsed();
        
        assertGraveyardCount(playerB, "Lightning Bolt", 1);
        
        assertGraveyardCount(playerA, "Midnight Reaper", 1);
        assertGraveyardCount(playerA, "Ixidron", 1);
        
        assertHandCount(playerA, 0);
        assertLife(playerA, 20);

    }
}