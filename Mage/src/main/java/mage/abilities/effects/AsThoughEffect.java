package mage.abilities.effects;

import mage.abilities.Ability;
import mage.constants.AsThoughEffectType;
import mage.game.Game;

import java.util.UUID;

/**
 * This class functions in two steps:
 * 1. Check if the effect `applies` to a given situation using the two `.applies()` method.
 * 2. If the effect applies (because `.applies()` return true) then the corresponding `.apply()` method
 *    will be called to make any of the required changes to the state.
 * <p>
 * The `.applies()` method MUST NOT change the state of the game as they are called multiple times in the course of
 * figuring out which cards and abilities are playable.
 * <p>
 * For example. Bolas's Citadel allows you to play lands and cast spells from the top of your library.
 * Its `.applies()` method must only check if the the specified card can be played.
 * The changing of casting cost (mana to life) MUST be handled by the `.apply()` method.
 *
 * @author BetaSteward_at_googlemail.com, Alex-Vasile
 */
public interface AsThoughEffect extends ContinuousEffect {

    // TODO: sourceId appears to have been renamed to objectId
    /**
     * WARNING: DO NOT CHANGE STATE IN THIS FUNCTION. (other than discard the effect)
     * If your implementation requires that the state of the game be changed, any information stored, or the player asked
     * a question, you must do all of that in the `.apply()` functions from this class.
     * For the purpose of the `.applies()` methods, assume that the player will pick whichever answer allows for the
     * greatest number of abilities to be used.
     * <p>
     * Check if the asThoughEffect applies for the SINGLE specified ability from the object (sourceId).
     * <p>
     * Warning, if you don't need ability to check then ignore it (by default it calls full object check)
     * Warning, if you use conditional effect then you must override both applies methods to support different types
     *
     * @param sourceId          object to check
     * @param affectedAbility   ability from object to check (example: check if spell ability can be cast from non hand)
     * @param source            ability that created this effect
     * @param game              game to check in
     * @param playerId          player to check
     * @return                  boolean indicating if the effect can apply (i.e. applies) for affectedAbility from sourceId.
     */
    boolean applies(UUID sourceId, Ability affectedAbility, Ability source, Game game, UUID playerId);

    /**
     * TODO Need refactoring
     *      - SpendManaAsAnyColorToCastTopOfLibraryTargetEffect
     */
    /**
     * WARNING: DO NOT CHANGE STATE IN THIS FUNCTION. (other than discard the effect)
     * If your implementation requires that the state of the game be changed, any information stored, or the player asked
     * a question, you must do all of that in the `.apply()` functions from this class.
     * For the purpose of the `.applies()` methods, assume that the player will pick whichever answer allows for the
     * greatest number of abilities to be used.
     * <p>
     * Apply to ANY ability from the object (sourceId)
     *
     * @param sourceId              object to check
     * @param source                TODO
     * @param affectedControllerId  player to check (example: you can activate opponent's card or ability)
     * @param game                  game to check in
     * @return                      boolean indicating if the effect can apply (i.e. applies) for sourceId.
     */
    boolean applies(UUID sourceId, Ability source, UUID affectedControllerId, Game game);

    /**
     * Called after {@link AsThoughEffect#applies(UUID, Ability, Ability, Game, UUID)} has been called and returned true.
     * Put any code that requires changing of state, storing of information, or asking the player for input in this method.
     *
     * @param sourceId          object whose ability to check
     * @param affectedAbility   ability to check
     * @param source            TODO
     * @param game              game to apply the effect in
     * @param playerId          player to check
     * @return                  boolean indicating if the effect WAS APPLIED to affectedAbility from sourceId.
     */
    boolean apply(UUID sourceId, Ability affectedAbility, Ability source, Game game, UUID playerId);

    /**
     * Called after {@link AsThoughEffect#applies(UUID, Ability, UUID, Game)} has been called and returned true.
     * Put any code that requires changing of state, storing of information, or asking the player for input in this method.
     *
     *
     * @param sourceId              Object to check
     * @param source                TODO
     * @param affectedControllerId  player to check if the effect applies to them
     * @param game                  game to apply the effect in
     * @return                      boolean indicating if the effect WAS APPLIED to sourceId.
     */
    boolean apply(UUID sourceId, Ability source, UUID affectedControllerId, Game game);

    AsThoughEffectType getAsThoughEffectType();

    @Override
    AsThoughEffect copy();

    boolean isConsumable();
}
