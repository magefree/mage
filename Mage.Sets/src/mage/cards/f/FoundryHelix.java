package mage.cards.f;

import mage.abilities.condition.common.SacrificedPermanentCondition;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.common.TargetAnyTarget;

import java.util.UUID;

/**
 * @author weirddan455
 */
public final class FoundryHelix extends CardImpl {

    public FoundryHelix(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{R}{W}");

        // As an additional cost to cast this spell, sacrifice a permanent.
        this.getSpellAbility().addCost(new SacrificeTargetCost(StaticFilters.FILTER_PERMANENT));

        // Foundry Helix deals 4 damage to any target. If the sacrificed permanent was an artifact, you gain 4 life.
        this.getSpellAbility().addEffect(new DamageTargetEffect(4));
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(new GainLifeEffect(4),
                new SacrificedPermanentCondition(StaticFilters.FILTER_PERMANENT_ARTIFACT_AN)));
        this.getSpellAbility().addTarget(new TargetAnyTarget());
    }

    private FoundryHelix(final FoundryHelix card) {
        super(card);
    }

    @Override
    public FoundryHelix copy() {
        return new FoundryHelix(this);
    }
}