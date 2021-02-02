
package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.PhantomPreventionEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.ProtectionAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;

/**
 * @author LevelX2
 */
public final class PhantomCentaur extends CardImpl {

    public PhantomCentaur(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}{G}");
        this.subtype.add(SubType.CENTAUR);
        this.subtype.add(SubType.SPIRIT);

        this.power = new MageInt(2);
        this.toughness = new MageInt(0);

        // Protection from black
        this.addAbility(ProtectionAbility.from(ObjectColor.BLACK));

        // Phantom Centaur enters the battlefield with three +1/+1 counters on it.
        this.addAbility(new EntersBattlefieldAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance(3)), "with three +1/+1 counters on it"));

        // If damage would be dealt to Phantom Centaur, prevent that damage. Remove a +1/+1 counter from Phantom Centaur.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new PhantomPreventionEffect()));
    }

    private PhantomCentaur(final PhantomCentaur card) {
        super(card);
    }

    @Override
    public PhantomCentaur copy() {
        return new PhantomCentaur(this);
    }
}