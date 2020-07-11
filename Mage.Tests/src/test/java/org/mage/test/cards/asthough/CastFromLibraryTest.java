package org.mage.test.cards.asthough;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class CastFromLibraryTest extends CardTestPlayerBase {

    /**
     * Any creature that you cast through Vizier of the Menagerie (ie the card
     * on top of your deck) is cast-able at instant speed. This means that they
     * can be cast on your opponent's turn, before you scry (and thus change the
     * top card), or even right before the Vizier gets destroyed by a fatal push
     * or similar.
     */
    @Test
    public void testVizierOfTheMenagerieWithGenericCreatures() {
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 8);

        addCard(Zone.LIBRARY, playerA, "Silvercoat Lion", 2);
        // You may look at the top card of your library. (You may do this at any time.)
        // You may cast the top card of your library if it's a creature card.
        // You may spend mana as though it were mana of any type to cast creature spells.
        addCard(Zone.HAND, playerA, "Vizier of the Menagerie", 1); // Creature 3/4 {3}{G}

        skipInitShuffling();

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Vizier of the Menagerie");
        castSpell(1, PhaseStep.BEGIN_COMBAT, playerA, "Silvercoat Lion");
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Silvercoat Lion");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Vizier of the Menagerie", 1);
        assertPermanentCount(playerA, "Silvercoat Lion", 1);
        assertLibraryCount(playerA, "Silvercoat Lion", 1);

    }

    @Test
    public void testVizierOfTheMenagerieWithDryadArbor() {
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 8);
        addCard(Zone.LIBRARY, playerA, "Dryad Arbor", 2);
        // You may look at the top card of your library. (You may do this at any time.)
        // You may cast the top card of your library if it's a creature card.
        // You may spend mana as though it were mana of any type to cast creature spells.
        addCard(Zone.HAND, playerA, "Vizier of the Menagerie", 1); // Creature 3/4 {3}{G}

        skipInitShuffling();

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Vizier of the Menagerie");
        castSpell(1, PhaseStep.BEGIN_COMBAT, playerA, "Dryad Arbor");
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Dryad Arbor");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Vizier of the Menagerie", 1);
        assertPermanentCount(playerA, "Dryad Arbor", 0); // can't be cast, only played
        assertLibraryCount(playerA, "Dryad Arbor", 2);

    }
}
