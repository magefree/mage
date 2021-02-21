
package mage.cards.k;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldAllTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;

/**
 *
 * @author LoneFox
 */
public final class KavuMonarch extends CardImpl {

    private static final FilterCreaturePermanent filter1 = new FilterCreaturePermanent("Kavu creatures");
    private static final FilterCreaturePermanent filter2 = new FilterCreaturePermanent("another Kavu");

    static {
        filter1.add(SubType.KAVU.getPredicate());
        filter2.add(SubType.KAVU.getPredicate());
        filter2.add(AnotherPredicate.instance);
    }

    public KavuMonarch(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{R}{R}");
        this.subtype.add(SubType.KAVU);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Kavu creatures have trample.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GainAbilityAllEffect(TrampleAbility.getInstance(),
            Duration.WhileOnBattlefield, filter1)));

        // Whenever another Kavu enters the battlefield, put a +1/+1 counter on Kavu Monarch.
        this.addAbility(new EntersBattlefieldAllTriggeredAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance()), filter2));
    }

    private KavuMonarch(final KavuMonarch card) {
        super(card);
    }

    @Override
    public KavuMonarch copy() {
        return new KavuMonarch(this);
    }
}
