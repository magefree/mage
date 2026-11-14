package org.mage.test.cards.single.msc;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * 701.50b
 * If a permanent is no longer on the battlefield as an effect instructs it to connive, its last known information
 * is used to determine which object connived and who controlled it.
 * <p>
 * 701.50f
 * A permanent “connives” after the process described in either rule 701.50a or 701.50d is complete, even if some or
 * all of those actions were impossible.
 *
 * @author JayDi85
 */
public class GloriousPurposeTest extends CardTestPlayerBase {

    /**
     * Glorious Purpose {1}{U}
     * Enchantment - Plan
     * Whenever a creature you control connives, put a +1/+1 counter on that creature and a plan counter on this
     * enchantment.
     * When the sixth plan counter is put on this enchantment, sacrifice it. If you do, exile the top four cards of your
     * library. You may cast any number of spells from among them without paying their mana costs. Put the rest into
     * your hand.
     */
    private static final String purpose = "Glorious Purpose";

    @Test
    public void test_Connive_NonlandDiscarded() {
        skipInitShuffling();
        addCard(Zone.BATTLEFIELD, playerA, purpose);
        //
        addCard(Zone.HAND, playerA, "Echo Inspector"); // {3}{U}, 2/3, connives on enter
        addCard(Zone.BATTLEFIELD, playerA, "Island", 4);
        addCard(Zone.HAND, playerA, "Grizzly Bears"); // nonland card to discard
        addCard(Zone.LIBRARY, playerA, "Island"); // card to draw

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Echo Inspector");
        setChoice(playerA, "Grizzly Bears"); // connive discard

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertCounterCount(playerA, "Echo Inspector", CounterType.P1P1, 1 + 1); // connive + Glorious Purpose
        assertCounterCount(playerA, purpose, CounterType.PLAN, 1);
    }

    @Test
    public void test_Connive_LandDiscarded() {
        skipInitShuffling();
        addCard(Zone.BATTLEFIELD, playerA, purpose);
        //
        addCard(Zone.HAND, playerA, "Echo Inspector"); // {3}{U}, 2/3, connives on enter
        addCard(Zone.BATTLEFIELD, playerA, "Island", 4);
        addCard(Zone.HAND, playerA, "Mountain"); // land card to discard
        addCard(Zone.LIBRARY, playerA, "Island"); // card to draw

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Echo Inspector");
        setChoice(playerA, "Mountain"); // connive discard

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        // a land discarded - no counter from connive itself, but the creature still connives (701.50f)
        assertCounterCount(playerA, "Echo Inspector", CounterType.P1P1, 1); // Glorious Purpose
        assertCounterCount(playerA, purpose, CounterType.PLAN, 1);
    }

    @Test
    public void test_Connive_OpponentsCreature_NoTrigger() {
        skipInitShuffling();
        addCard(Zone.BATTLEFIELD, playerA, purpose);
        //
        addCard(Zone.HAND, playerB, "Echo Inspector"); // {3}{U}, 2/3, connives on enter
        addCard(Zone.BATTLEFIELD, playerB, "Island", 4);
        addCard(Zone.HAND, playerB, "Grizzly Bears"); // nonland card to discard
        addCard(Zone.LIBRARY, playerB, "Island"); // card to draw

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Echo Inspector");
        setChoice(playerB, "Grizzly Bears"); // connive discard

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.END_TURN);
        execute();

