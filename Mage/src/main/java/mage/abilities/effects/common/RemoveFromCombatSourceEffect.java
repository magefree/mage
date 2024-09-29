
package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 * @author LevelX2
 */
public class RemoveFromCombatSourceEffect extends OneShotEffect {

    public RemoveFromCombatSourceEffect() {
        super(Outcome.AIDontUseIt);
        staticText = setText();
    }

    protected RemoveFromCombatSourceEffect(final RemoveFromCombatSourceEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (permanent != null) {
            permanent.removeFromCombat(game);
        }
        return false;
    }

    @Override
    public RemoveFromCombatSourceEffect copy() {
        return new RemoveFromCombatSourceEffect(this);
    }

    private String setText() {
        return "Remove {this} from combat";
    }

}
