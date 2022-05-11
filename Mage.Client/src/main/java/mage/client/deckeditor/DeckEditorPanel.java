package mage.client.deckeditor;

import mage.cards.Card;
import mage.cards.decks.*;
import mage.cards.decks.importer.DeckImporter;
import mage.cards.repository.CardInfo;
import mage.cards.repository.CardRepository;
import mage.client.MageFrame;
import mage.client.SessionHandler;
import mage.client.cards.BigCard;
import mage.client.cards.ICardGrid;
import mage.client.components.LegalityLabel;
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
import mage.util.DeckUtil;
import mage.view.CardView;
import mage.view.SimpleCardView;
import org.apache.log4j.Logger;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.filechooser.FileFilter;
import java.awt.*;
import java.awt.dnd.DropTarget;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.*;
import java.util.concurrent.*;

import static mage.cards.decks.DeckFormats.XMAGE;
import static mage.cards.decks.DeckFormats.XMAGE_INFO;

/**
 * @author BetaSteward_at_googlemail.com, JayDi85, Elandril
 */
public class DeckEditorPanel extends javax.swing.JPanel {

    private static final Logger logger = Logger.getLogger(DeckEditorPanel.class);
    private static final Border LEGALITY_LABEL_BORDER_SELECTED = BorderFactory.createLineBorder(Color.gray, 2);
    private static final Border LEGALITY_LABEL_BORDER_EMPTY = BorderFactory.createEmptyBorder();

    private final JFileChooser fcSelectDeck;
    private final JFileChooser fcImportDeck;
    private final JFileChooser fcExportDeck;
    private final Map<UUID, Card> temporaryCards = new HashMap<>(); // Cards dragged out of one part of the view into another
    private final String LAST_DECK_FOLDER = "lastDeckFolder";
    private Deck deck = new Deck();
    private UUID tableId;
    private DeckEditorMode mode;
    private int timeout;
    private javax.swing.Timer countdown;
    private UpdateDeckTask updateDeckTask;
    private int timeToSubmit = -1;

