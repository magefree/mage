package org.mage.test.cards.single.brc;

import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

import mage.constants.PhaseStep;
import mage.constants.Zone;

public class KaylasMusicBoxTest extends CardTestPlayerBase {
    
    private static final String BOX = "Kayla's Music Box";
    private static final String LION = "Silvercoat Lion";

    /**
     * {W}, {T}: Look at the top card of your library, then exile it face down. (You may look at it any time.)
     * {T}: Until end of turn, you may play cards you own exiled with Kayla’s Music Box.
     */
    @Test
    public void testEffect() {
        removeAllCardsFromLibrary(playerA);
        skipInitShuffling();

        addCard(Zone.LIBRARY, playerA, LION, 2);
        addCard(Zone.LIBRARY, playerA, "Mountain", 1);
        addCard(Zone.LIBRARY, playerA, "Plains", 1);
        addCard(Zone.LIBRARY, playerA, "Mountain", 1);
        addCard(Zone.BATTLEFIELD, playerA, BOX);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 1);

        // Player A activates the box, exiling lion
        activateAbility(1, PhaseStep.END_TURN, playerA, "{W}, {T}");

        // Player A draws mountain, activates box again to exile plains
        activateAbility(3, PhaseStep.PRECOMBAT_MAIN, playerA, "{W}, {T}");

        // Player A activates the box on next turn, plays lion and plains
        activateAbility(5, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}");
        waitStackResolved(5, PhaseStep.PRECOMBAT_MAIN, playerA);

        playLand(5, PhaseStep.PRECOMBAT_MAIN, playerA, "Plains");
        castSpell(5, PhaseStep.PRECOMBAT_MAIN, playerA, LION);

        setStrictChooseMode(true);
        setStopAt(5, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, LION, 1);
        assertPermanentCount(playerA, "Plains", 2);
    }

}
