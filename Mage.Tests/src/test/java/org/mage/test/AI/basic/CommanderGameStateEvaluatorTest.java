package org.mage.test.AI.basic;

import mage.constants.PhaseStep;
import mage.player.ai.score.GameStateEvaluator2;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestCommander4Players;

/**
 * @author TheElk801
 */
public class CommanderGameStateEvaluatorTest extends CardTestCommander4Players {

    @Test
    public void testOneDefeatedOpponentDoesNotScoreAsGameWin() {
        setLife(playerA, 20);
        setLife(playerB, 0);
        setLife(playerC, 20);
        setLife(playerD, 20);

        runCode("evaluate multiplayer score", 1, PhaseStep.PRECOMBAT_MAIN, playerA, (info, player, game) -> {
            GameStateEvaluator2.PlayerEvaluateScore score = GameStateEvaluator2.evaluate(player.getId(), game);
            Assert.assertNotEquals(
                    "A single defeated opponent in Commander FFA must not score as winning the whole game",
                    GameStateEvaluator2.WIN_GAME_SCORE,
                    score.getTotalScore()
            );
            Assert.assertTrue(
                    "Remaining live opponents should still contribute to opponent life score",
                    score.getOpponentLifeScore() > 0
            );
        });

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
    }
}
