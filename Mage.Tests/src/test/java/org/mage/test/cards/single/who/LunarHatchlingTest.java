package org.mage.test.cards.single.who;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author Susucr
 */
public class LunarHatchlingTest extends CardTestPlayerBase {

    /**
     * Lunar Hatchling
     * {4}{G}{U}
     * Creature — Alien Beast
     * <p>
     * Flying, trample
     * Basic landcycling {2} ({2}, Discard this card: Search your library for a basic land card, reveal it, put it into your hand, then shuffle.)
     * Escape-{4}{G}{U}, Exile a land you control, Exile five other cards from your graveyard. (You may cast this card from your graveyard for its escape cost.)
     * <p>
     * 6/6
     */
    private static final String hatchling = "Lunar Hatchling";

    @Test
    public void test_EscapeWithAdditionalCost() {
        setStrictChooseMode(true);
        addCard(Zone.GRAVEYARD, playerA, hatchling);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 3);
        addCard(Zone.GRAVEYARD, playerA, "Balduvian Bears", 5);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, hatchling + " with Escape");
        setChoice(playerA, "Forest"); // choose to exile a Forest
        setChoice(playerA, "Balduvian Bears^Balduvian Bears^Balduvian Bears^Balduvian Bears^Balduvian Bears");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, hatchling, 1);
        assertPermanentCount(playerA, "Forest", 2);
        assertPermanentCount(playerA, "Island", 3);
        assertExileCount(playerA, "Forest", 1);
        assertExileCount(playerA, "Balduvian Bears", 5);
        assertGraveyardCount(playerA, "Balduvian Bears", 0);
    }
}
