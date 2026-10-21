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
     * The action pays the spell's cost directly. It calls {@link ManaCost#setPayment} on the unpaid parts
     * ({@link mage.util.ManaUtil#getUnpaidParts}) of the cost, and it looks the cost up with
     * {@link mage.util.ManaUtil#getCastingCost} each time it is used.
     * Mana it really spends (assist) must also be added to {@link ManaCost#getUsedManaToPay},
     * and paid as the spell so its MANA_PAID events count.
     * <p>
     * If the action fails, the rollback keeps the stack, so the cost stays as the action left it.
     * The action must not pay anything before its last step that can fail.
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
