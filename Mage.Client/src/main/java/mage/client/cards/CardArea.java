/*
 * Copyright 2012 BetaSteward_at_googlemail.com. All rights reserved.
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
package mage.client.cards;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;
import java.util.UUID;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import mage.cards.CardDimensions;
import mage.cards.MageCard;
import mage.client.plugins.impl.Plugins;
import mage.client.util.Config;
import mage.client.util.Event;
import mage.client.util.Listener;
import mage.view.AbilityView;
import mage.view.CardView;
import mage.view.CardsView;
import mage.view.SimpleCardView;
import org.mage.card.arcane.CardPanel;

public class CardArea extends JPanel implements MouseListener {

    protected CardEventSource cardEventSource = new CardEventSource();

    private boolean reloaded = false;
    private final javax.swing.JLayeredPane cardArea;
    private final javax.swing.JScrollPane scrollPane;
    private int yTextOffset;

    /**
     * Create the panel.
     */
    public CardArea() {
        setLayout(new BorderLayout(0, 0));

        scrollPane = new JScrollPane();
        add(scrollPane, BorderLayout.CENTER);

        cardArea = new JLayeredPane();
        scrollPane.setViewportView(cardArea);
        yTextOffset = 10;

    }

    public void cleanUp() {
        for (Component comp : cardArea.getComponents()) {
            if (comp instanceof CardPanel) {
                ((CardPanel) comp).cleanUp();
                cardArea.remove(comp);
            }
        }
    }

    public void loadCards(CardsView showCards, BigCard bigCard, CardDimensions dimension, UUID gameId) {
        this.reloaded = true;
        cardArea.removeAll();
        if (showCards != null && showCards.size() < 10) {
            yTextOffset = 10;
            loadCardsFew(showCards, bigCard, gameId);
        } else {
            yTextOffset = 0;
            loadCardsMany(showCards, bigCard, gameId, dimension);
        }
        cardArea.revalidate();

        this.revalidate();
        this.repaint();
    }

    public void loadCardsNarrow(CardsView showCards, BigCard bigCard, CardDimensions dimension, UUID gameId) {
        this.reloaded = true;
        cardArea.removeAll();
        yTextOffset = 0;
        loadCardsMany(showCards, bigCard, gameId, dimension);
        cardArea.revalidate();

        this.revalidate();
        this.repaint();
    }

    private void loadCardsFew(CardsView showCards, BigCard bigCard, UUID gameId) {
        Rectangle rectangle = new Rectangle(Config.dimensions.frameWidth, Config.dimensions.frameHeight);
        Dimension dimension = new Dimension(Config.dimensions.frameWidth, Config.dimensions.frameHeight);
        for (CardView card : showCards.values()) {
            addCard(card, bigCard, gameId, rectangle, dimension, Config.dimensions);
            rectangle.translate(Config.dimensions.frameWidth, 0);
        }
        cardArea.setPreferredSize(new Dimension(Config.dimensions.frameWidth * showCards.size(), Config.dimensions.frameHeight));
    }

    private void addCard(CardView card, BigCard bigCard, UUID gameId, Rectangle rectangle, Dimension dimension, CardDimensions cardDimensions) {
        if (card instanceof AbilityView) {
            CardView tmp = ((AbilityView) card).getSourceCard();
            tmp.overrideRules(card.getRules());
            tmp.setIsAbility(true);
            tmp.overrideTargets(card.getTargets());
            tmp.setAbility(card); // cross-reference, required for ability picker
            card = tmp;
        }
        MageCard cardPanel = Plugins.getInstance().getMageCard(card, bigCard, dimension, gameId, true);

        cardPanel.setBounds(rectangle);
        cardPanel.addMouseListener(this);
        cardArea.add(cardPanel);
        cardArea.moveToFront(cardPanel);
        cardPanel.update(card);
        cardPanel.setCardBounds(rectangle.x, rectangle.y, cardDimensions.frameWidth, cardDimensions.frameHeight);
        cardPanel.setTextOffset(yTextOffset);
        cardPanel.showCardTitle();
    }

    private void loadCardsMany(CardsView showCards, BigCard bigCard, UUID gameId, CardDimensions cardDimensions) {
        int columns = 1;
        if (showCards != null && showCards.size() > 0) {
            Rectangle rectangle = new Rectangle(cardDimensions.frameWidth, cardDimensions.frameHeight);
            Dimension dimension = new Dimension(cardDimensions.frameWidth, cardDimensions.frameHeight);
            int count = 0;
            for (CardView card : showCards.values()) {
                addCard(card, bigCard, gameId, rectangle, dimension, cardDimensions);
                if (count >= 20) {
                    rectangle.translate(cardDimensions.frameWidth, -400);
                    columns++;
                    count = 0;
                } else {
                    rectangle.translate(0, 20);
                    count++;
                }
            }
        }
        cardArea.setPreferredSize(new Dimension(cardDimensions.frameWidth * columns, cardDimensions.frameHeight + 400));
    }

    public boolean isReloaded() {
        return this.reloaded;
    }

    public void clearReloaded() {
        this.reloaded = false;
    }

    public void selectCards(List<UUID> selected) {
        for (Component component : cardArea.getComponents()) {
            if (component instanceof MageCard) {
                MageCard mageCard = (MageCard) component;
                if (selected.contains(mageCard.getOriginal().getId())) {
                    mageCard.setSelected(true);
                }
            }
        }
    }

    public void markCards(List<UUID> marked) {
        for (Component component : cardArea.getComponents()) {
            if (component instanceof MageCard) {
                MageCard mageCard = (MageCard) component;
                if (marked.contains(mageCard.getOriginal().getId())) {
                    mageCard.setChoosable(true);
                }
            }
        }
    }

    public void setPopupMenu(JPopupMenu popupMenu) {
        for (Component component : cardArea.getComponents()) {
            if (component instanceof MageCard) {
                MageCard mageCard = (MageCard) component;
                mageCard.setPopupMenu(popupMenu);
            }
        }
    }

    public void addCardEventListener(Listener<Event> listener) {
        cardEventSource.addListener(listener);
    }

    public void clearCardEventListeners() {
        cardEventSource.clearListeners();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getClickCount() >= 1 && !e.isConsumed()) {
            Object obj = e.getSource();
            if (e.getClickCount() == 2) {
                e.consume();
                if (obj instanceof Card) {
                    if (e.isAltDown()) {
                        cardEventSource.altDoubleClick(((Card) obj).getOriginal(), "alt-double-click");
                    } else {
                        cardEventSource.doubleClick(((Card) obj).getOriginal(), "double-click");
                    }
                } else if (obj instanceof MageCard) {
                    if (e.isAltDown()) {
                        cardEventSource.altDoubleClick(((MageCard) obj).getOriginal(), "alt-double-click");
                    } else {
                        cardEventSource.doubleClick(((MageCard) obj).getOriginal(), "double-click");
                    }
                }
            }
            if (obj instanceof MageCard) {
                checkMenu(e, ((MageCard) obj).getOriginal());
            } else {
                checkMenu(e, null);
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (!e.isConsumed()) {
            Object obj = e.getSource();
            if (obj instanceof MageCard) {
                checkMenu(e, ((MageCard) obj).getOriginal());
            } else {
                checkMenu(e, null);
            }
        } else {
            cardEventSource.actionConsumedEvent("action-consumed");
        }
    }

    private void checkMenu(MouseEvent Me, SimpleCardView card) {
        if (Me.isPopupTrigger()) {
            Me.consume();
            cardEventSource.showPopupMenuEvent(card, Me.getComponent(), Me.getX(), Me.getY(), "show-popup-menu");
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

}
