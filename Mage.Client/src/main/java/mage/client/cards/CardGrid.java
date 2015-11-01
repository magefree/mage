/*
 *  Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without modification, are
 *  permitted provided that the following conditions are met:
 *
 *     1. Redistributions of source code must retain the above copyright notice, this list of
 *        conditions and the following disclaimer.
 *
 *     2. Redistributions in binary form must reproduce the above copyright notice, this list
 *        of conditions and the following disclaimer in the documentation and/or other materials
 *        provided with the distribution.
 *
 *  THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
 *  WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 *  FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
 *  CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 *  CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 *  SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 *  ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 *  NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 *  ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 *  The views and conclusions contained in the software and documentation are those of the
 *  authors and should not be interpreted as representing official policies, either expressed
 *  or implied, of BetaSteward_at_googlemail.com.
 */

/*
 * CardGrid.java
 *
 * Created on 30-Mar-2010, 9:25:40 PM
 */
package mage.client.cards;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;
import mage.cards.MageCard;
import mage.client.deckeditor.SortSetting;
import mage.client.plugins.impl.Plugins;
import mage.client.util.Config;
import mage.client.util.Event;
import mage.client.util.Listener;
import mage.utils.CardUtil;
import mage.view.CardView;
import mage.view.CardsView;
import org.mage.card.arcane.CardPanel;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class CardGrid extends javax.swing.JLayeredPane implements MouseListener, ICardGrid {

    protected CardEventSource cardEventSource = new CardEventSource();
    protected BigCard bigCard;
    protected UUID gameId;
    private final Map<UUID, MageCard> cards = new HashMap<>();
    private Dimension cardDimension;

    /**
     * Max amount of cards in card grid for which card images will be drawn.
     * Done so to solve issue with memory for big piles of cards.
     */
    public static final int MAX_IMAGES = 350;

    public CardGrid() {
        initComponents();
        setOpaque(false);
    }

    public void clear() {
        for (MouseListener ml : this.getMouseListeners()) {
            this.removeMouseListener(ml);
        }
        this.clearCardEventListeners();
        this.clearCards();
        this.bigCard = null;
    }

    @Override
    public void loadCards(CardsView showCards, SortSetting sortSetting, BigCard bigCard, UUID gameId) {
        this.loadCards(showCards, sortSetting, bigCard, gameId, true);
    }

    @Override
    public void loadCards(CardsView showCards, SortSetting sortSetting, BigCard bigCard, UUID gameId, boolean merge) {
        boolean drawImage = showCards.size() <= MAX_IMAGES;
        this.bigCard = bigCard;
        this.gameId = gameId;
        if (merge) {
            for (CardView card : showCards.values()) {
                if (!cards.containsKey(card.getId())) {
                    addCard(card, bigCard, gameId, drawImage);
                }
            }
            for (Iterator<Entry<UUID, MageCard>> i = cards.entrySet().iterator(); i.hasNext();) {
                Entry<UUID, MageCard> entry = i.next();
                if (!showCards.containsKey(entry.getKey())) {
                    removeCardImg(entry.getKey());
                    i.remove();
                }
            }
        } else {
            this.clearCards();
            for (CardView card : showCards.values()) {
                addCard(card, bigCard, gameId, drawImage);
            }
        }
        // System.gc();
        drawCards(sortSetting);
        this.setVisible(true);
    }

    private void addCard(CardView card, BigCard bigCard, UUID gameId, boolean drawImage) {
        if (cardDimension == null) {
            cardDimension = new Dimension(Config.dimensions.frameWidth, Config.dimensions.frameHeight);
        }
        MageCard cardImg = Plugins.getInstance().getMageCard(card, bigCard, cardDimension, gameId, drawImage);
        cards.put(card.getId(), cardImg);
        cardImg.addMouseListener(this);
        add(cardImg);
        cardImg.update(card);
        cards.put(card.getId(), cardImg);
    }

    @Override
    public void drawCards(SortSetting sortSetting) {
        int maxWidth = this.getParent().getWidth();
        int numColumns = maxWidth / Config.dimensions.frameWidth;
        int curColumn = 0;
        int curRow = 0;
        if (cards.size() > 0) {
            Rectangle rectangle = new Rectangle(Config.dimensions.frameWidth, Config.dimensions.frameHeight);
            List<MageCard> sortedCards = new ArrayList<>(cards.values());
            switch (sortSetting.getSortBy()) {
                case NAME:
                    Collections.sort(sortedCards, new CardNameComparator());
                    break;
                case RARITY:
                    Collections.sort(sortedCards, new CardRarityComparator());
                    break;
                case COLOR:
                    Collections.sort(sortedCards, new CardColorComparator());
                    break;
                case COLOR_IDENTITY:
                    Collections.sort(sortedCards, new CardColorDetailedIdentity());
                    break;
                case CASTING_COST:
                    Collections.sort(sortedCards, new CardCostComparator());
                    break;

            }
            MageCard lastCard = null;
            for (MageCard cardImg : sortedCards) {
                if (sortSetting.isPilesToggle()) {
                    if (lastCard == null) {
                        lastCard = cardImg;
                    }
                    switch (sortSetting.getSortBy()) {
                        case NAME:
                            if (!cardImg.getOriginal().getName().equals(lastCard.getOriginal().getName())) {
                                curColumn++;
                                curRow = 0;
                            }
                            break;
                        case RARITY:
                            if (!cardImg.getOriginal().getRarity().equals(lastCard.getOriginal().getRarity())) {
                                curColumn++;
                                curRow = 0;
                            }
                            break;
                        case COLOR:
                            if (cardImg.getOriginal().getColor().compareTo(lastCard.getOriginal().getColor()) != 0) {
                                curColumn++;
                                curRow = 0;
                            }
                            break;
                        case COLOR_IDENTITY:
                            if (CardUtil.getColorIdentitySortValue(cardImg.getOriginal().getManaCost(), cardImg.getOriginal().getColor(), cardImg.getOriginal().getRules())
                                    != CardUtil.getColorIdentitySortValue(lastCard.getOriginal().getManaCost(), lastCard.getOriginal().getColor(), lastCard.getOriginal().getRules())) {
                                curColumn++;
                                curRow = 0;
                            }
                            break;
                        case CASTING_COST:
                            if (cardImg.getOriginal().getConvertedManaCost() != lastCard.getOriginal().getConvertedManaCost()) {
                                curColumn++;
                                curRow = 0;
                            }
                            break;
                    }
                    rectangle.setLocation(curColumn * Config.dimensions.frameWidth, curRow * 20);
                    cardImg.setBounds(rectangle);
                    cardImg.setCardBounds(rectangle.x, rectangle.y, Config.dimensions.frameWidth, Config.dimensions.frameHeight);
                    moveToFront(cardImg);
                    curRow++;
                    lastCard = cardImg;
                } else {
                    rectangle.setLocation(curColumn * Config.dimensions.frameWidth, curRow * 20);
                    cardImg.setBounds(rectangle);
                    cardImg.setCardBounds(rectangle.x, rectangle.y, Config.dimensions.frameWidth, Config.dimensions.frameHeight);
                    moveToFront(cardImg);
                    curColumn++;
                    if (curColumn == numColumns) {
                        curColumn = 0;
                        curRow++;
                    }
                }
            }
        }
        resizeArea();
        revalidate();
        repaint();
    }

    private void clearCards() {
        // remove possible mouse listeners, preventing gc
        for (MageCard mageCard : cards.values()) {
            if (mageCard instanceof CardPanel) {
                ((CardPanel) mageCard).cleanUp();
            }
        }
        this.cards.clear();
        removeAllCardImg();
    }

    private void removeAllCardImg() {
        for (Component comp : getComponents()) {
            if (comp instanceof Card || comp instanceof MageCard) {
                remove(comp);
            }
        }
    }

    private void removeCardImg(UUID cardId) {
        for (Component comp : getComponents()) {
            if (comp instanceof Card) {
                if (((Card) comp).getCardId().equals(cardId)) {
                    remove(comp);
                    comp = null;
                }
            } else if (comp instanceof MageCard) {
                if (((MageCard) comp).getOriginal().getId().equals(cardId)) {
                    remove(comp);
                    comp = null;
                }
            }
        }
    }

    public void removeCard(UUID cardId) {
        removeCardImg(cardId);
        cards.remove(cardId);
    }

    @Override
    public void addCardEventListener(Listener<Event> listener) {
        cardEventSource.addListener(listener);
    }

    @Override
    public void clearCardEventListeners() {
        cardEventSource.clearListeners();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 294, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 197, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getClickCount() == 2 && !e.isConsumed()) {
            e.consume();
            Object obj = e.getSource();
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
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    private void resizeArea() {
        Dimension area = new Dimension(0, 0);
        Dimension size = getPreferredSize();

        for (Component comp : getComponents()) {
            Rectangle r = comp.getBounds();
            if (r.x + r.width > area.width) {
                area.width = r.x + r.width;
            }
            if (r.y + r.height > area.height) {
                area.height = r.y + r.height;
            }
        }
        if (size.height != area.height || size.width != area.width) {
            setPreferredSize(area);
        }
    }

    @Override
    public void refresh() {
        revalidate();
        repaint();
    }

    @Override
    public int cardsSize() {
        return cards.size();
    }
}

class CardNameComparator implements Comparator<MageCard> {

    @Override
    public int compare(MageCard o1, MageCard o2) {
        return o1.getOriginal().getName().compareTo(o2.getOriginal().getName());
    }

}

class CardRarityComparator implements Comparator<MageCard> {

    @Override
    public int compare(MageCard o1, MageCard o2) {
        int val = o1.getOriginal().getRarity().compareTo(o2.getOriginal().getRarity());
        if (val == 0) {
            return o1.getOriginal().getName().compareTo(o2.getOriginal().getName());
        } else {
            return val;
        }
    }

}

class CardCostComparator implements Comparator<MageCard> {

    @Override
    public int compare(MageCard o1, MageCard o2) {
        int val = Integer.valueOf(o1.getOriginal().getConvertedManaCost()).compareTo(Integer.valueOf(o2.getOriginal().getConvertedManaCost()));
        if (val == 0) {
            return o1.getOriginal().getName().compareTo(o2.getOriginal().getName());
        } else {
            return val;
        }
    }

}

class CardColorComparator implements Comparator<MageCard> {

    @Override
    public int compare(MageCard o1, MageCard o2) {
        int val = o1.getOriginal().getColor().compareTo(o2.getOriginal().getColor());
        if (val == 0) {
            return o1.getOriginal().getName().compareTo(o2.getOriginal().getName());
        } else {
            return val;
        }
    }

}

class CardColorDetailedIdentity implements Comparator<MageCard> {

    @Override
    public int compare(MageCard o1, MageCard o2) {
        int val = CardUtil.getColorIdentitySortValue(o1.getOriginal().getManaCost(), o1.getOriginal().getColor(), o1.getOriginal().getRules())
                - CardUtil.getColorIdentitySortValue(o2.getOriginal().getManaCost(), o2.getOriginal().getColor(), o2.getOriginal().getRules());
        if (val == 0) {
            return o1.getOriginal().getName().compareTo(o2.getOriginal().getName());
        } else {
            return val;
        }
    }

}
