
package org.mage.test.cards.enchantments;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class ParadoxHazeTest extends CardTestPlayerBase {

    @Test
    public void testNormal() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 8);
        // Enchant player
        // At the beginning of enchanted player's first upkeep each turn, that player gets an additional upkeep step after this step.
        addCard(Zone.HAND, playerA, "Paradox Haze", 1); // {2}{U}
        // At the beginning of each upkeep, put a 1/1 green Saproling creature token onto the battlefield.
        addCard(Zone.HAND, playerA, "Verdant Force", 1); // {5}{G}{G}{G}

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Paradox Haze", playerA);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Verdant Force");

        setStopAt(3, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, "Paradox Haze", 1);
        assertPermanentCount(playerA, "Verdant Force", 1);
        assertPermanentCount(playerA, "Saproling Token", 3);// 1 from turn 2 and 2 from turn 3
    }

    @Test
    public void testCopied() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 6);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 8);
        // Enchant player
        // At the beginning of enchanted player's first upkeep each turn, that player gets an additional upkeep step after this step.
        addCard(Zone.HAND, playerA, "Paradox Haze", 1); // {2}{U}

        // You may have Copy Enchantment enter the battlefield as a copy of any enchantment on the battlefield.
        addCard(Zone.HAND, playerA, "Copy Enchantment", 1); // {2}{U}

        // At the beginning of each upkeep, put a 1/1 green Saproling creature token onto the battlefield.
        addCard(Zone.HAND, playerA, "Verdant Force", 1); // {5}{G}{G}{G}

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Paradox Haze", playerA);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Copy Enchantment");
        setChoice(playerA, "Paradox Haze");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Verdant Force");

        setStopAt(3, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, "Paradox Haze", 2);
        assertPermanentCount(playerA, "Verdant Force", 1);
        assertPermanentCount(playerA, "Saproling Token", 4); // 1 from turn 2 and 3 from turn 3
    }
}
