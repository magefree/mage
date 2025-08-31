package mage.cards.a;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldAllTriggeredAbility;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AvatarEnthusiasts extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledPermanent(SubType.ALLY, "another Ally you control");

    static {
        filter.add(AnotherPredicate.instance);
    }

    public AvatarEnthusiasts(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.PEASANT);
        this.subtype.add(SubType.ALLY);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever another Ally you control enters, put a +1/+1 counter on this creature.
        this.addAbility(new EntersBattlefieldAllTriggeredAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance()), filter
        ));
    }

    private AvatarEnthusiasts(final AvatarEnthusiasts card) {
        super(card);
    }

    @Override
    public AvatarEnthusiasts copy() {
        return new AvatarEnthusiasts(this);
    }
}
