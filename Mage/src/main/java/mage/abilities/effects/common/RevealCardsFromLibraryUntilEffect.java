package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.Outcome;
import mage.constants.PutCards;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.players.Player;
import mage.util.CardUtil;

/**
 * @author Styxo
 */
public class RevealCardsFromLibraryUntilEffect extends OneShotEffect {

    private final FilterCard filter;
    private final PutCards putPickedCard;
    private final PutCards putRemainingCards;

    public RevealCardsFromLibraryUntilEffect(FilterCard filter, PutCards putPickedCard, PutCards putRemainingCards) {
        super(Outcome.Benefit);
        this.filter = filter;
        this.putPickedCard = putPickedCard;
        this.putRemainingCards = putRemainingCards;
    }

    private RevealCardsFromLibraryUntilEffect(final RevealCardsFromLibraryUntilEffect effect) {
        super(effect);
        this.filter = effect.filter;
        this.putPickedCard = effect.putPickedCard;
        this.putRemainingCards = effect.putRemainingCards;
    }

    @Override
    public RevealCardsFromLibraryUntilEffect copy() {
        return new RevealCardsFromLibraryUntilEffect(this);
    }

    private Card getCard(Player controller, Cards cards, Ability source, Game game) {
        for (Card card : controller.getLibrary().getCards(game)) {
            cards.add(card);
            if (filter.match(card, source.getControllerId(), source, game)) {
                return card;
            }
        }
        return null;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null || !controller.getLibrary().hasCards()) {
            return false;
        }
        Cards cards = new CardsImpl();
        Card card = getCard(controller, cards, source, game);
        controller.revealCards(source, cards, game);
        putPickedCard.moveCard(controller, card, source, game, "card");
        if (putPickedCard.getZone() == Zone.LIBRARY) {
            cards.remove(card);
        } else {
            cards.retainZone(Zone.LIBRARY, game);
        }
        putRemainingCards.moveCards(controller, cards, source, game);
        return true;
    }

    @Override
    public String getText(Mode mode) {
        if (staticText != null && !staticText.isEmpty()) {
            return staticText;
        }
        StringBuilder sb = new StringBuilder("reveal cards from the top of your library until you reveal ");
        sb.append(CardUtil.addArticle(filter.getMessage()));
        sb.append(". Put that card ");
        sb.append(putPickedCard.getMessage(false, false));
        switch (putRemainingCards) {
            case SHUFFLE:
                sb.append(" and shuffle the rest into your library");
                break;
            case EXILED:
                sb.append(" and exile all other cards revealed this way.");
                break;
            case GRAVEYARD:
                sb.append(" and all other cards revealed this way into your graveyard");
                break;
            default:
                sb.append(" and the rest ");
                sb.append(putRemainingCards.getMessage(false, true));
        }
        return sb.toString();
    }
}
