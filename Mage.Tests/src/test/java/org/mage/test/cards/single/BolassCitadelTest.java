
package org.mage.test.cards.single;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author jgray1206
 */
public class BolassCitadelTest extends CardTestPlayerBase {

    /*Bolas's Citadel {3}{B}{B}{B}
     *You may look at the top card of your library any time.
     *You may play the top card of your library. If you cast a spell this way, pay life equal to its converted mana cost rather than pay its mana cost.
     *{T}, Sacrifice ten nonland permanents: Each opponent loses 10 life.
     */
    private String bolass = "Bolas's Citadel";

    /*
     * Issue #5912: Bolas's is not able to cast split cards off top of deck with life.
     */
    @Test
    public void testBolassCastSplitCardOffTopOfDeckWithLife() {

        /* Arbitrary split card
         * Return target creature card with converted mana cost 3 or less from your graveyard to the battlefield
         */
        String revivalRevenge = "Revival // Revenge";
        String snubhorn = "Snubhorn Sentry"; //Arbitrary creature with converted mana cost < 3

        addCard(Zone.BATTLEFIELD, playerA, bolass, 1);
        addCard(Zone.LIBRARY, playerA, revivalRevenge);
        addCard(Zone.GRAVEYARD, playerA, snubhorn);
        skipInitShuffling();

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Revival", snubhorn);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerA, 18);
        assertGraveyardCount(playerA, snubhorn, 0);
        assertPermanentCount(playerA, snubhorn, 1);
        assertGraveyardCount(playerA, revivalRevenge, 1);

    }

    /*
     * Test basic functionality of Bolas's
     */
    @Test
    public void testBolassCastCardOffTopOfDeckWithLife() {
        String krakenHatchling = "Kraken Hatchling"; //Arbitrary creature to cast using life
        addCard(Zone.BATTLEFIELD, playerA, bolass, 1);
        addCard(Zone.LIBRARY, playerA, krakenHatchling);
        skipInitShuffling();

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, krakenHatchling);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerA, 19);
        assertPermanentCount(playerA, krakenHatchling, 1);
    }

}
