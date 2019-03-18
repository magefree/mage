
package org.mage.test.cards.cost.modification;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class KaradorGhostChieftainTest extends CardTestPlayerBase {

    @Test
    public void castReducedTwo() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 5);
        addCard(Zone.GRAVEYARD, playerA, "Silvercoat Lion", 2);
        // Karador, Ghost Chieftain costs {1} less to cast for each creature card in your graveyard.
        // During each of your turns, you may cast one creature card from your graveyard.
        addCard(Zone.HAND, playerA, "Karador, Ghost Chieftain");// {5}{B}{G}{W}

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Karador, Ghost Chieftain");
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();
        assertTappedCount("Island", false, 2);
        assertPermanentCount(playerA, "Karador, Ghost Chieftain", 1);
    }

    /**
     * I had a couple problems in a commander game last night. Using Karador,
     * Ghost Chieftain as my commander. Most of the match, his casting cost was
     * correctly calculated, reducing the extra commander tax and generic mana
     * costs by the number of creature cards in my graveyard. On the 4th cast
     * though, the cost was 12 mana total. I tried casting a few times over a
     * couple turns, but it was still an incorrect cost (I had probably 15
     * creatures in my graveyard).
     */
    @Test
    public void castReducedSeven() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 5);
        addCard(Zone.GRAVEYARD, playerA, "Silvercoat Lion", 7);
        // Karador, Ghost Chieftain costs {1} less to cast for each creature card in your graveyard.
        // During each of your turns, you may cast one creature card from your graveyard.
        addCard(Zone.HAND, playerA, "Karador, Ghost Chieftain");// {5}{B}{G}{W}

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Karador, Ghost Chieftain");
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();
        assertTappedCount("Island", false, 5);
        assertPermanentCount(playerA, "Karador, Ghost Chieftain", 1);
    }

}
