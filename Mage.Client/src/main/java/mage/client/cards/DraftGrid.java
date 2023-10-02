package mage.client.cards;

import mage.abilities.icon.CardIconRenderSettings;
import mage.cards.CardDimensions;
import mage.cards.MageCard;
import mage.client.dialog.PreferencesDialog;
import mage.client.draft.DraftPanel;
import mage.client.plugins.impl.Plugins;
import mage.client.util.ClientEventType;
import mage.client.util.Event;
import mage.client.util.Listener;
import mage.client.util.audio.AudioManager;
import mage.client.util.comparators.CardViewRarityComparator;
import mage.constants.Constants;
import mage.view.CardView;
import mage.view.CardsView;
import org.apache.log4j.Logger;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Drafting: panel with the picks
 *
 * @author BetaSteward_at_googlemail.com, JayDi85
 */
public class DraftGrid extends javax.swing.JPanel implements CardEventProducer {

    private static final Logger logger = Logger.getLogger(DraftGrid.class);

    private final DraftPanel parentPanel;

    protected final CardEventSource cardEventSource = new CardEventSource();
    protected BigCard bigCard;
    protected MageCard markedCard;
    protected boolean emptyGrid;

    /**
     * Creates new form DraftGrid
     */
    public DraftGrid(DraftPanel panel) {
        initComponents();
        parentPanel = panel;
        markedCard = null;
        emptyGrid = true;

        // ENABLE picks and other actions
        cardEventSource.addListener(event -> {
            if (event.getEventType() == ClientEventType.CARD_DOUBLE_CLICK
                    || event.getEventType() == ClientEventType.CARD_CLICK) {
                // There is a protection against picking too early in DraftPanel logic.
                // So, when double clicking early, we do mark the card as selected like
                //     a single click would.

                CardView card = (CardView) event.getSource();
                if(event.getEventType() == ClientEventType.CARD_DOUBLE_CLICK
                        && parentPanel.isAllowedToPick()
                ) {
                    cardEventSource.fireEvent(card, ClientEventType.DRAFT_PICK_CARD);
                    hidePopup();
                    AudioManager.playOnDraftSelect();
                } else {
                    MageCard cardPanel = (MageCard) event.getComponent();
                    if (markedCard != null) {
                        markedCard.setSelected(false);
                    }
                    cardEventSource.fireEvent(card, ClientEventType.DRAFT_MARK_CARD);
                    markedCard = cardPanel;
                    markedCard.setSelected(true);
                    repaint();
                }
            }
        });
    }

    public void clear() {
        markedCard = null;
        for (Component comp : getComponents()) {
            if (comp instanceof MageCard) {
                this.remove(comp);
            }
        }
    }

    public void loadBooster(CardsView booster, BigCard bigCard) {
        if (booster != null && booster.isEmpty()) {
            emptyGrid = true;
        } else {
            if (!emptyGrid) {
                AudioManager.playOnDraftSelect();
            }
            emptyGrid = false;
        }
        this.bigCard = bigCard;
        this.removeAll();

        int maxRows = 4;

        int numColumns = 5;
        int curColumn = 0;
        int curRow = 0;
        int offsetX = 5;
        int offsetY = 3;

        CardDimensions cardDimension = null;
        int maxCards;
        double scale;

        for (int i = 1; i < maxRows; i++) {
            scale = (double) (this.getHeight() / i) / Constants.FRAME_MAX_HEIGHT;
            cardDimension = new CardDimensions(scale);
            maxCards = this.getWidth() / (cardDimension.getFrameWidth() + offsetX);
            if ((maxCards * i) >= booster.size()) {
                numColumns = booster.size() / i;
                if (booster.size() % i > 0) {
                    numColumns++;
                }
                break;
            }
        }

        if (cardDimension != null) {
            Rectangle rectangle = new Rectangle(cardDimension.getFrameWidth(), cardDimension.getFrameHeight());
            Dimension dimension = new Dimension(cardDimension.getFrameWidth(), cardDimension.getFrameHeight());

            List<CardView> sortedCards = new ArrayList<>(booster.values());
            sortedCards.sort(new CardViewRarityComparator());
            for (CardView card : sortedCards) {
                MageCard cardImg = Plugins.instance.getMageCard(card, bigCard, new CardIconRenderSettings(), dimension, null, true, true, PreferencesDialog.getRenderMode(), true);
                cardImg.setCardContainerRef(this);
                cardImg.update(card);
                this.add(cardImg);

                rectangle.setLocation(curColumn * (cardDimension.getFrameWidth() + offsetX) + offsetX, curRow * (rectangle.height + offsetY) + offsetY);
                cardImg.setCardBounds(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
                curColumn++;
                if (curColumn == numColumns) {
                    curColumn = 0;
                    curRow++;
                }
            }
            repaint();
        } else {
            logger.warn("Draft Grid - no possible fit of cards");
        }
    }

    public void addCardEventListener(Listener<Event> listener) {
        cardEventSource.addListener(listener);
    }

    private void hidePopup() {
        Plugins.instance.getActionCallback().mouseExited(null, null);
    }

    /**
     * This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    @Override
    public CardEventSource getCardEventSource() {
        return cardEventSource;
    }

    public boolean isEmptyGrid() {
        return emptyGrid;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables

}
