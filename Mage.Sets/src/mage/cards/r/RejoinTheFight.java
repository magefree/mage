package mage.cards.r;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.MillCardsControllerEffect;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInGraveyard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RejoinTheFight extends CardImpl {

    public RejoinTheFight(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{5}{B}");

        // Mill three cards. Then starting with the next opponent in turn order, each opponent chooses a creature card in your graveyard that hasn't been chosen. Return each card chosen this way to the battlefield under your control.
        this.getSpellAbility().addEffect(new MillCardsControllerEffect(3));
        this.getSpellAbility().addEffect(new RejoinTheFightEffect());
    }

    private RejoinTheFight(final RejoinTheFight card) {
        super(card);
    }

    @Override
    public RejoinTheFight copy() {
        return new RejoinTheFight(this);
    }
}

class RejoinTheFightEffect extends OneShotEffect {

    RejoinTheFightEffect() {
        super(Outcome.Benefit);
        staticText = "Then starting with the next opponent in turn order, each opponent chooses " +
                "a creature card in your graveyard that hasn't been chosen. " +
                "Return each card chosen this way to the battlefield under your control";
    }

    private RejoinTheFightEffect(final RejoinTheFightEffect effect) {
        super(effect);
    }

    @Override
    public RejoinTheFightEffect copy() {
        return new RejoinTheFightEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        Cards cards = new CardsImpl(controller.getGraveyard().getCards(StaticFilters.FILTER_CARD_CREATURE, game));
        if (cards.isEmpty()) {
            return false;
        }
        Cards toPlay = new CardsImpl();
        for (UUID opponentId : game.getOpponents(source.getControllerId())) {
            if (cards.isEmpty()) {
                break;
            }
            Player opponent = game.getPlayer(opponentId);
            if (opponent == null) {
                continue;
            }
            TargetCard target = new TargetCardInGraveyard(StaticFilters.FILTER_CARD_CREATURE);
            target.withNotTarget(true);
            opponent.choose(outcome, cards, target, source, game);
            Card card = game.getCard(target.getFirstTarget());
            if (card == null) {
                continue;
            }
            game.informPlayers(opponent.getLogName() + " chooses " + card.getLogName());
            toPlay.add(card);
            cards.remove(card);
        }
        return controller.moveCards(toPlay, Zone.BATTLEFIELD, source, game);
    }
}
