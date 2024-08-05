package mage.cards.d;

import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.SourceTargetsPermanentCondition;
import mage.abilities.effects.common.PutOnTopOrBottomLibraryTargetEffect;
import mage.abilities.effects.common.cost.SpellCostReductionSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.permanent.AttackingPredicate;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DireDowndraft extends CardImpl {

    private static final FilterPermanent filter
            = new FilterCreaturePermanent("an attacking or tapped creature");

    static {
        filter.add(Predicates.or(
                AttackingPredicate.instance,
                TappedPredicate.TAPPED
        ));
    }

    private static final Condition condition = new SourceTargetsPermanentCondition(filter);

    public DireDowndraft(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{3}{U}");

        // This spell costs {1} less to cast if it targets an attacking or tapped creature.
        this.addAbility(new SimpleStaticAbility(
                Zone.ALL, new SpellCostReductionSourceEffect(1, condition).setCanWorksOnStackOnly(true)
        ).setRuleAtTheTop(true));

        // Target creature's owner puts it on the top or bottom of their library.
        this.getSpellAbility().addEffect(new PutOnTopOrBottomLibraryTargetEffect(false));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private DireDowndraft(final DireDowndraft card) {
        super(card);
    }

    @Override
    public DireDowndraft copy() {
        return new DireDowndraft(this);
    }
}
