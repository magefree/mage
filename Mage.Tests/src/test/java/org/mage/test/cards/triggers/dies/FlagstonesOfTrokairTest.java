
package org.mage.test.cards.triggers.dies;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class FlagstonesOfTrokairTest extends CardTestPlayerBase {

    /**
     * If a flagstones of trokair enchated with spreading seas that is then
     * ghostquartered. Should only fetch 1 land, but instead got 2.
     */
    @Test
    public void testDontTriggerIfIsland() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);
        // Enchant land
        // When Spreading Seas enters the battlefield, draw a card.
        // Enchanted land is an Island.
        addCard(Zone.HAND, playerA, "Spreading Seas", 1); // Enchantment Aura - {1}{U}
        // {tap}: Add {W}.
        // When Flagstones of Trokair is put into a graveyard from the battlefield, you may search your library for a Plains card and put it onto the battlefield tapped. If you do, shuffle your library.
        addCard(Zone.BATTLEFIELD, playerA, "Flagstones of Trokair", 1);
        addCard(Zone.LIBRARY, playerA, "Plains", 5);
        // {T}: Add {C}.
        // {T}, Sacrifice Ghost Quarter: Destroy target land. Its controller may search their library for a basic land card, put it onto the battlefield, then shuffle their library.
        addCard(Zone.BATTLEFIELD, playerB, "Ghost Quarter", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Spreading Seas", "Flagstones of Trokair");

        activateAbility(1, PhaseStep.POSTCOMBAT_MAIN, playerB, "{T}, Sacrifice", "Flagstones of Trokair");
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerB, "Ghost Quarter", 1);
        assertGraveyardCount(playerA, "Flagstones of Trokair", 1);
        assertGraveyardCount(playerA, "Spreading Seas", 1);

        assertPermanentCount(playerA, 3);

    }

}
