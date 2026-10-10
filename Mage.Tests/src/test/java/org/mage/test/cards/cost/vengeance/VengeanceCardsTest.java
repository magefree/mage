package org.mage.test.cards.cost.vengeance;

import mage.constants.PhaseStep;
import mage.constants.Zone;
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
        setStrictChooseMode(true);
        skipInitShuffling();
        addCard(Zone.GRAVEYARD, playerA, HELA);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 3);
        addCard(Zone.LIBRARY, playerA, "Mountain");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, TUTOR);
        addTarget(playerA, "Mountain");
        setChoice(playerA, true); // hand

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertExileCount(playerA, HELA, 1);
        assertGraveyardCount(playerA, HELA, 0);
        assertHandCount(playerA, "Mountain", 1);
        assertLife(playerA, 20 - 3);
    }

    @Test
    public void testTutorToGraveyard() {
        setStrictChooseMode(true);
        skipInitShuffling();
        addCard(Zone.GRAVEYARD, playerA, HELA);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 3);
        addCard(Zone.LIBRARY, playerA, "Mountain");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, TUTOR);
        addTarget(playerA, "Mountain");
        setChoice(playerA, false); // graveyard

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

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();
    }

    @Test
    public void testCantCastFromExile() {
        addCard(Zone.EXILED, playerA, HELA);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 6);

        checkPlayableAbility("no vengeance from exile", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast " + TUTOR, false);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();
    }

    @Test
    public void testCantCastFromOpponentsGraveyard() {
        addCard(Zone.GRAVEYARD, playerB, HELA);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 6);

        checkPlayableAbility("no vengeance from opponent graveyard", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast " + TUTOR, false);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();
    }

    @Test
    public void testCantCastMainCardFromGraveyard() {
        addCard(Zone.GRAVEYARD, playerA, HELA);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 6);

        checkPlayableAbility("vengeance from graveyard", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast " + TUTOR, true);
        checkPlayableAbility("no main card from graveyard", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast " + HELA, false);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();
    }

    @Test
    public void testCounteredGoesToGraveyard() {
        setStrictChooseMode(true);
        addCard(Zone.GRAVEYARD, playerA, HELA);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 3);
        addCard(Zone.BATTLEFIELD, playerB, "Island", 2);
        addCard(Zone.HAND, playerB, "Counterspell");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, TUTOR);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Counterspell", TUTOR, TUTOR);

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

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, HELA, 1);
    }

    @Test
    public void testHelaCreatesZombiesOnlyOnYourTurn() {
        setStrictChooseMode(true);
        addCard(Zone.BATTLEFIELD, playerA, HELA);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);
        addCard(Zone.HAND, playerA, "Lightning Bolt", 2);

        // opponent's turn: no trigger
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", playerB);
        // your turn
        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", playerB);
        setChoice(playerA, true);

        setStopAt(3, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerB, 20 - 6);
        assertPermanentCount(playerA, "Zombie Token", 3);
        assertTapped("Zombie Token", true);
    }
}
