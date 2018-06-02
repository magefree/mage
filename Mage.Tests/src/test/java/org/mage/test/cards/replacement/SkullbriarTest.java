
package org.mage.test.cards.replacement;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author Dilnu
 */
public class SkullbriarTest extends CardTestPlayerBase {

    /**
     * Skullbriar retains counters even when Humility is on the field.
     */
    @Test
    public void testSkullbriarCloudshift() {
        // Counters remain on Skullbriar as it moves to any zone other than a player's hand or library.
        addCard(Zone.BATTLEFIELD, playerB, "Skullbriar, the Walking Grave");
        addCard(Zone.BATTLEFIELD, playerB, "Plains");
        addCard(Zone.BATTLEFIELD, playerB, "Forest");

        // Put a +1/+1 counter on target creature.
        addCard(Zone.HAND, playerB, "Battlegrowth");
        // Exile target creature you control, then return that card to the battlefield under your control.
        addCard(Zone.HAND, playerB, "Cloudshift");

        castSpell(1, PhaseStep.UPKEEP, playerB, "Battlegrowth", "Skullbriar, the Walking Grave");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Cloudshift", "Skullbriar, the Walking Grave");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertCounterCount("Skullbriar, the Walking Grave", CounterType.P1P1, 1);
    }

    /**
     * Skullbriar should not retain counters when Humility is on the field.
     */
    @Test
    public void testHumilityAndSkullbriarCloudshift() {
        // Counters remain on Skullbriar as it moves to any zone other than a player's hand or library.
        addCard(Zone.BATTLEFIELD, playerB, "Skullbriar, the Walking Grave");

        // Enchantment  {2}{W}{W}
        // All creatures lose all abilities and are 1/1.
        addCard(Zone.BATTLEFIELD, playerA, "Humility");
        addCard(Zone.BATTLEFIELD, playerB, "Plains");
        addCard(Zone.BATTLEFIELD, playerB, "Forest");

        // Put a +1/+1 counter on target creature.
        addCard(Zone.HAND, playerB, "Battlegrowth");
        // Exile target creature you control, then return that card to the battlefield under your control.
        addCard(Zone.HAND, playerB, "Cloudshift");

        castSpell(1, PhaseStep.UPKEEP, playerB, "Battlegrowth", "Skullbriar, the Walking Grave");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Cloudshift", "Skullbriar, the Walking Grave");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertCounterCount("Skullbriar, the Walking Grave", CounterType.P1P1, 0);
    }
}
