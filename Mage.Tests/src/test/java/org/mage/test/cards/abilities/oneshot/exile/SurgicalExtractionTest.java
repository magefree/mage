package org.mage.test.cards.abilities.oneshot.exile;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * {@link mage.cards.s.SurgicalExtraction Surgical Extraction}
 * {B/P}
 * Instant
 * ({B/P} can be paid with either {B} or 2 life.)
 * Choose target card in a graveyard other than a basic land card.
 * Search its owner’s graveyard, hand, and library for any number of cards with the same name as that card and exile them.
 * Then that player shuffles.
 *
 * @author LevelX2
 */
public class SurgicalExtractionTest extends CardTestPlayerBase {
    /**
     * I noticed that surgical extraction did not allow me to select any cards
     * to exile when I targeted breaking // entering. It did however allow my
     * opponent to target lingering souls so it could be a split card
     * interaction or just a random glitch.
     */
    @Test
    public void testSearchAndExileSplitCards() {
        addCard(Zone.HAND, playerA, "Surgical Extraction", 1); // Instant {B/P}

        addCard(Zone.GRAVEYARD, playerB, "Breaking // Entering", 2);
        addCard(Zone.HAND, playerB, "Breaking // Entering", 1);
        addCard(Zone.LIBRARY, playerB, "Breaking // Entering", 1);

        setStrictChooseMode(true);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Surgical Extraction", "Breaking // Entering");
        setChoice(playerA, "Yes"); // Pay 2 life to cast instead of {B}
        addTarget(playerA, "Breaking // Entering^Breaking // Entering"); // Graveyard
        addTarget(playerA, "Breaking // Entering"); // Hand
        addTarget(playerA, "Breaking // Entering"); // Library

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Surgical Extraction", 1);

        assertGraveyardCount(playerB, "Breaking // Entering", 0);
        assertLibraryCount(playerB, "Breaking // Entering", 0);
        assertHandCount(playerB, "Breaking // Entering", 0);

        assertExileCount(playerB, "Breaking // Entering", 4);
    }
}
