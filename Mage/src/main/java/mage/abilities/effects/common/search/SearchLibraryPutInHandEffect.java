
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
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author LokiX, BetaSteward_at_googlemail.com
 */
public class SearchLibraryPutInHandEffect extends SearchEffect {

    private boolean revealCards = false;
    private boolean forceShuffle;
    private String rulePrefix;

    public SearchLibraryPutInHandEffect(TargetCardInLibrary target) {
        this(target, false, true);
    }

    public SearchLibraryPutInHandEffect(TargetCardInLibrary target, boolean revealCards) {
        this(target, revealCards, true);
    }

    public SearchLibraryPutInHandEffect(TargetCardInLibrary target, boolean revealCards, boolean forceShuffle) {
        this(target, revealCards, forceShuffle, "search your library for ");
    }

    public SearchLibraryPutInHandEffect(TargetCardInLibrary target, boolean revealCards, boolean forceShuffle, String rulePrefix) {
        super(target, Outcome.DrawCard);
        this.revealCards = revealCards;
        this.forceShuffle = forceShuffle;
        this.rulePrefix = rulePrefix;
        setText();
    }

    public SearchLibraryPutInHandEffect(final SearchLibraryPutInHandEffect effect) {
        super(effect);
        this.revealCards = effect.revealCards;
        this.forceShuffle = effect.forceShuffle;
        this.rulePrefix = effect.rulePrefix;
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
        if (forceShuffle) {
            controller.shuffleLibrary(source, game);
        }
        return false;
    }

    private void setText() {
        StringBuilder sb = new StringBuilder();
        sb.append(rulePrefix);
        if (target.getNumberOfTargets() == 0 && target.getMaxNumberOfTargets() > 0) {
            sb.append("up to ").append(CardUtil.numberToText(target.getMaxNumberOfTargets())).append(' ');
            sb.append(target.getTargetName());
            if (forceShuffle) {
                sb.append(revealCards ? ", reveal them" : "");
                sb.append(", put them into your hand, then shuffle");
            } else {
                sb.append(revealCards ? ", reveal them," : "");
                sb.append(" and put them into your hand. If you do, shuffle");
            }
        } else {
            sb.append(CardUtil.addArticle(target.getTargetName()));
            if (forceShuffle) {
                sb.append(revealCards ? ", reveal it, put it" : ", put that card");
                sb.append(" into your hand, then shuffle");
            } else {
                sb.append(revealCards ? ", reveal it," : "");
                sb.append(" and put that card into your hand. If you do, shuffle");
            }
        }
        staticText = sb.toString();
    }

}
