package mage.cards.h;

import java.util.Set;
import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.LoseLifeSourceControllerEffect;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetOpponent;

/**
 *
 * @author weirddan455
 */
public final class HostileNegotiations extends CardImpl {

    public HostileNegotiations(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{3}{B}");

        // Exile the top three cards of your library in a face-down pile, then exile the next top three cards of your library in another face-down pile. Look at the cards in each pile, then turn a pile of your choice face up. An opponent chooses one of those piles. Put all cards in the chosen pile into your hand and the rest into your graveyard. You lose 3 life.
        this.getSpellAbility().addEffect(new HostileNegotiationsEffect());
        this.getSpellAbility().addEffect(new LoseLifeSourceControllerEffect(3));
    }

    private HostileNegotiations(final HostileNegotiations card) {
        super(card);
    }

    @Override
    public HostileNegotiations copy() {
        return new HostileNegotiations(this);
    }
}

class HostileNegotiationsEffect extends OneShotEffect {

    public HostileNegotiationsEffect() {
        super(Outcome.DrawCard);
        this.staticText = "Exile the top three cards of your library in a face-down pile, then exile the next top three cards of your library in another face-down pile. Look at the cards in each pile, then turn a pile of your choice face up. An opponent chooses one of those piles. Put all cards in the chosen pile into your hand and the rest into your graveyard";
    }

    private HostileNegotiationsEffect(final HostileNegotiationsEffect effect) {
        super(effect);
    }

    @Override
    public HostileNegotiationsEffect copy() {
        return new HostileNegotiationsEffect(this);
    }

    private Cards exileTopThree(Player controller, Ability source, Game game) {
        Set<Card> toExile = controller.getLibrary().getTopCards(game, 3);
        controller.moveCardsToExile(toExile, source, game, false, null, "");
        Cards exiled = new CardsImpl();
        for (Card card : toExile) {
            UUID cardId = card.getId();
            if (game.getState().getZone(cardId) == Zone.EXILED) {
                card.setFaceDown(true, game);
                exiled.add(cardId);
            }
        }
        return exiled;
    }

    private void setFaceUp(Cards cards, Game game) {
        for (UUID cardId : cards) {
            Card card = game.getCard(cardId);
            if (card != null) {
                card.setFaceDown(false, game);
            }
        }
    }

    @Override
    public boolean apply(Game game, Ability source) {
        UUID controllerId = source.getControllerId();
        Player controller = game.getPlayer(controllerId);
        if (controller == null) {
            return false;
        }
        Set<UUID> opponents = game.getOpponents(controllerId);
        if (opponents.isEmpty()) {
            return false;
        }
        Player opponent;
        if (opponents.size() == 1) {
            opponent = game.getPlayer(opponents.iterator().next());
        } else {
            TargetOpponent targetOpponent = new TargetOpponent(true);
            controller.chooseTarget(Outcome.Neutral, targetOpponent, source, game);
            opponent = game.getPlayer(targetOpponent.getFirstTarget());
        }
        if (opponent == null) {
            return false;
        }
        Cards pile1 = exileTopThree(controller, source, game);
        Cards pile2 = exileTopThree(controller, source, game);
        controller.lookAtCards(source, "Pile 1", pile1, game);
        controller.lookAtCards(source, "Pile 2", pile2, game);
        if (controller.chooseUse(Outcome.Neutral, "Choose pile to turn face up", null, "Pile 1", "Pile 2", source, game)) {
            setFaceUp(pile1, game);
            controller.revealCards(source, "Pile 1", pile1, game);
        } else {
            setFaceUp(pile2, game);
            controller.revealCards(source, "Pile 2", pile2, game);
        }
        if (opponent.chooseUse(Outcome.Neutral, "Choose pile to go to opponent's hand", null, "Pile 1", "Pile 2", source, game)) {
            controller.moveCards(pile1, Zone.HAND, source, game);
            controller.moveCards(pile2, Zone.GRAVEYARD, source, game);
        } else {
            controller.moveCards(pile2, Zone.HAND, source, game);
            controller.moveCards(pile1, Zone.GRAVEYARD, source, game);
        }
        return true;
    }
}
