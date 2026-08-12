

package mage.player.ai;

import java.util.Comparator;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class PermanentComparator implements Comparator<Permanent> {

    private Game game;
    private PermanentEvaluator evaluator = new PermanentEvaluator();

    public PermanentComparator(Game game) {
        this.game = game;
    }

    @Override
    public int compare(Permanent o1, Permanent o2) {
        return evaluator.evaluate(o1, game) - evaluator.evaluate(o2, game);
    }

}
