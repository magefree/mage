package org.mage.test.cards.cost.vengeance;

import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.asthought.PlayFromNotOwnHandZoneAllEffect;
import mage.constants.Duration;
import mage.constants.PhaseStep;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * Vengeance cards: the inset spell can be cast only from its owner's graveyard,
 * and the whole card is exiled on resolution.
 */
public class VengeanceCardsTest extends CardTestPlayerBase {

    /*
    Hela, Mistress of Darkness
    {4}{B}{B}
    Legendary Creature - God Sorcerer Villain
    Whenever a player loses life during your turn, you may create that many tapped 2/2 black Zombie creature tokens. Do this only once each turn.
    6/6
    */
    private static final String HELA = "Hela, Mistress of Darkness";
    /*
    Dark Tutor
    {1}{B}{B}
    Sorcery - Vengeance
    Search your library for a card and put that card into your hand. Then shuffle your library.
    */
    private static final String TUTOR = "Dark Tutor";

    @Test
    public void testCastFromGraveyardTutorsAndExiles() {
        skipInitShuffling();
        addCard(Zone.GRAVEYARD, playerA, HELA);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 3);
        addCard(Zone.LIBRARY, playerA, "Mountain");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, TUTOR);
        addTarget(playerA, "Mountain");
        setChoice(playerA, true); // hand

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertExileCount(playerA, HELA, 1);
        assertGraveyardCount(playerA, HELA, 0);
        assertHandCount(playerA, "Mountain", 1);
        assertLife(playerA, 20 - 3);
    }

    @Test
    public void testTutorToGraveyard() {
        skipInitShuffling();
        addCard(Zone.GRAVEYARD, playerA, HELA);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 3);
        addCard(Zone.LIBRARY, playerA, "Mountain");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, TUTOR);
        addTarget(playerA, "Mountain");
        setChoice(playerA, false); // graveyard

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Mountain", 1);
        assertExileCount(playerA, HELA, 1);
    }

    @Test
    public void testCantCastFromHand() {
        addCard(Zone.HAND, playerA, HELA);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 6);

        checkPlayableAbility("main card only", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast " + HELA, true);
        checkPlayableAbility("no vengeance from hand", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast " + TUTOR, false);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();
    }

    @Test
    public void testCantCastFromExile() {
        addCard(Zone.EXILED, playerA, HELA);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 6);

        checkPlayableAbility("no vengeance from exile", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast " + TUTOR, false);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();
    }

    @Test
    public void testCantCastFromOpponentsGraveyard() {
        addCard(Zone.GRAVEYARD, playerB, HELA);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 6);

        checkPlayableAbility("no vengeance from opponent graveyard", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast " + TUTOR, false);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();
    }

    @Test
    public void testCantCastMainCardFromGraveyard() {
        addCard(Zone.GRAVEYARD, playerA, HELA);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 6);

        checkPlayableAbility("vengeance from graveyard", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast " + TUTOR, true);
        checkPlayableAbility("no main card from graveyard", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast " + HELA, false);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();
    }

    @Test
    public void testCounteredGoesToGraveyard() {
        addCard(Zone.GRAVEYARD, playerA, HELA);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 3);
        addCard(Zone.BATTLEFIELD, playerB, "Island", 2);
        addCard(Zone.HAND, playerB, "Counterspell");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, TUTOR);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Counterspell", TUTOR, TUTOR);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, HELA, 1);
        assertExileCount(playerA, HELA, 0);
        assertLife(playerA, 20);
    }

    @Test
    public void testMainCardCastFromHandWorks() {
        addCard(Zone.HAND, playerA, HELA);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 6);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, HELA);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, HELA, 1);
    }

    @Test
    public void testHelaCreatesZombiesOnlyOnYourTurn() {
        addCard(Zone.BATTLEFIELD, playerA, HELA);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);
        addCard(Zone.HAND, playerA, "Lightning Bolt", 2);

        // opponent's turn: no trigger
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", playerB);
        // your turn
        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", playerB);
        setChoice(playerA, true);

        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerB, 20 - 6);
        assertPermanentCount(playerA, "Zombie Token", 3);
        assertTapped("Zombie Token", true);
    }

    @Test
    public void test_CantCastVengeanceFromLibrary_WithPlayPermission() {
        skipInitShuffling();

        // Play with the top card of your library revealed.
        // You may play lands and cast spells from the top of your library.
        addCard(Zone.BATTLEFIELD, playerA, "Future Sight");
        //
        addCard(Zone.LIBRARY, playerA, HELA);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 6);

        // cast only from your graveyard - the permission allows the main card only
        checkPlayableAbility("main card from library", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast " + HELA, true);
        checkPlayableAbility("no vengeance from library", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast " + TUTOR, false);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();
    }

    @Test
    public void test_CastMainCardFromGraveyard_WithPermission() {
        // During each of your turns, you may play a land and cast a permanent spell of each permanent type
        // from your graveyard.
        addCard(Zone.BATTLEFIELD, playerA, "Muldrotha, the Gravetide");
        //
        addCard(Zone.GRAVEYARD, playerA, HELA);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 6);

        // both parts can be cast from the graveyard now
        checkPlayableAbility("vengeance", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast " + TUTOR, true);
        checkPlayableAbility("main card", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast " + HELA, true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, HELA);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, HELA, 1);
        assertGraveyardCount(playerA, HELA, 0);
    }

    @Test
    public void test_CantCastFromOpponentsGraveyard_WithPlayPermission() {
        // you may play cards from any graveyard
        addCustomCardWithAbility("play from any graveyard", playerA, new SimpleStaticAbility(
                new PlayFromNotOwnHandZoneAllEffect(
                        StaticFilters.FILTER_CARD, Zone.GRAVEYARD, false, TargetController.YOU, Duration.WhileOnBattlefield
                )
        ));
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);
        //
        addCard(Zone.GRAVEYARD, playerB, HELA);
        addCard(Zone.GRAVEYARD, playerB, "Lightning Bolt");

        // cast only from your graveyard - the permission works for other cards only
        checkPlayableAbility("other card", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Lightning Bolt", true);
        checkPlayableAbility("no vengeance", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast " + TUTOR, false);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();
    }

    @Test
    public void test_CastWithoutPayingManaCost_CantCastVengeanceFromExile() {
        skipInitShuffling();
        removeAllCardsFromLibrary(playerA);

        // Cascade, cascade
        addCard(Zone.HAND, playerA, "Maelstrom Wanderer"); // {5}{U}{R}{G}
        addCard(Zone.BATTLEFIELD, playerA, "Island", 8);
        addCard(Zone.BATTLEFIELD, playerA, "Taiga", 8);
        //
        addCard(Zone.LIBRARY, playerA, "Swamp", 3);
        addCard(Zone.LIBRARY, playerA, HELA);

        // cascade into a vengeance card - only the main card can be cast, no choice of the vengeance spell
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Maelstrom Wanderer");
        setChoice(playerA, "cascade"); // triggers order
        setChoice(playerA, true); // cast the exiled card

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, HELA, 1);
        assertExileCount(playerA, HELA, 0);
    }
}
