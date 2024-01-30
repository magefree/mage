package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.delayed.ReflexiveTriggeredAbility;
import mage.abilities.costs.common.CollectEvidenceCost;
import mage.abilities.effects.common.DoWhenCostPaid;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SampleCollector extends CardImpl {

    public SampleCollector(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");

        this.subtype.add(SubType.TROLL);
        this.subtype.add(SubType.DETECTIVE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Whenever Sample Collector attacks, you may collect evidence 3. When you do, put a +1/+1 counter on target creature you control.
        ReflexiveTriggeredAbility ability = new ReflexiveTriggeredAbility(
                new AddCountersTargetEffect(CounterType.P1P1.createInstance()), false
        );
        ability.addTarget(new TargetControlledCreaturePermanent());
        this.addAbility(new AttacksTriggeredAbility(new DoWhenCostPaid(
                ability, new CollectEvidenceCost(3), "Collect evidence 3?"
        )));
    }

    private SampleCollector(final SampleCollector card) {
        super(card);
    }

    @Override
    public SampleCollector copy() {
        return new SampleCollector(this);
    }
}
