
package org.mage.test.turnmod;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class SkipTurnTest extends CardTestPlayerBase {

    @Test
    public void testEaterOfDays() {
        // At the beginning of your upkeep or whenever you cast a green spell, put a charge counter on Shrine of Boundless Growth.
        // {T}, Sacrifice Shrine of Boundless Growth: Add {C} for each charge counter on Shrine of Boundless Growth.
        addCard(Zone.HAND, playerA, "Shrine of Boundless Growth", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 7);
        // Flying
        // Trample
        // When Eater of Days enters the battlefield, you skip your next two turns.
        addCard(Zone.HAND, playerA, "Eater of Days", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Eater of Days", true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Shrine of Boundless Growth");

        setStopAt(5, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, "Eater of Days", 1);
        assertPermanentCount(playerA, "Shrine of Boundless Growth", 1);
        assertCounterCount("Shrine of Boundless Growth", CounterType.CHARGE, 1);
    }
}
