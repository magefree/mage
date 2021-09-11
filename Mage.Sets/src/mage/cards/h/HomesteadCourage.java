package mage.cards.h;

import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.FlashbackAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.TimingRule;
import mage.counters.CounterType;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class HomesteadCourage extends CardImpl {

    public HomesteadCourage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{W}");

        // Put a +1/+1 counter on target creature you control. It gains vigilance until end of turn.
        this.getSpellAbility().addEffect(new AddCountersTargetEffect(CounterType.P1P1.createInstance()));
        this.getSpellAbility().addEffect(new GainAbilityTargetEffect(
                VigilanceAbility.getInstance(), Duration.EndOfTurn
        ).setText("It gains vigilance until end of turn"));
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent());

        // Flashback {W}
        this.addAbility(new FlashbackAbility(new ManaCostsImpl<>("{W}"), TimingRule.SORCERY));
    }

    private HomesteadCourage(final HomesteadCourage card) {
        super(card);
    }

    @Override
    public HomesteadCourage copy() {
        return new HomesteadCourage(this);
    }
}
