package mage.abilities.costs.mana;

import mage.abilities.Ability;
import mage.abilities.mana.ManaOptions;
import mage.game.Game;

/**
 * Interface for abilities that allow the player to pay mana costs of a spell in alternate ways.
 * For the payment SpecialActions are used.
 * <p>
 * Example of such an alternate payment ability: {@link mage.abilities.keyword.DelveAbility}
 *
 * @author LevelX2, JayDi85
 */
public interface AlternateManaPaymentAbility {
    /**
     * Adds the special action to the state, that allows the user to do the alternate mana payment
     * <p>
     * The action pays the spell's cost directly. Each time it is used, it looks up the cost with
     * {@link mage.util.ManaUtil#getCastingCost} and calls {@link ManaCost#setPayment} on its unpaid parts
     * ({@link mage.util.ManaUtil#getUnpaidParts}). If the action spends real mana (assist), it must also add that
     * mana to {@link ManaCost#getUsedManaToPay}, and pay with the spell's ability so that the MANA_PAID events count
     * it as mana spent on the spell.
     * <p>
     * If the action fails, the rollback keeps the stack, so the cost stays as the action left it. The action must
     * not pay anything until its steps that can fail are done.
     *
     * @param source
     * @param game
     */
    void addSpecialAction(Ability source, Game game);

    /**
     * All possible mana payments that can make that ability (uses to find playable abilities)
     *
     * @param source
     * @param game
     * @param unpaid
     * @return
     */
    ManaOptions getManaOptions(Ability source, Game game, ManaCost unpaid);

    /**
     * Mana payment step where you can use it
     *
     * @return
     */
    ActivationManaAbilityStep useOnActivationManaAbilityStep();
}
