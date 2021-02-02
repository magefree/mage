
package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.InspiredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;

/**
 *
 * @author LevelX2
 */
public final class PheresBandTromper extends CardImpl {

    public PheresBandTromper(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{G}");
        this.subtype.add(SubType.CENTAUR);
        this.subtype.add(SubType.WARRIOR);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // <i>Inspired</i> &mdash; Whenever Pheres-Band Tromper becomes untapped, put a +1/+1 counter on it.
        this.addAbility(new InspiredAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance())));
    }

    private PheresBandTromper(final PheresBandTromper card) {
        super(card);
    }

    @Override
    public PheresBandTromper copy() {
        return new PheresBandTromper(this);
    }
}
