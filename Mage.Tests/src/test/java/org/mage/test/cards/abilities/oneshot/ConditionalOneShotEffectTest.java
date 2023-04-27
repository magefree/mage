package org.mage.test.cards.abilities.oneshot;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class ConditionalOneShotEffectTest extends CardTestPlayerBase {

    @Test
    public void testDisintegrationWithoutArtifact() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);
        // Destroy target creature. If you control an artifact, Unlicensed Disintegration deals 3 damage to that creature's controller.        
        addCard(Zone.HAND, playerA, "Unlicensed Disintegration", 1); // Instant {1}{B}{R}

        addCard(Zone.BATTLEFIELD, playerB, "Silvercoat Lion", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Unlicensed Disintegration", "Silvercoat Lion");
        
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        
        setStrictChooseMode(true);
        execute();

        assertGraveyardCount(playerA, "Unlicensed Disintegration", 1);
        assertGraveyardCount(playerB, "Silvercoat Lion", 1);

        assertLife(playerA, 20);
        assertLife(playerB, 20);
    }
    
   /**
     * Noticed that everytime that i succesfully cast Unlicensed Disintigration
     * with an artifact on the board the opponent wont lose 3 life. The creature
     * dies but the last piece of text does not work
     */
    @Test
    public void testDisintegrationWithArtifact() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);
        
        // Whenever a player casts a red spell, you may gain 1 life.
        addCard(Zone.BATTLEFIELD, playerA, "Dragon's Claw", 1);
        
        // Destroy target creature. If you control an artifact, Unlicensed Disintegration deals 3 damage to that creature's controller.        
        addCard(Zone.HAND, playerA, "Unlicensed Disintegration", 1); // Instant {1}{B}{R}

        addCard(Zone.BATTLEFIELD, playerB, "Silvercoat Lion", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Unlicensed Disintegration", "Silvercoat Lion");
        
        setChoice(playerA, true); // Get life from Dragon's Claw
        
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        
        setStrictChooseMode(true);
        execute();

        assertGraveyardCount(playerA, "Unlicensed Disintegration", 1);
        assertGraveyardCount(playerB, "Silvercoat Lion", 1);

        assertLife(playerA, 21);
        assertLife(playerB, 17);
    }

}
