
package mage.cards.p;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldWithCountersAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.PreventDamageAndRemoveCountersEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class PhantomWurm extends CardImpl {

    public PhantomWurm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{G}{G}");
        this.subtype.add(SubType.WURM);
        this.subtype.add(SubType.SPIRIT);

        this.power = new MageInt(2);
        this.toughness = new MageInt(0);

        // Phantom Wurm enters the battlefield with four +1/+1 counters on it.
        this.addAbility(new EntersBattlefieldWithCountersAbility(CounterType.P1P1.createInstance(4)));

        // If damage would be dealt to Phantom Wurm, prevent that damage. Remove a +1/+1 counter from Phantom Wurm.
        this.addAbility(new SimpleStaticAbility(
                new PreventDamageAndRemoveCountersEffect(false, false, false).withPhantomText()
        ), PreventDamageAndRemoveCountersEffect.createWatcher());
    }

    private PhantomWurm(final PhantomWurm card) {
        super(card);
    }

    @Override
    public PhantomWurm copy() {
        return new PhantomWurm(this);
    }
}
