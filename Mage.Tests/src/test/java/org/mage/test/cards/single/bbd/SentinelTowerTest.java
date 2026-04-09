package org.mage.test.cards.single.bbd;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class SentinelTowerTest extends CardTestPlayerBase {

    @Test
    public void testBasic() {
        addCard(Zone.BATTLEFIELD, playerB, "Memnite");
        addCard(Zone.BATTLEFIELD, playerB, "Balduvian Bears");
        addCard(Zone.BATTLEFIELD, playerA, "Sentinel Tower");
        addCard(Zone.HAND, playerA, "Reach Through Mists", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 3);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Reach Through Mists");
        addTarget(playerA, "Memnite");

        castSpell(1, PhaseStep.BEGIN_COMBAT, playerA, "Reach Through Mists");
        addTarget(playerA, "Balduvian Bears");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerB, "Memnite", 1);
        assertGraveyardCount(playerB, "Balduvian Bears", 1);
    }

}
