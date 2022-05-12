package mage.cards.e;

import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.dynamicvalue.common.DevotionCount;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.effects.common.search.SearchLibraryGraveyardPutOntoBattlefieldEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ElspethUndauntedHero extends CardImpl {

    private static final FilterCard filter = new FilterCard("card named Sunlit Hoplite");

    static {
        filter.add(new NamePredicate("Sunlit Hoplite"));
    }

    public ElspethUndauntedHero(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{2}{W}{W}{W}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.ELSPETH);
        this.setStartingLoyalty(5);

        // +2: Put a +1/+1 counter on each of up to two target creatures.
        Ability ability = new LoyaltyAbility(new AddCountersTargetEffect(CounterType.P1P1.createInstance()), 2);
        ability.addTarget(new TargetPermanent(0, 2, StaticFilters.FILTER_PERMANENT_CREATURES));
        this.addAbility(ability);

        // −2: Search your library and/or graveyard for a card named Sunlit Hoplite and put it onto the battlefield. If you search your library this way, shuffle it.
        this.addAbility(new LoyaltyAbility(new SearchLibraryGraveyardPutOntoBattlefieldEffect(filter), -2));

        // −8: Until end of turn, creatures you control gain flying and get +X/+X, where X is your devotion to white.
        ability = new LoyaltyAbility(new GainAbilityControlledEffect(
                FlyingAbility.getInstance(), Duration.EndOfTurn,
                StaticFilters.FILTER_PERMANENT_CREATURES
        ).setText("Until end of turn, creatures you control gain flying"), -8);
        ability.addEffect(new BoostControlledEffect(
                DevotionCount.W, DevotionCount.W, Duration.EndOfTurn
        ).setText("and get +X/+X, where X is your devotion to white"));
        ability.addHint(DevotionCount.W.getHint());
        this.addAbility(ability);
    }

    private ElspethUndauntedHero(final ElspethUndauntedHero card) {
        super(card);
    }

    @Override
    public ElspethUndauntedHero copy() {
        return new ElspethUndauntedHero(this);
    }
}
