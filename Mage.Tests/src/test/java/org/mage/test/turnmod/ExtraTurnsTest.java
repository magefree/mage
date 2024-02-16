package org.mage.test.turnmod;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.players.Player;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.player.TestPlayer;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author LevelX2, JayDi85
 */
public class ExtraTurnsTest extends CardTestPlayerBase {

    private void checkTurnControl(int turn, TestPlayer needTurnController, boolean isExtraTurn) {
        runCode("checking turn " + turn, turn, PhaseStep.POSTCOMBAT_MAIN, playerA, (info, player, game) -> {
            Player defaultTurnController = game.getPlayer(game.getActivePlayerId());
            Player realTurnController = defaultTurnController.getTurnControlledBy() == null ? defaultTurnController : game.getPlayer(defaultTurnController.getTurnControlledBy());
            Assert.assertEquals(String.format("turn %d must be controlled by %s", turn, needTurnController.getName()),
                    needTurnController.getName(), realTurnController.getName());
            Assert.assertEquals(String.format("turn %d must be %s", turn, (isExtraTurn ? "extra turn" : "normal turn")),
                    isExtraTurn, game.getState().isExtraTurn());
        });
    }

    @Test
    public void test_EmrakulMustGiveExtraTurn_OnOwnTurn() {
        // Emrakul, the Promised End costs {1} less to cast for each card type among cards in your graveyard.
        // When you cast Emrakul, you gain control of target opponent during that player's next turn. After that turn, that player takes an extra turn.
        addCard(Zone.HAND, playerA, "Emrakul, the Promised End", 1); // {13}
        addCard(Zone.BATTLEFIELD, playerA, "Island", 12);
        addCard(Zone.GRAVEYARD, playerA, "Island", 1);

        // cast emrakul on own turn and take extra turn
        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerA, "Emrakul, the Promised End");
        addTarget(playerA, playerB);

        // checks for all turns
        // Turn 4 is the next turn of opponent (player B)  that player A controls
        // So Turn 5 is the extra turn for player B after Turn 4
        checkTurnControl(1, playerA, false);
        checkTurnControl(2, playerB, false);
        checkTurnControl(3, playerA, false);
        checkTurnControl(4, playerA, false); // A take control of B's turn
        checkTurnControl(5, playerB, true); // B take extra turn
        checkTurnControl(6, playerA, false);
        checkTurnControl(7, playerB, false);

        setStrictChooseMode(true);
        setStopAt(8, PhaseStep.END_TURN);
        execute();
    }

    @Test
    public void test_EmrakulMustGiveExtraTurn_OnOpponentTurn() {
        // Emrakul, the Promised End costs {1} less to cast for each card type among cards in your graveyard.
        // When you cast Emrakul, you gain control of target opponent during that player's next turn. After that turn, that player takes an extra turn.
        addCard(Zone.HAND, playerA, "Emrakul, the Promised End", 1); // {13}
        addCard(Zone.BATTLEFIELD, playerA, "Island", 12);
        addCard(Zone.GRAVEYARD, playerA, "Island", 1);
        //
        // Flash (You may cast this spell any time you could cast an instant.)
        // Creature cards you own that aren't on the battlefield have flash.
        // Each opponent can cast spells only any time they could cast a sorcery.
        addCard(Zone.BATTLEFIELD, playerA, "Teferi, Mage of Zhalfir", 1);

        // cast emrakul on opponent's turn and take extra turn
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerA, "Emrakul, the Promised End");
        addTarget(playerA, playerB);

        // checks for all turns
        // Turn 4 is the next turn of opponent (player B)  that player A controls
        // So Turn 5 is the extra turn for player B after Turn 4
        checkTurnControl(1, playerA, false);
        checkTurnControl(2, playerB, false);
        checkTurnControl(3, playerA, false);
        checkTurnControl(4, playerA, false); // A take control of B's turn
        checkTurnControl(5, playerB, true); // B take extra turn
        checkTurnControl(6, playerA, false);
        checkTurnControl(7, playerB, false);

        setStrictChooseMode(true);
        setStopAt(8, PhaseStep.END_TURN);
        execute();
    }

    /**
     * https://github.com/magefree/mage/issues/6824
     * <p>
     * When you cast miracled Temporal Mastery with God-Eternal Kefnet on the
     * battlefield and copy it with it's ability you get only 1 extra turn. It
     * should be 2, since you cast Temporal Mastery with it's miracle ability +
     * you get a copy from Kefnet's ability. Still after first extra turn game
     * proceeds to next player.
     */
    @Test
    public void test_MiracledTemporalMastery_MustGives2ExtraTurnsWithCopy() {
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

        // turn checks
        // Turn 3 + 4 are extra turns
        checkTurnControl(1, playerA, false);
        checkTurnControl(2, playerB, false);
        checkTurnControl(3, playerB, true); // extra turn for B
        checkTurnControl(4, playerB, true); // extra turn for B
        checkTurnControl(5, playerA, false);

        setStrictChooseMode(true);
        setStopAt(5, PhaseStep.END_TURN);
        execute();

        assertExileCount(playerB, "Temporal Mastery", 1);
    }
}
