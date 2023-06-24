package mage.cards.l;

import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.SacrificeAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardIdPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.ZombieToken;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetControlledPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class LilianaDreadhordeGeneral extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledCreaturePermanent("creatures");

    public LilianaDreadhordeGeneral(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{4}{B}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.LILIANA);
        this.setStartingLoyalty(6);

        // Whenever a creature you control dies, draw a card.
        this.addAbility(new DiesCreatureTriggeredAbility(
                new DrawCardSourceControllerEffect(1), false,
                StaticFilters.FILTER_CONTROLLED_A_CREATURE
        ));

        // +1: Create a 2/2 black Zombie creature token.
        this.addAbility(new LoyaltyAbility(new CreateTokenEffect(new ZombieToken()), 1));

        // -4: Each player sacrifices two creatures.
        this.addAbility(new LoyaltyAbility(new SacrificeAllEffect(2, filter), -4));

        // -9: Each opponent chooses a permanent they control of each permanent type and sacrifices the rest.
        this.addAbility(new LoyaltyAbility(new LilianaDreadhordeGeneralEffect(), -9));
    }

    private LilianaDreadhordeGeneral(final LilianaDreadhordeGeneral card) {
        super(card);
    }

    @Override
    public LilianaDreadhordeGeneral copy() {
        return new LilianaDreadhordeGeneral(this);
    }
}

class LilianaDreadhordeGeneralEffect extends OneShotEffect {

    LilianaDreadhordeGeneralEffect() {
        super(Outcome.Benefit);
        staticText = "Each opponent chooses a permanent they control of each permanent type and sacrifices the rest.";
    }

    private LilianaDreadhordeGeneralEffect(final LilianaDreadhordeGeneralEffect effect) {
        super(effect);
    }

    @Override
    public LilianaDreadhordeGeneralEffect copy() {
        return new LilianaDreadhordeGeneralEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        FilterPermanent keepFilter = new FilterPermanent();
        keepFilter.add(TargetController.OPPONENT.getControllerPredicate());
        for (UUID opponentId : game.getState().getPlayersInRange(source.getControllerId(), game)) {
            Player opponent = game.getPlayer(opponentId);
            if (opponent == null || !opponent.hasOpponent(source.getControllerId(), game)) {
                continue;
            }
            for (CardType cardType : CardType.values()) {
                if (!cardType.isPermanentType()) {
                    continue;
                }
                FilterControlledPermanent filter = new FilterControlledPermanent(
                        "a " + cardType.toString() + " you control " +
                                "(everything you don't choose will be sacrificed)"
                );
                filter.add(cardType.getPredicate());
                Target target = new TargetControlledPermanent(filter);
                target.setNotTarget(true);
                if (opponent.choose(outcome, target, source, game)) {
                    keepFilter.add(Predicates.not(new CardIdPredicate(target.getFirstTarget())));
                }
            }
        }
        for (Permanent permanent : game.getBattlefield().getActivePermanents(source.getControllerId(), game)) {
            if (keepFilter.match(permanent, source.getControllerId(), source, game)) {
                permanent.sacrifice(source, game);
            }
        }
        return true;
    }
}