
package mage.cards.p;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.PreventDamageAndRemoveCountersEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class PhantomNomad extends CardImpl {

    public PhantomNomad(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");
        this.subtype.add(SubType.SPIRIT);
        this.subtype.add(SubType.NOMAD);

        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Phantom Nomad enters the battlefield with two +1/+1 counters on it.
        this.addAbility(new EntersBattlefieldAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance(2)),
                "with two +1/+1 counters on it"));

        // If damage would be dealt to Phantom Nomad, prevent that damage. Remove a +1/+1 counter from Phantom Nomad.
        this.addAbility(new SimpleStaticAbility(
                new PreventDamageAndRemoveCountersEffect(false, false, false).withPhantomText()
        ), PreventDamageAndRemoveCountersEffect.createWatcher());


    }

    private PhantomNomad(final PhantomNomad card) {
        super(card);
    }

    @Override
    public PhantomNomad copy() {
        return new PhantomNomad(this);
    }
}