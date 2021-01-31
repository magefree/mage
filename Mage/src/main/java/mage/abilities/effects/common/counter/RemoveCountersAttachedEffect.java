
package mage.abilities.effects.common.counter;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.counters.Counter;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.util.CardUtil;

import java.util.Locale;

/**
 * @author noahg
 */
public class RemoveCountersAttachedEffect extends OneShotEffect {

    private Counter counter;
    private DynamicValue amount;
    private String textEnchanted;

    public RemoveCountersAttachedEffect(Counter counter, String textEnchanted) {
        this(counter, StaticValue.get(0), textEnchanted);
    }

    /**
     * @param counter
     * @param amount        this amount will be added to the counter instances
     * @param textEnchanted text used for the enchanted permanent in rule text
     */
    public RemoveCountersAttachedEffect(Counter counter, DynamicValue amount, String textEnchanted) {
        super(Outcome.UnboostCreature);
        this.counter = counter.copy();
        this.amount = amount;
        this.textEnchanted = textEnchanted;
        setText();
    }

    public RemoveCountersAttachedEffect(final RemoveCountersAttachedEffect effect) {
        super(effect);
        if (effect.counter != null) {
            this.counter = effect.counter.copy();
        }
        this.amount = effect.amount;
        this.textEnchanted = effect.textEnchanted;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (permanent != null && permanent.getAttachedTo() != null) {
            Permanent attachedTo = game.getPermanent(permanent.getAttachedTo());
            if (attachedTo != null && counter != null) {
                Counter newCounter = counter.copy();
                newCounter.add(amount.calculate(game, source, this));
                attachedTo.removeCounters(newCounter, source, game);
            }
            return true;
        }
        return false;
    }

    private void setText() {
        StringBuilder sb = new StringBuilder();
        // put a +1/+1 counter on it
        sb.append("remove ");
        if (counter.getCount() > 1) {
            sb.append(CardUtil.numberToText(counter.getCount())).append(' ');
            sb.append(counter.getName().toLowerCase(Locale.ENGLISH)).append(" counters from ");
        } else {
            sb.append(CounterType.findArticle(counter.getName())).append(' ');
            sb.append(counter.getName().toLowerCase(Locale.ENGLISH)).append(" counter from ");
        }
        sb.append(textEnchanted);
        if (!amount.getMessage().isEmpty()) {
            sb.append(" for each ").append(amount.getMessage());
        }
        staticText = sb.toString();
    }

    @Override
    public RemoveCountersAttachedEffect copy() {
        return new RemoveCountersAttachedEffect(this);
    }

}
