package mage.cards.m;

import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MaximumOverdrive extends CardImpl {

    public MaximumOverdrive(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{B}");

        // Put a +1/+1 counter on target creature. It gains deathtouch and indestructible until end of turn.
        this.getSpellAbility().addEffect(new AddCountersTargetEffect(CounterType.P1P1.createInstance()));
        this.getSpellAbility().addEffect(new GainAbilityTargetEffect(DeathtouchAbility.getInstance())
                .setText("it gains deathtouch"));
        this.getSpellAbility().addEffect(new GainAbilityTargetEffect(IndestructibleAbility.getInstance())
                .setText("and indestructible until end of turn"));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private MaximumOverdrive(final MaximumOverdrive card) {
        super(card);
    }

    @Override
    public MaximumOverdrive copy() {
        return new MaximumOverdrive(this);
    }
}
