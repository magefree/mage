package org.mage.test.cards.single.dka;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author BetaSteward
 */
public class GrafdiggersCageTest extends CardTestPlayerBase {

    @Test
    public void testCard1() {
        // Creature cards can't enter the battlefield from graveyards or libraries.
        // Players can't cast cards in graveyards or libraries.
        addCard(Zone.BATTLEFIELD, playerA, "Grafdigger's Cage");
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 2);
        
        // Put two 1/1 white Spirit creature tokens with flying onto the battlefield.
        // Flashback {1}{B}        
        addCard(Zone.GRAVEYARD, playerA, "Lingering Souls");

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Flashback {1}{B}");
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);
        assertPermanentCount(playerA, "Spirit Token", 0);
        assertGraveyardCount(playerA, "Lingering Souls", 1);
    }

    @Test
    public void testCard2() {
        addCard(Zone.BATTLEFIELD, playerA, "Grafdigger's Cage");
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 5);
        addCard(Zone.HAND, playerA, "Rise from the Grave", 1);
        addCard(Zone.GRAVEYARD, playerA, "Craw Wurm");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Rise from the Grave", "Craw Wurm");
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);
        assertPermanentCount(playerA, "Craw Wurm", 0);
        assertGraveyardCount(playerA, "Craw Wurm", 1);
        assertGraveyardCount(playerA, "Rise from the Grave", 1);
    }
  
    /**
     * With a Grafdigger's Cage in play you can still announce Cabal Therapy and pay it's flashback cost - resulting 
     * in a sacrificed creature (and any triggers along with that) and a Cabal Therapy still in the graveyard.
     * 
     * Don't get me wrong, I love sacrificing 2-3 Veteran Explorer to the same Cabal Therapy, but it's just not all that fair.
     * 
     * Same thing goes for cards like Ethersworn Canonist, assuming that the flashback isn't the first non-artifact spell for the turn.
     */
    @Test
    public void testCard3() {
        // Creature cards can't enter the battlefield from graveyards or libraries.
        // Players can't cast cards in graveyards or libraries.
        addCard(Zone.BATTLEFIELD, playerA, "Grafdigger's Cage");
        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion", 2);
        
        // Name a nonland card. Target player reveals their hand and discards all cards with that name.
        // Flashback-Sacrifice a creature. (You may cast this card from your graveyard for its flashback cost. Then exile it.)       
        addCard(Zone.GRAVEYARD, playerA, "Cabal Therapy");

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Flashback");
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);
        assertPermanentCount(playerA, "Silvercoat Lion", 2);
        assertGraveyardCount(playerA, "Cabal Therapy", 1);
    }    
 
}
