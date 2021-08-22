package mage.cards.d;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardIdPredicate;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetCardInGraveyard;

import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author TheElk801
 */
public final class DanseMacabre extends CardImpl {

    public DanseMacabre(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{B}{B}");

        // Each player sacrifices a nontoken creature. Roll a d20 and add the toughness of the creature you sacrificed this way.
        // 1-14 | Return a creature card put into a graveyard this way to the battlefield under your control.
        // 15+ | Return up to two creature cards put into a graveyard this way to the battlefield under your control
        this.getSpellAbility().addEffect(new DanseMacabreEffect());
    }

    private DanseMacabre(final DanseMacabre card) {
        super(card);
    }

    @Override
    public DanseMacabre copy() {
        return new DanseMacabre(this);
    }
}

class DanseMacabreEffect extends OneShotEffect {

    private static final FilterPermanent filter
            = new FilterControlledCreaturePermanent("nontoken creature you control");

    static {
        filter.add(TokenPredicate.FALSE);
    }

    DanseMacabreEffect() {
        super(Outcome.Benefit);
        staticText = "each player sacrifices a nontoken creature. Roll a d20 " +
                "and add the toughness of the creature you sacrificed this way." +
                "<br>1-14 | Return a creature card put into a graveyard " +
                "this way to the battlefield under your control." +
                "<br>15+ | Return up to two creature cards put into " +
                "graveyards this way to the battlefield under your control";
    }

    private DanseMacabreEffect(final DanseMacabreEffect effect) {
        super(effect);
    }

    @Override
    public DanseMacabreEffect copy() {
        return new DanseMacabreEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        int toughness = 0;
        Cards cards = new CardsImpl();
        for (UUID playerId : game.getState().getPlayersInRange(source.getControllerId(), game)) {
            Player player = game.getPlayer(playerId);
            if (player == null || game.getBattlefield().count(
                    filter, source.getSourceId(), source.getControllerId(), game
            ) < 1) {
                continue;
            }
            TargetPermanent target = new TargetPermanent(filter);
            target.setNotTarget(true);
            player.choose(Outcome.Sacrifice, target, source.getSourceId(), game);
            Permanent permanent = game.getPermanent(source.getSourceId());
            if (permanent == null) {
                continue;
            }
            if (source.isControlledBy(playerId)) {
                toughness += permanent.getToughness().getValue();
            }
            cards.add(permanent);
            permanent.sacrifice(source, game);
        }
        int result = controller.rollDice(source, game, 20) + toughness;
        cards.retainZone(Zone.GRAVEYARD, game);
        if (cards.isEmpty()) {
            return true;
        }
        FilterCard filterCard = new FilterCard("card put into a graveyard this way");
        filterCard.add(Predicates.or(
                cards.stream()
                        .map(cardId -> new CardIdPredicate(cardId))
                        .collect(Collectors.toSet())
        ));
        TargetCardInGraveyard target;
        if (result >= 15) {
            target = new TargetCardInGraveyard(0, 2, filterCard);
        } else if (result > 0) {
            target = new TargetCardInGraveyard(filterCard);
        } else {
            return true;
        }
        target.setNotTarget(true);
        controller.choose(Outcome.PutCreatureInPlay, target, source.getSourceId(), game);
        controller.moveCards(new CardsImpl(target.getTargets()), Zone.BATTLEFIELD, source, game);
        return true;
    }
}
