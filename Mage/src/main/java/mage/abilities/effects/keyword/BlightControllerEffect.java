package mage.abilities.effects.keyword;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 * @author TheElk801
 */
public class BlightControllerEffect extends OneShotEffect {

    private final int amount;

    public BlightControllerEffect(int amount) {
        super(Outcome.Detriment);
        this.amount = amount;
        staticText = "blight " + amount;
    }

    private BlightControllerEffect(final BlightControllerEffect effect) {
        super(effect);
        this.amount = effect.amount;
    }

    @Override
    public BlightControllerEffect copy() {
        return new BlightControllerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        return doBlight(player, amount, game, source) != null;
    }

    public static Permanent doBlight(Player player, int amount, Game game, Ability source) {
        if (player == null || amount < 1 || !game.getBattlefield().contains(
                StaticFilters.FILTER_CONTROLLED_CREATURE, player.getId(), source, game, 1
        )) {
            return null;
        }
        TargetPermanent target = new TargetControlledCreaturePermanent();
        target.withNotTarget(true);
        target.withChooseHint("to put a -1/-1 counter on");
        player.choose(Outcome.UnboostCreature, target, source, game);
        Permanent permanent = game.getPermanent(target.getFirstTarget());
        if (permanent != null) {
            permanent.addCounters(CounterType.M1M1.createInstance(amount), source, game);
        }
        return permanent;
    }
}
