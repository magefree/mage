package org.mage.test.cards.triggers;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 * 
 */
public class BiovisionaryTest extends CardTestPlayerBase {
    /**
     * Biovisionary   {1}{G}{U}
     * Creature 2/3
     * At the beginning of the end step, if you control four or more creatures named Biovisionary, you win the game.
     * 
     */

    @Test
    public void testFourWin() {

        addCard(Zone.BATTLEFIELD, playerA, "Biovisionary", 4);

        setStopAt(2, PhaseStep.UNTAP);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);
        
        assertPermanentCount(playerA, "Biovisionary", 4);

        assertLostTheGame(playerB);
        assertWonTheGame(playerA);

    }

    @Test
    public void testFourWithThreeClonesWin() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 12);
        addCard(Zone.BATTLEFIELD, playerA, "Biovisionary", 1);
        
        addCard(Zone.HAND, playerA, "Clone", 3);
        
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Clone");
        setChoice(playerA, true);
        setChoice(playerA, "Biovisionary");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Clone");
        setChoice(playerA, true);
        setChoice(playerA, "Biovisionary");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Clone");
        setChoice(playerA, true);
        setChoice(playerA, "Biovisionary");

        setStopAt(2, PhaseStep.UNTAP);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);

        assertPermanentCount(playerA, "Biovisionary", 4);
        assertLostTheGame(playerB);
        assertWonTheGame(playerA);

    }
}
