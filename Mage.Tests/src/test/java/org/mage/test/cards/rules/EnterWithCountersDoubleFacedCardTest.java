package org.mage.test.cards.rules;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * Counters from game.setEnterWithCounters must be put on a double-faced card too: they are set by the card id,
 * but a double-faced card enters as a side object with another id
 *
 * @author JayDi85
 */
public class EnterWithCountersDoubleFacedCardTest extends CardTestPlayerBase {

    private static final String kytheon = "Kytheon, Hero of Akros"; // {W}, 2/1, transforming double-faced

    @Test
    public void test_ReturnFromGraveyardWithCounter_GracefulRestoration() {
        // Return target creature card from your graveyard to the battlefield with an additional +1/+1 counter on it.
        addCard(Zone.HAND, playerA, "Graceful Restoration"); // {3}{W}{B}
        addCard(Zone.BATTLEFIELD, playerA, "Scrubland", 5);
        addCard(Zone.GRAVEYARD, playerA, kytheon);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Graceful Restoration");
        setModeChoice(playerA, "1");
        addTarget(playerA, kytheon);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertCounterCount(playerA, kytheon, CounterType.P1P1, 1);
    }

    @Test
    public void test_ExileThenReturnWithCounter_PlanarIncision() {
        // Exile target artifact or creature, then return it to the battlefield under its owner's control with
        // a +1/+1 counter on it.
        addCard(Zone.HAND, playerA, "Planar Incision"); // {1}{U}
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);
        addCard(Zone.BATTLEFIELD, playerA, kytheon);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Planar Incision", kytheon);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertCounterCount(playerA, kytheon, CounterType.P1P1, 1);
    }

    @Test
    public void test_DiesAndReturnWithCounter_FeignDeath() {
        // Until end of turn, target creature gains "When this creature dies, return it to the battlefield tapped
        // under its owner's control with a +1/+1 counter on it."
        addCard(Zone.HAND, playerA, "Feign Death"); // {B}
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 1);
        addCard(Zone.HAND, playerA, "Lightning Bolt"); // {R}
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);
        addCard(Zone.BATTLEFIELD, playerA, kytheon);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Feign Death", kytheon);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", kytheon);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertCounterCount(playerA, kytheon, CounterType.P1P1, 1);
        assertTapped(kytheon, true);
    }

    @Test
    public void test_CustomEffect_GhostVacuum() {
        // {T}: Exile target card from a graveyard.
        // {6}, {T}, Sacrifice this artifact: Put each creature card exiled with this artifact onto the battlefield
        // under your control with a flying counter on it. Each of them is a 1/1 Spirit in addition to its other
        // types. Activate only as a sorcery.
        addCard(Zone.BATTLEFIELD, playerA, "Ghost Vacuum");
        addCard(Zone.BATTLEFIELD, playerA, "Wastes", 6);
        addCard(Zone.GRAVEYARD, playerB, kytheon);

        // exile
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Exile", kytheon);

        // return
        activateAbility(3, PhaseStep.PRECOMBAT_MAIN, playerA, "{6}, {T}, Sacrifice");

        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.END_TURN);
        execute();

        assertCounterCount(playerA, kytheon, CounterType.FLYING, 1);
    }
}
