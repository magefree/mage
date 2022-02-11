package org.mage.test.cards.single.eld;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */

public class OnceUponATimeTest extends CardTestPlayerBase {

    @Test
    public void test_castRegularly() {
        setStrictChooseMode(true);

        addCard(Zone.LIBRARY, playerA, "Silvercoat Lion");
        addCard(Zone.LIBRARY, playerA, "Plains", 4);
        skipInitShuffling();
        
        // If this spell is the first spell you've cast this game, you may cast it without paying its mana cost.
        // Look at the top five cards of your library. 
        // You may reveal a creature or land card from among them and put it into your hand. 
        // Put the rest on the bottom of your library in a random order.     
        addCard(Zone.HAND, playerA, "Once Upon a Time"); // Instant {1}{G}
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 1);
        addCard(Zone.HAND, playerA, "Forest", 1);
        
        playLand(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Forest");

        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Once Upon a Time");
        
        setChoice(playerA, false); // Cast without paying its mana cost?
        
        setChoice(playerA, true); // Do you wish to reveal a creature or land card and put into your hand?
        setChoice(playerA, "Silvercoat Lion");
        
        setStopAt(2, PhaseStep.END_TURN);
        execute();

        assertAllCommandsUsed();

        assertGraveyardCount(playerA, "Once Upon a Time", 1);
        assertTappedCount("Forest", true, 2);
        assertHandCount(playerA, "Silvercoat Lion", 1);
    }
    
    @Test
    public void test_castForFree() {
        setStrictChooseMode(true);

        addCard(Zone.LIBRARY, playerA, "Silvercoat Lion");
        addCard(Zone.LIBRARY, playerA, "Plains", 4);

        addCard(Zone.LIBRARY, playerB, "Silvercoat Lion", 5);

        skipInitShuffling();
        
        // If this spell is the first spell you've cast this game, you may cast it without paying its mana cost.
        // Look at the top five cards of your library. 
        // You may reveal a creature or land card from among them and put it into your hand. 
        // Put the rest on the bottom of your library in a random order.     
        addCard(Zone.HAND, playerA, "Once Upon a Time"); // Instant {1}{G}
        addCard(Zone.HAND, playerB, "Once Upon a Time"); // Instant {1}{G}

        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Once Upon a Time");
        setChoice(playerA, true); // Cast without paying its mana cost?
        setChoice(playerA, true); // Do you wish to reveal a creature or land card and put into your hand?
        setChoice(playerA, "Silvercoat Lion");

        castSpell(2, PhaseStep.POSTCOMBAT_MAIN, playerB, "Once Upon a Time");
        setChoice(playerB, true); // Cast without paying its mana cost?
        setChoice(playerB, true); // Do you wish to reveal a creature or land card and put into your hand?
        setChoice(playerB, "Silvercoat Lion");
        
        setStopAt(2, PhaseStep.END_TURN);
        execute();

        assertAllCommandsUsed();

        assertGraveyardCount(playerA, "Once Upon a Time", 1);
        assertGraveyardCount(playerB, "Once Upon a Time", 1);

        assertHandCount(playerA, "Silvercoat Lion", 1);
        assertHandCount(playerB, "Silvercoat Lion", 2);
    }
}