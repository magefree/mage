package org.mage.test.cards.triggers.dies;

import mage.abilities.keyword.HasteAbility;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author JayDi85
 */
public class SilumgarScavengerTest extends CardTestPlayerBase {

    @Test
    public void test_BoostOnDies() {
        // Exploit (When this creature enters the battlefield, you may sacrifice a creature.)
        // Whenever another creature you control dies, put a +1/+1 counter on Silumgar Scavenger. It gains haste until end of turn if it exploited that creature.
        addCard(Zone.HAND, playerA, "Silumgar Scavenger", 1); // {4}{B}
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 5);
        //
        addCard(Zone.BATTLEFIELD, playerA, "Balduvian Bears", 1);

        // cast and exploit
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Silumgar Scavenger");
        setChoice(playerA, true); // yes, exploit
        addTarget(playerA, "Balduvian Bears");

        checkPermanentCounters("boost", 1, PhaseStep.BEGIN_COMBAT, playerA, "Silumgar Scavenger", CounterType.P1P1, 1);
        checkAbility("boost", 1, PhaseStep.BEGIN_COMBAT, playerA, "Silumgar Scavenger", HasteAbility.class, true);

        setStopAt(1, PhaseStep.END_TURN);
        setStrictChooseMode(true);
        execute();
    }
}
