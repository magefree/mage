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
     * @param xValue      announced X value
     * @param xMultiplier special X multiplier to change announced X value without pay increase, see Unbound Flourishing
     */
    void setX(int xValue, int xMultiplier);

    void load(String mana);

    List<String> getSymbols();

    boolean payOrRollback(Ability ability, Game game, UUID sourceId, UUID payingPlayerId);

    @Override
    Mana getMana();

    @Override
    ManaCosts<T> copy();


    static ManaCosts<ManaCost> removeVariableManaCost(ManaCosts<ManaCost> m) {
        return m.stream()
                .filter(mc -> !(mc instanceof VariableManaCost))
                .collect(Collectors.toCollection(ManaCostsImpl::new));

    }

}
