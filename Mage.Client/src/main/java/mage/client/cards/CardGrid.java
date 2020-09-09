

 /*
  * CardGrid.java
  *
  * Created on 30-Mar-2010, 9:25:40 PM
  */
 package mage.client.cards;

 import mage.cards.MageCard;
 import mage.client.deckeditor.SortSetting;
 import mage.client.dialog.PreferencesDialog;
 import mage.client.plugins.impl.Plugins;
 import mage.client.util.ClientEventType;
 import mage.client.util.Event;
 import mage.client.util.GUISizeHelper;
 import mage.client.util.Listener;
 import mage.constants.Rarity;
 import mage.utils.CardColorUtil;
 import mage.view.CardView;
 import mage.view.CardsView;
 import org.mage.card.arcane.CardPanel;

 import java.awt.*;
 import java.awt.event.MouseEvent;
 import java.awt.event.MouseListener;
 import java.util.List;
 import java.util.*;
 import java.util.Map.Entry;

 /**
  * @author BetaSteward_at_googlemail.com
  */
 public class CardGrid extends javax.swing.JLayeredPane implements MouseListener, ICardGrid {

     protected final CardEventSource cardEventSource = new CardEventSource();
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
         setGUISize();
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

     public void changeGUISize() {
         setGUISize();
     }

     private void setGUISize() {
         cardDimension = GUISizeHelper.editorCardDimension;
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
             for (Iterator<Entry<UUID, MageCard>> i = cards.entrySet().iterator(); i.hasNext(); ) {
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
         drawCards(sortSetting);
         this.setVisible(true);
     }

     private void addCard(CardView card, BigCard bigCard, UUID gameId, boolean drawImage) {
         MageCard cardImg = Plugins.instance.getMageCard(card, bigCard, cardDimension, gameId, drawImage, true, PreferencesDialog.getRenderMode(), true);
         cards.put(card.getId(), cardImg);
         cardImg.addMouseListener(this);
         add(cardImg);
         cardImg.update(card);
         cards.put(card.getId(), cardImg);
     }

     @Override
     public void drawCards(SortSetting sortSetting) {
         int maxWidth = this.getParent().getWidth();
         int cardVerticalOffset = GUISizeHelper.editorCardOffsetSize;
         int numColumns = maxWidth / cardDimension.width;
         int curColumn = 0;
         int curRow = 0;
         if (!cards.isEmpty()) {
             Rectangle rectangle = new Rectangle(cardDimension.width, cardDimension.height);
             List<MageCard> sortedCards = new ArrayList<>(cards.values());
             switch (sortSetting.getSortBy()) {
                 case NAME:
                     sortedCards.sort(new CardNameComparator());
                     break;
                 case CARD_TYPE:
                     sortedCards.sort(new CardTypeComparator());
                     break;
                 case RARITY:
                     sortedCards.sort(new CardRarityComparator());
                     break;
                 case COLOR:
                     sortedCards.sort(new CardColorComparator());
                     break;
                 case COLOR_IDENTITY:
                     sortedCards.sort(new CardColorDetailedIdentity());
                     break;
                 case CASTING_COST:
                     sortedCards.sort(new CardCostComparator());
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
                         case CARD_TYPE:
                             if (!cardImg.getOriginal().getCardTypes().equals(lastCard.getOriginal().getCardTypes())) {
                                 curColumn++;
                                 curRow = 0;
                             }
                             break;
                         case RARITY:
                             if (cardImg.getOriginal().getRarity() != lastCard.getOriginal().getRarity()) {
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
                             if (CardColorUtil.getColorIdentitySortValue(cardImg.getOriginal().getManaCost(), cardImg.getOriginal().getColor(), cardImg.getOriginal().getRules())
                                     != CardColorUtil.getColorIdentitySortValue(lastCard.getOriginal().getManaCost(), lastCard.getOriginal().getColor(), lastCard.getOriginal().getRules())) {
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
                     rectangle.setLocation(curColumn * cardDimension.width, curRow * cardVerticalOffset);
                     cardImg.setBounds(rectangle);
                     cardImg.setCardBounds(rectangle.x, rectangle.y, cardDimension.width, cardDimension.height);
                     moveToFront(cardImg);
                     curRow++;
                     lastCard = cardImg;
                 } else {
                     rectangle.setLocation(curColumn * cardDimension.width, curRow * cardVerticalOffset);
                     cardImg.setBounds(rectangle);
                     cardImg.setCardBounds(rectangle.x, rectangle.y, cardDimension.width, cardDimension.height);
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
         if ((e.getClickCount() & 1) == 0 && (e.getClickCount() > 0) && !e.isConsumed()) { // double clicks and repeated double clicks
             e.consume();
             Object obj = e.getSource();
             if (obj instanceof Card) {
                 if (e.isAltDown()) {
                     cardEventSource.fireEvent(((Card) obj).getOriginal(), ClientEventType.ALT_DOUBLE_CLICK);
                 } else {
                     cardEventSource.fireEvent(((Card) obj).getOriginal(), ClientEventType.DOUBLE_CLICK);
                 }
             } else if (obj instanceof MageCard) {
                 if (e.isAltDown()) {
                     cardEventSource.fireEvent(((MageCard) obj).getOriginal(), ClientEventType.ALT_DOUBLE_CLICK);
                 } else {
                     cardEventSource.fireEvent(((MageCard) obj).getOriginal(), ClientEventType.DOUBLE_CLICK);
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
         Rarity r1 = o1.getOriginal().getRarity();
         Rarity r2 = o2.getOriginal().getRarity();

         int val = Integer.compare(
                 r1 == null ? 0 : r1.getSorting(),
                 r2 == null ? 0 : r2.getSorting()
         );

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
         int val = Integer.compare(o1.getOriginal().getConvertedManaCost(), o2.getOriginal().getConvertedManaCost());
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
         int val = CardColorUtil.getColorIdentitySortValue(o1.getOriginal().getManaCost(), o1.getOriginal().getColor(), o1.getOriginal().getRules())
                 - CardColorUtil.getColorIdentitySortValue(o2.getOriginal().getManaCost(), o2.getOriginal().getColor(), o2.getOriginal().getRules());
         if (val == 0) {
             return o1.getOriginal().getName().compareTo(o2.getOriginal().getName());
         } else {
             return val;
         }
     }

 }

 class CardTypeComparator implements Comparator<MageCard> {

     @Override
     public int compare(MageCard o1, MageCard o2) {
         int val = o1.getOriginal().getCardTypes().toString().compareTo(o2.getOriginal().getCardTypes().toString());
         if (val == 0) {
             return o1.getOriginal().getName().compareTo(o2.getOriginal().getName());
         } else {
             return val;
         }
     }

 }
