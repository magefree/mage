package org.mage.test.cards.single.frc;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestCommander4Players;

/**
 * @author DarioBarrabinoDev
 */
public class TheUrSphinxTest extends CardTestCommander4Players {

    // Eminence -- As long as The Ur-Sphinx is in the command zone or on the battlefield, other Sphinx spells you cast cost {1} less to cast.
    // Flying
    // Whenever one or more Sphinxes you control attack, each player mills that many cards. For each player, you may cast a card that player milled this way without paying its mana cost.
    private static final String urSphinx = "The Ur-Sphinx"; // {6}{W}{U}{B} 10/10

    private static final String curator = "Curator of Mysteries"; // {2}{U}{U} Sphinx 4/4 flying
    private static final String talrand = "Talrand, Sky Summoner"; // {2}{U}{U} Human Wizard 2/2
    private static final String bears = "Grizzly Bears"; // {1}{G} 2/2
    private static final String lion = "Silvercoat Lion"; // {1}{W} 2/2
    private static final String runeclaw = "Runeclaw Bear"; // {1}{G} 2/2
    private static final String forest = "Forest";
    private static final String island = "Island";
    private static final String swamp = "Swamp";

    @Test
    public void test_Eminence_CommandZone() {
        // Player order: A -> D -> C -> B
        setStrictChooseMode(true);

        addCard(Zone.COMMAND, playerA, urSphinx);
        addCard(Zone.HAND, playerA, curator);
        addCard(Zone.HAND, playerA, talrand);
        addCard(Zone.BATTLEFIELD, playerA, island, 3);

        checkPlayableAbility("Sphinx costs 1 less", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast " + curator, true);
        checkPlayableAbility("non-Sphinx costs full", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast " + talrand, false);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, curator);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertCommandZoneCount(playerA, urSphinx, 1);
        assertPermanentCount(playerA, curator, 1);
        assertHandCount(playerA, talrand, 1);
    }

    @Test
    public void test_Eminence_Battlefield() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, urSphinx);
        addCard(Zone.HAND, playerA, curator);
        addCard(Zone.HAND, playerA, talrand);
        addCard(Zone.BATTLEFIELD, playerA, island, 3);

        checkPlayableAbility("Sphinx costs 1 less", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast " + curator, true);
        checkPlayableAbility("non-Sphinx costs full", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast " + talrand, false);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, curator);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, curator, 1);
        assertHandCount(playerA, talrand, 1);
    }

    @Test
    public void test_OneSphinxAttacks_EachPlayerMillsOne_CastOnePerPlayer() {
        setStrictChooseMode(true);
        skipInitShuffling();

        addCard(Zone.BATTLEFIELD, playerA, urSphinx);

        // last added card goes to top
        removeAllCardsFromLibrary(playerA);
        addCard(Zone.LIBRARY, playerA, island);
        addCard(Zone.LIBRARY, playerA, bears);
        addCard(Zone.LIBRARY, playerA, swamp); // drawn on turn 1 (no draw skip in multiplayer)
        removeAllCardsFromLibrary(playerB);
        addCard(Zone.LIBRARY, playerB, island);
        addCard(Zone.LIBRARY, playerB, lion);
        removeAllCardsFromLibrary(playerC);
        addCard(Zone.LIBRARY, playerC, island);
        addCard(Zone.LIBRARY, playerC, forest);
        removeAllCardsFromLibrary(playerD);
        addCard(Zone.LIBRARY, playerD, island);
        addCard(Zone.LIBRARY, playerD, runeclaw);

        attack(1, playerA, urSphinx, playerB);
        setChoice(playerA, true); // cast Grizzly Bears
        setChoice(playerA, true); // cast Silvercoat Lion
        setChoice(playerA, true); // cast Runeclaw Bear

        setStopAt(1, PhaseStep.END_COMBAT);
        execute();

        assertLife(playerB, 20 - 10);
        assertPermanentCount(playerA, bears, 1);
        assertPermanentCount(playerA, lion, 1);
        assertPermanentCount(playerA, runeclaw, 1);
        assertGraveyardCount(playerC, forest, 1);
        assertLibraryCount(playerA, island, 1);
        assertLibraryCount(playerB, island, 1);
        assertLibraryCount(playerC, island, 1);
        assertLibraryCount(playerD, island, 1);
    }

    @Test
    public void test_TwoSphinxesAttack_EachPlayerMillsTwo_OnlyOneCastPerPlayer() {
        setStrictChooseMode(true);
        skipInitShuffling();

        addCard(Zone.BATTLEFIELD, playerA, urSphinx);
        addCard(Zone.BATTLEFIELD, playerA, curator);

        removeAllCardsFromLibrary(playerA);
        addCard(Zone.LIBRARY, playerA, island);
        addCard(Zone.LIBRARY, playerA, lion);
        addCard(Zone.LIBRARY, playerA, bears);
        addCard(Zone.LIBRARY, playerA, swamp); // drawn on turn 1 (no draw skip in multiplayer)
        removeAllCardsFromLibrary(playerB);
        addCard(Zone.LIBRARY, playerB, island, 3);
        removeAllCardsFromLibrary(playerC);
        addCard(Zone.LIBRARY, playerC, forest, 3);
        removeAllCardsFromLibrary(playerD);
        addCard(Zone.LIBRARY, playerD, island);
        addCard(Zone.LIBRARY, playerD, forest, 2);

        attack(1, playerA, urSphinx, playerB);
        attack(1, playerA, curator, playerB);
        setChoice(playerA, bears); // card to cast from among Grizzly Bears and Silvercoat Lion
        setChoice(playerA, true); // cast Grizzly Bears

        setStopAt(1, PhaseStep.END_COMBAT);
        execute();

        assertLife(playerB, 20 - 10 - 4);
        assertPermanentCount(playerA, bears, 1);
        assertPermanentCount(playerA, lion, 0);
        assertGraveyardCount(playerA, lion, 1);
        assertGraveyardCount(playerB, island, 2);
        assertGraveyardCount(playerC, forest, 2);
        assertGraveyardCount(playerD, forest, 2);
        assertLibraryCount(playerA, island, 1);
        assertLibraryCount(playerB, island, 1);
        assertLibraryCount(playerC, forest, 1);
        assertLibraryCount(playerD, island, 1);
    }

    @Test
    public void test_OnlyLandsMilled_NothingToCast() {
        setStrictChooseMode(true);
        skipInitShuffling();

        addCard(Zone.BATTLEFIELD, playerA, urSphinx);

        removeAllCardsFromLibrary(playerA);
        addCard(Zone.LIBRARY, playerA, forest, 2);
        addCard(Zone.LIBRARY, playerA, swamp); // drawn on turn 1 (no draw skip in multiplayer)
        removeAllCardsFromLibrary(playerB);
        addCard(Zone.LIBRARY, playerB, island, 2);
        removeAllCardsFromLibrary(playerC);
        addCard(Zone.LIBRARY, playerC, forest, 2);
        removeAllCardsFromLibrary(playerD);
        addCard(Zone.LIBRARY, playerD, island, 2);

        attack(1, playerA, urSphinx, playerB);
        // no choices: lands can't be cast

        setStopAt(1, PhaseStep.END_COMBAT);
        execute();

        assertGraveyardCount(playerA, forest, 1);
        assertGraveyardCount(playerB, island, 1);
        assertGraveyardCount(playerC, forest, 1);
        assertGraveyardCount(playerD, island, 1);
        assertPermanentCount(playerA, forest, 0);
    }

    @Test
    public void test_ChooseNotToCast() {
        setStrictChooseMode(true);
        skipInitShuffling();

        addCard(Zone.BATTLEFIELD, playerA, urSphinx);

        removeAllCardsFromLibrary(playerA);
        addCard(Zone.LIBRARY, playerA, bears);
        addCard(Zone.LIBRARY, playerA, swamp); // drawn on turn 1 (no draw skip in multiplayer)
        removeAllCardsFromLibrary(playerB);
        addCard(Zone.LIBRARY, playerB, lion);
        removeAllCardsFromLibrary(playerC);
        addCard(Zone.LIBRARY, playerC, runeclaw);
        removeAllCardsFromLibrary(playerD);
        addCard(Zone.LIBRARY, playerD, forest);

        attack(1, playerA, urSphinx, playerB);
        setChoice(playerA, false); // don't cast
        setChoice(playerA, false); // don't cast
        setChoice(playerA, false); // don't cast

        setStopAt(1, PhaseStep.END_COMBAT);
        execute();

        assertPermanentCount(playerA, bears, 0);
        assertPermanentCount(playerA, lion, 0);
        assertPermanentCount(playerA, runeclaw, 0);
        assertGraveyardCount(playerA, bears, 1);
        assertGraveyardCount(playerB, lion, 1);
        assertGraveyardCount(playerC, runeclaw, 1);
        assertGraveyardCount(playerD, forest, 1);
    }

    @Test
    public void test_NonSphinxAttacks_NoTrigger() {
        setStrictChooseMode(true);
        skipInitShuffling();

        addCard(Zone.BATTLEFIELD, playerA, urSphinx);
        addCard(Zone.BATTLEFIELD, playerA, lion);

        removeAllCardsFromLibrary(playerA);
        addCard(Zone.LIBRARY, playerA, bears);
        addCard(Zone.LIBRARY, playerA, swamp); // drawn on turn 1 (no draw skip in multiplayer)

        attack(1, playerA, lion, playerB);

        setStopAt(1, PhaseStep.END_COMBAT);
        execute();

        assertLife(playerB, 20 - 2);
        assertLibraryCount(playerA, bears, 1);
        assertHandCount(playerA, swamp, 1);
        assertGraveyardCount(playerA, 0);
    }
}
