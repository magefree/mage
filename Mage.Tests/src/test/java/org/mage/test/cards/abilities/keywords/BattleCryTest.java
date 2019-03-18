

package org.mage.test.cards.abilities.keywords;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.filter.Filter;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * 702.90. Battle Cry
 *
 *  702.90a Battle cry is a triggered ability. "Battle cry" means "Whenever this creature attacks, each other attacking creature gets +1/+0 until end of turn."
 *  702.90b If a creature has multiple instances of battle cry, each triggers separately.
 *
 * @author LevelX2
 */

public class BattleCryTest extends CardTestPlayerBase {

    /**
     * Tests boost last until end of turn
     */
    @Test
    public void testBoostDurationUntilEndTurn() {
        // Signal Pest {1}
        // Artifact Creature - Pest   0/1
        // Battle cry (Whenever this creature attacks, each other attacking creature gets +1/+0 until end of turn.)
        // Signal Pest can't be blocked except by creatures with flying or reach.
        addCard(Zone.BATTLEFIELD, playerB, "Signal Pest", 3);

        attack(2, playerB, "Signal Pest");
        attack(2, playerB, "Signal Pest");
        attack(2, playerB, "Signal Pest");

        setStopAt(2, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPermanentCount(playerB, "Signal Pest", 3);
        assertPowerToughness(playerB, "Signal Pest", 2,1, Filter.ComparisonScope.All); // two other Signal Pest atack, so it get +2 power
    }

    /**
     * Tests boost last until end of turn
     */
    @Test
    public void testBoostDurationNotNextTurn() {
        // Signal Pest {1}
        // Artifact Creature - Pest   0/1
        // Battle cry (Whenever this creature attacks, each other attacking creature gets +1/+0 until end of turn.)
        // Signal Pest can't be blocked except by creatures with flying or reach.
        addCard(Zone.BATTLEFIELD, playerB, "Signal Pest", 3);

        attack(2, playerB, "Signal Pest");
        attack(2, playerB, "Signal Pest");
        attack(2, playerB, "Signal Pest");

        setStopAt(3, PhaseStep.UPKEEP);
        execute();

        assertPermanentCount(playerB, "Signal Pest", 3);
        assertPowerToughness(playerB, "Signal Pest", 0,1); 
    }

}
