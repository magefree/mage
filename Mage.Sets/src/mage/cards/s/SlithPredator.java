
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;

/**
 *
 * @author Loki
 */
public final class SlithPredator extends CardImpl {

    public SlithPredator(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{G}{G}");
        this.subtype.add(SubType.SLITH);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);
        this.addAbility(TrampleAbility.getInstance());
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance(1)), false));
    }

    private SlithPredator(final SlithPredator card) {
        super(card);
    }

    @Override
    public SlithPredator copy() {
        return new SlithPredator(this);
    }
}
