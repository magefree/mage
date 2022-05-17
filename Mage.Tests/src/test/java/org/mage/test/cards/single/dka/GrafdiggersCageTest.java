package org.mage.test.cards.single.dka;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * {@link mage.cards.g.GrafdiggersCage Grafdigger's Cage}
 * {1}
 * Artifact
 * Creature cards in graveyards and libraries can’t enter the battlefield.
 * Players can’t cast spells from graveyards or libraries.
 *
 * @author BetaSteward
 */
public class GrafdiggersCageTest extends CardTestPlayerBase {

    /**
     * Test that the flashback ability can't be used.
     */
    @Test
    public void testFlashback() {
        addCard(Zone.BATTLEFIELD, playerA, "Grafdigger's Cage");
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 2);
        
        // Put two 1/1 white Spirit creature tokens with flying onto the battlefield.
        // Flashback {1}{B}        
        addCard(Zone.GRAVEYARD, playerA, "Lingering Souls");

        checkPlayableAbility("flashback", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Flashback", false);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();
    }

    /**
     * Test that a creature can't be put onto the battlefield from the graveyard.
     */
    @Test
    public void testBeingPutOnBattlefieldFromGraveyard() {
        addCard(Zone.BATTLEFIELD, playerA, "Grafdigger's Cage");
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 5);

        // Put target creature card from a graveyard onto the battlefield under your control.
        // That creature is a black Zombie in addition to its other colors and types.
        addCard(Zone.HAND, playerA, "Rise from the Grave", 1);

        addCard(Zone.GRAVEYARD, playerA, "Craw Wurm");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Rise from the Grave", "Craw Wurm");
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

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
    public void testFlashbackNonPermanent() {
        addCard(Zone.BATTLEFIELD, playerA, "Grafdigger's Cage");
        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion", 1);
        
        // Name a nonland card. Target player reveals their hand and discards all cards with that name.
        // Flashback - Sacrifice a creature. (You may cast this card from your graveyard for its flashback cost. Then exile it.)
        addCard(Zone.GRAVEYARD, playerA, "Cabal Therapy");

        checkPlayableAbility("flashback", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Flashback", false);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();
    }
}
