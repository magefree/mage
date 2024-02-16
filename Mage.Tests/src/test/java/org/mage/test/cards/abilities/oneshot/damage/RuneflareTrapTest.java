
package org.mage.test.cards.abilities.oneshot.damage;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class RuneflareTrapTest extends CardTestPlayerBase {

    /**
     * Runeflare Trap counts the starting hand as card draw for the purpose of
     * enabling its alternative casting cost, which allows for a turn 1 cast for
     * 1 red mana dealing up to 7 damage.
     */
    @Test
    public void testDamageFromInstantToPlayer() {
        // If an opponent drew three or more cards this turn, you may pay {R} rather than pay Runeflare Trap's mana cost.
        // Runeflare Trap deals damage to target player equal to the number of cards in that player's hand.
        addCard(Zone.HAND, playerB, "Runeflare Trap");
        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 1);

        checkPlayableAbility("Can't Runeflare Trap", 1, PhaseStep.PRECOMBAT_MAIN, playerB, "Cast Runeflare", false);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();
    }
}
