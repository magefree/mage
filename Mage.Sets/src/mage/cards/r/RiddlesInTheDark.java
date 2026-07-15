package mage.cards.r;

import mage.MageObject;
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
import mage.game.Game;
import mage.players.Player;
import mage.target.Target;
import mage.target.TargetCard;
import mage.target.common.TargetOpponent;

import java.util.Set;
import java.util.UUID;

/**
 *
 * @author muz
 */
public final class RiddlesInTheDark extends CardImpl {

    public RiddlesInTheDark(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{U}");

        // Look at the top four cards of your library and separate them into a face-down pile and a face-up pile. An opponent chooses one of the piles. Put that pile into your hand and the other into your graveyard.
        this.getSpellAbility().addEffect(new RiddlesInTheDarkEffect());
    }

    private RiddlesInTheDark(final RiddlesInTheDark card) {
        super(card);
    }

    @Override
    public RiddlesInTheDark copy() {
        return new RiddlesInTheDark(this);
    }
}

class RiddlesInTheDarkEffect extends OneShotEffect {

    RiddlesInTheDarkEffect() {
        super(Outcome.Benefit);
        this.staticText = "Look at the top four cards of your library and separate them into a face-down pile and a face-up pile. " +
            "An opponent chooses one of the piles. Put that pile into your hand and the other into your graveyard";
    }

    private RiddlesInTheDarkEffect(final RiddlesInTheDarkEffect effect) {
        super(effect);
    }

    @Override
    public RiddlesInTheDarkEffect copy() {
        return new RiddlesInTheDarkEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = game.getObject(source);
        if (controller == null || sourceObject == null) {
            return false;
        }
        Cards cards = new CardsImpl(controller.getLibrary().getTopCards(game, 4));
        if (cards.isEmpty()) {
            return true;
        }

        TargetCard faceDownTarget = new TargetCard(0, 4, Zone.LIBRARY, new FilterCard("cards for the face-down pile"));
        controller.choose(Outcome.Benefit, cards, faceDownTarget, source, game);
        Cards faceDownPile = new CardsImpl(faceDownTarget.getTargets());
        Cards faceUpPile = new CardsImpl(cards);
        faceUpPile.removeAll(faceDownTarget.getTargets());

        // Reveal the face-up pile to all players
        controller.revealCards(sourceObject.getIdName() + " - face-up pile", faceUpPile, game);
        game.informPlayers(controller.getLogName() + " puts " + faceDownPile.size() + " card(s) into the face-down pile");

        // An opponent chooses which pile to put into controller's hand
        Player opponent;
        Set<UUID> opponents = game.getOpponents(controller.getId());
        if (opponents.size() == 1) {
            opponent = game.getPlayer(opponents.iterator().next());
        } else {
            Target opponentTarget = new TargetOpponent(true);
            controller.chooseTarget(Outcome.Detriment, opponentTarget, source, game);
            opponent = game.getPlayer(opponentTarget.getFirstTarget());
        }

        if (opponent != null && opponent.chooseUse(Outcome.Detriment,
                "Choose a pile to put into " + controller.getLogName() + "'s hand.",
                null, "Face-down pile", "Face-up pile", source, game)) {
            controller.moveCards(faceDownPile, Zone.HAND, source, game);
            controller.moveCards(faceUpPile, Zone.GRAVEYARD, source, game);
        } else {
            controller.moveCards(faceDownPile, Zone.GRAVEYARD, source, game);
            controller.moveCards(faceUpPile, Zone.HAND, source, game);
        }
        return true;
    }
}
