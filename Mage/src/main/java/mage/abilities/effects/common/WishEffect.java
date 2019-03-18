
package mage.abilities.effects.common;

import java.util.List;
import java.util.Set;
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

/**
 *
 * @author Styxo
 */
public class WishEffect extends OneShotEffect {

    private final FilterCard filter;
    private final boolean reveal;
    private final boolean alsoFromExile;
    private final String choiceText;

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
        super(Outcome.DrawCard);
        this.filter = filter;
        this.alsoFromExile = alsoFromExile;
        this.reveal = reveal;
        choiceText = "Choose " + filter.getMessage() + " you own from outside the game"
                + (alsoFromExile ? " or in exile" : "")
                + (reveal ? ", reveal that card," : "")
                + " and put it into your hand.";
        staticText = "You may c" + choiceText.substring(1, choiceText.length() - 1);

    }

    public WishEffect(final WishEffect effect) {
        super(effect);
        this.filter = effect.filter;
        this.alsoFromExile = effect.alsoFromExile;
        this.reveal = effect.reveal;
        this.choiceText = effect.choiceText;
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
                boolean noTargets = cards.isEmpty() && (alsoFromExile ? exile.isEmpty() : true);
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

                TargetCard target = new TargetCard(Zone.OUTSIDE, filter);
                target.setNotTarget(true);
                if (controller.choose(Outcome.Benefit, filteredCards, target, game)) {
                    Card card = controller.getSideboard().get(target.getFirstTarget(), game);
                    if (card == null && alsoFromExile) {
                        card = game.getCard(target.getFirstTarget());
                    }
                    if (card != null) {
                        card.moveToZone(Zone.HAND, source.getSourceId(), game, false);
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
