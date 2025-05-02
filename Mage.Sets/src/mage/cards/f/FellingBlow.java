package mage.cards.f;

import mage.abilities.effects.common.DamageWithPowerFromOneToAnotherTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetOpponentsCreaturePermanent;

import java.util.UUID;

/**
 *
 * @author ciaccona007
 */
public final class FellingBlow extends CardImpl {

    public FellingBlow(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{G}");

        // Put a +1/+1 counter on target creature you control. Then that creature deals damage equal to its power to target creature an opponent controls.
        this.getSpellAbility().addEffect(new AddCountersTargetEffect(CounterType.P1P1.createInstance()));
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent());
        this.getSpellAbility().addEffect(new DamageWithPowerFromOneToAnotherTargetEffect("that creature").concatBy("Then"));
        this.getSpellAbility().addTarget(new TargetOpponentsCreaturePermanent());
    }

    private FellingBlow(final FellingBlow card) {
        super(card);
    }

    @Override
    public FellingBlow copy() {
        return new FellingBlow(this);
    }
}
