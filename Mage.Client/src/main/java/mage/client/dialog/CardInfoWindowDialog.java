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
 * CardInfoWindowDialog.java
 *
 * Created on Feb 1, 2010, 3:00:35 PM
 */
package mage.client.dialog;

import java.awt.Dimension;
import java.awt.Point;
import java.beans.PropertyVetoException;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import javax.swing.ImageIcon;
import javax.swing.SwingUtilities;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
import mage.client.cards.BigCard;
import mage.client.util.GUISizeHelper;
import mage.client.util.ImageHelper;
import mage.client.util.SettingsManager;
import mage.client.util.gui.GuiDisplayUtil;
import mage.constants.CardType;
import mage.view.CardView;
import mage.view.CardsView;
import mage.view.ExileView;
import mage.view.SimpleCardsView;
import org.apache.log4j.Logger;
import org.mage.plugins.card.utils.impl.ImageManagerImpl;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class CardInfoWindowDialog extends MageDialog {

    private static final Logger LOGGER = Logger.getLogger(CardInfoWindowDialog.class);

    public enum ShowType {
        REVEAL, REVEAL_TOP_LIBRARY, LOOKED_AT, EXILE, GRAVEYARD, OTHER
    }

    private final ShowType showType;
    private boolean positioned;
    private final String name;

    public CardInfoWindowDialog(ShowType showType, String name) {
        this.name = name;
        this.title = name;
        this.showType = showType;
        this.positioned = false;
        initComponents();

        this.setModal(false);
        switch (this.showType) {
            case LOOKED_AT:
                this.setFrameIcon(new ImageIcon(ImageManagerImpl.getInstance().getLookedAtImage()));
                this.setClosable(true);
                break;
            case REVEAL:
                this.setFrameIcon(new ImageIcon(ImageManagerImpl.getInstance().getRevealedImage()));
                this.setClosable(true);
                break;
            case REVEAL_TOP_LIBRARY:
                this.setFrameIcon(new ImageIcon(ImageHelper.getImageFromResources("/info/library.png")));
                this.setClosable(true);
                break;
            case GRAVEYARD:
                this.setFrameIcon(new ImageIcon(ImageHelper.getImageFromResources("/info/grave.png")));
                this.setClosable(true);
                this.setDefaultCloseOperation(HIDE_ON_CLOSE);
                addInternalFrameListener(new InternalFrameAdapter() {
                    @Override
                    public void internalFrameClosing(InternalFrameEvent e) {
                        CardInfoWindowDialog.this.hideDialog();
                    }
                });
                break;
            case EXILE:
                this.setFrameIcon(new ImageIcon(ImageManagerImpl.getInstance().getExileImage()));
                break;
            default:
            // no icon yet
        }
        this.setTitelBarToolTip(name);
        setGUISize();

    }

    public void cleanUp() {
        cards.cleanUp();
    }

    @Override
    public void changeGUISize() {
        setGUISize();
        this.validate();
        this.repaint();
    }

    private void setGUISize() {
        cards.setCardDimension(GUISizeHelper.otherZonesCardDimension);
        cards.changeGUISize();
    }

    public void loadCards(ExileView exile, BigCard bigCard, UUID gameId) {
        boolean changed = cards.loadCards(exile, bigCard, gameId, true);
        String titel = name + " (" + exile.size() + ')';
        setTitle(titel);
        this.setTitelBarToolTip(titel);
        if (!exile.isEmpty()) {
            show();
            if (changed) {
                try {
                    this.setIcon(false);
                } catch (PropertyVetoException ex) {
                    LOGGER.error(null, ex);
                }
            }
        } else {
            this.hideDialog();
        }
    }

    public void loadCards(SimpleCardsView showCards, BigCard bigCard, UUID gameId) {
        cards.loadCards(showCards, bigCard, gameId);
        showAndPositionWindow();
    }

    public void loadCards(CardsView showCards, BigCard bigCard, UUID gameId) {
        loadCards(showCards, bigCard, gameId, true);
    }

    public void loadCards(CardsView showCards, BigCard bigCard, UUID gameId, boolean revertOrder) {
        cards.loadCards(showCards, bigCard, gameId, revertOrder);
        if (showType.equals(ShowType.GRAVEYARD)) {
            int qty = qtyCardTypes(showCards);
            String titel = name + "'s Graveyard (" + showCards.size() + ")  -  " + qty + ((qty == 1) ? " Card Type" : " Card Types");
            setTitle(titel);
            this.setTitelBarToolTip(titel);
        }
        showAndPositionWindow();
    }

    @Override
    public void show() {
        if (showType.equals(ShowType.EXILE)) {
            if (cards == null || cards.getNumberOfCards() == 0) {
                return;
            }
        }
        super.show();
        if (positioned) { // check if in frame rectangle
            showAndPositionWindow();
        }
    }

    private void showAndPositionWindow() {
        SwingUtilities.invokeLater(() -> {
            int width = CardInfoWindowDialog.this.getWidth();
            int height = CardInfoWindowDialog.this.getHeight();
            if (width > 0 && height > 0) {
                Point centered = SettingsManager.getInstance().getComponentPosition(width, height);
                if (!positioned) {
                    int xPos = centered.x / 2;
                    int yPos = centered.y / 2;
                    CardInfoWindowDialog.this.setLocation(xPos, yPos);
                    show();
                    positioned = true;
                }
                GuiDisplayUtil.keepComponentInsideFrame(centered.x, centered.y, CardInfoWindowDialog.this);
            }
        });
    }

        private int qtyCardTypes(mage.view.CardsView cardsView){
        Set<String> cardTypesPresent = new LinkedHashSet<String>() {};
        for (CardView card : cardsView.values()){
            List<CardType> cardTypes = card.getCardTypes();
            for (CardType cardType : cardTypes){
                cardTypesPresent.add(cardType.toString());
            }
        }
        if (cardTypesPresent.isEmpty()) return 0;
        else return cardTypesPresent.size();
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        cards = new mage.client.cards.Cards();

        setIconifiable(true);
        setResizable(true);
        setPreferredSize(new Dimension((int) Math.round(GUISizeHelper.otherZonesCardDimension.width * 1.3),
            (int) Math.round(GUISizeHelper.otherZonesCardDimension.height * 1.2)));

    javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
    getContentPane().setLayout(layout);
    layout.setHorizontalGroup(
        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(layout.createSequentialGroup()
            .addComponent(cards, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGap(0, 0, 0))
    );
    layout.setVerticalGroup(
        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(layout.createSequentialGroup()
            .addComponent(cards, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGap(0, 0, 0))
    );

    pack();
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private mage.client.cards.Cards cards;
    // End of variables declaration//GEN-END:variables

}
