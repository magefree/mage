
package mage.abilities.effects.common.combat;

import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.SubLayer;
import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;


import mage.util.CardUtil;

/**
 *
 * @author Quercitron
 */
public class CantBeBlockedByMoreThanOneAllEffect extends ContinuousEffectImpl {

    private FilterPermanent filter;
    protected int amount;

    public CantBeBlockedByMoreThanOneAllEffect(FilterPermanent filter) {
        this(1, filter, Duration.WhileOnBattlefield);
    }

    public CantBeBlockedByMoreThanOneAllEffect(int amount, FilterPermanent filter) {
        this(amount, filter, Duration.WhileOnBattlefield);
    }

    public CantBeBlockedByMoreThanOneAllEffect(int amount, FilterPermanent filter, Duration duration) {
        super(duration, Outcome.Benefit);
        this.amount = amount;
        this.filter = filter;
        staticText = new StringBuilder("Each ").append(filter.getMessage()).append(" can't be blocked by more than ")
                .append(CardUtil.numberToText(amount)).append(" creature").append(amount > 1 ? "s" : "").toString();
    }

    public CantBeBlockedByMoreThanOneAllEffect(final CantBeBlockedByMoreThanOneAllEffect effect) {
        super(effect);
        this.amount = effect.amount;
        this.filter = effect.filter;
    }

    @Override
    public CantBeBlockedByMoreThanOneAllEffect copy() {
        return new CantBeBlockedByMoreThanOneAllEffect(this);
    }

    @Override
    public boolean apply(Layer layer, SubLayer sublayer, Ability source, Game game) {
        switch (layer) {
            case RulesEffects:
                for (Permanent perm : game.getBattlefield().getActivePermanents(filter, source.getControllerId(), source, game)) {
                    perm.setMaxBlockedBy(amount);
                }
                break;
        }
        return true;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public boolean hasLayer(Layer layer) {
        return layer == Layer.RulesEffects;
    }
}
