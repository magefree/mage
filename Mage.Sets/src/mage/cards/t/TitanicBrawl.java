package mage.cards.t;

import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.SourceTargetsPermanentCondition;
import mage.abilities.effects.common.FightTargetsEffect;
import mage.abilities.effects.common.cost.SpellCostReductionSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.filter.predicate.permanent.CounterPredicate;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TitanicBrawl extends CardImpl {

    private static final FilterCreaturePermanent filter
            = new FilterCreaturePermanent("creature you don't control");
    private static final FilterPermanent filter2
            = new FilterControlledCreaturePermanent("a creature you control with a +1/+1 counter on it");

    static {
        filter.add(new ControllerPredicate(TargetController.NOT_YOU));
        filter2.add(new CounterPredicate(CounterType.P1P1));
    }

    private static final Condition condition = new SourceTargetsPermanentCondition(filter2);

    public TitanicBrawl(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{G}");

        // This spell costs {1} less to cast if it targets a creature you control with a +1/+1 counter on it.
        this.addAbility(new SimpleStaticAbility(
                Zone.STACK, new SpellCostReductionSourceEffect(1, condition)
        ).setRuleAtTheTop(true));

        // Target creature you control fights target creature you don't control.
        this.getSpellAbility().addEffect(new FightTargetsEffect());
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(filter));
    }

    private TitanicBrawl(final TitanicBrawl card) {
        super(card);
    }

    @Override
    public TitanicBrawl copy() {
        return new TitanicBrawl(this);
    }
}
