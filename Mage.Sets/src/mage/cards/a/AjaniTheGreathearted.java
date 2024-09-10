package mage.cards.a;

import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.effects.common.counter.AddCountersAllEffect;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledPlaneswalkerPermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AjaniTheGreathearted extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledPlaneswalkerPermanent();

    static {
        filter.add(AnotherPredicate.instance);
    }

    public AjaniTheGreathearted(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{2}{G}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.AJANI);
        this.setStartingLoyalty(5);

        // Creatures you control have vigilance.
        this.addAbility(new SimpleStaticAbility(new GainAbilityControlledEffect(
                VigilanceAbility.getInstance(),
                Duration.WhileOnBattlefield,
                StaticFilters.FILTER_PERMANENT_CREATURES
        )));

        // +1: You gain 3 life.
        this.addAbility(new LoyaltyAbility(new GainLifeEffect(3), 1));

        // -2: Put a +1/+1 counter on each creature you control and a loyalty counter on each other planeswalker you control.
        Ability ability = new LoyaltyAbility(new AddCountersAllEffect(
                CounterType.P1P1.createInstance(), StaticFilters.FILTER_CONTROLLED_CREATURE
        ), -2);
        ability.addEffect(new AddCountersAllEffect(
                CounterType.LOYALTY.createInstance(), filter
        ).setText("and a loyalty counter on each other planeswalker you control"));
        this.addAbility(ability);
    }

    private AjaniTheGreathearted(final AjaniTheGreathearted card) {
        super(card);
    }

    @Override
    public AjaniTheGreathearted copy() {
        return new AjaniTheGreathearted(this);
    }
}
