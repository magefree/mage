package mage.client.cards;

import mage.client.deckeditor.SortSetting;
import mage.client.util.Event;
import mage.client.util.Listener;
import mage.view.CardsView;

import java.util.UUID;

/**
 * Interface for card container.
 *
 * @author nantuko
 */
public interface ICardGrid {
    void clearCardEventListeners();

    void addCardEventListener(Listener<Event> listener);

    void drawCards(SortSetting sortSetting);

    void loadCards(CardsView showCards, SortSetting sortSetting, BigCard bigCard, UUID gameId);

    void loadCards(CardsView showCards, SortSetting sortSetting, BigCard bigCard, UUID gameId, boolean merge);

    void refresh();

    int cardsSize();
}
