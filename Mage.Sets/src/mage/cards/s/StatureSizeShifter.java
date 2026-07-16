package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.SourceMatchesFilterCondition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalRestrictionEffect;
import mage.abilities.dynamicvalue.common.GetXValue;
import mage.abilities.effects.common.combat.CantBeBlockedSourceEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.PowerUpAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.mageobject.PowerPredicate;

import java.util.UUID;

/**
 *
 * @author muz
 */
public final class StatureSizeShifter extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("{this}'s power is 1 or less");

    static {
        filter.add(new PowerPredicate(ComparisonType.OR_LESS, 1));
    }

    private static final Condition condition = new SourceMatchesFilterCondition(filter);

    public StatureSizeShifter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.HERO);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Stature can't be blocked if her power is 1 or less.
        this.addAbility(new SimpleStaticAbility(new ConditionalRestrictionEffect(
            new CantBeBlockedSourceEffect(), condition,
            "{this} can't be blocked if her power is 1 or less"
        )));

        // Power-up -- {X}{U}{U}: Put X +1/+1 counters on Stature.
        this.addAbility(new PowerUpAbility(
            new AddCountersSourceEffect(CounterType.P1P1.createInstance(), GetXValue.instance)
                .setText("put X +1/+1 counters on {this}"),
            new ManaCostsImpl<>("{X}{U}{U}")
        ));
    }

    private StatureSizeShifter(final StatureSizeShifter card) {
        super(card);
    }

    @Override
    public StatureSizeShifter copy() {
        return new StatureSizeShifter(this);
    }
}
