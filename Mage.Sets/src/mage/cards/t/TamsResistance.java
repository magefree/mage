package mage.cards.t;

import java.util.UUID;

import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.effects.keyword.EmpowerJaceEffect;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author muz
 */
public final class TamsResistance extends CardImpl {

    public TamsResistance(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{G/U}");

        // Put a +1/+1 counter on up to one target creature. It gains vigilance until end of turn.
        this.getSpellAbility().addEffect(
            new AddCountersTargetEffect(CounterType.P1P1.createInstance())
                .setText("Put a +1/+1 counter on up to one target creature")
        );
        this.getSpellAbility().addEffect(
            new GainAbilityTargetEffect(VigilanceAbility.getInstance())
                .setText("It gains vigilance until end of turn")
        );
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(0, 1));

        // Empower Jace 4.
        this.getSpellAbility().addEffect(new EmpowerJaceEffect(4).concatBy("<br>"));
    }

    private TamsResistance(final TamsResistance card) {
        super(card);
    }

    @Override
    public TamsResistance copy() {
        return new TamsResistance(this);
    }
}
