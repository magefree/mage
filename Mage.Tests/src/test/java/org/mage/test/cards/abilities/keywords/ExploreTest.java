package org.mage.test.cards.abilities.keywords;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.player.TestPlayer;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author xenohedron
 */
public class ExploreTest extends CardTestPlayerBase {

    /**
     * 701.40. Explore
     * 701.40a. Certain abilities instruct a permanent to explore.
     * To do so, that permanent's controller reveals the top card of their library.
     * If a land card is revealed this way, that player puts that card into their hand.
     * Otherwise, that player puts a +1/+1 counter on the exploring permanent and may put the revealed card into their graveyard.
     * 701.40b. A permanent "explores" after the process described in rule 701.40a is complete,
     * even if some or all of those actions were impossible.
     * 701.40c. If a permanent changes zones before an effect causes it to explore,
     * its last known information is used to determine which object explored and who controlled it.
     */

    private static final String mb = "Merfolk Branchwalker"; // 1G 2/1 ETB explores
    private static final String ww = "Wildgrowth Walker"; // 1G 1/3
    // Whenever a creature you control explores, put a +1/+1 counter on Wildgrowth Walker and you gain 3 life.
    private static final String nicanzil = "Nicanzil, Current Conductor";
    // Whenever a creature you control explores a land card, you may put a land card from your hand onto the battlefield tapped.
    // Whenever a creature you control explores a nonland card, put a +1/+1 counter on Nicanzil, Current Conductor.
    private static final String enter = "Enter the Unknown"; // G Sorcery - Target creature you control explores.
    private static final String quicksand = "Quicksand"; // Land
    private static final String gg = "Giant Growth"; // Nonland


