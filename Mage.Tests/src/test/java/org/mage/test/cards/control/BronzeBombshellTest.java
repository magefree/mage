
package org.mage.test.cards.control;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX
 */
public class BronzeBombshellTest extends CardTestPlayerBase {

    @Test
    public void testEndlessWhispers() {
        // When a player other than Bronze Bombshell's owner controls it, that player sacrifices it.
        // If the player does, Bronze Bombshell deals 7 damage to the player.
        addCard(Zone.BATTLEFIELD, playerA, "Bronze Bombshell", 1);

        // Each creature has "When this creature dies, choose target opponent.
        // That player puts this card from its owner's graveyard onto the battlefield
        // under their control at the beginning of the next end step."
        addCard(Zone.BATTLEFIELD, playerA, "Endless Whispers", 1);

        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 3);
        // Destroy target creature or planeswalker..
        addCard(Zone.HAND, playerA, "Hero's Downfall"); // {1}{B}{B}

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Hero's Downfall", "Bronze Bombshell");
        // playerB is autochosen since only possible target

        setStopAt(2, PhaseStep.UPKEEP);
        execute();

        assertGraveyardCount(playerA, "Hero's Downfall", 1);
        assertGraveyardCount(playerA, "Bronze Bombshell", 1);

        assertLife(playerA, 20);
        assertLife(playerB, 13);
    }
}
