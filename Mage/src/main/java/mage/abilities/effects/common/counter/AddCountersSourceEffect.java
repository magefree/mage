package mage.abilities.effects.common.counter;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.constants.AbilityType;
import mage.constants.Outcome;
import mage.counters.Counter;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.util.CardUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class AddCountersSourceEffect extends OneShotEffect {

    private Counter counter;
    private boolean informPlayers;
    private DynamicValue amount;
    private boolean putOnCard;

    public AddCountersSourceEffect(Counter counter) {
        this(counter, false);
    }

    public AddCountersSourceEffect(Counter counter, boolean informPlayers) {
        this(counter, StaticValue.get(0), informPlayers);
    }

    public AddCountersSourceEffect(Counter counter, DynamicValue amount, boolean informPlayers) {
        this(counter, amount, informPlayers, false);
    }

    /**
     * @param counter
     * @param amount        this amount will be added to the counter instances
     * @param informPlayers
     * @param putOnCard     - counters have to be put on a card instead of a
     *                      permanent
     */
    public AddCountersSourceEffect(Counter counter, DynamicValue amount, boolean informPlayers, boolean putOnCard) {
        super(Outcome.Benefit);
        this.counter = counter.copy();
        this.informPlayers = informPlayers;
        this.amount = amount;
        this.putOnCard = putOnCard;
        setText();
    }

    public AddCountersSourceEffect(final AddCountersSourceEffect effect) {
        super(effect);
        if (effect.counter != null) {
            this.counter = effect.counter.copy();
        }
        this.informPlayers = effect.informPlayers;
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
            if (informPlayers && !game.isSimulation()) {
                Player player = game.getPlayer(source.getControllerId());
                if (player != null) {
                    game.informPlayers(player.getLogName() + " puts " + newCounter.getCount() + ' ' + newCounter.getName().toLowerCase(Locale.ENGLISH) + " counter on " + card.getLogName());
                }
            }
            return true;
        } else {
            Permanent permanent = game.getPermanent(source.getSourceId());
            if (permanent == null && source.getAbilityType() == AbilityType.STATIC) {
                permanent = game.getPermanentEntering(source.getSourceId());
            }
            if (permanent == null) {
                return false;
            }

            if ((source.getSourceObjectZoneChangeCounter() == 0 // from static ability
                    || source.getSourceObjectZoneChangeCounter() == permanent.getZoneChangeCounter(game))) { // prevent to add counters to later source objects
                Counter newCounter = counter.copy();
                int countersToAdd = amount.calculate(game, source, this);
                if (amount instanceof StaticValue || countersToAdd > 0) {
                    if (countersToAdd > 0 && newCounter.getCount() == 1) {
                        countersToAdd--;
                    }
                    newCounter.add(countersToAdd);
                    int before = permanent.getCounters(game).getCount(newCounter.getName());
                    List<UUID> appliedEffects = (ArrayList<UUID>) this.getValue("appliedEffects");
                    permanent.addCounters(newCounter, source.getControllerId(), source, game, appliedEffects); // if used from a replacement effect, the basic event determines if an effect was already applied to an event
                    if (informPlayers && !game.isSimulation()) {
                        int amountAdded = permanent.getCounters(game).getCount(newCounter.getName()) - before;
                        Player player = game.getPlayer(source.getControllerId());
                        if (player != null) {
                            game.informPlayers(player.getLogName() + " puts " + amountAdded + ' ' + newCounter.getName().toLowerCase(Locale.ENGLISH) + " counter on " + permanent.getLogName());
                        }
                    }
                }
            }
        }
        return true;
    }

    private void setText() {
        StringBuilder sb = new StringBuilder();
        sb.append("put ");
        boolean plural = true;
        if (counter.getCount() > 1) {
            sb.append(CardUtil.numberToText(counter.getCount())).append(' ');
        } else if (amount.toString().equals("X") && amount.getMessage().isEmpty()) {
            sb.append("X ");
        } else {
            sb.append(CounterType.findArticle(counter.getName())).append(' ');
            plural = false;
        }
        sb.append(counter.getName().toLowerCase(Locale.ENGLISH)).append(" counter");
        if (plural) {
            sb.append('s');
        }
        sb.append(" on {this}");
        if (!amount.getMessage().isEmpty()) {
            sb.append(" for each ").append(amount.getMessage());
        }
        staticText = sb.toString();
    }

    @Override
    public AddCountersSourceEffect copy() {
        return new AddCountersSourceEffect(this);
    }

}
