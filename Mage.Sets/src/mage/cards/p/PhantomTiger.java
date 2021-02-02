
package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.PhantomPreventionEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;

/**
 * @author Temba
 */
public final class PhantomTiger extends CardImpl {

    public PhantomTiger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");
        this.subtype.add(SubType.CAT);
        this.subtype.add(SubType.SPIRIT);
        this.power = new MageInt(1);
        this.toughness = new MageInt(0);

        // Phantom Tiger enters the battlefield with two +1/+1 counters on it.
        this.addAbility(new EntersBattlefieldAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance(2)), "with two +1/+1 counters on it"));

        // If damage would be dealt to Phantom Tiger, prevent that damage. Remove a +1/+1 counter from Phantom Tiger.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new PhantomPreventionEffect()));
    }

    private PhantomTiger(final PhantomTiger card) {
        super(card);
    }

    @Override
    public PhantomTiger copy() {
        return new PhantomTiger(this);
    }
}