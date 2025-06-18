package mage.cards.l;

import mage.abilities.condition.common.IsStepCondition;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.common.ActivateIfConditionActivatedAbility;
import mage.abilities.dynamicvalue.common.SacrificeCostCreaturesToughness;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class LifeChisel extends CardImpl {

    public LifeChisel(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{4}");

        // Sacrifice a creature: You gain life equal to the sacrificed creature's toughness. Activate this ability only during your upkeep.
        this.addAbility(new ActivateIfConditionActivatedAbility(
                new GainLifeEffect(SacrificeCostCreaturesToughness.instance)
                        .setText("you gain life equal to the sacrificed creature's toughness"),
                new SacrificeTargetCost(StaticFilters.FILTER_PERMANENT_CREATURE),
                IsStepCondition.getMyUpkeep()
        ));
    }

    private LifeChisel(final LifeChisel card) {
        super(card);
    }

    @Override
    public LifeChisel copy() {
        return new LifeChisel(this);
    }
}
