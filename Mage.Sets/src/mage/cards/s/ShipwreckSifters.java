package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.DiscardCardControllerTriggeredAbility;
import mage.abilities.effects.common.DrawDiscardControllerEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.DisturbAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AbilityPredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ShipwreckSifters extends CardImpl {

    private static final FilterCard filter = new FilterCard("a Spirit card or a card with disturb");

    static {
        filter.add(Predicates.or(
                SubType.SPIRIT.getPredicate(),
                new AbilityPredicate(DisturbAbility.class)
        ));
    }

    public ShipwreckSifters(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}");

        this.subtype.add(SubType.SPIRIT);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // When Shipwreck Sifters enters the battlefield, draw a card, then discard a card.
        this.addAbility(new EntersBattlefieldTriggeredAbility(
                new DrawDiscardControllerEffect(1, 1)
        ));

        // Whenever you discard a Spirit card or a card with disturb, put a +1/+1 counter on Shipwreck Sifters.
        this.addAbility(new DiscardCardControllerTriggeredAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance()), false, filter
        ));
    }

    private ShipwreckSifters(final ShipwreckSifters card) {
        super(card);
    }

    @Override
    public ShipwreckSifters copy() {
        return new ShipwreckSifters(this);
    }
}
