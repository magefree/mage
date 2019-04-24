package mage.abilities.effects.common.counter;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.constants.Outcome;
import mage.counters.Counter;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.util.CardUtil;

import java.util.Locale;
import java.util.UUID;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class AddCountersTargetEffect extends OneShotEffect {

    private Counter counter;
    private DynamicValue amount;

    public AddCountersTargetEffect(Counter counter) {
        this(counter, counter.getName().equals(CounterType.M1M1.getName()) ? Outcome.UnboostCreature : Outcome.Benefit);
    }

    public AddCountersTargetEffect(Counter counter, DynamicValue amount) {
        this(counter, amount, Outcome.Benefit);
    }

    public AddCountersTargetEffect(Counter counter, Outcome outcome) {
        this(counter, new StaticValue(0), outcome);
    }

    public AddCountersTargetEffect(Counter counter, DynamicValue amount, Outcome outcome) {
        super(outcome);
        this.counter = counter;
        this.amount = amount;
    }

    public AddCountersTargetEffect(final AddCountersTargetEffect effect) {
        super(effect);
        if (effect.counter != null) {
            this.counter = effect.counter.copy();
        }
        this.amount = effect.amount;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = game.getObject(source.getSourceId());
        if (controller != null && sourceObject != null && counter != null) {
            int affectedTargets = 0;
            for (UUID uuid : targetPointer.getTargets(game, source)) {
                Permanent permanent = game.getPermanent(uuid);
                Player player = game.getPlayer(uuid);
                Card card = game.getCard(targetPointer.getFirst(game, source));
                if (permanent != null) {
                    Counter newCounter = counter.copy();
                    int calculated = amount.calculate(game, source, this);
                    if (calculated > 0 && newCounter.getCount() > 0) {
                        newCounter.remove(newCounter.getCount());
                    }
                    newCounter.add(calculated);
                    int before = permanent.getCounters(game).getCount(counter.getName());
                    permanent.addCounters(newCounter, source, game);
                    int numberAdded = permanent.getCounters(game).getCount(counter.getName()) - before;
                    affectedTargets++;
                    if (!game.isSimulation()) {
                        game.informPlayers(sourceObject.getLogName() + ": " + controller.getLogName() + " puts "
                                + numberAdded + ' ' + counter.getName().toLowerCase(Locale.ENGLISH) + " counter on " + permanent.getLogName());
                    }
                } else if (player != null) {
                    Counter newCounter = counter.copy();
                    newCounter.add(amount.calculate(game, source, this));
                    player.addCounters(newCounter, game);
                    affectedTargets++;
                    if (!game.isSimulation()) {
                        game.informPlayers(sourceObject.getLogName() + ": " + controller.getLogName() + " puts "
                                + counter.getCount() + ' ' + counter.getName().toLowerCase(Locale.ENGLISH) + " counter on " + player.getLogName());
                    }
                } else if (card != null) {
                    card.addCounters(counter, source, game);
                    if (!game.isSimulation()) {
                        game.informPlayers(new StringBuilder("Added ").append(counter.getCount()).append(' ').append(counter.getName())
                                .append(" counter to ").append(card.getName())
                                .append(" (").append(card.getCounters(game).getCount(counter.getName())).append(')').toString());
                    }
                    return true;
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public String getText(Mode mode) {
        if (!staticText.isEmpty()) {
            return staticText;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("put ");
        if (counter.getCount() > 1) {
            sb.append(CardUtil.numberToText(counter.getCount())).append(' ');
        } else {
            sb.append("a ");
        }
        sb.append(counter.getName().toLowerCase(Locale.ENGLISH)).append(" counter");
        if (counter.getCount() > 1) {
            sb.append('s');
        }
        sb.append(" on ");

        Target target = mode.getTargets().getEffectTarget(this.targetPointer);
        if (target != null) {
            if (target.getNumberOfTargets() == 0) {
                sb.append("up to ");
            }

            if (target.getMaxNumberOfTargets() > 1 || target.getNumberOfTargets() == 0) {
                sb.append(CardUtil.numberToText(target.getMaxNumberOfTargets()))
                        .append(" target ").append(target.getTargetName());
            } else {
                if (!target.getTargetName().startsWith("another")) {
                    sb.append("target ");
                }
                sb.append(target.getTargetName());
            }
        } else {
            sb.append("that creature");
        }

        if (!amount.getMessage().isEmpty()) {
            sb.append(" for each ").append(amount.getMessage());
        }
        return sb.toString();
    }

    @Override
    public AddCountersTargetEffect copy() {
        return new AddCountersTargetEffect(this);
    }

}
