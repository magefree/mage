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
        addCard(Zone.BATTLEFIELD, playerB, "Sylvan Echoes" , 1); // Whenever you clash and win, you may draw a card.

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

    }

    @Test
    public void testClashYouWin() {
        prepareClashing();
        addCard(Zone.LIBRARY, playerA, "Divination", 1);
        performClashing();
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();
        assertPowerToughness(playerA, rascal, 3, 3);
    }

    @Test
    public void testClashOppWin() {
        prepareClashing();
        addCard(Zone.LIBRARY, playerB, "Divination", 1);
        performClashing();
        setChoice(playerB, true); // Draw a card from Sylvan Echoes
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();
        assertPowerToughness(playerA, rascal, 2, 2);
        assertHandCount(playerB, "Divination", 1);
    }

    @Test
    public void testClashNoWin() {
        prepareClashing();
        performClashing();
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();
        assertPowerToughness(playerA, rascal, 2, 2);
    }

    @Test
    public void testHoardersGreed() {

        setStrictChooseMode(true);

        removeAllCardsFromLibrary(playerA);
        removeAllCardsFromLibrary(playerB);
        skipInitShuffling();
        addCard(Zone.LIBRARY, playerB, "Island", 5);
        addCard(Zone.LIBRARY, playerA, "Island", 5);
        addCard(Zone.LIBRARY, playerA, "Wastes", 1);
        addCard(Zone.LIBRARY, playerA, "Razorfield Thresher", 1);
        addCard(Zone.LIBRARY, playerA, "Phyrexian Hulk", 1);
        addCard(Zone.LIBRARY, playerA, "Stone Golem", 1);
        addCard(Zone.LIBRARY, playerA, "Gilded Sentinel", 1);
        addCard(Zone.LIBRARY, playerA, "Stonework Puma", 1);
        addCard(Zone.LIBRARY, playerA, "Field Creeper", 1);
        addCard(Zone.LIBRARY, playerA, "Metallic Sliver", 1);
        addCard(Zone.LIBRARY, playerA, "Memnite", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 4);
        addCard(Zone.HAND, playerA, "Hoarder's Greed", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Hoarder's Greed");
        // Draw two cards (Memnite and Metallic Sliver), lose 2 life, clash:
        setChoice(playerA, "PlayerB");
        setChoice(playerA, false); // Field Creeper to bottom of library
        setChoice(playerB, true);
        // Clash won. Draw two cards (Stonework Puma and Gilded Sentinel), lose 2 life, clash:
        setChoice(playerA, "PlayerB");
        setChoice(playerA, false); // Stone Golem to bottom of library
        setChoice(playerB, true);
        // Clash won. Draw two cards (Phyrexian Hulk and Razorfield Thresher), lose 2 life, clash:
        setChoice(playerA, "PlayerB");
        setChoice(playerA, true); // Wastes to top of library
        setChoice(playerB, true);
        // No winner in this clash.

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerA, 20 - (2 * 3));
        assertHandCount(playerA, "Memnite", 1);
        assertHandCount(playerA, "Metallic Sliver", 1);
        assertHandCount(playerA, "Field Creeper", 0);
        assertHandCount(playerA, "Stonework Puma", 1);
        assertHandCount(playerA, "Gilded Sentinel", 1);
        assertHandCount(playerA, "Stone Golem", 0);
        assertHandCount(playerA, "Phyrexian Hulk", 1);
        assertHandCount(playerA, "Razorfield Thresher", 1);

    }

}
