/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mage.filter.predicate.permanent;

import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 * Filters out the id of the enchanted object, if the source is an enchantment
 *
 * @author LevelX2
 */
public class AnotherEnchantedPredicate implements ObjectSourcePlayerPredicate<ObjectSourcePlayer<Permanent>> {

    @Override
    public boolean apply(ObjectSourcePlayer<Permanent> input, Game game) {
        Permanent enchantment = game.getPermanentOrLKIBattlefield(input.getSourceId());
        return enchantment != null && !input.getObject().getId().equals(enchantment.getAttachedTo());
    }

    @Override
    public String toString() {
        return "Another enchanted";
    }
}
