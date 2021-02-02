
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.BecomesBlockedSourceTriggeredAbility;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;

/**
 *
 * @author Loki
 */
public final class SlithStrider extends CardImpl {

    public SlithStrider(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{U}{U}");
        this.subtype.add(SubType.SLITH);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);
        this.addAbility(new BecomesBlockedSourceTriggeredAbility(new DrawCardSourceControllerEffect(1), false));
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance(1)), false));
    }

    private SlithStrider(final SlithStrider card) {
        super(card);
    }

    @Override
    public SlithStrider copy() {
        return new SlithStrider(this);
    }
}
