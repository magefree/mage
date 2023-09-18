package mage.abilities.effects;

import mage.abilities.Ability;
import mage.constants.AsThoughEffectType;
import mage.game.Game;

import java.util.UUID;

/**
 * @author BetaSteward_at_googlemail.com
 */
public interface AsThoughEffect extends ContinuousEffect {

    /**
     * Apply to ONE affected ability from the object (sourceId)
     * <p>
     * Warning, if you don't need ability to check then ignore it (by default it calls full object check)
     * Warning, if you use conditional effect then you must override both applies methods to support different types
     *
     * @param sourceId
     * @param affectedAbility ability to check (example: check if spell ability can be cast from non hand)
     * @param source
     * @param game
     * @param playerId        player to check
     * @return
     */
    boolean applies(UUID sourceId, Ability affectedAbility, Ability source, Game game, UUID playerId);

    /**
     * Apply to ANY ability from the object (sourceId)
     *
     * @param sourceId             object to check
     * @param source
     * @param affectedControllerId player to check (example: you can activate opponent's card or ability)
     * @param game
     * @return
     */
    boolean applies(UUID sourceId, Ability source, UUID affectedControllerId, Game game);

    AsThoughEffectType getAsThoughEffectType();

    @Override
    AsThoughEffect copy();

    boolean isConsumable();
}
