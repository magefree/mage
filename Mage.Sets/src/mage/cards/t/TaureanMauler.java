
package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SpellCastOpponentTriggeredAbility;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.ChangelingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;

/**
 *
 * @author jeffwadsworth
 */
public final class TaureanMauler extends CardImpl {

    public TaureanMauler(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{R}");
        this.subtype.add(SubType.SHAPESHIFTER);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Changeling
        this.addAbility(new ChangelingAbility());
        
        // Whenever an opponent casts a spell, you may put a +1/+1 counter on Taurean Mauler.
        this.addAbility(new SpellCastOpponentTriggeredAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance()), true));
        
    }

    private TaureanMauler(final TaureanMauler card) {
        super(card);
    }

    @Override
    public TaureanMauler copy() {
        return new TaureanMauler(this);
    }
}
