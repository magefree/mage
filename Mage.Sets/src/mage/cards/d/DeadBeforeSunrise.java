package mage.cards.d;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.DamageWithPowerFromSourceToAnotherTargetEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.OutlawPredicate;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DeadBeforeSunrise extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent();

    static {
        filter.add(OutlawPredicate.instance);
    }

    public DeadBeforeSunrise(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{3}{R}");

        // Until end of turn, outlaw creatures you control get +1/+0 and gain "{T}: This creature deals damage equal to its power to target creature."
        this.getSpellAbility().addEffect(new BoostControlledEffect(
                1, 0, Duration.EndOfTurn, filter
        ).setText("until end of turn, outlaw creatures you control get +1/+0"));
        Ability ability = new SimpleActivatedAbility(
                new DamageWithPowerFromSourceToAnotherTargetEffect("this creature"), new TapSourceCost()
        );
        ability.addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addEffect(new GainAbilityControlledEffect(
                ability, Duration.EndOfTurn, filter
        ).setText("and gain \"{T}: This creature deals damage equal to its power to target creature.\""));
    }

    private DeadBeforeSunrise(final DeadBeforeSunrise card) {
        super(card);
    }

    @Override
    public DeadBeforeSunrise copy() {
        return new DeadBeforeSunrise(this);
    }
}
