
package mage.abilities.effects.keyword;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.game.Game;

/**
 *
 * @author LevelX2
 */
public class ExploreTargetEffect extends OneShotEffect {

    public ExploreTargetEffect() {
        this(true);
    }

    public ExploreTargetEffect(boolean showAbilityHint) {
        super(Outcome.Benefit);
        this.staticText = ExploreSourceEffect.getRuleText(showAbilityHint);
    }

    public ExploreTargetEffect(final ExploreTargetEffect effect) {
        super(effect);
    }

    @Override
    public ExploreTargetEffect copy() {
        return new ExploreTargetEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return ExploreSourceEffect.explorePermanent(game, getTargetPointer().getFirst(game, source), source);
    }

}
