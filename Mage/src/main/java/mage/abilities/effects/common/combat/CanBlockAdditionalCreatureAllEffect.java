
package mage.abilities.effects.common.combat;

import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.SubLayer;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.util.CardUtil;

/**
 * @author emerald000
 */

public class CanBlockAdditionalCreatureAllEffect extends ContinuousEffectImpl {

    protected int amount;
    protected FilterPermanent filter;

    public CanBlockAdditionalCreatureAllEffect(int amount, FilterPermanent filter, Duration duration) {
        super(duration, Outcome.Benefit);
        this.amount = amount;
        this.filter = filter;
        staticText = setText();
    }

    public CanBlockAdditionalCreatureAllEffect(final CanBlockAdditionalCreatureAllEffect effect) {
        super(effect);
        this.amount = effect.amount;
        this.filter = effect.filter;
    }

    @Override
    public CanBlockAdditionalCreatureAllEffect copy() {
        return new CanBlockAdditionalCreatureAllEffect(this);
    }

    @Override
    public boolean apply(Layer layer, SubLayer sublayer, Ability source, Game game) {
        for (Permanent permanent : game.getBattlefield().getActivePermanents(filter, source.getControllerId(), source, game)) {
            if (permanent != null) {
                // maxBlocks = 0 equals to "can block any number of creatures"
                if (amount > 0) {
                    permanent.setMaxBlocks(permanent.getMaxBlocks() + amount);
                } else {
                    permanent.setMaxBlocks(0);
                }
            }
        }
        return true;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    private String setText() {
        StringBuilder sb = new StringBuilder(filter.getMessage());
        sb.append(" can block ");
        switch (amount) {
            case 0:
                sb.append("any number of creatures");
                break;
            case 1:
                sb.append("an additional creature each combat");
                break;
            default:
                sb.append(CardUtil.numberToText(amount));
                sb.append(" additional creatures");
        }
        return sb.toString();
    }

    @Override
    public boolean hasLayer(Layer layer) {
        return layer == Layer.RulesEffects;
    }
}
