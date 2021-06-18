package mage.cards.m;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.ConvokeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledLandPermanent;
import mage.filter.predicate.permanent.TappedPredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MartyrsSoul extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledLandPermanent();

    static {
        filter.add(TappedPredicate.TAPPED);
    }

    private static final Condition condition
            = new PermanentsOnTheBattlefieldCondition(filter, ComparisonType.EQUAL_TO, 0);

    public MartyrsSoul(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");

        this.subtype.add(SubType.SPIRIT);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Convoke
        this.addAbility(new ConvokeAbility());

        // When Martyr's Soul enters the battlefield, if you control no tapped lands, put two +1/+1 counters on it.
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(
                new EntersBattlefieldTriggeredAbility(new AddCountersSourceEffect(
                        CounterType.P1P1.createInstance(2)
                )), condition, "When {this} enters the battlefield, " +
                "if you control no tapped lands, put two +1/+1 counters on it."
        ));
    }

    private MartyrsSoul(final MartyrsSoul card) {
        super(card);
    }

    @Override
    public MartyrsSoul copy() {
        return new MartyrsSoul(this);
    }
}
