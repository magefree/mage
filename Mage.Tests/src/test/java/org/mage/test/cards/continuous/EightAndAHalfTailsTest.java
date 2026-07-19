
package org.mage.test.cards.continuous;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.game.permanent.Permanent;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class EightAndAHalfTailsTest extends CardTestPlayerBase {

    @Test
    public void testColorChange() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 1);

        // {1}{W}: Target permanent you control gains protection from white until end of turn.
        // {1}: Target spell or permanent becomes white until end of turn.
        addCard(Zone.BATTLEFIELD, playerA, "Eight-and-a-Half-Tails", 1); // Creature 2/2

        addCard(Zone.BATTLEFIELD, playerB, "Nekusar, the Mindrazer", 1);

        // At the beginning of each player's draw step, that player draws an additional card.
        // Whenever an opponent draws a card, Nekusar, the Mindrazer deals 1 damage to that player.
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{1}:", "Nekusar, the Mindrazer");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerB, "Nekusar, the Mindrazer", 1);
        Permanent nekusar = getPermanent("Nekusar, the Mindrazer");

        Assertions.assertTrue(nekusar.getColor(currentGame).isWhite(), "Nekusar should be white");
        Assertions.assertFalse(nekusar.getColor(currentGame).isBlue(), "Nekusar should not be blue");
        Assertions.assertFalse(nekusar.getColor(currentGame).isBlack(), "Nekusar should not be black");
        Assertions.assertFalse(nekusar.getColor(currentGame).isRed(), "Nekusar should not be red");

    }

    /**
     * I made opponent's Nekusar white with 8.5 tails and when Nekusar was
     * recast he was still white.
     */
    @Test
    public void testColorChangeIsReset() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 1);

        // {1}{W}: Target permanent you control gains protection from white until end of turn.
        // {1}: Target spell or permanent becomes white until end of turn.
        addCard(Zone.BATTLEFIELD, playerA, "Eight-and-a-Half-Tails", 1); // Creature 2/2

        // At the beginning of each player's draw step, that player draws an additional card.
        // Whenever an opponent draws a card, Nekusar, the Mindrazer deals 1 damage to that player.
        addCard(Zone.BATTLEFIELD, playerB, "Nekusar, the Mindrazer", 1);

        // Exile target creature you control, then return it to the battlefield under its owner's control.
        // Flashback {3}{U}
        addCard(Zone.HAND, playerB, "Momentary Blink", 1); // Instant {1}{W}
        addCard(Zone.BATTLEFIELD, playerB, "Plains", 2);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{1}:", "Nekusar, the Mindrazer");
        castSpell(1, PhaseStep.BEGIN_COMBAT, playerB, "Momentary Blink", "Nekusar, the Mindrazer");

        setStopAt(1, PhaseStep.END_COMBAT);
        execute();

        assertPermanentCount(playerB, "Nekusar, the Mindrazer", 1);
        Permanent nekusar = getPermanent("Nekusar, the Mindrazer");

        assertGraveyardCount(playerB, "Momentary Blink", 1);
        Assertions.assertFalse(nekusar.getColor(currentGame).isWhite(), "Nekusar should not be white");
        Assertions.assertTrue(nekusar.getColor(currentGame).isBlue(), "Nekusar should be blue");
        Assertions.assertTrue(nekusar.getColor(currentGame).isBlack(), "Nekusar should be black");
        Assertions.assertTrue(nekusar.getColor(currentGame).isRed(), "Nekusar should be red");

    }

}
