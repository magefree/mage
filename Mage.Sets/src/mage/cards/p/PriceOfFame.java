package mage.cards.p;

import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.SourceTargetsPermanentCondition;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.cost.SpellCostReductionSourceEffect;
import mage.abilities.effects.keyword.SurveilEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.SupertypePredicate;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class PriceOfFame extends CardImpl {

    private static final FilterPermanent filter
            = new FilterControlledCreaturePermanent("a legendary creature");

    static {
        filter.add(new SupertypePredicate(SuperType.LEGENDARY));
    }

    private static final Condition condition = new SourceTargetsPermanentCondition(filter);

    public PriceOfFame(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{3}{B}");

        // This spell costs {2} less to cast if it targets a legendary creature.
        this.addAbility(new SimpleStaticAbility(
                Zone.STACK, new SpellCostReductionSourceEffect(2, condition)
        ).setRuleAtTheTop(true));

        // Destroy target creature.
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());

        // Surveil 2.
        this.getSpellAbility().addEffect(new SurveilEffect(2));
    }

    public PriceOfFame(final PriceOfFame card) {
        super(card);
    }

    @Override
    public PriceOfFame copy() {
        return new PriceOfFame(this);
    }
}
