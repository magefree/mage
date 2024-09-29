package mage.cards.k;

import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.common.*;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.game.permanent.token.KrakenToken;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author Svyatoslav28
 */
public final class KioraTheTidesFury extends CardImpl {
    private static final FilterPermanent filter = new FilterPermanent (SubType.KRAKEN, "Kraken");

    public KioraTheTidesFury(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{3}{U}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.KIORA);

        this.setStartingLoyalty(4);

        // +1:  Conjure a card named Kraken Hatchling into your hand.
        LoyaltyAbility ability = new LoyaltyAbility((new ConjureCardEffect("Kraken Hatchling")), 1);
        this.addAbility(ability);

        // +1: Untap target creature or land. Prevent all damage that would be dealt to and dealt by that permanent until your next turn.
        Ability secondAbility = new LoyaltyAbility(new UntapTargetEffect(), 1);
        secondAbility.addEffect(new PreventDamageToTargetEffect(Duration.UntilYourNextTurn)
                .setText("Prevent all damage that would be dealt to"));
        secondAbility.addEffect(new PreventDamageByTargetEffect(Duration.UntilYourNextTurn, false)
                .setText(" and dealt by that permanent until your next turn."));
        secondAbility.addTarget(new TargetPermanent(StaticFilters.FILTER_PERMANENT_CREATURE_OR_LAND));
        this.addAbility(secondAbility);


        // −3: You may sacrifice a Kraken. If you do, create an 8/8 blue Kraken creature token.
        this.addAbility(new LoyaltyAbility(new DoIfCostPaid(
                new CreateTokenEffect(new KrakenToken()),
                new SacrificeTargetCost(filter)),
                -3));
    }

    private KioraTheTidesFury(final KioraTheTidesFury card) {
        super(card);
    }

    @Override
    public KioraTheTidesFury copy() {
        return new KioraTheTidesFury(this);
    }
}

