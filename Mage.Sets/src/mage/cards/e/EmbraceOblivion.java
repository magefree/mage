package mage.cards.e;

import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.predicate.Predicates;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class EmbraceOblivion extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("creature or Spacecraft");

    static {
        filter.add(Predicates.or(
                CardType.CREATURE.getPredicate(),
                SubType.SPACECRAFT.getPredicate()
        ));
    }

    public EmbraceOblivion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{B}");

        // As an additional cost to cast this spell, sacrifice an artifact or creature.
        this.getSpellAbility().addCost(new SacrificeTargetCost(StaticFilters.FILTER_PERMANENT_ARTIFACT_OR_CREATURE));

        // Destroy target creature or Spacecraft.
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addTarget(new TargetPermanent(filter));
    }

    private EmbraceOblivion(final EmbraceOblivion card) {
        super(card);
    }

    @Override
    public EmbraceOblivion copy() {
        return new EmbraceOblivion(this);
    }
}
