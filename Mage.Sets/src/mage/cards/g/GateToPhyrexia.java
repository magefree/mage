package mage.cards.g;

import mage.abilities.Ability;
import mage.abilities.common.LimitedTimesPerTurnActivatedAbility;
import mage.abilities.condition.common.IsStepCondition;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.target.common.TargetArtifactPermanent;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class GateToPhyrexia extends CardImpl {

    public GateToPhyrexia(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{B}{B}");

        // Sacrifice a creature: Destroy target artifact. Activate this ability only during your upkeep and only once each turn.
        Ability ability = new LimitedTimesPerTurnActivatedAbility(
                Zone.BATTLEFIELD, new DestroyTargetEffect(),
                new SacrificeTargetCost(StaticFilters.FILTER_PERMANENT_CREATURE),
                1, IsStepCondition.getMyUpkeep());
        ability.addTarget(new TargetArtifactPermanent());
        this.addAbility(ability);
    }

    private GateToPhyrexia(final GateToPhyrexia card) {
        super(card);
    }

    @Override
    public GateToPhyrexia copy() {
        return new GateToPhyrexia(this);
    }
}
