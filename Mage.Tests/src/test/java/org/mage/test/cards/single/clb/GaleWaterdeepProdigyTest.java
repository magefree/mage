package org.mage.test.cards.single.clb;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author R
 * jayz
 */
public class GaleWaterdeepProdigyTest extends CardTestPlayerBase {

    @Test
    public void TestGaleWaterDeepProdigy() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 3);

        // Whenever you cast an instant or sorcery spell from your hand, you may cast up to one of the other type from your graveyard.
        // If a spell cast from your graveyard this way would be put into your graveyard, exile it instead.
        addCard(Zone.BATTLEFIELD, playerA, "Gale, Waterdeep Prodigy", 1);

        // Draw two cards on a sorcery
        addCard(Zone.HAND, playerA, "Divination");
        addCard(Zone.GRAVEYARD, playerA, "Lightning Bolt");

        playerA.getLibrary().clear();
        addCard(Zone.LIBRARY, playerA, "Island");
        addCard(Zone.LIBRARY, playerA, "Island");
        skipInitShuffling();

        setStrictChooseMode(true);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Divination");
        // Target Lightning Bolt in graveyard with Gale's ability
        addTarget(playerA, "Lightning Bolt");

        execute();

        // Divination will have been cast, Lightning bolt should not have been cast
        assertHandCount(playerA, 2);
        assertHandCount(playerA, "Island", 2);
        assertLibraryCount(playerA, 0);
        assertGraveyardCount(playerA, 2);
        assertGraveyardCount(playerA, "Divination", 1);
        assertGraveyardCount(playerA, "Lightning Bolt", 1);
    }
}
