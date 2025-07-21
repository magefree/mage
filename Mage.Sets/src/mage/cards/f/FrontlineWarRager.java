package mage.cards.f;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.condition.common.TwoTappedCreaturesCondition;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.triggers.BeginningOfEndStepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class FrontlineWarRager extends CardImpl {

    public FrontlineWarRager(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");

        this.subtype.add(SubType.KAVU);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // At the beginning of your end step, if you control two or more tapped creatures, put a +1/+1 counter on this creature.
        Ability ability = new BeginningOfEndStepTriggeredAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance())
        ).withInterveningIf(TwoTappedCreaturesCondition.instance);
        this.addAbility(ability.addHint(TwoTappedCreaturesCondition.getHint()));
    }

    private FrontlineWarRager(final FrontlineWarRager card) {
        super(card);
    }

    @Override
    public FrontlineWarRager copy() {
        return new FrontlineWarRager(this);
    }
}
