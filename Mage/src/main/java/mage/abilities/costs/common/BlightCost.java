package mage.abilities.costs.common;

import mage.abilities.Ability;
import mage.abilities.costs.Cost;
import mage.abilities.costs.CostImpl;
import mage.constants.Outcome;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.permanent.CanHaveCounterAddedPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public class BlightCost extends CostImpl {

    private static final FilterPermanent filter = new FilterControlledCreaturePermanent();

    static {
        filter.add(new CanHaveCounterAddedPredicate(CounterType.M1M1));
    }

    private int amount;

    public BlightCost(int amount) {
        super();
        this.amount = amount;
        this.text = "blight " + amount;
    }

    private BlightCost(final BlightCost cost) {
        super(cost);
        this.amount = cost.amount;
    }

    @Override
    public BlightCost copy() {
        return new BlightCost(this);
    }

    @Override
    public boolean canPay(Ability ability, Ability source, UUID controllerId, Game game) {
        return canBlight(controllerId, game, source);
    }

    public static boolean canBlight(UUID controllerId, Game game, Ability source) {
        return game
                .getBattlefield()
                .contains(filter, controllerId, source, game, 1);
    }

    @Override
    public boolean pay(Ability ability, Game game, Ability source, UUID controllerId, boolean noMana, Cost costToPay) {
        Player player = game.getPlayer(controllerId);
        paid = player != null && doBlight(player, amount, game, source) != null;
        return paid;
    }

    public static Permanent doBlight(Player player, int amount, Game game, Ability source) {
        if (player == null || amount < 1 || !canBlight(player.getId(), game, source)) {
            return null;
        }
        TargetPermanent target = new TargetPermanent(filter);
        target.withNotTarget(true);
        target.withChooseHint("to put a -1/-1 counter on");
        player.choose(Outcome.UnboostCreature, target, source, game);
        Permanent permanent = game.getPermanent(target.getFirstTarget());
        if (permanent != null) {
            permanent.addCounters(CounterType.M1M1.createInstance(amount), player.getId(), source, game);
        }
        return permanent;
    }
}
