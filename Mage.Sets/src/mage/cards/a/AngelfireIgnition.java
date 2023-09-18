package mage.cards.a;

import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.*;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.TimingRule;
import mage.counters.CounterType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AngelfireIgnition extends CardImpl {

    public AngelfireIgnition(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{R}{W}");

        // Put two +1/+1 counters on target creature. It gains vigilance, trample, lifelink, indestructible, and haste until end of turn.
        this.getSpellAbility().addEffect(new AddCountersTargetEffect(CounterType.P1P1.createInstance(2)));
        this.getSpellAbility().addEffect(new GainAbilityTargetEffect(
                VigilanceAbility.getInstance(), Duration.EndOfTurn
        ).setText("It gains vigilance"));
        this.getSpellAbility().addEffect(new GainAbilityTargetEffect(
                TrampleAbility.getInstance(), Duration.EndOfTurn
        ).setText(", trample"));
        this.getSpellAbility().addEffect(new GainAbilityTargetEffect(
                LifelinkAbility.getInstance(), Duration.EndOfTurn
        ).setText(", lifelink"));
        this.getSpellAbility().addEffect(new GainAbilityTargetEffect(
                IndestructibleAbility.getInstance(), Duration.EndOfTurn
        ).setText(", indestructible"));
        this.getSpellAbility().addEffect(new GainAbilityTargetEffect(
                HasteAbility.getInstance(), Duration.EndOfTurn
        ).setText(", and haste until end of turn"));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());

        // Flashback {2}{R}{W}
        this.addAbility(new FlashbackAbility(this, new ManaCostsImpl<>("{2}{R}{W}")));
    }

    private AngelfireIgnition(final AngelfireIgnition card) {
        super(card);
    }

    @Override
    public AngelfireIgnition copy() {
        return new AngelfireIgnition(this);
    }
}
