
package org.mage.test.cards.asthough;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class CastAsInstantTest extends CardTestPlayerBase {

    @Test
    public void testEffectOnlyForOneTurn() {
        addCard(Zone.BATTLEFIELD, playerB, "Island");
        addCard(Zone.BATTLEFIELD, playerB, "Swamp", 4);
        // The next sorcery card you cast this turn can be cast as though it had flash.
        // Draw a card.
        addCard(Zone.HAND, playerB, "Quicken"); // {U}
        // Devoid (This card has no color.)
        // Target opponent exiles two cards from their hand and loses 2 life.
        addCard(Zone.HAND, playerB, "Witness the End"); // {3}{B}

        addCard(Zone.HAND, playerA, "Silvercoat Lion", 2);

        castSpell(1, PhaseStep.UPKEEP, playerB, "Quicken", true);
        castSpell(1, PhaseStep.UPKEEP, playerB, "Witness the End", playerA);

        setStopAt(1, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertGraveyardCount(playerB, "Quicken", 1);
        assertGraveyardCount(playerB, "Witness the End", 1);

        assertExileCount("Silvercoat Lion", 2);

        assertLife(playerA, 18);
        assertLife(playerB, 20);

    }

}
