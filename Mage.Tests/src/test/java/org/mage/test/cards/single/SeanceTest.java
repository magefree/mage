package org.mage.test.cards.single;

import mage.Constants;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author BetaSteward
 */
public class SeanceTest extends CardTestPlayerBase {
    
    @Test
    public void testCard() {
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Seance");
        addCard(Constants.Zone.GRAVEYARD, playerA, "Craw Wurm");
        
        setStopAt(1, Constants.PhaseStep.DRAW);
        execute();
        
        assertLife(playerA, 20);
        assertLife(playerB, 20);
        assertExileCount("Craw Wurm", 1);
        assertPermanentCount(playerA, "Craw Wurm", 1);
    }

    @Test
    public void testCard1() {
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Seance");
        addCard(Constants.Zone.GRAVEYARD, playerA, "Craw Wurm");
        
        setStopAt(2, Constants.PhaseStep.DRAW);
        execute();
        
        assertLife(playerA, 20);
        assertLife(playerB, 20);
        assertExileCount("Craw Wurm", 1);
        assertPermanentCount(playerA, "Craw Wurm", 0);
    }
    
}
