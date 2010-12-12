/*
 *  Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
 * 
 *  Redistribution and use in source and binary forms, with or without modification, are
 *  permitted provided that the following conditions are met:
 * 
 *     1. Redistributions of source code must retain the above copyright notice, this list of
 *        conditions and the following disclaimer.
 * 
 *     2. Redistributions in binary form must reproduce the above copyright notice, this list
 *        of conditions and the following disclaimer in the documentation and/or other materials
 *        provided with the distribution.
 * 
 *  THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
 *  WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 *  FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
 *  CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 *  CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 *  SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 *  ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 *  NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 *  ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 * 
 *  The views and conclusions contained in the software and documentation are those of the
 *  authors and should not be interpreted as representing official policies, either expressed
 *  or implied, of BetaSteward_at_googlemail.com.
 */

/*
 * CardGrid.java
 *
 * Created on 30-Mar-2010, 9:25:40 PM
 */

package mage.client.cards;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.Map.Entry;

import mage.cards.MageCard;
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
public class CardGrid extends javax.swing.JLayeredPane implements MouseListener {

	protected CardEventSource cardEventSource = new CardEventSource();
	protected BigCard bigCard;
	protected UUID gameId;
	private Map<UUID, MageCard> cards = new HashMap<UUID, MageCard>();

    public CardGrid() {
        initComponents();
        setOpaque(false);
    }

	public void loadCards(CardsView showCards, BigCard bigCard, UUID gameId) {
		this.bigCard = bigCard;
		this.gameId = gameId;
		for (CardView card: showCards.values()) {
			if (!cards.containsKey(card.getId())) {
				addCard(card, bigCard, gameId);
			}
		}
		for (Iterator<Entry<UUID, MageCard>> i = cards.entrySet().iterator(); i.hasNext();) {
			Entry<UUID, MageCard> entry = i.next();
			if (!showCards.containsKey(entry.getKey())) {
				removeCard(entry.getKey());
				i.remove();
			}
		}
		drawCards();
		this.setVisible(true);
	}
	
	private void addCard(CardView card, BigCard bigCard, UUID gameId) {
		MageCard cardImg = Plugins.getInstance().getMageCard(card, bigCard, Config.dimensions, gameId);
		cards.put(card.getId(), cardImg);
		cardImg.addMouseListener(this);
		add(cardImg);
		cardImg.update(card);
		cards.put(card.getId(), cardImg);
	}

	public void drawCards() {
		int maxWidth = this.getParent().getWidth();
		int numColumns = maxWidth / Config.dimensions.frameWidth;
		int curColumn = 0;
		int curRow = 0;
		if (cards.size() > 0) {
			Rectangle rectangle = new Rectangle(Config.dimensions.frameWidth, Config.dimensions.frameHeight);
			List<MageCard> sortedCards = new ArrayList<MageCard>(cards.values());
			Collections.sort(sortedCards, new CardComparator());
			for (MageCard cardImg: sortedCards) {
				rectangle.setLocation(curColumn * Config.dimensions.frameWidth, curRow * 20);
				cardImg.setBounds(rectangle);
				cardImg.setCardBounds(rectangle.x, rectangle.y, Config.dimensions.frameWidth, Config.dimensions.frameHeight);
				moveToFront(cardImg);
				curColumn++;
				if (curColumn == numColumns) {
					curColumn = 0;
					curRow++;
				}
			}
		}
		resizeArea();
	}

	public void removeCard(UUID cardId) {
        for (Component comp: getComponents()) {
        	if (comp instanceof Card) {
        		if (((Card)comp).getCardId().equals(cardId)) {
					remove(comp);
        		} 
        	} else if (comp instanceof MageCard) {
        		if (((MageCard)comp).getOriginal().getId().equals(cardId)) {
					remove(comp);
        		}
        	}
        }
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

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 294, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 197, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables

	@Override
	public void mouseClicked(MouseEvent e) {
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
	public void mousePressed(MouseEvent e) {}

	@Override
	public void mouseReleased(MouseEvent e) {}

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}

	private void resizeArea() {
        Dimension area = new Dimension(0, 0);
        Dimension size = getPreferredSize();

        for (Component comp: getComponents()) {
        	Rectangle r = comp.getBounds();
        	if (r.x + r.width > area.width) {
        		area.width = r.x + r.width;
        	}
        	if (r.y + r.height > area.height) {
        		area.height = r.y + r.height;
        	}
        }
        if (size.height != area.height || size.width != area.width) {
        	setPreferredSize(area);
        	revalidate();
        	repaint();
       }

	}
}

class CardComparator implements Comparator<MageCard> {

	@Override
	public int compare(MageCard o1, MageCard o2) {
		return o1.getOriginal().getName().compareTo(o2.getOriginal().getName());
	}

}