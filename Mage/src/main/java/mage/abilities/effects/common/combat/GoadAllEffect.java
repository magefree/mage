package mage.abilities.effects.common.combat;

import mage.abilities.Ability;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;

/**
 * @author TheElk801
 */
public class GoadAllEffect extends OneShotEffect {

    public GoadAllEffect() {
        super(Outcome.Benefit);
        staticText = "Goad all creatures you don't control. <i>(Until your next turn, those creatures attack each combat if able and attack a player other than you if able.)</i>";
    }

    public GoadAllEffect(final GoadAllEffect effect) {
        super(effect);
    }

    @Override
    public GoadAllEffect copy() {
        return new GoadAllEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (Permanent creature : game.getBattlefield().getActivePermanents(StaticFilters.FILTER_PERMANENT_CREATURE, source.getControllerId(), game)) {
            if (!creature.isControlledBy(source.getControllerId())) {
                Effect effect = new GoadTargetEffect();
                effect.setTargetPointer(new FixedTarget(creature, game));
                effect.apply(game, source);
            }
        }
        return true;
    }
}
