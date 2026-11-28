package org.mage.test.cards.rules;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * Tests that when an effect fails to put a card onto the battlefield with counters,
 * those counters are not applied the next time the card enters the battlefield,
 * while other cards entering in the same batch still receive their counters.
 *
 * @author JayDi85
 */
public class EnterWithCountersCleanupTest extends CardTestPlayerBase {

    @Test
    public void test_PreventedEntryByGrafdiggersCageAndLaterCast_GracefulRestoration() {
        // Choose one — Return target creature card from your graveyard to the battlefield with an additional +1/+1 counter on it.
        addCard(Zone.HAND, playerA, "Graceful Restoration"); // {3}{W}{B}
        addCard(Zone.BATTLEFIELD, playerA, "Scrubland", 5);
        //
        // Creature cards in graveyards and libraries can't enter the battlefield.
        addCard(Zone.BATTLEFIELD, playerB, "Grafdigger's Cage");
        //
        addCard(Zone.GRAVEYARD, playerA, "Grizzly Bears"); // {1}{G}, bear 2/2
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2);
        addCustomEffect_ReturnFromAnyToHand(playerA);

        // Grafdigger's Cage prevents Graceful Restoration from returning Grizzly Bears
        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {W}", 1);
        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {B}", 4);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Graceful Restoration");
        setModeChoice(playerA, "1"); // return with +1/+1 counter
        addTarget(playerA, "Grizzly Bears"); // target creature card in graveyard
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkGraveyardCount("after prevented return, Grizzly Bears stays in graveyard", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Grizzly Bears", 1);
        checkPermanentCount("after prevented return, Grizzly Bears not on battlefield", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Grizzly Bears", 0);

        // Return Grizzly Bears to hand
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "return from graveyard", "Grizzly Bears");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkHandCardCount("after return, Grizzly Bears in hand", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Grizzly Bears", 1);
        checkGraveyardCount("after return, Grizzly Bears left graveyard", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Grizzly Bears", 0);

        // Cast Grizzly Bears normally; it must enter with no counters
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Grizzly Bears");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Grizzly Bears", 1);
        assertCounterCount(playerA, "Grizzly Bears", CounterType.P1P1, 0);
        assertPowerToughness(playerA, "Grizzly Bears", 2, 2);
    }

    @Test
    public void test_PreventedEntryByGrafdiggersCageAndLaterReanimate_GracefulRestoration() {
        // Choose one — Return target creature card from your graveyard to the battlefield with an additional +1/+1 counter on it.
        addCard(Zone.HAND, playerA, "Graceful Restoration"); // {3}{W}{B}
        addCard(Zone.BATTLEFIELD, playerA, "Scrubland", 5);
        //
        // Creature cards in graveyards and libraries can't enter the battlefield.
        addCard(Zone.BATTLEFIELD, playerB, "Grafdigger's Cage");
        //
        // Return target creature card from your graveyard to the battlefield.
        addCard(Zone.HAND, playerA, "Zombify"); // {3}{B}
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 4);
        //
        addCard(Zone.GRAVEYARD, playerA, "Grizzly Bears"); // bear 2/2
        addCustomEffect_TargetDestroy(playerA);

        // Grafdigger's Cage prevents Graceful Restoration from returning Grizzly Bears
        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {W}", 1);
        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {B}", 4);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Graceful Restoration");
        setModeChoice(playerA, "1"); // return with +1/+1 counter
        addTarget(playerA, "Grizzly Bears"); // target creature card in graveyard
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkGraveyardCount("after prevented return, Grizzly Bears stays in graveyard", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Grizzly Bears", 1);
        checkPermanentCount("after prevented return, Grizzly Bears not on battlefield", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Grizzly Bears", 0);

        // Destroy Grafdigger's Cage
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "target destroy", "Grafdigger's Cage");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkPermanentCount("after destroy, Grafdigger's Cage gone", 1, PhaseStep.PRECOMBAT_MAIN, playerA, playerB, "Grafdigger's Cage", 0);
        checkGraveyardCount("before Zombify, Grizzly Bears still in graveyard", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Grizzly Bears", 1);

        // Reanimate the same Grizzly Bears from the graveyard; it must enter with no counters
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Zombify", "Grizzly Bears");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Grizzly Bears", 1);
        assertCounterCount(playerA, "Grizzly Bears", CounterType.P1P1, 0);
        assertPowerToughness(playerA, "Grizzly Bears", 2, 2);
    }

    @Test
    public void test_PreventedEntryAndLaterReanimateOnSameStack_MakeshiftMannequin() {
        // Return target creature card from your graveyard to the battlefield with a mannequin counter on it.
        addCard(Zone.HAND, playerA, "Makeshift Mannequin"); // {3}{B}
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 4);
        //
        // Creature cards in graveyards and libraries can't enter the battlefield.
        addCard(Zone.BATTLEFIELD, playerB, "Grafdigger's Cage");
        //
        // Return target creature card from your graveyard to the battlefield.
        addCard(Zone.HAND, playerA, "Zombify"); // {3}{B}
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 4);
        //
        addCard(Zone.GRAVEYARD, playerA, "Grizzly Bears"); // bear 2/2
        addCustomEffect_TargetDestroy(playerA);

        // Stack (bottom to top): Zombify -> target destroy (Grafdigger's Cage) -> Makeshift Mannequin
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Zombify", "Grizzly Bears");
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "target destroy", "Grafdigger's Cage");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Makeshift Mannequin", "Grizzly Bears");
        checkStackSize("all 3 objects on stack", 1, PhaseStep.PRECOMBAT_MAIN, playerA, 3);

        // Top resolves first: Makeshift Mannequin is prevented by Grafdigger's Cage while 2 objects remain on stack
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, 1);
        checkStackSize("after Makeshift Mannequin, 2 objects remain on stack", 1, PhaseStep.PRECOMBAT_MAIN, playerA, 2);
        checkGraveyardCount("after prevented Makeshift Mannequin, Grizzly Bears stays in graveyard", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Grizzly Bears", 1);
        checkPermanentCount("after prevented Makeshift Mannequin, Grizzly Bears not on battlefield", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Grizzly Bears", 0);

        // Next resolves: target destroy removes Grafdigger's Cage while Zombify remains on stack
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, 1);
        checkStackSize("after destroy, Zombify remains on stack", 1, PhaseStep.PRECOMBAT_MAIN, playerA, 1);
        checkPermanentCount("after destroy, Grafdigger's Cage gone", 1, PhaseStep.PRECOMBAT_MAIN, playerA, playerB, "Grafdigger's Cage", 0);
        checkGraveyardCount("before Zombify resolves, Grizzly Bears still in graveyard", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Grizzly Bears", 1);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Grizzly Bears", 1);
        assertCounterCount(playerA, "Grizzly Bears", CounterType.MANNEQUIN, 0);
    }

    @Test
    public void test_PreventedEntryByContainmentPriestAndLaterCast_PlanarIncision() {
        // Exile target artifact or creature, then return it to the battlefield under its owner's control with a +1/+1 counter on it.
        addCard(Zone.HAND, playerA, "Planar Incision"); // {1}{U}
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);
        //
        // If a nontoken creature would enter and it wasn't cast, exile it instead.
        addCard(Zone.BATTLEFIELD, playerB, "Containment Priest");
        //
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears"); // {1}{G}, bear 2/2
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2);
        addCustomEffect_ReturnFromAnyToHand(playerA);

        // Containment Priest exiles Grizzly Bears instead of letting Planar Incision return it
        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {U}", 2);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Planar Incision", "Grizzly Bears");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkExileCount("after Containment Priest replacement, Grizzly Bears in exile", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Grizzly Bears", 1);
        checkPermanentCount("after Containment Priest replacement, Grizzly Bears not on battlefield", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Grizzly Bears", 0);

        // Return Grizzly Bears from exile to hand
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "return from exile", "Grizzly Bears");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkHandCardCount("after return from exile, Grizzly Bears in hand", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Grizzly Bears", 1);
        checkExileCount("after return from exile, Grizzly Bears left exile", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Grizzly Bears", 0);

        // Cast Grizzly Bears normally; it must enter with no counters
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Grizzly Bears");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Grizzly Bears", 1);
        assertCounterCount(playerA, "Grizzly Bears", CounterType.P1P1, 0);
        assertPowerToughness(playerA, "Grizzly Bears", 2, 2);
    }

    @Test
    public void test_BatchReturnWithDevour_OtherReturningCreatureKeepsCounter_GhostVacuum() {
        // {T}: Exile target card from a graveyard.
        // {6}, {T}, Sacrifice this artifact: Put each creature card exiled with this artifact onto the battlefield
        // under your control with a flying counter on it. Each of them is a 1/1 Spirit in addition to its other types.
        addCard(Zone.BATTLEFIELD, playerA, "Ghost Vacuum");
        addCard(Zone.BATTLEFIELD, playerA, "Wastes", 6);
        //
        // Devour 1 (As this creature enters, you may sacrifice any number of creatures. It enters with that many +1/+1 counters on it.)
        addCard(Zone.GRAVEYARD, playerB, "Gluttonous Slime"); // 2/2
        addCard(Zone.GRAVEYARD, playerB, "Grizzly Bears"); // bear 2/2
        addCard(Zone.BATTLEFIELD, playerA, "Memnite"); // 1/1 for Devour sacrifice

        // Exile Gluttonous Slime first, then Grizzly Bears so Devour resolves before Grizzly Bears enters
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Exile", "Gluttonous Slime");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkExileCount("after turn 1 exile, Gluttonous Slime in exile", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Gluttonous Slime", 1);

        activateAbility(3, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Exile", "Grizzly Bears");
        waitStackResolved(3, PhaseStep.PRECOMBAT_MAIN);
        checkExileCount("after turn 3 exile, Grizzly Bears in exile", 3, PhaseStep.PRECOMBAT_MAIN, playerA, "Grizzly Bears", 1);

        // Return both creatures simultaneously with flying counters; Gluttonous Slime devours Memnite
        checkPermanentCount("before sacrifice, Memnite on battlefield", 5, PhaseStep.PRECOMBAT_MAIN, playerA, "Memnite", 1);
        activateAbility(5, PhaseStep.PRECOMBAT_MAIN, playerA, "{6}, {T}, Sacrifice");
        setChoice(playerA, true); // Devour creatures? -> yes
        addTarget(playerA, "Memnite"); // sacrifice Memnite to Devour 1

        setStrictChooseMode(true);
        setStopAt(5, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Memnite", 0);
        assertPermanentCount(playerA, "Gluttonous Slime", 1);
        assertPermanentCount(playerA, "Grizzly Bears", 1);
        assertCounterCount(playerA, "Gluttonous Slime", CounterType.P1P1, 1);
        assertCounterCount(playerA, "Gluttonous Slime", CounterType.FLYING, 1);
        assertCounterCount(playerA, "Grizzly Bears", CounterType.FLYING, 1);
    }

    @Test
    public void test_PreventedEntryOfNonPermanentFrontFaceMdfcAndLaterPlay_PlanarIncision() {
        // Exile target artifact or creature, then return it to the battlefield under its owner's control with a +1/+1 counter on it.
        addCard(Zone.HAND, playerA, "Planar Incision"); // {1}{U}
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);
        //
        // Until end of turn, target land becomes a 3/3 creature that's still a land.
        addCard(Zone.HAND, playerA, "Animate Land"); // {G}
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 1);
        //
        addCard(Zone.HAND, playerA, "Sejiri Shelter"); // MDFC: Sejiri Shelter (Instant) // Sejiri Glacier (Land)
        addCustomEffect_ReturnFromAnyToHand(playerA);

        // Play back face Sejiri Glacier and animate it into a creature
        playLand(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Sejiri Glacier");
        checkPermanentCount("after playLand, Sejiri Glacier on battlefield", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Sejiri Glacier", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Animate Land", "Sejiri Glacier");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkPT("after Animate Land, Sejiri Glacier is 3/3", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Sejiri Glacier", 3, 3);

        // Flicker animated Sejiri Glacier; its front face is an instant, so it stays in exile
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Planar Incision", "Sejiri Glacier");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkExileCount("after Planar Incision, Sejiri Shelter stays in exile", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Sejiri Shelter", 1);
        checkPermanentCount("after Planar Incision, Sejiri Glacier not on battlefield", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Sejiri Glacier", 0);

        // Return Sejiri Shelter to hand and play Sejiri Glacier again on next turn; it must enter with no counters
        activateAbility(3, PhaseStep.PRECOMBAT_MAIN, playerA, "return from exile", "Sejiri Shelter");
        waitStackResolved(3, PhaseStep.PRECOMBAT_MAIN);
        checkHandCardCount("after return from exile, Sejiri Shelter in hand", 3, PhaseStep.PRECOMBAT_MAIN, playerA, "Sejiri Shelter", 1);
        checkExileCount("after return from exile, Sejiri Shelter left exile", 3, PhaseStep.PRECOMBAT_MAIN, playerA, "Sejiri Shelter", 0);

        playLand(3, PhaseStep.PRECOMBAT_MAIN, playerA, "Sejiri Glacier");

        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Sejiri Glacier", 1);
        assertCounterCount(playerA, "Sejiri Glacier", CounterType.P1P1, 0);
    }

    @Test
    public void test_PreventedEntryOfDoubleFacedCardAndLaterReanimate_GracefulRestoration() {
        // Choose one — Return target creature card from your graveyard to the battlefield with an additional +1/+1 counter on it.
        addCard(Zone.HAND, playerA, "Graceful Restoration"); // {3}{W}{B}
        addCard(Zone.BATTLEFIELD, playerA, "Scrubland", 5);
        //
        // Creature cards in graveyards and libraries can't enter the battlefield.
        addCard(Zone.BATTLEFIELD, playerB, "Grafdigger's Cage");
        //
        // Return target creature card from your graveyard to the battlefield.
        addCard(Zone.HAND, playerA, "Zombify"); // {3}{B}
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 4);
        //
        addCard(Zone.GRAVEYARD, playerA, "Kytheon, Hero of Akros"); // transforming double-faced creature 2/1
        addCustomEffect_TargetDestroy(playerA);

        // Grafdigger's Cage prevents Graceful Restoration from returning Kytheon
        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {W}", 1);
        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {B}", 4);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Graceful Restoration");
        setModeChoice(playerA, "1"); // return with +1/+1 counter
        addTarget(playerA, "Kytheon, Hero of Akros"); // target creature card in graveyard
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkGraveyardCount("after prevented return, Kytheon stays in graveyard", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Kytheon, Hero of Akros", 1);
        checkPermanentCount("after prevented return, Kytheon not on battlefield", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Kytheon, Hero of Akros", 0);

        // Destroy Grafdigger's Cage
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "target destroy", "Grafdigger's Cage");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkPermanentCount("after destroy, Grafdigger's Cage gone", 1, PhaseStep.PRECOMBAT_MAIN, playerA, playerB, "Grafdigger's Cage", 0);
        checkGraveyardCount("before Zombify, Kytheon still in graveyard", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Kytheon, Hero of Akros", 1);

        // Reanimate Kytheon; it must enter with no counters
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Zombify", "Kytheon, Hero of Akros");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Kytheon, Hero of Akros", 1);
        assertCounterCount(playerA, "Kytheon, Hero of Akros", CounterType.P1P1, 0);
    }
}
