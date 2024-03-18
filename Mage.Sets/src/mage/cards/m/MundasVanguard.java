package mage.cards.m;

import mage.MageInt;
import mage.abilities.abilityword.CohortAbility;
import mage.abilities.effects.common.counter.AddCountersAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 *
 * @author fireshoes
 */
public final class MundasVanguard extends CardImpl {

    public MundasVanguard(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{W}");
        this.subtype.add(SubType.KOR);
        this.subtype.add(SubType.KNIGHT);
        this.subtype.add(SubType.ALLY);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // <i>Cohort</i> &mdash; {T}, Tap an untapped Ally you control: Put a +1/+1 counter on each creature you control.
        this.addAbility(new CohortAbility(new AddCountersAllEffect(CounterType.P1P1.createInstance(), StaticFilters.FILTER_CONTROLLED_CREATURE)));
    }

    private MundasVanguard(final MundasVanguard card) {
        super(card);
    }

    @Override
    public MundasVanguard copy() {
        return new MundasVanguard(this);
    }
}
