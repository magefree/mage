package mage.abilities.effects.common.combat;

import mage.abilities.Ability;
import mage.abilities.effects.RestrictionEffect;
import mage.constants.Duration;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 * @author LevelX2
 */
public class CantBlockAttackActivateAttachedEffect extends RestrictionEffect {

    public CantBlockAttackActivateAttachedEffect() {
        this("creature");
    }

    public CantBlockAttackActivateAttachedEffect(String enchantedName) {
        super(Duration.WhileOnBattlefield);
        staticText = "Enchanted " + enchantedName + " can't attack or block, and its activated abilities can't be activated";
    }

    public CantBlockAttackActivateAttachedEffect(final CantBlockAttackActivateAttachedEffect effect) {
        super(effect);
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        return permanent.getAttachments().contains(source.getSourceId());
    }

    @Override
    public boolean canAttack(Game game, boolean canUseChooseDialogs) {
        return false;
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
    public CantBlockAttackActivateAttachedEffect copy() {
        return new CantBlockAttackActivateAttachedEffect(this);
    }

}
