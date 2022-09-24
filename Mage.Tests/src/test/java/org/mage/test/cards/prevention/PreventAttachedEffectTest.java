
package org.mage.test.cards.prevention;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class PreventAttachedEffectTest extends CardTestPlayerBase {

    /**
     * Kaervek the Merciless still deals damage with the triggered ability when
     * enchanted with Temporal Isolation.
     */
    @Test
    public void testDamageToPlayerPrevented() {
        // Whenever an opponent casts a spell, Kaervek the Merciless deals damage to any target equal to that spell's converted mana cost.
        addCard(Zone.BATTLEFIELD, playerA, "Kaervek the Merciless");
        // Flash
        // Enchant creature
        // Enchanted creature has shadow.
        // Prevent all damage that would be dealt by enchanted creature.
        addCard(Zone.HAND, playerA, "Temporal Isolation", 1); // {1}{W}
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);

        addCard(Zone.HAND, playerB, "Silvercoat Lion", 1); // {1}{W}
        addCard(Zone.HAND, playerB, "Pillarfield Ox", 1); // {3}{W}
        addCard(Zone.BATTLEFIELD, playerB, "Plains", 6);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Temporal Isolation", "Kaervek the Merciless");

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Silvercoat Lion", true);
        addTarget(playerA, playerB);
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Pillarfield Ox");
        addTarget(playerA, "Silvercoat Lion");

        setStopAt(2, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Kaervek the Merciless", 1);
        assertPermanentCount(playerA, "Temporal Isolation", 1);

        assertPermanentCount(playerB, "Silvercoat Lion", 1);
        assertPermanentCount(playerB, "Pillarfield Ox", 1);

        assertLife(playerA, 20);
        assertLife(playerB, 20);
    }

}
