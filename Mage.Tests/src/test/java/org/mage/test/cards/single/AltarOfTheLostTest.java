package org.mage.test.cards.single;

import mage.Constants;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author BetaSteward
 */
public class AltarOfTheLostTest extends CardTestPlayerBase {
    
    @Test
    public void testCard() {
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Altar of the Lost");
        addCard(Constants.Zone.GRAVEYARD, playerA, "Lingering Souls");

        setChoice(playerA, "Black");
        setChoice(playerA, "Black");

        activateAbility(3, Constants.PhaseStep.PRECOMBAT_MAIN, playerA, "Flashback {1}{B}");
        setStopAt(3, Constants.PhaseStep.BEGIN_COMBAT);
        execute();
        
        assertLife(playerA, 20);
        assertLife(playerB, 20);
        assertPermanentCount(playerA, "Spirit", 2);
    }

    @Test
    public void testCard1() {
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Plains");
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Altar of the Lost");
        addCard(Constants.Zone.HAND, playerA, "Lingering Souls");

        setChoice(playerA, "Black");
        setChoice(playerA, "Black");

        castSpell(3, Constants.PhaseStep.PRECOMBAT_MAIN, playerA, "Lingering Souls");
        setStopAt(3, Constants.PhaseStep.BEGIN_COMBAT);
        execute();
        
        assertLife(playerA, 20);
        assertLife(playerB, 20);
        assertPermanentCount(playerA, "Spirit", 0);
    }
    
}
