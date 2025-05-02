package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldOrDiesSourceTriggeredAbility;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SevenTailMentor extends CardImpl {

    public SevenTailMentor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}");

        this.subtype.add(SubType.FOX);
        this.subtype.add(SubType.SAMURAI);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // When Seven-Tail Mentor enters the battlefield or dies, put a +1/+1 counter on target creature or Vehicle you control.
        Ability ability = new EntersBattlefieldOrDiesSourceTriggeredAbility(
                new AddCountersTargetEffect(CounterType.P1P1.createInstance()), false
        );
        ability.addTarget(new TargetPermanent(StaticFilters.FILTER_CONTROLLED_PERMANENT_CREATURE_OR_VEHICLE));
        this.addAbility(ability);
    }

    private SevenTailMentor(final SevenTailMentor card) {
        super(card);
    }

    @Override
    public SevenTailMentor copy() {
        return new SevenTailMentor(this);
    }
}
