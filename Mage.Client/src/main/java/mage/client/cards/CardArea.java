/*
* Copyright 2012 BetaSteward_at_googlemail.com. All rights reserved.
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

package mage.client.cards;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.MouseListener;
import java.util.UUID;

import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import mage.cards.CardDimensions;
import mage.cards.MageCard;
import mage.client.plugins.impl.Plugins;
import mage.client.util.CardsViewUtil;
import mage.client.util.Config;
import mage.view.AbilityView;
import mage.view.CardView;
import mage.view.CardsView;
import mage.view.SimpleCardsView;

public class CardArea extends JPanel {

	private boolean reloaded = false;
	private javax.swing.JLayeredPane cardArea;
	private javax.swing.JScrollPane scrollPane;

	/**
	 * Create the panel.
	 */
	public CardArea() {
		setLayout(new BorderLayout(0, 0));
		
		scrollPane = new JScrollPane();
		add(scrollPane, BorderLayout.CENTER);
		
		cardArea = new JLayeredPane();
		scrollPane.setViewportView(cardArea);

	}

	public void loadCards(SimpleCardsView showCards, BigCard bigCard, CardDimensions dimension, UUID gameId, MouseListener listener) {
        loadCards(CardsViewUtil.convertSimple(showCards), bigCard, dimension, gameId, listener);
    }
    
    public void loadCards(CardsView showCards, BigCard bigCard, CardDimensions dimension, UUID gameId, MouseListener listener) {
		this.reloaded = true;
		cardArea.removeAll();
		if (showCards != null && showCards.size() < 10)
			loadCardsFew(showCards, bigCard, gameId, listener);
		else
			loadCardsMany(showCards, bigCard, gameId, listener);
		cardArea.revalidate();

		this.revalidate();
		this.repaint();
	}

    public void loadCardsNarrow(CardsView showCards, BigCard bigCard, CardDimensions dimension, UUID gameId, MouseListener listener) {
		this.reloaded = true;
		cardArea.removeAll();
		loadCardsMany(showCards, bigCard, gameId, listener);
		cardArea.revalidate();

		this.revalidate();
		this.repaint();
	}

    private void loadCardsFew(CardsView showCards, BigCard bigCard, UUID gameId, MouseListener listener) {
		Rectangle rectangle = new Rectangle(Config.dimensions.frameWidth, Config.dimensions.frameHeight);
		Dimension dimension = new Dimension(Config.dimensions.frameWidth, Config.dimensions.frameHeight);
		for (CardView card : showCards.values()) {
			addCard(card, bigCard, gameId, rectangle, dimension, listener);
			rectangle.translate(Config.dimensions.frameWidth, 0);
		}
		cardArea.setPreferredSize(new Dimension(Config.dimensions.frameWidth * showCards.size(), Config.dimensions.frameHeight));
	}

	private void addCard(CardView card, BigCard bigCard, UUID gameId, Rectangle rectangle, Dimension dimension, MouseListener listener) {
		if (card instanceof AbilityView) {
			CardView tmp = ((AbilityView) card).getSourceCard();
			tmp.overrideRules(card.getRules());
			tmp.setIsAbility(true);
			tmp.overrideTargets(card.getTargets());
			tmp.setAbility(card); // cross-reference, required for ability picker
			card = tmp;
		}
		MageCard cardImg = Plugins.getInstance().getMageCard(card, bigCard, dimension, gameId, true);
		cardImg.setBounds(rectangle);
        if (listener != null)
            cardImg.addMouseListener(listener);
		cardArea.add(cardImg);
		cardArea.moveToFront(cardImg);
		cardImg.update(card);
		cardImg.setCardBounds(rectangle.x, rectangle.y, Config.dimensions.frameWidth, Config.dimensions.frameHeight);
	}

	private void loadCardsMany(CardsView showCards, BigCard bigCard, UUID gameId, MouseListener listener) {
		int columns = 1;
		if (showCards != null && showCards.size() > 0) {
			Rectangle rectangle = new Rectangle(Config.dimensions.frameWidth, Config.dimensions.frameHeight);
			Dimension dimension = new Dimension(Config.dimensions.frameWidth, Config.dimensions.frameHeight);
			int count = 0;
			for (CardView card : showCards.values()) {
				addCard(card, bigCard, gameId, rectangle, dimension, listener);
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
	
}
