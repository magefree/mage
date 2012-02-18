package org.mage.test.cards;

import junit.framework.Assert;
import mage.Constants;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author BetaSteward
 */
public class TestArchangelsLight extends CardTestPlayerBase {

    @Test
    public void testCard() {
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Plains", 8);
        addCard(Constants.Zone.GRAVEYARD, playerA, "Forest", 6);
        addCard(Constants.Zone.HAND, playerA, "Archangel's Light");

        castSpell(1, Constants.PhaseStep.PRECOMBAT_MAIN, playerA, "Archangel's Light");

        setStopAt(1, Constants.PhaseStep.BEGIN_COMBAT);
        execute();
        
        assertLife(playerA, 32);
        assertLife(playerB, 20);
        assertGraveyardCount(playerA, 1);
        assertGraveyardCount(playerA, "Archangel's Light", 1);
        Assert.assertEquals(currentGame.getPlayer(playerA.getId()).getLibrary().size(), 66);
    }

    
}
