
package mage.abilities.effects.common.search;

import mage.abilities.Ability;
import mage.abilities.effects.SearchEffect;
import mage.cards.Card;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 * @author LokiX, BetaSteward_at_googlemail.com
 */
public class SearchLibraryPutInHandEffect extends SearchEffect {

    private boolean reveal;
    private boolean textThatCard;

    public SearchLibraryPutInHandEffect(TargetCardInLibrary target, boolean reveal) {
        this(target, reveal, false);
    }

    public SearchLibraryPutInHandEffect(TargetCardInLibrary target, boolean reveal, boolean textThatCard) {
        super(target, Outcome.DrawCard);
        this.reveal = reveal;
        this.textThatCard = textThatCard;
        setText();
    }

    public SearchLibraryPutInHandEffect(final SearchLibraryPutInHandEffect effect) {
        super(effect);
        this.reveal = effect.reveal;
        this.textThatCard = effect.textThatCard;
    }

    @Override
    public SearchLibraryPutInHandEffect copy() {
        return new SearchLibraryPutInHandEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        target.clearChosen();
        if (controller.searchLibrary(target, source, game)) {
            if (!target.getTargets().isEmpty()) {
                Cards cards = new CardsImpl();
                for (UUID cardId : target.getTargets()) {
                    Card card = game.getCard(cardId);
                    if (card != null) {
                        cards.add(card);
                    }
                }
                controller.moveCards(cards, Zone.HAND, source, game);
                if (reveal) {
                    String name = "Reveal";
                    Card sourceCard = game.getCard(source.getSourceId());
                    if (sourceCard != null) {
                        name = sourceCard.getIdName();
                    }
                    controller.revealCards(name, cards, game);
                }
            }
            controller.shuffleLibrary(source, game);
            return true;
        }
        controller.shuffleLibrary(source, game);
        return false;
    }

    private void setText() {
        StringBuilder sb = new StringBuilder();
        sb.append("search your library for ");
        sb.append(target.getDescription());
        if (target.getMaxNumberOfTargets() > 1) {
            if (reveal) {
                sb.append(", reveal ");
                sb.append(textThatCard ? "those cards" : "them");
                sb.append(", put them");
            } else {
                sb.append(", put ");
                sb.append(textThatCard ? "those cards" : "them");
            }
        } else {
            if (reveal) {
                sb.append(", reveal ");
                sb.append(textThatCard ? "that card" : "it");
                sb.append(", put it");
            } else {
                sb.append(", put ");
                sb.append(textThatCard ? "that card" : "it");
            }
        }
        sb.append(" into your hand, then shuffle");
        staticText = sb.toString();
    }

}
