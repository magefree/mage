
package mage.abilities.effects.common.search;

import java.util.UUID;
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
import mage.util.CardUtil;

/**
 *
 * @author LokiX, BetaSteward_at_googlemail.com (spjspj)
 */
public class SearchLibraryPutInHandOrOnBattlefieldEffect extends SearchEffect {

    private boolean revealCards;
    private String nameToPutOnBattlefield;

    public SearchLibraryPutInHandOrOnBattlefieldEffect(TargetCardInLibrary target, boolean revealCards, String nameToPutOnBattlefield) {
        super(target, Outcome.DrawCard);
        this.revealCards = revealCards;
        this.nameToPutOnBattlefield = nameToPutOnBattlefield;
        setText();
    }

    public SearchLibraryPutInHandOrOnBattlefieldEffect(final SearchLibraryPutInHandOrOnBattlefieldEffect effect) {
        super(effect);
        this.revealCards = effect.revealCards;
        this.nameToPutOnBattlefield = effect.nameToPutOnBattlefield;
    }

    @Override
    public SearchLibraryPutInHandOrOnBattlefieldEffect copy() {
        return new SearchLibraryPutInHandOrOnBattlefieldEffect(this);
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
                boolean askToPutOntoBf = false;
                Card cardToPutOnBf = null;
                for (UUID cardId : target.getTargets()) {
                    Card card = game.getCard(cardId);
                    if (card != null) {
                        if (card.getName().equals(nameToPutOnBattlefield)) {
                            askToPutOntoBf = true;
                            cardToPutOnBf = card;
                        }
                        cards.add(card);
                    }
                }
                if (askToPutOntoBf && controller.chooseUse(Outcome.PutCardInPlay, "Put " + cardToPutOnBf.getLogName() + " onto the battlefield instead?", source, game)) {
                    controller.moveCards(cards, Zone.BATTLEFIELD, source, game);
                } else {
                    controller.moveCards(cards, Zone.HAND, source, game);
                }
                if (revealCards) {
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
        if (target.getNumberOfTargets() == 0 && target.getMaxNumberOfTargets() > 0) {
            sb.append("up to ").append(CardUtil.numberToText(target.getMaxNumberOfTargets())).append(' ');
            sb.append(target.getTargetName()).append(revealCards ? ", reveal them," : "").append(" and put them into your hand");
        } else {
            sb.append("a ").append(target.getTargetName()).append(revealCards ? ", reveal it," : "").append(" and put that card into your hand");
        }
        if (nameToPutOnBattlefield != null) {
            sb.append(". If you reveal a card named ");
            sb.append(nameToPutOnBattlefield);
            sb.append("this way, you may put it onto the battlefield instead");
        }
        sb.append(". Then shuffle");
        staticText = sb.toString();
    }

}
