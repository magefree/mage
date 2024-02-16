
package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.AttacksOrBlocksTriggeredAbility;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.dynamicvalue.common.CountersSourceCount;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;

/**
 *
 * @author jeffwadsworth
 */
public final class DuskUrchins extends CardImpl {

    public DuskUrchins(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{B}");
        this.subtype.add(SubType.OUPHE);

        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // Whenever Dusk Urchins attacks or blocks, put a -1/-1 counter on it.
        this.addAbility(new AttacksOrBlocksTriggeredAbility(new AddCountersSourceEffect(CounterType.M1M1.createInstance()).setText("put a -1/-1 counter on it"), false));

        // When Dusk Urchins dies, draw a card for each -1/-1 counter on it.
        this.addAbility(new DiesSourceTriggeredAbility(new DrawCardSourceControllerEffect(new CountersSourceCount(CounterType.M1M1)).setText("draw a card for each -1/-1 counter on it")));

    }

    private DuskUrchins(final DuskUrchins card) {
        super(card);
    }

    @Override
    public DuskUrchins copy() {
        return new DuskUrchins(this);
    }
}
