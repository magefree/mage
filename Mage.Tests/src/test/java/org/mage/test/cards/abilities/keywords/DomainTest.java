package org.mage.test.cards.abilities.keywords;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author LevelX2
 */
public class DomainTest extends CardTestPlayerBase {

    /**
     * Collapsing Borders correctly does the 3 damage to each player at the
     * beginning of their upkeeps. However, it does NOT add any life for each
     * type of basic land the player has on the field.
     */
    @Test
    public void testCollapsingBorders() {
        // Domain - At the beginning of each player's upkeep, that player gains 1 life for each basic land type among lands they control.
        // Then Collapsing Borders deals 3 damage to that player.
        addCard(Zone.HAND, playerA, "Collapsing Borders", 1); // {3}{R}
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 3);

        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 3);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Collapsing Borders");

        setStopAt(3, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, "Collapsing Borders", 1);

        assertLife(playerA, 21);
        assertLife(playerB, 18);
    }

}
