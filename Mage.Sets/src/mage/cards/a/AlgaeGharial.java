
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.ShroudAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;

/**
 *
 * @author North
 */
public final class AlgaeGharial extends CardImpl {

    public AlgaeGharial(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{G}");
        this.subtype.add(SubType.CROCODILE);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        this.addAbility(ShroudAbility.getInstance());
        // Whenever another creature dies, you may put a +1/+1 counter on Algae Gharial.
        this.addAbility(new DiesCreatureTriggeredAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance()), true, true));
    }

    private AlgaeGharial(final AlgaeGharial card) {
        super(card);
    }

    @Override
    public AlgaeGharial copy() {
        return new AlgaeGharial(this);
    }
}
