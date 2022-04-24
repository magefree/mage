package mage.abilities.effects.keyword;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;

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
        int amount = xValue.calculate(game, source, this);
        return amount > 0 && ConniveSourceEffect.connive(permanent, amount, source, game);
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
        sb.append(" <i>(Draw ");
        sb.append(xValue);
        sb.append(" cards, then discard ");
        sb.append(xValue);
        sb.append(" cards. Put a +1/+1 counter on that creature for each nonland card discarded this way.)</i>");
        return sb.toString();
    }
}
