package mage.abilities.effects.common.counter;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.counters.Counter;
import mage.counters.CounterType;
import mage.filter.common.FilterPermanentOrPlayer;
import mage.filter.common.FilterPermanentOrPlayerWithCounter;
import mage.game.Game;
import mage.game.events.GameEvent;
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

    private static final FilterPermanentOrPlayer filter = new FilterPermanentOrPlayerWithCounter();

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
        if (controller == null) {
            return false;
        }
        GameEvent event = new GameEvent(GameEvent.EventType.PROLIFERATE, getId(), source, source.getControllerId(), 1, true);
        if (game.replaceEvent(event)) {
            return false;
        }
        for (int i = 0; i < event.getAmount(); i++) {
            Target target = new TargetPermanentOrPlayer(0, Integer.MAX_VALUE, filter, true);
            Map<String, Serializable> options = new HashMap<>();
            options.put("UI.right.btn.text", "Done");
            controller.choose(Outcome.Benefit, target, source, game, options);
            for (UUID chosen : target.getTargets()) {
                Permanent permanent = game.getPermanent(chosen);
                if (permanent != null) {
                    for (Counter counter : permanent.getCounters(game).values()) {
                        Counter newCounter = CounterType.findByName(counter.getName()).createInstance();
                        if (permanent.addCounters(newCounter, source.getControllerId(), source, game)) {
                            game.informPlayers(permanent.getName() + " had " + newCounter.getDescription() + " added to it.");
                        }
                    }
                    continue;
                }
                Player player = game.getPlayer(chosen);
                if (player == null) {
                    continue;
                }
                for (Counter counter : player.getCounters().values()) {
                    Counter newCounter = CounterType.findByName(counter.getName()).createInstance();
                    if (player.addCounters(newCounter, source.getControllerId(), source, game)) {
                        game.informPlayers(player.getLogName() + " had " + newCounter.getDescription() + " added to them.");
                    }
                }
            }
            game.fireEvent(GameEvent.getEvent(
                    GameEvent.EventType.PROLIFERATED,
                    controller.getId(), source, controller.getId()
            ));
        }
        return true;
    }

    @Override
    public ProliferateEffect copy() {
        return new ProliferateEffect(this);
    }

}
