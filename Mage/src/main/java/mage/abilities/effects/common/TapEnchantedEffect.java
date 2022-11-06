package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 * @author LevelX
 */
public class TapEnchantedEffect extends OneShotEffect {

    public TapEnchantedEffect() {
        this("creature");
    }

    public TapEnchantedEffect(String name) {
        super(Outcome.Tap);
        staticText = "tap enchanted " + name;
    }

    public TapEnchantedEffect(final TapEnchantedEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = source.getSourcePermanentIfItStillExists(game);
        if (permanent != null) {
            Permanent attach = game.getPermanent(permanent.getAttachedTo());
            if (attach != null) {
                attach.tap(source, game);
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
