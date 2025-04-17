package mage.cards.e;

import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.effects.common.counter.AddCountersAllEffect;
import mage.abilities.effects.common.replacement.CreateTwiceThatManyTokensEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterOpponentsCreaturePermanent;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.permanent.token.SoldierToken;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ElspethStormSlayer extends CardImpl {

    private static final FilterPermanent filter
            = new FilterOpponentsCreaturePermanent("creature an opponent controls with mana value 3 or greater");

    static {
        filter.add(new ManaValuePredicate(ComparisonType.MORE_THAN, 2));
    }

    public ElspethStormSlayer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{3}{W}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ELSPETH);
        this.setStartingLoyalty(5);

        // If one or more tokens would be created under your control, twice that many of those tokens are created instead.
        this.addAbility(new SimpleStaticAbility(new CreateTwiceThatManyTokensEffect()));

        // +1: Create a 1/1 white Soldier creature token.
        this.addAbility(new LoyaltyAbility(new CreateTokenEffect(new SoldierToken()), 1));

        // 0: Put a +1/+1 counter on each creature you control. Those creatures gain flying until your next turn.
        Ability ability = new LoyaltyAbility(new AddCountersAllEffect(
                CounterType.P1P1.createInstance(), StaticFilters.FILTER_CONTROLLED_CREATURE
        ), 0);
        ability.addEffect(new GainAbilityControlledEffect(
                FlyingAbility.getInstance(), Duration.UntilYourNextTurn,
                StaticFilters.FILTER_CONTROLLED_CREATURE
        ).setText("those creatures gain flying until your next turn"));
        this.addAbility(ability);

        // -3: Destroy target creature an opponent controls with mana value 3 or greater.
        ability = new LoyaltyAbility(new DestroyTargetEffect(), -3);
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);
    }

    private ElspethStormSlayer(final ElspethStormSlayer card) {
        super(card);
    }

    @Override
    public ElspethStormSlayer copy() {
        return new ElspethStormSlayer(this);
    }
}
