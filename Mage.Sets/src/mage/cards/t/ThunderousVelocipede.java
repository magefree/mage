package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.EntersWithCountersControlledEffect;
import mage.constants.ComparisonType;
import mage.constants.SubType;
import mage.abilities.keyword.TrampleAbility;
import mage.abilities.keyword.CrewAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ManaValuePredicate;

/**
 *
 * @author Jmlundeen
 */
public final class ThunderousVelocipede extends CardImpl {

    private static final FilterPermanent fourOrLessFilter = new FilterPermanent("other Vehicle and creature you control with mana value 4 or less");
    private static final FilterPermanent greaterThanFourFilter = new FilterPermanent("other Vehicle and creature you control with mana value greater than 4");

    static {
        fourOrLessFilter.add(new ManaValuePredicate(ComparisonType.OR_LESS, 4));
        fourOrLessFilter.add(Predicates.or(
                CardType.CREATURE.getPredicate(),
                SubType.VEHICLE.getPredicate()
        ));
        greaterThanFourFilter.add(new ManaValuePredicate(ComparisonType.MORE_THAN, 4));
        greaterThanFourFilter.add(Predicates.or(
                CardType.CREATURE.getPredicate(),
                SubType.VEHICLE.getPredicate()
        ));
    }

    public ThunderousVelocipede(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}{G}{G}");
        
        this.subtype.add(SubType.VEHICLE);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Each other Vehicle and creature you control enters with an additional +1/+1 counter on it if its mana value is 4 or less. Otherwise, it enters with three additional +1/+1 counters on it.
        Ability ability = new SimpleStaticAbility(new EntersWithCountersControlledEffect(fourOrLessFilter, CounterType.P1P1.createInstance(1), true)
                .setText("Each other Vehicle and creature you control enters with an additional +1/+1 counter on it if its mana value is 4 or less."));
        ability.addEffect(new EntersWithCountersControlledEffect(greaterThanFourFilter, CounterType.P1P1.createInstance(3), true)
                .setText("otherwise, it enters with three additional +1/+1 counters on it."));
        this.addAbility(ability);
        // Crew 3
        this.addAbility(new CrewAbility(3));

    }

    private ThunderousVelocipede(final ThunderousVelocipede card) {
        super(card);
    }

    @Override
    public ThunderousVelocipede copy() {
        return new ThunderousVelocipede(this);
    }
}
