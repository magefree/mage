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
 * @author noahg
 */
public class RemoveCountersAttachedEffect extends OneShotEffect {

    private Counter counter;
    private DynamicValue amount;

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
        this.staticText = CardUtil.getAddRemoveCountersText(amount, counter, textEnchanted, false);
    }

    public RemoveCountersAttachedEffect(final RemoveCountersAttachedEffect effect) {
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
                newCounter.add(amount.calculate(game, source, this));
                attachedTo.removeCounters(newCounter, source, game);
            }
            return true;
        }
        return false;
    }

    @Override
    public RemoveCountersAttachedEffect copy() {
        return new RemoveCountersAttachedEffect(this);
    }
}
