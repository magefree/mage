package org.mage.test.cards.single.mdd;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author JayDi85
 */
public class HelaMistressOfDarknessTest extends CardTestPlayerBase {

    @Test
    public void test_OnlyOnceEachTurn() {
        // Whenever a player loses life during your turn, you may create that many tapped 2/2 black Zombie creature
        // tokens. Do this only once each turn.
        addCard(Zone.BATTLEFIELD, playerA, "Hela, Mistress of Darkness");
        //
        addCard(Zone.HAND, playerA, "Lightning Bolt", 2); // {R}
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);

        // first life loss - create
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", playerB);
        setChoice(playerA, true); // create tokens
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        // second life loss - already done this turn, no choice
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", playerB);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerB, 20 - 3 - 3);
        assertPermanentCount(playerA, "Zombie Token", 3);
    }

    @Test
    public void test_DeclinedTrigger_CanCreateLaterThisTurn() {
        // Whenever a player loses life during your turn, you may create that many tapped 2/2 black Zombie creature
        // tokens. Do this only once each turn.
        addCard(Zone.BATTLEFIELD, playerA, "Hela, Mistress of Darkness");
        //
        addCard(Zone.HAND, playerA, "Lightning Bolt", 1); // {R}
        addCard(Zone.HAND, playerA, "Shock", 1); // {R}
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);

        // first life loss - decline, it isn't done
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", playerB);
        setChoice(playerA, false); // don't create
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        // second life loss, your own - create
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Shock", playerA);
        setChoice(playerA, true); // create tokens

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerB, 20 - 3);
        assertLife(playerA, 20 - 2);
        assertPermanentCount(playerA, "Zombie Token", 2);
        assertTapped("Zombie Token", true);
    }

    @Test
    public void test_OnceEachTurn_NextTurnAgain() {
        // Whenever a player loses life during your turn, you may create that many tapped 2/2 black Zombie creature
        // tokens. Do this only once each turn.
        addCard(Zone.BATTLEFIELD, playerA, "Hela, Mistress of Darkness");
        //
        addCard(Zone.HAND, playerA, "Lightning Bolt", 2); // {R}
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);

        // turn 1
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", playerB);
        setChoice(playerA, true); // create tokens

        // turn 3
        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", playerB);
        setChoice(playerA, true); // create tokens

        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.END_TURN);
        execute();

        assertLife(playerB, 20 - 3 - 3);
        assertPermanentCount(playerA, "Zombie Token", 3 + 3);
    }

    @Test
    public void test_DarkTutor_LifeLossTriggersOtherHela() {
        skipInitShuffling();

        // Whenever a player loses life during your turn, you may create that many tapped 2/2 black Zombie creature
        // tokens. Do this only once each turn.
        addCard(Zone.BATTLEFIELD, playerA, "Hela, Mistress of Darkness");
        //
        // Dark Tutor {1}{B}{B}
        // Vengeance (Cast only from your graveyard. Then exile it.)
        // Search your library for a card, put it into your hand or graveyard, then shuffle. You lose 3 life.
        addCard(Zone.GRAVEYARD, playerA, "Hela, Mistress of Darkness");
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 3);
        addCard(Zone.LIBRARY, playerA, "Grizzly Bears");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Dark Tutor");
        addTarget(playerA, "Grizzly Bears"); // search
        setChoice(playerA, true); // put into hand
        setChoice(playerA, true); // create tokens

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 20 - 3);
        assertHandCount(playerA, "Grizzly Bears", 1);
        assertExileCount(playerA, "Hela, Mistress of Darkness", 1);
        assertPermanentCount(playerA, "Hela, Mistress of Darkness", 1);
        assertPermanentCount(playerA, "Zombie Token", 3);
    }
}
