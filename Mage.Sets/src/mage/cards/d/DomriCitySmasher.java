package mage.cards.d;

import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.effects.common.counter.AddCountersAllEffect;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.target.common.TargetAnyTarget;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DomriCitySmasher extends CardImpl {

    public DomriCitySmasher(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{4}{R}{G}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.DOMRI);
        this.setStartingLoyalty(4);

        // +2: Creatures you control get +1/+1 and gain haste until end of turn.
        Ability ability = new LoyaltyAbility(new BoostControlledEffect(
                1, 1, Duration.EndOfTurn
        ).setText("creatures you control get +1/+1"), 2);
        ability.addEffect(new GainAbilityControlledEffect(
                HasteAbility.getInstance(), Duration.EndOfTurn
        ).setText("and gain haste until end of turn"));
        this.addAbility(ability);

        // -3: Domri, City Smasher deals 3 damage to any target.
        ability = new LoyaltyAbility(new DamageTargetEffect(3), -3);
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);

        // -8: Put three +1/+1 counters on each creature you control. Those creatures gain trample until end of turn.
        ability = new LoyaltyAbility(new AddCountersAllEffect(
                CounterType.P1P1.createInstance(3),
                StaticFilters.FILTER_CONTROLLED_CREATURE
        ), -8);
        ability.addEffect(new GainAbilityControlledEffect(
                TrampleAbility.getInstance(), Duration.EndOfTurn
        ).setText("Those creatures gain trample until end of turn"));
        this.addAbility(ability);
    }

    private DomriCitySmasher(final DomriCitySmasher card) {
        super(card);
    }

    @Override
    public DomriCitySmasher copy() {
        return new DomriCitySmasher(this);
    }
}
