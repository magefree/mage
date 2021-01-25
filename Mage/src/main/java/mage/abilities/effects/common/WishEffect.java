package mage.abilities.effects.common;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;

import java.util.List;
import java.util.Set;

/**
 * @author Styxo
 */
public class WishEffect extends OneShotEffect {

    private final FilterCard filter;
    private final boolean reveal;
    private final boolean alsoFromExile;
    private final String choiceText;
    private final boolean topOfLibrary;

    public WishEffect() {
        this(new FilterCard());
    }

    public WishEffect(FilterCard filter) {
        this(filter, true);
    }

    public WishEffect(FilterCard filter, boolean reveal) {
        this(filter, reveal, false);
    }

    public WishEffect(FilterCard filter, boolean reveal, boolean alsoFromExile) {
        this(filter, reveal, alsoFromExile, false);
    }

    public WishEffect(FilterCard filter, boolean reveal, boolean alsoFromExile, boolean topOfLibrary) {
        super(Outcome.DrawCard);
        this.filter = filter;
        this.alsoFromExile = alsoFromExile;
        this.reveal = reveal;
        this.topOfLibrary = topOfLibrary;
        choiceText = (topOfLibrary ? "Put " : "Choose ") + filter.getMessage() + " you own from outside the game"
                + (alsoFromExile ? " or in exile" : "")
                + (reveal ? ", reveal that card," : "")
                + (topOfLibrary ? " on top of your library." : " and put it into your hand.");
        staticText = "You may " + Character.toLowerCase(choiceText.charAt(0)) + choiceText.substring(1, choiceText.length() - 1);
    }

    public WishEffect(final WishEffect effect) {
        super(effect);
        this.filter = effect.filter;
        this.alsoFromExile = effect.alsoFromExile;
        this.reveal = effect.reveal;
        this.choiceText = effect.choiceText;
        this.topOfLibrary = effect.topOfLibrary;
    }

    @Override
    public WishEffect copy() {
        return new WishEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = source.getSourceObject(game);
        if (controller != null && sourceObject != null) {
            if (controller.chooseUse(Outcome.Benefit, choiceText, source, game)) {
                Cards cards = controller.getSideboard();
                List<Card> exile = game.getExile().getAllCards(game);
                boolean noTargets = cards.isEmpty() && (!alsoFromExile || exile.isEmpty());
                if (noTargets) {
                    game.informPlayer(controller, "You have no cards outside the game" + (alsoFromExile ? " or in exile" : "") + '.');
                    return true;
                }

                Set<Card> filtered = cards.getCards(filter, game);
                Cards filteredCards = new CardsImpl();
                for (Card card : filtered) {
                    filteredCards.add(card.getId());
                }
                if (alsoFromExile) {
                    for (Card exileCard : exile) {
                        if (exileCard.isOwnedBy(source.getControllerId()) && filter.match(exileCard, game)) {
                            filteredCards.add(exileCard);
                        }
                    }
                }
                if (filteredCards.isEmpty()) {
                    game.informPlayer(controller, "You don't have " + filter.getMessage() + " outside the game" + (alsoFromExile ? " or in exile" : "") + '.');
                    return true;
                }

                TargetCard target = new TargetCard(Zone.ALL, filter);
                target.setNotTarget(true);
                if (controller.choose(Outcome.Benefit, filteredCards, target, game)) {
                    Card card = controller.getSideboard().get(target.getFirstTarget(), game);
                    if (card == null && alsoFromExile) {
                        card = game.getCard(target.getFirstTarget());
                    }
                    if (card != null) {
                        if (topOfLibrary) {
                            controller.putCardsOnTopOfLibrary(card, game, source, true);
                        } else {
                            card.moveToZone(Zone.HAND, source, game, false);
                        }
                        if (reveal) {
                            Cards revealCard = new CardsImpl();
                            revealCard.add(card);
                            controller.revealCards(sourceObject.getIdName(), revealCard, game);
                        }
                    }
                }
            }
            return true;
        }
        return false;
    }

}
