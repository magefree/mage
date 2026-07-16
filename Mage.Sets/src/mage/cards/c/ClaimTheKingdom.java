package mage.cards.c;

import java.util.UUID;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.abilities.Ability;
import mage.abilities.common.LandfallAbility;
import mage.abilities.common.PlanCounterThresholdTriggeredAbility;
import mage.abilities.common.delayed.ReflexiveTriggeredAbility;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class ClaimTheKingdom extends CardImpl {

    public ClaimTheKingdom(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{G}");

        this.subtype.add(SubType.PLAN);

        // Landfall -- Whenever a land you control enters, put a +1/+1 counter on target creature you control and a plan counter on this enchantment.
        Ability ability = new LandfallAbility(new AddCountersTargetEffect(CounterType.P1P1.createInstance()));
        ability.addTarget(new TargetControlledCreaturePermanent());
        ability.addEffect(new AddCountersSourceEffect(CounterType.PLAN.createInstance()).concatBy("and"));
        this.addAbility(ability);

        // When the fourth plan counter is put on this enchantment, sacrifice it. When you do, put an indestructible counter on target creature you control.
        ReflexiveTriggeredAbility reflexive = new ReflexiveTriggeredAbility(
            new AddCountersTargetEffect(CounterType.INDESTRUCTIBLE.createInstance()), false,
            "put an indestructible counter on target creature you control"
        );
        reflexive.addTarget(new TargetControlledCreaturePermanent());
        this.addAbility(new PlanCounterThresholdTriggeredAbility(4, reflexive));
    }

    private ClaimTheKingdom(final ClaimTheKingdom card) {
        super(card);
    }

    @Override
    public ClaimTheKingdom copy() {
        return new ClaimTheKingdom(this);
    }
}
