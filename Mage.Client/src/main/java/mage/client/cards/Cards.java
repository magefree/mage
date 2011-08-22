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

import java.awt.*;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;
import java.util.Map.Entry;

import mage.cards.MageCard;
import mage.client.plugins.impl.Plugins;
import mage.client.util.Config;
import mage.view.CardView;
import mage.view.CardsView;
import mage.view.PermanentView;
import mage.view.StackAbilityView;

import javax.swing.border.Border;
import mage.client.util.CardsViewUtil;
import mage.sets.Sets;
import mage.view.SimpleCardView;
import mage.view.SimpleCardsView;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class Cards extends javax.swing.JPanel {

	private Map<UUID, MageCard> cards = new LinkedHashMap<UUID, MageCard>();
	private boolean dontDisplayTapped = false;
	private static final int GAP_X = 5;
    private String zone;
	
	/**
	 * Defines whether component should be visible whenever there is no objects within.
	 * True by default.
	 */
	private boolean isVisibleIfEmpty = true;

	private Dimension cardDimension;

	/** Creates new form Cards */
    public Cards() {
    	this(false);
    }

	public Cards(boolean skipAddingScrollPane) {
		initComponents(skipAddingScrollPane);
        setOpaque(false);
		//cardArea.setOpaque(false);
        setBackgroundColor(new Color(0,0,0,100));
        if (!skipAddingScrollPane) {
			jScrollPane1.setOpaque(false);
			jScrollPane1.getViewport().setOpaque(false);
		}
        if (Plugins.getInstance().isCardPluginLoaded()) {
        	cardArea.setLayout(null);
        }
	}

    /**
     * Sets components background color
     * @param color
     */
    public void setBackgroundColor(Color color) {
        cardArea.setOpaque(true);
        cardArea.setBackground(color);
    }
    
	public void setVisibleIfEmpty(boolean isVisibleIfEmpty) {
		this.isVisibleIfEmpty = isVisibleIfEmpty;
	}

	public void setBorder(Border border) {
		super.setBorder(border);
		if (jScrollPane1 != null) {
			jScrollPane1.setViewportBorder(border);
			jScrollPane1.setBorder(border);
		}
	}

    public boolean loadCards(SimpleCardsView cardsView, BigCard bigCard, UUID gameId) {        
        return loadCards(CardsViewUtil.convertSimple(cardsView), bigCard, gameId);
    }
    
	public boolean loadCards(CardsView cardsView, BigCard bigCard, UUID gameId) {
		boolean changed = false;
		
		for (Iterator<Entry<UUID, MageCard>> i = cards.entrySet().iterator(); i.hasNext();) {
			Entry<UUID, MageCard> entry = i.next();
			if (!cardsView.containsKey(entry.getKey())) {
				removeCard(entry.getKey());
				i.remove();
				changed = true;
			}
		}
		
		for (CardView card: cardsView.values()) {
			if (dontDisplayTapped) {
				if (card instanceof PermanentView) {
					((PermanentView)card).overrideTapped(false);
				}
			}
			if (card instanceof StackAbilityView) {
				CardView tmp = ((StackAbilityView)card).getSourceCard();
				tmp.overrideRules(card.getRules());
				tmp.setIsAbility(true);
				tmp.overrideTargets(card.getTargets());
				tmp.overrideId(card.getId());
				card = tmp;
			}
			if (!cards.containsKey(card.getId())) {
				addCard(card, bigCard, gameId);
				changed = true;
			}
			cards.get(card.getId()).update(card);
		}
		
		if (changed) {
			layoutCards(getCardDimension());
		}

        if (!isVisibleIfEmpty) {
        	cardArea.setVisible(cards.size() > 0);
        }
		cardArea.setPreferredSize(new Dimension((int)(cards.size() * (getCardDimension().getWidth() + GAP_X)), (int)(getCardDimension().getHeight())));
		cardArea.revalidate();
		cardArea.repaint();
		this.revalidate();
		this.repaint();
		
		return changed;
	}

	private Dimension getCardDimension() {
	   	if (cardDimension == null) {
			cardDimension = new Dimension(Config.dimensions.frameWidth, Config.dimensions.frameHeight);
		}
		return cardDimension;
	}

	private void addCard(CardView card, BigCard bigCard, UUID gameId) {
		MageCard cardImg = Plugins.getInstance().getMageCard(card, bigCard, getCardDimension(), gameId, true);
        if (zone != null) cardImg.setZone(zone);
		cards.put(card.getId(), cardImg);
		cardArea.add(cardImg);
	}
	
	private void removeCard(UUID cardId) {
        for (Component comp: cardArea.getComponents()) {
        	if (comp instanceof Card) {
        		if (((Card)comp).getCardId().equals(cardId)) {
					cardArea.remove(comp);
        		}
        	} else if (comp instanceof MageCard) {
        		if (((MageCard)comp).getOriginal().getId().equals(cardId)) {
					cardArea.remove(comp);
        		}
        	}
        }
	}
	
	private void layoutCards(Dimension dimension) {
		if (Plugins.getInstance().isCardPluginLoaded()) {
			int dx = GAP_X;
			for (MageCard card: cards.values()) {
				card.setLocation(dx, 0);
				card.setCardBounds(dx, 0, dimension.width, dimension.height);
				dx += dimension.width + GAP_X;
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
    private void initComponents(boolean skipAddingScrollPane) {
        setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0, 0)));
        setLayout(new java.awt.BorderLayout());

		cardArea = new javax.swing.JPanel();
		cardArea.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 0, 0));

		if (skipAddingScrollPane) {
			add(cardArea, java.awt.BorderLayout.CENTER);
		} else{
			jScrollPane1 = new javax.swing.JScrollPane();
			jScrollPane1.setViewportView(cardArea);
			jScrollPane1.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
			add(jScrollPane1, java.awt.BorderLayout.CENTER);
		}
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel cardArea;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables

    public void setDontDisplayTapped(boolean dontDisplayTapped) {
		this.dontDisplayTapped = dontDisplayTapped;
	}
    
    public void setHScrollSpeed(int unitIncrement) {
    	if (jScrollPane1 != null) {
			jScrollPane1.getHorizontalScrollBar().setUnitIncrement(unitIncrement);
		}
    }
    
    public void setVScrollSpeed(int unitIncrement) {
    	if (jScrollPane1 != null) {
			jScrollPane1.getVerticalScrollBar().setUnitIncrement(unitIncrement);
		}
    }

	public void setCardDimension(Dimension dimension) {
		this.cardDimension = dimension;
	}

    public void setZone(String zone) {
        this.zone = zone;
    }
}
