
 /*
 * DeckArea.java
 *
 * Created on Feb 18, 2010, 3:10:39 PM
 */
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
 *
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
    private static final boolean isLimitedBuildingOrientation = false;

    public DeckCardLayout getCardLayout() {
        return dragCardGrid1.getCardLayout();
    }

    public DeckCardLayout getSideboardLayout() {
        return dragCardGrid2.getCardLayout();
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
        //dragCardGrid1.setOpaque(false);
        //dragCardGrid2.setOpaque(false);
        dragCardGrid1.setRole(DragCardGrid.Role.MAINDECK);
        dragCardGrid2.setRole(DragCardGrid.Role.SIDEBOARD);

        // When a selection happens in one pane, deselect the selection in the other
        dragCardGrid1.addDragCardGridListener(new DragCardGrid.DragCardGridListener() {
            @Override
            public void cardsSelected() {
                dragCardGrid2.deselectAll();
            }

            @Override
            public void hideCards(Collection<CardView> cards) {
                // Add to hidden and move to sideboard
                for (CardView card : cards) {
                    hiddenCards.add(card.getId());
                    maindeckVirtualEvent.fireEvent(card, ClientEventType.REMOVE_SPECIFIC_CARD);
                    sideboardVirtualEvent.fireEvent(card, ClientEventType.ADD_SPECIFIC_CARD);
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
                dragCardGrid2.deselectAll();
                for (CardView card : cards) {
                    CardView newCard = new CardView(card);
                    dragCardGrid1.addCardView(newCard, true);
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
        dragCardGrid2.addDragCardGridListener(new DragCardGrid.DragCardGridListener() {
            @Override
            public void cardsSelected() {
                dragCardGrid1.deselectAll();
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
                dragCardGrid1.deselectAll();
                for (CardView card : cards) {
                    CardView newCard = new CardView(card);
                    dragCardGrid2.addCardView(newCard, true);
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

    public Settings saveSettings() {
        Settings settings = new Settings();
        settings.maindeckSettings = dragCardGrid1.saveSettings();
        settings.sideboardSetings = dragCardGrid2.saveSettings();
        if (isLimitedBuildingOrientation) {
            dividerLocationLimited = deckAreaSplitPane.getDividerLocation();
        } else {
            dividerLocationNormal = deckAreaSplitPane.getDividerLocation();
        }
        settings.dividerLocationLimited = dividerLocationLimited;
        settings.dividerLocationNormal = dividerLocationNormal;
        return settings;
    }

    public void loadSettings(Settings s) {
        if (s != null) {
            dragCardGrid1.loadSettings(s.maindeckSettings);
            dragCardGrid2.loadSettings(s.sideboardSetings);
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
        dragCardGrid1.cleanUp();
        dragCardGrid2.cleanUp();
    }

    public void changeGUISize() {
        setGUISize();
        dragCardGrid1.changeGUISize();
        dragCardGrid2.changeGUISize();
        deckAreaSplitPane.setDividerSize(GUISizeHelper.dividerBarSize);
    }

    private void setGUISize() {
    }

    public void setOrientation(boolean limitedBuildingOrientation) {
        if (limitedBuildingOrientation) {
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
        this.dragCardGrid2.setVisible(show);
    }

    public void setDeckEditorMode(DeckEditorMode mode) {
        this.dragCardGrid1.setDeckEditorMode(mode);
        this.dragCardGrid2.setDeckEditorMode(mode);
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
        dragCardGrid1.setCards(
                new CardsView(filterHidden(lastDeck.getCards())),
                useLayout ? deck.getCardsLayout() : null,
                lastBigCard);
        if (dragCardGrid2.isVisible()) {
            dragCardGrid2.setCards(
                    new CardsView(filterHidden(lastDeck.getSideboard())),
                    useLayout ? deck.getSideboardLayout() : null,
                    lastBigCard);
        }
    }

    public void addDeckEventListener(Listener<Event> listener) {
        dragCardGrid1.addCardEventListener(listener);
        maindeckVirtualEvent.addListener(listener);
    }

    public void clearDeckEventListeners() {
        dragCardGrid1.clearCardEventListeners();
    }

    public void addSideboardEventListener(Listener<Event> listener) {
        dragCardGrid2.addCardEventListener(listener);
        sideboardVirtualEvent.addListener(listener);
    }

    public void clearSideboardEventListeners() {
        dragCardGrid2.clearCardEventListeners();
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
        dragCardGrid1 = new mage.client.cards.DragCardGrid();
        dragCardGrid2 = new mage.client.cards.DragCardGrid();

        deckAreaSplitPane.setBorder(null);
        deckAreaSplitPane.setResizeWeight(0.6);
        deckAreaSplitPane.setLeftComponent(dragCardGrid1);
        deckAreaSplitPane.setRightComponent(dragCardGrid2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(deckAreaSplitPane, javax.swing.GroupLayout.PREFERRED_SIZE, 918, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(deckAreaSplitPane, javax.swing.GroupLayout.DEFAULT_SIZE, 377, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    public DragCardGrid getDeckList() {
        return dragCardGrid1;
    }

    public DragCardGrid getSideboardList() {
        return dragCardGrid2;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JSplitPane deckAreaSplitPane;
    private mage.client.cards.DragCardGrid dragCardGrid1;
    private mage.client.cards.DragCardGrid dragCardGrid2;
    // End of variables declaration//GEN-END:variables

}
