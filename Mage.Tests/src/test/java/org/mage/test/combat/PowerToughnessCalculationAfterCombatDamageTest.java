package org.mage.test.combat;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * Test that calculated p/t is applied after combat damage resolution, so a 2/2 Nighthowler
 * dies if blocked from a 2/2 creature.
 * All combat damage happens simultaneously. Then any abilities that trigger from combat 
 * damage go on the stack. In normal circumstances, the combo you're trying to construct doesn't work.
 * @author LevelX2
 */
public class PowerToughnessCalculationAfterCombatDamageTest extends CardTestPlayerBase {

    @Test
    public void powerToughnessCalculation() {
        // Pain Seer   Creature - Human Wizard 2/2
        // Inspired - Whenever Pain Seer becomes untapped, reveal the top card of your library and put that card 
        // into your hand. You lose life equal to that card's converted mana cost.
        addCard(Zone.BATTLEFIELD, playerA, "Pain Seer");
        addCard(Zone.GRAVEYARD, playerA, "Silvercoat Lion");
        // Nighthowler and enchanted creature each get +X/+X, where X is the number of creature cards in all graveyards.
        addCard(Zone.BATTLEFIELD, playerB, "Nighthowler");
        addCard(Zone.GRAVEYARD, playerB, "Silvercoat Lion");

        attack(2, playerB, "Nighthowler");
        block(2, playerA, "Pain Seer", "Nighthowler");

        setStopAt(2, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);

        assertGraveyardCount(playerB, "Nighthowler", 1);
        assertGraveyardCount(playerA, "Pain Seer", 1);
    }
}
