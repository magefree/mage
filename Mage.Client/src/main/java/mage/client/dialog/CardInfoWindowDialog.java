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

import static com.sun.java.accessibility.util.AWTEventMonitor.addWindowListener;
import java.awt.Point;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyVetoException;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.SwingUtilities;
import mage.client.cards.BigCard;
import mage.client.util.Config;
import mage.client.util.ImageHelper;
import mage.client.util.SettingsManager;
import mage.client.util.gui.GuiDisplayUtil;
import mage.view.CardsView;
import mage.view.ExileView;
import mage.view.SimpleCardsView;
import org.mage.plugins.card.utils.impl.ImageManagerImpl;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class CardInfoWindowDialog extends MageDialog {

    public static enum ShowType { REVEAL, LOOKED_AT, EXILE, GRAVEYARD, OTHER };
    
    private ShowType showType;
    private boolean positioned;
    private String name;
    
    public CardInfoWindowDialog(ShowType showType, String name) {
        this.name = name;
        this.title = name;
        this.showType = showType;
        this.positioned = false;
        initComponents();
                       
        this.setModal(false);
        switch(this.showType) {
            case LOOKED_AT:
                this.setFrameIcon(new ImageIcon(ImageManagerImpl.getInstance().getLookedAtImage()));
                this.setClosable(true);
                break;
            case REVEAL:
                this.setFrameIcon(new ImageIcon(ImageManagerImpl.getInstance().getRevealedImage()));
                this.setClosable(true);
                break;
            case GRAVEYARD:
                this.setFrameIcon(new ImageIcon(ImageHelper.getImageFromResources("/info/grave.png")));
                this.setIconifiable(false);
                this.setClosable(true);
                this.setDefaultCloseOperation(HIDE_ON_CLOSE);
                addWindowListener(new WindowAdapter() {            
                    @Override
                    public void windowClosing(WindowEvent e) {
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
    }

    public void cleanUp() {
        cards.cleanUp();
    }

    public void loadCards(SimpleCardsView showCards, BigCard bigCard, UUID gameId) {
        cards.loadCards(showCards, bigCard, gameId);
        showAndPositionWindow();
    }
    
    public void loadCards(CardsView showCards, BigCard bigCard, UUID gameId) {
        cards.loadCards(showCards, bigCard, gameId, null);                
        if (showType.equals(ShowType.GRAVEYARD)) {
            setTitle(name + "'s Graveyard (" + showCards.size() + ")");
            this.setTitelBarToolTip(name);  
        }
        showAndPositionWindow();            
    }

    private void showAndPositionWindow() {        
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                int width = CardInfoWindowDialog.this.getWidth();
                int height = CardInfoWindowDialog.this.getHeight();
                if (width > 0 && height > 0) {
                    Point centered = SettingsManager.getInstance().getComponentPosition(width, height);
                    if (!positioned) {
                        positioned = true;
                        int xPos = centered.x / 2;
                        int yPos = centered.y / 2;
                        CardInfoWindowDialog.this.setLocation(xPos, yPos);
                        show();
                    }
                    GuiDisplayUtil.keepComponentInsideScreen(centered.x, centered.y, CardInfoWindowDialog.this);
                }
            }
        });
    }
    
    public void loadCards(ExileView exile, BigCard bigCard, UUID gameId) {
        boolean changed = cards.loadCards(exile, bigCard, gameId, null);
        if (exile.size() > 0) {
            show();
            if (changed) {
                try {
                    this.setIcon(false);
                } catch (PropertyVetoException ex) {
                    Logger.getLogger(CardInfoWindowDialog.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        else {
            this.hideDialog();
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

        cards = new mage.client.cards.Cards();

        setIconifiable(true);
        setResizable(true);

        cards.setPreferredSize(new java.awt.Dimension(Config.dimensions.frameWidth, Config.dimensions.frameHeight + 25));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(cards, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 239, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(cards, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private mage.client.cards.Cards cards;
    // End of variables declaration//GEN-END:variables

}
