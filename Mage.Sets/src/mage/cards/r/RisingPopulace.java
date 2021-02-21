package mage.cards.r;

import mage.MageInt;
import mage.abilities.common.PutIntoGraveFromBattlefieldAllTriggeredAbility;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreatureOrPlaneswalkerPermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RisingPopulace extends CardImpl {

    private static final FilterPermanent filter
            = new FilterCreatureOrPlaneswalkerPermanent("another creature or planeswalker you control");

    static {
        filter.add(AnotherPredicate.instance);
        filter.add(TargetController.YOU.getControllerPredicate());
    }

    public RisingPopulace(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");

        this.subtype.add(SubType.HUMAN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever another creature or planeswalker you control dies, put a +1/+1 counter on Rising Populace.
        this.addAbility(new PutIntoGraveFromBattlefieldAllTriggeredAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance()),
                false, filter, false
        ));
    }

    private RisingPopulace(final RisingPopulace card) {
        super(card);
    }

    @Override
    public RisingPopulace copy() {
        return new RisingPopulace(this);
    }
}
