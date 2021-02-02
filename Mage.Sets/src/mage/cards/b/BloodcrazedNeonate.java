
package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.AttacksEachCombatStaticAbility;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
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
public final class BloodcrazedNeonate extends CardImpl {

    public BloodcrazedNeonate(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{R}");
        this.subtype.add(SubType.VAMPIRE);

        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Bloodcrazed Neonate attacks each turn if able.
        this.addAbility(new AttacksEachCombatStaticAbility());
        // Whenever Bloodcrazed Neonate deals combat damage to a player, put a +1/+1 counter on it.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance()), false));
    }

    private BloodcrazedNeonate(final BloodcrazedNeonate card) {
        super(card);
    }

    @Override
    public BloodcrazedNeonate copy() {
        return new BloodcrazedNeonate(this);
    }
}
