package mage.client.game;

import java.awt.*;
import java.util.UUID;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import mage.client.cards.BigCard;
import mage.client.util.GUISizeHelper;
import mage.constants.Zone;
import mage.view.CardsView;

public class HandPanel extends JPanel {

    private static final int HAND_MIN_CARDS_OFFSET_Y = -10;

    public HandPanel() {
        initComponents();
        changeGUISize();
    }

    public void initComponents() {
        hand = new mage.client.cards.Cards(true);
        hand.setMinOffsetY(HAND_MIN_CARDS_OFFSET_Y);
        hand.setCardDimension(GUISizeHelper.handCardDimension);

        jPanel = new JPanel();
        jScrollPane1 = new JScrollPane(jPanel);
        jScrollPane1.getViewport().setBackground(new Color(0, 0, 0, 0));

        jPanel.setLayout(new GridBagLayout()); // centers hand
        jPanel.setBackground(new Color(0, 0, 0, 0));
        jPanel.add(hand);

        setOpaque(false);
        jPanel.setOpaque(false);
        jScrollPane1.setOpaque(false);

        jPanel.setBorder(EMPTY_BORDER);
        jScrollPane1.setBorder(EMPTY_BORDER);
        jScrollPane1.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        jScrollPane1.getHorizontalScrollBar().setUnitIncrement(8);
        jScrollPane1.setViewportBorder(EMPTY_BORDER);

        setLayout(new BorderLayout());
        add(jScrollPane1, BorderLayout.CENTER);

        hand.setHScrollSpeed(8);
        hand.setBackgroundColor(new Color(0, 0, 0, 0));
        hand.setVisibleIfEmpty(false);
        hand.setBorder(EMPTY_BORDER);
        hand.setZone(Zone.HAND.toString());
    }

    public void cleanUp() {
        hand.cleanUp();
    }

    public void changeGUISize() {
        setGUISize();
    }

    private void setGUISize() {
        jScrollPane1.getVerticalScrollBar().setPreferredSize(new Dimension(GUISizeHelper.scrollBarSize, 0));
        jScrollPane1.getHorizontalScrollBar().setPreferredSize(new Dimension(0, GUISizeHelper.scrollBarSize));
        hand.setCardDimension(GUISizeHelper.handCardDimension);
        hand.changeGUISize();
    }

    public void loadCards(CardsView cards, BigCard bigCard, UUID gameId) {
        hand.loadCards(cards, bigCard, gameId, true);
    }

    private JPanel jPanel;
    private JScrollPane jScrollPane1;
    private static final Border EMPTY_BORDER = new EmptyBorder(0, 0, 0, 0);
    private mage.client.cards.Cards hand;

}
