
package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.DealsDamageGainLifeSourceTriggeredAbility;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.PhantomPreventionEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;

/**
 * @author fireshoes
 */
public final class PhantomNishoba extends CardImpl {

    public PhantomNishoba(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{G}{W}");
        this.subtype.add(SubType.CAT);
        this.subtype.add(SubType.BEAST);
        this.subtype.add(SubType.SPIRIT);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Phantom Nishoba enters the battlefield with seven +1/+1 counters on it.
        this.addAbility(new EntersBattlefieldAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance(7), true), "with seven +1/+1 counters on it"));

        // Whenever Phantom Nishoba deals damage, you gain that much life.
        this.addAbility(new DealsDamageGainLifeSourceTriggeredAbility());

        // If damage would be dealt to Phantom Nishoba, prevent that damage. Remove a +1/+1 counter from Phantom Nishoba.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new PhantomPreventionEffect()));
    }

    private PhantomNishoba(final PhantomNishoba card) {
        super(card);
    }

    @Override
    public PhantomNishoba copy() {
        return new PhantomNishoba(this);
    }
}