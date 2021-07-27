package org.mage.test.cards.enchantments;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author escplan9 (Derek Monturo - dmontur1 at gmail dot com)
 */
public class MindsDilationTest extends CardTestPlayerBase {

    @Test
    public void testExileNonLandCardAndCastIt() {

        /**
         * Mind's Dilation {5}{U}{U} Enchantment Whenever an opponent casts his
         * or her first spell each turn, that player exiles the top card of his
         * or her library. If it's a nonland card, you may cast it without
         * paying its mana cost.
         */
        addCard(Zone.BATTLEFIELD, playerB, "Mind's Dilation", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);
        addCard(Zone.HAND, playerA, "Lightning Bolt", 1);
        addCard(Zone.LIBRARY, playerA, "Divination", 1); // draw 2 cards

        skipInitShuffling();

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", playerB);
        setChoice(playerB, true);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerB, 17);
        assertExileCount("Divination", 0);
        assertHandCount(playerB, 2); // free divination!
    }

    @Test
    public void testExileNonLandCardDontCastIt() {

        removeAllCardsFromLibrary(playerA);

        /**
         * Mind's Dilation {5}{U}{U} Enchantment Whenever an opponent casts his
         * or her first spell each turn, that player exiles the top card of his
         * or her library. If it's a nonland card, you may cast it without
         * paying its mana cost.
         */
        addCard(Zone.BATTLEFIELD, playerB, "Mind's Dilation", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);
        addCard(Zone.HAND, playerA, "Lightning Bolt", 1);
        addCard(Zone.LIBRARY, playerA, "Divination", 1); // draw 2 cards

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", playerB);
        setChoice(playerB, false); // no, I don't want my free 2 cards

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerB, 17);
        assertExileCount("Divination", 1);
        assertHandCount(playerB, 0); // Divination never cast
    }
}
