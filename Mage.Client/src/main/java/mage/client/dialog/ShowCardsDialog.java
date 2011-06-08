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
 * ShowCardsDialog.java
 *
 * Created on 3-Feb-2010, 8:59:11 PM
 */

package mage.client.dialog;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.UUID;

import javax.swing.JLayeredPane;

import mage.cards.CardDimensions;
import mage.cards.MageCard;
import mage.client.MageFrame;
import mage.client.cards.BigCard;
import mage.client.plugins.impl.Plugins;
import mage.client.util.Config;
import mage.view.AbilityView;
import mage.view.CardView;
import mage.view.CardsView;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class ShowCardsDialog extends MageDialog implements MouseListener {

	private boolean reloaded = false;

    /** Creates new form ShowCardsDialog */
    public ShowCardsDialog() {
        initComponents();
		this.setModal(false);
    }

	public void loadCards(String name, CardsView showCards, BigCard bigCard, CardDimensions dimension, UUID gameId, boolean modal) {
		this.reloaded = true;
		this.title = name;
		cardArea.removeAll();
		if (showCards != null && showCards.size() < 10)
			loadCardsFew(showCards, bigCard, gameId);
		else
			loadCardsMany(showCards, bigCard, gameId);
		cardArea.revalidate();
		if (getParent() != MageFrame.getDesktop() /*|| this.isClosed*/) {
			MageFrame.getDesktop().add(this, JLayeredPane.POPUP_LAYER);
		}
		pack();
		this.revalidate();
		this.repaint();
		this.setModal(modal);
		this.setVisible(true);
	}

	private void loadCardsFew(CardsView showCards, BigCard bigCard, UUID gameId) {
		Rectangle rectangle = new Rectangle(Config.dimensions.frameWidth, Config.dimensions.frameHeight);
		Dimension dimension = new Dimension(Config.dimensions.frameWidth, Config.dimensions.frameHeight);
		for (CardView card: showCards.values()) {
			addCard(card, bigCard, gameId, rectangle, dimension);
			rectangle.translate(Config.dimensions.frameWidth, 0);
		}
		cardArea.setPreferredSize(new Dimension(Config.dimensions.frameWidth * showCards.size(), Config.dimensions.frameHeight));
	}
	
	private void addCard(CardView card, BigCard bigCard, UUID gameId, Rectangle rectangle, Dimension dimension) {
		if (card instanceof AbilityView) {
			CardView tmp = ((AbilityView)card).getSourceCard();
			tmp.overrideRules(card.getRules());
			tmp.setIsAbility(true);
			tmp.overrideTargets(card.getTargets());
			tmp.setAbility(card); // cross-reference, required for ability picker
			card = tmp;
		}
		MageCard cardImg = Plugins.getInstance().getMageCard(card, bigCard, dimension, gameId, true);
		cardImg.setBounds(rectangle);
		cardArea.add(cardImg);
		cardArea.moveToFront(cardImg);
		cardImg.update(card);
		cardImg.addMouseListener(this);
		cardImg.setCardBounds(rectangle.x, rectangle.y, Config.dimensions.frameWidth, Config.dimensions.frameHeight);
	}

	private void loadCardsMany(CardsView showCards, BigCard bigCard, UUID gameId) {
		int columns = 1;
		if (showCards != null && showCards.size() > 0) {
			Rectangle rectangle = new Rectangle(Config.dimensions.frameWidth, Config.dimensions.frameHeight);
			Dimension dimension = new Dimension(Config.dimensions.frameWidth, Config.dimensions.frameHeight);
			int count = 0;
			for (CardView card: showCards.values()) {
				addCard(card, bigCard, gameId, rectangle, dimension);
				if (count >= 20) {
					rectangle.translate(Config.dimensions.frameWidth, -400);
					columns++;
					count = 0;
				} else {
					rectangle.translate(0, 20);
					count++;
				}
			}
		}
		cardArea.setPreferredSize(new Dimension(Config.dimensions.frameWidth * columns, Config.dimensions.frameHeight + 400));
	}

	public boolean isReloaded() {
		return this.reloaded;
	}

	public void clearReloaded() {
		this.reloaded = false;
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

        setClosable(true);
        setResizable(true);
        getContentPane().setLayout(new java.awt.BorderLayout());

        jScrollPane1.setViewportView(cardArea);

        getContentPane().add(jScrollPane1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLayeredPane cardArea;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables

	@Override
	public void mouseClicked(MouseEvent e) {
		this.hideDialog();
	}

	@Override
	public void mousePressed(MouseEvent e) {}

	@Override
	public void mouseReleased(MouseEvent e) {}

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}

}
