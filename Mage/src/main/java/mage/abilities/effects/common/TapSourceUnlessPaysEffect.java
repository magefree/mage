
package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.costs.Cost;
import mage.abilities.effects.OneShotEffect;
import mage.constants.AbilityType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.util.CardUtil;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class TapSourceUnlessPaysEffect extends OneShotEffect {

    protected Cost cost;

    public TapSourceUnlessPaysEffect(Cost cost) {
        super(Outcome.Tap);
        this.cost = cost;
        staticText = "tap {this} unless you " + CardUtil.addCostVerb(cost.getText());
    }

    public TapSourceUnlessPaysEffect(final TapSourceUnlessPaysEffect effect) {
        super(effect);
        this.cost = effect.cost.copy();
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (permanent == null && source.getAbilityType() == AbilityType.STATIC) {
            permanent = game.getPermanentEntering(source.getSourceId());
        }
        if (player != null && permanent != null) {
            if (cost.canPay(source, source, source.getControllerId(), game)
                    && player.chooseUse(Outcome.Benefit, cost.getText() + "? (otherwise " + permanent.getName() + " becomes tapped)", source, game)) {
                cost.clearPaid();
                if (cost.pay(source, game, source, source.getControllerId(), false, null)) {
                    return true;
                }
            }
            permanent.tap(source, game);
            return true;
        }
        return false;
    }

    @Override
    public TapSourceUnlessPaysEffect copy() {
        return new TapSourceUnlessPaysEffect(this);
    }

}
