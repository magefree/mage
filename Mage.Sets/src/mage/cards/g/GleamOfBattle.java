
package mage.cards.g;

import java.util.UUID;
import mage.abilities.common.AttacksCreatureYouControlTriggeredAbility;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;

/**
 *
 * @author LevelX2
 */
public final class GleamOfBattle extends CardImpl {

    public GleamOfBattle(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{4}{R}{W}");


        // Whenever a creature you control attacks, put a +1/+1 counter on it.
        this.addAbility(new AttacksCreatureYouControlTriggeredAbility(new AddCountersTargetEffect(CounterType.P1P1.createInstance()), false, true));
    }

    private GleamOfBattle(final GleamOfBattle card) {
        super(card);
    }

    @Override
    public GleamOfBattle copy() {
        return new GleamOfBattle(this);
    }
}
