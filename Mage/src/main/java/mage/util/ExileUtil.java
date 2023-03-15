package mage.util;

import java.util.Set;

import mage.cards.Card;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.cards.MeldCard;
import mage.game.Game;
import mage.game.permanent.PermanentCard;
import mage.game.permanent.PermanentMeld;

public class ExileUtil {
  public static Cards returnCardsFromExile(Set<Card> cards, Game game) {
    Cards cardsToReturn = new CardsImpl();
    for (Card exiled : cards) {
      if (exiled instanceof PermanentMeld) {
        MeldCard meldCard = (MeldCard) ((PermanentCard) exiled).getCard();
        Card topCard = meldCard.getTopHalfCard();
        Card bottomCard = meldCard.getBottomHalfCard();
        if (topCard.getZoneChangeCounter(game) == meldCard.getTopLastZoneChangeCounter()) {
          cardsToReturn.add(topCard);
        }
        if (bottomCard.getZoneChangeCounter(game) == meldCard.getBottomLastZoneChangeCounter()) {
          cardsToReturn.add(bottomCard);
        }
      } else if (exiled.getZoneChangeCounter(game) == game.getState().getZoneChangeCounter(exiled.getId()) - 1) {
        cardsToReturn.add(exiled);
      }
    }

    return cardsToReturn;
  }
}
