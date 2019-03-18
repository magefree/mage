
package org.mage.test.cards.abilities.oneshot.destroy;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class NaturesClaimTest extends CardTestPlayerBase {

    @Test
    public void testTargetDestroyable() {
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 1);
        // Destroy target artifact or enchantment. Its controller gains 4 life.
        addCard(Zone.HAND, playerA, "Nature's Claim");

        // Flying
        addCard(Zone.BATTLEFIELD, playerA, "Gold-Forged Sentinel"); // 4/4

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Nature's Claim", "Gold-Forged Sentinel");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Nature's Claim", 1);

        assertGraveyardCount(playerA, "Gold-Forged Sentinel", 1);
        assertLife(playerA, 24);
        assertLife(playerB, 20);
    }

    @Test
    public void testTargetUndestroyable() {
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 1);
        // Destroy target artifact or enchantment. Its controller gains 4 life.
        addCard(Zone.HAND, playerA, "Nature's Claim");

        // Flying
        // Darksteel Gargoyle is indestructible. ("Destroy" effects and lethal damage don't destroy it.)
        addCard(Zone.BATTLEFIELD, playerA, "Darksteel Gargoyle");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Nature's Claim", "Darksteel Gargoyle");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Nature's Claim", 1);

        assertPermanentCount(playerA, "Darksteel Gargoyle", 1);
        assertLife(playerA, 24);
        assertLife(playerB, 20);
    }
}
