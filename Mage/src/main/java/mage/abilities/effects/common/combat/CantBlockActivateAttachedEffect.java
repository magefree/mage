package mage.abilities.effects.common.combat;

import mage.abilities.Ability;
import mage.abilities.effects.RestrictionEffect;
import mage.constants.Duration;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 * @author escplan9 (Derek Monturo - dmontur1 at gmail dot com)
 */
public class CantBlockActivateAttachedEffect extends RestrictionEffect {

    public CantBlockActivateAttachedEffect() {
        super(Duration.WhileOnBattlefield);
        staticText = "Enchanted creature can't block, and its activated abilities can't be activated";
    }

    public CantBlockActivateAttachedEffect(final CantBlockActivateAttachedEffect effect) {
        super(effect);
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        return permanent.getAttachments().contains(source.getSourceId());
    }

    @Override
    public boolean canBlock(Permanent attacker, Permanent blocker, Ability source, Game game, boolean canUseChooseDialogs) {
        return false;
    }

    @Override
    public boolean canUseActivatedAbilities(Permanent permanent, Ability source, Game game, boolean canUseChooseDialogs) {
        return false;
    }

    @Override
    public CantBlockActivateAttachedEffect copy() {
        return new CantBlockActivateAttachedEffect(this);
    }
}