        assertCounterCount(playerB, "Echo Inspector", CounterType.P1P1, 1); // connive only
        assertCounterCount(playerA, purpose, CounterType.PLAN, 0);
    }

    @Test
    public void test_Connive_CreatureLeftBattlefield_StillTriggers() {
        skipInitShuffling();
        addCard(Zone.BATTLEFIELD, playerA, purpose);
        //
        addCard(Zone.HAND, playerA, "Echo Inspector"); // {3}{U}, 2/3, connives on enter
        addCard(Zone.BATTLEFIELD, playerA, "Island", 4);
        addCard(Zone.HAND, playerA, "Grizzly Bears"); // nonland card to discard
        addCard(Zone.LIBRARY, playerA, "Island"); // card to draw
        //
        addCard(Zone.HAND, playerA, "Unsummon"); // {U}, return target creature to its owner's hand
        addCard(Zone.BATTLEFIELD, playerA, "Island", 1);

        // bounce Echo Inspector in response to its connive trigger
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Echo Inspector");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, playerA, true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Unsummon", "Echo Inspector");
        setChoice(playerA, "Grizzly Bears"); // connive discard

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        // it's not on the battlefield when it connives, so its last known information is used (701.50b)
        assertHandCount(playerA, "Echo Inspector", 1);
        assertGraveyardCount(playerA, "Grizzly Bears", 1);
        assertCounterCount(playerA, purpose, CounterType.PLAN, 1);
    }

    @Test
    public void test_SixthPlanCounter_SacrificeAndExileOnSameResolve() {
        skipInitShuffling();
        removeAllCardsFromLibrary(playerA);
        addCard(Zone.BATTLEFIELD, playerA, purpose);
        addCard(Zone.LIBRARY, playerA, "Island", 4); // top four cards

        addCounters(1, PhaseStep.PRECOMBAT_MAIN, playerA, purpose, CounterType.PLAN, 6);
        checkStackSize("plan trigger", 1, PhaseStep.PRECOMBAT_MAIN, playerA, 1);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, playerA, true);
        // "sacrifice it. If you do, exile ..." - all of it happens while the trigger resolves
        checkStackSize("after plan trigger", 1, PhaseStep.PRECOMBAT_MAIN, playerA, 0);
        checkGraveyardCount("sacrificed", 1, PhaseStep.PRECOMBAT_MAIN, playerA, purpose, 1);
        checkHandCardCount("lands to hand", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Island", 4);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
    }

    @Test
    public void test_SixthPlanCounter_CastSpellsAndPutRestIntoHand() {
        skipInitShuffling();
        removeAllCardsFromLibrary(playerA);
        addCard(Zone.BATTLEFIELD, playerA, purpose);
        addCard(Zone.LIBRARY, playerA, "Lightning Bolt"); // {R}, 3 damage to any target
        addCard(Zone.LIBRARY, playerA, "Grizzly Bears"); // {1}{G}, bear 2/2
        addCard(Zone.LIBRARY, playerA, "Hill Giant"); // {3}{R}, giant 3/3
        addCard(Zone.LIBRARY, playerA, "Island");

        // no lands on the battlefield - the spells can be cast only without paying their mana costs
        addCounters(1, PhaseStep.PRECOMBAT_MAIN, playerA, purpose, CounterType.PLAN, 6);
        setChoice(playerA, "Lightning Bolt"); // spell to cast
        setChoice(playerA, true); // cast it without paying its mana cost
        addTarget(playerA, playerB); // target for Lightning Bolt
        setChoice(playerA, "Grizzly Bears"); // spell to cast
        setChoice(playerA, true); // cast it without paying its mana cost
        setChoice(playerA, false); // don't cast Hill Giant
        setChoice(playerA, false); // stop casting

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerA, purpose, 1);
        assertLife(playerB, 20 - 3);
        assertPermanentCount(playerA, "Grizzly Bears", 1);
        assertHandCount(playerA, "Hill Giant", 1);
        assertHandCount(playerA, "Island", 1);
        assertExileCount(playerA, 0);
    }

    @Test
    public void test_SixthPlanCounter_CantSacrifice_NothingExiled() {
        skipInitShuffling();
        removeAllCardsFromLibrary(playerA);
        addCard(Zone.BATTLEFIELD, playerA, purpose);
        addCard(Zone.LIBRARY, playerA, "Island", 4); // top four cards
        //
        addCard(Zone.HAND, playerA, "Disperse"); // {1}{U}, return target nonland permanent to its owner's hand
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);

        // bounce it in response to the plan trigger - nothing to sacrifice, so nothing is exiled ("If you do")
        addCounters(1, PhaseStep.PRECOMBAT_MAIN, playerA, purpose, CounterType.PLAN, 6);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Disperse", purpose);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertHandCount(playerA, purpose, 1);
        assertLibraryCount(playerA, "Island", 4);
        assertExileCount(playerA, 0);
    }
}