    @Test
    public void exploreLandToHand() {
        addCard(Zone.BATTLEFIELD, playerA, ww);
        addCard(Zone.HAND, playerA, mb);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2);
        removeAllCardsFromLibrary(playerA);
        skipInitShuffling();
        addCard(Zone.LIBRARY, playerA, quicksand);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, mb);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        setStrictChooseMode(true);
        execute();

        assertCounterCount(mb, CounterType.P1P1, 0);
        assertCounterCount(ww, CounterType.P1P1, 1);
        assertLife(playerA, 23);
        assertHandCount(playerA, quicksand, 1);
        assertGraveyardCount(playerA, 0);
        assertLibraryCount(playerA, 0);
    }

    @Test
    public void exploreNonlandToTop() {
        addCard(Zone.BATTLEFIELD, playerA, ww);
        addCard(Zone.HAND, playerA, mb);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2);
        removeAllCardsFromLibrary(playerA);
        skipInitShuffling();
        addCard(Zone.LIBRARY, playerA, gg);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, mb);
        setChoice(playerA, false); // not to graveyard

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        setStrictChooseMode(true);
        execute();

        assertCounterCount(mb, CounterType.P1P1, 1);
        assertCounterCount(ww, CounterType.P1P1, 1);
        assertLife(playerA, 23);
        assertHandCount(playerA, 0);
        assertGraveyardCount(playerA, 0);
        assertLibraryCount(playerA, gg, 1);
    }

    @Test
    public void exploreNonlandToGraveyard() {
        addCard(Zone.BATTLEFIELD, playerA, ww);
        addCard(Zone.BATTLEFIELD, playerA, nicanzil);
        addCard(Zone.HAND, playerA, mb);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2);
        removeAllCardsFromLibrary(playerA);
        skipInitShuffling();
        addCard(Zone.LIBRARY, playerA, gg);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, mb);
        setChoice(playerA, true); // yes to graveyard
        setChoice(playerA, "Whenever a creature you control explores a nonland card,"); // order trigger

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        setStrictChooseMode(true);
        execute();

        assertCounterCount(mb, CounterType.P1P1, 1);
        assertCounterCount(ww, CounterType.P1P1, 1);
        assertCounterCount(nicanzil, CounterType.P1P1, 1);
        assertLife(playerA, 23);
        assertHandCount(playerA, 0);
        assertGraveyardCount(playerA, gg,1);
        assertLibraryCount(playerA, 0);
    }

    @Test
    public void exploreLandToBattlefield() {
        addCard(Zone.BATTLEFIELD, playerA, ww);
        addCard(Zone.BATTLEFIELD, playerA, nicanzil);
        addCard(Zone.HAND, playerA, mb);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2);
        removeAllCardsFromLibrary(playerA);
        skipInitShuffling();
        addCard(Zone.LIBRARY, playerA, quicksand);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, mb);
        setChoice(playerA, "Whenever a creature you control explores a land card,"); // order trigger
        setChoice(playerA, true); // yes to put land to battlefield tapped
        setChoice(playerA, quicksand); // choose land

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        setStrictChooseMode(true);
        execute();

        assertCounterCount(mb, CounterType.P1P1, 0);
        assertCounterCount(ww, CounterType.P1P1, 1);
        assertLife(playerA, 23);
        assertTapped(quicksand, true);
        assertGraveyardCount(playerA, 0);
        assertLibraryCount(playerA, 0);
    }

    @Test
    public void exploreEmptyLibrary() {
        // If no card is revealed, most likely because that player's library is empty,
        // the exploring creature receives a +1/+1 counter.
        addCard(Zone.BATTLEFIELD, playerA, ww);
        addCard(Zone.BATTLEFIELD, playerA, nicanzil);
        addCard(Zone.HAND, playerA, mb);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2);
        removeAllCardsFromLibrary(playerA);
        skipInitShuffling();

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, mb);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        setStrictChooseMode(true);
        execute();

        assertCounterCount(mb, CounterType.P1P1, 1);
        assertCounterCount(ww, CounterType.P1P1, 1);
        assertCounterCount(nicanzil, CounterType.P1P1, 0);
        assertLife(playerA, 23);
        assertHandCount(playerA, 0);
        assertGraveyardCount(playerA, 0);
        assertLibraryCount(playerA, 0);
    }

    @Test
    public void exploreTarget() {
        addCard(Zone.BATTLEFIELD, playerA, ww);
        addCard(Zone.HAND, playerA, enter);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 1);
        removeAllCardsFromLibrary(playerA);
        skipInitShuffling();

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, enter, ww);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        setStrictChooseMode(true);
        execute();

        assertCounterCount(ww, CounterType.P1P1, 2);
        assertLife(playerA, 23);
        assertHandCount(playerA, 0);
        assertGraveyardCount(playerA, 1);
        assertGraveyardCount(playerA, enter,1);
        assertLibraryCount(playerA, 0);
    }

    @Test
    public void exploreTwice() {
        String jr = "Jadelight Ranger";
        addCard(Zone.BATTLEFIELD, playerA, ww);
        addCard(Zone.HAND, playerA, jr);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 3);
        removeAllCardsFromLibrary(playerA);
        skipInitShuffling();
        addCard(Zone.LIBRARY, playerA, quicksand, 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, jr);

        setChoice(playerA, "Whenever a creature you control explores"); // order trigger

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        setStrictChooseMode(true);
        execute();

        assertCounterCount(jr, CounterType.P1P1, 0);
        assertCounterCount(ww, CounterType.P1P1, 2);
        assertLife(playerA, 26);
        assertHandCount(playerA, quicksand, 2);
        assertGraveyardCount(playerA, 0);
        assertLibraryCount(playerA, 0);
    }

    @Test
    public void exploreXTimes() {
        String js = "Jadelight Spelunker";
        addCard(Zone.BATTLEFIELD, playerA, ww);
        addCard(Zone.HAND, playerA, js);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 4);
        removeAllCardsFromLibrary(playerA);
        skipInitShuffling();
        addCard(Zone.LIBRARY, playerA, gg, 3);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, js);
        setChoice(playerA, "X=3");
        setChoice(playerA, false); // no to graveyard
        setChoice(playerA, true); // yes to graveyard
        setChoice(playerA, false); // no to graveyard

        setChoice(playerA, "Whenever a creature you control explores"); // order trigger
        setChoice(playerA, "Whenever a creature you control explores"); // order trigger

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        setStrictChooseMode(true);
        execute();

        assertCounterCount(js, CounterType.P1P1, 3);
        assertCounterCount(ww, CounterType.P1P1, 3);
        assertLife(playerA, 29);
        assertHandCount(playerA, gg, 0);
        assertGraveyardCount(playerA, gg, 1);
        assertLibraryCount(playerA, gg, 2);
    }

    @Test
    public void exploreReplacement() {
        String twists = "Twists and Turns";
        // If a creature you control would explore, instead you scry 1, then that creature explores.

        addCard(Zone.BATTLEFIELD, playerA, ww);
        addCard(Zone.BATTLEFIELD, playerA, twists);
        addCard(Zone.HAND, playerA, mb);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2);
        removeAllCardsFromLibrary(playerA);
        skipInitShuffling();
        addCard(Zone.LIBRARY, playerA, quicksand);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, mb);
        addTarget(playerA, TestPlayer.TARGET_SKIP); // scry to top (no targets to bottom)

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        setStrictChooseMode(true);
        execute();

        assertCounterCount(mb, CounterType.P1P1, 0);
        assertCounterCount(ww, CounterType.P1P1, 1);
        assertLife(playerA, 23);
        assertHandCount(playerA, quicksand, 1);
        assertGraveyardCount(playerA, 0);
        assertLibraryCount(playerA, 0);
    }
    
}
