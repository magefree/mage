package mage.abilities.dynamicvalue.common;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.cards.Card;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;

/**
 * @author TheElk801
 */
public enum InstantSorceryExileGraveyardCount implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        Player player = game.getPlayer(sourceAbility.getControllerId());
        if (player != null) {
            int exileCount = 0;
            for (Card exiledCard : game.getExile().getAllCards(game)) {
                if (exiledCard.getOwnerId().equals(player.getId()) && exiledCard.isInstantOrSorcery(game)) {
                    exileCount++;
                }
            }
            return player.getGraveyard().count(
                    StaticFilters.FILTER_CARD_INSTANT_OR_SORCERY, game
            ) + exileCount;
        }
        return 0;
    }

    @Override
    public InstantSorceryExileGraveyardCount copy() {
        return instance;
    }

    @Override
    public String getMessage() {
        return "";
    }
}
