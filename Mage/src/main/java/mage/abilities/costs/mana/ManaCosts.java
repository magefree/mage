package mage.abilities.costs.mana;

import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.costs.VariableCost;
import mage.game.Game;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @param <T>
 * @author BetaSteward_at_googlemail.com
 */
public interface ManaCosts<T extends ManaCost> extends List<T>, ManaCost {

    ManaCosts<T> getUnpaidVariableCosts();

    List<VariableCost> getVariableCosts();

    boolean containsX();

    int getX();

    /**
     * @param xValue final X value -- announced X * xMultiplier, where xMultiplier can be changed by replace events like Unbound Flourishing)
     * @param xPay   real number of pay amount (x * xMultiplier * xInstances, where xInstances is number of {X} in pay like 1, 2, 3)
     */
    void setX(int xValue, int xPay);

    default void load(String mana) {
        load(mana, false);
    }

    /**
     * @param mana
     * @param extractMonoHybridGenericValue for tests only, extract generic mana value from mono hybrid string
     */
    void load(String mana, boolean extractMonoHybridGenericValue);

    boolean payOrRollback(Ability ability, Game game, Ability source, UUID payingPlayerId);

    @Override
    Mana getMana();

    @Override
    ManaCosts<T> copy();


    static ManaCosts<ManaCost> removeVariableManaCost(ManaCosts<ManaCost> m) {
        return m.stream()
                .filter(mc -> !(mc instanceof VariableManaCost))
                .collect(Collectors.toCollection(ManaCostsImpl::new));

    }

    void incrPhyrexianPaid();

    int getPhyrexianPaid();
}
