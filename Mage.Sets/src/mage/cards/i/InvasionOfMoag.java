package mage.cards.i;

import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SiegeAbility;
import mage.abilities.effects.common.counter.AddCountersAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class InvasionOfMoag extends CardImpl {

    public InvasionOfMoag(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.BATTLE}, "{2}{G}{W}");

        this.subtype.add(SubType.SIEGE);
        this.setStartingDefense(5);
        this.secondSideCardClazz = mage.cards.b.BloomwielderDryads.class;

        // (As a Siege enters, choose an opponent to protect it. You and others can attack it. When it's defeated, exile it, then cast it transformed.)
        this.addAbility(new SiegeAbility());

        // When Invasion of Moag enters the battlefield, put a +1/+1 counter on each creature you control.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new AddCountersAllEffect(
                CounterType.P1P1.createInstance(), StaticFilters.FILTER_CONTROLLED_CREATURE
        )));
    }

    private InvasionOfMoag(final InvasionOfMoag card) {
        super(card);
    }

    @Override
    public InvasionOfMoag copy() {
        return new InvasionOfMoag(this);
    }
}
