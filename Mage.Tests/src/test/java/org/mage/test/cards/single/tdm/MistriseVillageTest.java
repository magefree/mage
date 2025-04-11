package org.mage.test.cards.single.tdm;


import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class MistriseVillageTest extends CardTestPlayerBase {

    private static final String MISTRISE = "Mistrise Village";
    private static final String COUNTER = "Counterspell";
    private static final String CUB = "Bear Cub";
    private static final String BEARS = "Balduvian Bears";

    @Test
    public void testCounter() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, MISTRISE);
        addCard(Zone.BATTLEFIELD, playerA, "Island");
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 4);
        addCard(Zone.BATTLEFIELD, playerB, "Island", 4);
        addCard(Zone.HAND, playerB, COUNTER, 2);
        addCard(Zone.HAND, playerA, CUB);
        addCard(Zone.HAND, playerA, BEARS);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{U}, {T}");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, playerA);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, CUB, true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, COUNTER, CUB, CUB);
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, BEARS, true);
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerB, COUNTER, BEARS, BEARS);

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, CUB, 1);
        assertGraveyardCount(playerA, BEARS, 1);
        assertGraveyardCount(playerB, COUNTER, 2);
    }
}