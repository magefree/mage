package mage.abilities.effects.common.counter;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.counters.Counter;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.util.CardUtil;

/**
 * @author LevelX2
 */
public class AddCountersAttachedEffect extends OneShotEffect {

    private Counter counter;
    private DynamicValue amount;

    public AddCountersAttachedEffect(Counter counter, String textEnchanted) {
        this(counter, StaticValue.get(1), textEnchanted);
    }

    /**
     * @param counter
     * @param amount        this amount will be added to the counter instances
     * @param textEnchanted text used for the enchanted permanent in rule text
     */
    public AddCountersAttachedEffect(Counter counter, DynamicValue amount, String textEnchanted) {
        super(Outcome.Benefit);
        this.counter = counter.copy();
        this.amount = amount;
        staticText = CardUtil.getAddRemoveCountersText(amount, counter, textEnchanted, true);
    }

    public AddCountersAttachedEffect(final AddCountersAttachedEffect effect) {
        super(effect);
        if (effect.counter != null) {
            this.counter = effect.counter.copy();
        }
        this.amount = effect.amount;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (permanent != null && permanent.getAttachedTo() != null) {
            Permanent attachedTo = game.getPermanent(permanent.getAttachedTo());
            if (attachedTo != null && counter != null) {
                Counter newCounter = counter.copy();
                int countersToAdd = amount.calculate(game, source, this);
                if (countersToAdd > 0) {
                    countersToAdd--;
                    newCounter.add(countersToAdd);
                    attachedTo.addCounters(newCounter, source.getControllerId(), source, game);
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public AddCountersAttachedEffect copy() {
        return new AddCountersAttachedEffect(this);
    }
}
