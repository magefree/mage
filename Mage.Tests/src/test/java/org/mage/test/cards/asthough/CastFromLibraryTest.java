package org.mage.test.cards.asthough;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * Vizier of the Menagerie
 * {3}{G}
 * Creature — Naga Cleric
 * P/T
 *
 * You may look at the top card of your library any time.
 * You may cast creature spells from the top of your library.
 * You may spend mana as though it were mana of any type to cast creature spells.
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
        addCard(Zone.HAND, playerA, "Vizier of the Menagerie", 1); // Creature 3/4 {3}{G}

        skipInitShuffling();

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Vizier of the Menagerie");
        checkPlayableAbility("Can't cast at instant speed", 1, PhaseStep.BEGIN_COMBAT, playerA, "Cast Silvercoat Lion", false);
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
        // Added so that neither of the Dryad Arbors end up in the hand.
        addCard(Zone.LIBRARY, playerA, "Forest", 1);

        addCard(Zone.HAND, playerA, "Vizier of the Menagerie", 1); // Creature 3/4 {3}{G}

        skipInitShuffling();

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Vizier of the Menagerie");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        checkPlayableAbility("Can't cast Dryad Arbor", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Dryad", false);
        checkPlayableAbility("Can't cast Dryad Arbor", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Play Dryad", false);

        checkPlayableAbility("Can't cast Dryad Arbor", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Cast Dryad", false);
        checkPlayableAbility("Can't cast Dryad Arbor", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Play Dryad", false);

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Vizier of the Menagerie", 1);
        assertPermanentCount(playerA, "Dryad Arbor", 0); // can't be cast, only played
        assertLibraryCount(playerA, "Dryad Arbor", 2);
    }
}
