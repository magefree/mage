package org.mage.test.cards.single.yneo;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author JayDi85
 */
public class JukaiLiberatorTest extends CardTestPlayerBase {

    @Test
    public void test_SeekNonland_PermanentCard() {
        removeAllCardsFromLibrary(playerA);
        addCard(Zone.LIBRARY, playerA, "Lightning Bolt");
        addCard(Zone.LIBRARY, playerA, "Divination");
        addCard(Zone.LIBRARY, playerA, "Opt");
        addCard(Zone.LIBRARY, playerA, "Island");
        addCard(Zone.LIBRARY, playerA, "Grizzly Bears");

        // Whenever Jukai Liberator deals combat damage to a player, choose land or nonland.
        // Seek a permanent card of the chosen kind.
        addCard(Zone.BATTLEFIELD, playerA, "Jukai Liberator"); // 3/3

        attack(1, playerA, "Jukai Liberator", playerB);
        setChoice(playerA, false); // nonland

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerB, 20 - 3);
        assertHandCount(playerA, "Grizzly Bears", 1);
        assertHandCount(playerA, 1);
    }

    @Test
    public void test_SeekNonland_NoPermanentCards() {
        removeAllCardsFromLibrary(playerA);
        addCard(Zone.LIBRARY, playerA, "Lightning Bolt");
        addCard(Zone.LIBRARY, playerA, "Divination");
        addCard(Zone.LIBRARY, playerA, "Island");

        // Whenever Jukai Liberator deals combat damage to a player, choose land or nonland.
        // Seek a permanent card of the chosen kind.
        addCard(Zone.BATTLEFIELD, playerA, "Jukai Liberator"); // 3/3

        attack(1, playerA, "Jukai Liberator", playerB);
        setChoice(playerA, false); // nonland

        // instants and sorceries aren't permanent cards
        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertHandCount(playerA, 0);
        assertLibraryCount(playerA, 3);
    }
}
