package mage.cards.e;

import java.util.UUID;

import mage.abilities.condition.common.CollectedEvidenceCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.SacrificeOpponentsEffect;
import mage.abilities.keyword.CollectEvidenceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.permanent.GreatestPowerControlledPredicate;

/**
 * @author Cguy7777
 */
public final class ExtractAConfession extends CardImpl {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent(
            "a creature with the greatest power among creatures they control");

    static {
        filter.add(GreatestPowerControlledPredicate.instance);
    }

    public ExtractAConfession(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{B}");

        // As an additional cost to cast this spell, you may collect evidence 6.
        this.addAbility(new CollectEvidenceAbility(6));

        // Each opponent sacrifices a creature. If evidence was collected,
        // instead each opponent sacrifices a creature with the greatest power among creatures they control.
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new SacrificeOpponentsEffect(filter),
                new SacrificeOpponentsEffect(StaticFilters.FILTER_PERMANENT_CREATURE),
                CollectedEvidenceCondition.instance,
                "each opponent sacrifices a creature. " +
                        "If evidence was collected, instead each opponent " +
                        "sacrifices a creature with the greatest power among creatures they control"));
    }

    private ExtractAConfession(final ExtractAConfession card) {
        super(card);
    }

    @Override
    public ExtractAConfession copy() {
        return new ExtractAConfession(this);
    }
}
