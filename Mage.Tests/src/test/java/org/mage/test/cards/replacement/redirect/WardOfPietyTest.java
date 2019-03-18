
package org.mage.test.cards.replacement.redirect;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class WardOfPietyTest extends CardTestPlayerBase {

    @Test
    public void testNonCombatDamageToPlayer() {
        addCard(Zone.HAND, playerB, "Lightning Bolt");
        addCard(Zone.BATTLEFIELD, playerB, "Mountain");

        // Enchant creature
        // {1}{W}: The next 1 damage that would be dealt to enchanted creature this turn is dealt to any target instead.
        addCard(Zone.HAND, playerA, "Ward of Piety"); // {1}{W}
        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion"); // 2/2
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 6);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Ward of Piety", "Silvercoat Lion");

        activateAbility(1, PhaseStep.BEGIN_COMBAT, playerA, "{1}{W}: The next 1 damage", playerB);
        activateAbility(1, PhaseStep.BEGIN_COMBAT, playerA, "{1}{W}: The next 1 damage", playerB);

        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerB, "Lightning Bolt", "Silvercoat Lion");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerB, "Lightning Bolt", 1);
        assertPermanentCount(playerA, "Ward of Piety", 1);
        assertPermanentCount(playerA, "Silvercoat Lion", 1);

        assertLife(playerA, 20);
        assertLife(playerB, 18);

    }

}
