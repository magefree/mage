package org.mage.test.cards.single.m10;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author escplan9 (Derek Monturo - dmontur1 at gmail dot com)
 */
public class MindShatterTest extends CardTestPlayerBase {
    
    /*
    Reported bug: Mind Shatter forces discard when X=0
    */
    @Test
    public void testMindShatterXZeroOneCardInOpponentHand() {
        
        // {X}{B}{B} - Sorcery
        // Target player discards X cards at random.
        addCard(Zone.HAND, playerA, "Mind Shatter");
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 2);
        addCard(Zone.HAND, playerB, "Wooded Foothills", 1);
        
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Mind Shatter");
        addTarget(playerA, playerB);
        setChoice(playerA, "X=0");
        
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();
        
        assertGraveyardCount(playerA, "Mind Shatter", 1);
        assertGraveyardCount(playerB, "Wooded Foothills", 0);
        assertHandCount(playerB, "Wooded Foothills", 1);
    }
    
    @Test
    public void testMindShatterXZeroTwoCardsInOpponentHand() {
        
        // {X}{B}{B} - Sorcery
        // Target player discards X cards at random.
        addCard(Zone.HAND, playerA, "Mind Shatter");
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 2);
        addCard(Zone.HAND, playerB, "Wooded Foothills", 2);
        
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Mind Shatter");
        addTarget(playerA, playerB);
        setChoice(playerA, "X=0");
        
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();
        
        assertGraveyardCount(playerA, "Mind Shatter", 1);
        assertGraveyardCount(playerB, "Wooded Foothills", 0);
        assertHandCount(playerB, "Wooded Foothills", 2);
    }
}
