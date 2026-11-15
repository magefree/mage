package mage.abilities.effects.common.counter;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.choices.Choice;
import mage.choices.ChoiceImpl;
import mage.constants.Outcome;
import mage.counters.Counter;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.util.CardUtil;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

/**
 * @author LevelX2
 */
public class RemoveCounterTargetEffect extends OneShotEffect {

    private final Counter counter;
    private final DynamicValue amount; // null: as many as the counter itself names

    public RemoveCounterTargetEffect() {
        this(null, null);
    }

    public RemoveCounterTargetEffect(Counter counter) {
        this(counter, null);
    }

    public RemoveCounterTargetEffect(Counter counter, DynamicValue amount) {
        super(Outcome.UnboostCreature);
        this.counter = counter;
        this.amount = amount;
    }

    public RemoveCounterTargetEffect(RemoveCounterTargetEffect effect) {
        super(effect);
        this.counter = (effect.counter == null ? null : effect.counter.copy());
        this.amount = (effect.amount == null ? null : effect.amount.copy());
    }

    @Override
    public boolean apply(Game game, Ability source) {
        UUID targetId = getTargetPointer().getFirst(game, source);
        Permanent permanent = game.getPermanent(targetId);
        Card target = permanent != null ? permanent : game.getCard(targetId);
        if (target == null) {
            return true;
        }
        Counter toRemove = counter != null ? counter : selectCounterType(game, source, target);
        if (toRemove == null) {
            return true;
        }
        int count = amount == null ? toRemove.getCount() : amount.calculate(game, source, this);
        if (count < 1) {
            return true;
        }
        target.removeCounters(toRemove.getName(), count, source, game);
        return true;
    }

    private Counter selectCounterType(Game game, Ability source, Card object) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null && !object.getCounters(game).isEmpty()) {
            String counterName = null;
            if (object.getCounters(game).size() > 1) {
                Choice choice = new ChoiceImpl(true);
                Set<String> choices = new LinkedHashSet<>();
                for (Counter counterOnObject : object.getCounters(game).values()) {
                    if (object.getCounters(game).getCount(counterOnObject.getName()) > 0) {
                        choices.add(counterOnObject.getName());
                    }
                }
                choice.setChoices(choices);
                choice.setMessage("Choose a counter type to remove from " + object.getName());
                if (controller.choose(Outcome.Detriment, choice, game)) {
                    counterName = choice.getChoice();
                } else {
                    return null;
                }
            } else {
                for (Counter counterOnObject : object.getCounters(game).values()) {
                    if (counterOnObject.getCount() > 0) {
                        counterName = counterOnObject.getName();
                    }
                }
            }
            return new Counter(counterName);
        }
        return null;
    }

    @Override
    public RemoveCounterTargetEffect copy() {
        return new RemoveCounterTargetEffect(this);
    }

    @Override
    public String getText(Mode mode) {
        if (staticText != null && !staticText.isEmpty()) {
            return staticText;
        }
        String targets = getTargetPointer().describeTargets(mode.getTargets(), "that creature");
        return counter == null
                ? "remove a counter from " + targets
                : CardUtil.getAddRemoveCountersText(amount, counter, targets, false);
    }
}
