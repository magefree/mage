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
 * @author LevelX2
 */
public class AddCountersAttachedEffect extends OneShotEffect {

    private Counter counter;
    private DynamicValue amount;
    private String textEnchanted;

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
        this.textEnchanted = textEnchanted;
        setText();
    }

    public AddCountersAttachedEffect(final AddCountersAttachedEffect effect) {
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

    private void setText() {
        if (!staticText.isEmpty()) {
            return;
        }
        StringBuilder sb = new StringBuilder();
        // put a +1/+1 counter on it
        sb.append("put ");
        boolean plural = true;
        if (amount.toString().equals("X")) {
            sb.append("X ");
        } else if (counter.getCount() > 1) {
            sb.append(CardUtil.numberToText(counter.getCount())).append(' ');
        } else {
            sb.append(CounterType.findArticle(counter.getName())).append(' ');
            plural = false;
        }
        sb.append(counter.getName().toLowerCase(Locale.ENGLISH));
        if (plural) {
            sb.append(" counters on ");
        } else {
            sb.append(" counter on ");
        }
        sb.append(textEnchanted);
        if (!amount.getMessage().isEmpty()) {
            sb.append(" for each ").append(amount.getMessage());
        }
        staticText = sb.toString();
    }

    @Override
    public AddCountersAttachedEffect copy() {
        return new AddCountersAttachedEffect(this);
    }

}
