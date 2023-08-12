
package mage.cards.u;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.LandfallAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.PreventDamageAndRemoveCountersEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;

/**
 *
 * @author fireshoes
 */
public final class UndergrowthChampion extends CardImpl {

    public UndergrowthChampion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{G}{G}");
        this.subtype.add(SubType.ELEMENTAL);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // If damage would be dealt to Undergrowth Champion while it has a +1/+1 counter on it, prevent that damage and remove a +1/+1 counter from Undergrowth Champion.
        this.addAbility(new SimpleStaticAbility(new PreventDamageAndRemoveCountersEffect(false, true, false)));

        // <i>Landfall</i>-Whenever a land enters the battlefield under your control, put a +1/+1 counter on Undergrowth Champion.
        this.addAbility(new LandfallAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance()), false));
    }

    private UndergrowthChampion(final UndergrowthChampion card) {
        super(card);
    }

    @Override
    public UndergrowthChampion copy() {
        return new UndergrowthChampion(this);
    }
}
