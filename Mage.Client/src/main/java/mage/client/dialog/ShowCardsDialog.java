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

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.UUID;

import javax.swing.JLayeredPane;
import javax.swing.SwingUtilities;

import mage.cards.CardDimensions;
import mage.client.MageFrame;
import mage.client.cards.BigCard;
import mage.client.cards.CardArea;
import mage.client.util.CardsViewUtil;
import mage.client.util.SettingsManager;
import mage.client.util.gui.GuiDisplayUtil;
import mage.view.CardsView;
import mage.view.SimpleCardsView;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class ShowCardsDialog extends MageDialog implements MouseListener {

	private boolean reloaded = false;

	/**
	 * Creates new form ShowCardsDialog
	 */
	public ShowCardsDialog() {
		initComponents();
		this.setModal(false);
	}

	public void loadCards(String name, SimpleCardsView showCards, BigCard bigCard, CardDimensions dimension, UUID gameId, boolean modal) {
        loadCards(name, CardsViewUtil.convertSimple(showCards), bigCard, dimension, gameId, modal);
    }
    
    public void loadCards(String name, CardsView showCards, BigCard bigCard, CardDimensions dimension, UUID gameId, boolean modal) {
		this.reloaded = true;
		this.title = name;
		cardArea.loadCards(showCards, bigCard, dimension, gameId, this);
		if (getParent() != MageFrame.getDesktop() /*|| this.isClosed*/) {
			MageFrame.getDesktop().add(this, JLayeredPane.POPUP_LAYER);
		}
		pack();

		this.revalidate();
		this.repaint();
		this.setModal(modal);

		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				int width = ShowCardsDialog.this.getWidth();
				int height = ShowCardsDialog.this.getWidth();
				if (width > 0 && height > 0) {
					Point centered = SettingsManager.getInstance().getComponentPosition(width, height);
					ShowCardsDialog.this.setLocation(centered.x, centered.y);
					GuiDisplayUtil.keepComponentInsideScreen(centered.x, centered.y, ShowCardsDialog.this);
				}
				ShowCardsDialog.this.setVisible(true);
			}
		});
	}

	public boolean isReloaded() {
		return this.reloaded;
	}

	public void clearReloaded() {
		this.reloaded = false;
	}

	private void initComponents() {

		cardArea = new CardArea();

		setClosable(true);
		setResizable(true);
		getContentPane().setLayout(new java.awt.BorderLayout());
		getContentPane().add(cardArea, java.awt.BorderLayout.CENTER);
        this.addMouseListener(this);

		pack();
	}
	
	private CardArea cardArea;

	@Override
	public void mouseClicked(MouseEvent e) {
		this.hideDialog();
	}

	@Override
	public void mousePressed(MouseEvent e) {
		this.hideDialog();
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
