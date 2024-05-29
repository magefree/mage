package org.mage.test.serverside.base;

import mage.constants.MultiplayerAttackOption;
import mage.constants.RangeOfInfluence;
import mage.game.Game;
import mage.game.GameException;
import mage.game.TwoPlayerDuel;
import mage.game.mulligan.MulliganType;
import mage.players.Player;
import org.mage.test.player.TestComputerPlayer7;
import org.mage.test.player.TestPlayer;
import org.mage.test.serverside.base.impl.CardTestPlayerAPIImpl;

import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * PlayerA is full AI player and process all actions as AI logic. You don't need aiXXX commands in that tests.
 * <p>
 * If you need simple AI tests for single command/priority then use CardTestPlayerBaseWithAIHelps with aiXXX commands
 * If you need full AI tests with game simulations then use current CardTestPlayerBaseAI
 * <p>
 * Only PlayerA ai-controlled by default. Use getFullSimulatedPlayers for additional AI players, e.g. AI vs AI tests.
 *
 * @author LevelX2, JayDi85
 */
public abstract class CardTestPlayerBaseAI extends CardTestPlayerAPIImpl {

    /**
     * Allow to change AI skill level
     */
    public int getSkillLevel() {
        return 6;
    }

    /**
     * Allow to change full simulates players (default is PlayerA)
     *
     * @return
     */
    public List<String> getFullSimulatedPlayers() {
        return Arrays.asList("PlayerA");
    }


    @Override
    protected Game createNewGameAndPlayers() throws GameException, FileNotFoundException {
        Game game = new TwoPlayerDuel(MultiplayerAttackOption.LEFT, RangeOfInfluence.ONE, MulliganType.GAME_DEFAULT.getMulligan(0), 60, 20, 7);

        playerA = createPlayer(game, "PlayerA");
        playerB = createPlayer(game, "PlayerB");
        return game;
    }

    @Override
    protected TestPlayer createPlayer(String name, RangeOfInfluence rangeOfInfluence) {
        if (getFullSimulatedPlayers().contains(name)) {
            TestPlayer testPlayer = new TestPlayer(new TestComputerPlayer7(name, RangeOfInfluence.ONE, getSkillLevel()));
            testPlayer.setAIPlayer(true);
            testPlayer.setAIRealGameSimulation(true); // enable full AI support (game simulations) for all turns by default
            return testPlayer;
        }
        return super.createPlayer(name, rangeOfInfluence);
    }
}
