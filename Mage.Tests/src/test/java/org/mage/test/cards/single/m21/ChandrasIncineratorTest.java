package org.mage.test.cards.single.m21;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class ChandrasIncineratorTest extends CardTestPlayerBase {

    @Test
    public void testCostReduction(){
        // {5}{R}
        // This spell costs {X} less to cast,
        // where X is the total amount of noncombat damage dealt to your opponents this turn.
        addCard(Zone.HAND, playerA, "Chandra's Incinerator");

        // deal 3 damage to any target
        addCard(Zone.HAND, playerA, "Lightning Bolt");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 6);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", playerB);
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Chandra's Incinerator");
        setStopAt(1, PhaseStep.END_TURN);
        setStrictChooseMode(true);
        execute();

        // {R} lightning bolt + {2}{R} Chandra's Incinerator
        assertTappedCount("Mountain", true, 4);
    }
}
