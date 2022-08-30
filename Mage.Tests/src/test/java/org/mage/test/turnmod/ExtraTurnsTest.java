package org.mage.test.turnmod;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class ExtraTurnsTest extends CardTestPlayerBase {

    /**
     * Emrakul, the Promised End not giving an extra turn when cast in the
     * opponent's turn
     */
    @Test
    public void testEmrakulCastOnOpponentsTurnCheckTurn3() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 12);
        addCard(Zone.GRAVEYARD, playerA, "Island", 1);
        // Emrakul, the Promised End costs {1} less to cast for each card type among cards in your graveyard.
        // When you cast Emrakul, you gain control of target opponent during that player's next turn. After that turn, that player takes an extra turn.
        // Flying
        // Trample
        // Protection from instants
        addCard(Zone.HAND, playerA, "Emrakul, the Promised End", 1); // {13}
        // Flash (You may cast this spell any time you could cast an instant.)
        // Creature cards you own that aren't on the battlefield have flash.
        // Each opponent can cast spells only any time they could cast a sorcery.
        addCard(Zone.BATTLEFIELD, playerA, "Teferi, Mage of Zhalfir", 1);

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerA, "Emrakul, the Promised End");
        // Turn 4 is the next turn of opponent (player B)  that player A controls
        // So Turn 5 is the extra turn for player B after Turn 4
        setStopAt(3, PhaseStep.DRAW);
        execute();

        assertPermanentCount(playerA, "Emrakul, the Promised End", 1);
        Assert.assertTrue("Turn 3 is no extra turn ", !currentGame.getState().isExtraTurn());
        Assert.assertEquals("For turn " + currentGame.getTurnNum() + ", playerA has to be the active player but active player is: "
                + currentGame.getPlayer(currentGame.getActivePlayerId()).getName(), currentGame.getActivePlayerId(), playerA.getId());
    }

    @Test
    public void testEmrakulCastOnOpponentsTurnCheckTurn4() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 12);
        addCard(Zone.GRAVEYARD, playerA, "Island", 1);
        // Emrakul, the Promised End costs {1} less to cast for each card type among cards in your graveyard.
        // When you cast Emrakul, you gain control of target opponent during that player's next turn. After that turn, that player takes an extra turn.
        // Flying
        // Trample
        // Protection from instants
        addCard(Zone.HAND, playerA, "Emrakul, the Promised End", 1); // {13}
        // Flash (You may cast this spell any time you could cast an instant.)
        // Creature cards you own that aren't on the battlefield have flash.
        // Each opponent can cast spells only any time they could cast a sorcery.
        addCard(Zone.BATTLEFIELD, playerA, "Teferi, Mage of Zhalfir", 1);

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerA, "Emrakul, the Promised End");
        // Turn 4 is the next turn of opponent (player B)  that player A controls
        // So Turn 5 is the extra turn for player B after Turn 4
        setStopAt(4, PhaseStep.DRAW);
        execute();

        assertPermanentCount(playerA, "Emrakul, the Promised End", 1);
        Assert.assertTrue("Turn 4 is a controlled turn ", !playerB.isGameUnderControl());
        Assert.assertEquals("For turn " + currentGame.getTurnNum() + ", playerB has to be the active player but active player is: "
                + currentGame.getPlayer(currentGame.getActivePlayerId()).getName(), currentGame.getActivePlayerId(), playerB.getId());
    }

    @Test
    public void testEmrakulCastOnOpponentsTurnCheckTurn5() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 12);
        addCard(Zone.GRAVEYARD, playerA, "Island", 1);
        // Emrakul, the Promised End costs {1} less to cast for each card type among cards in your graveyard.
        // When you cast Emrakul, you gain control of target opponent during that player's next turn. After that turn, that player takes an extra turn.
        // Flying
        // Trample
        // Protection from instants
        addCard(Zone.HAND, playerA, "Emrakul, the Promised End", 1); // {13}
        // Flash (You may cast this spell any time you could cast an instant.)
        // Creature cards you own that aren't on the battlefield have flash.
        // Each opponent can cast spells only any time they could cast a sorcery.
        addCard(Zone.BATTLEFIELD, playerA, "Teferi, Mage of Zhalfir", 1);

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerA, "Emrakul, the Promised End");
        // Turn 4 is the next turn of opponent (player B)  that player A controls
        // So Turn 5 is the extra turn for player B after Turn 4
        setStopOnTurn(5);
        execute();

        assertPermanentCount(playerA, "Emrakul, the Promised End", 1);
        Assert.assertTrue("Turn 5 is an extra turn ", currentGame.getState().isExtraTurn());
        Assert.assertEquals("For turn " + currentGame.getTurnNum() + ", playerB has to be the active player but active player is: "
                + currentGame.getPlayer(currentGame.getActivePlayerId()).getName(), currentGame.getActivePlayerId(), playerB.getId());
    }

    /**
     * https://github.com/magefree/mage/issues/6824
     *
     * When you cast miracled Temporal Mastery with God-Eternal Kefnet on the
     * battlefield and copy it with it's ability you get only 1 extra turn. It
     * should be 2, since you cast Temporal Mastery with it's miracle ability +
     * you get a copy from Kefnet's ability. Still after first extra turn game
     * proceeds to next player.
     */
    @Test
    public void testCopyMiracledTemporalMastery4TwoExtraTurns() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerB, "Island", 7);
        // Flying
        // You may reveal the first card you draw each turn as you draw it. Whenever you reveal an instant or sorcery card this way,
        // copy that card and you may cast the copy. That copy costs {2} less to cast.
        // When God-Eternal Kefnet dies or is put into exile from the battlefield, you may put it into its owner’s library third from the top.
        addCard(Zone.BATTLEFIELD, playerB, "God-Eternal Kefnet", 1);
        // Take an extra turn after this one. Exile Temporal Mastery.
        // Miracle {1}{U} (You may cast this card for its miracle cost when you draw it if it's the first card you drew this turn.)
        addCard(Zone.LIBRARY, playerB, "Temporal Mastery", 1); // Sorcery {5}{U}{U}
        skipInitShuffling();

        setChoice(playerB, true); // Would you like to reveal first drawn card (Temporal Mastery, you can copy it and cast {2} less)?
        setChoice(playerB, true); // Would you like to copy Temporal Mastery and cast it {2} less?
        setChoice(playerB, true); // Reveal Temporal Mastery to be able to use Miracle?
        setChoice(playerB, true); // Miracle {1}{U} (You may cast this card for its miracle cost when you draw it if it's the first card you drew this turn.)

        setChoice(playerB, false); // Would you like to reveal first drawn card? (Turn 3)
        setChoice(playerB, false); // Would you like to reveal first drawn card? (Turn 4)

        // Turn 3 + 4 are extra turns
        setStopAt(4, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertExileCount(playerB, "Temporal Mastery", 1);

        Assert.assertTrue("Turn 4 is an extra turn ", currentGame.getState().isExtraTurn());
        Assert.assertEquals("For turn " + currentGame.getTurnNum() + ", playerB has to be the active player but active player is: "
                + currentGame.getPlayer(currentGame.getActivePlayerId()).getName(), currentGame.getActivePlayerId(), playerB.getId());
    }

}
