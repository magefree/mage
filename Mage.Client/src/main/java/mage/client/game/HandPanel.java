package mage.client.game;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.util.UUID;
import java.util.prefs.Preferences;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

import mage.Constants;
import mage.client.MageFrame;
import mage.client.cards.BigCard;
import mage.client.dialog.PreferencesDialog;
import mage.client.util.Config;
import mage.view.SimpleCardsView;

public class HandPanel extends JPanel {

    private static final int CARD_WIDTH = 75;
	
	private Dimension handCardDimensionBig;
	private Dimension handCardDimension;

    public HandPanel() {
        double factor = 1;
        sizeHand(factor);
        initComponents();
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
		hand.setPreferredSize(new java.awt.Dimension((getHandCardDimension().width + 5) * cards.size() + 5, getHandCardDimension().height + 20)); // for scroll
	}

    private Dimension getHandCardDimension() {
        Preferences pref = MageFrame.getPreferences();
        String useBigCards = pref.get(PreferencesDialog.KEY_HAND_USE_BIG_CARDS, "false");
        if (useBigCards.equals("true")) {
            return handCardDimensionBig;
        }
        return handCardDimension;
    }

    public void sizeHand(double factor) {
        int width = (int)(factor * CARD_WIDTH);
        handCardDimension = new Dimension(CARD_WIDTH, (int)(CARD_WIDTH * 3.5f / 2.5f));
        handCardDimensionBig = new Dimension(CARD_WIDTH, (int)(width * 3.5f / 2.5f));
    }
    
    private JPanel jPanel;
    private javax.swing.JScrollPane jScrollPane1;
	private Border emptyBorder = new EmptyBorder(0,0,0,0);
    private mage.client.cards.Cards hand;
    
}
