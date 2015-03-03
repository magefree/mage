package org.mage.test.cards.watchers;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author BetaSteward
 */
public class KaradorGhostChieftainTest extends CardTestPlayerBase {
    /*
     * Karador, Ghost Chieftain
     * Legendary Creature — Centaur Spirit 3/4, 5WBG (8)
     * Karador, Ghost Chieftain costs {1} less to cast for each creature card in your graveyard.
     * During each of your turns, you may cast one creature card from your graveyard.
     *
    */
    
    // test that can play spell from graveyard
    @Test
    public void testPlayFromGraveyard() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 5);
        addCard(Zone.BATTLEFIELD, playerA, "Karador, Ghost Chieftain");
        addCard(Zone.GRAVEYARD, playerA, "Raging Goblin");
        
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Raging Goblin");
        
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();
                
        this.assertPermanentCount(playerA, "Raging Goblin", 1);
        this.assertGraveyardCount(playerA, "Raging Goblin", 0);
        
    }
    
    // test that can only play one spell from graveyard
    @Test
    public void testPlayOneFromGraveyard() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 5);
        addCard(Zone.BATTLEFIELD, playerA, "Karador, Ghost Chieftain");
        addCard(Zone.GRAVEYARD, playerA, "Raging Goblin", 2);
        
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Raging Goblin");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Raging Goblin");
        
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();
                
        this.assertPermanentCount(playerA, "Raging Goblin", 1);
        this.assertGraveyardCount(playerA, "Raging Goblin", 1);
        
    }

    // test that can only play one spell from graveyard per turn
    @Test
    public void testPlayOneFromGraveyardPerTurn() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 5);
        addCard(Zone.BATTLEFIELD, playerA, "Karador, Ghost Chieftain");
        addCard(Zone.GRAVEYARD, playerA, "Raging Goblin", 2);
        
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Raging Goblin");
        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerA, "Raging Goblin");
        
        setStopAt(3, PhaseStep.BEGIN_COMBAT);
        execute();
                
        this.assertPermanentCount(playerA, "Raging Goblin", 2);
        this.assertGraveyardCount(playerA, "Raging Goblin", 0);
        
    }
    
}
