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
        this(counter, StaticValue.get(0), outcome);
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
                Counter newCounter = counter.copy();
                int calculated = amount.calculate(game, source, this); // 0 -- you must use default couner
                if (calculated < 0) {
                    continue;
                } else if (calculated == 0) {
                    // use original counter
                } else {
                    // increase to calculated value
                    newCounter.remove(newCounter.getCount());
                    newCounter.add(calculated);
                }

                Permanent permanent = game.getPermanent(uuid);
                Player player = game.getPlayer(uuid);
                Card card = game.getCard(targetPointer.getFirst(game, source));
                if (permanent != null) {
                    permanent.addCounters(newCounter, source.getControllerId(), source, game);
                    affectedTargets++;
                    game.informPlayers(sourceObject.getLogName() + ": " + controller.getLogName() + " puts "
                            + newCounter.getCount() + ' ' + newCounter.getName().toLowerCase(Locale.ENGLISH) + " counters on " + permanent.getLogName());
                } else if (player != null) {
                    player.addCounters(newCounter, source.getControllerId(), source, game);
                    affectedTargets++;
                    game.informPlayers(sourceObject.getLogName() + ": " + controller.getLogName() + " puts "
                            + newCounter.getCount() + ' ' + newCounter.getName().toLowerCase(Locale.ENGLISH) + " counters on " + player.getLogName());
                } else if (card != null) {
                    card.addCounters(newCounter, source.getControllerId(), source, game);
                    affectedTargets++;
                    game.informPlayers(sourceObject.getLogName() + ": " + controller.getLogName() + " puts "
                            + newCounter.getCount() + ' ' + newCounter.getName().toLowerCase(Locale.ENGLISH) + " counters on " + card.getLogName());
                }
            }
            return affectedTargets > 0;
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
        String counterName = counter.getName().toLowerCase(Locale.ENGLISH);
        if (counter.getCount() > 1) {
            sb.append(CardUtil.numberToText(counter.getCount())).append(' ');
        } else {
            sb.append(CounterType.findArticle(counterName)).append(' ');
        }
        sb.append(counterName).append(" counter");
        if (counter.getCount() > 1) {
            sb.append('s');
        }
        sb.append(" on ");

        Target target = mode.getTargets().getEffectTarget(this.targetPointer);
        if (target != null) {
            if (target.getNumberOfTargets() == 0) {
                if (target.getMaxNumberOfTargets() > 1) {
                    sb.append("each of ");
                }
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
