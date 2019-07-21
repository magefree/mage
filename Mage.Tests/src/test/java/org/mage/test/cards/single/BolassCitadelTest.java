
package org.mage.test.cards.single;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author jgray1206
 */
public class BolassCitadelTest extends CardTestPlayerBase {

    private String bolass = "Bolas's Citadel";

    @Test
    public void testBolassCastSplitCardOffTopOfDeckWithLife() {

        addCard(Zone.BATTLEFIELD, playerA, bolass, 1);
        addCard(Zone.LIBRARY, playerA, "Revival // Revenge");
        addCard(Zone.GRAVEYARD, playerA, "Snubhorn Sentry");
        skipInitShuffling();

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Revival", "Snubhorn Sentry");
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerA, 18);
        assertGraveyardCount(playerA, "Snubhorn Sentry", 0);
        assertPermanentCount(playerA, "Snubhorn Sentry", 1);
        assertGraveyardCount(playerA, "Revival // Revenge", 1);

    }

    @Test
    public void testBolassCastCardOffTopOfDeckWithLife() {

        addCard(Zone.BATTLEFIELD, playerA, bolass, 1);
        addCard(Zone.LIBRARY, playerA, "Kraken Hatchling");
        skipInitShuffling();

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Kraken Hatchling");
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerA, 19);
        assertPermanentCount(playerA, "Kraken Hatchling", 1);
    }

}
