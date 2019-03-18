
package mage.cards.decks;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class DeckCardLists implements Serializable {

    private String name;
    private String author;
    private List<DeckCardInfo> cards = new ArrayList<>();
    private List<DeckCardInfo> sideboard = new ArrayList<>();

    // Layout (if supported)
    private DeckCardLayout cardLayout = null;
    private DeckCardLayout sideboardLayout = null;

    /**
     * @return The layout of the cards
     */
    public DeckCardLayout getCardLayout() {
        return cardLayout;
    }
    public void setCardLayout(DeckCardLayout layout) {
        this.cardLayout = layout;
    }
    public DeckCardLayout getSideboardLayout() {
        return sideboardLayout;
    }
    public void setSideboardLayout(DeckCardLayout layout) {
        this.sideboardLayout = layout;
    }

    /**
     * @return the cards
     */
    public List<DeckCardInfo> getCards() {
        return cards;
    }

    /**
     * @param cards the cards to set
     */
    public void setCards(List<DeckCardInfo> cards) {
        this.cards = cards;
    }

    /**
     * @return the sideboard
     */
    public List<DeckCardInfo> getSideboard() {
        return sideboard;
    }

    /**
     * @param sideboard the sideboard to set
     */
    public void setSideboard(List<DeckCardInfo> sideboard) {
        this.sideboard = sideboard;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
