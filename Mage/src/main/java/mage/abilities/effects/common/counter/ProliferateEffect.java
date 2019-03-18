
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
        staticText = "Proliferate. <i>(You choose any number of permanents and/or players with counters on them, then give each another counter of a kind already there.)</i>";
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
                    if (permanent.getCounters(game).size() == 1) {
                        for (Counter counter : permanent.getCounters(game).values()) {
                            Counter newCounter = new Counter(counter.getName());
                            permanent.addCounters(newCounter, source, game);
                        }
                    } else {
                        Choice choice = new ChoiceImpl(true);
                        Set<String> choices = new HashSet<>();
                        for (Counter counter : permanent.getCounters(game).values()) {
                            choices.add(counter.getName());
                        }
                        choice.setChoices(choices);
                        choice.setMessage("Choose a counter to proliferate (" + permanent.getIdName() + ')');
                        if (controller.choose(Outcome.Benefit, choice, game)) {
                            for (Counter counter : permanent.getCounters(game).values()) {
                                if (counter.getName().equals(choice.getChoice())) {
                                    Counter newCounter = new Counter(counter.getName());
                                    permanent.addCounters(newCounter, source, game);
                                    break;
                                }
                            }
                        } else {
                            return false;
                        }
                    }
                }
            } else {
                Player player = game.getPlayer(chosen);
                if (player != null) {
                    if (!player.getCounters().isEmpty()) {
                        if (player.getCounters().size() == 1) {
                            for (Counter counter : player.getCounters().values()) {
                                Counter newCounter = new Counter(counter.getName());
                                player.addCounters(newCounter, game);
                            }
                        } else {
                            Choice choice = new ChoiceImpl(true);
                            Set<String> choices = new HashSet<>();
                            for (Counter counter : player.getCounters().values()) {
                                choices.add(counter.getName());
                            }
                            choice.setChoices(choices);
                            choice.setMessage("Choose a counter to proliferate (" + player.getLogName() + ')');
                            if (controller.choose(Outcome.Benefit, choice, game)) {
                                for (Counter counter : player.getCounters().values()) {
                                    if (counter.getName().equals(choice.getChoice())) {
                                        Counter newCounter = new Counter(counter.getName());
                                        player.addCounters(newCounter, game);
                                        break;
                                    }
                                }
                            } else {
                                return false;
                            }
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
