package org.mage.test.cards;

import mage.Constants;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * also tests regenerate and
 * tests that permanents with protection can be sacrificed
 * 
 * @author BetaSteward
 */
public class FiendOfTheShadowsTest extends CardTestPlayerBase {

    @Test
    public void testCard() {
        addCard(Constants.Zone.BATTLEFIELD, playerA, "White Knight");
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Fiend of the Shadows");
        addCard(Constants.Zone.BATTLEFIELD, playerB, "Mountain");
        addCard(Constants.Zone.HAND, playerB, "Lightning Bolt");
        
        activateAbility(1, Constants.PhaseStep.PRECOMBAT_MAIN, playerA, "Sacrifice a human: Regenerate {this}. ");
        castSpell(1, Constants.PhaseStep.POSTCOMBAT_MAIN, playerB, "Lightning Bolt", "Fiend of the Shadows");

        setStopAt(1, Constants.PhaseStep.END_TURN);
        execute();
        
        assertLife(playerA, 20);
        assertLife(playerB, 20);
        assertPermanentCount(playerA, "Fiend of the Shadows", 1);
        assertPermanentCount(playerA, "White Knight", 0);
    }

    @Test
    public void testCardExile1() {
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Fiend of the Shadows");
        removeAllCardsFromHand(playerB);
        addCard(Constants.Zone.HAND, playerB, "Swamp");
        
        attack(1, playerA, "Fiend of the Shadows");
        playLand(1, Constants.PhaseStep.POSTCOMBAT_MAIN, playerA, "Swamp");

        setStopAt(1, Constants.PhaseStep.END_TURN);
        execute();
        
        assertLife(playerA, 20);
        assertLife(playerB, 17);
        assertPermanentCount(playerA, "Fiend of the Shadows", 1);
        assertPermanentCount(playerA, "Swamp", 1);
        assertHandCount(playerB, 0);
    }

    @Test
    public void testCardExile2() {
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Mountain");
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Fiend of the Shadows");
        removeAllCardsFromHand(playerB);
        addCard(Constants.Zone.HAND, playerB, "Lightning Bolt");
        
        attack(1, playerA, "Fiend of the Shadows");
        castSpell(1, Constants.PhaseStep.POSTCOMBAT_MAIN, playerA, "Lightning Bolt", playerB);

        setStopAt(1, Constants.PhaseStep.END_TURN);
        execute();
        
        assertLife(playerA, 20);
        assertLife(playerB, 14);
        assertPermanentCount(playerA, "Fiend of the Shadows", 1);
        assertGraveyardCount(playerA, "Lightning Bolt", 0);
        assertHandCount(playerB, 0);
        assertGraveyardCount(playerB, "Lightning Bolt", 1);
    }

}
