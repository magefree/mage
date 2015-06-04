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
package mage.client.deckeditor;

import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.Timer;
import javax.swing.filechooser.FileFilter;
import mage.cards.Card;
import mage.cards.Sets;
import mage.cards.decks.Deck;
import mage.cards.decks.importer.DeckImporter;
import mage.cards.decks.importer.DeckImporterUtil;
import mage.cards.repository.CardInfo;
import mage.cards.repository.CardRepository;
import mage.client.MageFrame;
import mage.client.cards.BigCard;
import mage.client.cards.ICardGrid;
import mage.client.constants.Constants.DeckEditorMode;
import static mage.client.constants.Constants.DeckEditorMode.FREE_BUILDING;
import static mage.client.constants.Constants.DeckEditorMode.LIMITED_BUILDING;
import static mage.client.constants.Constants.DeckEditorMode.SIDEBOARDING;
import mage.client.dialog.AddLandDialog;
import mage.client.plugins.impl.Plugins;
import mage.client.util.Event;
import mage.client.util.Listener;
import mage.client.util.audio.AudioManager;
import mage.components.CardInfoPane;
import mage.game.GameException;
import mage.remote.Session;
import mage.view.CardView;
import mage.view.SimpleCardView;
import org.apache.log4j.Logger;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class DeckEditorPanel extends javax.swing.JPanel {

    private static final Logger logger = Logger.getLogger(DeckEditorPanel.class);
    private final JFileChooser fcSelectDeck;
    private final JFileChooser fcImportDeck;
    private Deck deck = new Deck();
    private boolean isShowCardInfo = false;
    private UUID tableId;
    private DeckEditorMode mode;
    private int timeout;
    private Timer countdown;
    private UpdateDeckTask updateDeckTask;

    /**
     * Creates new form DeckEditorPanel
     */
    public DeckEditorPanel() {
        initComponents();
        fcSelectDeck = new JFileChooser();
        fcSelectDeck.setAcceptAllFileFilterUsed(false);
        fcSelectDeck.addChoosableFileFilter(new DeckFilter());
        fcImportDeck = new JFileChooser();
        fcImportDeck.setAcceptAllFileFilterUsed(false);
        fcImportDeck.addChoosableFileFilter(new ImportFilter());

        deckArea.setOpaque(false);
        jPanel1.setOpaque(false);
        jSplitPane1.setOpaque(false);
        countdown = new Timer(1000,
                new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (--timeout > 0) {
                    setTimeout(timeout);
                } else {
                    if (updateDeckTask != null) {
                        updateDeckTask.cancel(true);
                    }
                    setTimeout(0);
                    countdown.stop();
                    removeDeckEditor();
                }
            }
        });
    }

    /**
     * Free resources so GC can remove unused objects from memory
     */
    public void cleanUp() {
        if (updateDeckTask != null) {
            updateDeckTask.cancel(true);
        }
        if (countdown != null) {
            if (countdown.isRunning()) {
                countdown.stop();
            }
            for (ActionListener al : countdown.getActionListeners()) {
                countdown.removeActionListener(al);
            }
        }
        this.cardSelector.cleanUp();
        this.deckArea.cleanUp();

        this.remove(bigCard);
        this.bigCard = null;
    }

    public void showDeckEditor(DeckEditorMode mode, Deck deck, UUID tableId, int time) {
        if (deck != null) {
            this.deck = deck;
        }
        this.tableId = tableId;
        this.mode = mode;
        this.btnAddLand.setVisible(false);
        
        switch (mode) {
            case LIMITED_BUILDING:
                this.btnAddLand.setVisible(true);
                this.txtTimeRemaining.setVisible(true);
            case SIDEBOARDING:
                this.btnSubmit.setVisible(true);
                if (deck != null) {
                    this.cardSelector.loadSideboard(new ArrayList<>(deck.getSideboard()), this.bigCard);
                }
                // TODO: take from preferences
                this.cardSelector.switchToGrid();

                this.btnExit.setVisible(false);
                this.btnImport.setVisible(false);
                if (!MageFrame.getSession().isTestMode()) {
                    this.btnLoad.setVisible(false);
                }
                this.deckArea.showSideboard(false);
                countdown.stop();
                this.timeout = time;
                setTimeout(timeout);
                if (timeout != 0) {
                    countdown.start();
                    if (updateDeckTask == null || updateDeckTask.isDone()) {
                        updateDeckTask = new UpdateDeckTask(MageFrame.getSession(), tableId, deck);
                        updateDeckTask.execute();
                    }
                }
                break;
            case FREE_BUILDING:
                this.btnSubmit.setVisible(false);
                this.btnAddLand.setVisible(true);
                this.cardSelector.loadCards(this.bigCard);
                //this.cardTableSelector.loadCards(this.bigCard);
                this.btnExit.setVisible(true);
                this.btnImport.setVisible(true);
                if (!MageFrame.getSession().isTestMode()) {
                    this.btnLoad.setVisible(true);
                }
                this.deckArea.showSideboard(true);
                this.txtTimeRemaining.setVisible(false);
                break;
        }
        init();
        this.deckArea.setDeckEditorMode(mode);
    }

    private void init() {
        this.cardSelector.setVisible(true);
        this.jPanel1.setVisible(true);
        for (ICardGrid component : this.cardSelector.getCardGridComponents()) {
            component.clearCardEventListeners();
            component.addCardEventListener(
                    new Listener<Event>() {
                @Override
                public void event(Event event) {                    
                    switch (event.getEventName()) {                        
                        case "double-click":
                            moveSelectorCardToDeck(event);
                            break;
                        case "alt-double-click":
                            if (mode == DeckEditorMode.FREE_BUILDING) { 
                                moveSelectorCardToSideboard(event);
                            } else {
                                // because in match mode selector is used as sideboard the card goes to deck also for shift click
                                moveSelectorCardToDeck(event);
                            }
                            break;
                        case "remove-main":
                            DeckEditorPanel.this.deckArea.getDeckList().handleDoubleClick();
                            break;
                        case "remove-sideboard":
                            DeckEditorPanel.this.deckArea.getSideboardList().handleDoubleClick();
                            break;
                    }
                    refreshDeck();
                }
            });
        }
        this.deckArea.clearDeckEventListeners();
        this.deckArea.addDeckEventListener(
                new Listener<Event>() {
            @Override
            public void event(Event event) {
                if (mode.equals(DeckEditorMode.FREE_BUILDING)) {
                    switch (event.getEventName()) {
                        case "double-click":
                        {
                            SimpleCardView cardView = (SimpleCardView) event.getSource();
                            for (Card card : deck.getCards()) {
                                if (card.getId().equals(cardView.getId())) {
                                    deck.getCards().remove(card);
                                    break;
                                }
                            }
                            hidePopup();
                            refreshDeck();
                            break;
                        }
                        case "alt-double-click":
                        {
                            SimpleCardView cardView = (SimpleCardView) event.getSource();
                            for (Card card : deck.getCards()) {
                                if (card.getId().equals(cardView.getId())) {
                                    deck.getCards().remove(card);
                                    deck.getSideboard().add(card);
                                    break;
                                }
                            }
                            hidePopup();
                            refreshDeck();
                            break;
                        }
                        case "set-number":
                        {
                            setCardNumberToCardsList(event, deck.getCards());
                        }                        
                    }
                } else {
                    // constructing phase or sideboarding during match -> card goes always to sideboard
                    switch (event.getEventName()) {
                        case "double-click":
                        case "alt-double-click":
                        {
                            SimpleCardView cardView = (SimpleCardView) event.getSource();
                            for (Card card : deck.getCards()) {
                                if (card.getId().equals(cardView.getId())) {
                                    deck.getCards().remove(card);
                                    deck.getSideboard().add(card);
                                    cardSelector.loadSideboard(new ArrayList<>(deck.getSideboard()), getBigCard());
                                    break;
                                }
                            }       
                            hidePopup();
                            refreshDeck();
                            break;
                        }
                    }                    
                }
            }
        });
        this.deckArea.addSideboardEventListener(
                new Listener<Event>() {
            @Override
            public void event(Event event) {
                if (mode.equals(DeckEditorMode.FREE_BUILDING)) {
                    // normal edit mode
                    switch (event.getEventName()) {
                        case "double-click":
                            // remove card from sideboard (don't add it to deck)
                            SimpleCardView cardView = (SimpleCardView) event.getSource();
                            for (Card card : deck.getSideboard()) {
                                if (card.getId().equals(cardView.getId())) {
                                    deck.getSideboard().remove(card);
                                    break;
                                }
                            }   
                            hidePopup();
                            refreshDeck();
                            break;
                        case "alt-double-click":
                            // remove card from sideboard
                            cardView = (SimpleCardView) event.getSource();
                            for (Card card : deck.getSideboard()) {
                                if (card.getId().equals(cardView.getId())) {
                                    deck.getSideboard().remove(card);
                                    deck.getCards().add(card);
                                    break;
                                }
                            }   
                            hidePopup();
                            refreshDeck();                            
                            break;
                        case "set-number":
                        {
                            setCardNumberToCardsList(event, deck.getSideboard());
                        }                             
                    }                    
                } else {
                    // construct phase or sideboarding during match
                    switch (event.getEventName()) {
                        case "double-click":
                        case "alt-double-click":
                            SimpleCardView cardView = (SimpleCardView) event.getSource();
                            for (Card card : deck.getSideboard()) {
                                if (card.getId().equals(cardView.getId())) {
                                    deck.getSideboard().remove(card);
                                    deck.getCards().add(card);
                                    break;
                                }
                            }   hidePopup();
                            refreshDeck();
                            break;
                    }
                }
            }
        });
        refreshDeck();

        this.setVisible(true);
        this.repaint();
    }

    private void setCardNumberToCardsList(Event event, Set<Card> cards) {
        CardView cardView = (CardView) event.getSource();
        int numberToSet = event.getNumber();
        int cardsFound = 0;
        List<Card> toDelete = new ArrayList<>();
        for (Card card : cards) {
            if (card.getName().equals(cardView.getName()) 
                    && card.getCardNumber() == cardView.getCardNumber() 
                    && card.getExpansionSetCode().equals(cardView.getExpansionSetCode())) {
                cardsFound++;
                if (cardsFound > numberToSet) {
                    toDelete.add(card);

                }
            }
        }                            
        if (toDelete.isEmpty()) {
            // add cards
            CardInfo cardInfo = CardRepository.instance.findCard(cardView.getExpansionSetCode(), cardView.getCardNumber());
            for (int i = cardsFound; i < numberToSet; i++) {
                cards.add(cardInfo.getMockCard());
            }                                     
        } else {
            // remove cards 
            for (Card card: toDelete) {
                cards.remove(card);
            }
        }
        hidePopup();
        refreshDeck();
    }
    
    private void moveSelectorCardToDeck(Event event) {
        SimpleCardView cardView = (SimpleCardView) event.getSource();
        CardInfo cardInfo = CardRepository.instance.findCard(cardView.getExpansionSetCode(), cardView.getCardNumber());
        Card card = null;
        if (mode == DeckEditorMode.SIDEBOARDING || mode == DeckEditorMode.LIMITED_BUILDING) {
            Iterator sideboard = deck.getSideboard().iterator();
            while (sideboard.hasNext()) {
                card = (Card) sideboard.next();
                if (card.getId().equals(cardView.getId())) {
                    break;
                }
            }
        } else {
            card = cardInfo != null ? cardInfo.getMockCard() : null;
        }
        if (card != null) {
            deck.getCards().add(card);
            if (mode == DeckEditorMode.SIDEBOARDING || mode == DeckEditorMode.LIMITED_BUILDING) {
                deck.getSideboard().remove(card);
                cardSelector.removeCard(card.getId());
                cardSelector.setCardCount(deck.getSideboard().size());
                cardSelector.refresh();
            }
            if (cardInfoPane instanceof CardInfoPane) {
                ((CardInfoPane) cardInfoPane).setCard(new CardView(card), null);
            }
            hidePopup();
        }        
    }

    private void moveSelectorCardToSideboard(Event event) {
        SimpleCardView cardView = (SimpleCardView) event.getSource();
        CardInfo cardInfo = CardRepository.instance.findCard(cardView.getExpansionSetCode(), cardView.getCardNumber());
        Card card = cardInfo != null ? cardInfo.getMockCard() : null;
        if (card != null) {
            deck.getSideboard().add(card);
        }
        if (cardInfoPane instanceof CardInfoPane) {
            ((CardInfoPane) cardInfoPane).setCard(new CardView(card), null);
        }
        hidePopup();
    }
    
    private void hidePopup() {
        Plugins.getInstance().getActionCallback().mouseExited(null, null);
    }

    public void removeDeckEditor() {
        hidePopup();
        this.cleanUp();

        Component c = this.getParent();
        while (c != null && !(c instanceof DeckEditorPane)) {
            c = c.getParent();
        }
        if (c != null) {
            ((DeckEditorPane) c).removeFrame();
        }
    }

    public DeckEditorMode getDeckEditorMode() {
        return mode;
    }

    private BigCard getBigCard() {
        return this.bigCard;
    }

    private void refreshDeck() {
        try {
            setCursor(new Cursor(Cursor.WAIT_CURSOR));
            this.txtDeckName.setText(deck.getName());
            deckArea.loadDeck(deck, bigCard);
        } finally {
            setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        }
    }

    private void setTimeout(int s) {
        int minute = s / 60;
        int second = s - (minute * 60);
        String text;
        if (minute < 10) {
            text = "0" + Integer.toString(minute) + ":";
        } else {
            text = Integer.toString(minute) + ":";
        }
        if (second < 10) {
            text = text + "0" + Integer.toString(second);
        } else {
            text = text + Integer.toString(second);
        }
        this.txtTimeRemaining.setText(text);
        if (s==60) {
            AudioManager.playOnCountdown1();
        }
    }

    private void initComponents() {

        jSplitPane1 = new javax.swing.JSplitPane();
        cardSelector = new mage.client.deckeditor.CardSelector();
        deckArea = new mage.client.deckeditor.DeckArea();
        jPanel1 = new javax.swing.JPanel();
        bigCard = new mage.client.cards.BigCard();
        txtDeckName = new javax.swing.JTextField();
        lblDeckName = new javax.swing.JLabel();
        btnSave = new javax.swing.JButton();
        btnLoad = new javax.swing.JButton();
        btnNew = new javax.swing.JButton();
        btnExit = new javax.swing.JButton();
        btnImport = new javax.swing.JButton();
        btnSubmit = new javax.swing.JButton();
        btnAddLand = new javax.swing.JButton();
        txtTimeRemaining = new javax.swing.JTextField();

        jSplitPane1.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
        jSplitPane1.setResizeWeight(0.5);
        jSplitPane1.setTopComponent(cardSelector);
        jSplitPane1.setBottomComponent(deckArea);

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                jSplitPane1.setDividerLocation(0.6);
            }
        });

        bigCard.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        cardInfoPane = Plugins.getInstance().getCardInfoPane();
        if (cardInfoPane != null && System.getProperty("testCardInfo") != null) {
            cardInfoPane.setPreferredSize(new Dimension(170, 150));
            cardInfoPane.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(200, 0, 0)));
            isShowCardInfo = true;
        } else {
            cardInfoPane = new JLabel();
            cardInfoPane.setVisible(false);
        }

        lblDeckName.setLabelFor(txtDeckName);
        lblDeckName.setText("Deck Name:");

        btnSave.setText("Save");
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });

        btnLoad.setText("Load");
        btnLoad.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLoadActionPerformed(evt);
            }
        });

        btnNew.setText("New");
        btnNew.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNewActionPerformed(evt);
            }
        });

        btnExit.setText("Exit");
        btnExit.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExitActionPerformed(evt);
            }
        });

        btnImport.setText("Import");
        btnImport.setName("btnImport"); // NOI18N
        btnImport.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnImportActionPerformed(evt);
            }
        });

        btnSubmit.setText("Submit");
        btnSubmit.setName("btnSubmit"); // NOI18N
        btnSubmit.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSubmitActionPerformed(evt);
            }
        });

        btnAddLand.setText("Add Land");
        btnAddLand.setName("btnAddLand"); // NOI18N
        btnAddLand.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddLandActionPerformed(evt);
            }
        });

        txtTimeRemaining.setEditable(false);
        txtTimeRemaining.setForeground(java.awt.Color.red);
        txtTimeRemaining.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtTimeRemaining.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                /*.addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                 .addContainerGap()
                 .addComponent(jLayeredPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 256, Short.MAX_VALUE))*/
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                            .addComponent(lblDeckName)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(txtDeckName, javax.swing.GroupLayout.DEFAULT_SIZE, 189, Short.MAX_VALUE))

                        .addComponent(cardInfoPane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(bigCard, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)

                                .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(btnSave)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnLoad)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnNew)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnExit))
                        .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(btnImport)
                        .addContainerGap()
                        .addComponent(btnAddLand)
                        .addContainerGap()
                        .addComponent(btnSubmit))
                        .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(txtTimeRemaining))
                    )
                .addContainerGap()));
        jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(txtDeckName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(lblDeckName))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(btnSave)
                .addComponent(btnLoad)
                .addComponent(btnNew)
                .addComponent(btnExit))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(btnImport)
                .addComponent(btnAddLand)
                .addComponent(btnSubmit))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(txtTimeRemaining))
                //.addComponent(jLayeredPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, isShowCardInfo ? 30 : 159, Short.MAX_VALUE)
                .addComponent(cardInfoPane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 104, Short.MAX_VALUE)
                .addComponent(bigCard, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 261, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jSplitPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 604, Short.MAX_VALUE)));
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jSplitPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 615, Short.MAX_VALUE));
    }

    private void btnLoadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLoadActionPerformed
        String lastFolder = MageFrame.getPreferences().get("lastDeckFolder", "");
        if (!lastFolder.isEmpty()) {
            fcSelectDeck.setCurrentDirectory(new File(lastFolder));
        }
        int ret = fcSelectDeck.showOpenDialog(this);
        if (ret == JFileChooser.APPROVE_OPTION) {
            File file = fcSelectDeck.getSelectedFile();
            try {
                setCursor(new Cursor(Cursor.WAIT_CURSOR));
                deck = Deck.load(DeckImporterUtil.importDeck(file.getPath()), true, true);
            } catch (GameException ex) {
                JOptionPane.showMessageDialog(MageFrame.getDesktop(), ex.getMessage(), "Error loading deck", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                logger.fatal(ex);
            } finally {
                setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
            refreshDeck();
            try {
                MageFrame.getPreferences().put("lastDeckFolder", file.getCanonicalPath());
            } catch (IOException ex) {
            }
        }
        fcSelectDeck.setSelectedFile(null);
    }//GEN-LAST:event_btnLoadActionPerformed

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        String lastFolder = MageFrame.getPreferences().get("lastDeckFolder", "");
        if (!lastFolder.isEmpty()) {
            fcSelectDeck.setCurrentDirectory(new File(lastFolder));
        }
        deck.setName(this.txtDeckName.getText());
        int ret = fcSelectDeck.showSaveDialog(this);
        if (ret == JFileChooser.APPROVE_OPTION) {
            File file = fcSelectDeck.getSelectedFile();
            try {
                String fileName = file.getPath();
                if (!fileName.endsWith(".dck")) {
                    fileName += ".dck";
                }
                setCursor(new Cursor(Cursor.WAIT_CURSOR));
                Sets.saveDeck(fileName, deck.getDeckCardLists());
            } catch (Exception ex) {
                logger.fatal(ex);
            } finally {
                setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
            try {
                MageFrame.getPreferences().put("lastDeckFolder", file.getCanonicalPath());
            } catch (IOException ex) {
            }
        }
    }//GEN-LAST:event_btnSaveActionPerformed

    private void btnNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNewActionPerformed
        if (mode == DeckEditorMode.SIDEBOARDING || mode == DeckEditorMode.LIMITED_BUILDING) {
            for (Card card : deck.getCards()) {
                deck.getSideboard().add(card);
            }
            deck.getCards().clear();
            cardSelector.loadSideboard(new ArrayList<>(deck.getSideboard()), this.bigCard);
        } else {
            deck = new Deck();
        }
        refreshDeck();
    }//GEN-LAST:event_btnNewActionPerformed

    private void btnExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExitActionPerformed
        removeDeckEditor();
    }//GEN-LAST:event_btnExitActionPerformed

    private void btnImportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnImportActionPerformed
        String lastFolder = MageFrame.getPreferences().get("lastImportFolder", "");
        if (!lastFolder.isEmpty()) {
            fcImportDeck.setCurrentDirectory(new File(lastFolder));
        }
        int ret = fcImportDeck.showOpenDialog(this);
        if (ret == JFileChooser.APPROVE_OPTION) {
            File file = fcImportDeck.getSelectedFile();
            try {
                setCursor(new Cursor(Cursor.WAIT_CURSOR));
                DeckImporter importer = DeckImporterUtil.getDeckImporter(file.getPath());
                if (importer != null) {
                    deck = Deck.load(importer.importDeck(file.getPath()));
                    String errors = importer.getErrors();
                    if (!errors.isEmpty()) {
                        JOptionPane.showMessageDialog(MageFrame.getDesktop(), errors, "Error importing deck", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(MageFrame.getDesktop(), "Unknown deck format", "Error importing deck", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception ex) {
                logger.fatal(ex);
            } finally {
                setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
            refreshDeck();
            try {
                if (file != null) {
                    MageFrame.getPreferences().put("lastImportFolder", file.getCanonicalPath());
                }
            } catch (IOException ex) {
            }
        }
        fcImportDeck.setSelectedFile(null);
    }//GEN-LAST:event_btnImportActionPerformed

    private void btnSubmitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSubmitActionPerformed
        if (updateDeckTask != null) {
            updateDeckTask.cancel(true);
        }

        if (MageFrame.getSession().submitDeck(tableId, deck.getDeckCardLists())) {
            removeDeckEditor();
        }
    }//GEN-LAST:event_btnSubmitActionPerformed

    private void btnAddLandActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddLandActionPerformed
        AddLandDialog addLand = new AddLandDialog();
        addLand.showDialog(deck, mode);
        refreshDeck();
    }//GEN-LAST:event_btnAddLandActionPerformed
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private mage.client.cards.BigCard bigCard;
    private javax.swing.JButton btnExit;
    private javax.swing.JButton btnImport;
    private javax.swing.JButton btnLoad;
    private javax.swing.JButton btnNew;
    private javax.swing.JButton btnSave;
    private mage.client.deckeditor.CardSelector cardSelector;
    private mage.client.deckeditor.DeckArea deckArea;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JLabel lblDeckName;
    private javax.swing.JTextField txtDeckName;
    private javax.swing.JButton btnSubmit;
    private javax.swing.JButton btnAddLand;
    private JComponent cardInfoPane;
    private javax.swing.JTextField txtTimeRemaining;
    // End of variables declaration//GEN-END:variables
}

class DeckFilter extends FileFilter {

    @Override
    public boolean accept(File f) {
        if (f.isDirectory()) {
            return true;
        }

        String ext = null;
        String s = f.getName();
        int i = s.lastIndexOf('.');

        if (i > 0 && i < s.length() - 1) {
            ext = s.substring(i + 1).toLowerCase();
        }
        return (ext == null) ? false : ext.equals("dck");
    }

    @Override
    public String getDescription() {
        return "Deck Files";
    }
}

class ImportFilter extends FileFilter {

    @Override
    public boolean accept(File f) {
        if (f.isDirectory()) {
            return true;
        }

        String ext = null;
        String s = f.getName();
        int i = s.lastIndexOf('.');

        if (i > 0 && i < s.length() - 1) {
            ext = s.substring(i + 1).toLowerCase();
        }
        if (ext != null) {
            if (ext.toLowerCase().equals("dec") || ext.toLowerCase().equals("mwdeck") || ext.toLowerCase().equals("txt")) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String getDescription() {
        return "*.dec | *.mwDeck | *.txt";
    }
}

class UpdateDeckTask extends SwingWorker<Void, Void> {

    private static final Logger logger = Logger.getLogger(UpdateDeckTask.class);
    private final Session session;
    private final UUID tableId;
    private final Deck deck;

    UpdateDeckTask(Session session, UUID tableId, Deck deck) {
        this.session = session;
        this.tableId = tableId;
        this.deck = deck;
    }

    @Override
    protected Void doInBackground() throws Exception {
        while (!isCancelled()) {
            session.updateDeck(tableId, deck.getDeckCardLists());
            Thread.sleep(5000);
        }
        return null;
    }

    @Override
    protected void done() {
        try {
            get();
        } catch (InterruptedException | ExecutionException ex) {
            logger.fatal("Update Matches Task error", ex);
        } catch (CancellationException ex) {
        }
    }
}
