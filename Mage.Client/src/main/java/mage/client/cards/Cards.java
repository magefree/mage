

 /*
  * Cards.java
  *
  * Created on Dec 18, 2009, 10:40:12 AM
  */
 package mage.client.cards;

 import mage.cards.MageCard;
 import mage.client.dialog.PreferencesDialog;
 import mage.client.plugins.impl.Plugins;
 import mage.client.util.CardsViewUtil;
 import mage.client.util.ClientDefaultSettings;
 import mage.client.util.GUISizeHelper;
 import mage.view.*;
 import org.apache.log4j.Logger;
 import org.mage.card.arcane.CardPanel;

 import javax.swing.border.Border;
 import javax.swing.border.EmptyBorder;
 import java.awt.*;
 import java.util.*;
 import java.util.Map.Entry;

 /**
  * @author BetaSteward_at_googlemail.com
  */
 public class Cards extends javax.swing.JPanel {

     private static final Logger LOGGER = Logger.getLogger(Cards.class);
     private static final Border EMPTY_BORDER = new EmptyBorder(0, 0, 0, 0);

     private final Map<UUID, MageCard> cards = new LinkedHashMap<>();
     private boolean dontDisplayTapped = false;
     private static final int GAP_X = 5; // needed for marking cards with coloured fram (e.g. on hand)
     private String zone;

     private int minOffsetY = 0;

     /**
      * Defines whether component should be visible whenever there is no objects
      * within. True by default.
      */
     private boolean isVisibleIfEmpty = true;

     private Dimension cardDimension;

     /**
      * Creates new form Cards
      */
     public Cards() {
         this(false);
     }

     public Cards(boolean skipAddingScrollPane) {
         initComponents(skipAddingScrollPane);
         setOpaque(false);
         //cardArea.setOpaque(false);
         setBackgroundColor(new Color(0, 0, 0, 100));
         if (!skipAddingScrollPane) {
             jScrollPane1.setOpaque(false);
             jScrollPane1.getViewport().setOpaque(false);
             jScrollPane1.setBorder(EMPTY_BORDER);
         }
         if (Plugins.instance.isCardPluginLoaded()) {
             cardArea.setLayout(null);
         }
         cardArea.setBorder(EMPTY_BORDER);
         setGUISize();
     }

     public void cleanUp() {
     }

     public void changeGUISize() {
         setGUISize();
         for (MageCard mageCard : cards.values()) {
             mageCard.setCardBounds(0, 0, getCardDimension().width, getCardDimension().height);
             mageCard.updateArtImage();
             mageCard.doLayout();
         }
         layoutCards();
         sizeCards(getCardDimension());
     }

     private void setGUISize() {
         if (jScrollPane1 != null) {
             jScrollPane1.getVerticalScrollBar().setPreferredSize(new Dimension(GUISizeHelper.scrollBarSize, 0));
             jScrollPane1.getHorizontalScrollBar().setPreferredSize(new Dimension(0, GUISizeHelper.scrollBarSize));
         }
     }

     /**
      * Sets components background color
      *
      * @param color
      */
     public void setBackgroundColor(Color color) {
         setBackground(color);
         cardArea.setOpaque(true);
         cardArea.setBackground(color);
     }

     public void setVisibleIfEmpty(boolean isVisibleIfEmpty) {
         this.isVisibleIfEmpty = isVisibleIfEmpty;
     }

     @Override
     public void setBorder(Border border) {
         super.setBorder(border);
         if (jScrollPane1 != null) {
             jScrollPane1.setViewportBorder(border);
             jScrollPane1.setBorder(border);
         }
     }

     public boolean loadCards(SimpleCardsView cardsView, BigCard bigCard, UUID gameId) {
         return loadCards(CardsViewUtil.convertSimple(cardsView), bigCard, gameId, true);
     }

     public boolean loadCards(CardsView cardsView, BigCard bigCard, UUID gameId, boolean revertOrder) {
         boolean changed = false;

         // remove objects no longer on the stack from display
         for (Iterator<Entry<UUID, MageCard>> i = cards.entrySet().iterator(); i.hasNext(); ) {
             Entry<UUID, MageCard> entry = i.next();
             if (!cardsView.containsKey(entry.getKey())) {
                 removeCard(entry.getKey());
                 i.remove();
                 changed = true;
             }
         }

         // Workaround for bug leaving display of objects on the stack (issue #213 https://github.com/magefree/mage/issues/213)
         if (cardsView.isEmpty() && countCards() > 0) {
             // problem happens with transformable cards
             LOGGER.fatal("Card object on the cards panel was not removed");
             for (Component comp : cardArea.getComponents()) {
                 if (comp instanceof Card) {
                     Card card = (Card) comp;
                     LOGGER.fatal("Card name:" + card.getName() + " type:" + card.getType(null));
                 } else if (comp instanceof MageCard) {
                     MageCard mageCard = (MageCard) comp;
                     LOGGER.fatal("MageCard name:" + mageCard.getName() + " toolTiptext:" + mageCard.getToolTipText());
                 } else {
                     LOGGER.fatal("Unknown object:" + comp.getName() + " className:" + comp.getClass().getName());
                 }
                 cardArea.remove(comp);
             }
         }

         java.util.List<CardView> orderedList = new ArrayList<>();
         if (revertOrder) {
             // order objects for display
             for (CardView card : cardsView.values()) {
                 orderedList.add(0, card);
             }
         } else {
             orderedList.addAll(cardsView.values());
         }

         // add objects to the panel
         for (CardView card : orderedList) {
             if (dontDisplayTapped) {
                 if (card instanceof PermanentView) {
                     ((PermanentView) card).overrideTapped(false);
                 }
             }
             if (card instanceof StackAbilityView) {
                 CardView tmp = ((StackAbilityView) card).getSourceCard();
                 tmp.overrideRules(card.getRules());
                 tmp.setIsAbility(true);
                 tmp.overrideTargets(card.getTargets());
                 tmp.overrideId(card.getId());
                 tmp.setAbilityType(card.getAbilityType());
                 card = tmp;
             } else {
                 card.setAbilityType(null);
             }
             if (!cards.containsKey(card.getId())) {
                 addCard(card, bigCard, gameId);
                 changed = true;
             }
             cards.get(card.getId()).update(card);
         }

         if (changed) {
             layoutCards();
         }

         if (!isVisibleIfEmpty) {
             cardArea.setVisible(!cards.isEmpty());
         }

         sizeCards(getCardDimension());
         this.revalidate();
         this.repaint();

         return changed;
     }

     public void sizeCards(Dimension cardDimension) {
         cardArea.setPreferredSize(new Dimension((int) ((cards.size()) * (cardDimension.getWidth() + GAP_X)) + 20, (int) (cardDimension.getHeight()) + 20));
         cardArea.revalidate();
         cardArea.repaint();
     }

     public int getNumberOfCards() {
         return cards.size();
     }

     private Dimension getCardDimension() {
         if (cardDimension == null) {
             cardDimension = new Dimension(ClientDefaultSettings.dimensions.getFrameWidth(), ClientDefaultSettings.dimensions.getFrameHeight());
         }
         return cardDimension;
     }

     public void setCardDimension(Dimension dimension) {
         this.cardDimension = dimension;
         for (Component component : cardArea.getComponents()) {
             if (component instanceof CardPanel) {
                 component.setBounds(0, 0, dimension.width, dimension.height);
             }
         }
         layoutCards();
     }

     private void addCard(CardView card, BigCard bigCard, UUID gameId) {
         MageCard mageCard = Plugins.instance.getMageCard(card, bigCard, getCardDimension(), gameId, true, true, PreferencesDialog.getRenderMode(), true);
         if (zone != null) {
             mageCard.setZone(zone);
         }
         cards.put(card.getId(), mageCard);
         cardArea.add(mageCard);
         definePosition(mageCard);
         mageCard.setCardAreaRef(cardArea);
     }

     private void definePosition(MageCard card) {
         int dx = 0;
         for (Component comp : cardArea.getComponents()) {
             if (!comp.equals(card)) {
                 dx = Math.max(dx, (int) comp.getLocation().getX());
             }
         }
         dx += ((CardPanel) card).getCardWidth() + GAP_X;
         card.setLocation(dx, (int) card.getLocation().getY());
     }

     private void removeCard(UUID cardId) {
         for (Component comp : cardArea.getComponents()) {
             if (comp instanceof Card) {
                 if (((Card) comp).getCardId().equals(cardId)) {
                     cardArea.remove(comp);
                 }
             } else if (comp instanceof MageCard) {
                 if (((MageCard) comp).getOriginal().getId().equals(cardId)) {
                     cardArea.remove(comp);
                 }
             }
         }
     }

     private int countCards() {
         return cardArea.getComponentCount();
     }

     /**
      * This method is called from within the constructor to initialize the form.
      * WARNING: Do NOT modify this code. The content of this method is always
      * regenerated by the Form Editor.
      */
     @SuppressWarnings("unchecked")
     // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
     private void initComponents(boolean skipAddingScrollPane) {
         setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0, 0)));
         setLayout(new java.awt.BorderLayout());

         cardArea = new javax.swing.JPanel();
         cardArea.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 0, 0));

         if (skipAddingScrollPane) {
             add(cardArea, java.awt.BorderLayout.CENTER);
         } else {
             jScrollPane1 = new javax.swing.JScrollPane();
             jScrollPane1.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
             jScrollPane1.setViewportView(cardArea);
             add(jScrollPane1, java.awt.BorderLayout.CENTER);
         }
     }// </editor-fold>//GEN-END:initComponents

     // Variables declaration - do not modify//GEN-BEGIN:variables
     private javax.swing.JPanel cardArea;
     private javax.swing.JScrollPane jScrollPane1;
     // End of variables declaration//GEN-END:variables

     public void setDontDisplayTapped(boolean dontDisplayTapped) {
         this.dontDisplayTapped = dontDisplayTapped;
     }

     public void setHScrollSpeed(int unitIncrement) {
         if (jScrollPane1 != null) {
             jScrollPane1.getHorizontalScrollBar().setUnitIncrement(unitIncrement);
         }
     }

     public void setVScrollSpeed(int unitIncrement) {
         if (jScrollPane1 != null) {
             jScrollPane1.getVerticalScrollBar().setUnitIncrement(unitIncrement);
         }
     }

     private void layoutCards() {
         java.util.List<CardPanel> cardsToLayout = new ArrayList<>();
         // get all the card panels
         for (Component component : cardArea.getComponents()) {
             if (component instanceof CardPanel) {
                 cardsToLayout.add((CardPanel) component);
             }
         }
         // sort the cards
         cardsToLayout.sort(Comparator.comparingInt(cp -> cp.getLocation().x));
         // relocate the cards
         int dx = 0;
         for (Component component : cardsToLayout) {
             component.setLocation(dx, Math.max(component.getLocation().y, minOffsetY));
             dx += ((CardPanel) component).getCardWidth() + GAP_X;
         }
     }

     public void setZone(String zone) {
         this.zone = zone;
     }

     public void setMinOffsetY(int minOffsetY) {
         this.minOffsetY = minOffsetY;
     }
 }
