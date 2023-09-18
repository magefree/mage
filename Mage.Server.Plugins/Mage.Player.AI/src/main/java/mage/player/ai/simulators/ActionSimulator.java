

package mage.player.ai.simulators;

import mage.abilities.ActivatedAbility;
import mage.cards.Card;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.player.ai.ComputerPlayer;
import mage.player.ai.PermanentEvaluator;
import mage.players.Player;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class ActionSimulator {

    private ComputerPlayer player;
    private List<Card> playableInstants = new ArrayList<>();
    private List<ActivatedAbility> playableAbilities = new ArrayList<>();

    private Game game;

    public ActionSimulator(ComputerPlayer player) {
        this.player = player;
    }

    public void simulate(Game game) {

    }

    public int evaluateState() {
        Player opponent = game.getPlayer(game.getOpponents(player.getId()).stream().findFirst().orElse(null));
        if (opponent == null) {
            return Integer.MAX_VALUE;
        }

        if (game.checkIfGameIsOver()) {
            if (player.hasLost() || opponent.hasWon()) {
                return Integer.MIN_VALUE;
            }
            if (opponent.hasLost() || player.hasWon()) {
                return Integer.MAX_VALUE;
            }
        }
        int value = player.getLife();
        value -= opponent.getLife();
        PermanentEvaluator evaluator = new PermanentEvaluator();
        for (Permanent permanent: game.getBattlefield().getAllActivePermanents(player.getId())) {
            value += evaluator.evaluate(permanent, game);
        }
        for (Permanent permanent: game.getBattlefield().getAllActivePermanents(player.getId())) {
            value -= evaluator.evaluate(permanent, game);
        }
        value += player.getHand().size();
        value -= opponent.getHand().size();
        return value;
    }

}
