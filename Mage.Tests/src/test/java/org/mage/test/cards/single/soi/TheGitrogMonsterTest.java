package org.mage.test.cards.single.soi;

import java.util.Set;
import mage.cards.Card;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *  3BG
    Legendary Creature - Frog Horror
    Deathtouch

    At the beginning of your upkeep, sacrifice The Gitrog Monster unless you sacrifice a land.

    You may play an additional land on each of your turns.

    Whenever one or more land cards are put into your graveyard from anywhere, draw a card. 
 *
 * @author escplan9 (Derek Monturo - dmontur1 at gmail dot com)
 */
public class TheGitrogMonsterTest extends CardTestPlayerBase  {
    
    /**
     * Basic sacrifice test when no lands are present
     */
    @Test
    public void noLandsSacrificeGitrog() {
         
        addCard(Zone.HAND, playerA, "The Gitrog Monster", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2);
                
        addCard(Zone.HAND, playerB, "Armageddon", 1); // destroy all lands
        addCard(Zone.BATTLEFIELD, playerB, "Plains", 4);
        
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "The Gitrog Monster");
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Armageddon");

        setStopAt(3, PhaseStep.DRAW);
        execute();
        
        Set<Card> hand = playerA.getHand().getCards(currentGame);
        assertGraveyardCount(playerA, "Swamp", 3);
        assertGraveyardCount(playerA, "Forest", 2);
        assertGraveyardCount(playerA, "The Gitrog Monster", 1);
        assertGraveyardCount(playerB, "Plains", 4);
        assertGraveyardCount(playerB, "Armageddon", 1);
        assertPermanentCount(playerA, "The Gitrog Monster", 0);
        assertHandCount(playerA, 6); // 1 for turn, 5 more for lands that hit the grave
    }
    
    /**
     * Basic sacrifice test when there is a land
     */
    @Test
    public void hasLandsSacrificeLand() {
         
        addCard(Zone.HAND, playerA, "The Gitrog Monster", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2);
                        
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "The Gitrog Monster");
        
        // on 3rd turn during upkeep opt to sacrifice a land
        setChoice(playerA, "Yes");
        addTarget(playerA, "Swamp");
        setStopAt(3, PhaseStep.DRAW);
        execute();
        
        Set<Card> hand = playerA.getHand().getCards(currentGame);
        assertGraveyardCount(playerA, "Swamp", 1);
        assertPermanentCount(playerA, "The Gitrog Monster", 1);
        assertHandCount(playerA, 2); // 1 for turn, 1 more for land sacrificed
    }
}
