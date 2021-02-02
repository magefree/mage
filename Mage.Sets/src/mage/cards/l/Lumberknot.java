
package mage.cards.l;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.HexproofAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;

/**
 *
 * @author North
 */
public final class Lumberknot extends CardImpl {

    public Lumberknot(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{G}{G}");
        this.subtype.add(SubType.TREEFOLK);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        this.addAbility(HexproofAbility.getInstance());
        // Whenever a creature dies, put a +1/+1 counter on Lumberknot.
        this.addAbility(new DiesCreatureTriggeredAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance()), false));
    }

    private Lumberknot(final Lumberknot card) {
        super(card);
    }

    @Override
    public Lumberknot copy() {
        return new Lumberknot(this);
    }
}
