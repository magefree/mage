package org.mage.test.cards.abilities.keywords;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author xenohedron
 */
public class ClashEffectTest extends CardTestPlayerBase {

    /**
     * 701.23. Clash
     * 701.23a. To clash, a player reveals the top card of their library.
     * That player may then put that card on the bottom of their library.
     * 701.23b. "Clash with an opponent" means "Choose an opponent. You and that opponent each clash."
     * 701.23c. Each clashing player reveals the top card of their library at the same time.
     * Then those players decide in APNAP order (see rule 101.4) where to put those cards, then those cards move at the same time.
     * 701.23d. A player wins a clash if that player revealed a card with a higher mana value than all other cards revealed in that clash.
     */

    private static final String rascal = "Paperfin Rascal"; // 2/2 for 2U, ETB clash, +1/+1 counter if won (so 3/3)

    private void prepareClashing() {

        setStrictChooseMode(true);

        addCard(Zone.HAND, playerA, rascal);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 3);

        // Default: both players have only lands with mana value 0
        // Add card with greater mana value to one player's library so that they win the clash
        removeAllCardsFromLibrary(playerA);
        removeAllCardsFromLibrary(playerB);
        addCard(Zone.LIBRARY, playerA, "Island", 4);
        addCard(Zone.LIBRARY, playerB, "Island", 4);
        skipInitShuffling();

    }

    private void performClashing() {

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, rascal);
        setChoice(playerA, "PlayerB"); // choose an opponent to clash with
        setChoice(playerA, false); // put revealed card to bottom of library
        setChoice(playerB, true); // put revealed card to top of library

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

    }

    @Test
    public void testClashYouWin() {
        prepareClashing();
        addCard(Zone.LIBRARY, playerA, "Divination", 1);
        performClashing();
        assertPowerToughness(playerA, rascal, 3, 3);
    }

    @Test
    public void testClashOppWin() {
        prepareClashing();
        addCard(Zone.LIBRARY, playerB, "Divination", 1);
        performClashing();
        assertPowerToughness(playerA, rascal, 2, 2);
    }

    @Test
    public void testClashNoWin() {
        prepareClashing();
        performClashing();
        assertPowerToughness(playerA, rascal, 2, 2);
    }

}
