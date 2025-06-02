package mage.cards.l;

import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.FlyingAbility;
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
public final class LightfootTechnique extends CardImpl {

    public LightfootTechnique(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{W}");

        // Put a +1/+1 counter on target creature. It gains flying and indestructible until end of turn.
        this.getSpellAbility().addEffect(new AddCountersTargetEffect(CounterType.P1P1.createInstance()));
        this.getSpellAbility().addEffect(new GainAbilityTargetEffect(FlyingAbility.getInstance()).setText("it gains flying"));
        this.getSpellAbility().addEffect(new GainAbilityTargetEffect(IndestructibleAbility.getInstance()).setText("and indestructible until end of turn"));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private LightfootTechnique(final LightfootTechnique card) {
        super(card);
    }

    @Override
    public LightfootTechnique copy() {
        return new LightfootTechnique(this);
    }
}
