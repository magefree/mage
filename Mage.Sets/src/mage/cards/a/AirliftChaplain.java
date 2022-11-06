package mage.cards.a;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.MillThenPutInHandEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ManaValuePredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AirliftChaplain extends CardImpl {

    private static final FilterCard filter = new FilterCard("a Plains card or a creature card with mana value 3 or less");

    static {
        filter.add(Predicates.or(
                SubType.PLAINS.getPredicate(),
                Predicates.and(
                        CardType.CREATURE.getPredicate(),
                        new ManaValuePredicate(ComparisonType.FEWER_THAN, 4)
                )
        ));
    }

    public AirliftChaplain(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Airlift Chaplain enters the battlefield, mill three cards. You may put a Plains card or a creature card with mana value 3 or less from among the cards milled this way into your hand. If you don't, put a +1/+1 counter on Airlift Chaplain.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new MillThenPutInHandEffect(
                3, filter, new AddCountersSourceEffect(CounterType.P1P1.createInstance())
        )));
    }

    private AirliftChaplain(final AirliftChaplain card) {
        super(card);
    }

    @Override
    public AirliftChaplain copy() {
        return new AirliftChaplain(this);
    }
}
