/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mage.abilities.condition.common;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.constants.CardType;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */

public class SpellMasteryCondition implements Condition {

    private static final FilterCard filter = new FilterCard();
    
    static {
        filter.add(Predicates.or(new CardTypePredicate(CardType.INSTANT), new CardTypePredicate(CardType.SORCERY)));
    }
    
    private static SpellMasteryCondition fInstance = null;

    public static SpellMasteryCondition getInstance() {
        if (fInstance == null) {
            fInstance = new SpellMasteryCondition();
        }
        return fInstance;
    }
    
    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        return player != null && player.getGraveyard().count(filter, game) >= 2;
    }

    @Override
    public String toString() {
        return "there are two or more instant and/or sorcery cards in your graveyard";
    }
    
    
}