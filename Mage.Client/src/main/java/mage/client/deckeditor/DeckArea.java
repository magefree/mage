package mage.client.deckeditor;

import mage.cards.Card;
import mage.cards.decks.Deck;
import mage.cards.decks.DeckCardLayout;
import mage.client.cards.BigCard;
import mage.client.cards.CardEventSource;
import mage.client.cards.DragCardGrid;
import mage.client.constants.Constants.DeckEditorMode;
import mage.client.util.ClientEventType;
import mage.client.util.Event;
import mage.client.util.GUISizeHelper;
import mage.client.util.Listener;
import mage.view.CardView;
import mage.view.CardsView;

import javax.swing.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class DeckArea extends javax.swing.JPanel {

    private final CardEventSource maindeckVirtualEvent = new CardEventSource();
    private final CardEventSource sideboardVirtualEvent = new CardEventSource();
    private final Set<UUID> hiddenCards = new HashSet<>();
    private Deck lastDeck = new Deck();
    private BigCard lastBigCard = null;
    private int dividerLocationNormal = 0;
    private int dividerLocationLimited = 0;

    public DeckCardLayout getCardLayout() {
        return deckList.getCardLayout();
    }

    public DeckCardLayout getSideboardLayout() {
        return sideboardList.getCardLayout();
    }

    public static class Settings {

        public DragCardGrid.Settings maindeckSettings;
        public DragCardGrid.Settings sideboardSetings;
        public int dividerLocationLimited;
        public int dividerLocationNormal;

        private static final Pattern parser = Pattern.compile("([^|]*)\\|([^|]*)\\|([^|]*)\\|([^|]*)");

        public static Settings parse(String s) {
            Matcher m = parser.matcher(s);
            if (m.find()) {
                Settings settings = new Settings();
                settings.maindeckSettings = DragCardGrid.Settings.parse(m.group(1));
                settings.sideboardSetings = DragCardGrid.Settings.parse(m.group(2));
                settings.dividerLocationNormal = Integer.parseInt(m.group(3));
                settings.dividerLocationLimited = Integer.parseInt(m.group(4));
                return settings;
            } else {
                return null;
            }
        }

        @Override
        public String toString() {
            return maindeckSettings.toString() + '|' + sideboardSetings.toString() + '|' + dividerLocationNormal + '|' + dividerLocationLimited;
        }
    }

    /**
     * Creates new form DeckArea
     */
    public DeckArea() {
        initComponents();
        //deckAreaSplitPane.setOpaque(false);
        //deckList.setOpaque(false);
        //sideboardList.setOpaque(false);
        deckList.setRole(DragCardGrid.Role.MAINDECK);
        sideboardList.setRole(DragCardGrid.Role.SIDEBOARD);

        // When a selection happens in one pane, deselect the selection in the other
        deckList.addDragCardGridListener(new DragCardGrid.DragCardGridListener() {
            @Override
            public void cardsSelected() {
                sideboardList.deselectAll();
            }

            @Override
            public void hideCards(Collection<CardView> cards) {
                // Add to hidden and move to sideboard
                for (CardView card : cards) {
                    hiddenCards.add(card.getId());
                    maindeckVirtualEvent.fireEvent(card, ClientEventType.DECK_REMOVE_SPECIFIC_CARD);
                    sideboardVirtualEvent.fireEvent(card, ClientEventType.DECK_ADD_SPECIFIC_CARD);
                }
                loadDeck(lastDeck, lastBigCard);
            }

            @Override
            public void showAll() {
                hiddenCards.clear();
                loadDeck(lastDeck, lastBigCard);
            }

            @Override
            public void duplicateCards(Collection<CardView> cards) {
                sideboardList.deselectAll();
                for (CardView card : cards) {
                    CardView newCard = new CardView(card);
                    deckList.addCardView(newCard, true);
                }
            }

            @Override
            public void invertCardSelection(Collection<CardView> cards) {
                // Invert Selection
                for (CardView card : cards) {
                    card.setSelected(!card.isSelected());
                }
            }
        });
        sideboardList.addDragCardGridListener(new DragCardGrid.DragCardGridListener() {
            @Override
            public void cardsSelected() {
                deckList.deselectAll();
            }

            @Override
            public void hideCards(Collection<CardView> cards) {
                // Just add to hidden, already in sideboard
                for (CardView card : cards) {
                    hiddenCards.add(card.getId());
                }
                loadDeck(lastDeck, lastBigCard);
            }

            @Override
            public void showAll() {
                hiddenCards.clear();
                loadDeck(lastDeck, lastBigCard);
            }

            @Override
            public void duplicateCards(Collection<CardView> cards) {
                deckList.deselectAll();
                for (CardView card : cards) {
                    CardView newCard = new CardView(card);
                    sideboardList.addCardView(newCard, true);
                }
            }

            @Override
            public void invertCardSelection(Collection<CardView> cards) {
                // Invert Selection
                for (CardView card : cards) {
                    card.setSelected(!card.isSelected());
                }
            }

        });
    }

    public Settings saveSettings(boolean isLimitedBuildingOrientation) {
        Settings settings = new Settings();
        settings.maindeckSettings = deckList.saveSettings();
        settings.sideboardSetings = sideboardList.saveSettings();
        if (isLimitedBuildingOrientation) {
            dividerLocationLimited = deckAreaSplitPane.getDividerLocation();
        } else {
            dividerLocationNormal = deckAreaSplitPane.getDividerLocation();
        }
        settings.dividerLocationLimited = dividerLocationLimited;
        settings.dividerLocationNormal = dividerLocationNormal;
        return settings;
    }

    public void loadSettings(Settings s, boolean isLimitedBuildingOrientation) {
        if (s != null) {
            deckList.loadSettings(s.maindeckSettings);
            sideboardList.loadSettings(s.sideboardSetings);
            dividerLocationLimited = s.dividerLocationLimited;
            dividerLocationNormal = s.dividerLocationNormal;
            if (isLimitedBuildingOrientation) {
                if (dividerLocationLimited != 0) {
                    deckAreaSplitPane.setDividerLocation(s.dividerLocationLimited);
                }
            } else if (dividerLocationNormal != 0) {
                deckAreaSplitPane.setDividerLocation(s.dividerLocationNormal);
            }
        }
    }

    public void cleanUp() {
        deckList.cleanUp();
        sideboardList.cleanUp();
    }

    public void changeGUISize() {
        setGUISize();
        deckList.changeGUISize();
        sideboardList.changeGUISize();
        deckAreaSplitPane.setDividerSize(GUISizeHelper.dividerBarSize);
    }

    private void setGUISize() {
    }

    public void setOrientation(boolean isLimitedBuildingOrientation) {
        if (isLimitedBuildingOrientation) {
            deckAreaSplitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
            if (dividerLocationLimited != 0) {
                deckAreaSplitPane.setDividerLocation(dividerLocationLimited);
            }
        } else {
            deckAreaSplitPane.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
            if (dividerLocationNormal != 0) {
                deckAreaSplitPane.setDividerLocation(dividerLocationNormal);
            }
        }
    }

    public void showSideboard(boolean show) {
        this.sideboardList.setVisible(show);
    }

    public void setDeckEditorMode(DeckEditorMode mode) {
        this.deckList.setDeckEditorMode(mode);
        this.sideboardList.setDeckEditorMode(mode);
    }

    private Set<Card> filterHidden(Set<Card> cards) {
        Set<Card> newSet = new LinkedHashSet<>();
        for (Card card : cards) {
            if (!hiddenCards.contains(card.getId())) {
                newSet.add(card);
            }
        }
        return newSet;
    }

    public void loadDeck(Deck deck, BigCard bigCard) {
        loadDeck(deck, false, bigCard);
    }

    public void loadDeck(Deck deck, boolean useLayout, BigCard bigCard) {
        lastDeck = deck;
        lastBigCard = bigCard;
        deckList.setCards(
                new CardsView(filterHidden(lastDeck.getCards())),
                useLayout ? deck.getCardsLayout() : null,
                lastBigCard);
        if (sideboardList.isVisible()) {
            sideboardList.setCards(
                    new CardsView(filterHidden(lastDeck.getSideboard())),
                    useLayout ? deck.getSideboardLayout() : null,
                    lastBigCard);
        }
    }

    public void addDeckEventListener(Listener<Event> listener) {
        deckList.addCardEventListener(listener);
        maindeckVirtualEvent.addListener(listener);
    }

    public void clearDeckEventListeners() {
        deckList.clearCardEventListeners();
    }

    public void addSideboardEventListener(Listener<Event> listener) {
        sideboardList.addCardEventListener(listener);
        sideboardVirtualEvent.addListener(listener);
    }

    public void clearSideboardEventListeners() {
        sideboardList.clearCardEventListeners();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        deckAreaSplitPane = new javax.swing.JSplitPane();
        deckList = new mage.client.cards.DragCardGrid();
        sideboardList = new mage.client.cards.DragCardGrid();

        setLayout(new java.awt.BorderLayout());

        deckAreaSplitPane.setBorder(null);
        deckAreaSplitPane.setDividerSize(10);
        deckAreaSplitPane.setResizeWeight(0.5);

        deckList.setMinimumSize(new java.awt.Dimension(200, 56));
        deckAreaSplitPane.setLeftComponent(deckList);

        sideboardList.setMinimumSize(new java.awt.Dimension(200, 56));
        deckAreaSplitPane.setRightComponent(sideboardList);

        add(deckAreaSplitPane, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    public DragCardGrid getDeckList() {
        return deckList;
    }

    public DragCardGrid getSideboardList() {
        return sideboardList;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JSplitPane deckAreaSplitPane;
    private mage.client.cards.DragCardGrid deckList;
    private mage.client.cards.DragCardGrid sideboardList;
    // End of variables declaration//GEN-END:variables

}
