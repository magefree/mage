package org.mage.test.cards.single.yotj;

import mage.constants.PhaseStep;
import mage.constants.Zone;

import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class JetCollectorTest extends CardTestPlayerBase {

    @Test
    public void testConjureOnlyOnceAcrossTurns() {
        addCard(Zone.BATTLEFIELD, playerA, "Jet Collector");
        addCard(Zone.GRAVEYARD, playerA, "Island", 4);
        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertHandCount(playerA, "Mox Jet", 1);
    }

    @Test
    public void testThresholdRecheckedBeforeResolution() {
        addCard(Zone.BATTLEFIELD, playerA, "Jet Collector");
        addCard(Zone.GRAVEYARD, playerA, "Island", 4);
        addCard(Zone.BATTLEFIELD, playerB, "Tormod's Crypt");
        activateAbility(1, PhaseStep.POSTCOMBAT_MAIN, playerB, "{T}, Sacrifice", playerA);
        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertHandCount(playerA, "Mox Jet", 0);
        assertGraveyardCount(playerA, 0);
    }

    @Test
    public void testReanimateExactXWithFinality() {
        addCard(Zone.BATTLEFIELD, playerA, "Jet Collector");
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 3);
        addCard(Zone.GRAVEYARD, playerA, "Grizzly Bears");
        addCard(Zone.GRAVEYARD, playerA, "Hill Giant");
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{X}{B}: Return");
        addTarget(playerA, "Grizzly Bears");
        setChoice(playerA, "X=2");
        addCard(Zone.BATTLEFIELD, playerB, "Swamp", 2);
        addCard(Zone.HAND, playerB, "Doom Blade");
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerB, "Doom Blade", "Grizzly Bears");
        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertExileCount(playerA, "Grizzly Bears", 1);
        assertGraveyardCount(playerA, "Hill Giant", 1);
    }

}
