/*
* Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
*
* Redistribution and use in source and binary forms, with or without modification, are
* permitted provided that the following conditions are met:
*
*    1. Redistributions of source code must retain the above copyright notice, this list of
*       conditions and the following disclaimer.
*
*    2. Redistributions in binary form must reproduce the above copyright notice, this list
*       of conditions and the following disclaimer in the documentation and/or other materials
*       provided with the distribution.
*
* THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
* WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
* FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
* CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
* CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
* SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
* ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
* NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
* ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*
* The views and conclusions contained in the software and documentation are those of the
* authors and should not be interpreted as representing official policies, either expressed
* or implied, of BetaSteward_at_googlemail.com.
*/

/*
 * CardsList.java
 *
 * Created on Dec 18, 2009, 10:40:12 AM
 */

package mage.client.cards;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.Beans;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import javax.swing.DefaultComboBoxModel;
import mage.Constants.CardType;

import mage.cards.MageCard;
import mage.client.constants.Constants.SortBy;
import mage.client.plugins.impl.Plugins;
import mage.client.util.Config;
import mage.client.util.Event;
import mage.client.util.Listener;
import mage.view.CardView;
import mage.view.CardsView;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class CardsList extends javax.swing.JPanel implements MouseListener {

	protected CardEventSource cardEventSource = new CardEventSource();
    private Dimension cardDimension;
	private CardsView cards;
	protected BigCard bigCard;
	protected UUID gameId;

    /** Creates new form Cards */
    public CardsList() {
        initComponents();
        jScrollPane1.setOpaque(false);
        cardArea.setOpaque(false);
        jScrollPane1.getViewport().setOpaque(false);
		cbSortBy.setModel(new DefaultComboBoxModel(SortBy.values()));
    }

	public void loadCards(CardsView showCards, BigCard bigCard, UUID gameId) {
		loadCards(showCards, bigCard, gameId, SortBy.UNSORTED);
	}

	public void loadCards(CardsView showCards, BigCard bigCard, UUID gameId, SortBy sortBy) {
		//FIXME: why we remove all cards? for performance it's better to merge changes
		cards = showCards;
		this.bigCard = bigCard;
		this.gameId = gameId;
		drawCards(sortBy);
	}

	private void drawCards(SortBy sortBy) {
		int maxWidth = this.getParent().getWidth();
		int numColumns = maxWidth / Config.dimensions.frameWidth;
		int curColumn = 0;
		int curRow = 0;
		int landCount = 0;
		int creatureCount = 0;
		cardArea.removeAll();
		if (cards != null && cards.size() > 0) {
			Rectangle rectangle = new Rectangle(Config.dimensions.frameWidth, Config.dimensions.frameHeight);
			int count = 0;
			List<CardView> sortedCards = new ArrayList<CardView>(cards.values());
			switch (sortBy) {
				case NAME:
					Collections.sort(sortedCards, new CardViewNameComparator());
					break;
				case RARITY:
					Collections.sort(sortedCards, new CardViewRarityComparator());
					break;
				case COLOR:
					Collections.sort(sortedCards, new CardViewColorComparator());
					break;
				case CASTING_COST:
					Collections.sort(sortedCards, new CardViewCostComparator());
					break;
			}
			for (CardView card: sortedCards) {
				rectangle.setLocation(curColumn * Config.dimensions.frameWidth, curRow * 20);
				addCard(card, bigCard, gameId, rectangle);
				if (card.getCardTypes().contains(CardType.LAND))
					landCount++;
				if (card.getCardTypes().contains(CardType.CREATURE))
					creatureCount++;
				curColumn++;
				if (curColumn == numColumns) {
					curColumn = 0;
					curRow++;
				}
			}
		}
		this.lblCount.setText("Count: " + Integer.toString(cards.size()));
		this.lblCreatureCount.setText("Creatures: " + Integer.toString(creatureCount));
		this.lblLandCount.setText("Lands: " + Integer.toString(landCount));
		cardArea.setPreferredSize(new Dimension(Config.dimensions.frameWidth, Config.dimensions.frameHeight + 200));
		cardArea.revalidate();
		this.revalidate();
		this.repaint();
		this.setVisible(true);
	}

	private void addCard(CardView card, BigCard bigCard, UUID gameId, Rectangle rectangle) {
		if (cardDimension == null) {
			cardDimension = new Dimension(Config.dimensions.frameWidth, Config.dimensions.frameHeight);
		}
		MageCard cardImg = Plugins.getInstance().getMageCard(card, bigCard, cardDimension, gameId, true);
		cardImg.setBounds(rectangle);
		cardArea.add(cardImg);
		cardArea.moveToFront(cardImg);
		cardImg.update(card);
		cardImg.addMouseListener(this);
		cardImg.setCardBounds(rectangle.x, rectangle.y, Config.dimensions.frameWidth, Config.dimensions.frameHeight);
	}
	
	public void addCardEventListener(Listener<Event> listener) {
		cardEventSource.addListener(listener);
	}

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

        jScrollPane1 = new javax.swing.JScrollPane();
        cardArea = new javax.swing.JLayeredPane();
        jPanel1 = new javax.swing.JPanel();
        cbSortBy = new javax.swing.JComboBox();
        lblCount = new javax.swing.JLabel();
        lblCreatureCount = new javax.swing.JLabel();
        lblLandCount = new javax.swing.JLabel();

        setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        setPreferredSize((!Beans.isDesignTime())?(new Dimension(Config.dimensions.frameWidth, Config.dimensions.frameHeight)):(new Dimension(100, 100)));

        jScrollPane1.setViewportView(cardArea);

        cbSortBy.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbSortBy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbSortByActionPerformed(evt);
            }
        });

        lblCount.setText("Card Count");

        lblCreatureCount.setText("Creature Count");

        lblLandCount.setText("Land Count");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addComponent(lblCount, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblCreatureCount, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblLandCount)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(cbSortBy, javax.swing.GroupLayout.PREFERRED_SIZE, 338, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(cbSortBy, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(lblCount)
                .addComponent(lblCreatureCount)
                .addComponent(lblLandCount))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 553, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 321, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

	private void cbSortByActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbSortByActionPerformed
		drawCards((SortBy) cbSortBy.getSelectedItem());
	}//GEN-LAST:event_cbSortByActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLayeredPane cardArea;
    private javax.swing.JComboBox cbSortBy;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblCount;
    private javax.swing.JLabel lblCreatureCount;
    private javax.swing.JLabel lblLandCount;
    // End of variables declaration//GEN-END:variables

	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
        if (e.getClickCount() == 2 && !e.isConsumed()) {
			e.consume();
			Object obj = e.getSource();
			if (obj instanceof Card) {
				cardEventSource.doubleClick(((Card)obj).getCardId(), "double-click");
			} else if (obj instanceof MageCard) {
				cardEventSource.doubleClick(((MageCard)obj).getOriginal().getId(), "double-click");
			}
		}
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

}

class CardViewNameComparator implements Comparator<CardView> {

	@Override
	public int compare(CardView o1, CardView o2) {
		return o1.getName().compareTo(o2.getName());
	}

}

class CardViewRarityComparator implements Comparator<CardView> {

	@Override
	public int compare(CardView o1, CardView o2) {
		return o1.getRarity().compareTo(o2.getRarity());
	}

}

class CardViewCostComparator implements Comparator<CardView> {

	@Override
	public int compare(CardView o1, CardView o2) {
		return Integer.valueOf(o1.getConvertedManaCost()).compareTo(Integer.valueOf(o2.getConvertedManaCost()));
	}

}

class CardViewColorComparator implements Comparator<CardView> {

	@Override
	public int compare(CardView o1, CardView o2) {
		return o1.getColor().compareTo(o2.getColor());
	}

}