
package mage.abilities.costs.mana;

import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.costs.VariableCost;
import mage.game.Game;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 *
 * @author BetaSteward_at_googlemail.com
 * @param <T>
 */
public interface ManaCosts<T extends ManaCost> extends List<T>, ManaCost {

    ManaCosts<T> getUnpaidVariableCosts();

    List<VariableCost> getVariableCosts();

    int getX();

    void setX(int x);

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
                .collect(Collectors.toCollection(ManaCostsImpl<ManaCost>::new));

    }

}
