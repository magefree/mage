package mage.cards.t;

import mage.MageInt;
import mage.abilities.common.CastSecondSpellTriggeredAbility;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ThunderDrake extends CardImpl {

    public ThunderDrake(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}");

        this.subtype.add(SubType.ELEMENTAL);
        this.subtype.add(SubType.DRAKE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever you cast you cast your second spell each turn, put a +1/+1 counter on Thunder Drake.
        this.addAbility(new CastSecondSpellTriggeredAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance())
        ));
    }

    private ThunderDrake(final ThunderDrake card) {
        super(card);
    }

    @Override
    public ThunderDrake copy() {
        return new ThunderDrake(this);
    }
}
