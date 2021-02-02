
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;

/**
 *
 * @author Loki
 */
public final class SlithFirewalker extends CardImpl {

    public SlithFirewalker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{R}{R}");
        this.subtype.add(SubType.SLITH);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);
        this.addAbility(HasteAbility.getInstance());
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance(1)), false));
    }

    private SlithFirewalker(final SlithFirewalker card) {
        super(card);
    }

    @Override
    public SlithFirewalker copy() {
        return new SlithFirewalker(this);
    }
}
