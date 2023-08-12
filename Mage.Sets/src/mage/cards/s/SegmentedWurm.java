
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SourceBecomesTargetTriggeredAbility;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;

/**
 *
 * @author North
 */
public final class SegmentedWurm extends CardImpl {

    public SegmentedWurm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{R}{G}");
        this.subtype.add(SubType.WURM);

        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        this.addAbility(new SourceBecomesTargetTriggeredAbility(new AddCountersSourceEffect(CounterType.M1M1.createInstance())));
    }

    private SegmentedWurm(final SegmentedWurm card) {
        super(card);
    }

    @Override
    public SegmentedWurm copy() {
        return new SegmentedWurm(this);
    }
}
