package mage;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.abilities.costs.Cost;
import mage.abilities.mana.conditional.ManaCondition;
import mage.constants.ManaType;
import mage.filter.Filter;
import mage.filter.FilterMana;
import mage.game.Game;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author nantuko
 */
public class ConditionalMana extends Mana implements Serializable {

    /**
     * Conditions that should be met (all or any depending on comparison scope)
     * to allow spending {@link Mana} mana.
     */
    private List<Condition> conditions = new ArrayList<>();

    /**
     * Text displayed as a description for conditional mana. Usually includes
     * the conditions that should be met to use this mana.
     */
    protected String staticText = "Conditional mana.";

    /**
     * By default all conditions should be met
     */
    private Filter.ComparisonScope scope = Filter.ComparisonScope.All;

    /**
     * UUID of source for mana.
     */
    private UUID manaProducerId;

    /**
     * UUID originalId of source ability for mana.
     */
    private UUID manaProducerOriginalId;

    public ConditionalMana(Mana mana) {
        super(mana);
    }

    public ConditionalMana(final ConditionalMana conditionalMana) {
        super(conditionalMana);
        conditions = conditionalMana.conditions;
        scope = conditionalMana.scope;
        staticText = conditionalMana.staticText;
        manaProducerId = conditionalMana.manaProducerId;
        manaProducerOriginalId = conditionalMana.manaProducerOriginalId;
    }

    public void addCondition(Condition condition) {
        this.conditions.add(condition);
    }

    public void setComparisonScope(Filter.ComparisonScope scope) {
        this.scope = scope;
    }

    public boolean apply(Ability ability, Game game, UUID manaProducerId, Cost costToPay) {
        if (conditions.isEmpty()) {
            throw new IllegalStateException("Conditional mana should contain at least one Condition");
        }
        for (Condition condition : conditions) {
            boolean applied = (condition instanceof ManaCondition)
                    ? ((ManaCondition) condition).apply(game, ability, manaProducerId, costToPay) : condition.apply(game, ability);

            if (!applied) {
                // if one condition fails, return false only if All conditions should be met
                // otherwise it may happen that Any other condition will be ok
                if (scope == Filter.ComparisonScope.All) {
                    return false;
                }
            } else {
                // if one condition succeeded, return true only if Any conditions should be met
                // otherwise it may happen that any other condition will fail
                if (scope == Filter.ComparisonScope.Any) {
                    return true;
                }
            }
        }
        // we are here
        // if All conditions should be met, then it's Ok (return true)
        // if Any, then it should have already returned true, so returning false here
        return scope == Filter.ComparisonScope.All;
    }

    @Override
    public ConditionalMana copy() {
        return new ConditionalMana(this);
    }

    public String getDescription() {
        return staticText;
    }

    public void removeAll(FilterMana filter) {
        if (filter == null) {
            return;
        }
        if (filter.isBlack()) {
            black = 0;
        }
        if (filter.isBlue()) {
            blue = 0;
        }
        if (filter.isWhite()) {
            white = 0;
        }
        if (filter.isGreen()) {
            green = 0;
        }
        if (filter.isRed()) {
            red = 0;
        }
        if (filter.isColorless()) {
            colorless = 0;
        }
        if (filter.isGeneric()) {
            generic = 0;
        }
    }

    public UUID getManaProducerId() {
        return manaProducerId;
    }

    public void setManaProducerId(UUID manaProducerId) {
        this.manaProducerId = manaProducerId;
    }

    public UUID getManaProducerOriginalId() {
        return manaProducerOriginalId;
    }

    public void setManaProducerOriginalId(UUID manaProducerOriginalId) {
        this.manaProducerOriginalId = manaProducerOriginalId;
    }

    public void clear(ManaType manaType) {
        switch (manaType) {
            case BLACK:
                black = 0;
                break;
            case BLUE:
                blue = 0;
                break;
            case GREEN:
                green = 0;
                break;
            case RED:
                red = 0;
                break;
            case WHITE:
                white = 0;
                break;
            case GENERIC:
                generic = 0;
                break;
            case COLORLESS:
                colorless = 0;
                break;
        }
    }

    public void add(ManaType manaType, int amount) {
        switch (manaType) {
            case BLACK:
                black += amount;
                break;
            case BLUE:
                blue += amount;
                break;
            case GREEN:
                green += amount;
                break;
            case RED:
                red += amount;
                break;
            case WHITE:
                white += amount;
                break;
            case COLORLESS:
                colorless += amount;
                break;
            case GENERIC:
                generic += amount;
                break;
        }
    }
}
