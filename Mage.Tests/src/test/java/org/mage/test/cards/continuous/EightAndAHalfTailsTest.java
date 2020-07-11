
package org.mage.test.cards.continuous;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.game.permanent.Permanent;
import org.junit.Assert;
import org.junit.Test;
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

        Assert.assertTrue("Nekusar should be white", nekusar.getColor(currentGame).isWhite());
        Assert.assertFalse("Nekusar should not be blue", nekusar.getColor(currentGame).isBlue());
        Assert.assertFalse("Nekusar should not be black", nekusar.getColor(currentGame).isBlack());
        Assert.assertFalse("Nekusar should not be red", nekusar.getColor(currentGame).isRed());

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
        Assert.assertFalse("Nekusar should not be white", nekusar.getColor(currentGame).isWhite());
        Assert.assertTrue("Nekusar should be blue", nekusar.getColor(currentGame).isBlue());
        Assert.assertTrue("Nekusar should be black", nekusar.getColor(currentGame).isBlack());
        Assert.assertTrue("Nekusar should be red", nekusar.getColor(currentGame).isRed());

    }

}
