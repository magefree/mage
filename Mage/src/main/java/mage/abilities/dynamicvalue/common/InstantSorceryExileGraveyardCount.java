package mage.abilities.dynamicvalue.common;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
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
        if (player == null) {
            return 0;
        }
        return game.getExile().getCardsOwned(StaticFilters.FILTER_CARD_INSTANT_OR_SORCERY, player.getId(), sourceAbility, game).size()
                + player.getGraveyard().count(StaticFilters.FILTER_CARD_INSTANT_OR_SORCERY, player.getId(), sourceAbility, game);
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
