 package mage.client.cards;

 import mage.abilities.icon.CardIconRenderSettings;
 import mage.cards.MageCard;
 import mage.client.deckeditor.SortSetting;
 import mage.client.dialog.PreferencesDialog;
 import mage.client.plugins.impl.Plugins;
 import mage.client.util.Event;
 import mage.client.util.GUISizeHelper;
 import mage.client.util.Listener;
 import mage.client.util.comparators.*;
 import mage.constants.Rarity;
 import mage.view.CardView;
 import mage.view.CardsView;

 import javax.swing.*;
 import java.awt.*;
 import java.awt.event.MouseListener;
 import java.util.List;
 import java.util.*;
 import java.util.Map.Entry;
 import java.util.stream.Collectors;

 /**
  * Deck editor: grid mode for selected cards list (NOT a drafting, only for free edition and sideboarding)
  * TODO: combine source code with CardsList.java
  *
  * @author BetaSteward_at_googlemail.com, JayDi85
  */
 public class CardGrid extends javax.swing.JLayeredPane implements CardEventProducer, /* MouseListener,*/ ICardGrid {

     protected final CardEventSource cardEventSource = new CardEventSource();
     protected BigCard bigCard;
     protected UUID gameId;
     private final Map<UUID, MageCard> cards = new HashMap<>();
     private Dimension cardDimension;
     private final List<JLabel> countLabels = new ArrayList<>();

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
     public Object getCardsStore() {
         return this.cards;
     }

     @Override
     public void clearCardsStoreBeforeUpdate() {
         this.cards.clear();
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
         MageCard cardImg = Plugins.instance.getMageCard(card, bigCard, new CardIconRenderSettings(), cardDimension, gameId, drawImage, true, PreferencesDialog.getRenderMode(), true);
         cardImg.setCardContainerRef(this);
         cardImg.update(card);
         // card position calculated on parent call by drawCards
         //cardImg.setCardBounds(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
         cards.put(card.getId(), cardImg);
         this.add(cardImg, (Integer) 10); // count label must be on layer 0 for background drawing
     }

     @Override
     public void drawCards(SortSetting sortSetting) {
         this.countLabels.forEach(this::remove);
         this.countLabels.clear();

         int maxWidth = this.getParent().getWidth();
         int vertOffsetPerCardInStack = GUISizeHelper.editorCardVertOffsetInStack;
         int numColumns = maxWidth / cardDimension.width;
         int curColumn = 0;
         int curRow = 0;
         if (!cards.isEmpty()) {
             Rectangle rectangle = new Rectangle(cardDimension.width, cardDimension.height);

             List<CardView> sortedCards = cards.values().stream().map(MageCard::getOriginal).collect(Collectors.toList());
             CardViewComparator comparator;
             switch (sortSetting.getSortBy()) {
                 case NAME:
                     comparator = new CardViewNameComparator();
                     break;
                 case CARD_TYPE:
                     comparator = new CardViewCardTypeComparator();
                     break;
                 case RARITY:
                     comparator = new CardViewRarityComparator();
                     break;
                 case COLOR:
                     comparator = new CardViewColorComparator();
                     break;
                 case COLOR_IDENTITY:
                     comparator = new CardViewColorIdentityComparator();
                     break;
                 case CASTING_COST:
                     comparator = new CardViewCostComparator();
                     break;
                 case UNSORTED:
                     comparator = new CardViewNoneComparator();
                     break;
                 case EDH_POWER_LEVEL:
                     comparator = new CardViewEDHPowerLevelComparator();
                     break;
                 default:
                     throw new IllegalArgumentException("Error, unknown sort settings in deck editor: " + sortSetting.getSortBy());
             }

             sortedCards.sort(new CardViewNameComparator());
             sortedCards.sort(comparator);

             MageCard lastCard = null;
             JLabel lastCountLabel = null;
             for (CardView sortedCard : sortedCards) {
                 MageCard currentCard = this.cards.get(sortedCard.getId());
                 if (sortSetting.isPilesToggle()) {
                     if (lastCard == null) {
                         lastCard = currentCard;
                         // new new count label
                         lastCountLabel = addNewCountLabel(curColumn);
                     }

                     // create new column on different card sorting
                     if (comparator.compare(currentCard.getOriginal(), lastCard.getOriginal()) != 0) {
                         curColumn++;
                         curRow = 0;
                         // add new count label
                         lastCountLabel = addNewCountLabel(curColumn);
                     }

                     // update last count label stats
                     String description = comparator.getCategoryName(currentCard.getOriginal());
                     DragCardGrid.updateCountLabel(lastCountLabel, curRow + 1, description);

                     rectangle.setLocation(curColumn * cardDimension.width, curRow * vertOffsetPerCardInStack + DragCardGrid.COUNT_LABEL_HEIGHT);
                     currentCard.setCardBounds(rectangle.x, rectangle.y, cardDimension.width, cardDimension.height);
                     moveToFront(currentCard);
                     curRow++;
                     lastCard = currentCard;
                 } else {
                     rectangle.setLocation(curColumn * cardDimension.width, curRow * vertOffsetPerCardInStack);
                     currentCard.setCardBounds(rectangle.x, rectangle.y, cardDimension.width, cardDimension.height);
                     moveToFront(currentCard);
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

     private JLabel addNewCountLabel(int columnNumber) {
         JLabel label = DragCardGrid.createCountLabel(null);
         this.countLabels.add(label);
         this.add(label, (Integer) 0); // draw on background
         label.setLocation(columnNumber * cardDimension.width, 5);
         label.setSize(cardDimension.width, DragCardGrid.COUNT_LABEL_HEIGHT);
         label.setVisible(true);
         return label;
     }

     private void clearCards() {
         // remove possible mouse listeners, preventing gc
         for (MageCard mageCard : cards.values()) {
                 mageCard.cleanUp();
         }
         this.cards.clear();
         removeAllCardImg();
     }

     private void removeAllCardImg() {
         for (Component comp : getComponents()) {
             if (comp instanceof MageCard) {
                 remove(comp);
             }
         }
     }

     private void removeCardImg(UUID cardId) {
         for (Component comp : getComponents()) {
             if (comp instanceof MageCard) {
                 if (((MageCard) comp).getOriginal().getId().equals(cardId)) {
                     remove(comp);
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
     public CardEventSource getCardEventSource() {
         return this.cardEventSource;
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

 /**
  * Workaround to use CardViewComparator with card panels
   */
 interface CardPanelComparator extends Comparator<MageCard> {
     String getCategoryName(MageCard sample);
 }

 class CardPanelNameComparator implements Comparator<MageCard> {

     @Override
     public int compare(MageCard o1, MageCard o2) {
         return o1.getOriginal().getName().compareTo(o2.getOriginal().getName());
     }

 }

 class CardPanelRarityComparator implements Comparator<MageCard> {

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

 class CardPanelCostComparator implements Comparator<MageCard> {

     @Override
     public int compare(MageCard o1, MageCard o2) {
         int val = Integer.compare(o1.getOriginal().getManaValue(), o2.getOriginal().getManaValue());
         if (val == 0) {
             return o1.getOriginal().getName().compareTo(o2.getOriginal().getName());
         } else {
             return val;
         }
     }

 }

 class CardPanelColorComparator implements Comparator<MageCard> {

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

 class CardPanelColorIdentityComparator implements Comparator<MageCard> {

     @Override
     public int compare(MageCard o1, MageCard o2) {
         int val = CardViewColorIdentityComparator.calcHash(o1.getOriginal()) - CardViewColorIdentityComparator.calcHash(o2.getOriginal());
         if (val == 0) {
             return o1.getOriginal().getName().compareTo(o2.getOriginal().getName());
         } else {
             return val;
         }
     }
 }

 class CardPanelTypeComparator implements Comparator<MageCard> {

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
