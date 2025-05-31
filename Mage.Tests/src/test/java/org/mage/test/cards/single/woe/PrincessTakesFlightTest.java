package org.mage.test.cards.single.woe;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author notgreat
 */
public class PrincessTakesFlightTest extends CardTestPlayerBase {
    private static final String saga = "The Princess Takes Flight";
    private static final String bear = "Grizzly Bears";
    private static final String memnite = "Memnite";
    private static final String flicker = "Flicker of Fate";

    @Test
    public void testSimple() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 3);
        addCard(Zone.HAND, playerA, saga);
        addCard(Zone.BATTLEFIELD, playerA, bear);
        addCard(Zone.BATTLEFIELD, playerA, memnite);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, saga);
        addTarget(playerA, bear);

        checkPermanentCount("Bear gone", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, bear, 0);

        addTarget(playerA, memnite);
        checkExileCount("Bear still exiled", 3, PhaseStep.POSTCOMBAT_MAIN, playerA, bear, 1);
        checkPT("Memnite big", 3, PhaseStep.POSTCOMBAT_MAIN, playerA, memnite, 3, 3);

        setStrictChooseMode(true);
        setStopAt(5, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, memnite, 1);
        assertPowerToughness(playerA, memnite, 1, 1);
        assertPermanentCount(playerA, bear, 1);
        assertGraveyardCount(playerA, saga, 1);
    }
    @Test
    public void testFlicker() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 5);
        addCard(Zone.HAND, playerA, saga);
        addCard(Zone.HAND, playerA, flicker);
        addCard(Zone.BATTLEFIELD, playerA, bear);
        addCard(Zone.BATTLEFIELD, playerA, memnite);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, saga);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, 1); //Saga resolves, Bear exile on stack
        addTarget(playerA, bear);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, flicker, saga);
        addTarget(playerA, memnite);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, 2); //Flicker resolves, Memnite exile resolves
        checkExileCount("Memnite exiled first", 1, PhaseStep.PRECOMBAT_MAIN, playerA, memnite, 1);
        checkExileCount("Bear not yet exiled", 1, PhaseStep.PRECOMBAT_MAIN, playerA, bear, 0);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN); //Bear exile resolves
        checkExileCount("Bear exiled", 1, PhaseStep.PRECOMBAT_MAIN, playerA, bear, 1);

        setStrictChooseMode(true);
        setStopAt(5, PhaseStep.END_TURN);
        execute();

        assertExileCount(playerA, bear, 1); //Bear stays exiled
        assertPermanentCount(playerA, memnite, 1);
        assertGraveyardCount(playerA, saga, 1);
    }
}
