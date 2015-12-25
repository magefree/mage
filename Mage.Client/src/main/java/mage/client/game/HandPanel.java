package mage.client.game;

import mage.client.cards.BigCard;
import mage.client.dialog.PreferencesDialog;
import mage.client.util.Config;
import mage.constants.Zone;
import mage.view.CardsView;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.UUID;

public class HandPanel extends JPanel {

    private static final int CARD_WIDTH = 75;
    private static final double ASPECT_RATIO = 3.5 / 2.5;
    private static final int HAND_MIN_CARDS_OFFSET_Y = -10;

    private boolean smallMode = false;
    private Dimension handCardDimensionBig;
    private Dimension handCardDimension;

    public HandPanel() {
        double factor = 1;
        initComponents();
        sizeHand(factor, false);
    }

    public void initComponents() {
        hand = new mage.client.cards.Cards(true);
        hand.setCardDimension(getHandCardDimension());
        hand.setMinOffsetY(HAND_MIN_CARDS_OFFSET_Y);

        jPanel = new JPanel();
        jScrollPane1 = new JScrollPane(jPanel);
        jScrollPane1.getViewport().setBackground(new Color(0, 0, 0, 0));

        jPanel.setLayout(new GridBagLayout()); // centers hand
        jPanel.setBackground(new Color(0, 0, 0, 0));
        jPanel.add(hand);

        setOpaque(false);
        jPanel.setOpaque(false);
        jScrollPane1.setOpaque(false);

        jPanel.setBorder(emptyBorder);
        jScrollPane1.setBorder(emptyBorder);
        jScrollPane1.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        jScrollPane1.getHorizontalScrollBar().setUnitIncrement(8);
        jScrollPane1.setViewportBorder(emptyBorder);

        setLayout(new BorderLayout());
        add(jScrollPane1, BorderLayout.CENTER);

        hand.setHScrollSpeed(8);
        hand.setBackgroundColor(new Color(0, 0, 0, 0));
        hand.setVisibleIfEmpty(false);
        hand.setBorder(emptyBorder);
        hand.setZone(Zone.HAND.toString());
    }

    public void cleanUp() {
        hand.cleanUp();
    }

    public void loadCards(CardsView cards, BigCard bigCard, UUID gameId) {
        hand.loadCards(cards, bigCard, gameId, true);
        hand.sizeCards(getHandCardDimension());
    }

    private Dimension getHandCardDimension() {
        String useBigCards = PreferencesDialog.getCachedValue(PreferencesDialog.KEY_HAND_USE_BIG_CARDS, "true");
        if (!smallMode && useBigCards.equals("true")) {
            return handCardDimensionBig;
        }
        return handCardDimension;
    }

    public void sizeHand(double factor, boolean smallMode) {
        this.smallMode = smallMode;
        int width = (int) (factor * CARD_WIDTH);
        int bigWidth = (int) (Config.handScalingFactor * CARD_WIDTH);
        handCardDimension = new Dimension(width, (int) (width * ASPECT_RATIO));
        handCardDimensionBig = new Dimension(bigWidth, (int) (bigWidth * ASPECT_RATIO));
        hand.setCardDimension(getHandCardDimension());
        hand.sizeCards(getHandCardDimension());
    }

    private JPanel jPanel;
    private JScrollPane jScrollPane1;
    private static final Border emptyBorder = new EmptyBorder(0, 0, 0, 0);
    private mage.client.cards.Cards hand;

}
