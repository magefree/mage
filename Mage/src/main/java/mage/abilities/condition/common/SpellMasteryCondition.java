package mage.abilities.condition.common;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.constants.CardType;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */

public enum SpellMasteryCondition implements Condition {

    instance;
    private static final FilterCard filter = new FilterCard();
    
    static {
        filter.add(Predicates.or(CardType.INSTANT.getPredicate(), CardType.SORCERY.getPredicate()));
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