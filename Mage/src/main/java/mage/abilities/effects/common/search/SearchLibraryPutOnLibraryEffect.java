
package mage.abilities.effects.common.search;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.SearchEffect;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;
import mage.util.CardUtil;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class SearchLibraryPutOnLibraryEffect extends SearchEffect {

    private boolean reveal;

    public SearchLibraryPutOnLibraryEffect(TargetCardInLibrary target, boolean reveal) {
        super(target, Outcome.DrawCard);
        this.reveal = reveal;
        setText();
    }

    public SearchLibraryPutOnLibraryEffect(final SearchLibraryPutOnLibraryEffect effect) {
        super(effect);
        this.reveal = effect.reveal;
    }

    @Override
    public SearchLibraryPutOnLibraryEffect copy() {
        return new SearchLibraryPutOnLibraryEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = source.getSourceObject(game);
        if (controller == null || sourceObject == null) {
            return false;
        }
        if (controller.searchLibrary(target, source, game)) {
            Cards foundCards = new CardsImpl(target.getTargets());
            if (reveal && !foundCards.isEmpty()) {
                controller.revealCards(sourceObject.getIdName(), foundCards, game);
            }
            controller.shuffleLibrary(source, game);
            controller.putCardsOnTopOfLibrary(foundCards, game, source, reveal);
            return true;
        }
        controller.shuffleLibrary(source, game);
        return false;
    }

    private void setText() {
        StringBuilder sb = new StringBuilder();
        sb.append("search your library for ").append(CardUtil.addArticle(target.getTargetName()));
        if (reveal) {
            sb.append(", reveal it");
        }
        sb.append(", then shuffle and put that card on top");
        staticText = sb.toString();
    }
}
