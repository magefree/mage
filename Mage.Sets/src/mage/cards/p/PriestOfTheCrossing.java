package mage.cards.p;

import mage.MageInt;
import mage.abilities.dynamicvalue.common.CreaturesYouControlDiedCount;
import mage.abilities.effects.common.counter.AddCountersAllEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.triggers.BeginningOfEndStepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.counters.CounterType;
import mage.filter.StaticFilters;

import java.util.UUID;

public class PriestOfTheCrossing extends CardImpl {
    public PriestOfTheCrossing(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}");

        this.addSubType(SubType.ZOMBIE);
        this.addSubType(SubType.BIRD);
        this.addSubType(SubType.CLERIC);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // At the beginning of each end step, put X +1/+1 counters on each creature you control, where X is the number of creatures that died under your control this turn.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(
                TargetController.ANY,
                new AddCountersAllEffect(
                        CounterType.P1P1.createInstance(), CreaturesYouControlDiedCount.instance,
                        StaticFilters.FILTER_CONTROLLED_CREATURE
                ).setText("put X +1/+1 counters on each creature you control, where X is " +
                        "the number of creatures that died under your control this turn"), false
        ));
    }

    public PriestOfTheCrossing(PriestOfTheCrossing card) {
        super(card);
    }

    @Override
    public PriestOfTheCrossing copy() {
        return new PriestOfTheCrossing(this);
    }
}
