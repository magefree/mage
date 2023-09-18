
package org.mage.test.cards.abilities.keywords;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * 702.56. Forecast 702.56a A forecast ability is a special kind of activated
 * ability that can be activated only from a player's hand. It's written
 * "Forecast - [Activated ability]."
 *
 * 702.56b A forecast ability may be activated only during the upkeep step of
 * the card's owner and only once each turn. The controller of the forecast
 * ability reveals the card with that ability from their hand as the
 * ability is activated. That player plays with that card revealed in their
 * hand until it leaves the player's hand or until a step or phase that isn't an
 * upkeep step begins, whichever comes first.
 *
 * @author LevelX2
 */
public class ForecastTest extends CardTestPlayerBase {

    @Test
    public void testPaladinOfPrahv() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);
        addCard(Zone.HAND, playerA, "Silvercoat Lion");
        addCard(Zone.HAND, playerA, "Paladin of Prahv");

        setStrictChooseMode(true);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Silvercoat Lion");

        activateAbility(3, PhaseStep.UPKEEP, playerA, "Forecast");
        addTarget(playerA, "Silvercoat Lion");

        attack(3, playerA, "Silvercoat Lion");
        
        setStopAt(3, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, "Silvercoat Lion", 1);
        assertHandCount(playerA, "Paladin of Prahv", 1);
        
        assertLife(playerA, 22);
        assertLife(playerB, 18);
    }
}
