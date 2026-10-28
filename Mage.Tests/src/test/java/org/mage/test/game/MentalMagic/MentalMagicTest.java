package org.mage.test.game.MentalMagic;

import mage.constants.MultiplayerAttackOption;
import mage.constants.PhaseStep;
import mage.constants.RangeOfInfluence;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.GameException;
import mage.game.MentalMagicGame;
import mage.game.mulligan.MulliganType;
import mage.watchers.common.MentalMagicNamesWatcher;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

import java.io.FileNotFoundException;

/**
 * @author xico7
 */
public class MentalMagicTest extends CardTestPlayerBase {

    private static final String PLAY_AS_LAND = "Play this card face down as a Mental Land";
    private static final String TURN_INTO = "Turn this card into another card";

    @Override
    protected Game createNewGameAndPlayers() throws GameException, FileNotFoundException {
        Game game = new MentalMagicGame(MultiplayerAttackOption.LEFT, RangeOfInfluence.ONE,
                MulliganType.GAME_DEFAULT.getMulligan(0), 60, 20, 7, false, false);
        playerA = createPlayer(game, "PlayerA", deckNameA);
        playerB = createPlayer(game, "PlayerB", deckNameB);
        return game;
    }

    @Test
    public void test_EveryPlayerGetsEmblem() {
        checkEmblemCount("A", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Emblem Mental Magic", 1);
        checkEmblemCount("B", 1, PhaseStep.PRECOMBAT_MAIN, playerB, "Emblem Mental Magic", 1);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
    }

    @Test
    public void test_PlayCardAsLand() {
        addCard(Zone.HAND, playerA, "Lightning Bolt@bolt");
        addCard(Zone.HAND, playerA, "Shock", 1); // {R}
        addCard(Zone.HAND, playerA, "Mountain", 1);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "@bolt " + PLAY_AS_LAND);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkPermanentCount("land", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Mental Land", 1);

        // it's the land for the turn
        checkPlayableAbility("no more lands", 1, PhaseStep.PRECOMBAT_MAIN, playerA, PLAY_AS_LAND, false);
        checkPlayableAbility("no more lands", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Play Mountain", false);

        // taps for any color
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Shock", playerB);
        setChoice(playerA, "Red"); // mana color

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Mental Land", 1);
        assertHandCount(playerA, "Lightning Bolt", 0);
        assertExileCount(playerA, 1); // face down
        assertLife(playerB, 20 - 2);
    }

    @Test
    public void test_PlayCardAsLand_OnlyAtSorcerySpeed() {
        addCard(Zone.HAND, playerA, "Lightning Bolt");

        checkPlayableAbility("main phase", 1, PhaseStep.PRECOMBAT_MAIN, playerA, PLAY_AS_LAND, true);
        checkPlayableAbility("combat", 1, PhaseStep.BEGIN_COMBAT, playerA, PLAY_AS_LAND, false);
        checkPlayableAbility("opponent's turn", 2, PhaseStep.PRECOMBAT_MAIN, playerA, PLAY_AS_LAND, false);

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.END_TURN);
        execute();
    }

    @Test
    public void test_TurnIntoCardWithSameManaCost() {
        addCard(Zone.HAND, playerA, "Shock", 1); // {R}
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, TURN_INTO);
        setChoice(playerA, "Lightning Bolt"); // {R}
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkHandCardCount("transformed", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", playerB);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertHandCount(playerA, "Shock", 0);
        assertExileCount(playerA, 1); // face down
        assertGraveyardCount(playerA, "Lightning Bolt", 1);
        assertLife(playerB, 20 - 3);
    }

    @Test
    public void test_TurnInto_AtInstantSpeed() {
        addCard(Zone.HAND, playerA, "Shock", 1); // {R}

        checkPlayableAbility("opponent's turn", 2, PhaseStep.PRECOMBAT_MAIN, playerA, TURN_INTO, true);
        activateAbility(2, PhaseStep.PRECOMBAT_MAIN, playerA, TURN_INTO);
        setChoice(playerA, "Lightning Bolt");

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.END_TURN);
        execute();

        assertHandCount(playerA, "Lightning Bolt", 1);
    }

    @Test
    public void test_NameCanBeCastOnlyOnce() {
        addCard(Zone.HAND, playerA, "Lightning Bolt", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);
        addCard(Zone.HAND, playerB, "Lightning Bolt", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", playerB);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkPlayableAbility("same player", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Cast Lightning Bolt", false);
        checkPlayableAbility("other player", 2, PhaseStep.PRECOMBAT_MAIN, playerB, "Cast Lightning Bolt", false);

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.END_TURN);
        execute();

        assertLife(playerB, 20 - 3);
        assertHandCount(playerA, "Lightning Bolt", 1);
        assertHandCount(playerB, "Lightning Bolt", 1);
    }

    @Test
    public void test_ChosenNameIsReservedForThatCard() {
        addCard(Zone.HAND, playerA, "Shock", 1); // {R}
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);
        addCard(Zone.HAND, playerB, "Lightning Bolt", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 1);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, TURN_INTO);
        setChoice(playerA, "Lightning Bolt");

        // B's own Lightning Bolt can't be cast anymore, A's new one can
        checkPlayableAbility("reserved", 2, PhaseStep.PRECOMBAT_MAIN, playerB, "Cast Lightning Bolt", false);
        checkPlayableAbility("owner", 3, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Lightning Bolt", true);

        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.END_TURN);
        execute();
    }

    @Test
    public void test_CastNameCanNotBeChosen() {
        addCard(Zone.HAND, playerA, "Lightning Bolt", 1); // {R}
        addCard(Zone.HAND, playerA, "Shock", 1); // {R}
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);

        runCode("before cast", 1, PhaseStep.PRECOMBAT_MAIN, playerA, (info, player, game) -> {
            Assert.assertFalse(game.getState().getWatcher(MentalMagicNamesWatcher.class).isTaken("Lightning Bolt"));
        });
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", playerB);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        runCode("after cast", 1, PhaseStep.PRECOMBAT_MAIN, playerA, (info, player, game) -> {
            Assert.assertTrue(game.getState().getWatcher(MentalMagicNamesWatcher.class).isTaken("Lightning Bolt"));
        });

        activateAbility(1, PhaseStep.POSTCOMBAT_MAIN, playerA, TURN_INTO);
        setChoice(playerA, "Burst Lightning"); // {R}, still free

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertHandCount(playerA, "Burst Lightning", 1);
        assertLife(playerB, 20 - 3);
    }
}
