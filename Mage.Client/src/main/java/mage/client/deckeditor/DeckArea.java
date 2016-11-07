/*
* Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
*
* Redistribution and use in source and binary forms, with or without modification, are
* permitted provided that the following conditions are met:
*
*    1. Redistributions of source code must retain the above copyright notice, this list of
*       conditions and the following disclaimer.
*
*    2. Redistributions in binary form must reproduce the above copyright notice, this list
*       of conditions and the following disclaimer in the documentation and/or other materials
*       provided with the distribution.
*
* THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
* WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
* FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
* CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
* CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
* SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
* ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
* NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
* ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*
* The views and conclusions contained in the software and documentation are those of the
* authors and should not be interpreted as representing official policies, either expressed
* or implied, of BetaSteward_at_googlemail.com.
 */
 /*
 * DeckArea.java
 *
 * Created on Feb 18, 2010, 3:10:39 PM
 */
package mage.client.deckeditor;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.*;
import mage.cards.Card;
import mage.cards.decks.Deck;
import mage.cards.decks.DeckCardLayout;
import mage.client.cards.BigCard;
import mage.client.cards.CardEventSource;
import mage.client.cards.DragCardGrid;
import mage.client.constants.Constants.DeckEditorMode;
import mage.client.util.Event;
import mage.client.util.GUISizeHelper;
import mage.client.util.Listener;
import mage.view.CardView;
import mage.view.CardsView;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class DeckArea extends javax.swing.JPanel {

    private CardEventSource maindeckVirtualEvent = new CardEventSource();
    private CardEventSource sideboardVirtualEvent = new CardEventSource();
    private Set<UUID> hiddenCards = new HashSet<>();
    private Deck lastDeck = new Deck();
    private BigCard lastBigCard = null;
    private int dividerLocationNormal = 0;
    private int dividerLocationLimited = 0;
    private final boolean isLimitedBuildingOrientation = false;

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

        private final static Pattern parser = Pattern.compile("([^|]*)\\|([^|]*)\\|([^|]*)\\|([^|]*)");

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
            return maindeckSettings.toString() + "|" + sideboardSetings.toString() + "|" + dividerLocationNormal + "|" + dividerLocationLimited;
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
                    maindeckVirtualEvent.removeSpecificCard(card, "remove-specific-card");
                    sideboardVirtualEvent.addSpecificCard(card, "add-specific-card");
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
        });
    }

    public Settings saveSettings() {
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

    public void loadSettings(Settings s) {
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

        deckAreaSplitPane.setBorder(null);
        deckAreaSplitPane.setResizeWeight(0.6);
        deckAreaSplitPane.setLeftComponent(deckList);
        deckAreaSplitPane.setRightComponent(sideboardList);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(deckAreaSplitPane, javax.swing.GroupLayout.DEFAULT_SIZE, 918, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(deckAreaSplitPane, javax.swing.GroupLayout.DEFAULT_SIZE, 377, Short.MAX_VALUE)
        );
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
