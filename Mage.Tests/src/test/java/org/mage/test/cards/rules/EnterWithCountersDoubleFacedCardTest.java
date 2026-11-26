package org.mage.test.cards.rules;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.player.TestPlayer;
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

    @Test
    public void test_DiesAndReturnsTransformedWithCounters_OjerPakpatiq() {
        // When Ojer Pakpatiq dies, return it to the battlefield tapped and transformed under its owner's control with three time counters on it.
        addCard(Zone.BATTLEFIELD, playerA, "Ojer Pakpatiq, Deepest Epoch"); // 4/3, transforming double-faced
        //
        addCustomEffect_TargetDestroy(playerA);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "target destroy", "Ojer Pakpatiq, Deepest Epoch");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Temple of Cyclical Time", 1);
        assertTapped("Temple of Cyclical Time", true);
        assertCounterCount(playerA, "Temple of Cyclical Time", CounterType.TIME, 3);
    }

    @Test
    public void test_FlashbackAndReturnsTransformedWithCounter_EsperOrigins() {
        skipInitShuffling();
        // Surveil 2. You gain 2 life. If this spell was cast from a graveyard, exile it, then put it onto the battlefield
        // transformed under its owner's control with a finality counter on it.
        // Flashback {3}{G}
        addCard(Zone.GRAVEYARD, playerA, "Esper Origins"); // Flashback {3}{G}
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 4);
        //
        addCard(Zone.LIBRARY, playerA, "Grizzly Bears"); // top of library for Surveil 2 and Chapter I

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Flashback");
        addTarget(playerA, TestPlayer.TARGET_SKIP); // Surveil 2: keep both cards on top
        setChoice(playerA, "Grizzly Bears"); // order top of library

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Summon: Esper Maduin", 1);
        assertCounterCount(playerA, "Summon: Esper Maduin", CounterType.FINALITY, 1);
        assertCounterCount(playerA, "Summon: Esper Maduin", CounterType.LORE, 1);
    }

    @Test
    public void test_ModalBackFaceDiesAndReturnsFrontFaceWithCounter_FeignDeath() {
        // Until end of turn, target creature gains "When this creature dies, return it to the battlefield tapped
        // under its owner's control with a +1/+1 counter on it."
        addCard(Zone.HAND, playerA, "Feign Death"); // {B}
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 1);
        //
        // Modal double-faced: Plargg, Dean of Chaos ({1}{R} 2/2) // Augusta, Dean of Order ({2}{W} 1/3)
        addCard(Zone.HAND, playerA, "Plargg, Dean of Chaos"); // cast back face {2}{W}
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 3);
        addCustomEffect_TargetDestroy(playerA);

        // Cast back face Augusta, Dean of Order
        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {W}", 3);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Augusta, Dean of Order");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkPermanentCount("Augusta on battlefield", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Augusta, Dean of Order", 1);

        // Cast Feign Death on Augusta and destroy it; it must return front-face up with a +1/+1 counter
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Feign Death", "Augusta, Dean of Order");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "target destroy", "Augusta, Dean of Order");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Plargg, Dean of Chaos", 1);
        assertTapped("Plargg, Dean of Chaos", true);
        assertCounterCount(playerA, "Plargg, Dean of Chaos", CounterType.P1P1, 1);
    }

    @Test
    public void test_ModalBackFaceDiesAndReturnsFrontFaceWithCounter_LuminousBroodmoth() {
        // Whenever a creature you control without flying dies, return it to the battlefield under its owner's control with a flying counter on it.
        addCard(Zone.BATTLEFIELD, playerA, "Luminous Broodmoth"); // 3/4 flying
        //
        // Modal double-faced: Plargg, Dean of Chaos ({1}{R} 2/2) // Augusta, Dean of Order ({2}{W} 1/3, no flying)
        addCard(Zone.HAND, playerA, "Plargg, Dean of Chaos"); // cast back face {2}{W}
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 3);
        addCustomEffect_TargetDestroy(playerA);

        // Cast back face Augusta, Dean of Order
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Augusta, Dean of Order");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkPermanentCount("Augusta on battlefield", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Augusta, Dean of Order", 1);

        // Destroy Augusta; Luminous Broodmoth must return it front-face up with a flying counter
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "target destroy", "Augusta, Dean of Order");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Plargg, Dean of Chaos", 1);
        assertCounterCount(playerA, "Plargg, Dean of Chaos", CounterType.FLYING, 1);
    }

    @Test
    public void test_ModalBackFaceDiesAndReturnsFrontFaceWithCounter_UnholyIndenture() {
        // Enchant creature. When enchanted creature dies, return that card to the battlefield under your control with a +1/+1 counter on it.
        addCard(Zone.HAND, playerA, "Unholy Indenture"); // {2}{B}
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 3);
        //
        // Modal double-faced: Plargg, Dean of Chaos ({1}{R} 2/2) // Augusta, Dean of Order ({2}{W} 1/3)
        addCard(Zone.HAND, playerA, "Plargg, Dean of Chaos"); // cast back face {2}{W}
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 3);
        addCustomEffect_TargetDestroy(playerA);

        // Cast back face Augusta, Dean of Order
        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {W}", 3);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Augusta, Dean of Order");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkPermanentCount("Augusta on battlefield", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Augusta, Dean of Order", 1);

        // Enchant Augusta with Unholy Indenture and destroy it
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Unholy Indenture", "Augusta, Dean of Order");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "target destroy", "Augusta, Dean of Order");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Plargg, Dean of Chaos", 1);
        assertCounterCount(playerA, "Plargg, Dean of Chaos", CounterType.P1P1, 1);
    }

    @Test
    public void test_ReturnModalCardFromGraveyardWithCounter_UnbreakableBond() {
        // Return target creature card from your graveyard to the battlefield with a lifelink counter on it.
        addCard(Zone.HAND, playerA, "Unbreakable Bond"); // {4}{B}
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 5);
        //
        // Modal double-faced: Plargg, Dean of Chaos ({1}{R} 2/2) // Augusta, Dean of Order ({2}{W} 1/3)
        addCard(Zone.GRAVEYARD, playerA, "Plargg, Dean of Chaos");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Unbreakable Bond", "Plargg, Dean of Chaos");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Plargg, Dean of Chaos", 1);
        assertCounterCount(playerA, "Plargg, Dean of Chaos", CounterType.LIFELINK, 1);
    }
}
