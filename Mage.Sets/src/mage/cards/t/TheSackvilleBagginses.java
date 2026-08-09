package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SacrificePermanentTriggeredAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.permanent.token.TreasureToken;
import mage.target.common.TargetOpponent;

/**
 *
 * @author muz
 */
public final class TheSackvilleBagginses extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("a token");

    static {
        filter.add(TokenPredicate.TRUE);
    }

    public TheSackvilleBagginses(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HALFLING);
        this.subtype.add(SubType.CITIZEN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // When The Sackville-Bagginses enter, you may sacrifice another creature or artifact. If you do, draw a card and create a Treasure token.
        this.addAbility(new EntersBattlefieldTriggeredAbility(
            new DoIfCostPaid(
                new DrawCardSourceControllerEffect(1),
                new SacrificeTargetCost(StaticFilters.FILTER_CONTROLLED_ANOTHER_CREATURE_OR_ARTIFACT)
            ).addEffect(new CreateTokenEffect(new TreasureToken()).concatBy("and"))
        ).setTriggerPhrase("When {this} enter, "));

        // Whenever you sacrifice a token, target opponent loses 1 life.
        Ability ability = new SacrificePermanentTriggeredAbility(new LoseLifeTargetEffect(1), filter);
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);
    }

    private TheSackvilleBagginses(final TheSackvilleBagginses card) {
        super(card);
    }

    @Override
    public TheSackvilleBagginses copy() {
        return new TheSackvilleBagginses(this);
    }
}
