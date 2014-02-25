/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mage.abilities.effects.common.combat;

import mage.abilities.Ability;
import mage.abilities.effects.RequirementEffect;
import mage.constants.Duration;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author LevelX2
 */
public class AttacksIfAbleAllEffect extends RequirementEffect<AttacksIfAbleAllEffect> {

    private final FilterCreaturePermanent filter;

    public AttacksIfAbleAllEffect(FilterCreaturePermanent filter) {
        super(Duration.WhileOnBattlefield);
        staticText = new StringBuilder(filter.getMessage()).append(" attack each turn if able").toString();
        this.filter = filter;
    }

    public AttacksIfAbleAllEffect(final AttacksIfAbleAllEffect effect) {
        super(effect);
        this.filter = effect.filter;
    }

    @Override
    public AttacksIfAbleAllEffect copy() {
        return new AttacksIfAbleAllEffect(this);
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        return filter.match(permanent, source.getSourceId(), source.getControllerId(), game);
    }

    @Override
    public boolean mustAttack(Game game) {
        return true;
    }

    @Override
    public boolean mustBlock(Game game) {
        return false;
    }

}
