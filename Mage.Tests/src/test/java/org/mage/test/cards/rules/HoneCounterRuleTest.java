package org.mage.test.cards.rules;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class HoneCounterRuleTest extends CardTestPlayerBase {

    private static final String LION = "Silvercoat Lion";
    private static final String SHIELD = "Accorder's Shield";

    @Test
    public void testHoneCountersBoostEquippedCreature() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 4);
        addCard(Zone.BATTLEFIELD, playerA, SHIELD);
        addCard(Zone.BATTLEFIELD, playerA, LION);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Equip {3}", LION);
        addCounters(1, PhaseStep.PRECOMBAT_MAIN, playerA, SHIELD, CounterType.HONE, 2);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        // 2/2 plus +0/+3 from Accorder's Shield plus +2/+0 from two hone counters.
        assertPowerToughness(playerA, LION, 4, 5);
    }

    @Test
    public void testHoneCountersDoNothingIfNotEquipped() {
        addCard(Zone.BATTLEFIELD, playerA, SHIELD);
        addCard(Zone.BATTLEFIELD, playerA, LION);

        addCounters(1, PhaseStep.PRECOMBAT_MAIN, playerA, SHIELD, CounterType.HONE, 3);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPowerToughness(playerA, LION, 2, 2);
    }
}
