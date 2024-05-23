package mage.cards.d;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.PlotAbility;
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
 * @author Susucr
 */
public final class DustAnimus extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledLandPermanent("untapped lands");

    static {
        filter.add(TappedPredicate.UNTAPPED);
    }

    private static final Condition condition = new PermanentsOnTheBattlefieldCondition(filter, ComparisonType.OR_GREATER, 5);

    public DustAnimus(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");

        this.subtype.add(SubType.SPIRIT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // If you control five or more untapped lands, Dust Animus enters the battlefield with two +1/+1 counters and a lifelink counter on it.
        Ability ability = new EntersBattlefieldAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance(2)),
                condition,
                "If you control five or more untapped lands, "
                        + "{this} enters the battlefield with two +1/+1 counters "
                        + "and a lifelink counter on it.",
                ""
        );
        ability.addEffect(new AddCountersSourceEffect(CounterType.LIFELINK.createInstance()));
        this.addAbility(ability);

        // Plot {1}{W}
        this.addAbility(new PlotAbility("{1}{W}"));
    }

    private DustAnimus(final DustAnimus card) {
        super(card);
    }

    @Override
    public DustAnimus copy() {
        return new DustAnimus(this);
    }
}
