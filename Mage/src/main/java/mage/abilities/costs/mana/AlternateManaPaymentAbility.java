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
     * The action pays {@code paying} directly, using {@link ManaCost#setPayment}
     * on its unpaid parts ({@link mage.util.ManaUtil#getUnpaidParts}).
     * Mana it really spends (assist) must also be added to {@link ManaCost#getUsedManaToPay},
     * and paid as the spell so its MANA_PAID events count.
     * <p>
     * Copies of the action must share {@code paying}, not copy it: the payment loop keeps paying
     * that object, even across a rollback mid-payment.
     *
     * @param source
     * @param game
     * @param paying the cost being paid, including any parts already paid
     */
    void addSpecialAction(Ability source, Game game, ManaCost paying);

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
