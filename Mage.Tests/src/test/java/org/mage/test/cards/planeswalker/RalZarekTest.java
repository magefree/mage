
package org.mage.test.cards.planeswalker;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX
 */
public class RalZarekTest extends CardTestPlayerBase {

    @Test
    public void testFirstAbility() {
        // +1: Tap target permanent, then untap another target permanent.
        // -2: Ral Zarek deals 3 damage to any target.
        // -7: Flip five coins. Take an extra turn after this one for each coin that comes up heads.
        String ralZarek = "Ral Zarek"; // {2}{U}{R}
        addCard(Zone.HAND, playerA, ralZarek);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);

        addCard(Zone.BATTLEFIELD, playerB, "Silvercoat Lion");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, ralZarek, true);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "+1:", "Silvercoat Lion"); // Ral Zarek +1
        addTarget(playerA, "Mountain"); // Untap the Mountain

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, ralZarek, 1);
        assertCounterCount(playerA, ralZarek, CounterType.LOYALTY, 5);

        assertTapped("Mountain", false);
        assertTapped("Silvercoat Lion", true);
    }
}
