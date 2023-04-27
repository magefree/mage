package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.constants.SubType;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;

/**
 *
 * @author weirddan455
 */
public final class SengirConnoisseur extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("one or more other creatures");

    static {
        filter.add(AnotherPredicate.instance);
    }

    public SengirConnoisseur(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}{B}");

        this.subtype.add(SubType.VAMPIRE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever one or more other creatures die, put a +1/+1 counter on Sengir Connoisseur. This ability triggers only once each turn.
        this.addAbility(new DiesCreatureTriggeredAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance()), false, filter)
                .setTriggersOnce(true));
    }

    private SengirConnoisseur(final SengirConnoisseur card) {
        super(card);
    }

    @Override
    public SengirConnoisseur copy() {
        return new SengirConnoisseur(this);
    }
}
