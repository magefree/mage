
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.HeroicAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;

/**
 *
 * @author LevelX2
 */
public final class SatyrHoplite extends CardImpl {

    public SatyrHoplite(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{R}");
        this.subtype.add(SubType.SATYR);
        this.subtype.add(SubType.SOLDIER);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Heroic - Whenever you cast a spell that targets Satyr Hoplite, put a +1/+1 counter on Satyr Hoplite.
        this.addAbility(new HeroicAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance())));
    }

    private SatyrHoplite(final SatyrHoplite card) {
        super(card);
    }

    @Override
    public SatyrHoplite copy() {
        return new SatyrHoplite(this);
    }
}
