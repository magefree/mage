package mage.abilities.costs.mana;

/**
 * Some special AlternateManaPaymentAbility must be restricted to pay before or after mana abilities.
 * Game logic: if you use special mana ability then normal mana abilities must be restricted and vice versa,
 * see Convoke for more info and rules
 *
 * @author JayDi85
 */

public enum ActivationManaAbilityStep {
    BEFORE(0), // assist
    NORMAL(1), // all activated mana abilities
    AFTER(2); // convoke, delve, improvise

    private final int stepOrder;

    ActivationManaAbilityStep(int stepOrder) {
        this.stepOrder = stepOrder;
    }

    public int getStepOrder() {
        return stepOrder;
    }
}
