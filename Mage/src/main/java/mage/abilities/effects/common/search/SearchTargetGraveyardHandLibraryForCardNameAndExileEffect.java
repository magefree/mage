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
    protected boolean drawForEachHandCard;
    protected int maxAmount;

    protected SearchTargetGraveyardHandLibraryForCardNameAndExileEffect(boolean graveyardExileOptional, String searchWhatText, String searchForText) {
        this(graveyardExileOptional, searchWhatText, searchForText, false);
    }

    protected SearchTargetGraveyardHandLibraryForCardNameAndExileEffect(boolean graveyardExileOptional, String searchWhatText, String searchForText, boolean drawForEachHandCard) {
        this(graveyardExileOptional, searchWhatText, searchForText, drawForEachHandCard, Integer.MAX_VALUE);
    }
    protected SearchTargetGraveyardHandLibraryForCardNameAndExileEffect(boolean graveyardExileOptional, String searchWhatText, String searchForText, boolean drawForEachHandCard, int maxAmount) {
        super(Outcome.Exile);
        this.searchWhatText = searchWhatText;
        this.searchForText = searchForText;
        this.graveyardExileOptional = graveyardExileOptional;
        this.drawForEachHandCard = drawForEachHandCard;
        this.maxAmount = maxAmount;
        this.staticText = "search " + searchWhatText + " graveyard, hand, and library for " + searchForText + " and exile them. " +
                (drawForEachHandCard ? "That player shuffles, then draws a card for each card exiled from their hand this way" : "Then that player shuffles");
    }

    protected SearchTargetGraveyardHandLibraryForCardNameAndExileEffect(final SearchTargetGraveyardHandLibraryForCardNameAndExileEffect effect) {
        super(effect);
        this.searchWhatText = effect.searchWhatText;
        this.searchForText = effect.searchForText;
        this.graveyardExileOptional = effect.graveyardExileOptional;
        this.drawForEachHandCard = effect.drawForEachHandCard;
        this.maxAmount = effect.maxAmount;
    }

    /**
     * @param game
     * @param source
     * @param cardName       name of the card to exile
     * @param targetPlayerId id of the target player to exile card name from his
     *                       or her zones
     * @return
     */
    protected boolean applySearchAndExile(Game game, Ability source, String cardName, UUID targetPlayerId) {
        Player controller = game.getPlayer(source.getControllerId());
        if (cardName != null && controller != null) {
            Player targetPlayer = game.getPlayer(targetPlayerId);
            if (targetPlayer != null) {
                int handCards = 0;
                int maxRemaining = maxAmount;
                FilterCard filter = new FilterCard("card named \"" + cardName + "\"");
                filter.add(new NamePredicate(cardName));

                // cards in Graveyard
                int cardsCount = Math.min(targetPlayer.getGraveyard().count(filter, game), maxRemaining);
                if (cardsCount > 0) {
                    filter.setMessage("card named " + cardName + " in the graveyard of " + targetPlayer.getName());
                    TargetCard target = new TargetCard((graveyardExileOptional ? 0 : cardsCount), cardsCount, Zone.GRAVEYARD, filter);
                    target.withNotTarget(true);
                    if (controller.choose(Outcome.Exile, targetPlayer.getGraveyard(), target, source, game)) {
                        maxRemaining -= target.getTargets().size();
                        controller.moveCards(new CardsImpl(target.getTargets()), Zone.EXILED, source, game);
                    }
                }

                // cards in Hand
                cardsCount = Math.min(targetPlayer.getHand().count(filter, game), maxRemaining);
                filter.setMessage("card named " + cardName + " in the hand of " + targetPlayer.getName());
                TargetCard target = new TargetCard(0, cardsCount, Zone.HAND, filter);
                target.withNotTarget(true);
                if (controller.choose(Outcome.Exile, targetPlayer.getHand(), target, source, game)) {
                    maxRemaining -= target.getTargets().size();
                    if (drawForEachHandCard) {
                        handCards = target.getTargets().size();
                    }
                    controller.moveCards(new CardsImpl(target.getTargets()), Zone.EXILED, source, game);
                }

                // cards in Library
                Cards cardsInLibrary = new CardsImpl();
                cardsInLibrary.addAllCards(targetPlayer.getLibrary().getCards(game));
                cardsCount = Math.min(cardsInLibrary.count(filter, game), maxRemaining);
                filter.setMessage("card named " + cardName + " in the library of " + targetPlayer.getLogName());
                TargetCardInLibrary targetLib = new TargetCardInLibrary(0, cardsCount, filter);
                if (controller.choose(Outcome.Exile, cardsInLibrary, targetLib, source, game)) {
                    controller.moveCards(new CardsImpl(targetLib.getTargets()), Zone.EXILED, source, game);
                }
                targetPlayer.shuffleLibrary(source, game);

                if (handCards > 0) {
                    targetPlayer.drawCards(handCards, source, game);
                }
            }

            return true;
        }

        return false;
    }
}
