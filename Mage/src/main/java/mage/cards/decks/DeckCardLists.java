package mage.cards.decks;

import mage.util.CardUtil;
import mage.util.Copyable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author BetaSteward_at_googlemail.com, JayDi85
 */
public class DeckCardLists implements Serializable, Copyable<DeckCardLists> {

    private String name = null;
    private String author = null;
    private List<DeckCardInfo> cards = new ArrayList<>();
    private List<DeckCardInfo> sideboard = new ArrayList<>();

    // Layout (if supported)
    private DeckCardLayout cardLayout = null;
    private DeckCardLayout sideboardLayout = null;

    public DeckCardLists() {
    }

    protected DeckCardLists(final DeckCardLists deck) {
        this.name = deck.name;
        this.author = deck.author;
        this.cards = CardUtil.deepCopyObject(deck.cards);
        this.sideboard = CardUtil.deepCopyObject(deck.sideboard);
        this.cardLayout = CardUtil.deepCopyObject(deck.cardLayout);
        this.sideboardLayout = CardUtil.deepCopyObject(deck.sideboardLayout);
    }

    @Override
    public DeckCardLists copy() {
        return new DeckCardLists(this);
    }

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
