package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;

/**
 * @author LevelX2
 */
public class RegenerateAllEffect extends OneShotEffect {

    private final FilterPermanent filter;

    public RegenerateAllEffect(FilterPermanent filter) {
        super(Outcome.Regenerate);
        this.filter = filter;
        staticText = "Regenerate each " + filter.getMessage();
    }

    public RegenerateAllEffect(final RegenerateAllEffect effect) {
        super(effect);
        this.filter = effect.filter.copy();
    }

    @Override
    public RegenerateAllEffect copy() {
        return new RegenerateAllEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (Permanent permanent : game.getBattlefield().getActivePermanents(filter, source.getControllerId(), source, game)) {
            RegenerateTargetEffect regenEffect = new RegenerateTargetEffect();
            regenEffect.setTargetPointer(new FixedTarget(permanent, game));
            game.addEffect(regenEffect, source);
        }
        return true;
    }
}
