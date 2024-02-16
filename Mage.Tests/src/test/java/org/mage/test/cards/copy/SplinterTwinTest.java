
package org.mage.test.cards.copy;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class SplinterTwinTest extends CardTestPlayerBase {

    @Test
    public void testCopyCreature() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 4);
        // Enchant creature
        // Enchanted creature has "{T}: Create a token that's a copy of this creature onto the battlefield. That token has haste. Exile it at the beginning of the next end step."
        addCard(Zone.HAND, playerA, "Splinter Twin"); // {2}{R}{R}
        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion");

        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 1);
        addCard(Zone.HAND, playerB, "Lightning Bolt");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Splinter Twin", "Silvercoat Lion");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, 1);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Create a token");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Lightning Bolt", "Silvercoat Lion", "{T}: Create a token");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertHandCount(playerB, "Lightning Bolt", 0);
        assertGraveyardCount(playerB, "Lightning Bolt", 1);

        assertGraveyardCount(playerA, "Silvercoat Lion", 1);
        assertGraveyardCount(playerA, "Splinter Twin", 1);

        assertPermanentCount(playerA, "Silvercoat Lion", 1);
    }
}
