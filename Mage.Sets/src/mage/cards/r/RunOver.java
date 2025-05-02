package mage.cards.r;

import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.SourceTargetsPermanentCondition;
import mage.abilities.effects.common.DamageWithPowerFromOneToAnotherTargetEffect;
import mage.abilities.effects.common.cost.SpellCostReductionSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetOpponentsCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RunOver extends CardImpl {

    private static final FilterPermanent filter
            = new FilterControlledPermanent("a Mount or Vehicle you control");

    static {
        filter.add(Predicates.or(
                SubType.MOUNT.getPredicate(),
                SubType.VEHICLE.getPredicate()
        ));
    }

    private static final Condition condition = new SourceTargetsPermanentCondition(filter);

    public RunOver(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{G}");

        // This spell costs {1} less to cast if it targets a Mount or Vehicle you control.
        this.addAbility(new SimpleStaticAbility(
                Zone.ALL, new SpellCostReductionSourceEffect(1, condition).setCanWorksOnStackOnly(true)
        ).setRuleAtTheTop(true));

        // Target creature you control deals damage equal to its power to target creature an opponent controls.
        this.getSpellAbility().addEffect(new DamageWithPowerFromOneToAnotherTargetEffect());
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent());
        this.getSpellAbility().addTarget(new TargetOpponentsCreaturePermanent());
    }

    private RunOver(final RunOver card) {
        super(card);
    }

    @Override
    public RunOver copy() {
        return new RunOver(this);
    }
}
