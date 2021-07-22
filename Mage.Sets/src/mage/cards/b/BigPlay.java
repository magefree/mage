package mage.cards.b;

import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.counters.CounterType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BigPlay extends CardImpl {

    public BigPlay(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{G}");

        // Target creature gets +2/+2 and gains reach until end of turn. Put a +1/+1 counter on it.
        this.getSpellAbility().addEffect(new BoostTargetEffect(
                2, 2
        ).setText("target creature gets +2/+2"));
        this.getSpellAbility().addEffect(new GainAbilityTargetEffect(
                ReachAbility.getInstance(), Duration.EndOfTurn
        ).setText("and gains reach until end of turn"));
        this.getSpellAbility().addEffect(new AddCountersTargetEffect(
                CounterType.P1P1.createInstance()
        ).setText("Put a +1/+1 counter on it"));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private BigPlay(final BigPlay card) {
        super(card);
    }

    @Override
    public BigPlay copy() {
        return new BigPlay(this);
    }
}
