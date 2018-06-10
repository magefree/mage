

package mage.abilities.effects.common;

import mage.constants.Outcome;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author LevelX
 */
public class UntapEnchantedEffect extends OneShotEffect {

    public UntapEnchantedEffect() {
        super(Outcome.Untap);
        staticText = "untap enchanted creature";
    }

    public UntapEnchantedEffect(final UntapEnchantedEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (permanent != null) {
            Permanent attach = game.getPermanent(permanent.getAttachedTo());
            if (attach != null) {
                attach.untap(game);
                return true;
            }
        }
        return false;
    }

    @Override
    public UntapEnchantedEffect copy() {
        return new UntapEnchantedEffect(this);
    }

}
