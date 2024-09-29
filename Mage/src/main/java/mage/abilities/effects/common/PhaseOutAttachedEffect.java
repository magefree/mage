package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 * @author escplan9 (Derek Monturo - dmontur1 at gmail dot com)
 */
public class PhaseOutAttachedEffect extends OneShotEffect {

    public PhaseOutAttachedEffect() {
        super(Outcome.Detriment);
        this.staticText = "Enchanted creature phases out";
    }

    protected PhaseOutAttachedEffect(final PhaseOutAttachedEffect effect) {
        super(effect);
    }

    @Override
    public PhaseOutAttachedEffect copy() {
        return new PhaseOutAttachedEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent enchantment = source.getSourcePermanentOrLKI(game);
        if (enchantment != null) {
            Permanent enchanted = game.getPermanent(enchantment.getAttachedTo());
            if (enchanted != null) {
                return enchanted.phaseOut(game);
            }
        }
        return false;
    }
}
