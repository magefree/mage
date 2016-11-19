/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mage.abilities.condition.common;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author anonymous
 */
public class AttackingCondition implements Condition {

    private static final AttachedCondition fInstance = new AttachedCondition();

    public static AttachedCondition getInstance() {
        return fInstance;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getSourceId());
        return permanent != null && permanent.isAttacking();
    }
}
