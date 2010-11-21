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
 * Cards.java
 *
 * Created on Dec 18, 2009, 10:40:12 AM
 */

package mage.client.cards;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.MouseListener;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

import javax.swing.BorderFactory;

import mage.cards.CardDimensions;
import mage.cards.MageCard;
import mage.client.plugins.adapters.MageMouseAdapter;
import mage.client.plugins.adapters.MageMouseMotionAdapter;
import mage.client.plugins.impl.Plugins;
import mage.client.util.Config;
import mage.view.CardView;
import mage.view.CardsView;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class Cards extends javax.swing.JPanel {

	private Map<UUID, MageCard> cards = new HashMap<UUID, MageCard>();
	private boolean mouseHandlingEnabled = false;
	
    /** Creates new form Cards */
    public Cards() {
        initComponents();
        setOpaque(false);
        jScrollPane1.setOpaque(false);
        jScrollPane1.getViewport().setOpaque(false);
        cardArea.setOpaque(false);
        if (Plugins.getInstance().isCardPluginLoaded()) {
        	cardArea.setLayout(null);
        }
    }

	public boolean loadCards(CardsView cardsView, BigCard bigCard, UUID gameId) {
		boolean changed = false;
		for (CardView card: cardsView.values()) {
			if (!cards.containsKey(card.getId())) {
				addCard(card, bigCard, gameId);
				changed = true;
			}
			cards.get(card.getId()).update(card);
		}
		for (Iterator<Entry<UUID, MageCard>> i = cards.entrySet().iterator(); i.hasNext();) {
			Entry<UUID, MageCard> entry = i.next();
			if (!cardsView.containsKey(entry.getKey())) {
				removeCard(entry.getKey());
				i.remove();
				changed = true;
			}
		}
		
		if (!mouseHandlingEnabled) {
			synchronized (this) {
				if (!mouseHandlingEnabled) {
					mouseHandlingEnabled = true;
					//cardArea.addMouseListener(new MageMouseAdapter(this, gameId));			
					//cardArea.addMouseMotionListener(new MageMouseMotionAdapter(this, bigCard));
					jScrollPane1.addMouseListener(new MageMouseAdapter(cardArea, gameId));
					jScrollPane1.addMouseMotionListener(new MageMouseMotionAdapter(cardArea, bigCard));
					//addMouseListener(new MageMouseAdapter(this, gameId));
				}
			}
		}

		cardArea.setPreferredSize(new Dimension(cards.size() * Config.dimensions.frameWidth, Config.dimensions.frameHeight));
		cardArea.revalidate();
		cardArea.repaint();
		this.revalidate();
		this.repaint();
		if (changed) {
			layoutCards(Config.dimensions);
		}
		return changed;
	}

	private void addCard(CardView card, BigCard bigCard, UUID gameId) {
		MageCard cardImg = Plugins.getInstance().getMageCard(card, bigCard, Config.dimensions, gameId);
		cards.put(card.getId(), cardImg);
		cardArea.add(cardImg);
		/*if (Plugins.getInstance().isCardPluginLoaded()) {
			cardImg.setBorder(BorderFactory.createLineBorder(Color.red));
		}*/
	}
	
	private void removeCard(UUID cardId) {
        for (Component comp: cardArea.getComponents()) {
        	if (comp instanceof Card) {
        		if (((Card)comp).getCardId().equals(cardId)) {
					cardArea.remove(comp);
        		}
        	} else if (comp instanceof MageCard) {
        		if (((MageCard)comp).getOriginal().equals(cardId)) {
					cardArea.remove(comp);
        		}
        	}
        }
	}
	
	private void layoutCards(CardDimensions dimension) {
		if (Plugins.getInstance().isCardPluginLoaded()) {
			int dx = 0;
			for (MageCard card: cards.values()) {
				card.setLocation(dx, 0);
				card.setCardBounds(dx, 0, dimension.frameWidth, dimension.frameHeight);
				dx += dimension.frameWidth + 5;
			}
		}
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
        cardArea = new javax.swing.JPanel();

        setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        setLayout(new java.awt.BorderLayout());

        jScrollPane1.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

        cardArea.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 0, 0));
        jScrollPane1.setViewportView(cardArea);

        add(jScrollPane1, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel cardArea;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables

}
