
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.TurnedFaceUpAllTriggeredAbility;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;

/**
 *
 * @author LoneFox
 */
public final class AvenFarseer extends CardImpl {

    public AvenFarseer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{W}");
        this.subtype.add(SubType.BIRD);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // Whenever a permanent is turned face up, put a +1/+1 counter on Aven Farseer.
        this.addAbility(new TurnedFaceUpAllTriggeredAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance()), new FilterPermanent("a permanent")));
    }

    private AvenFarseer(final AvenFarseer card) {
        super(card);
    }

    @Override
    public AvenFarseer copy() {
        return new AvenFarseer(this);
    }
}
