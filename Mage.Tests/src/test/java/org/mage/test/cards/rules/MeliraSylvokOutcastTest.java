package org.mage.test.cards.rules;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
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
        addCard(Zone.BATTLEFIELD, playerA, "Melira, Sylvok Outcast"); // 2/2
        // {T}: Add {G}.
        // Put a -1/-1 counter on Devoted Druid: Untap Devoted Druid.
        addCard(Zone.BATTLEFIELD, playerA, "Devoted Druid", 1); // 0/2

        checkPlayableAbility("can't put counters", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {G}", false);
    }

    @Test
    public void testBlight() {
        addCard(Zone.BATTLEFIELD, playerA, "Gristle Glutton");
        addCard(Zone.BATTLEFIELD, playerA, "Melira, Sylvok Outcast");

        checkPlayableAbility("can't put counters", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T},", false);
    }
}
