package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.MillThenPutInHandEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.HexproofAbility;
import mage.abilities.keyword.IndestructibleAbility;
import mage.abilities.keyword.SpreeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreatureCard;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.PowerPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInHand;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class SmugglersSurprise extends CardImpl {

    private static final FilterCard filterCard = new FilterCard("creature and/or land cards");

    static {
        filterCard.add(Predicates.or(CardType.CREATURE.getPredicate(), CardType.LAND.getPredicate()));
    }

    private static final FilterPermanent filter = new FilterCreaturePermanent("Creatures you control with power 4 or greater");

    static {
        filter.add(new PowerPredicate(ComparisonType.OR_GREATER, 4));
    }

    public SmugglersSurprise(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{G}");

        // Spree
        this.addAbility(new SpreeAbility(this));

        // + {2} -- Mill four cards. You may put up to two creature and/or land cards from among the milled cards into your hand.
        this.getSpellAbility().addEffect(new MillThenPutInHandEffect(
                4, filterCard, null, true, 2
        ));
        this.getSpellAbility().withFirstModeCost(new GenericManaCost(2));

        // + {4}{G} -- You may put up to two creature cards from your hand onto the battlefield.
        this.getSpellAbility().addMode(new Mode(new SmugglersSurpriseEffect())
                .withCost(new ManaCostsImpl<>("{4}{G}")));

        // + {1} -- Creatures you control with power 4 or greater gain hexproof and indestructible until end of turn.
        this.getSpellAbility().addMode(new Mode(
                new GainAbilityControlledEffect(HexproofAbility.getInstance(), Duration.EndOfTurn, filter)
                        .setText("Creatures you control with power 4 or greater gain hexproof"))
                .addEffect(new GainAbilityControlledEffect(IndestructibleAbility.getInstance(), Duration.EndOfTurn, filter)
                        .setText(" and indestructible until end of turn"))
                .withCost(new GenericManaCost(1))
        );
    }

    private SmugglersSurprise(final SmugglersSurprise card) {
        super(card);
    }

    @Override
    public SmugglersSurprise copy() {
        return new SmugglersSurprise(this);
    }
}

class SmugglersSurpriseEffect extends OneShotEffect {

    SmugglersSurpriseEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "You may put up to two creature cards from your hand onto the battlefield";
    }

    private SmugglersSurpriseEffect(final SmugglersSurpriseEffect effect) {
        super(effect);
    }

    @Override
    public SmugglersSurpriseEffect copy() {
        return new SmugglersSurpriseEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        TargetCardInHand target = new TargetCardInHand(0, 2, new FilterCreatureCard("creature cards"));
        if (controller.choose(Outcome.PutCreatureInPlay, target, source, game)) {
            return controller.moveCards(
                    new CardsImpl(target.getTargets()).getCards(game),
                    Zone.BATTLEFIELD, source, game
            );
        }
        return false;
    }
}
