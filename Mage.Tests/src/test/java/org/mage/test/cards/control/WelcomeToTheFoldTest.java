package org.mage.test.cards.control;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *  2UU
    Sorcery
    Madness XUU (If you discard this card, discard it into exile. When you do, cast it for its madness cost or put it into your graveyard.)

    Gain control of target creature if its toughness is 2 or less. 
    * If Welcome to the Fold's madness cost was paid, instead gain control of that creature if its toughness is X or less. 
 * @author escplan9 (Derek Monturo - dmontur1 at gmail dot com)
 */
public class WelcomeToTheFoldTest extends CardTestPlayerBase {
    
    /**
     * Paid with regular cost, gain control of creatures with toughness <= 2
     */
    @Test
    public void regularCostVariousCreatures() {
                
        addCard(Zone.HAND, playerA, "Welcome to the Fold", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 12);
        
        addCard(Zone.BATTLEFIELD, playerB, "Arashin Cleric", 1); // 1/3
        addCard(Zone.BATTLEFIELD, playerB, "Jace, Vryn's Prodigy", 1); // 0/2
        addCard(Zone.BATTLEFIELD, playerB, "Elite Vanguard", 1); // 2/1
                
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Welcome to the Fold", true);
        addTarget(playerA, "Arashin Cleric"); // does not gain control
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Welcome to the Fold", true);
        addTarget(playerA, "Jace, Vryn's Prodigy"); // gains control
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Welcome to the Fold");
        addTarget(playerA, "Elite Vanguard"); // gains control
        
        setStopAt(1, PhaseStep.END_TURN);
        execute();
        
        assertGraveyardCount(playerA, "Welcome to the Fold", 3);
        assertPermanentCount(playerA, "Arashin Cleric", 0); // should not gain control
        assertPermanentCount(playerA, "Jace, Vryn's Prodigy", 1); // should gain control
        assertPermanentCount(playerA, "Elite Vanguard", 1);
    }
    
    // TODO: add a madness cost test
}
