
package mage.cards.k;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.CantBlockAbility;
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
 *
 * @author LoneFox

 */
public final class KavuAggressor extends CardImpl {

    public KavuAggressor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{R}");
        this.subtype.add(SubType.KAVU);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Kicker {4}
        this.addAbility(new KickerAbility("{4}"));
        // Kavu Aggressor can't block.
        this.addAbility(new CantBlockAbility());
        // If Kavu Aggressor was kicked, it enters the battlefield with a +1/+1 counter on it.
        this.addAbility(new EntersBattlefieldAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance(1)),
            KickedCondition.ONCE, "If {this} was kicked, it enters the battlefield with a +1/+1 counter on it.", ""));
    }

    private KavuAggressor(final KavuAggressor card) {
        super(card);
    }

    @Override
    public KavuAggressor copy() {
        return new KavuAggressor(this);
    }
}
