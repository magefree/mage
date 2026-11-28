
package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.costs.Cost;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.util.CardUtil;

/**
 * @author JayDi85
 */
public class TapSourceUnlessPaysEffect extends TapSourceEffect {

    protected Cost cost;

    public TapSourceUnlessPaysEffect(Cost cost) {
        this(cost, true);
    }

    public TapSourceUnlessPaysEffect(Cost cost, boolean withoutTrigger) {
        super(withoutTrigger);
        this.cost = cost;
        staticText = "tap {this} unless you " + CardUtil.addCostVerb(cost.getText());
    }

    protected TapSourceUnlessPaysEffect(final TapSourceUnlessPaysEffect effect) {
        super(effect);
        this.cost = effect.cost.copy();
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent permanent = source.getSourcePermanentIfItStillExists(game);
        if (permanent == null) {
            permanent = game.getPermanentEntering(source.getSourceId());
        }
        if (player == null || permanent == null) {
            return false;
        }
        if (cost.canPay(source, source, source.getControllerId(), game)
                && player.chooseUse(Outcome.Benefit, cost.getText() + "? (otherwise " + permanent.getName() + " will be tapped)", source, game)) {
            cost.clearPaid();
            if (cost.pay(source, game, source, source.getControllerId(), false, null)) {
                return true;
            }
        }
        return super.apply(game, source);
    }

    @Override
    public TapSourceUnlessPaysEffect copy() {
        return new TapSourceUnlessPaysEffect(this);
    }

}
