package org.mage.test.serverside.base;

import mage.constants.MultiplayerAttackOption;
import mage.constants.RangeOfInfluence;
import mage.game.Game;
import mage.game.GameException;
import mage.game.TwoPlayerDuel;
import mage.game.mulligan.MulliganType;
import org.mage.test.player.TestComputerPlayer7;
import org.mage.test.player.TestPlayer;
import org.mage.test.serverside.base.impl.CardTestPlayerAPIImpl;

import java.io.FileNotFoundException;

/**
 * PlayerA is full AI player and process all actions as AI logic. You don't need aiXXX commands in that tests.
 *
 * If you need custom AI tests then use CardTestPlayerBaseWithAIHelps with aiXXX commands
 *
 * @author LevelX2
 */
public abstract class CardTestPlayerBaseAI extends CardTestPlayerAPIImpl {

    int skill = 6;

    @Override
    protected Game createNewGameAndPlayers() throws GameException, FileNotFoundException {
        Game game = new TwoPlayerDuel(MultiplayerAttackOption.LEFT, RangeOfInfluence.ONE, MulliganType.GAME_DEFAULT.getMulligan(0), 20);

        playerA = createPlayer(game, "PlayerA");
        playerB = createPlayer(game, "PlayerB");
        return game;
    }

    @Override
    protected TestPlayer createPlayer(String name, RangeOfInfluence rangeOfInfluence) {
        if (name.equals("PlayerA")) {
            TestPlayer testPlayer = new TestPlayer(new TestComputerPlayer7("PlayerA", RangeOfInfluence.ONE, skill));
            testPlayer.setAIPlayer(true);
            testPlayer.setAIRealGameSimulation(true); // enable AI logic simulation for all turns by default
            return testPlayer;
        }
        return super.createPlayer(name, rangeOfInfluence);
    }
}
