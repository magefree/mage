/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mage.filter.predicate.other;

import mage.cards.Card;
import mage.constants.Zone;
import mage.filter.predicate.Predicate;
import mage.game.Game;
import mage.game.stack.Spell;

/**
 *
 * @author LevelX2
 */
public class CastFromZonePredicate implements Predicate<Card> {

    private final Zone zone;

    public CastFromZonePredicate(Zone zone) {
        this.zone = zone;
    }

    @Override
    public boolean apply(Card input, Game game) {
        if (input instanceof Spell) {
            return zone.match(((Spell) input).getFromZone());
        } else {
            return zone.match(game.getState().getZone(input.getId()));
        }
    }

    @Override
    public String toString() {
        return "Spells you cast from your " + zone.toString();
    }
}
