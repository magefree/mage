
package org.mage.test.cards.abilities.keywords;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class EmergeTest extends CardTestPlayerBase {

    /**
     * Wretched Gryff is bugged. I could not use its Emerge ability. Clicking on
     * the card did not give me the interaction menu.
     */
    @Test
    public void testCastWithEmerge() {

        addCard(Zone.BATTLEFIELD, playerA, "Island", 4);
        // Emerge {5}{U} (You may cast this spell by sacrificing a creature and paying the emerge cost reduced by that creature's converted mana cost.)
        // When you cast Wretched Gryff, draw a card.
        // Flying
        addCard(Zone.HAND, playerA, "Wretched Gryff"); // Creature

        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Wretched Gryff with emerge");
        setChoice(playerA, "Silvercoat Lion");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Silvercoat Lion", 1);
        assertPermanentCount(playerA, "Wretched Gryff", 1);
    }

}
