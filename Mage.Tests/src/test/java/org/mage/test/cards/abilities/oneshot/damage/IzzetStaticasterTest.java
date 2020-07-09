
package org.mage.test.cards.abilities.oneshot.damage;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class IzzetStaticasterTest extends CardTestPlayerBase {

    /**
     * Izzet Staticaster killed 2 Birds of Paradise under my opponent control
     * but it didn't kill my own Birds of Paradise. I remember this worked good
     * on XMage but this time it just did not. Is it possibile because the 3
     * Birds come from different sets? (my opponent had those Ravnica, while
     * mine was from M12).
     */
    @Test
    public void testAllReceiveDamage() {
        // Flash (You may cast this spell any time you could cast an instant.)
        // Haste
        // {T}: Izzet Staticaster deals 1 damage to target creature and each other creature with the same name as that creature.
        addCard(Zone.BATTLEFIELD, playerA, "Izzet Staticaster");
        addCard(Zone.BATTLEFIELD, playerA, "Birds of Paradise", 2);
        addCard(Zone.BATTLEFIELD, playerB, "Birds of Paradise", 2);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: {this} deals", "Birds of Paradise");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Birds of Paradise", 2);
        assertGraveyardCount(playerB, "Birds of Paradise", 2);

    }
}
