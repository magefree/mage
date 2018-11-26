package mage.client.deckeditor;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.*;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import mage.cards.Card;
import mage.cards.Sets;
import mage.cards.decks.Deck;
import mage.cards.decks.DeckCardLists;
import mage.cards.decks.importer.DeckImporter;
import mage.cards.decks.importer.DeckImporterUtil;
import mage.cards.repository.CardInfo;
import mage.cards.repository.CardRepository;
import mage.client.MageFrame;
import mage.client.SessionHandler;
import mage.client.cards.BigCard;
import mage.client.cards.ICardGrid;
import mage.client.constants.Constants.DeckEditorMode;
import mage.client.deck.generator.DeckGenerator;
import mage.client.deck.generator.DeckGenerator.DeckGeneratorException;
import mage.client.dialog.AddLandDialog;
import mage.client.dialog.PreferencesDialog;
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
 * @author BetaSteward_at_googlemail.com
 */
public class DeckEditorPanel extends javax.swing.JPanel {

    private static final Logger logger = Logger.getLogger(DeckEditorPanel.class);
    private final JFileChooser fcSelectDeck;
    private final JFileChooser fcImportDeck;
    private Deck deck = new Deck();
    private final Map<UUID, Card> temporaryCards = new HashMap<>(); // Cards dragged out of one part of the view into another
    private boolean isShowCardInfo = false;
    private UUID tableId;
    private DeckEditorMode mode;
    private int timeout;
    private javax.swing.Timer countdown;
    private UpdateDeckTask updateDeckTask;
    private int timeToSubmit = -1;

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
        restoreDividerLocationsAndDeckAreaSettings();
        countdown = new javax.swing.Timer(1000,
                e -> {
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
                });

