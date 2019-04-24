

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
public class TapEnchantedEffect extends OneShotEffect {

    public TapEnchantedEffect() {
    super(Outcome.Tap);
     staticText = "tap enchanted creature";
   }

    public TapEnchantedEffect(final TapEnchantedEffect effect) {
    super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = (Permanent) source.getSourceObject(game);
        if (permanent != null) {
            Permanent attach = game.getPermanent(permanent.getAttachedTo());
            if (attach != null) {
                attach.tap(game);
                return true;
            }
        }
        return false;
    }

    @Override
    public TapEnchantedEffect copy() {
        return new TapEnchantedEffect(this);
    }

 }
