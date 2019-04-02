
package mage.abilities.effects.common.counter;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.choices.Choice;
import mage.choices.ChoiceImpl;
import mage.constants.Outcome;
import mage.counters.Counter;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetPermanentOrPlayerWithCounter;

/**
 * @author nantuko
 */
public class ProliferateEffect extends OneShotEffect {

    public ProliferateEffect() {
        super(Outcome.Benefit);
        staticText = "Proliferate. <i>(You choose any number of permanents and/or players with counters on them, then give each another counter of each kind already there.)</i>";
    }

    public ProliferateEffect(ProliferateEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        Target target = new TargetPermanentOrPlayerWithCounter(0, Integer.MAX_VALUE, true);
        Map<String, Serializable> options = new HashMap<>();
        options.put("UI.right.btn.text", "Done");
        controller.choose(Outcome.Benefit, target, source.getSourceId(), game, options);

        for (UUID chosen : target.getTargets()) {
            Permanent permanent = game.getPermanent(chosen);
            if (permanent != null) {
                if (!permanent.getCounters(game).isEmpty()) {
                    for (Counter counter : permanent.getCounters(game).values()) {
                        Counter newCounter = new Counter(counter.getName());
                        permanent.addCounters(newCounter, source, game);
                    }
                }
            } else {
                Player player = game.getPlayer(chosen);
                if (player != null) {
                    if (!player.getCounters().isEmpty()) {
                        for (Counter counter : player.getCounters().values()) {
                            Counter newCounter = new Counter(counter.getName());
                            player.addCounters(newCounter, game);
                        }
                    }
                }
            }
        }
        return true;
    }

    @Override
    public ProliferateEffect copy() {
        return new ProliferateEffect(this);
    }

}
