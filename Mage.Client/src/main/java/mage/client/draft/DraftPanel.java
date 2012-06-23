/*
 *  Copyright 2011 BetaSteward_at_googlemail.com. All rights reserved.
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
 * DraftPanel.java
 *
 * Created on Jan 7, 2011, 2:15:48 PM
 */

package mage.client.draft;

import mage.client.MageFrame;
import mage.client.plugins.impl.Plugins;
import mage.client.util.CardsViewUtil;
import mage.client.util.Event;
import mage.client.util.Listener;
import mage.remote.Session;
import mage.view.CardsView;
import mage.view.DraftPickView;
import mage.view.DraftView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.UUID;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class DraftPanel extends javax.swing.JPanel {

    private UUID draftId;
    private Session session;
    private Timer countdown;
    private int timeout;
    private boolean picked;

    private static CardsView emptyView = new CardsView();

    /** Creates new form DraftPanel */
    public DraftPanel() {
        initComponents();
        countdown = new Timer(1000,
            new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (--timeout > 0) {
                        setTimeout(Integer.toString(timeout));
                    }
                    else {
                        setTimeout("0");
                        countdown.stop();
                    }
                }
            }
        );
    }

    public synchronized void showDraft(UUID draftId) {
        this.draftId = draftId;
        session = MageFrame.getSession();
        MageFrame.addDraft(draftId, this);
        if (!session.joinDraft(draftId))
            hideDraft();
    }

    public void updateDraft(DraftView draftView) {        
        this.txtPack1.setText(draftView.getSets().get(0));
        this.txtPack2.setText(draftView.getSets().get(1));
        this.txtPack3.setText(draftView.getSets().get(2));
        this.chkPack1.setSelected(draftView.getBoosterNum() > 0);
        this.chkPack2.setSelected(draftView.getBoosterNum() > 1);
        this.chkPack3.setSelected(draftView.getBoosterNum() > 2);
        this.txtCardNo.setText(Integer.toString(draftView.getCardNum()));
    }

    public void loadBooster(DraftPickView draftPickView) {
        draftBooster.loadBooster(CardsViewUtil.convertSimple(draftPickView.getBooster()), bigCard);
        draftPicks.loadCards(CardsViewUtil.convertSimple(draftPickView.getPicks()), bigCard, null);
        this.draftBooster.clearCardEventListeners();
        this.draftBooster.addCardEventListener(
            new Listener<Event> () {
                @Override
                public void event(Event event) {
                    if (event.getEventName().equals("pick-a-card")) {
                        DraftPickView view = session.sendCardPick(draftId, (UUID)event.getSource());
                        if (view != null) {
                            //draftBooster.loadBooster(view.getBooster(), bigCard);
                            draftBooster.loadBooster(emptyView, bigCard);
                            draftPicks.loadCards(CardsViewUtil.convertSimple(view.getPicks()), bigCard, null);
                            Plugins.getInstance().getActionCallback().hidePopup();
                            setMessage("Waiting for other players");
                        }
                    }
                }
            }
        );
        setMessage("Pick a card");
        countdown.stop();
        this.timeout = draftPickView.getTimeout();
        setTimeout(Integer.toString(timeout));
        if (timeout != 0) {
            countdown.start();
        }
    }

    private void setTimeout(String text) {
        this.txtTimeRemaining.setText(text);
    }

    public void hideDraft() {
        Component c = this.getParent();
        while (c != null && !(c instanceof DraftPane)) {
            c = c.getParent();
        }
        if (c != null) {
            ((DraftPane)c).hideFrame();
        }
    }

    protected void setMessage(String message) {
        this.lblMessage.setText(message);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jSeparator1 = new javax.swing.JSeparator();
        jPanel1 = new javax.swing.JPanel();
        bigCard = new mage.client.cards.BigCard();
        lblCardNo = new javax.swing.JLabel();
        lblPack1 = new javax.swing.JLabel();
        lblPack2 = new javax.swing.JLabel();
        lblPack3 = new javax.swing.JLabel();
        txtPack1 = new javax.swing.JTextField();
        txtPack2 = new javax.swing.JTextField();
        txtPack3 = new javax.swing.JTextField();
        txtCardNo = new javax.swing.JTextField();
        chkPack1 = new javax.swing.JCheckBox();
        chkPack2 = new javax.swing.JCheckBox();
        chkPack3 = new javax.swing.JCheckBox();
        txtTimeRemaining = new javax.swing.JTextField();
        lblMessage = new javax.swing.JLabel();
        draftBooster = new mage.client.cards.DraftGrid();
        draftPicks = new mage.client.cards.CardsList();

        jPanel1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        lblCardNo.setText("Card #:");

        lblPack1.setText("Pack 1:");

        lblPack2.setText("Pack 2:");

        lblPack3.setText("Pack 3:");

        txtPack1.setEditable(false);
        txtPack1.setEnabled(false);

        txtPack2.setEditable(false);
        txtPack2.setEnabled(false);

        txtPack3.setEditable(false);
        txtPack3.setEnabled(false);

        txtCardNo.setEditable(false);
        txtCardNo.setEnabled(false);

        txtTimeRemaining.setEditable(false);
        txtTimeRemaining.setForeground(java.awt.Color.red);
        txtTimeRemaining.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtTimeRemaining.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        lblMessage.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(bigCard, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblCardNo))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addComponent(lblPack2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtPack2))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addComponent(lblPack1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtPack1, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addComponent(lblPack3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtCardNo, javax.swing.GroupLayout.DEFAULT_SIZE, 112, Short.MAX_VALUE)
                            .addComponent(txtPack3)
                            .addComponent(txtTimeRemaining, javax.swing.GroupLayout.DEFAULT_SIZE, 112, Short.MAX_VALUE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(chkPack3)
                    .addComponent(chkPack2)
                    .addComponent(chkPack1)))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblMessage, javax.swing.GroupLayout.DEFAULT_SIZE, 236, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblPack1)
                    .addComponent(txtPack1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(chkPack1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblPack2)
                    .addComponent(txtPack2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(chkPack2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblPack3)
                    .addComponent(txtPack3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(chkPack3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblCardNo)
                    .addComponent(txtCardNo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(txtTimeRemaining, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblMessage, javax.swing.GroupLayout.DEFAULT_SIZE, 32, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(bigCard, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        draftBooster.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout draftBoosterLayout = new javax.swing.GroupLayout(draftBooster);
        draftBooster.setLayout(draftBoosterLayout);
        draftBoosterLayout.setHorizontalGroup(
            draftBoosterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 580, Short.MAX_VALUE)
        );
        draftBoosterLayout.setVerticalGroup(
            draftBoosterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 452, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(draftPicks, javax.swing.GroupLayout.DEFAULT_SIZE, 582, Short.MAX_VALUE)
                    .addComponent(draftBooster, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(draftPicks, javax.swing.GroupLayout.DEFAULT_SIZE, 106, Short.MAX_VALUE)
                .addGap(0, 0, 0)
                .addComponent(draftBooster, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private mage.client.cards.BigCard bigCard;
    private javax.swing.JCheckBox chkPack1;
    private javax.swing.JCheckBox chkPack2;
    private javax.swing.JCheckBox chkPack3;
    private mage.client.cards.DraftGrid draftBooster;
    private mage.client.cards.CardsList draftPicks;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel lblCardNo;
    private javax.swing.JLabel lblMessage;
    private javax.swing.JLabel lblPack1;
    private javax.swing.JLabel lblPack2;
    private javax.swing.JLabel lblPack3;
    private javax.swing.JTextField txtCardNo;
    private javax.swing.JTextField txtPack1;
    private javax.swing.JTextField txtPack2;
    private javax.swing.JTextField txtPack3;
    private javax.swing.JTextField txtTimeRemaining;
    // End of variables declaration//GEN-END:variables

}
