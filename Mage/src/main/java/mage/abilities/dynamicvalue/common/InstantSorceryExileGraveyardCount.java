package mage.abilities.dynamicvalue.common;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.game.Game;
import mage.filter.StaticFilters;
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
            return player.getGraveyard().count(
                    StaticFilters.FILTER_CARD_INSTANT_OR_SORCERY, game
            ) + game.getExile().getExileZone(player.getId()).count(
                    StaticFilters.FILTER_CARD_INSTANT_OR_SORCERY, game
            );
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