        // Set up tracking to save the deck editor settings when the deck editor is hidden.
        addHierarchyListener((HierarchyEvent e) -> {
            if ((e.getChangeFlags() & HierarchyEvent.SHOWING_CHANGED) != 0) {
                if (!isShowing()) {
                    saveDividerLocationsAndDeckAreaSettings();
                }
            }
        });
    }

    /**
     * Free resources so GC can remove unused objects from memory
     */
    public void cleanUp() {
        saveDividerLocationsAndDeckAreaSettings();
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

    private void saveDividerLocationsAndDeckAreaSettings() {
        PreferencesDialog.saveValue(PreferencesDialog.KEY_EDITOR_HORIZONTAL_DIVIDER_LOCATION, Integer.toString(jSplitPane1.getDividerLocation()));
        PreferencesDialog.saveValue(PreferencesDialog.KEY_EDITOR_DECKAREA_SETTINGS, this.deckArea.saveSettings().toString());
    }

    private void restoreDividerLocationsAndDeckAreaSettings() {
        // Load horizontal split position setting
        String dividerLocation = PreferencesDialog.getCachedValue(PreferencesDialog.KEY_EDITOR_HORIZONTAL_DIVIDER_LOCATION, "");
        if (!dividerLocation.isEmpty()) {
            jSplitPane1.setDividerLocation(Integer.parseInt(dividerLocation));
        }

        // Load deck area settings
        this.deckArea.loadSettings(
                DeckArea.Settings.parse(
                        PreferencesDialog.getCachedValue(PreferencesDialog.KEY_EDITOR_DECKAREA_SETTINGS, "")));
    }

    public void changeGUISize() {
        this.cardSelector.changeGUISize();
        this.deckArea.changeGUISize();
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
            // Fall through to sideboarding
            case SIDEBOARDING:
                this.btnSubmit.setVisible(true);
                this.btnSubmitTimer.setVisible(true);
                if (mode == DeckEditorMode.SIDEBOARDING) {
                    this.deckArea.setOrientation(/*limitedBuildingOrientation = */false);
                } else /*(if (mode == LIMITED_BUILDING)*/ {
                    this.deckArea.setOrientation(/*limitedBuildingOrientation = */true);
                }
                this.cardSelector.setVisible(false);
                this.btnExit.setVisible(false);
                this.btnImport.setVisible(false);
                this.btnGenDeck.setVisible(false);
                if (!SessionHandler.isTestMode()) {
                    this.btnLoad.setVisible(false);
                }
                this.deckArea.showSideboard(true);
                countdown.stop();
                this.timeout = time;
                setTimeout(timeout);
                if (timeout != 0) {
                    countdown.start();
                    if (updateDeckTask == null || updateDeckTask.isDone()) {
                        updateDeckTask = new UpdateDeckTask(SessionHandler.getSession(), tableId, deck);
                        updateDeckTask.execute();
                    }
                }
                break;
            case FREE_BUILDING:
                this.deckArea.setOrientation(/*limitedBuildingOrientation = */false);
                this.btnSubmit.setVisible(false);
                this.btnSubmitTimer.setVisible(false);
                this.btnAddLand.setVisible(true);
                this.cardSelector.setVisible(true);
                this.cardSelector.loadCards(this.bigCard);
                //this.cardTableSelector.loadCards(this.bigCard);
                this.btnExit.setVisible(true);
                this.btnImport.setVisible(true);
                this.btnGenDeck.setVisible(true);
                if (!SessionHandler.isTestMode()) {
                    this.btnLoad.setVisible(true);
                }
                this.deckArea.showSideboard(true);
                this.txtTimeRemaining.setVisible(false);
                break;
            case VIEW_LIMITED_DECK:
                this.btnExit.setVisible(true);
                this.btnSave.setVisible(true);
                this.btnAddLand.setVisible(false);
                this.btnGenDeck.setVisible(false);
                this.btnImport.setVisible(false);
                this.btnLoad.setVisible(false);
                this.btnNew.setVisible(false);
                this.btnSubmit.setVisible(false);
                this.btnSubmitTimer.setVisible(false);
                this.cardSelector.loadCards(this.bigCard);
                this.cardSelector.setVisible(false);
                this.deckArea.setOrientation(/*limitedBuildingOrientation = */true);
                this.deckArea.showSideboard(true);
                this.lblDeckName.setVisible(false);
                this.txtDeckName.setVisible(false);
                this.txtTimeRemaining.setVisible(false);
                break;
        }
        init();
        this.deckArea.setDeckEditorMode(mode);
    }

    private Card retrieveTemporaryCard(SimpleCardView cardView) {
        Card card = temporaryCards.get(cardView.getId());
        if (card == null) {
            // Need to make a new card
            Logger.getLogger(DeckEditorPanel.class).info("Retrieve " + cardView.getCardNumber() + " Failed");
            card = CardRepository.instance.findCard(cardView.getExpansionSetCode(), cardView.getCardNumber()).getCard();
        } else {
            // Only need a temporary card once
            temporaryCards.remove(cardView.getId());
        }
        return card;
    }

    private void storeTemporaryCard(Card card) {
        temporaryCards.put(card.getId(), card);
    }

    private void init() {
        //this.cardSelector.setVisible(true);
        this.jPanel1.setVisible(true);
        for (ICardGrid component : this.cardSelector.getCardGridComponents()) {
            component.clearCardEventListeners();
            component.addCardEventListener(
                    (Listener<Event>) event -> {
                        switch (event.getEventType()) {
                            case DOUBLE_CLICK:
                                moveSelectorCardToDeck(event);
                                break;
                            case ALT_DOUBLE_CLICK:
                                if (mode == DeckEditorMode.FREE_BUILDING) {
                                    moveSelectorCardToSideboard(event);
                                } else {
                                    // because in match mode selector is used as sideboard the card goes to deck also for shift click
                                    moveSelectorCardToDeck(event);
                                }
                                break;
                            case REMOVE_MAIN:
                                DeckEditorPanel.this.deckArea.getDeckList().removeSelection();
                                break;
                            case REMOVE_SIDEBOARD:
                                DeckEditorPanel.this.deckArea.getSideboardList().removeSelection();
                                break;
                        }
                        refreshDeck();
                    });
        }
        this.deckArea.clearDeckEventListeners();
        this.deckArea.addDeckEventListener(
                (Listener<Event>) event -> {
                    if (mode == DeckEditorMode.FREE_BUILDING) {
                        switch (event.getEventType()) {
                            case DOUBLE_CLICK: {
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
                            case ALT_DOUBLE_CLICK: {
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
                            case SET_NUMBER: {
                                setCardNumberToCardsList(event, deck.getCards());
                                break;
                            }
                            case REMOVE_SPECIFIC_CARD: {
                                SimpleCardView cardView = (SimpleCardView) event.getSource();
                                for (Card card : deck.getCards()) {
                                    if (card.getId().equals(cardView.getId())) {
                                        deck.getCards().remove(card);
                                        storeTemporaryCard(card);
                                        break;
                                    }
                                }
                                break;
                            }
                            case ADD_SPECIFIC_CARD: {
                                SimpleCardView cardView = (CardView) event.getSource();
                                deck.getCards().add(retrieveTemporaryCard(cardView));
                                break;
                            }
                        }
                    } else {
                        // constructing phase or sideboarding during match -> card goes always to sideboard
                        switch (event.getEventType()) {
                            case DOUBLE_CLICK:
                            case ALT_DOUBLE_CLICK: {
                                SimpleCardView cardView = (SimpleCardView) event.getSource();
                                for (Card card : deck.getCards()) {
                                    if (card.getId().equals(cardView.getId())) {
                                        deck.getCards().remove(card);
                                        deck.getSideboard().add(card);
                                        cardSelector.loadSideboard(new ArrayList<>(deck.getSideboard()), this.bigCard);
                                        break;
                                    }
                                }
                                hidePopup();
                                refreshDeck();
                                break;
                            }
                            case REMOVE_SPECIFIC_CARD: {
                                SimpleCardView cardView = (SimpleCardView) event.getSource();
                                for (Card card : deck.getCards()) {
                                    if (card.getId().equals(cardView.getId())) {
                                        deck.getCards().remove(card);
                                        storeTemporaryCard(card);
                                        break;
                                    }
                                }
                                break;
                            }
                            case ADD_SPECIFIC_CARD: {
                                SimpleCardView cardView = (CardView) event.getSource();
                                deck.getCards().add(retrieveTemporaryCard(cardView));
                                break;
                            }
                        }
                    }
                });
        this.deckArea.clearSideboardEventListeners();
        this.deckArea.addSideboardEventListener(
                (Listener<Event>) event -> {
                    if (mode == DeckEditorMode.FREE_BUILDING) {
                        // normal edit mode
                        switch (event.getEventType()) {
                            case DOUBLE_CLICK:
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
                            case ALT_DOUBLE_CLICK:
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
                            case SET_NUMBER: {
                                setCardNumberToCardsList(event, deck.getSideboard());
                                break;
                            }
                            case REMOVE_SPECIFIC_CARD: {
                                cardView = (SimpleCardView) event.getSource();
                                for (Card card : deck.getSideboard()) {
                                    if (card.getId().equals(cardView.getId())) {
                                        deck.getSideboard().remove(card);
                                        storeTemporaryCard(card);
                                        break;
                                    }
                                }
                                break;
                            }
                            case ADD_SPECIFIC_CARD: {
                                cardView = (CardView) event.getSource();
                                deck.getSideboard().add(retrieveTemporaryCard(cardView));
                                break;
                            }
                        }
                    } else {
                        // construct phase or sideboarding during match
                        switch (event.getEventType()) {
                            case REMOVE_SPECIFIC_CARD: {
                                SimpleCardView cardView = (SimpleCardView) event.getSource();
                                for (Card card : deck.getSideboard()) {
                                    if (card.getId().equals(cardView.getId())) {
                                        deck.getSideboard().remove(card);
                                        storeTemporaryCard(card);
                                        break;
                                    }
                                }
                                break;
                            }
                            case ADD_SPECIFIC_CARD: {
                                SimpleCardView cardView = (CardView) event.getSource();
                                deck.getSideboard().add(retrieveTemporaryCard(cardView));
                                break;
                            }
                            case DOUBLE_CLICK:
                            case ALT_DOUBLE_CLICK:
                                SimpleCardView cardView = (SimpleCardView) event.getSource();
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
        java.util.List<Card> toDelete = new ArrayList<>();
        for (Card card : cards) {
            if (card.getName().equals(cardView.getName())
                    && Objects.equals(card.getCardNumber(), cardView.getCardNumber())
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
            for (Card card : toDelete) {
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
            for (Object o : deck.getSideboard()) {
                card = (Card) o;
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
        Plugins.instance.getActionCallback().mouseExited(null, null);
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
        refreshDeck(false);
    }

    private void refreshDeck(boolean useLayout) {
        try {
            setCursor(new Cursor(Cursor.WAIT_CURSOR));
            this.txtDeckName.setText(deck.getName());
            deckArea.loadDeck(deck, useLayout, bigCard);
        } finally {
            setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        }
    }

    private void setTimeout(int s) {
        int minute = s / 60;
        int second = s - (minute * 60);
        String text;
        if (minute < 10) {
            text = '0' + Integer.toString(minute) + ':';
        } else {
            text = Integer.toString(minute) + ':';
        }
        if (second < 10) {
            text = text + '0' + Integer.toString(second);
        } else {
            text = text + Integer.toString(second);
        }
        this.txtTimeRemaining.setText(text);
        if (s == 60) {
            AudioManager.playOnCountdown1();
        }
        if (timeToSubmit > 0) {
            timeToSubmit--;
            btnSubmitTimer.setText("Submit (" + timeToSubmit + ')');
            btnSubmitTimer.setToolTipText("Submit your deck in " + timeToSubmit + " seconds!");
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
        btnSubmitTimer = new javax.swing.JButton();
        btnAddLand = new javax.swing.JButton();
        btnGenDeck = new javax.swing.JButton();
        txtTimeRemaining = new javax.swing.JTextField();

        jSplitPane1.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
        jSplitPane1.setResizeWeight(0.5);
        jSplitPane1.setTopComponent(cardSelector);
        jSplitPane1.setBottomComponent(deckArea);

        bigCard.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        cardInfoPane = Plugins.instance.getCardInfoPane();
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
        btnSave.addActionListener(evt -> btnSaveActionPerformed(evt));

        btnLoad.setText("Load");
        btnLoad.addActionListener(evt -> btnLoadActionPerformed(evt));

        btnNew.setText("New");
        btnNew.addActionListener(evt -> btnNewActionPerformed(evt));

        btnExit.setText("Exit");
        btnExit.addActionListener(evt -> btnExitActionPerformed(evt));

        btnImport.setText("Import");
        btnImport.setName("btnImport"); // NOI18N
        btnImport.addActionListener(evt -> {
            Object[] options = {"File", "Clipboard", "Append from Clipboard"};

            int n = JOptionPane.showOptionDialog(MageFrame.getDesktop(),
                    "Where would you like to import from?",
                    "Deck import",
                    JOptionPane.YES_NO_CANCEL_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    options,
                    options[0]
            );

            logger.info(n);

            switch (n) {
                case 0:
                    btnImportActionPerformed(evt);
                    break;
                case 1:
                    btnImportFromClipboardActionPerformed(evt);
                    break;
                case 2:
                    btnImportFromClipboardActionWAppendPerformed(evt);
                    break;
            }
        });

        btnSubmit.setText("Submit");
        btnSubmitTimer.setToolTipText("Submit your deck now!");
        btnSubmit.setName("btnSubmit"); // NOI18N
        btnSubmit.addActionListener(evt -> btnSubmitActionPerformed(evt));

        btnSubmitTimer.setText("Submit (60s)");
        btnSubmitTimer.setToolTipText("Submit your deck in one minute!");
        btnSubmitTimer.setName("btnSubmitTimer");
        btnSubmitTimer.addActionListener(evt -> btnSubmitTimerActionPerformed(evt));

        btnAddLand.setText("Add Land");
        btnAddLand.setName("btnAddLand"); // NOI18N
        btnAddLand.addActionListener(evt -> btnAddLandActionPerformed(evt));

        btnGenDeck.setText("Generate");
        btnGenDeck.setName("btnGenDeck");
        btnGenDeck.addActionListener(evt -> btnGenDeckActionPerformed(evt));
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
                                                .addComponent(btnGenDeck)
                                                .addContainerGap()
                                                .addComponent(btnAddLand)
                                                .addContainerGap()
                                                .addComponent(btnSubmit)
                                                .addContainerGap()
                                                .addComponent(btnSubmitTimer))
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
                                        .addComponent(btnGenDeck)
                                        .addComponent(btnAddLand)
                                        .addComponent(btnSubmit)
                                        .addComponent(btnSubmitTimer))
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

    private void processAndShowImportErrors(StringBuilder errorMessages) {
        // show up errors list
        if (errorMessages.length() > 0) {
            String mes = "Founded problems with deck: \n\n" + errorMessages.toString();
            JOptionPane.showMessageDialog(MageFrame.getDesktop(), mes.substring(0, Math.min(1000, mes.length())), "Errors while loading deck", JOptionPane.WARNING_MESSAGE);
        }
    }

    /**
     * @param evt ActionEvent
     */
    private void btnImportFromClipboardActionPerformed(ActionEvent evt) {
        final DeckImportFromClipboardDialog dialog = new DeckImportFromClipboardDialog();
        dialog.pack();
        dialog.setVisible(true);

        dialog.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                Deck newDeck = null;
                StringBuilder errorMessages = new StringBuilder();

                MageFrame.getDesktop().setCursor(new Cursor(Cursor.WAIT_CURSOR));
                try {
                    newDeck = Deck.load(DeckImporterUtil.importDeck(dialog.getTmpPath(), errorMessages), true, true);
                    processAndShowImportErrors(errorMessages);

                    if (newDeck != null) {
                        deck = newDeck;
                        refreshDeck();
                    }

                } catch (GameException e1) {
                    JOptionPane.showMessageDialog(MageFrame.getDesktop(), e1.getMessage(), "Error loading deck", JOptionPane.ERROR_MESSAGE);
                } finally {
                    MageFrame.getDesktop().setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                }
            }
        });
    }

    /**
     * @param evt ActionEvent
     */
    private void btnImportFromClipboardActionWAppendPerformed(ActionEvent evt) {
        final DeckImportFromClipboardDialog dialog = new DeckImportFromClipboardDialog();
        dialog.pack();
        dialog.setVisible(true);

        dialog.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                Deck deckToAppend = null;
                StringBuilder errorMessages = new StringBuilder();

                MageFrame.getDesktop().setCursor(new Cursor(Cursor.WAIT_CURSOR));
                try {
                    deckToAppend = Deck.load(DeckImporterUtil.importDeck(dialog.getTmpPath(), errorMessages), true, true);
                    processAndShowImportErrors(errorMessages);

                    if (deckToAppend != null) {
                        deck = Deck.append(deckToAppend, deck);
                        refreshDeck();
                    }
                } catch (GameException e1) {
                    JOptionPane.showMessageDialog(MageFrame.getDesktop(), e1.getMessage(), "Error loading deck", JOptionPane.ERROR_MESSAGE);
                } finally {
                    MageFrame.getDesktop().setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                }
            }
        });
    }

    private void btnLoadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLoadActionPerformed
        //fcSelectDeck.setCurrentDirectory(new File());
        String lastFolder = MageFrame.getPreferences().get("lastDeckFolder", "");
        if (!lastFolder.isEmpty()) {
            fcSelectDeck.setCurrentDirectory(new File(lastFolder));
        }
        int ret = fcSelectDeck.showOpenDialog(this);
        if (ret == JFileChooser.APPROVE_OPTION) {
            File file = fcSelectDeck.getSelectedFile();
            {
                /**
                 * Work around a JFileChooser bug on Windows 7-10 with JRT 7+ In
                 * the case where the user selects the exact same file as was
                 * previously selected without touching anything else in the
                 * dialog, getSelectedFile() will erroneously return null due to
                 * some combination of our settings.
                 *
                 * We manually sub in the last selected file in this case.
                 */
                if (file == null) {
                    if (!lastFolder.isEmpty()) {
                        file = new File(lastFolder);
                    }
                }
            }

            MageFrame.getDesktop().setCursor(new Cursor(Cursor.WAIT_CURSOR));
            try {
                Deck newDeck = null;
                StringBuilder errorMessages = new StringBuilder();

                newDeck = Deck.load(DeckImporterUtil.importDeck(file.getPath(), errorMessages), true, true);
                processAndShowImportErrors(errorMessages);

                if (newDeck != null) {
                    deck = newDeck;
                    refreshDeck(true);
                }

                // save last deck history
                try {
                    MageFrame.getPreferences().put("lastDeckFolder", file.getCanonicalPath());
                } catch (IOException ex) {
                    logger.error("Error on save last load deck folder: " + ex.getMessage());
                }

            } catch (GameException ex) {
                JOptionPane.showMessageDialog(MageFrame.getDesktop(), ex.getMessage(), "Error loading deck", JOptionPane.ERROR_MESSAGE);
            } finally {
                MageFrame.getDesktop().setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
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
            {
                /**
                 * Work around a JFileChooser bug on Windows 7-10 with JRT 7+ In
                 * the case where the user selects the exact same file as was
                 * previously selected without touching anything else in the
                 * dialog, getSelectedFile() will erroneously return null due to
                 * some combination of our settings.
                 *
                 * We manually sub in the last selected file in this case.
                 */
                if (file == null) {
                    if (!lastFolder.isEmpty()) {
                        file = new File(lastFolder);
                    }
                }
            }
            try {
                String fileName = file.getPath();
                if (!fileName.endsWith(".dck")) {
                    fileName += ".dck";
                }
                MageFrame.getDesktop().setCursor(new Cursor(Cursor.WAIT_CURSOR));
                DeckCardLists cardLists = deck.getDeckCardLists();
                cardLists.setCardLayout(deckArea.getCardLayout());
                cardLists.setSideboardLayout(deckArea.getSideboardLayout());
                Sets.saveDeck(fileName, cardLists);
            } catch (FileNotFoundException ex) {
                JOptionPane.showMessageDialog(MageFrame.getDesktop(), ex.getMessage() + "\nTry ensuring that the selected directory is writable.", "Error saving deck", JOptionPane.ERROR_MESSAGE);
            } finally {
                MageFrame.getDesktop().setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
            try {
                MageFrame.getPreferences().put("lastDeckFolder", file.getCanonicalPath());
            } catch (IOException ex) {
                ex.printStackTrace();
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
            MageFrame.getDesktop().setCursor(new Cursor(Cursor.WAIT_CURSOR));
            try {
                DeckImporter importer = DeckImporterUtil.getDeckImporter(file.getPath());

                if (importer != null) {
                    StringBuilder errorMessages = new StringBuilder();
                    Deck newDeck = null;

                    newDeck = Deck.load(importer.importDeck(file.getPath(), errorMessages));
                    processAndShowImportErrors(errorMessages);

                    if (newDeck != null) {
                        deck = newDeck;
                        refreshDeck();
                    }

                    // save last deck import folder
                    try {
                        MageFrame.getPreferences().put("lastImportFolder", file.getCanonicalPath());
                    } catch (IOException ex) {
                        logger.error("Error on save last used import folder: " + ex.getMessage());
                    }

                } else {
                    JOptionPane.showMessageDialog(MageFrame.getDesktop(), "Unknown deck format", "Error importing deck", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception ex) {
                logger.fatal(ex);
            } finally {
                MageFrame.getDesktop().setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
        }
        fcImportDeck.setSelectedFile(null);
    }//GEN-LAST:event_btnImportActionPerformed

    private void btnSubmitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSubmitActionPerformed
        if (updateDeckTask != null) {
            updateDeckTask.cancel(true);
        }

        if (SessionHandler.submitDeck(tableId, deck.getDeckCardLists())) {
            removeDeckEditor();
        }
    }//GEN-LAST:event_btnSubmitActionPerformed

    private void btnSubmitTimerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSubmitTimerActionPerformed

        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(5);
        timeToSubmit = 60;
        this.btnSubmitTimer.setEnabled(false);

        ScheduledFuture scheduledFuture = scheduledExecutorService.schedule((Callable) () -> {
            if (updateDeckTask != null) {
                updateDeckTask.cancel(true);
            }

            if (SessionHandler.submitDeck(tableId, deck.getDeckCardLists())) {
                removeDeckEditor();
            }
            return null;
        }, 60, TimeUnit.SECONDS);
    }//GEN-LAST:event_btnSubmitTimerActionPerformed

    private void btnAddLandActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddLandActionPerformed
        AddLandDialog addLand = new AddLandDialog();
        addLand.showDialog(deck, mode);
        refreshDeck();
    }//GEN-LAST:event_btnAddLandActionPerformed

    private void btnGenDeckActionPerformed(ActionEvent evt) {
        try {
            MageFrame.getDesktop().setCursor(new Cursor(Cursor.WAIT_CURSOR));
            String path = DeckGenerator.generateDeck();
            deck = Deck.load(DeckImporterUtil.importDeck(path), true, true);
        } catch (GameException ex) {
            JOptionPane.showMessageDialog(MageFrame.getDesktop(), ex.getMessage(), "Error loading generated deck", JOptionPane.ERROR_MESSAGE);
        } catch (DeckGeneratorException ex) {
            JOptionPane.showMessageDialog(MageFrame.getDesktop(), ex.getMessage(), "Generator error", JOptionPane.ERROR_MESSAGE);
        } finally {
            MageFrame.getDesktop().setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        }
        refreshDeck();
    }
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
    private javax.swing.JButton btnSubmitTimer;
    private javax.swing.JButton btnAddLand;
    private javax.swing.JButton btnGenDeck;
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
            ext = s.substring(i + 1).toLowerCase(Locale.ENGLISH);
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
            ext = s.substring(i + 1).toLowerCase(Locale.ENGLISH);
        }
        if (ext != null) {
            if (ext.toLowerCase(Locale.ENGLISH).equals("dec") || ext.toLowerCase(Locale.ENGLISH).equals("mwdeck") || ext.toLowerCase(Locale.ENGLISH).equals("txt") || ext.toLowerCase(Locale.ENGLISH).equals("dek")) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String getDescription() {
        return "*.dec | *.mwDeck | *.txt | *.dek";
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
            SessionHandler.updateDeck(tableId, deck.getDeckCardLists());
            TimeUnit.SECONDS.sleep(5);
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
