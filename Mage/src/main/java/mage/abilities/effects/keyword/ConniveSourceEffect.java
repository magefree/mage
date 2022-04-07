package mage.abilities.effects.keyword;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 * @author TheElk801
 */
public class ConniveSourceEffect extends OneShotEffect {

    public ConniveSourceEffect() {
        super(Outcome.Benefit);
        staticText = "{this} connives. <i>(Draw a card, then discard a card. " +
                "If you discarded a nonland card, put a +1/+1 counter on this creature.)</i>";
    }

    private ConniveSourceEffect(final ConniveSourceEffect effect) {
        super(effect);
    }

    @Override
    public ConniveSourceEffect copy() {
        return new ConniveSourceEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = source.getSourcePermanentIfItStillExists(game);
        if (permanent == null) {
            return false;
        }
        return permanent != null && connive(permanent, 1, source, game);
    }

    public static boolean connive(Permanent permanent, int amount, Ability source, Game game) {
        if (amount < 1) {
            return false;
        }
        Player player = game.getPlayer(permanent.getControllerId());
        if (player == null) {
            return false;
        }
        player.drawCards(amount, source, game);
        int counters = player
                .discard(amount, false, false, source, game)
                .count(StaticFilters.FILTER_CARDS_NON_LAND, game);
        if (counters > 0) {
            permanent.addCounters(CounterType.P1P1.createInstance(counters), source, game);
        }
        return true;
    }
}
