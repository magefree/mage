
package mage.cards.s;

import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
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
import mage.target.TargetCard;
import mage.target.common.TargetOpponent;

/**
 *
 * @author LevelX2
 */
public final class StruggleForSanity extends CardImpl {

    public StruggleForSanity(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{2}{B}{B}");

        // Target opponent reveals their hand. That player exiles a card from it, then you exile a card from it. Repeat this process until all cards in that hand have been exiled. That player returns the cards they exiled this way to their hand and puts the rest into their graveyard.
        this.getSpellAbility().addEffect(new StruggleForSanityEffect());
        this.getSpellAbility().addTarget(new TargetOpponent());
    }

    private StruggleForSanity(final StruggleForSanity card) {
        super(card);
    }

    @Override
    public StruggleForSanity copy() {
        return new StruggleForSanity(this);
    }
}

class StruggleForSanityEffect extends OneShotEffect {

    public StruggleForSanityEffect() {
        super(Outcome.Discard); // kind of
        this.staticText = "Target opponent reveals their hand. That player exiles a card from it, then you exile a card from it. Repeat this process until all cards in that hand have been exiled. That player returns the cards they exiled this way to their hand and puts the rest into their graveyard";
    }

    public StruggleForSanityEffect(final StruggleForSanityEffect effect) {
        super(effect);
    }

    @Override
    public StruggleForSanityEffect copy() {
        return new StruggleForSanityEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player targetPlayer = game.getPlayer(getTargetPointer().getFirst(game, source));
        MageObject sourceObject = source.getSourceObject(game);
        Player controller = game.getPlayer(source.getControllerId());
        if (targetPlayer == null || sourceObject == null || controller == null) {
            return false;
        }
        targetPlayer.revealCards(sourceObject.getIdName(), targetPlayer.getHand(), game);

        Cards cardsLeft = new CardsImpl(targetPlayer.getHand());
        Cards exiledByController = new CardsImpl();
        UUID exileZoneController = UUID.randomUUID();
        Cards exiledByOpponent = new CardsImpl();
        UUID exileZoneOpponent = UUID.randomUUID();
        boolean opponentsChoice = true;
        TargetCard target = new TargetCard(Zone.HAND, new FilterCard("a card to exile"));
        while (!cardsLeft.isEmpty()) {
            if (opponentsChoice) {
                targetPlayer.choose(Outcome.ReturnToHand, cardsLeft, target, source, game);
                Card card = game.getCard(target.getFirstTarget());
                if (card != null) {
                    exiledByOpponent.add(card);
                    cardsLeft.remove(card);
                    targetPlayer.moveCardsToExile(card, source, game, true, exileZoneOpponent, sourceObject.getIdName() + " exiled by " + targetPlayer.getName());
                }
            } else {
                controller.choose(Outcome.Discard, cardsLeft, target, source, game);
                Card card = game.getCard(target.getFirstTarget());
                if (card != null) {
                    exiledByController.add(card);
                    cardsLeft.remove(card);
                    controller.moveCardsToExile(card, source, game, true, exileZoneController, sourceObject.getIdName() + " exiled by " + controller.getName());
                }
            }
            target.clearChosen();
            opponentsChoice = !opponentsChoice;

        }
        targetPlayer.moveCards(exiledByOpponent, Zone.HAND, source, game);
        targetPlayer.moveCards(exiledByController, Zone.GRAVEYARD, source, game);
        return true;
    }
}
