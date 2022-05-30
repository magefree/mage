package mage.abilities.effects.common.counter;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.counters.Counter;
import mage.filter.common.FilterPermanentOrPlayerWithCounter;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetPermanentOrPlayer;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author nantuko
 */
public class ProliferateEffect extends OneShotEffect {

    public ProliferateEffect() {
        this("", true);
    }

    public ProliferateEffect(boolean showAbilityHint) {
        this("", showAbilityHint);
    }

    public ProliferateEffect(String afterText, boolean showAbilityHint) {
        super(Outcome.Benefit);
        staticText = "proliferate" + afterText;
        if (showAbilityHint) {
            staticText += ". <i>(Choose any number of permanents and/or players, then give each another counter of each kind already there.)</i>";
        }
    }

    public ProliferateEffect(ProliferateEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Counter newCounter = null;
        if (controller == null) {
            return false;
        }
        Target target = new TargetPermanentOrPlayer(0, Integer.MAX_VALUE, new FilterPermanentOrPlayerWithCounter(), true);
        Map<String, Serializable> options = new HashMap<>();
        options.put("UI.right.btn.text", "Done");
        controller.choose(Outcome.Benefit, target, source, game, options);

        for (UUID chosen : target.getTargets()) {
            Permanent permanent = game.getPermanent(chosen);
            if (permanent != null) {
                if (!permanent.getCounters(game).isEmpty()) {
                    for (Counter counter : permanent.getCounters(game).values()) {
                        newCounter = new Counter(counter.getName());
                        permanent.addCounters(newCounter, source.getControllerId(), source, game);
                    }
                    if (newCounter != null) {
                        game.informPlayers(permanent.getName()
                                + " had 1 "
                                + newCounter.getName()
                                + " counter added to it.");
                    }
                }
            } else {
                Player player = game.getPlayer(chosen);
                if (player != null) {
                    if (!player.getCounters().isEmpty()) {
                        for (Counter counter : player.getCounters().values()) {
                            newCounter = new Counter(counter.getName());
                            player.addCounters(newCounter, source.getControllerId(), source, game);
                        }
                        if (newCounter != null) {
                            game.informPlayers(player.getLogName()
                                    + " had 1 "
                                    + newCounter.getName()
                                    + " counter added to them.");
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
