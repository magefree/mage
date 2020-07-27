package org.mage.test.cards.single.c16;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author escplan9
 */
public class DivergentTransformationsTest extends CardTestPlayerBase {

    /*
    * Reported bug: creatures exiled end up in both the exile pile and remain on the battlefield.
    * Manual testing unable to reproduce this issue.    
    */
    @Test
    public void testDivergentTransformationsExiles() 
    {
        /*
        Divergent Transformations {6}{R}
         Instant
        Undaunted (This spell costs 1 less to cast for each opponent.)
        Exile two target creatures. For each of those creatures, its controller reveals cards from the top of their library until they reveal a creature card,
                puts that card onto the battlefield, then shuffles the rest into their library.
        */
        String dTransformations = "Divergent Transformations";        
        String memnite = "Memnite"; // {0} 1/1
        String gBears = "Grizzly Bears"; // {1}{G} 2/2
        String hGiant = "Hill Giant"; // {3}{R} 3/3
        String mFlunkies = "Mogg Flunkies"; // {1}{R} 3/3 cannot attack alone
        
        addCard(Zone.HAND, playerA, dTransformations);
        addCard(Zone.BATTLEFIELD, playerA, memnite);
        addCard(Zone.BATTLEFIELD, playerB, gBears);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 6); // costs 1 less with Undaunted
        addCard(Zone.LIBRARY, playerA, hGiant);
        addCard(Zone.LIBRARY, playerB, mFlunkies);
        
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, dTransformations, memnite + '^' + gBears);
        
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();
        
        assertGraveyardCount(playerA, dTransformations, 1);
        assertTappedCount("Mountain", true, 6);
        assertExileCount(playerA, memnite, 1);
        assertExileCount(playerA, 1);
        assertExileCount(playerB, gBears, 1);
        assertExileCount(playerB, 1);
        assertPermanentCount(playerA, memnite, 0);
        assertPermanentCount(playerB, gBears, 0);
        assertPermanentCount(playerA, hGiant, 1); // revealed and brought to battlefield
        assertPermanentCount(playerB, mFlunkies, 1);
    }
}
