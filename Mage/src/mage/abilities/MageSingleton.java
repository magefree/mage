package mage.abilities;

/**
 * Marker class for singleton abilities and effects.
 * Prevents effects and abilities to get new Id across the games on the server.
 * Can be used only for stateless effects and abilities.
 *
 * Intended to be used to avoid bugs and for performance reasons.
 *
 * "Must" be used for abilities that use getId() as compare parameter like:
 * FlyingAbility.getInstance().getId()
 * in
 * permanent.getAbilities().containsKey(FlyingAbility.getInstance().getId())
 *
 *
 * @author noxx
 */
public interface MageSingleton {
}
