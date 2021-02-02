
package mage.cards.f;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.common.counter.AddCountersAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.common.FilterAttackingCreature;

/**
 *
 * @author emerald000
 */
public final class FangrenFirstborn extends CardImpl {

    public FangrenFirstborn(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{G}{G}{G}");
        this.subtype.add(SubType.BEAST);

        this.power = new MageInt(4);
        this.toughness = new MageInt(2);

        // Whenever Fangren Firstborn attacks, put a +1/+1 counter on each attacking creature.
        this.addAbility(new AttacksTriggeredAbility(new AddCountersAllEffect(CounterType.P1P1.createInstance(), new FilterAttackingCreature()), false));
    }

    private FangrenFirstborn(final FangrenFirstborn card) {
        super(card);
    }

    @Override
    public FangrenFirstborn copy() {
        return new FangrenFirstborn(this);
    }
}
