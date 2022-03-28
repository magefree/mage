package mage.abilities.effects.common;

import java.util.Set;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.players.Player;
import mage.util.CardUtil;

/**
 *
 * @author LevelX
 */
public class RevealLibraryPutIntoHandEffect extends OneShotEffect {

    private DynamicValue amountCards;
    private FilterCard filter;
    private Zone zoneToPutRest;
    private boolean anyOrder;

    public RevealLibraryPutIntoHandEffect(int amountCards, FilterCard filter, Zone zoneToPutRest) {
        this(amountCards, filter, zoneToPutRest, true);
    }

    public RevealLibraryPutIntoHandEffect(int amountCards, FilterCard filter, Zone zoneToPutRest, boolean anyOrder) {
        this(StaticValue.get(amountCards), filter, zoneToPutRest, anyOrder);
    }

    public RevealLibraryPutIntoHandEffect(DynamicValue amountCards, FilterCard filter, Zone zoneToPutRest, boolean anyOrder) {
        super(Outcome.DrawCard);
        this.amountCards = amountCards;
        this.filter = filter;
        this.zoneToPutRest = zoneToPutRest;
        this.anyOrder = anyOrder;
        this.staticText = setText();
    }

    public RevealLibraryPutIntoHandEffect(final RevealLibraryPutIntoHandEffect effect) {
        super(effect);
        this.amountCards = effect.amountCards;
        this.filter = effect.filter;
        this.zoneToPutRest = effect.zoneToPutRest;
        this.anyOrder = effect.anyOrder;
    }

    @Override
    public RevealLibraryPutIntoHandEffect copy() {
        return new RevealLibraryPutIntoHandEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = game.getObject(source);
        if (controller == null || sourceObject == null) {
            return false;
        }

        CardsImpl cards = new CardsImpl();
        cards.addAll(controller.getLibrary().getTopCards(game, amountCards.calculate(game, source, this)));
        controller.revealCards(sourceObject.getIdName(), cards, game);

        Set<Card> cardsList = cards.getCards(game);
        Cards cardsToHand = new CardsImpl();
        for (Card card : cardsList) {
            if (filter.match(card, controller.getId(), source, game)) {
                cardsToHand.add(card);
                cards.remove(card);
            }
        }
        controller.moveCards(cardsToHand, Zone.HAND, source, game);
        switch (zoneToPutRest) {
            case LIBRARY: {
                controller.putCardsOnBottomOfLibrary(cards, game, source, anyOrder);
                break;
            }
            default:
                controller.moveCards(cards, zoneToPutRest, source, game);
        }
        return true;
    }

    private String setText() {
        StringBuilder sb = new StringBuilder("reveal the top ");
        sb.append(CardUtil.numberToText(amountCards.toString())).append(" cards of your library. Put all ");
        sb.append(filter.getMessage());
        sb.append(" revealed this way into your hand and the rest ");
        switch (zoneToPutRest) {
            case LIBRARY: {
                sb.append("on the bottom of your library");
                if (anyOrder) {
                    sb.append(" in any order");
                } else {
                    sb.append(" in a random order");
                }
                break;
            }
            case GRAVEYARD:
                sb.append("into your graveyard");
        }

        return sb.toString();
    }
}
