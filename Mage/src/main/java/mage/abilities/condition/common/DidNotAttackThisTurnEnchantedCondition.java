/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mage.abilities.condition.common;

import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.watchers.common.AttackedThisTurnWatcher;

/**
 *
 * @author jeffwadsworth
 */
public enum DidNotAttackThisTurnEnchantedCondition implements Condition {
    
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent auraPermanent = game.getPermanentOrLKIBattlefield(source.getSourceId());
        if (auraPermanent != null) {
            Permanent enchantedPermanent = game.getPermanent(auraPermanent.getAttachedTo());
            AttackedThisTurnWatcher watcher = game.getState().getWatcher(AttackedThisTurnWatcher.class);
            return enchantedPermanent != null
                    && watcher != null
                    && !watcher.getAttackedThisTurnCreatures().contains(
                            new MageObjectReference(enchantedPermanent, game));
        }
        return false;
    }
}
