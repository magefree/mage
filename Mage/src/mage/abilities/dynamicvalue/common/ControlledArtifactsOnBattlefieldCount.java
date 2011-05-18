package mage.abilities.dynamicvalue.common;

import mage.Constants;
import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.filter.Filter;
import mage.filter.FilterPermanent;
import mage.game.Game;

public class ControlledArtifactsOnBattlefieldCount implements DynamicValue {
	private static final FilterPermanent filter = new FilterPermanent();

    static {
        filter.getCardType().add(Constants.CardType.ARTIFACT);
        filter.setScopeCardType(Filter.ComparisonScope.Any);
    }

    @Override
    public int calculate(Game game, Ability sourceAbility) {
        return game.getBattlefield().countAll(filter, sourceAbility.getControllerId());
    }

    @Override
    public DynamicValue clone() {
        return new ControlledArtifactsOnBattlefieldCount();
    }

    @Override
    public String toString() {
        return "X";
    }
}
