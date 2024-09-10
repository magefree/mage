

package mage.abilities.effects.common.counter;

import mage.constants.Outcome;
import mage.constants.Zone;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.util.CardUtil;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class AddPlusOneCountersAttachedEffect extends OneShotEffect {

    private int amount;

    public AddPlusOneCountersAttachedEffect(int amount) {
        super(Outcome.BoostCreature);
        this.amount = amount;
        setText();
    }

    protected AddPlusOneCountersAttachedEffect(final AddPlusOneCountersAttachedEffect effect) {
        super(effect);
        this.amount = effect.amount;
    }

    @Override
    public AddPlusOneCountersAttachedEffect copy() {
        return new AddPlusOneCountersAttachedEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent enchantment = game.getPermanent(source.getSourceId());
        if (enchantment == null) {
            enchantment = (Permanent) game.getLastKnownInformation(source.getSourceId(), Zone.BATTLEFIELD);
        }
        if (enchantment != null && enchantment.getAttachedTo() != null) {
            Permanent creature = game.getPermanent(enchantment.getAttachedTo());
            if (creature != null) {
                creature.addCounters(CounterType.P1P1.createInstance(amount), source.getControllerId(), source, game);
            }
        }
        return true;
    }

    private void setText() {
        if (amount > 1) {
            staticText = new StringBuilder("put ").append(CardUtil.numberToText(amount)).append(" +1/+1 counters on enchanted creature").toString();
        } else {
            staticText = "put a +1/+1 counter on enchanted creature";
        }
    }

}
