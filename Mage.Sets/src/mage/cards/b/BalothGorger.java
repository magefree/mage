
package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.condition.common.KickedCondition;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.KickerAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;

/**
 * @author CountAndromalius
 */
public final class BalothGorger extends CardImpl {

    public BalothGorger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{G}{G}");
        this.subtype.add(SubType.BEAST);

        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Kicker {4}
        this.addAbility(new KickerAbility("{4}"));

        // If Baloth Gorger was kicked, it enters the battlefield with three +1/+1 counters on it
        Ability ability = new EntersBattlefieldAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance(3)),
                KickedCondition.ONCE,
                "If {this} was kicked, it enters the battlefield with three +1/+1 counters on it",
                "");
        this.addAbility(ability);
    }

    private BalothGorger(final BalothGorger card) {
        super(card);
    }

    @Override
    public BalothGorger copy() {
        return new BalothGorger(this);
    }

}
