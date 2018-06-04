
package org.mage.test.cards.abilities.keywords;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class ParleyTest extends CardTestPlayerBase {

    /**
     * Selvala, Explorer Returned reveals cards at the wrong moments.
     *
     * I've noticed her revealing all cards during my upkeep, when I'm not even
     * activating her
     *
     */
    @Test
    public void testNothingHappens() {

        addCard(Zone.BATTLEFIELD, playerA, "Forest");
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);

        // Parley - {T}: Each player reveals the top card of their library. For each nonland card revealed this way, add {G} and you gain 1 life. Then each player draws a card.
        addCard(Zone.HAND, playerA, "Selvala, Explorer Returned");// Creature {1}{G}{W}

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Selvala, Explorer Returned");

        setStopAt(3, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Selvala, Explorer Returned", 1);
    }

    @Test
    public void testTwoMana() {

        addCard(Zone.BATTLEFIELD, playerA, "Forest");
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);

        // Parley - {T}: Each player reveals the top card of their library. For each nonland card revealed this way, add {G} and you gain 1 life. Then each player draws a card.
        addCard(Zone.HAND, playerA, "Selvala, Explorer Returned");// Creature {1}{G}{W}

        addCard(Zone.LIBRARY, playerA, "Silvercoat Lion", 2);
        addCard(Zone.LIBRARY, playerB, "Silvercoat Lion", 2);
        skipInitShuffling();

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Selvala, Explorer Returned");

        activateManaAbility(3, PhaseStep.PRECOMBAT_MAIN, playerA, "<i>Parley");

        setStopAt(3, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Selvala, Explorer Returned", 1);
        assertLife(playerA, 22);
        assertLife(playerB, 20);
    }

}
