package org.mage.test.cards.single.eoe;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;

import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author JayDi85
 */
public class SyrVondamSunstarExemplarTest extends CardTestPlayerBase {

    @Test
    public void test_Trigger_AnotherCreature() {
        // Whenever another creature you control dies or is put into exile, put a +1/+1 counter on Syr Vondam and you gain 1 life.
        addCard(Zone.BATTLEFIELD, playerA, "Syr Vondam, Sunstar Exemplar");
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears", 3);

        addCustomEffect_TargetDestroy(playerA);
        addCustomEffect_TargetExile(playerA);
        addCustomEffect_TargetBlink(playerA);

        // die
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "target destroy", "Grizzly Bears");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkLife("after die", 1, PhaseStep.PRECOMBAT_MAIN, playerA, 21);
        checkPermanentCounters("after die", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Syr Vondam, Sunstar Exemplar", CounterType.P1P1, 1);

        // exile
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "target exile", "Grizzly Bears");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkLife("after exile", 1, PhaseStep.PRECOMBAT_MAIN, playerA, 22);
        checkPermanentCounters("after exile", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Syr Vondam, Sunstar Exemplar", CounterType.P1P1, 2);

        // blink
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "target blink", "Grizzly Bears");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkLife("after blink", 1, PhaseStep.PRECOMBAT_MAIN, playerA, 23);
        checkPermanentCounters("after blink", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Syr Vondam, Sunstar Exemplar", CounterType.P1P1, 3);

        setStopAt(1, PhaseStep.END_TURN);
        setStrictChooseMode(true);
        execute();
    }

    @Test
    public void test_Trigger_Self_Die() {
        // When Syr Vondam dies or is put into exile while its power is 4 or greater, destroy up to one target nonland permanent.
        addCard(Zone.BATTLEFIELD, playerA, "Syr Vondam, Sunstar Exemplar");
        addCounters(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Syr Vondam, Sunstar Exemplar", CounterType.P1P1, 2);
        addCard(Zone.BATTLEFIELD, playerB, "Grizzly Bears", 1);

        addCustomEffect_TargetDestroy(playerA);

        // die
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "target destroy", "Syr Vondam, Sunstar Exemplar");
        addTarget(playerA, "Grizzly Bears");

        setStopAt(1, PhaseStep.END_TURN);
        setStrictChooseMode(true);
        execute();

        assertPermanentCount(playerB, "Grizzly Bears", 0);
    }

    @Test
    public void test_Trigger_Self_Exile() {
        // When Syr Vondam dies or is put into exile while its power is 4 or greater, destroy up to one target nonland permanent.
        addCard(Zone.BATTLEFIELD, playerA, "Syr Vondam, Sunstar Exemplar");
        addCounters(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Syr Vondam, Sunstar Exemplar", CounterType.P1P1, 2);
        addCard(Zone.BATTLEFIELD, playerB, "Grizzly Bears", 1);

        addCustomEffect_TargetExile(playerA);

        // exile
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "target exile", "Syr Vondam, Sunstar Exemplar");
        addTarget(playerA, "Grizzly Bears");

        setStopAt(1, PhaseStep.END_TURN);
        setStrictChooseMode(true);
        execute();

        assertPermanentCount(playerB, "Grizzly Bears", 0);
    }

    @Test
    public void test_Trigger_Self_Blink() {
        // When Syr Vondam dies or is put into exile while its power is 4 or greater, destroy up to one target nonland permanent.
        addCard(Zone.BATTLEFIELD, playerA, "Syr Vondam, Sunstar Exemplar");
        addCounters(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Syr Vondam, Sunstar Exemplar", CounterType.P1P1, 2);
        addCard(Zone.BATTLEFIELD, playerB, "Grizzly Bears", 1);

        addCustomEffect_TargetBlink(playerA);

        // blink
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "target blink", "Syr Vondam, Sunstar Exemplar");
        addTarget(playerA, "Grizzly Bears");

        setStopAt(1, PhaseStep.END_TURN);
        setStrictChooseMode(true);
        execute();

        assertPermanentCount(playerB, "Grizzly Bears", 0);
    }
}