    public DeckEditorPanel() {
        initComponents();

        fcSelectDeck = new JFileChooser();
        fcSelectDeck.setAcceptAllFileFilterUsed(false);
        fcSelectDeck.addChoosableFileFilter(new DeckFileFilter("dck", "XMage's deck files (*.dck)"));
        fcSelectDeck.addChoosableFileFilter(new DeckFileFilter("dck_info", "XMage's deck files with info (*.dck_info)"));
        fcImportDeck = new JFileChooser();
        fcImportDeck.setAcceptAllFileFilterUsed(false);
        fcImportDeck.addChoosableFileFilter(new ImportFilter());
        fcExportDeck = new JFileChooser();
        fcExportDeck.setAcceptAllFileFilterUsed(false);

        deckArea.setOpaque(false);
        panelLeft.setOpaque(false);
        panelRight.setOpaque(false);
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

        // save editor settings dynamicly on hides (e.g. app close)
        addHierarchyListener((HierarchyEvent e) -> {
            if ((e.getChangeFlags() & HierarchyEvent.SHOWING_CHANGED) != 0) {
                if (!isShowing()) {
                    // It's bugged and called on sideboarding creates too (before load). So:
                    // * for free mode - save here
                    // * for draft/sideboarding - save on cleanup call
                    if (mode == DeckEditorMode.FREE_BUILDING) {
                        saveDividerLocationsAndDeckAreaSettings();
                    }
                }
            }
        });

        // deck legality cards selection
        Arrays.stream(deckLegalityDisplay.getComponents())
                .filter(LegalityLabel.class::isInstance)
                .forEach(c -> {
                    c.addMouseListener(new MouseAdapter() {
                        public void mouseClicked(MouseEvent e) {
                            List<String> cardNames = new ArrayList<>();
                            LegalityLabel label = (LegalityLabel) e.getComponent();
                            label.getValidator().getErrorsList().stream()
                                    .map(DeckValidatorError::getCardName)
                                    .filter(Objects::nonNull)
                                    .forEach(cardNames::add);
                            deckArea.getDeckList().deselectAll();
                            deckArea.getDeckList().selectByName(cardNames);
                            deckArea.getSideboardList().deselectAll();
                            deckArea.getSideboardList().selectByName(cardNames);
                        }

                        @Override
                        public void mouseEntered(MouseEvent e) {
                            LegalityLabel label = (LegalityLabel) e.getComponent();
                            label.setBorder(LEGALITY_LABEL_BORDER_SELECTED);
                        }

                        @Override
                        public void mouseExited(MouseEvent e) {
                            LegalityLabel label = (LegalityLabel) e.getComponent();
                            label.setBorder(LEGALITY_LABEL_BORDER_EMPTY);
                        }
                    });
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
        boolean isLimitedBuildingOrientation = (mode != DeckEditorMode.FREE_BUILDING);
        if (isLimitedBuildingOrientation) {
            PreferencesDialog.saveValue(PreferencesDialog.KEY_EDITOR_HORIZONTAL_DIVIDER_LOCATION_LIMITED, Integer.toString(panelRight.getDividerLocation()));
        } else {
            PreferencesDialog.saveValue(PreferencesDialog.KEY_EDITOR_HORIZONTAL_DIVIDER_LOCATION_NORMAL, Integer.toString(panelRight.getDividerLocation()));
        }

        PreferencesDialog.saveValue(PreferencesDialog.KEY_EDITOR_DECKAREA_SETTINGS, this.deckArea.saveSettings(isLimitedBuildingOrientation).toString());
    }

    private void restoreDividerLocationsAndDeckAreaSettings() {
        String dividerLocation = "";
        boolean isLimitedBuildingOrientation = (mode != DeckEditorMode.FREE_BUILDING);
        if (isLimitedBuildingOrientation) {
            dividerLocation = PreferencesDialog.getCachedValue(PreferencesDialog.KEY_EDITOR_HORIZONTAL_DIVIDER_LOCATION_LIMITED, "");
        } else {
            dividerLocation = PreferencesDialog.getCachedValue(PreferencesDialog.KEY_EDITOR_HORIZONTAL_DIVIDER_LOCATION_NORMAL, "");
        }
        if (!dividerLocation.isEmpty()) {
            panelRight.setDividerLocation(Integer.parseInt(dividerLocation));
        }

        // Load deck area settings
        this.deckArea.loadSettings(
                DeckArea.Settings.parse(PreferencesDialog.getCachedValue(PreferencesDialog.KEY_EDITOR_DECKAREA_SETTINGS, "")),
                isLimitedBuildingOrientation);
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

        // workaround to enable real opaque in scrollbar pane (transparent panel) and remove scroll pane border
        scrollPaneInfo.getViewport().setOpaque(false);
        scrollPaneInfo.setBorder(BorderFactory.createEmptyBorder());
        scrollPaneInfo.setViewportBorder(BorderFactory.createEmptyBorder());

        restoreDividerLocationsAndDeckAreaSettings();
        switch (mode) {
            case LIMITED_BUILDING:
                this.btnAddLand.setVisible(true);
                this.txtTimeRemaining.setVisible(true);
                this.btnLegality.setVisible(false); // legality check available only in free building mode
                // Fall through to sideboarding (no break)
            case SIDEBOARDING:
                this.btnSubmit.setVisible(true);
                this.btnSubmitTimer.setVisible(true);
                /*(if (mode == LIMITED_BUILDING)*/
                /*limitedBuildingOrientation = */
                this.deckArea.setOrientation(/*limitedBuildingOrientation = */mode != DeckEditorMode.SIDEBOARDING);
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
                this.btnLegality.setVisible(true);
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
        this.panelLeft.setVisible(true);

        // TOP AREA: ENABLE ADDING/REMOVING BY DOUBLE CLICKS
        for (ICardGrid component : this.cardSelector.getCardGridComponents()) {
            //component.clearCardEventListeners();
            component.addCardEventListener((Listener<Event>) event -> {
                switch (event.getEventType()) {

                    case CARD_DOUBLE_CLICK: {
                        boolean gameMode = mode != DeckEditorMode.FREE_BUILDING;
                        if (gameMode) {
                            // in game mode selector is used as sideboard, so the card must goes to deck all the time
                            moveSelectorCardToDeck(event);
                        } else {
                            // edit mode
                            if (event.isMouseAltDown()) {
                                moveSelectorCardToSideboard(event);
                            } else {
                                moveSelectorCardToDeck(event);
                            }
                        }
                        break;
                    }

                    case DECK_REMOVE_SELECTION_MAIN: {
                        DeckEditorPanel.this.deckArea.getDeckList().removeSelection();
                        break;
                    }

                    case DECK_REMOVE_SELECTION_SIDEBOARD: {
                        DeckEditorPanel.this.deckArea.getSideboardList().removeSelection();
                        break;
                    }
                }
                refreshDeck();
            });
        }

        // BOTTOM AREA: ENABLE ADDING/REMOVING CARDS in MAINBOARD
        // do not clear event listener - DragCardGrid already have own listeners for cards
        //this.deckArea.clearDeckEventListeners();
        this.deckArea.addDeckEventListener(
                // card manipulation events
                // warning, do not use drag or single click events here, it's already processing by DragCardGrid

                (Listener<Event>) event -> {
                    if (mode == DeckEditorMode.FREE_BUILDING) {
                        switch (event.getEventType()) {
                            case CARD_DOUBLE_CLICK: {
                                SimpleCardView cardView = (SimpleCardView) event.getSource();
                                Card card = deck.findCard(cardView.getId());
                                if (card == null) {
                                    return;
                                }

                                if (event.isMouseAltDown()) {
                                    // ALT + double click: MOVE card from one deck to another
                                    deck.getCards().remove(card);
                                    deck.getSideboard().add(card);
                                } else {
                                    // double click: DELETE card from deck
                                    deck.getCards().remove(card);
                                }

                                hidePopup();
                                refreshDeck();
                                break;
                            }

                            case SET_NUMBER: {
                                setCardNumberToCardsList(event, deck.getCards());
                                break;
                            }

                            case DECK_REMOVE_SPECIFIC_CARD: {
                                SimpleCardView cardView = (SimpleCardView) event.getSource();
                                Card card = deck.findCard(cardView.getId());
                                if (card == null) {
                                    return;
                                }

                                deck.getCards().remove(card);
                                storeTemporaryCard(card);
                                break;
                            }

                            case DECK_ADD_SPECIFIC_CARD: {
                                SimpleCardView cardView = (CardView) event.getSource();
                                deck.getCards().add(retrieveTemporaryCard(cardView));
                                break;
                            }
                        }
                    } else {
                        // constructing phase or sideboarding during match -> card goes always to sideboard
                        switch (event.getEventType()) {

                            case CARD_DOUBLE_CLICK: {
                                SimpleCardView cardView = (SimpleCardView) event.getSource();
                                Card card = deck.findCard(cardView.getId());
                                if (card == null) {
                                    return;
                                }

                                deck.getCards().remove(card);
                                deck.getSideboard().add(card);
                                cardSelector.loadSideboard(new ArrayList<>(deck.getSideboard()), this.bigCard);

                                hidePopup();
                                refreshDeck();
                                break;
                            }

                            case DECK_REMOVE_SPECIFIC_CARD: {
                                SimpleCardView cardView = (SimpleCardView) event.getSource();
                                Card card = deck.findCard(cardView.getId());
                                if (card == null) {
                                    return;
                                }

                                deck.getCards().remove(card);
                                storeTemporaryCard(card);
                                break;
                            }

                            case DECK_ADD_SPECIFIC_CARD: {
                                SimpleCardView cardView = (CardView) event.getSource();
                                deck.getCards().add(retrieveTemporaryCard(cardView));
                                break;
                            }
                        }
                    }
                });

        // BOTTOM AREA: ENABLE ADDING/REMOVING CARDS in SIDEBOARD
        // do not clear event listener - DragCardGrid already have own listeners for cards
        //this.deckArea.clearSideboardEventListeners();
        this.deckArea.addSideboardEventListener(
                // card manipulation events
                (Listener<Event>) event -> {
                    if (mode == DeckEditorMode.FREE_BUILDING) {
                        // DECK EDITOR MODE
                        switch (event.getEventType()) {
                            case CARD_DOUBLE_CLICK: {
                                SimpleCardView cardView = (SimpleCardView) event.getSource();
                                Card card = deck.findSideboardCard(cardView.getId());
                                if (card == null) {
                                    return;
                                }

                                if (event.isMouseAltDown()) {
                                    // ALT + double click: MOVE card from one deck to another
                                    deck.getSideboard().remove(card);
                                    deck.getCards().add(card);
                                } else {
                                    // double click: DELETE card from deck
                                    deck.getSideboard().remove(card);
                                }

                                hidePopup();
                                refreshDeck();
                                break;
                            }

                            case SET_NUMBER: {
                                setCardNumberToCardsList(event, deck.getSideboard());
                                break;
                            }

                            case DECK_REMOVE_SPECIFIC_CARD: {
                                SimpleCardView cardView = (SimpleCardView) event.getSource();
                                Card card = deck.findSideboardCard(cardView.getId());
                                if (card == null) {
                                    return;
                                }

                                deck.getSideboard().remove(card);
                                storeTemporaryCard(card);
                                break;
                            }

                            case DECK_ADD_SPECIFIC_CARD: {
                                SimpleCardView cardView = (CardView) event.getSource();
                                deck.getSideboard().add(retrieveTemporaryCard(cardView));
                                break;
                            }
                        }
                    } else {
                        // GAME MODE
                        // constructing phase or sideboarding during match -> card goes always to main board
                        switch (event.getEventType()) {

                            case DECK_REMOVE_SPECIFIC_CARD: {
                                SimpleCardView cardView = (SimpleCardView) event.getSource();
                                Card card = deck.findSideboardCard(cardView.getId());
                                if (card == null) {
                                    return;
                                }

                                deck.getSideboard().remove(card);
                                storeTemporaryCard(card);
                                break;
                            }

                            case DECK_ADD_SPECIFIC_CARD: {
                                SimpleCardView cardView = (CardView) event.getSource();
                                deck.getSideboard().add(retrieveTemporaryCard(cardView));
                                break;
                            }

                            case CARD_DOUBLE_CLICK: {
                                // in games you can't delete cards, only moves from one deck to another
                                SimpleCardView cardView = (SimpleCardView) event.getSource();
                                Card card = deck.findSideboardCard(cardView.getId());
                                if (card == null) {
                                    return;
                                }

                                deck.getSideboard().remove(card);
                                deck.getCards().add(card);

                                hidePopup();
                                refreshDeck();
                                break;
                            }
                        }
                    }
                });
        refreshDeck(true);

        // auto-import dropped files from OS
        if (mode == DeckEditorMode.FREE_BUILDING) {
            setDropTarget(new DropTarget(this, new DnDDeckTargetListener() {

                @Override
                protected boolean handleFilesDrop(boolean move, List<File> files) {
                    loadDeck(files.get(0).getAbsolutePath(), true);
                    return true;
                }

                @Override
                protected boolean handlePlainTextDrop(boolean move, String text) {
                    String tmpFile = DeckUtil.writeTextToTempFile(text);
                    loadDeck(tmpFile, false);
                    return true;
                }
            }));
        }

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
        Card card = null;
        boolean gameMode = mode != DeckEditorMode.FREE_BUILDING;
        if (gameMode) {
            // game: use existing real cards
            for (Card sideCard : deck.getSideboard()) {
                if (sideCard.getId().equals(cardView.getId())) {
                    card = sideCard;
                    break;
                }
            }
        } else {
            // editor: create mock card
            CardInfo cardInfo = CardRepository.instance.findCard(cardView.getExpansionSetCode(), cardView.getCardNumber());
            card = cardInfo != null ? cardInfo.getMockCard() : null;
        }

        if (card != null) {
            deck.getCards().add(card);
            if (gameMode) {
                // game: move card from another board
                deck.getSideboard().remove(card);
                cardSelector.removeCard(card.getId());
                cardSelector.setCardCount(deck.getSideboard().size());
                cardSelector.refresh();
            }
            // card hint update
            if (cardInfoPane instanceof CardInfoPane) {
                ((CardInfoPane) cardInfoPane).setCard(new CardView(card), null);
            }
            hidePopup();
        }
    }

    private void moveSelectorCardToSideboard(Event event) {
        boolean gameMode = mode != DeckEditorMode.FREE_BUILDING;
        if (gameMode) {
            throw new IllegalArgumentException("ERROR, you can move card to sideboard from selector in game mode.");
        }

        SimpleCardView cardView = (SimpleCardView) event.getSource();
        CardInfo cardInfo = CardRepository.instance.findCard(cardView.getExpansionSetCode(), cardView.getCardNumber());
        Card card = cardInfo != null ? cardInfo.getMockCard() : null;
        if (card != null) {
            deck.getSideboard().add(card);
        }

        // card hint update
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
            text = text + '0' + second;
        } else {
            text = text + second;
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

    private void importChoose(java.awt.event.ActionEvent evt) {

        Object[] options = {"From file", "From clipboard (new deck)", "From clipboard (append cards)"};

        int n = JOptionPane.showOptionDialog(MageFrame.getDesktop(),
                "Choose import location",
                "Deck import",
                JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]
        );

        switch (n) {
            case 0:
                importFromFile(evt);
                break;
            case 1:
                importFromClipboard(evt);
                break;
            case 2:
                importFromClipboardWithAppend(evt);
                break;
            default:
                break;
        }
    }

    private void importFromFile(java.awt.event.ActionEvent evt) {
        String lastFolder = MageFrame.getPreferences().get("lastImportFolder", "");
        if (!lastFolder.isEmpty()) {
            fcImportDeck.setCurrentDirectory(new File(lastFolder));
        }
        int ret = fcImportDeck.showOpenDialog(this);
        if (ret == JFileChooser.APPROVE_OPTION) {
            File file = fcImportDeck.getSelectedFile();
            MageFrame.getDesktop().setCursor(new Cursor(Cursor.WAIT_CURSOR));
            try {
                DeckImporter importer = DeckImporter.getDeckImporter(file.getPath());

                if (importer != null) {
                    StringBuilder errorMessages = new StringBuilder();
                    Deck newDeck = null;

                    newDeck = Deck.load(importer.importDeck(file.getPath(), errorMessages, true)); // file will be auto-fixed and saved on simple errors
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
    }

    private void importFromClipboard(ActionEvent evt) {
        final DeckImportClipboardDialog dialog = new DeckImportClipboardDialog();
        dialog.showDialog();

        if (!dialog.getTmpPath().isEmpty()) {
            loadDeck(dialog.getTmpPath(), false);
        }
    }

    private void importFromClipboardWithAppend(ActionEvent evt) {
        final DeckImportClipboardDialog dialog = new DeckImportClipboardDialog();
        dialog.showDialog();

        if (!dialog.getTmpPath().isEmpty()) {
            Deck deckToAppend = null;
            StringBuilder errorMessages = new StringBuilder();

            MageFrame.getDesktop().setCursor(new Cursor(Cursor.WAIT_CURSOR));
            try {
                deckToAppend = Deck.load(DeckImporter.importDeckFromFile(dialog.getTmpPath(), errorMessages, false), true, true);
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
    }

    private void exportChoose(java.awt.event.ActionEvent evt) {

        Object[] options = {"To file", "To clipboard"};

        int n = JOptionPane.showOptionDialog(MageFrame.getDesktop(),
                "Choose export location",
                "Deck export",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]
        );

        switch (n) {
            case 0:
                exportToFile(evt);
                break;
            case 1:
                exportToClipboard(evt);
                break;
            default:
                break;
        }
    }

    private void exportToFile(java.awt.event.ActionEvent evt) {
        // all available export formats
        fcExportDeck.resetChoosableFileFilters();
        for (FileFilter filter : DeckFormats.getFileFilters()) {
            fcExportDeck.addChoosableFileFilter(filter);
        }

        String lastFolder = MageFrame.getPreferences().get("lastExportFolder", "");
        if (!lastFolder.isEmpty()) {
            fcExportDeck.setCurrentDirectory(new File(lastFolder));
        }
        int ret = fcExportDeck.showSaveDialog(this);
        if (ret == JFileChooser.APPROVE_OPTION) {
            File file = fcExportDeck.getSelectedFile();

            // default ext for file
            String needFileName = file.getAbsolutePath();
            String needFileExt = DeckFormats.getDefaultFileExtForFilter(fcExportDeck.getFileFilter());
            if (!needFileExt.isEmpty() && !needFileName.toLowerCase(Locale.ENGLISH).endsWith("." + needFileExt)) {
                needFileName += "." + needFileExt;
            }

            MageFrame.getDesktop().setCursor(new Cursor(Cursor.WAIT_CURSOR));
            try {
                DeckFormats.writeDeck(needFileName, deck.getDeckCardLists());

                try {
                    MageFrame.getPreferences().put("lastExportFolder", file.getCanonicalPath());
                } catch (IOException ex) {
                    logger.error("Error on save last used export folder: " + ex.getMessage());
                }
            } catch (Exception ex) {
                logger.fatal(ex);
            } finally {
                MageFrame.getDesktop().setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
        }
        fcExportDeck.setSelectedFile(null);
    }

    private void exportToClipboard(java.awt.event.ActionEvent evt) {
        final DeckExportClipboardDialog dialog = new DeckExportClipboardDialog();
        dialog.showDialog(deck);
    }

    private void processAndShowImportErrors(StringBuilder errorMessages) {
        // show up errors list
        if (errorMessages.length() > 0) {
            String mes = "Found problems with deck: \n\n" + errorMessages.toString();
            JOptionPane.showMessageDialog(MageFrame.getDesktop(), mes.substring(0, Math.min(1000, mes.length())), "Errors while loading deck", JOptionPane.WARNING_MESSAGE);
        }
    }

    private boolean loadDeck(String file, boolean saveAutoFixedFile) {
        MageFrame.getDesktop().setCursor(new Cursor(Cursor.WAIT_CURSOR));
        try {
            StringBuilder errorMessages = new StringBuilder();
            Deck newDeck = Deck.load(DeckImporter.importDeckFromFile(file, errorMessages, saveAutoFixedFile), true, true);
            processAndShowImportErrors(errorMessages);

            if (newDeck != null) {
                deck = newDeck;
                refreshDeck();
                return true;
            }

        } catch (GameException e1) {
            JOptionPane.showMessageDialog(MageFrame.getDesktop(), e1.getMessage(), "Error loading deck", JOptionPane.ERROR_MESSAGE);
        } finally {
            MageFrame.getDesktop().setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        }
        return false;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelRight = new javax.swing.JSplitPane();
        cardSelector = new mage.client.deckeditor.CardSelector();
        deckArea = new mage.client.deckeditor.DeckArea();
        panelLeft = new javax.swing.JPanel();
        cardInfoPane = Plugins.instance.getCardInfoPane();
        if (cardInfoPane != null && System.getProperty("testCardInfo") != null) {
            cardInfoPane.setPreferredSize(new Dimension(170, 150));
            cardInfoPane.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(200, 0, 0)));
        } else {
            cardInfoPane = new JLabel();
            cardInfoPane.setVisible(false);
        }
        panelDeck = new javax.swing.JPanel();
        panelDeckName = new javax.swing.JPanel();
        lblDeckName = new javax.swing.JLabel();
        txtDeckName = new javax.swing.JTextField();
        panelDeckCreate = new javax.swing.JPanel();
        btnNew = new javax.swing.JButton();
        btnGenDeck = new javax.swing.JButton();
        panelDeckLoad = new javax.swing.JPanel();
        btnLoad = new javax.swing.JButton();
        btnImport = new javax.swing.JButton();
        panelDeckSave = new javax.swing.JPanel();
        btnSave = new javax.swing.JButton();
        btnExport = new javax.swing.JButton();
        panelDeckDraft = new javax.swing.JPanel();
        btnSubmit = new javax.swing.JButton();
        btnSubmitTimer = new javax.swing.JButton();
        panelDeckLands = new javax.swing.JPanel();
        btnAddLand = new javax.swing.JButton();
        btnLegality = new javax.swing.JButton();
        panelDeckExit = new javax.swing.JPanel();
        btnExit = new javax.swing.JButton();
        txtTimeRemaining = new javax.swing.JTextField();
        scrollPaneInfo = new javax.swing.JScrollPane();
        panelInfo = new javax.swing.JPanel();
        deckLegalityDisplay = new mage.client.deckeditor.DeckLegalityPanel();
        bigCard = new mage.client.cards.BigCard();

        panelRight.setDividerSize(10);
        panelRight.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
        panelRight.setResizeWeight(0.5);
        panelRight.setTopComponent(cardSelector);

        deckArea.setMinimumSize(new java.awt.Dimension(200, 56));
        panelRight.setBottomComponent(deckArea);

        panelDeck.setOpaque(false);
        panelDeck.setLayout(new javax.swing.BoxLayout(panelDeck, javax.swing.BoxLayout.Y_AXIS));

        panelDeckName.setOpaque(false);

        lblDeckName.setForeground(new java.awt.Color(255, 255, 255));
        lblDeckName.setLabelFor(txtDeckName);
        lblDeckName.setText("Deck Name:");

        javax.swing.GroupLayout panelDeckNameLayout = new javax.swing.GroupLayout(panelDeckName);
        panelDeckName.setLayout(panelDeckNameLayout);
        panelDeckNameLayout.setHorizontalGroup(
                panelDeckNameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(panelDeckNameLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(lblDeckName)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtDeckName, javax.swing.GroupLayout.DEFAULT_SIZE, 205, Short.MAX_VALUE)
                                .addContainerGap())
        );
        panelDeckNameLayout.setVerticalGroup(
                panelDeckNameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(panelDeckNameLayout.createSequentialGroup()
                                .addGroup(panelDeckNameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(txtDeckName, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(lblDeckName))
                                .addGap(0, 0, 0))
        );

        panelDeck.add(panelDeckName);

        panelDeckCreate.setOpaque(false);

        btnNew.setIcon(new javax.swing.ImageIcon(getClass().getResource("/buttons/state_active.png"))); // NOI18N
        btnNew.setText("NEW");
        btnNew.setIconTextGap(2);
        btnNew.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNewActionPerformed(evt);
            }
        });

        btnGenDeck.setIcon(new javax.swing.ImageIcon(getClass().getResource("/buttons/card_panel.png"))); // NOI18N
        btnGenDeck.setText("Random");
        btnGenDeck.setIconTextGap(1);
        btnGenDeck.setName("btnGenDeck"); // NOI18N
        btnGenDeck.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGenDeckActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelDeckCreateLayout = new javax.swing.GroupLayout(panelDeckCreate);
        panelDeckCreate.setLayout(panelDeckCreateLayout);
        panelDeckCreateLayout.setHorizontalGroup(
                panelDeckCreateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(panelDeckCreateLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(btnNew, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnGenDeck, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(70, Short.MAX_VALUE))
        );
        panelDeckCreateLayout.setVerticalGroup(
                panelDeckCreateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(panelDeckCreateLayout.createSequentialGroup()
                                .addGap(5, 5, 5)
                                .addGroup(panelDeckCreateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(btnGenDeck, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
                                        .addComponent(btnNew, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)))
        );

        panelDeck.add(panelDeckCreate);

        panelDeckLoad.setOpaque(false);

        btnLoad.setIcon(new javax.swing.ImageIcon(getClass().getResource("/buttons/search_24.png"))); // NOI18N
        btnLoad.setText("LOAD");
        btnLoad.setIconTextGap(2);
        btnLoad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLoadActionPerformed(evt);
            }
        });

        btnImport.setIcon(new javax.swing.ImageIcon(getClass().getResource("/buttons/deck_in.png"))); // NOI18N
        btnImport.setText("Import");
        btnImport.setIconTextGap(2);
        btnImport.setName("btnImport"); // NOI18N
        btnImport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnImportActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelDeckLoadLayout = new javax.swing.GroupLayout(panelDeckLoad);
        panelDeckLoad.setLayout(panelDeckLoadLayout);
        panelDeckLoadLayout.setHorizontalGroup(
                panelDeckLoadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(panelDeckLoadLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(btnLoad, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnImport, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(70, Short.MAX_VALUE))
        );
        panelDeckLoadLayout.setVerticalGroup(
                panelDeckLoadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(panelDeckLoadLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(panelDeckLoadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(btnLoad, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                                        .addComponent(btnImport, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)))
        );

        panelDeck.add(panelDeckLoad);

        panelDeckSave.setOpaque(false);

        btnSave.setIcon(new javax.swing.ImageIcon(getClass().getResource("/buttons/sideboard_out.png"))); // NOI18N
        btnSave.setText("SAVE");
        btnSave.setIconTextGap(2);
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });

        btnExport.setIcon(new javax.swing.ImageIcon(getClass().getResource("/buttons/deck_out.png"))); // NOI18N
        btnExport.setText("Export");
        btnExport.setIconTextGap(2);
        btnExport.setName("btnImport"); // NOI18N
        btnExport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExportActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelDeckSaveLayout = new javax.swing.GroupLayout(panelDeckSave);
        panelDeckSave.setLayout(panelDeckSaveLayout);
        panelDeckSaveLayout.setHorizontalGroup(
                panelDeckSaveLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(panelDeckSaveLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnExport, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(70, Short.MAX_VALUE))
        );
        panelDeckSaveLayout.setVerticalGroup(
                panelDeckSaveLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(panelDeckSaveLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(panelDeckSaveLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(btnSave, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
                                        .addComponent(btnExport, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)))
        );

        panelDeck.add(panelDeckSave);

        panelDeckDraft.setOpaque(false);

        btnSubmit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/buttons/state_finished.png"))); // NOI18N
        btnSubmit.setText("SUBMIT");
        btnSubmit.setIconTextGap(2);
        btnSubmit.setName("btnSubmit"); // NOI18N
        btnSubmit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSubmitActionPerformed(evt);
            }
        });

        btnSubmitTimer.setFont(new java.awt.Font("Tahoma", 0, 9)); // NOI18N
        btnSubmitTimer.setIcon(new javax.swing.ImageIcon(getClass().getResource("/buttons/state_finished.png"))); // NOI18N
        btnSubmitTimer.setText("<html>Submit<br>in 1 min");
        btnSubmitTimer.setIconTextGap(2);
        btnSubmitTimer.setName("btnSubmitTimer"); // NOI18N
        btnSubmitTimer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSubmitTimerActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelDeckDraftLayout = new javax.swing.GroupLayout(panelDeckDraft);
        panelDeckDraft.setLayout(panelDeckDraftLayout);
        panelDeckDraftLayout.setHorizontalGroup(
                panelDeckDraftLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(panelDeckDraftLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(btnSubmit, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnSubmitTimer, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(70, Short.MAX_VALUE))
        );
        panelDeckDraftLayout.setVerticalGroup(
                panelDeckDraftLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(panelDeckDraftLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(panelDeckDraftLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(panelDeckDraftLayout.createSequentialGroup()
                                                .addComponent(btnSubmit, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(0, 0, Short.MAX_VALUE))
                                        .addComponent(btnSubmitTimer, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                                .addGap(0, 0, 0))
        );

        panelDeck.add(panelDeckDraft);

        panelDeckLands.setOpaque(false);

        btnAddLand.setIcon(new javax.swing.ImageIcon(getClass().getResource("/buttons/type_land.png"))); // NOI18N
        btnAddLand.setText("Lands");
        btnAddLand.setIconTextGap(2);
        btnAddLand.setName("btnAddLand"); // NOI18N
        btnAddLand.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddLandActionPerformed(evt);
            }
        });

        btnLegality.setText("Validate");
        btnLegality.setIconTextGap(2);
        btnLegality.setName("btnLegality"); // NOI18N
        btnLegality.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLegalityActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelDeckLandsLayout = new javax.swing.GroupLayout(panelDeckLands);
        panelDeckLands.setLayout(panelDeckLandsLayout);
        panelDeckLandsLayout.setHorizontalGroup(
                panelDeckLandsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(panelDeckLandsLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(btnAddLand, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnLegality, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(70, Short.MAX_VALUE))
        );
        panelDeckLandsLayout.setVerticalGroup(
                panelDeckLandsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelDeckLandsLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(panelDeckLandsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(btnAddLand, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(btnLegality, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(0, 0, 0))
        );

        panelDeck.add(panelDeckLands);

        panelDeckExit.setOpaque(false);

        btnExit.setText("Exit");
        btnExit.setIconTextGap(2);
        btnExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExitActionPerformed(evt);
            }
        });

        txtTimeRemaining.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTimeRemainingActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelDeckExitLayout = new javax.swing.GroupLayout(panelDeckExit);
        panelDeckExit.setLayout(panelDeckExitLayout);
        panelDeckExitLayout.setHorizontalGroup(
                panelDeckExitLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(panelDeckExitLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(btnExit, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtTimeRemaining, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(70, Short.MAX_VALUE))
        );
        panelDeckExitLayout.setVerticalGroup(
                panelDeckExitLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelDeckExitLayout.createSequentialGroup()
                                .addGap(0, 11, Short.MAX_VALUE)
                                .addGroup(panelDeckExitLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(btnExit, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(txtTimeRemaining, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        panelDeck.add(panelDeckExit);

        scrollPaneInfo.setBorder(null);
        scrollPaneInfo.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPaneInfo.setOpaque(false);

        panelInfo.setOpaque(false);

        deckLegalityDisplay.setMaximumSize(new java.awt.Dimension(245, 155));
        deckLegalityDisplay.setMinimumSize(new java.awt.Dimension(85, 155));
        deckLegalityDisplay.setOpaque(false);
        deckLegalityDisplay.setVisible(false);

        javax.swing.GroupLayout panelInfoLayout = new javax.swing.GroupLayout(panelInfo);
        panelInfo.setLayout(panelInfoLayout);
        panelInfoLayout.setHorizontalGroup(
                panelInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(panelInfoLayout.createSequentialGroup()
                                .addGap(15, 15, 15)
                                .addGroup(panelInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(bigCard, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelInfoLayout.createSequentialGroup()
                                                .addGap(0, 0, Short.MAX_VALUE)
                                                .addComponent(deckLegalityDisplay, javax.swing.GroupLayout.PREFERRED_SIZE, 245, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(0, 0, Short.MAX_VALUE)))
                                .addGap(15, 15, 15))
        );
        panelInfoLayout.setVerticalGroup(
                panelInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(panelInfoLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(deckLegalityDisplay, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(bigCard, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
        );

        scrollPaneInfo.setViewportView(panelInfo);

        javax.swing.GroupLayout panelLeftLayout = new javax.swing.GroupLayout(panelLeft);
        panelLeft.setLayout(panelLeftLayout);
        panelLeftLayout.setHorizontalGroup(
                panelLeftLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(panelDeck, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(scrollPaneInfo)
        );
        panelLeftLayout.setVerticalGroup(
                panelLeftLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(panelLeftLayout.createSequentialGroup()
                                .addComponent(panelDeck, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(scrollPaneInfo)
                                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(panelLeft, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(panelRight, javax.swing.GroupLayout.DEFAULT_SIZE, 883, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(panelLeft, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(panelRight, javax.swing.GroupLayout.Alignment.TRAILING)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        String lastFolder = MageFrame.getPreferences().get(LAST_DECK_FOLDER, "");
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
                if (!(fileName.endsWith(".dck") || fileName.endsWith(".dck_info"))) {
                    fileName += ".dck";
                }
                boolean useDeckInfo = false;
                if (fileName.endsWith(".dck_info")) {
                    useDeckInfo = true;
                }
                MageFrame.getDesktop().setCursor(new Cursor(Cursor.WAIT_CURSOR));
                DeckCardLists cardLists = deck.getDeckCardLists();
                cardLists.setCardLayout(deckArea.getCardLayout());
                cardLists.setSideboardLayout(deckArea.getSideboardLayout());
                if (!useDeckInfo) {
                    XMAGE.getExporter().writeDeck(fileName, cardLists);
                } else {
                    XMAGE_INFO.getExporter().writeDeck(fileName, cardLists);
                }
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(MageFrame.getDesktop(), ex.getMessage() + "\nTry ensuring that the selected directory is writable.", "Error saving deck", JOptionPane.ERROR_MESSAGE);
            } finally {
                MageFrame.getDesktop().setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
            try {
                MageFrame.getPreferences().put(LAST_DECK_FOLDER, file.getCanonicalPath());
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }//GEN-LAST:event_btnSaveActionPerformed

    private void btnLoadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLoadActionPerformed
        //fcSelectDeck.setCurrentDirectory(new File());
        String lastFolder = MageFrame.getPreferences().get(LAST_DECK_FOLDER, "");
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

                newDeck = Deck.load(DeckImporter.importDeckFromFile(file.getPath(), errorMessages, true), true, true);
                processAndShowImportErrors(errorMessages);

                if (newDeck != null) {
                    deck = newDeck;
                    refreshDeck(true);
                }

                // save last deck history
                try {
                    MageFrame.getPreferences().put(LAST_DECK_FOLDER, file.getCanonicalPath());
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

    private void btnAddLandActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddLandActionPerformed
        AddLandDialog addLand = new AddLandDialog();
        addLand.showDialog(deck, mode);
        refreshDeck();
    }//GEN-LAST:event_btnAddLandActionPerformed

    private void btnGenDeckActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGenDeckActionPerformed
        try {
            MageFrame.getDesktop().setCursor(new Cursor(Cursor.WAIT_CURSOR));
            String path = DeckGenerator.generateDeck();
            deck = Deck.load(DeckImporter.importDeckFromFile(path, false), true, true);
        } catch (GameException ex) {
            JOptionPane.showMessageDialog(MageFrame.getDesktop(), ex.getMessage(), "Error loading generated deck", JOptionPane.ERROR_MESSAGE);
        } catch (DeckGeneratorException ex) {
            JOptionPane.showMessageDialog(MageFrame.getDesktop(), ex.getMessage(), "Generator error", JOptionPane.ERROR_MESSAGE);
        } finally {
            MageFrame.getDesktop().setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        }
        refreshDeck();
    }//GEN-LAST:event_btnGenDeckActionPerformed

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

    private void txtTimeRemainingActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTimeRemainingActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTimeRemainingActionPerformed

    private void btnImportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnImportActionPerformed
        importChoose(evt);
    }//GEN-LAST:event_btnImportActionPerformed

    private void btnExportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExportActionPerformed
        exportChoose(evt);
    }//GEN-LAST:event_btnExportActionPerformed

    private void btnLegalityActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLegalityActionPerformed
        this.deckLegalityDisplay.setVisible(true);
        this.deckLegalityDisplay.validateDeck(deck);
    }//GEN-LAST:event_btnLegalityActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private mage.client.cards.BigCard bigCard;
    private javax.swing.JButton btnAddLand;
    private javax.swing.JButton btnExit;
    private javax.swing.JButton btnExport;
    private javax.swing.JButton btnGenDeck;
    private javax.swing.JButton btnImport;
    private javax.swing.JButton btnLegality;
    private javax.swing.JButton btnLoad;
    private javax.swing.JButton btnNew;
    private javax.swing.JButton btnSave;
    private javax.swing.JButton btnSubmit;
    private javax.swing.JButton btnSubmitTimer;
    private JComponent cardInfoPane;
    /*
    private org.mage.plugins.card.info.CardInfoPaneImpl cardInfoPane;
    */
    private mage.client.deckeditor.CardSelector cardSelector;
    private mage.client.deckeditor.DeckArea deckArea;
    private mage.client.deckeditor.DeckLegalityPanel deckLegalityDisplay;
    private javax.swing.JLabel lblDeckName;
    private javax.swing.JPanel panelDeck;
    private javax.swing.JPanel panelDeckCreate;
    private javax.swing.JPanel panelDeckDraft;
    private javax.swing.JPanel panelDeckExit;
    private javax.swing.JPanel panelDeckLands;
    private javax.swing.JPanel panelDeckLoad;
    private javax.swing.JPanel panelDeckName;
    private javax.swing.JPanel panelDeckSave;
    private javax.swing.JPanel panelInfo;
    private javax.swing.JPanel panelLeft;
    private javax.swing.JSplitPane panelRight;
    private javax.swing.JScrollPane scrollPaneInfo;
    private javax.swing.JTextField txtDeckName;
    private javax.swing.JTextField txtTimeRemaining;
    // End of variables declaration//GEN-END:variables
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
            return ext.equalsIgnoreCase("dec")
                    || ext.equalsIgnoreCase("mwdeck")
                    || ext.equalsIgnoreCase("txt")
                    || ext.equalsIgnoreCase("dek")
                    || ext.equalsIgnoreCase("cod")
                    || ext.equalsIgnoreCase("o8d")
                    || ext.equalsIgnoreCase("json")
                    || ext.equalsIgnoreCase("draft")
                    || ext.equalsIgnoreCase("mtga");
        }
        return false;
    }

    @Override
    public String getDescription() {
        return "All formats (*.dec; *.mwDeck; *.txt; *.dek; *.cod; *.o8d; *.json; *.draft; *.mtga;)";
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
