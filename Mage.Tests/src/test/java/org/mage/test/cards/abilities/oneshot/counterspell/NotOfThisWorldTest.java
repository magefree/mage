package org.mage.test.cards.abilities.oneshot.counterspell;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author LevelX2
 */
public class NotOfThisWorldTest extends CardTestPlayerBase {

    /**
     * Not of This World doesn't work when trying to counter a triggerd ability
     * from The Abyss targeting your owned and controlled Ruhan of the Fomori .
     * At the time I didn't have any mana open, but Ruhan of the Fomori should
     * qualify for the alternative casting cost (7 less) or Not of This World.
     */
    @Test
    public void testCounterFirstSpell() {
        // At the beginning of each player's upkeep, destroy target nonartifact creature that player controls of their choice. It can't be regenerated.
        addCard(Zone.BATTLEFIELD, playerA, "The Abyss", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Island", 7);
        //
        // Counter target spell or ability that targets a permanent you control.
        // Not of This World costs {7} less to cast if it targets a spell or ability that targets a creature you control with power 7 or greater.
        addCard(Zone.HAND, playerB, "Not of This World");
        //
        // At the beginning of combat on your turn, choose an opponent at random. Ruhan of the Fomori attacks that player this combat if able.
        addCard(Zone.BATTLEFIELD, playerB, "Ruhan of the Fomori", 1); // 7/7

        // trigger on upkeep to destroy
        addTarget(playerB, "Ruhan of the Fomori");
        // try to counter
        castSpell(2, PhaseStep.UPKEEP, playerB, "Not of This World", "stack ability (At the beginning of each player's upkeep");

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertHandCount(playerB, "Not of This World", 0);
        assertGraveyardCount(playerB, "Not of This World", 1);
        assertPermanentCount(playerB, "Ruhan of the Fomori", 1);

        assertTapped("Island", false);
    }

}
