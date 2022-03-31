package mage.abilities.effects.keyword;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.StaticValue;
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
public class ConniveTargetEffect extends OneShotEffect {

    private final DynamicValue xValue;

    public ConniveTargetEffect(int amount) {
        this(StaticValue.get(amount));
    }

    public ConniveTargetEffect(DynamicValue xValue) {
        super(Outcome.Benefit);
        this.xValue = xValue;
    }

    private ConniveTargetEffect(final ConniveTargetEffect effect) {
        super(effect);
        this.xValue = effect.xValue;
    }

    @Override
    public ConniveTargetEffect copy() {
        return new ConniveTargetEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (permanent == null) {
            return false;
        }
        Player player = game.getPlayer(permanent.getControllerId());
        if (player == null) {
            return false;
        }
        int amount = xValue.calculate(game, source, this);
        if (amount < 1) {
            return false;
        }
        player.drawCards(amount, source, game);
        int counters = player.discard(
                amount, false, false, source, game
        ).count(StaticFilters.FILTER_CARDS_NON_LAND, game);
        if (counters > 0) {
            permanent.addCounters(CounterType.P1P1.createInstance(counters), source, game);
        }
        return true;
    }

    @Override
    public String getText(Mode mode) {
        if (staticText != null && !staticText.isEmpty()) {
            return staticText;
        }
        StringBuilder sb = new StringBuilder("target creature connives ");
        sb.append(xValue);
        if (!(xValue instanceof StaticValue)) {
            sb.append(", where X is ");
            sb.append(xValue.getMessage());
        }
        sb.append("<i>(Draw ");
        sb.append(xValue);
        sb.append(" cards, then discard ");
        sb.append(xValue);
        sb.append(" cards. Put a +1/+1 counter on that creature for each nonland card discarded this way.)</i>");
        return sb.toString();
    }
}
