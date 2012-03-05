package org.mage.test.cards;

import mage.Constants;
import mage.Constants.PhaseStep;
import org.junit.Ignore;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author BetaSteward
 */
public class TestGrafdiggersCage extends CardTestPlayerBase {
    
    @Test
    public void testCard() {
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Grafdigger's Cage");
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Swamp", 2);
        addCard(Constants.Zone.GRAVEYARD, playerA, "Lingering Souls");
        
        activateAbility(1, Constants.PhaseStep.PRECOMBAT_MAIN, playerA, "Flashback {1}{B}");
        setStopAt(1, Constants.PhaseStep.BEGIN_COMBAT);
        execute();
        
        assertLife(playerA, 20);
        assertLife(playerB, 20);
        assertPermanentCount(playerA, "Spirit", 0);
        assertGraveyardCount(playerA, "Lingering Souls", 1);
    }

    @Test
    public void testCard1() {
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Grafdigger's Cage");
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Swamp", 5);
        addCard(Constants.Zone.HAND, playerA, "Rise from the Grave", 1);
        addCard(Constants.Zone.GRAVEYARD, playerA, "Craw Wurm");
        
        castSpell(1, Constants.PhaseStep.PRECOMBAT_MAIN, playerA, "Rise from the Grave", "Craw Wurm");
        setStopAt(1, Constants.PhaseStep.BEGIN_COMBAT);
        execute();
        
        assertLife(playerA, 20);
        assertLife(playerB, 20);
        assertPermanentCount(playerA, "Craw Wurm", 0);
        assertGraveyardCount(playerA, "Craw Wurm", 1);
        assertGraveyardCount(playerA, "Rise from the Grave", 1);
    }
    
}
