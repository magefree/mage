package mage.abilities.effects.common.search;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.Outcome;
import mage.constants.PutCards;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.TargetPlayer;
import mage.target.common.TargetCardInLibrary;
import mage.target.common.TargetCardWithDifferentNameInLibrary;
import mage.target.common.TargetOpponent;

/**
 * @author TheElk801
 */
public class SearchLibraryForFourDifferentCardsEffect extends OneShotEffect {

    private final FilterCard filter;
    private final PutCards putCards;
    private final boolean useTargetPointer;
    private static final FilterCard filter2 = new FilterCard("cards to put in graveyard");

    public SearchLibraryForFourDifferentCardsEffect(FilterCard filter, PutCards putCards, boolean useTargetPointer) {
        super(Outcome.Benefit);
        this.filter = filter;
        this.putCards = putCards;
        this.useTargetPointer = useTargetPointer;
        staticText = "search your library for up to four " + filter +
                " with different names and reveal them. " + (useTargetPointer ? "Target" : "An") +
                " opponent chooses two of those cards. Put the chosen cards into your graveyard and the rest " +
                putCards.getMessage(false, false) + ", then shuffle";
    }

    private SearchLibraryForFourDifferentCardsEffect(final SearchLibraryForFourDifferentCardsEffect effect) {
        super(effect);
        this.filter = effect.filter;
        this.putCards = effect.putCards;
        this.useTargetPointer = effect.useTargetPointer;
    }

    @Override
    public SearchLibraryForFourDifferentCardsEffect copy() {
        return new SearchLibraryForFourDifferentCardsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        TargetCardInLibrary targetCards = new TargetCardWithDifferentNameInLibrary(0, 4, filter);
        player.searchLibrary(targetCards, source, game);
        Cards cards = new CardsImpl(targetCards.getTargets());
        cards.retainZone(Zone.LIBRARY, game);
        player.revealCards(source, cards, game);
        if (cards.isEmpty()) {
            player.shuffleLibrary(source, game);
            return true;
        }

        Cards toGrave = new CardsImpl();
        if (cards.size() > 2) {
            Player opponent;
            if (useTargetPointer) {
                opponent = game.getPlayer(getTargetPointer().getFirst(game, source));
            } else {
                TargetPlayer target = new TargetOpponent(true);
                player.choose(outcome, target, source, game);
                opponent = game.getPlayer(target.getFirstTarget());
            }
            if (opponent != null) {
                TargetCard targetDiscard = new TargetCard(2, Zone.LIBRARY, filter2);
                opponent.choose(Outcome.Discard, cards, targetDiscard, source, game);
                toGrave.addAll(targetDiscard.getTargets());
            }
        } else {
            toGrave.addAll(cards);
        }
        cards.removeAll(toGrave);
        player.moveCards(toGrave, Zone.GRAVEYARD, source, game);
        putCards.moveCards(player, cards, source, game);
        player.shuffleLibrary(source, game);
        return true;
    }
}
