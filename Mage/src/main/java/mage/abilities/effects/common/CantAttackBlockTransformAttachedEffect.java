/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.effects.RestrictionEffect;
import mage.constants.Duration;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author halljared
 */
public class CantAttackBlockTransformAttachedEffect extends RestrictionEffect {

    public CantAttackBlockTransformAttachedEffect() {
        super(Duration.WhileOnBattlefield);
        staticText = "Enchanted creature can't attack, block, or transform.";
    }

    public CantAttackBlockTransformAttachedEffect(final CantAttackBlockTransformAttachedEffect effect) {
        super(effect);
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        Permanent enchantment = game.getPermanent(source.getSourceId());
        if (enchantment != null && enchantment.getAttachedTo() != null) {
            if (permanent.getId().equals(enchantment.getAttachedTo())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean canAttack(Game game) {
        return false;
    }

    @Override
    public boolean canBlock(Permanent attacker, Permanent blocker, Ability source, Game game) {
        return false;
    }

    @Override
    public boolean canTransform(Permanent permanent, Ability source, Game game) {
        return false;
    }

    @Override
    public CantAttackBlockTransformAttachedEffect copy() {
        return new CantAttackBlockTransformAttachedEffect(this);
    }

}
