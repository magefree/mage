 package mage.client.draft;

 import mage.cards.repository.CardInfo;
 import mage.cards.repository.CardRepository;
 import mage.client.MageFrame;
 import mage.client.SessionHandler;
 import mage.client.components.tray.MageTray;
 import mage.client.deckeditor.SortSettingDraft;
 import mage.client.dialog.PreferencesDialog;
 import mage.client.plugins.impl.Plugins;
 import mage.client.util.Event;
 import mage.client.util.*;
 import mage.client.util.audio.AudioManager;
 import mage.client.util.gui.BufferedImageBuilder;
 import mage.constants.PlayerAction;
 import mage.view.*;
 import org.apache.log4j.Logger;

 import javax.swing.Timer;
 import javax.swing.*;
 import java.awt.*;
 import java.awt.event.ActionEvent;
 import java.awt.event.ActionListener;
 import java.awt.event.KeyEvent;
 import java.awt.image.BufferedImage;
 import java.io.File;
 import java.text.SimpleDateFormat;
 import java.util.List;
 import java.util.*;

 /**
  * Game GUI: draft panel for drafting game mode only
  *
  * @author BetaSteward_at_googlemail.com, JayDi85
  */
 public class DraftPanel extends javax.swing.JPanel {

     private static final Logger logger = Logger.getLogger(DraftPanel.class);

     private UUID draftId;
     private Timer countdown;
     private int timeout;

     /**
      * ms delay between booster showing up and pick being allowed.
      */
     private static final int protectionTime = 1500;
     /**
      * Timer starting at booster being displayed, to protect from early pick due to clicking
      * a little too much on the last pick.
      */
     private Timer protectionTimer;
     /**
      * Number of the latest card pick for which the protection timer has been set.
      */
     private int protectionPickNo = 0;

     // popup menu area picked cards
     private final JPopupMenu popupMenuPickedArea;
     // popup menu for a card
     private final JPopupMenu popupMenuCardPanel;
     // cards hidden in the picked cards area
     private final Set<UUID> cardsHidden = new HashSet<>();
     // all cards picked
     protected SimpleCardsView pickedCards;
     // all cards picked
     protected final SimpleCardsView pickedCardsShown = new SimpleCardsView();
     // id of card with popup menu
     protected UUID cardIdPopupMenu;

     // Helper for writing the draft log.
     private DraftPickLogger draftLogger;

     // List of set codes (for draft log writing).
     private List<String> setCodes;

     // Number of the current booster (for draft log writing).
     private int packNo = 1;

     // Number of the current card pick (for draft log writing).
     private int pickNo = 1;
     
     // Number of the latest card pick for which the timeout has been set.
     private int timeoutPickNo = 0;

     // Cached booster data to be written into the log (see logLastPick).
     private String[] currentBooster;

     private static final CardsView EMPTY_VIEW = new CardsView();

     private Listener<Event> selectedCardsListener = null;
     private Listener<Event> pickingCardsListener = null;

     /**
      * Creates new form DraftPanel
      */
     public DraftPanel() {
         initComponents();

         draftBooster.setOpaque(false);
         draftPicks.setSortSetting(SortSettingDraft.getInstance());
         draftPicks.setOpaque(false);

         popupMenuPickedArea = new JPopupMenu();
         addPopupMenuPickArea();
         this.add(popupMenuPickedArea);

         popupMenuCardPanel = new JPopupMenu();
         addPopupMenuCardPanel();
         this.add(popupMenuCardPanel);

         panelInfo.setOpaque(false);

         countdown = new Timer(1000,
                 e -> {
                     if (--timeout > 0) {
                         setTimeout(timeout);
                     } else {
                         setTimeout(0);
                         countdown.stop();
                     }
                 }
         );

         protectionTimer = new Timer(protectionTime, e -> protectionTimer.stop());
     }

     public void cleanUp() {
         draftPicks.cleanUp();
         draftBooster.clear();

         if (countdown != null) {
             countdown.stop();
             for (ActionListener al : countdown.getActionListeners()) {
                 countdown.removeActionListener(al);
             }
         }

         if (protectionTimer != null) {
             protectionTimer.stop();
             for (ActionListener al : protectionTimer.getActionListeners()) {
                 protectionTimer.removeActionListener(al);
             }
         }
     }

     public void changeGUISize() {
         draftPicks.changeGUISize();
         setGUISize();
     }

     private void setGUISize() {
         GUISizeHelper.changePopupMenuFont(popupMenuPickedArea);
     }

     public synchronized void showDraft(UUID draftId) {
         this.draftId = draftId;
         MageFrame.addDraft(draftId, this);
         if (!SessionHandler.joinDraft(draftId)) {
             hideDraft();
         }

         if (isLogging()) {
             SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
             String logFilename = "Draft_" + sdf.format(new Date()) + '_' + draftId + ".draft";
             draftLogger = new DraftPickLogger(new File("gamelogs"), logFilename);
         } else {
             draftLogger = new DraftPickLogger();
         }
     }

     public void updateDraft(DraftView draftView) {
         if (draftView.getSets().size() != 3) {
             // Random draft - TODO: can we access the type of draft here?
             this.editPack1.setText("Random Boosters");
             this.editPack2.setText("Random Boosters");
             this.editPack3.setText("Random Boosters");
         } else {
             this.editPack1.setText(draftView.getSets().get(0));
             this.editPack2.setText(draftView.getSets().get(1));
             this.editPack3.setText(draftView.getSets().get(2));
         }
         this.checkPack1.setSelected(draftView.getBoosterNum() > 1);
         this.checkPack2.setSelected(draftView.getBoosterNum() > 2);
         this.checkPack3.setSelected(draftView.getBoosterNum() > 3);
         this.editCardNumber.setText(Integer.toString(draftView.getCardNum()));

         packNo = draftView.getBoosterNum();
         pickNo = draftView.getCardNum();
         setCodes = draftView.getSetCodes();
         draftLogger.updateDraft(draftId, draftView);

         int right = draftView.getPlayers().size() / 2;
         int left = draftView.getPlayers().size() - right;
         int height = left * 18;
         labelTableImage.setSize(new Dimension(labelTableImage.getWidth(), height));
         // TODO: Can we fix this for Rich Draft where there is no direction?
         Image tableImage = ImageHelper.getImageFromResources(draftView.getBoosterNum() % 2 == 1 ? "/draft/table_left.png" : "/draft/table_right.png");
         BufferedImage resizedTable = ImageHelper.getResizedImage(BufferedImageBuilder.bufferImage(tableImage, BufferedImage.TYPE_INT_ARGB), labelTableImage.getWidth(), labelTableImage.getHeight());
         labelTableImage.setIcon(new ImageIcon(resizedTable));

         int count = 0;
         int numberPlayers = draftView.getPlayers().size();
         for (String playerName : draftView.getPlayers()) {
             count++;
             setPlayerNameToLabel(playerName, count, numberPlayers);
         }
     }

     private void setPlayerNameToLabel(String name, int index, int players) {
         int tablePosition;
         int right = players / 2;
         int left = players - right;
         if (index <= left) {
             // left side down (1 - 8)
             tablePosition = index;
         } else {
             // right side up (16 - 9)
             tablePosition = 9 + right - (index - left);
         }
         switch (tablePosition) {
             case 1:
                 labelPlayer01.setText(name);
                 break;
             case 2:
                 labelPlayer02.setText(name);
                 break;
             case 3:
                 labelPlayer03.setText(name);
                 break;
             case 4:
                 labelPlayer04.setText(name);
                 break;
             case 5:
                 labelPlayer05.setText(name);
                 break;
             case 6:
                 labelPlayer06.setText(name);
                 break;
             case 7:
                 labelPlayer07.setText(name);
                 break;
             case 8:
                 labelPlayer08.setText(name);
                 break;
             case 9:
                 labelPlayer09.setText(name);
                 break;
             case 10:
                 labelPlayer10.setText(name);
                 break;
             case 11:
                 labelPlayer11.setText(name);
                 break;
             case 12:
                 labelPlayer12.setText(name);
                 break;
             case 13:
                 labelPlayer13.setText(name);
                 break;
             case 14:
                 labelPlayer14.setText(name);
                 break;
             case 15:
                 labelPlayer15.setText(name);
                 break;
             case 16:
                 labelPlayer16.setText(name);
                 break;
         }
     }

     public void loadBooster(DraftPickView draftPickView) {
         logLastPick(draftPickView);
         // upper area that shows the picks
         loadCardsToPickedCardsArea(draftPickView.getPicks());

         // ENABLE clicks on selected/picked cards
         if (this.selectedCardsListener == null) {
             this.selectedCardsListener = event -> {
                 if (event.getEventType() == ClientEventType.CARD_POPUP_MENU) {
                     if (event.getSource() != null) {
                         // Popup Menu Card
                         cardIdPopupMenu = ((SimpleCardView) event.getSource()).getId();
                         popupMenuCardPanel.show(event.getComponent(), event.getxPos(), event.getyPos());
                     } else {
                         // Popup Menu area
                         popupMenuPickedArea.show(event.getComponent(), event.getxPos(), event.getyPos());
                     }
                 }
             };
             this.draftPicks.addCardEventListener(this.selectedCardsListener);
         }

         // lower area that shows the booster
         this.draftBooster.loadBooster(CardsViewUtil.convertSimple(draftPickView.getBooster()), bigCard);
         if (this.pickingCardsListener == null) {
             this.pickingCardsListener = event -> {
                 if (event.getEventType() == ClientEventType.DRAFT_PICK_CARD) {
                     // PICK card
                     SimpleCardView source = (SimpleCardView) event.getSource();
                     DraftPickView view = SessionHandler.sendCardPick(draftId, source.getId(), cardsHidden);
                     if (view != null) {
                         loadCardsToPickedCardsArea(view.getPicks());
                         draftBooster.loadBooster(EMPTY_VIEW, bigCard);
                         Plugins.instance.getActionCallback().hideOpenComponents();
                         setMessage("Waiting for other players");
                     }
                 } else if (event.getEventType() == ClientEventType.DRAFT_MARK_CARD) {
                     // MARK card
                     SimpleCardView source = (SimpleCardView) event.getSource();
                     SessionHandler.sendCardMark(draftId, source.getId());
                 }
             };
             this.draftBooster.addCardEventListener(this.pickingCardsListener);
         }

         setMessage("Pick a card");
         if (!AppUtil.isAppActive()) {
             MageTray.instance.displayMessage("Pick the next card.");
             MageTray.instance.blink();
         }
         
         int newTimeout = draftPickView.getTimeout();
         if (pickNo != timeoutPickNo || newTimeout < timeout) { // if the timeout would increase the current pick's timer, don't set it (might happen if the client or server is lagging)
             timeoutPickNo = pickNo;
             countdown.stop();
             timeout = newTimeout;
             setTimeout(timeout);
             if (timeout != 0) {
                 countdown.start();
             }
         }
         
         if (!draftBooster.isEmptyGrid()) {
             SessionHandler.setBoosterLoaded(draftId); // confirm to the server that the booster has been successfully loaded, otherwise the server will re-send the booster

             if (pickNo != protectionPickNo && !protectionTimer.isRunning()) {
                 // Restart the protection timer.
                 protectionPickNo = pickNo;
                 protectionTimer.restart();
             }
         }
     }

     private void loadCardsToPickedCardsArea(SimpleCardsView pickedCards) {
         this.pickedCards = pickedCards;
         for (Map.Entry<UUID, SimpleCardView> entry : pickedCards.entrySet()) {
             if (!cardsHidden.contains(entry.getKey())) {
                 pickedCardsShown.put(entry.getKey(), entry.getValue());
             }
         }
         draftPicks.loadCards(CardsViewUtil.convertSimple(pickedCardsShown), bigCard, null);
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
         this.editTimeRemaining.setText(text);
         if (s == 6 && !draftBooster.isEmptyGrid()) {
             AudioManager.playOnCountdown1();
         }
     }

     public boolean isAllowedToPick() {
         return !protectionTimer.isRunning();
     }

     public void hideDraft() {
         Component c = this.getParent();
         while (c != null && !(c instanceof DraftPane)) {
             c = c.getParent();
         }
         if (c != null) {
             ((DraftPane) c).removeDraft();
         }
     }

     protected void setMessage(String message) {
         this.labelMessage.setText(message);
     }

     private void addPopupMenuPickArea() {
         int c = JComponent.WHEN_IN_FOCUSED_WINDOW;

         KeyStroke ks9 = KeyStroke.getKeyStroke(KeyEvent.VK_F9, 0);
         this.getInputMap(c).put(ks9, "F9_PRESS");
         this.getActionMap().put("F9_PRESS", new AbstractAction() {
             @Override
             public void actionPerformed(ActionEvent actionEvent) {
                 showAgainAllHiddenCards();
             }
         });

         JMenuItem menuItem;

         menuItem = new JMenuItem("F9 - Show all hidden cards");
         popupMenuPickedArea.add(menuItem);

         // Confirm (F9)
         menuItem.addActionListener(e -> showAgainAllHiddenCards());

         // popupMenuPickedArea.addSeparator();
     }

     private void addPopupMenuCardPanel() {

         JMenuItem menuItem;

         menuItem = new JMenuItem("Hide this card");
         popupMenuCardPanel.add(menuItem);

         // Hide Card
         menuItem.addActionListener(e -> hideThisCard(cardIdPopupMenu));

         // popupMenuCardPanel.addSeparator();
     }

     private void hideThisCard(UUID card) {
         // Add the card to the hidden cards
         cardsHidden.add(card);
         pickedCardsShown.remove(card);
         draftPicks.loadCards(CardsViewUtil.convertSimple(pickedCardsShown), bigCard, null);
     }

     private void showAgainAllHiddenCards() {
         // Add back the hidden cards to the shown set
         for (UUID card : cardsHidden) {
             pickedCardsShown.put(card, pickedCards.get(card));
         }
         cardsHidden.clear();
         draftPicks.loadCards(CardsViewUtil.convertSimple(pickedCardsShown), bigCard, null);
     }

     // Log the last card picked into the draft log together with booster
     // contents.
     // We don't get any event when the card is selected due to timeout
     // that's why instead of proactively logging our pick we instead
     // log *last* pick from the list of picks.
     // To make this possible we cache the list of cards from the
     // previous booster and its sequence number (pack number / pick number)
     // in fields currentBooster and currentBoosterHeader.
     private void logLastPick(DraftPickView pickView) {
         if (!isLogging()) {
             return;
         }
         if (currentBooster != null) {
             String lastPick = getCardName(getLastPick(pickView.getPicks().values()));
             if (lastPick != null && currentBooster.length > 1) {
                 draftLogger.logPick(getCurrentSetCode(), packNo, pickNo - 1, lastPick, currentBooster); // wtf pickno need -1?
             }
             currentBooster = null;
         }
         setCurrentBoosterForLog(pickView.getBooster());
         if (currentBooster.length == 1) {
             draftLogger.logPick(getCurrentSetCode(), packNo, pickNo, currentBooster[0], currentBooster);
         }
     }

     private String getCurrentSetCode() {
         // TODO: Record set codes for random drafts correctly
         if (setCodes != null && setCodes.size() >= packNo) {
             String setCode = setCodes.get(packNo - 1);
             if (setCode != null) { // Not sure how, but got a NPE from this method P1P2 in a ZEN/ZEN/WWK draft
                 return setCode;
             }
         }
         return "   ";
     }

     private static boolean isLogging() {
         String autoSave = PreferencesDialog.getCachedValue(PreferencesDialog.KEY_DRAFT_LOG_AUTO_SAVE, "true");
         return autoSave.equals("true");
     }

     private void setCurrentBoosterForLog(SimpleCardsView booster) {
         LinkedList<String> cards = new LinkedList<>();
         for (SimpleCardView simple : booster.values()) {
             String cardName = getCardName(simple);
             if (cardName != null) {
                 cards.add(cardName);
             }
         }

         currentBooster = cards.toArray(new String[cards.size()]);
     }

     private static SimpleCardView getLastPick(Collection<SimpleCardView> picks) {
         SimpleCardView last = null;
         for (SimpleCardView pick : picks) {
             last = pick;
         }
         return last;
     }

     private static String getCardName(SimpleCardView card) {
         if (card == null) {
             return null;
         }
         CardInfo cardInfo = CardRepository.instance.findCard(card.getExpansionSetCode(), card.getCardNumber());
         return cardInfo != null ? cardInfo.getName() : null;
     }

     /**
      * This method is called from within the constructor to initialize the form.
      * WARNING: Do NOT modify this code. The content of this method is always
      * regenerated by the Form Editor.
      */
     @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jSeparator1 = new javax.swing.JSeparator();
        panelInfo = new javax.swing.JPanel();
        buttonQuitTournament = new javax.swing.JButton();
        labelPack1 = new javax.swing.JLabel();
        editPack1 = new javax.swing.JTextField();
        checkPack1 = new javax.swing.JCheckBox();
        labelPack2 = new javax.swing.JLabel();
        editPack2 = new javax.swing.JTextField();
        checkPack2 = new javax.swing.JCheckBox();
        labelPack3 = new javax.swing.JLabel();
        editPack3 = new javax.swing.JTextField();
        checkPack3 = new javax.swing.JCheckBox();
        labelCardNumber = new javax.swing.JLabel();
        editCardNumber = new javax.swing.JTextField();
        editTimeRemaining = new javax.swing.JTextField();
        labelMessage = new javax.swing.JLabel();
        bigCard = new mage.client.cards.BigCard();
        panelPlayers = new javax.swing.JPanel();
        panelPlayersLeft = new javax.swing.JPanel();
        labelPlayer01 = new javax.swing.JLabel();
        labelPlayer02 = new javax.swing.JLabel();
        labelPlayer03 = new javax.swing.JLabel();
        labelPlayer04 = new javax.swing.JLabel();
        labelPlayer05 = new javax.swing.JLabel();
        labelPlayer06 = new javax.swing.JLabel();
        labelPlayer07 = new javax.swing.JLabel();
        labelPlayer08 = new javax.swing.JLabel();
        labelTableImage = new javax.swing.JLabel();
        panelPlayersRight = new javax.swing.JPanel();
        labelPlayer09 = new javax.swing.JLabel();
        labelPlayer10 = new javax.swing.JLabel();
        labelPlayer11 = new javax.swing.JLabel();
        labelPlayer12 = new javax.swing.JLabel();
        labelPlayer13 = new javax.swing.JLabel();
        labelPlayer14 = new javax.swing.JLabel();
        labelPlayer15 = new javax.swing.JLabel();
        labelPlayer16 = new javax.swing.JLabel();
        draftPicks = new mage.client.cards.CardsList();
        draftBooster = new mage.client.draft.DraftGrid();

        panelInfo.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        panelInfo.setFocusable(false);
        panelInfo.setRequestFocusEnabled(false);
        panelInfo.setVerifyInputWhenFocusTarget(false);

        buttonQuitTournament.setText("Quit Tournament");
        buttonQuitTournament.setFocusable(false);
        buttonQuitTournament.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonQuitTournamentActionPerformed(evt);
            }
        });

        labelPack1.setText("Pack 1:");

        editPack1.setEditable(false);
        editPack1.setEnabled(false);
        editPack1.setPreferredSize(new java.awt.Dimension(130, 22));

        labelPack2.setText("Pack 2:");

        editPack2.setEditable(false);
        editPack2.setEnabled(false);
        editPack2.setPreferredSize(new java.awt.Dimension(130, 22));

        labelPack3.setText("Pack 3:");

        editPack3.setEditable(false);
        editPack3.setEnabled(false);
        editPack3.setPreferredSize(new java.awt.Dimension(130, 22));

        labelCardNumber.setText("Card #:");

        editCardNumber.setEditable(false);
        editCardNumber.setEnabled(false);

        editTimeRemaining.setEditable(false);
        editTimeRemaining.setForeground(java.awt.Color.red);
        editTimeRemaining.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        editTimeRemaining.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        labelMessage.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelMessage.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        labelMessage.setOpaque(true);

        bigCard.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        panelPlayers.setOpaque(false);
        panelPlayers.setLayout(null);

        panelPlayersLeft.setFocusable(false);
        panelPlayersLeft.setMaximumSize(new java.awt.Dimension(80, 132));
        panelPlayersLeft.setMinimumSize(new java.awt.Dimension(80, 132));
        panelPlayersLeft.setOpaque(false);
        panelPlayersLeft.setPreferredSize(new java.awt.Dimension(80, 132));
        panelPlayersLeft.setRequestFocusEnabled(false);
        panelPlayersLeft.setVerifyInputWhenFocusTarget(false);
        panelPlayersLeft.setLayout(new java.awt.GridLayout(8, 1));

        labelPlayer01.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        labelPlayer01.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        labelPlayer01.setFocusable(false);
        labelPlayer01.setRequestFocusEnabled(false);
        labelPlayer01.setVerifyInputWhenFocusTarget(false);
        panelPlayersLeft.add(labelPlayer01);

        labelPlayer02.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        labelPlayer02.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        labelPlayer02.setFocusable(false);
        labelPlayer02.setRequestFocusEnabled(false);
        labelPlayer02.setVerifyInputWhenFocusTarget(false);
        panelPlayersLeft.add(labelPlayer02);

        labelPlayer03.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        labelPlayer03.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        labelPlayer03.setFocusable(false);
        labelPlayer03.setRequestFocusEnabled(false);
        labelPlayer03.setVerifyInputWhenFocusTarget(false);
        panelPlayersLeft.add(labelPlayer03);

        labelPlayer04.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        labelPlayer04.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        labelPlayer04.setFocusable(false);
        labelPlayer04.setRequestFocusEnabled(false);
        labelPlayer04.setVerifyInputWhenFocusTarget(false);
        panelPlayersLeft.add(labelPlayer04);

        labelPlayer05.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        labelPlayer05.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        labelPlayer05.setFocusable(false);
        labelPlayer05.setRequestFocusEnabled(false);
        labelPlayer05.setVerifyInputWhenFocusTarget(false);
        panelPlayersLeft.add(labelPlayer05);

        labelPlayer06.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        labelPlayer06.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        labelPlayer06.setFocusable(false);
        labelPlayer06.setRequestFocusEnabled(false);
        labelPlayer06.setVerifyInputWhenFocusTarget(false);
        panelPlayersLeft.add(labelPlayer06);

        labelPlayer07.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        labelPlayer07.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        labelPlayer07.setFocusable(false);
        labelPlayer07.setRequestFocusEnabled(false);
        labelPlayer07.setVerifyInputWhenFocusTarget(false);
        panelPlayersLeft.add(labelPlayer07);

        labelPlayer08.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        labelPlayer08.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        labelPlayer08.setFocusable(false);
        labelPlayer08.setRequestFocusEnabled(false);
        labelPlayer08.setVerifyInputWhenFocusTarget(false);
        panelPlayersLeft.add(labelPlayer08);

        panelPlayers.add(panelPlayersLeft);
        panelPlayersLeft.setBounds(0, 5, 90, 136);

        labelTableImage.setBackground(new java.awt.Color(51, 102, 255));
        labelTableImage.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelTableImage.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        labelTableImage.setFocusable(false);
        labelTableImage.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        labelTableImage.setOpaque(true);
        labelTableImage.setRequestFocusEnabled(false);
        labelTableImage.setVerifyInputWhenFocusTarget(false);
        panelPlayers.add(labelTableImage);
        labelTableImage.setBounds(95, 5, 40, 136);

        panelPlayersRight.setFocusable(false);
        panelPlayersRight.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        panelPlayersRight.setMaximumSize(new java.awt.Dimension(80, 132));
        panelPlayersRight.setMinimumSize(new java.awt.Dimension(80, 132));
        panelPlayersRight.setOpaque(false);
        panelPlayersRight.setPreferredSize(new java.awt.Dimension(80, 132));
        panelPlayersRight.setRequestFocusEnabled(false);
        panelPlayersRight.setVerifyInputWhenFocusTarget(false);
        panelPlayersRight.setLayout(new java.awt.GridLayout(8, 1));

        labelPlayer09.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        labelPlayer09.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        labelPlayer09.setFocusable(false);
        labelPlayer09.setRequestFocusEnabled(false);
        labelPlayer09.setVerifyInputWhenFocusTarget(false);
        panelPlayersRight.add(labelPlayer09);

        labelPlayer10.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        labelPlayer10.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        labelPlayer10.setFocusable(false);
        labelPlayer10.setRequestFocusEnabled(false);
        labelPlayer10.setVerifyInputWhenFocusTarget(false);
        panelPlayersRight.add(labelPlayer10);

        labelPlayer11.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        labelPlayer11.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        labelPlayer11.setFocusable(false);
        labelPlayer11.setRequestFocusEnabled(false);
        labelPlayer11.setVerifyInputWhenFocusTarget(false);
        panelPlayersRight.add(labelPlayer11);

        labelPlayer12.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        labelPlayer12.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        labelPlayer12.setFocusable(false);
        labelPlayer12.setRequestFocusEnabled(false);
        labelPlayer12.setVerifyInputWhenFocusTarget(false);
        panelPlayersRight.add(labelPlayer12);

        labelPlayer13.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        labelPlayer13.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        labelPlayer13.setFocusable(false);
        labelPlayer13.setRequestFocusEnabled(false);
        labelPlayer13.setVerifyInputWhenFocusTarget(false);
        panelPlayersRight.add(labelPlayer13);

        labelPlayer14.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        labelPlayer14.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        labelPlayer14.setFocusable(false);
        labelPlayer14.setRequestFocusEnabled(false);
        labelPlayer14.setVerifyInputWhenFocusTarget(false);
        panelPlayersRight.add(labelPlayer14);

        labelPlayer15.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        labelPlayer15.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        labelPlayer15.setFocusable(false);
        labelPlayer15.setRequestFocusEnabled(false);
        labelPlayer15.setVerifyInputWhenFocusTarget(false);
        panelPlayersRight.add(labelPlayer15);

        labelPlayer16.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        labelPlayer16.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        labelPlayer16.setFocusable(false);
        labelPlayer16.setRequestFocusEnabled(false);
        labelPlayer16.setVerifyInputWhenFocusTarget(false);
        panelPlayersRight.add(labelPlayer16);

        panelPlayers.add(panelPlayersRight);
        panelPlayersRight.setBounds(140, 5, 90, 136);

        javax.swing.GroupLayout panelInfoLayout = new javax.swing.GroupLayout(panelInfo);
        panelInfo.setLayout(panelInfoLayout);
        panelInfoLayout.setHorizontalGroup(
            panelInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelInfoLayout.createSequentialGroup()
                .addGroup(panelInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelInfoLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(panelInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(labelCardNumber)
                            .addGroup(panelInfoLayout.createSequentialGroup()
                                .addGroup(panelInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panelInfoLayout.createSequentialGroup()
                                        .addComponent(labelPack2)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(editPack2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panelInfoLayout.createSequentialGroup()
                                        .addComponent(labelPack1)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(editPack1, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panelInfoLayout.createSequentialGroup()
                                        .addComponent(labelPack3)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(panelInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(editCardNumber)
                                            .addComponent(editPack3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(panelInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(checkPack3)
                                    .addComponent(checkPack2)
                                    .addComponent(checkPack1)))
                            .addGroup(panelInfoLayout.createSequentialGroup()
                                .addComponent(buttonQuitTournament)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(editTimeRemaining, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(panelInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(panelPlayers, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(labelMessage, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 231, Short.MAX_VALUE))))
                    .addComponent(bigCard, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        panelInfoLayout.setVerticalGroup(
            panelInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelInfoLayout.createSequentialGroup()
                .addGroup(panelInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(buttonQuitTournament)
                    .addComponent(editTimeRemaining, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(checkPack1)
                    .addGroup(panelInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(labelPack1)
                        .addComponent(editPack1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelPack2)
                    .addComponent(editPack2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(checkPack2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelPack3)
                    .addComponent(editPack3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(checkPack3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelCardNumber)
                    .addComponent(editCardNumber, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(labelMessage, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelPlayers, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 55, Short.MAX_VALUE)
                .addComponent(bigCard, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        draftBooster.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout draftBoosterLayout = new javax.swing.GroupLayout(draftBooster);
        draftBooster.setLayout(draftBoosterLayout);
        draftBoosterLayout.setHorizontalGroup(
            draftBoosterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 738, Short.MAX_VALUE)
        );
        draftBoosterLayout.setVerticalGroup(
            draftBoosterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 439, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(panelInfo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(draftPicks, javax.swing.GroupLayout.DEFAULT_SIZE, 740, Short.MAX_VALUE)
                    .addComponent(draftBooster, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelInfo, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(draftPicks, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(draftBooster, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
    }// </editor-fold>//GEN-END:initComponents

     private void buttonQuitTournamentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonQuitTournamentActionPerformed
         UserRequestMessage message = new UserRequestMessage("Confirm quit tournament", "Are you sure you want to quit the draft tournament?");
         message.setButton1("No", null);
         message.setButton2("Yes", PlayerAction.CLIENT_QUIT_DRAFT_TOURNAMENT);
         message.setTournamentId(draftId);
         MageFrame.getInstance().showUserRequestDialog(message);
     }//GEN-LAST:event_buttonQuitTournamentActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private mage.client.cards.BigCard bigCard;
    private javax.swing.JButton buttonQuitTournament;
    private javax.swing.JCheckBox checkPack1;
    private javax.swing.JCheckBox checkPack2;
    private javax.swing.JCheckBox checkPack3;
    private mage.client.draft.DraftGrid draftBooster;
    private mage.client.cards.CardsList draftPicks;
    private javax.swing.JTextField editCardNumber;
    private javax.swing.JTextField editPack1;
    private javax.swing.JTextField editPack2;
    private javax.swing.JTextField editPack3;
    private javax.swing.JTextField editTimeRemaining;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel labelCardNumber;
    private javax.swing.JLabel labelMessage;
    private javax.swing.JLabel labelPack1;
    private javax.swing.JLabel labelPack2;
    private javax.swing.JLabel labelPack3;
    private javax.swing.JLabel labelPlayer01;
    private javax.swing.JLabel labelPlayer02;
    private javax.swing.JLabel labelPlayer03;
    private javax.swing.JLabel labelPlayer04;
    private javax.swing.JLabel labelPlayer05;
    private javax.swing.JLabel labelPlayer06;
    private javax.swing.JLabel labelPlayer07;
    private javax.swing.JLabel labelPlayer08;
    private javax.swing.JLabel labelPlayer09;
    private javax.swing.JLabel labelPlayer10;
    private javax.swing.JLabel labelPlayer11;
    private javax.swing.JLabel labelPlayer12;
    private javax.swing.JLabel labelPlayer13;
    private javax.swing.JLabel labelPlayer14;
    private javax.swing.JLabel labelPlayer15;
    private javax.swing.JLabel labelPlayer16;
    private javax.swing.JLabel labelTableImage;
    private javax.swing.JPanel panelInfo;
    private javax.swing.JPanel panelPlayers;
    private javax.swing.JPanel panelPlayersLeft;
    private javax.swing.JPanel panelPlayersRight;
    // End of variables declaration//GEN-END:variables

 }
