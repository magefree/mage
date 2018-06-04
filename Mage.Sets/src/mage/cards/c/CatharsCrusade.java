
package mage.cards.c;

import java.util.UUID;
import mage.abilities.common.CreatureEntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.counter.AddCountersAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;
import mage.filter.common.FilterControlledCreaturePermanent;

/**
 *
 * @author Loki
 */
public final class CatharsCrusade extends CardImpl {

    public CatharsCrusade(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{3}{W}{W}");


        // Whenever a creature enters the battlefield under your control, put a +1/+1 counter on each creature you control.
        this.addAbility(new CreatureEntersBattlefieldTriggeredAbility(new AddCountersAllEffect(CounterType.P1P1.createInstance(), new FilterControlledCreaturePermanent())));
    }

    public CatharsCrusade(final CatharsCrusade card) {
        super(card);
    }

    @Override
    public CatharsCrusade copy() {
        return new CatharsCrusade(this);
    }
}
