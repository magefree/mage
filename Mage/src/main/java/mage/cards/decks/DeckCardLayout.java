package mage.cards.decks;

import java.util.List;

/**
 * Created by stravant@gmail.com on 2016-10-03.
 */
public class DeckCardLayout {
    private List<List<List<DeckCardInfo>>> cards;
    private String settings;

    public DeckCardLayout(List<List<List<DeckCardInfo>>> cards, String settings) {
        this.cards = cards;
        this.settings = settings;
    }

    public List<List<List<DeckCardInfo>>> getCards() {
        return cards;
    }

    public String getSettings() {
        return settings;
    }
}
