package mage.cards.d;

import java.util.UUID;

import mage.abilities.effects.common.DamageWithPowerFromOneToAnotherTargetEffect;
import mage.abilities.effects.common.continuous.SetBasePowerToughnessTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardSetInfo;
import mage.cards.SplitCard;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SpellAbilityType;
import mage.counters.CounterType;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetOpponentsCreaturePermanent;

/**
 *
 * @author muz
 */
public final class DoubleJumpFlyingKick extends SplitCard {

    public DoubleJumpFlyingKick(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{U}", "{1}{R}", SpellAbilityType.SPLIT_FUSED);

        // Double Jump
        // Put a flying counter on target creature you control. Until end of turn, it has base power and toughness 5/5.
        getLeftHalfCard().getSpellAbility().addEffect(new AddCountersTargetEffect(CounterType.FLYING.createInstance()));
        getLeftHalfCard().getSpellAbility().addEffect(new SetBasePowerToughnessTargetEffect(5, 5, Duration.EndOfTurn)
            .setText("Until end of turn, it has base power and toughness 5/5"));
        getLeftHalfCard().getSpellAbility().addTarget(new TargetControlledCreaturePermanent()
            .withChooseHint("flying counter and base 5/5 until end of turn"));

        // Flying Kick
        // Target creature you control deals damage equal to its power to target creature an opponent controls.
        getRightHalfCard().getSpellAbility().addEffect(new DamageWithPowerFromOneToAnotherTargetEffect());
        getRightHalfCard().getSpellAbility().addTarget(new TargetControlledCreaturePermanent()
            .withChooseHint("deals damage equal to its power"));
        getRightHalfCard().getSpellAbility().addTarget(new TargetOpponentsCreaturePermanent()
            .withChooseHint("target creature an opponent controls"));
    }

    private DoubleJumpFlyingKick(final DoubleJumpFlyingKick card) {
        super(card);
    }

    @Override
    public DoubleJumpFlyingKick copy() {
        return new DoubleJumpFlyingKick(this);
    }
}
