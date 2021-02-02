
package mage.cards.c;

import java.util.UUID;
import mage.abilities.effects.common.counter.AddCountersAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;
import mage.filter.common.FilterAttackingCreature;

/**
 *
 * @author North
 */
public final class ChokingFumes extends CardImpl {

    private static final FilterAttackingCreature filter = new FilterAttackingCreature();

    public ChokingFumes(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{2}{W}");


        this.getSpellAbility().addEffect(new AddCountersAllEffect(CounterType.M1M1.createInstance(), filter));
    }

    private ChokingFumes(final ChokingFumes card) {
        super(card);
    }

    @Override
    public ChokingFumes copy() {
        return new ChokingFumes(this);
    }
}
