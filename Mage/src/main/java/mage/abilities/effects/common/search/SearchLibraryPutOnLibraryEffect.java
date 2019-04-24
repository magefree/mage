
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

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class SearchLibraryPutOnLibraryEffect extends SearchEffect {

    private boolean reveal;
    private boolean forceShuffle;

    public SearchLibraryPutOnLibraryEffect(TargetCardInLibrary target) {
        this(target, false, true);
        setText();
    }

    public SearchLibraryPutOnLibraryEffect(TargetCardInLibrary target, boolean reveal, boolean forceShuffle) {
        super(target, Outcome.DrawCard);
        this.reveal = reveal;
        this.forceShuffle = forceShuffle;
        setText();
    }

    public SearchLibraryPutOnLibraryEffect(final SearchLibraryPutOnLibraryEffect effect) {
        super(effect);
        this.reveal = effect.reveal;
        this.forceShuffle = effect.forceShuffle;
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
        if (controller.searchLibrary(target, game)) {
            Cards foundCards = new CardsImpl(target.getTargets());
            if (reveal && !foundCards.isEmpty()) {
                controller.revealCards(sourceObject.getIdName(), foundCards, game);
            }
            if (forceShuffle) {
                controller.shuffleLibrary(source, game);
            }
            controller.putCardsOnTopOfLibrary(foundCards, game, source, reveal);
            return true;
        }
        // shuffle
        if (forceShuffle) {
            controller.shuffleLibrary(source, game);
        }
        return false;
    }

    private void setText() {
        StringBuilder sb = new StringBuilder();
        sb.append("search your library for a ").append(target.getTargetName());
        if (reveal) {
            sb.append(" and reveal that card. Shuffle");
        } else {
            sb.append(", then shuffle");
        }
        sb.append(" your library and put that card on top of it");
        staticText = sb.toString();
    }

}
