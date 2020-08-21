
package mage.abilities.effects;

import java.util.UUID;
import mage.abilities.Ability;
import mage.constants.AsThoughEffectType;
import mage.game.Game;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public interface AsThoughEffect extends ContinuousEffect {

    boolean applies(UUID sourceId, Ability affectedAbility, Ability source, Game game, UUID playerId);

    boolean applies(UUID sourceId, Ability source, UUID affectedControllerId, Game game);

    AsThoughEffectType getAsThoughEffectType();

    @Override
    AsThoughEffect copy();
    
    boolean isConsumable();
}
