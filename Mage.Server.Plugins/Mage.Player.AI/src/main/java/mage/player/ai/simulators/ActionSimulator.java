

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
import java.util.Objects;

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

    @Override
    public int hashCode() {
        return 0; // TODO
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        ActionSimulator that = (ActionSimulator) o;

        if (!Objects.equals(this.player, that.player)) {
            return false;
        }
        if (!Objects.equals(this.game, that.game)) {
            return false;
        }
        if (!Objects.deepEquals(this.playableInstants, that.playableInstants)) {
            return false;
        }

        return Objects.deepEquals(this.playableAbilities, that.playableAbilities);
    }
}
