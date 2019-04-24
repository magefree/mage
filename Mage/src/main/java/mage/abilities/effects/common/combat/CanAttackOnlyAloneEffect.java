/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mage.abilities.effects.common.combat;

import mage.abilities.Ability;
import mage.abilities.effects.RestrictionEffect;
import mage.constants.Duration;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author LevelX2
 */
public class CanAttackOnlyAloneEffect extends RestrictionEffect {

    public CanAttackOnlyAloneEffect() {
        super(Duration.WhileOnBattlefield);
        staticText = "{this} can only attack alone";
    }

    public CanAttackOnlyAloneEffect(final CanAttackOnlyAloneEffect effect) {
        super(effect);
    }

    @Override
    public CanAttackOnlyAloneEffect copy() {
        return new CanAttackOnlyAloneEffect(this);
    }

    @Override
    public boolean canAttackCheckAfter(int numberOfAttackers, Ability source, Game game) {
        return numberOfAttackers == 1;
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        return source.getSourceId().equals(permanent.getId());
    }
}
