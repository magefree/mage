package mage.cards.b;

import mage.MageInt;
import mage.abilities.common.GainLifeControllerTriggeredAbility;
import mage.abilities.effects.common.counter.AddCountersAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BlechLoafingPest extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledPermanent("Pest, Bat, Insect, Snake, and Spider you control");

    static {
        filter.add(Predicates.or(
                SubType.PEST.getPredicate(),
                SubType.BAT.getPredicate(),
                SubType.INSECT.getPredicate(),
                SubType.SNAKE.getPredicate(),
                SubType.SPIDER.getPredicate()
        ));
    }

    public BlechLoafingPest(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.PEST);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Whenever you gain life, put a +1/+1 counter on each Pest, Bat, Insect, Snake, and Spider you control.
        this.addAbility(new GainLifeControllerTriggeredAbility(
                new AddCountersAllEffect(CounterType.P1P1.createInstance(), filter)
        ));
    }

    private BlechLoafingPest(final BlechLoafingPest card) {
        super(card);
    }

    @Override
    public BlechLoafingPest copy() {
        return new BlechLoafingPest(this);
    }
}
