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

    @Test
    public void testGainEmerge() {
        String herigast = "Herigast, Erupting Nullkite"; // {9} 6/6 Flying
        // Each creature spell you cast has emerge. The emerge cost is equal to its mana cost.
        String gorger = "Vastwood Gorger"; // 5G 5/6
        String elemental = "Air Elemental"; // 3UU 4/4 Flying

        addCard(Zone.BATTLEFIELD, playerA, herigast);
        addCard(Zone.BATTLEFIELD, playerA, "Forest");
        addCard(Zone.BATTLEFIELD, playerA, elemental);
        addCard(Zone.HAND, playerA, gorger);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Vastwood Gorger with emerge");
        setChoice(playerA, elemental);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, elemental, 1);
        assertPermanentCount(playerA, gorger, 1);
    }

}
