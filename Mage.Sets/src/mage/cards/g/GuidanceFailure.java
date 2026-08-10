package mage.cards.g;

import java.util.UUID;

import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.SourceTargetsPermanentCondition;
import mage.abilities.effects.common.PutOnTopOrBottomLibraryTargetEffect;
import mage.abilities.effects.common.cost.SpellCostReductionSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.predicate.Predicates;
import mage.target.TargetPermanent;

/**
 *
 * @author muz
 */
public final class GuidanceFailure extends CardImpl {

    private static final Condition condition = new SourceTargetsPermanentCondition(StaticFilters.FILTER_AN_ATTACKING_CREATURE);
    private static final FilterPermanent filter = new FilterPermanent("creature or Spacecraft");

    static {
        filter.add(Predicates.or(
            CardType.CREATURE.getPredicate(),
            SubType.SPACECRAFT.getPredicate()
        ));
    }

    public GuidanceFailure(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{3}{U}");


        // This spell costs {1} less to cast if it targets an attacking creature.
        this.addAbility(new SimpleStaticAbility(
            Zone.ALL, new SpellCostReductionSourceEffect(1, condition).setCanWorksOnStackOnly(true)
        ).setRuleAtTheTop(true));

        // Target creature or Spacecraft's owner puts it on their choice of the top or bottom of their library.
        this.getSpellAbility().addEffect(new PutOnTopOrBottomLibraryTargetEffect(false));
        this.getSpellAbility().addTarget(new TargetPermanent(filter));
    }

    private GuidanceFailure(final GuidanceFailure card) {
        super(card);
    }

    @Override
    public GuidanceFailure copy() {
        return new GuidanceFailure(this);
    }
}
