package mage.cards.f;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.MillThenPutInHandEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.FilterCard;
import mage.filter.common.FilterNonlandCard;
import mage.filter.predicate.Predicates;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FallajiArchaeologist extends CardImpl {

    private static final FilterCard filter = new FilterNonlandCard("a noncreature, nonland card");

    static {
        filter.add(Predicates.not(CardType.CREATURE.getPredicate()));
    }

    public FallajiArchaeologist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SCOUT);
        this.power = new MageInt(0);
        this.toughness = new MageInt(3);

        // When Fallaji Archaeologist enters the battlefield, mill three cards. You may put a noncreature, nonland card from among the cards milled this way into your hand. If you don't, put a +1/+1 counter on Fallaji Archaeologist.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new MillThenPutInHandEffect(
                3, filter, new AddCountersSourceEffect(CounterType.P1P1.createInstance())
        )));
    }

    private FallajiArchaeologist(final FallajiArchaeologist card) {
        super(card);
    }

    @Override
    public FallajiArchaeologist copy() {
        return new FallajiArchaeologist(this);
    }
}
