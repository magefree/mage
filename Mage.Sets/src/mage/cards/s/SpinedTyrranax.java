package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.delayed.ReflexiveTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DoWhenCostPaid;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.abilities.triggers.BeginningOfCombatTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SpinedTyrranax extends CardImpl {

    public SpinedTyrranax(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{G}");

        this.subtype.add(SubType.DINOSAUR);
        this.subtype.add(SubType.BEAST);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // At the beginning of combat on your turn, you may pay {2}{G}. When you do, put a +1/+1 counter on target creature. That creature gains trample until end of turn.
        ReflexiveTriggeredAbility ability = new ReflexiveTriggeredAbility(
                new AddCountersTargetEffect(CounterType.P1P1.createInstance()), false
        );
        ability.addEffect(new GainAbilityTargetEffect(TrampleAbility.getInstance())
                .setText("that creature gains trample until end of turn"));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(new BeginningOfCombatTriggeredAbility(new DoWhenCostPaid(
                ability, new ManaCostsImpl<>("{2}{G}"), "Pay {2}{G}?"
        )));
    }

    private SpinedTyrranax(final SpinedTyrranax card) {
        super(card);
    }

    @Override
    public SpinedTyrranax copy() {
        return new SpinedTyrranax(this);
    }
}
