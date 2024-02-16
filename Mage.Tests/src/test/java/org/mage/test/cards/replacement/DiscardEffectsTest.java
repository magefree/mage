package org.mage.test.cards.replacement;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author escplan9 (Derek Monturo - dmontur1 at gmail dot com)
 */
public class DiscardEffectsTest extends CardTestPlayerBase {
    
    @Test
    public void testOpponentDiscardsLoxodonSmiter() {
        
        addCard(Zone.HAND, playerA, "Thoughtseize"); // {B} target player, discard non-land card, lose two life.
        addCard(Zone.BATTLEFIELD, playerA, "Swamp");
        // Loxodon Smiter can't be countered.
        // If a spell or ability an opponent controls causes you to discard Loxodon Smiter, put it onto the battlefield instead of putting it into your graveyard.
        addCard(Zone.HAND, playerB, "Loxodon Smiter");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Thoughtseize");
        addTarget(playerA, playerB);
        setChoice(playerA, "Loxodon Smiter");
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();
        
        assertLife(playerA, 18);
        assertGraveyardCount(playerA, "Thoughtseize", 1);
        assertGraveyardCount(playerB, "Loxodon Smiter", 0);
        assertPermanentCount(playerB, "Loxodon Smiter", 1);
    }
    
    @Test
    public void testOwnerDiscardsOwnLoxodonSmiter() {
        
        // Loxodon Smiter can't be countered.
        // If a spell or ability an opponent controls causes you to discard Loxodon Smiter, put it onto the battlefield instead of putting it into your graveyard.
        addCard(Zone.HAND, playerA, "Loxodon Smiter");
        addCard(Zone.HAND, playerA, "Sift"); // {3}{U} Sorcery - draw 3 discard 1
        addCard(Zone.BATTLEFIELD, playerA, "Island", 4);

        setStrictChooseMode(true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Sift");
        setChoice(playerA, "Loxodon Smiter");
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();
        
        assertGraveyardCount(playerA, "Sift", 1);
        assertGraveyardCount(playerA, "Loxodon Smiter", 1);
        assertPermanentCount(playerA, "Loxodon Smiter", 0);
    }
}
