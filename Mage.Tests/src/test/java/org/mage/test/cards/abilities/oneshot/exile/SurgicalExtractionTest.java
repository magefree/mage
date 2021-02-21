package org.mage.test.cards.abilities.oneshot.exile;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
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
        // Choose target card in a graveyard other than a basic land card. Search its owner's graveyard,
        // hand, and library for any number of cards with the same name as that card and exile them.
        // Then that player shuffles their library.
        addCard(Zone.HAND, playerA, "Surgical Extraction", 1); // Instant {B/P}
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 2);

        addCard(Zone.GRAVEYARD, playerB, "Breaking // Entering", 2);
        addCard(Zone.HAND, playerB, "Breaking // Entering", 1);
        addCard(Zone.LIBRARY, playerB, "Breaking // Entering", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Surgical Extraction", "Breaking // Entering");

        addTarget(playerA, "Breaking // Entering^Breaking // Entering");
        addTarget(playerA, "Breaking // Entering");
        setChoice(playerA, "Breaking // Entering");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Surgical Extraction", 1);

        assertGraveyardCount(playerB, "Breaking // Entering", 0);
        assertLibraryCount(playerB, "Breaking // Entering", 0);
        assertHandCount(playerB, "Breaking // Entering", 0);

        assertExileCount(playerB, "Breaking // Entering", 4);

    }
}
