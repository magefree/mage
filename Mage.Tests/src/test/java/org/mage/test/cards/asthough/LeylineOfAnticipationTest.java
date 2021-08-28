package org.mage.test.cards.asthough;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */

public class LeylineOfAnticipationTest extends CardTestPlayerBase {

    @Test
    public void testCastAsThoughHasFlashDuringCombat() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains",2);

        addCard(Zone.HAND, playerA, "Leyline of Anticipation");
        addCard(Zone.HAND, playerA, "Silvercoat Lion");
        setChoice(playerA, true);
        castSpell(2, PhaseStep.DRAW, playerA, "Silvercoat Lion");

        setStopAt(2, PhaseStep.END_COMBAT);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);
        
        assertPermanentCount(playerA, "Leyline of Anticipation", 1);
        assertPermanentCount(playerA, "Silvercoat Lion", 1);
        
    }

    /**
     * Tests playing card with flash from graveyard with Yawgmoth's Agenda in play works also
     */
    @Test
    public void testNoCastPossibleOnOpponentsTurn() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains",2);
        addCard(Zone.BATTLEFIELD, playerA, "Yawgmoth's Agenda",1);

        addCard(Zone.HAND, playerA, "Leyline of Anticipation");
        setChoice(playerA, true);
        
        addCard(Zone.GRAVEYARD, playerA, "Silvercoat Lion");
        
        castSpell(2, PhaseStep.DRAW, playerA, "Silvercoat Lion");

        setStopAt(2, PhaseStep.END_COMBAT);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);
        
        assertPermanentCount(playerA, "Leyline of Anticipation", 1);
        assertGraveyardCount(playerA, "Silvercoat Lion", 0);
        assertPermanentCount(playerA, "Silvercoat Lion", 1);
    }
    
}
