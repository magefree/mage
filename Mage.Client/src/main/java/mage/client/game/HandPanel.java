package mage.client.game;

import mage.Constants;
import mage.client.MageFrame;
import mage.client.cards.BigCard;
import mage.client.dialog.PreferencesDialog;
import mage.client.util.Config;
import mage.view.SimpleCardsView;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.UUID;

public class HandPanel extends JPanel {

    private static final int CARD_WIDTH = 75;
    private static final double ASPECT_RATIO = 3.5 / 2.5;

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
        jPanel = new JPanel();
        jScrollPane1 = new JScrollPane(jPanel);
        jScrollPane1.getViewport().setBackground(new Color(0,0,0,0));

        jPanel.setLayout(new GridBagLayout()); // centers hand
        jPanel.setBackground(new Color(0,0,0,0));
        jPanel.add(hand);

        setOpaque(false);
        jPanel.setOpaque(false);
        jScrollPane1.setOpaque(false);

        jPanel.setBorder(emptyBorder);
        jScrollPane1.setBorder(emptyBorder);
        jScrollPane1.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        jScrollPane1.getHorizontalScrollBar().setUnitIncrement(8);

        setLayout(new BorderLayout());
        add(jScrollPane1, BorderLayout.CENTER);

        hand.setHScrollSpeed(8);
        hand.setBackgroundColor(new Color(0, 0, 0, 100));
        hand.setVisibleIfEmpty(false);
        hand.setBorder(emptyBorder);
        hand.setZone(Constants.Zone.HAND.toString());
    }

    public void loadCards(SimpleCardsView cards, BigCard bigCard, UUID gameId) {
        hand.loadCards(cards, bigCard, gameId);
        hand.sizeCards(getHandCardDimension());
    }

    private Dimension getHandCardDimension() {
        String useBigCards = MageFrame.getPreferences().get(PreferencesDialog.KEY_HAND_USE_BIG_CARDS, "false");
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
    private Border emptyBorder = new EmptyBorder(0,0,0,0);
    private mage.client.cards.Cards hand;

}
