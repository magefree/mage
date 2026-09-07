package mage.abilities.effects.common.counter;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.constants.AbilityType;
import mage.constants.Outcome;
import mage.counters.Counter;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.util.CardUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class AddCountersSourceEffect extends OneShotEffect {

    private Counter counter;
    private DynamicValue amount;
    private boolean putOnCard;

    public AddCountersSourceEffect(Counter counter) {
        this(counter, StaticValue.get(0));
    }

    public AddCountersSourceEffect(Counter counter, DynamicValue amount) {
        this(counter, amount, false);
    }

    /**
     * @param counter
     * @param amount    this amount will be added to the counter instances
     * @param putOnCard - counters have to be put on a card instead of a
     *                  permanent
     */
    public AddCountersSourceEffect(Counter counter, DynamicValue amount, boolean putOnCard) {
        super(Outcome.Benefit);
        this.counter = counter.copy();
        this.amount = amount;
        this.putOnCard = putOnCard;
        staticText = CardUtil.getAddRemoveCountersText(amount, counter, "{this}", true);
    }

    protected AddCountersSourceEffect(final AddCountersSourceEffect effect) {
        super(effect);
        if (effect.counter != null) {
            this.counter = effect.counter.copy();
        }
        this.amount = effect.amount;
        this.putOnCard = effect.putOnCard;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        if (counter == null) {
            return false;
        }

        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }

        if (putOnCard) {
            Card card = game.getCard(source.getSourceId());
            if (card == null) {
                return false;
            }

            Counter newCounter = counter.copy();
            int countersToAdd = amount.calculate(game, source, this);
            if (countersToAdd > 0 && newCounter.getCount() == 1) {
                countersToAdd--;
            }
            newCounter.add(countersToAdd);
            List<UUID> appliedEffects = (ArrayList<UUID>) this.getValue("appliedEffects");
            card.addCounters(newCounter, source.getControllerId(), source, game, appliedEffects);
            return true;
        } else {
            Permanent permanent = game.getPermanent(source.getSourceId());
            if (permanent == null && source.getAbilityType() == AbilityType.STATIC) {
                permanent = game.getPermanentEntering(source.getSourceId());
            }
            if (permanent == null) {
                return false;
            }

            if ((source.getStackMomentSourceZCC() == 0 // from static ability
                    || source.getStackMomentSourceZCC() == permanent.getZoneChangeCounter(game))) { // prevent to add counters to later source objects
                Counter newCounter = counter.copy();
                int countersToAdd = amount.calculate(game, source, this);
                if (amount instanceof StaticValue || countersToAdd > 0) {
                    if (countersToAdd > 0 && newCounter.getCount() == 1) {
                        countersToAdd--;
                    }
                    newCounter.add(countersToAdd);
                    List<UUID> appliedEffects = (ArrayList<UUID>) this.getValue("appliedEffects");
                    permanent.addCounters(newCounter, source.getControllerId(), source, game, appliedEffects); // if used from a replacement effect, the basic event determines if an effect was already applied to an event
                }
            }
        }
        return true;
    }

    @Override
    public AddCountersSourceEffect copy() {
        return new AddCountersSourceEffect(this);
    }
}
