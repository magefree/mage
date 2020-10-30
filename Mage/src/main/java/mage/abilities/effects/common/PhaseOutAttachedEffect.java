package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author escplan9 (Derek Monturo - dmontur1 at gmail dot com)
 */
public class PhaseOutAttachedEffect extends OneShotEffect {

    public PhaseOutAttachedEffect() {
        super(Outcome.Detriment);
        this.staticText = "Enchanted creature phases out";
    }

    public PhaseOutAttachedEffect(final PhaseOutAttachedEffect effect) {
        super(effect);
    }

    @Override
    public PhaseOutAttachedEffect copy() {
        return new PhaseOutAttachedEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        // In the case that the enchantment is blinked
        Permanent enchantment = (Permanent) game.getLastKnownInformation(source.getSourceId(), Zone.BATTLEFIELD);
        if (enchantment == null) {
            // It was not blinked, use the standard method
            enchantment = game.getPermanentOrLKIBattlefield(source.getSourceId());
        }
        if (enchantment != null) {
            Permanent enchanted = game.getPermanent(enchantment.getAttachedTo());
            if (enchanted != null) {
                return enchanted.phaseOut(game);
            }
        }
        return false;
    }
}
