
package org.mage.test.serverside.base;

import java.io.FileNotFoundException;
import mage.constants.MultiplayerAttackOption;
import mage.constants.RangeOfInfluence;
import mage.game.Game;
import mage.game.GameException;
import mage.game.TwoPlayerDuel;
import mage.player.ai.ComputerPlayer7;
import org.mage.test.player.TestPlayer;
import org.mage.test.serverside.base.impl.CardTestPlayerAPIImpl;

/**
 *
 * @author LevelX2
 */
public abstract class CardTestPlayerBaseAI extends CardTestPlayerAPIImpl {

    int skill = 6;

    @Override
    protected Game createNewGameAndPlayers() throws GameException, FileNotFoundException {
        Game game = new TwoPlayerDuel(MultiplayerAttackOption.LEFT, RangeOfInfluence.ONE, 0, 20);

        playerA = createPlayer(game, playerA, "PlayerA");
        playerB = createPlayer(game, playerB, "PlayerB");
        return game;
    }

    @Override
    protected TestPlayer createPlayer(String name, RangeOfInfluence rangeOfInfluence) {
        if (name.equals("PlayerA")) {
            TestPlayer testPlayer = new TestPlayer(new ComputerPlayer7("PlayerA", RangeOfInfluence.ONE, skill));
            testPlayer.setAIPlayer(true);
            return testPlayer;
        }
        return super.createPlayer(name, rangeOfInfluence);
    }

    public void setAISkill(int skill) {
        this.skill = skill;
    }
}
