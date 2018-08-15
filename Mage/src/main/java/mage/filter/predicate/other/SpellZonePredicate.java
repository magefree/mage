/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mage.filter.predicate.other;

import mage.constants.Zone;
import mage.filter.predicate.Predicate;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.game.stack.StackObject;

/**
 *
 * @author BetaSteward
 */
public class SpellZonePredicate implements Predicate<StackObject> {

    private final Zone zone;

    public SpellZonePredicate(Zone zone) {
        this.zone = zone;
    }

    @Override
    public boolean apply(StackObject input, Game game) {
        return input instanceof Spell && ((Spell) input).getFromZone().match(zone);
    }

    @Override
    public String toString() {
        return "SpellZone(" + zone + ')';
    }
}
