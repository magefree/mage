package org.mage.test.cards.single.yotj;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;

import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class VonaDeIedoTheAntifexTest extends CardTestPlayerBase {

    @Test
    public void testDuplicateUsesCopiedPermanentCharacteristics() {
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears");
        addCard(Zone.BATTLEFIELD, playerA, "Scrubland", 7);
        addCard(Zone.BATTLEFIELD, playerB, "Island", 4);
        addCard(Zone.HAND, playerB, "Clone");
        addCard(Zone.HAND, playerA, "Vona de Iedo, the Antifex");
        addCard(Zone.HAND, playerA, "Forest");
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Clone");
        setChoice(playerB, true);
        setChoice(playerB, "Grizzly Bears");
        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerA, "Vona de Iedo, the Antifex");
        addTarget(playerA, "Grizzly Bears[only copy]");
        setChoice(playerA, true);
        setChoice(playerA, "Forest");
        castSpell(3, PhaseStep.POSTCOMBAT_MAIN, playerA, "Grizzly Bears");

        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerB, "Clone", 1);
        assertPermanentCount(playerA, "Grizzly Bears", 2);
        assertHandCount(playerA, "Clone", 0);
    }

    @Test
    public void testDuplicateGetsPrismaticAndNotCounters() {
        addCard(Zone.BATTLEFIELD, playerA, "Scrubland", 7);
        addCard(Zone.BATTLEFIELD, playerB, "Grizzly Bears");
        addCounters(1, PhaseStep.UPKEEP, playerB, "Grizzly Bears", CounterType.P1P1, 2);
        addCard(Zone.HAND, playerA, "Vona de Iedo, the Antifex");
        addCard(Zone.HAND, playerA, "Forest");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Vona de Iedo, the Antifex");
        addTarget(playerA, "Grizzly Bears");
        setChoice(playerA, true);
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Grizzly Bears");
        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertGraveyardCount(playerB, "Grizzly Bears", 1);
        assertGraveyardCount(playerA, "Forest", 1);
        assertPowerToughness(playerA, "Grizzly Bears", 2, 2);
    }

    @Test
    public void testDeclineStillDestroys() {
        addCard(Zone.BATTLEFIELD, playerA, "Scrubland", 5);
        addCard(Zone.BATTLEFIELD, playerB, "Grizzly Bears");
        addCard(Zone.HAND, playerA, "Vona de Iedo, the Antifex");
        addCard(Zone.HAND, playerA, "Forest");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Vona de Iedo, the Antifex");
        addTarget(playerA, "Grizzly Bears");
        setChoice(playerA, false);
        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertGraveyardCount(playerB, "Grizzly Bears", 1);
        assertHandCount(playerA, "Forest", 1);
        assertHandCount(playerA, "Grizzly Bears", 0);
    }

}
