package mage.cards.r;

import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.SourceTargetsPermanentCondition;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.effects.common.cost.SpellCostReductionSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RidesEnd extends CardImpl {

    private static final FilterPermanent filter = new FilterCreaturePermanent("a tapped permanent");

    static {
        filter.add(TappedPredicate.TAPPED);
    }

    private static final Condition condition = new SourceTargetsPermanentCondition(filter);

    public RidesEnd(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{4}{W}");

        // This spell costs {3} less to cast if it targets a tapped permanent.
        this.addAbility(new SimpleStaticAbility(
                Zone.ALL, new SpellCostReductionSourceEffect(3, condition).setCanWorksOnStackOnly(true)
        ).setRuleAtTheTop(true));

        // Exile target creature or Vehicle.
        this.getSpellAbility().addEffect(new ExileTargetEffect());
        this.getSpellAbility().addTarget(new TargetPermanent(StaticFilters.FILTER_PERMANENT_CREATURE_OR_VEHICLE));
    }

    private RidesEnd(final RidesEnd card) {
        super(card);
    }

    @Override
    public RidesEnd copy() {
        return new RidesEnd(this);
    }
}
