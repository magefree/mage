
package org.mage.test.cards.rules;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */

public class MeliraSylvokOutcastTest extends CardTestPlayerBase {
    /**
     * with Melira, Sylvok Outcast on the table and Devoted Druid you can activated
     * his untap ability for infinity mana. This shouldn't work like this as its an
     * unpayable cost " 601.2g. The player pays the total cost in any order. Partial
     * payments are not allowed. Unpayable costs cant be paid"
     */
    @Test
    public void testUnpayableCost() {
        // You can't get poison counters.
        // Creatures you control can't have -1/-1 counters placed on them.
        // Creatures your opponents control lose infect.
        addCard(Zone.BATTLEFIELD, playerA, "Melira, Sylvok Outcast", 2); // 2/2
        // {T}: Add {G}.
        // Put a -1/-1 counter on Devoted Druid: Untap Devoted Druid.
        addCard(Zone.BATTLEFIELD, playerA, "Devoted Druid", 1); // 0/2

        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {G}");
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Put a -1/-1 counter on");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);

        // TODO: Needed since Melira's ability isn't been caught by the is playable check
        try {
            execute();
            Assert.fail("must throw exception on execute");
        } catch (Throwable e) {
            if (!e.getMessage().contains("Put a -1/-1 counter on")) {
                Assert.fail("Needed error about not being able to use the Devoted Druid's -1/-1 ability, but got:\n" + e.getMessage());
            }
        }

        assertPowerToughness(playerA, "Devoted Druid", 0, 2);
        assertCounterCount("Devoted Druid", CounterType.M1M1, 0);
        assertTapped("Devoted Druid", true); // Because untapping can't be paid
    }
}
