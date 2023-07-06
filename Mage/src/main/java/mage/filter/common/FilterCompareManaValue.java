package mage.filter.common;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.cards.Card;
import mage.constants.ComparisonType;
import mage.filter.FilterCard;
import mage.game.Game;

import java.util.UUID;

public class FilterCompareManaValue extends FilterCard {

    private final ComparisonType comparisonType;
    private final DynamicValue value;

    public FilterCompareManaValue(ComparisonType comparisonType, DynamicValue value) {
        super();
        this.comparisonType = comparisonType;
        this.value = value;
    }
    public FilterCompareManaValue(ComparisonType comparisonType, int value) {
        super();
        this.comparisonType = comparisonType;
        this.value = StaticValue.get(value);
    }

    @Override
    public boolean match(Card card, UUID playerId, Ability source, Game game) {
        return ComparisonType.compare(card.getManaValue(), comparisonType, value.calculate(game, source, null));
    }
}
