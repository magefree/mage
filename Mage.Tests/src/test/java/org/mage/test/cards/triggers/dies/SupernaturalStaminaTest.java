
package org.mage.test.cards.triggers.dies;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class SupernaturalStaminaTest extends CardTestPlayerBase {

    /**
     * Channeler Initiate did not get it's -1/-1 counters when reanimated with
     * Supernatural Stamina. This was a sealed game against a human player.
     */
    @Test
    public void testChanneler() {
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 2);

        // When Channeler Initiate enters the battlefield, put three -1/-1 counters on target creature you control.
        // {T}, Remove a -1/-1 counter from Channeler Initiate: Add one mana of any color.
        addCard(Zone.HAND, playerA, "Channeler Initiate"); // Creature 3/4 {1}{G}

        // Until end of turn, target creature gets +2/+0 and gains "When this creature dies, return it to the battlefield tapped under its owner's control."
        addCard(Zone.HAND, playerA, "Supernatural Stamina"); // Instant {B}
        // Shock deals 2 damage to any target.
        addCard(Zone.HAND, playerA, "Shock"); // Instant {R}

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Channeler Initiate", true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Supernatural Stamina", "Channeler Initiate");
        castSpell(1, PhaseStep.BEGIN_COMBAT, playerA, "Shock", "Channeler Initiate");

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertGraveyardCount(playerA, "Supernatural Stamina", 1);
        assertGraveyardCount(playerA, "Shock", 1);

        assertPermanentCount(playerA, "Channeler Initiate", 1);
        assertPowerToughness(playerA, "Channeler Initiate", 0, 1);

    }
}
