package mage.abilities.costs.common;

import mage.abilities.Ability;
import mage.abilities.costs.Cost;
import mage.abilities.costs.CostImpl;
import mage.choices.Choice;
import mage.choices.ChoiceImpl;
import mage.constants.Outcome;
import mage.counters.Counter;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.util.CardUtil;
import mage.util.RandomUtil;

import java.util.Iterator;
import java.util.Set;
import java.util.UUID;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class RemoveCountersSourceCost extends CostImpl {

    private final int amount;
    private final String name;

    public RemoveCountersSourceCost() {
        this((Counter) null);
    }

    public RemoveCountersSourceCost(Counter counter) {
        this.amount = counter != null ? counter.getCount() : 1;
        this.name = counter != null ? counter.getName() : "";
        this.text = new StringBuilder("remove ")
                .append((amount == 1 ? CounterType.findArticle(name) : CardUtil.numberToText(amount)))
                .append(name.isEmpty() ? "" : (' ' + name))
                .append(" counter")
                .append((amount != 1 ? "s" : ""))
                .append(" from {this}").toString();

    }

    private RemoveCountersSourceCost(RemoveCountersSourceCost cost) {
        super(cost);
        this.amount = cost.amount;
        this.name = cost.name;
    }

    @Override
    public boolean canPay(Ability ability, Ability source, UUID controllerId, Game game) {
        Permanent permanent = game.getPermanent(source.getSourceId());
        return permanent != null && permanent.getCounters(game).getCount(name) >= amount;
    }

    @Override
    public boolean pay(Ability ability, Game game, Ability source, UUID controllerId, boolean noMana, Cost costToPay) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent permanent = source.getSourcePermanentIfItStillExists(game);
        if (player == null || permanent == null) {
            return paid;
        }
        String toRemove;
        if (name.isEmpty()) {
            Set<String> toChoose = permanent.getCounters(game).keySet();
            switch (toChoose.size()) {
                case 0:
                    return paid;
                case 1:
                    toRemove = RandomUtil.randomFromCollection(toChoose);
                    break;
                case 2:
                    Iterator<String> iterator = toChoose.iterator();
                    String choice1 = iterator.next();
                    String choice2 = iterator.next();
                    toRemove = player.chooseUse(
                            Outcome.UnboostCreature, "Choose a type of counter to remove",
                            null, choice1, choice2, source, game
                    ) ? choice1 : choice2;
                    break;
                default:
                    Choice choice = new ChoiceImpl(true);
                    choice.setChoices(toChoose);
                    choice.setMessage("Choose a type of counter to remove");
                    player.choose(Outcome.UnboostCreature, choice, game);
                    toRemove = choice.getChoice();
            }
        } else {
            toRemove = name;
        }
        if (permanent.getCounters(game).getCount(toRemove) >= amount) {
            permanent.removeCounters(toRemove, amount, source, game);
            this.paid = true;
        }
        return paid;
    }

    @Override
    public RemoveCountersSourceCost copy() {
        return new RemoveCountersSourceCost(this);
    }
}
