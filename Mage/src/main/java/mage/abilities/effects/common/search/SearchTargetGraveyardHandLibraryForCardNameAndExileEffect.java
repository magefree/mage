
package mage.abilities.effects.common.search;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 * @author LevelX2
 */
public abstract class SearchTargetGraveyardHandLibraryForCardNameAndExileEffect extends OneShotEffect {

    protected String searchWhatText;
    protected String searchForText;

    /* Slaughter Games
     * 10/1/2012: You can leave any cards with that name in the zone they are in. You don't have to exile them.
     *
     * Sowing Salt
     * 2/1/2005: The copies must be found if they are in publicly viewable zones. Finding copies while searching private zones is optional.
     */
    protected boolean graveyardExileOptional;

    public SearchTargetGraveyardHandLibraryForCardNameAndExileEffect(boolean graveyardExileOptional, String searchWhatText, String searchForText) {
        super(Outcome.Exile);
        this.searchWhatText = searchWhatText;
        this.searchForText = searchForText;
        this.graveyardExileOptional = graveyardExileOptional;
        this.staticText = "search " + searchWhatText + " graveyard, hand, and library for " + searchForText + " and exile them. Then that player shuffles";
    }

    public SearchTargetGraveyardHandLibraryForCardNameAndExileEffect(final SearchTargetGraveyardHandLibraryForCardNameAndExileEffect effect) {
        super(effect);
        this.searchWhatText = effect.searchWhatText;
        this.searchForText = effect.searchForText;
        this.graveyardExileOptional = effect.graveyardExileOptional;
    }

    /**
     * @param game
     * @param source
     * @param cardName       name of the card to exile
     * @param targetPlayerId id of the target player to exile card name from his
     *                       or her zones
     * @return
     */
    public boolean applySearchAndExile(Game game, Ability source, String cardName, UUID targetPlayerId) {
        Player controller = game.getPlayer(source.getControllerId());
        if (cardName != null && controller != null) {
            Player targetPlayer = game.getPlayer(targetPlayerId);
            if (targetPlayer != null) {
                FilterCard filter = new FilterCard("card named " + cardName);
                filter.add(new NamePredicate(cardName));

                // cards in Graveyard
                int cardsCount = (cardName.isEmpty() ? 0 : targetPlayer.getGraveyard().count(filter, game));
                if (cardsCount > 0) {
                    filter.setMessage("card named " + cardName + " in the graveyard of " + targetPlayer.getName());
                    TargetCard target = new TargetCard((graveyardExileOptional ? 0 : cardsCount), cardsCount, Zone.GRAVEYARD, filter);
                    if (controller.choose(Outcome.Exile, targetPlayer.getGraveyard(), target, source, game)) {
                        controller.moveCards(new CardsImpl(target.getTargets()), Zone.EXILED, source, game);
                    }
                }

                // cards in Hand
                cardsCount = (cardName.isEmpty() ? 0 : targetPlayer.getHand().count(filter, game));
                filter.setMessage("card named " + cardName + " in the hand of " + targetPlayer.getName());
                TargetCard target = new TargetCard(0, cardsCount, Zone.HAND, filter);
                if (controller.choose(Outcome.Exile, targetPlayer.getHand(), target, source, game)) {
                    controller.moveCards(new CardsImpl(target.getTargets()), Zone.EXILED, source, game);
                }

                // cards in Library
                Cards cardsInLibrary = new CardsImpl();
                cardsInLibrary.addAllCards(targetPlayer.getLibrary().getCards(game));
                cardsCount = (cardName.isEmpty() ? 0 : cardsInLibrary.count(filter, game));
                filter.setMessage("card named " + cardName + " in the library of " + targetPlayer.getLogName());
                TargetCardInLibrary targetLib = new TargetCardInLibrary(0, cardsCount, filter);
                if (controller.choose(Outcome.Exile, cardsInLibrary, targetLib, source, game)) {
                    controller.moveCards(new CardsImpl(targetLib.getTargets()), Zone.EXILED, source, game);
                }
                targetPlayer.shuffleLibrary(source, game);
            }

            return true;
        }

        return false;
    }
}
