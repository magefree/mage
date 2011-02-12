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
 * CardSelector.java
 *
 * Created on Feb 18, 2010, 2:49:03 PM
 */

package mage.client.deckeditor;

import java.awt.Cursor;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.*;
import javax.swing.DefaultComboBoxModel;
import mage.Constants.CardType;
import mage.cards.Card;
import mage.cards.ExpansionSet;
import mage.client.cards.BigCard;
import mage.client.cards.CardGrid;
import mage.client.cards.CardsStorage;
import mage.client.constants.Constants.SortBy;
import mage.filter.Filter.ComparisonScope;
import mage.filter.FilterCard;
import mage.sets.Sets;
import mage.view.CardsView;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class CardSelector extends javax.swing.JPanel implements ComponentListener {

	private final List<Card> cards = new ArrayList<Card>();
	private final FilterCard filter = new FilterCard();
	private BigCard bigCard;
	
    /** Creates new form CardSelector */
    public CardSelector() {
        initComponents();
		this.addComponentListener(this);
		setOpaque(false);
	    cardGrid.setOpaque(false);
	    jScrollPane1.setOpaque(false);
	    jScrollPane1.getViewport().setOpaque(false);
		cbSortBy.setModel(new DefaultComboBoxModel(SortBy.values()));
    }

	public void loadCards(List<Card> sideboard, BigCard bigCard, boolean construct) {
		this.bigCard = bigCard;
		this.btnBooster.setVisible(false);
		this.btnClear.setVisible(false);
		this.cbExpansionSet.setVisible(false);
		this.cards.clear();
		for (Card card: sideboard) {
			this.cards.add(card);
		}
		initFilter();
		filterCards();
	}

	public void loadCards(BigCard bigCard) {
		this.bigCard = bigCard;
		this.btnBooster.setVisible(true);
		this.btnClear.setVisible(true);
		this.cbExpansionSet.setVisible(true);
		Object[] l = Sets.getInstance().values().toArray();
		Arrays.sort(l, new Comparator<Object>() {
		    @Override
		    public int compare(Object o1, Object o2) {
		        return ((ExpansionSet)o1).getName().compareTo(((ExpansionSet)o2).getName());
		    }
		});
		cbExpansionSet.setModel(new DefaultComboBoxModel(l));
//		cbExpansionSet.insertItemAt("All sets", 0);
		cbExpansionSet.setSelectedIndex(0);
		initFilter();
		filter.getExpansionSetCode().add(((ExpansionSet)this.cbExpansionSet.getSelectedItem()).getCode());
		filterCards();
	}

	private void initFilter() {
		filter.setUseColor(true);
		filter.getColor().setBlack(true);
		filter.getColor().setBlue(true);
		filter.getColor().setGreen(true);
		filter.getColor().setWhite(true);
		filter.getColor().setRed(true);
		filter.setColorless(true);
		filter.setUseColorless(true);
		filter.setNotColor(false);
		filter.setScopeColor(ComparisonScope.Any);
		filter.getCardType().add(CardType.LAND);
		filter.getCardType().add(CardType.ARTIFACT);
		filter.getCardType().add(CardType.CREATURE);
		filter.getCardType().add(CardType.ENCHANTMENT);
		filter.getCardType().add(CardType.INSTANT);
		filter.getCardType().add(CardType.PLANESWALKER);
		filter.getCardType().add(CardType.SORCERY);
		filter.setScopeCardType(ComparisonScope.Any);
	}

	private void filterCards() {
		try {
			List<Card> filteredCards = new ArrayList<Card>();
			setCursor(new Cursor(Cursor.WAIT_CURSOR));
			if (!cards.isEmpty()) {
				for (Card card: cards) {
					if (filter.match(card))
						filteredCards.add(card);
				}
			}
			else {
				for (Card card: CardsStorage.getAllCards()) {
					if (filter.match(card))
						filteredCards.add(card);
				}
			}
			this.cardGrid.loadCards(new CardsView(filteredCards), (SortBy) cbSortBy.getSelectedItem(), bigCard, null);
		}
		finally {
			setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		}
	}

	public CardGrid getCardsList() {
		return this.cardGrid;
	}

	public Card getCard(UUID cardId) {
		if (!cards.isEmpty()) {
			for (Card card: cards) {
				if (card.getId().equals(cardId))
					return card;
			}
		}
		else {
			for (Card card: CardsStorage.getAllCards()) {
				if (card.getId().equals(cardId))
					return card;
			}
		}
		return null;
	}

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        tbColor = new javax.swing.JToolBar();
        rdoRed = new javax.swing.JRadioButton();
        rdoGreen = new javax.swing.JRadioButton();
        rdoBlue = new javax.swing.JRadioButton();
        rdoBlack = new javax.swing.JRadioButton();
        rdoWhite = new javax.swing.JRadioButton();
        rdoColorless = new javax.swing.JRadioButton();
        cbExpansionSet = new javax.swing.JComboBox();
        btnBooster = new javax.swing.JButton();
        btnClear = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        cardGrid = new mage.client.cards.CardGrid();
        tbTypes = new javax.swing.JToolBar();
        rdoLand = new javax.swing.JRadioButton();
        rdoCreatures = new javax.swing.JRadioButton();
        rdoArtifacts = new javax.swing.JRadioButton();
        rdoEnchantments = new javax.swing.JRadioButton();
        rdoInstants = new javax.swing.JRadioButton();
        rdoSorceries = new javax.swing.JRadioButton();
        rdoPlaneswalkers = new javax.swing.JRadioButton();
        cbSortBy = new javax.swing.JComboBox();

        tbColor.setFloatable(false);
        tbColor.setRollover(true);

        rdoRed.setSelected(true);
        rdoRed.setText("Red ");
        rdoRed.setFocusable(false);
        rdoRed.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        rdoRed.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        rdoRed.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdoRedActionPerformed(evt);
            }
        });
        tbColor.add(rdoRed);

        rdoGreen.setSelected(true);
        rdoGreen.setText("Green ");
        rdoGreen.setFocusable(false);
        rdoGreen.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        rdoGreen.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        rdoGreen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdoGreenActionPerformed(evt);
            }
        });
        tbColor.add(rdoGreen);

        rdoBlue.setSelected(true);
        rdoBlue.setText("Blue ");
        rdoBlue.setFocusable(false);
        rdoBlue.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        rdoBlue.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        rdoBlue.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdoBlueActionPerformed(evt);
            }
        });
        tbColor.add(rdoBlue);

        rdoBlack.setSelected(true);
        rdoBlack.setText("Black ");
        rdoBlack.setFocusable(false);
        rdoBlack.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        rdoBlack.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        rdoBlack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdoBlackActionPerformed(evt);
            }
        });
        tbColor.add(rdoBlack);

        rdoWhite.setSelected(true);
        rdoWhite.setText("White ");
        rdoWhite.setFocusable(false);
        rdoWhite.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        rdoWhite.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        rdoWhite.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdoWhiteActionPerformed(evt);
            }
        });
        tbColor.add(rdoWhite);

        rdoColorless.setSelected(true);
        rdoColorless.setText("Colorless ");
        rdoColorless.setFocusable(false);
        rdoColorless.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        rdoColorless.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        rdoColorless.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdoColorlessActionPerformed(evt);
            }
        });
        tbColor.add(rdoColorless);

        cbExpansionSet.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbExpansionSet.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbExpansionSetActionPerformed(evt);
            }
        });
        tbColor.add(cbExpansionSet);

        btnBooster.setText("Open Booster");
        btnBooster.setFocusable(false);
        btnBooster.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnBooster.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnBooster.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBoosterActionPerformed(evt);
            }
        });
        tbColor.add(btnBooster);

        btnClear.setText("Clear");
        btnClear.setFocusable(false);
        btnClear.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnClear.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnClear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClearActionPerformed(evt);
            }
        });
        tbColor.add(btnClear);

        jScrollPane1.setViewportView(cardGrid);

        tbTypes.setFloatable(false);
        tbTypes.setRollover(true);

        rdoLand.setSelected(true);
        rdoLand.setFocusable(false);
        rdoLand.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        rdoLand.setLabel("Land ");
        rdoLand.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        rdoLand.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdoLandActionPerformed(evt);
            }
        });
        tbTypes.add(rdoLand);

        rdoCreatures.setSelected(true);
        rdoCreatures.setFocusable(false);
        rdoCreatures.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        rdoCreatures.setLabel("Creatures ");
        rdoCreatures.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        rdoCreatures.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdoCreaturesActionPerformed(evt);
            }
        });
        tbTypes.add(rdoCreatures);

        rdoArtifacts.setSelected(true);
        rdoArtifacts.setText("Artifacts ");
        rdoArtifacts.setFocusable(false);
        rdoArtifacts.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        rdoArtifacts.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        rdoArtifacts.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdoArtifactsActionPerformed(evt);
            }
        });
        tbTypes.add(rdoArtifacts);

        rdoEnchantments.setSelected(true);
        rdoEnchantments.setText("Enchantments ");
        rdoEnchantments.setFocusable(false);
        rdoEnchantments.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        rdoEnchantments.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        rdoEnchantments.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdoEnchantmentsActionPerformed(evt);
            }
        });
        tbTypes.add(rdoEnchantments);

        rdoInstants.setSelected(true);
        rdoInstants.setText("Instants ");
        rdoInstants.setFocusable(false);
        rdoInstants.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        rdoInstants.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        rdoInstants.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdoInstantsActionPerformed(evt);
            }
        });
        tbTypes.add(rdoInstants);

        rdoSorceries.setSelected(true);
        rdoSorceries.setText("Sorceries ");
        rdoSorceries.setFocusable(false);
        rdoSorceries.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        rdoSorceries.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        rdoSorceries.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdoSorceriesActionPerformed(evt);
            }
        });
        tbTypes.add(rdoSorceries);

        rdoPlaneswalkers.setSelected(true);
        rdoPlaneswalkers.setText("Planeswalkers ");
        rdoPlaneswalkers.setFocusable(false);
        rdoPlaneswalkers.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        rdoPlaneswalkers.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        rdoPlaneswalkers.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdoPlaneswalkersActionPerformed(evt);
            }
        });
        tbTypes.add(rdoPlaneswalkers);

        cbSortBy.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbSortBy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbSortByActionPerformed(evt);
            }
        });
        tbTypes.add(cbSortBy);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tbColor, javax.swing.GroupLayout.DEFAULT_SIZE, 917, Short.MAX_VALUE)
            .addComponent(tbTypes, javax.swing.GroupLayout.DEFAULT_SIZE, 917, Short.MAX_VALUE)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 917, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(tbColor, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(tbTypes, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 316, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

	private void rdoGreenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdoGreenActionPerformed
		filter.getColor().setGreen(this.rdoGreen.isSelected());
		filterCards();
	}//GEN-LAST:event_rdoGreenActionPerformed

	private void rdoBlackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdoBlackActionPerformed
		filter.getColor().setBlack(this.rdoBlack.isSelected());
		filterCards();
	}//GEN-LAST:event_rdoBlackActionPerformed

	private void rdoWhiteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdoWhiteActionPerformed
		filter.getColor().setWhite(this.rdoWhite.isSelected());
		filterCards();
	}//GEN-LAST:event_rdoWhiteActionPerformed

	private void rdoRedActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdoRedActionPerformed
		filter.getColor().setRed(this.rdoRed.isSelected());
		filterCards();
	}//GEN-LAST:event_rdoRedActionPerformed

	private void rdoBlueActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdoBlueActionPerformed
		filter.getColor().setBlue(this.rdoBlue.isSelected());
		filterCards();
	}//GEN-LAST:event_rdoBlueActionPerformed

	private void rdoColorlessActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdoColorlessActionPerformed
		filter.setColorless(this.rdoColorless.isSelected());
		filterCards();
	}//GEN-LAST:event_rdoColorlessActionPerformed

	private void rdoLandActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdoLandActionPerformed
		if (this.rdoLand.isSelected())
			filter.getCardType().add(CardType.LAND);
		else
			filter.getCardType().remove(CardType.LAND);
		filterCards();
	}//GEN-LAST:event_rdoLandActionPerformed

	private void rdoCreaturesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdoCreaturesActionPerformed
		if (this.rdoCreatures.isSelected())
			filter.getCardType().add(CardType.CREATURE);
		else
			filter.getCardType().remove(CardType.CREATURE);
		filterCards();
	}//GEN-LAST:event_rdoCreaturesActionPerformed

	private void rdoArtifactsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdoArtifactsActionPerformed
		if (this.rdoArtifacts.isSelected())
			filter.getCardType().add(CardType.ARTIFACT);
		else
			filter.getCardType().remove(CardType.ARTIFACT);
		filterCards();
	}//GEN-LAST:event_rdoArtifactsActionPerformed

	private void rdoEnchantmentsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdoEnchantmentsActionPerformed
		if (this.rdoEnchantments.isSelected())
			filter.getCardType().add(CardType.ENCHANTMENT);
		else
			filter.getCardType().remove(CardType.ENCHANTMENT);
		filterCards();
	}//GEN-LAST:event_rdoEnchantmentsActionPerformed

	private void rdoInstantsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdoInstantsActionPerformed
		if (this.rdoInstants.isSelected())
			filter.getCardType().add(CardType.INSTANT);
		else
			filter.getCardType().remove(CardType.INSTANT);
		filterCards();
	}//GEN-LAST:event_rdoInstantsActionPerformed

	private void rdoSorceriesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdoSorceriesActionPerformed
		if (this.rdoSorceries.isSelected())
			filter.getCardType().add(CardType.SORCERY);
		else
			filter.getCardType().remove(CardType.SORCERY);
		filterCards();
	}//GEN-LAST:event_rdoSorceriesActionPerformed

	private void rdoPlaneswalkersActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdoPlaneswalkersActionPerformed
		if (this.rdoPlaneswalkers.isSelected())
			filter.getCardType().add(CardType.PLANESWALKER);
		else
			filter.getCardType().remove(CardType.PLANESWALKER);
		filterCards();
	}//GEN-LAST:event_rdoPlaneswalkersActionPerformed

	private void cbExpansionSetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbExpansionSetActionPerformed
		filter.getExpansionSetCode().clear();
		if (cbExpansionSet.getSelectedItem() instanceof ExpansionSet) {
			filter.getExpansionSetCode().add(((ExpansionSet)this.cbExpansionSet.getSelectedItem()).getCode());
		}
		filterCards();
	}//GEN-LAST:event_cbExpansionSetActionPerformed

	private void btnClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearActionPerformed
		cards.clear();
		filterCards();
	}//GEN-LAST:event_btnClearActionPerformed

	private void btnBoosterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBoosterActionPerformed
		List<Card> booster = ((ExpansionSet)this.cbExpansionSet.getSelectedItem()).createBooster();
		for (Card card: booster) {
			cards.add(card);
		}
		filterCards();
	}//GEN-LAST:event_btnBoosterActionPerformed

	private void cbSortByActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbSortByActionPerformed
		if (cbSortBy.getSelectedItem() instanceof SortBy)
			this.cardGrid.drawCards((SortBy) cbSortBy.getSelectedItem());
	}//GEN-LAST:event_cbSortByActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBooster;
    private javax.swing.JButton btnClear;
    private mage.client.cards.CardGrid cardGrid;
    private javax.swing.JComboBox cbExpansionSet;
    private javax.swing.JComboBox cbSortBy;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JRadioButton rdoArtifacts;
    private javax.swing.JRadioButton rdoBlack;
    private javax.swing.JRadioButton rdoBlue;
    private javax.swing.JRadioButton rdoColorless;
    private javax.swing.JRadioButton rdoCreatures;
    private javax.swing.JRadioButton rdoEnchantments;
    private javax.swing.JRadioButton rdoGreen;
    private javax.swing.JRadioButton rdoInstants;
    private javax.swing.JRadioButton rdoLand;
    private javax.swing.JRadioButton rdoPlaneswalkers;
    private javax.swing.JRadioButton rdoRed;
    private javax.swing.JRadioButton rdoSorceries;
    private javax.swing.JRadioButton rdoWhite;
    private javax.swing.JToolBar tbColor;
    private javax.swing.JToolBar tbTypes;
    // End of variables declaration//GEN-END:variables

	@Override
	public void componentResized(ComponentEvent e) {
		if (cbSortBy.getSelectedItem() instanceof SortBy)
			this.cardGrid.drawCards((SortBy) cbSortBy.getSelectedItem());
	}

	@Override
	public void componentMoved(ComponentEvent e) {
		if (cbSortBy.getSelectedItem() instanceof SortBy)
			this.cardGrid.drawCards((SortBy) cbSortBy.getSelectedItem());
	}

	@Override
	public void componentShown(ComponentEvent e) {
		if (cbSortBy.getSelectedItem() instanceof SortBy)
			this.cardGrid.drawCards((SortBy) cbSortBy.getSelectedItem());
	}

	@Override
	public void componentHidden(ComponentEvent e) {
		if (cbSortBy.getSelectedItem() instanceof SortBy)
			this.cardGrid.drawCards((SortBy) cbSortBy.getSelectedItem());
	}

}
