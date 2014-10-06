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

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
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
import mage.view.CardView;
import mage.view.CardsView;
import org.jdesktop.swingx.JXPanel;
import org.jdesktop.swingx.painter.MattePainter;
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
    private List<List<UUID>> cardsPosition = new ArrayList<>();
    private List<UUID> selectedCards = new ArrayList<>();
    private Dimension cardDimension;
    
    private JXPanel selectionRectangle;

    private int selectionAreaX;
    private int selectionAreaY;

    /**
     * Max amount of cards in card grid for which card images will be drawn.
     * Done so to solve issue with memory for big piles of cards.
     */
    public static final int MAX_IMAGES = 350;

    public CardGrid() {
        initComponents();
        setOpaque(false);
        this.addMouseListener(this);
        
        //Create selection Rectangle
        selectionRectangle = new JXPanel();
        MattePainter p = new MattePainter(Color.BLUE);
        selectionRectangle.setBackgroundPainter(p);
        selectionRectangle.setBorder(javax.swing.BorderFactory.createLineBorder(Color.BLUE, 3));
        this.add(selectionRectangle);
        selectionRectangle.setAlpha(0.6f);
        selectionRectangle.setOpaque(false);
    }

    public void clear() {
        for(MouseListener ml: this.getMouseListeners()) {
            this.removeMouseListener(ml);
        }
        this.clearCardEventListeners();
        this.clearCards();
        this.bigCard = null;
    }
    
    public List<UUID> getSelectedCards() {
        return selectedCards;
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
            for (CardView card: showCards.values()) {
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
            for (CardView card: showCards.values()) {
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
        cardImg.addMouseMotionListener(new MouseMotionAdapter() {

            @Override
            public void mouseDragged(MouseEvent e) {
                dragCard(e);
            }
            
        });
        add(cardImg);
        cardImg.update(card);
        cards.put(card.getId(), cardImg);
        if(cardsPosition.size() == 0){
            cardsPosition.add(new ArrayList<UUID>());
        }
        cardsPosition.get(0).add(card.getId());
    }

    @Override
    public void drawCards(SortSetting sortSetting) {
        //Sort Cards
        sortCards(sortSetting);
        
        drawCards();
    }
    
    public void drawCards(){
        int curColumn = 0;
        int curRow = 0;
        Rectangle rectangle = new Rectangle(Config.dimensions.frameWidth, Config.dimensions.frameHeight);
        //Draw cars
        for(List<UUID> list : cardsPosition){
            for(UUID card : list){
                MageCard cardImg = cards.get(card);
                if(cardImg != null)
                {
                    rectangle.setLocation(curColumn * Config.dimensions.frameWidth, curRow * 20);
                    cardImg.setBounds(rectangle);
                    cardImg.setCardBounds(rectangle.x, rectangle.y, Config.dimensions.frameWidth, Config.dimensions.frameHeight);
                    moveToFront(cardImg);
                    curRow++;
                }
                
            }
            curColumn++;
            curRow = 0;
        }
            
        resizeArea();
        revalidate();
        repaint();
    }
    
    private void sortCards(SortSetting sortSetting)
    {
        cardsPosition.clear();
        if (cards.size() > 0) {
            //Choose comparator
            Comparator<MageCard> comparator;
            switch (sortSetting.getSortBy()) {
                case NAME:
                    comparator = new CardNameComparator();
                    break;
                case RARITY:
                    comparator = new CardRarityComparator();
                    break;
                case COLOR:
                    comparator = new CardColorComparator();
                    break;
                case COLOR_DETAILED:
                    comparator = new CardColorDetailedComparator();
                    break;
                case CASTING_COST:
                    comparator = new CardCostComparator();
                    break;
                case UNSORTED:
                    comparator = new UnsortdComparator();
                    break;
                default:
                    comparator = new CardNameComparator();
                    break;
            }
            //Sort cards
            List<MageCard> sortedCards = new ArrayList<>(cards.values());
            Collections.sort(sortedCards, new CardNameComparator());
            Collections.sort(sortedCards, comparator);
            //Put cards in piles
            if (sortSetting.isPilesToggle()) {
                MageCard lastCard = null;
                ArrayList<UUID> list = new ArrayList<>();
                for (MageCard cardImg: sortedCards) {
                    if(lastCard == null || comparator.compare(lastCard, cardImg) < 0){
                        list = new ArrayList<>();
                        cardsPosition.add(list);
                    }
                    lastCard = cardImg;
                    list.add(cardImg.getOriginal().getId());
                }
            }
            else
            {
                int maxWidth = this.getParent().getWidth();
                int numColumns = maxWidth / Config.dimensions.frameWidth;
                for (int i = 0; i < sortedCards.size(); i++) {
                    if(cardsPosition.size() <= i % numColumns)
                    {
                        cardsPosition.add(new ArrayList<UUID>());
                    } 
                    cardsPosition.get(i % numColumns).add(sortedCards.get(i).getOriginal().getId());
                }
            }
        }
    }

    private void clearCards() {
        // remove possible mouse listeners, preventing gc
        for (MageCard mageCard: cards.values()) {
            if (mageCard instanceof CardPanel) {
                ((CardPanel)mageCard).cleanUp();
            }
        }
        this.cards.clear();
        removeAllCardImg();
    }

    private void removeAllCardImg() {
        for (Component comp: getComponents()) {
            if (comp instanceof Card || comp instanceof MageCard) {
                remove(comp);
            }
        }
    }

    private void removeCardImg(UUID cardId) {
        for (Component comp: getComponents()) {
            if (comp instanceof Card) {
                if (((Card)comp).getCardId().equals(cardId)) {
                    remove(comp);
                    comp = null;
                }
            } else if (comp instanceof MageCard) {
                if (((MageCard)comp).getOriginal().getId().equals(cardId)) {
                    remove(comp);
                    comp = null;
                }
            }
        }
        for(List<UUID> list : cardsPosition){
            if(list.contains(cardId)){
                list.remove(cardId);
            }
        }
    }

    public void removeCard(UUID cardId) {
        removeCardImg(cardId);
        cards.remove(cardId);
        selectedCards.remove(cardId);
    }
    
    public void deselectAllCards(){
        for(UUID uuid : selectedCards){
            MageCard unselectCard = cards.get(uuid);
            if(unselectCard != null){
                unselectCard.setSelected(false);
            }
        }
        selectedCards.clear();
    }

    @Override
    public void addCardEventListener(Listener<Event> listener) {
        cardEventSource.addListener(listener);
    }

    @Override
    public void clearCardEventListeners() {
        cardEventSource.clearListeners();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                formMouseDragged(evt);
            }
        });

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

    private void formMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseDragged
        int x = (int) Math.min(evt.getX(), selectionAreaX);
        int y = (int) Math.min(evt.getY(), selectionAreaY);
        int w = (int)Math.abs(evt.getX() - selectionAreaX);
        int h = (int)Math.abs(evt.getY() - selectionAreaY);
       
        //cardArea.moveToFront(selectionArea);
        selectionRectangle.setBounds(x, y, w, h);
        
        for(Component c : getComponents())
        {
            if(c instanceof MageCard){
                ((MageCard)c).setSelected(false);
            }
        }
        int minColunm = x / (Config.dimensions.frameWidth);
        int maxColunm = (x + w) /(Config.dimensions.frameWidth);
        
        int minRow = y/20;
        int maxRow = (y+h)/20;
        
        deselectAllCards();
        
        for(int column = 0; column < cardsPosition.size(); column++)
        {
            if(column >= minColunm && column <= maxColunm){
                List<UUID> list = cardsPosition.get(column);
                for(int row = 0; row < list.size() -1; row++){
                    if(row >= minRow && row <= maxRow){
                        cards.get(list.get(row)).setSelected(true);
                        selectedCards.add(list.get(row));
                    }
                }
                if(list.size() > 0){
                   int row =  list.size() -1;
                   if(row*20 + Config.dimensions.frameHeight >= y && row*20 <= y+h){
                        cards.get(list.get(row)).setSelected(true);
                        selectedCards.add(list.get(row));
                   }
                }
            }
        }
        repaint();
    }//GEN-LAST:event_formMouseDragged


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables

    public void dragCard(MouseEvent e){
        MageCard card = (MageCard)e.getSource();
        UUID cardID = card.getOriginal().getId();
        if(!selectedCards.contains(cardID))
        {
            if(!e.isControlDown()){
                deselectAllCards();
            }
            selectedCards.add(cardID);
            card.setSelected(true);
        }
        //Move selected card in front
        if(selectedCards.get(0) != cardID){
            selectedCards.remove(cardID);
            selectedCards.add(0,cardID);
        }
        e.translatePoint(e.getComponent().getLocation().x, e.getComponent().getLocation().y);
        int culomn = e.getX()/Config.dimensions.frameWidth;
        int row = e.getY()/20;
        //Remove cards
        for(List<UUID> l : cardsPosition){
            l.removeAll(selectedCards);
        }
        
        //Select column
        List<UUID> selectedColumn = null;
        if(culomn < 0){
            selectedColumn = cardsPosition.get(0);
        }
        else if (culomn >= cardsPosition.size()){
            selectedColumn = new ArrayList<>();
            cardsPosition.add(selectedColumn);
        }
        else{
            selectedColumn = cardsPosition.get(culomn);
        }
        //Select row
        row = Math.min(row, selectedColumn.size());
        row = Math.max(row, 0);
        //Add selected cards
        for(UUID id : selectedCards){
            selectedColumn.add(row, id);
            row++;
        }
        //Draw cards
        drawCards();

        //Move selected cards for drag effect
        e.translatePoint(-Config.dimensions.frameWidth/2, -10);
        for(UUID id : selectedCards){
            MageCard selectedCard = cards.get(id);
            if(selectedCard != null){
                selectedCard.setCardBounds(e.getX(), e.getY(),Config.dimensions.frameWidth, Config.dimensions.frameHeight);
                e.translatePoint(0, 20);
            }
        }
    }
    
    @Override
    public void mouseClicked(MouseEvent e) {
        if(e.getClickCount() == 1 && !e.isConsumed()){
            if (e.getSource() instanceof MageCard){
                MageCard card = (MageCard)e.getSource();
                if(!e.isControlDown()){
                    //Remove selected cards
                    deselectAllCards();
                    card.setSelected(true);
                    selectedCards.add(card.getOriginal().getId());
                }
                else{
                    //Select card
                    if(!selectedCards.contains(card.getOriginal().getId())){
                        card.setSelected(true);
                        selectedCards.add(card.getOriginal().getId());
                    }
                    //Deselect card
                    else{
                        card.setSelected(false);
                        selectedCards.remove(card.getOriginal().getId());
                    }
                }
            }
            revalidate();
            repaint();
        }
        else if (e.getClickCount() == 2 && !e.isConsumed()) {
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
        if(e.getSource() == this){
            deselectAllCards();
            selectionRectangle.setVisible(true);
            selectionAreaX = e.getX();
            selectionAreaY = e.getY();
            selectionRectangle.setBounds(e.getX(), e.getY(), 0, 0);
            moveToFront(selectionRectangle);
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if(e.getSource() == this){
            selectionRectangle.setVisible(false);
        }
        drawCards();
    }

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}

    private void resizeArea() {
        Dimension area = new Dimension(0, 0);
        Dimension size = getPreferredSize();

        for (Component comp: getComponents()) {
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
        return o1.getOriginal().getRarity().compareTo(o2.getOriginal().getRarity());
        
    }

}

class CardCostComparator implements Comparator<MageCard> {

    @Override
    public int compare(MageCard o1, MageCard o2) {
        return Integer.valueOf(o1.getOriginal().getConvertedManaCost()).compareTo(Integer.valueOf(o2.getOriginal().getConvertedManaCost()));
    }

}

class CardColorComparator implements Comparator<MageCard> {

    @Override
    public int compare(MageCard o1, MageCard o2) {
        return o1.getOriginal().getColor().compareTo(o2.getOriginal().getColor());
    }

}

class CardColorDetailedComparator implements Comparator<MageCard> {

    @Override
    public int compare(MageCard o1, MageCard o2) {
        return o1.getOriginal().getColor().hashCode() - o2.getOriginal().getColor().hashCode();
    }
}
    
class UnsortdComparator implements Comparator<MageCard> {

    @Override
    public int compare(MageCard o1, MageCard o2) {
        return 0;
    }

}
