

package org.mage.test.cards.copy;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author BetaSteward
 */
public class ReversalOfFortuneTest extends CardTestPlayerBase {


    /**
     * Reversal of Fortune
     * Sorcery, 4RR (6)
     * Target opponent reveals their hand. You may copy an instant or sorcery
     * card in it. If you do, you may cast the copy without paying its mana cost.
     * 
    */    
    @Test
    public void testCopyCard() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 6);
        addCard(Zone.HAND, playerA, "Reversal of Fortune");
        addCard(Zone.HAND, playerB, "Lightning Bolt");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Reversal of Fortune", playerB);
        setChoice(playerA, "Lightning Bolt"); // select to copy
        setChoice(playerA, true); // cast copy

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerA, "Reversal of Fortune", 1);
        assertGraveyardCount(playerA, "Lightning Bolt", 0);
        assertGraveyardCount(playerB, "Lightning Bolt", 0);
        assertLife(playerB, 17);
        
    }

    @Test
    public void testCopyCardButDontCast() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 6);
        addCard(Zone.HAND, playerA, "Reversal of Fortune");
        addCard(Zone.HAND, playerB, "Lightning Bolt");

//        setStrictChooseMode(true);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Reversal of Fortune", playerB);
        addTarget(playerA, "Lightning Bolt");
        setChoice(playerA, false);

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerA, "Reversal of Fortune", 1);
        assertGraveyardCount(playerA, "Lightning Bolt", 0);
        assertGraveyardCount(playerB, "Lightning Bolt", 0);
        assertLife(playerB, 20);
        
    }
    
}
