package org.mage.test.cards.single.m20;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author jgray1206
 */
public class AetherGustTest extends CardTestPlayerBase {

    /* Aether Gust - Instant {1}{U}
     * Choose target spell or permanent that’s red or green. Its owner puts it on the top or bottom of their library.
     */
    String aetherGust = "Aether Gust";

    /**
     * Issue #5902:
     * Aether Gust is not putting spells back into the owner's library.
     */
    @Test
    public void testAetherGustWorksWithSpells() {
        String barkhide = "Barkhide Troll"; //Arbitrary creature {G}{G}

        removeAllCardsFromLibrary(playerA);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 3);
        addCard(Zone.HAND, playerA, barkhide);
        addCard(Zone.HAND, playerA, aetherGust);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, barkhide);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, aetherGust, barkhide);
        setChoice(playerA, true);

        setStopAt(1, PhaseStep.END_TURN);
        setStrictChooseMode(true);
        execute();

        assertGraveyardCount(playerA, aetherGust, 1);
        assertPermanentCount(playerA, barkhide, 0);
        assertLibraryCount(playerA, 1);
        assertAllCommandsUsed();
    }

    @Test
    public void testAetherGustWorksWithPermanents() {
        String barkhide = "Barkhide Troll"; //Arbitrary creature {G}{G}

        removeAllCardsFromLibrary(playerA);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 3);
        addCard(Zone.HAND, playerA, barkhide);
        addCard(Zone.HAND, playerA, aetherGust);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, barkhide);
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, aetherGust, barkhide);
        setChoice(playerA, true);

        setStopAt(1, PhaseStep.END_TURN);
        setStrictChooseMode(true);
        execute();

        assertGraveyardCount(playerA, aetherGust, 1);
        assertPermanentCount(playerA, barkhide, 0);
        assertLibraryCount(playerA, 1);
        assertAllCommandsUsed();
    }
}
