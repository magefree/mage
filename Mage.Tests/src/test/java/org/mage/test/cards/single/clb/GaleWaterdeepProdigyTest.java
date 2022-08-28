package org.mage.test.cards.single.clb;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author Rjayz
 */
public class GaleWaterdeepProdigyTest extends CardTestPlayerBase {

    @Test
    public void TestGaleWaterDeepProdigy() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 10);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 10);

        // Whenever you cast an instant or sorcery spell from your hand, you may cast up to one of the other type from your graveyard.
        // If a spell cast from your graveyard this way would be put into your graveyard, exile it instead.
        addCard(Zone.BATTLEFIELD, playerA, "Gale, Waterdeep Prodigy", 1);

        // Draw two cards, sorcery
        addCard(Zone.HAND, playerA, "Divination");
        // Deal three damage to any target, instant
        addCard(Zone.GRAVEYARD, playerA, "Lightning Bolt");

        playerA.getLibrary().clear();
        addCard(Zone.LIBRARY, playerA, "Island");
        addCard(Zone.LIBRARY, playerA, "Island");
        skipInitShuffling();

        setStrictChooseMode(true);

        // Cast Divination from hand,
        // this will trigger Gale's ability and let playerA cast an instant from their graveyard
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Divination", true);
        // Target Lightning Bolt in graveyard with Gale's ability
        addTarget(playerA, "Lightning Bolt");
        // Choose to cast Lightning Bolt when prompted
        setChoice(playerA, true);
        // Target opponent with Lightning bolt
        addTarget(playerA, playerB);

        execute();

        // Assert Divination was cast from hand
        assertHandCount(playerA, 2);
        assertHandCount(playerA, "Island", 2);
        assertLibraryCount(playerA, 0);
        assertGraveyardCount(playerA, 1);
        assertGraveyardCount(playerA, "Divination", 1);

        // Assert Lightning Bolt was cast from graveyard,
        // and afterwards exiled instead of being put back into the graveyard
        assertExileCount(playerA, 1);
        assertExileCount(playerA, "Lightning Bolt", 1);

        // Assert opponent was targeted by Lightning Bolt
        assertLife(playerB, 17);
    }
}
