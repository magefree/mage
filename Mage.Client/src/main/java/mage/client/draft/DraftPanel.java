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
 import java.awt.dnd.DragSourceEvent;
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
     // starts with 1
     private int packNo;

     // Number of the current card pick (for draft log writing).
     // starts with 1
     private int pickNo;

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

         draftLeftPane.setOpaque(false);

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
             // Random draft
             this.txtPack1.setText("Random Boosters");
             this.txtPack2.setText("Random Boosters");
             this.txtPack3.setText("Random Boosters");
         } else {
             this.txtPack1.setText(draftView.getSets().get(0));
             this.txtPack2.setText(draftView.getSets().get(1));
             this.txtPack3.setText(draftView.getSets().get(2));
         }
         this.chkPack1.setSelected(draftView.getBoosterNum() > 1);
         this.chkPack2.setSelected(draftView.getBoosterNum() > 2);
         this.chkPack3.setSelected(draftView.getBoosterNum() > 3);
         this.txtCardNo.setText(Integer.toString(draftView.getCardNum()));

         packNo = draftView.getBoosterNum();
         pickNo = draftView.getCardNum();
         setCodes = draftView.getSetCodes();
         draftLogger.updateDraft(draftId, draftView);

         int right = draftView.getPlayers().size() / 2;
         int left = draftView.getPlayers().size() - right;
         int height = left * 18;
         lblTableImage.setSize(new Dimension(lblTableImage.getWidth(), height));
         Image tableImage = ImageHelper.getImageFromResources(draftView.getBoosterNum() % 2 == 1 ? "/draft/table_left.png" : "/draft/table_right.png");
         BufferedImage resizedTable = ImageHelper.getResizedImage(BufferedImageBuilder.bufferImage(tableImage, BufferedImage.TYPE_INT_ARGB), lblTableImage.getWidth(), lblTableImage.getHeight());
         lblTableImage.setIcon(new ImageIcon(resizedTable));

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
                 lblPlayer01.setText(name);
                 break;
             case 2:
                 lblPlayer02.setText(name);
                 break;
             case 3:
                 lblPlayer03.setText(name);
                 break;
             case 4:
                 lblPlayer04.setText(name);
                 break;
             case 5:
                 lblPlayer05.setText(name);
                 break;
             case 6:
                 lblPlayer06.setText(name);
                 break;
             case 7:
                 lblPlayer07.setText(name);
                 break;
             case 8:
                 lblPlayer08.setText(name);
                 break;
             case 9:
                 lblPlayer09.setText(name);
                 break;
             case 10:
                 lblPlayer10.setText(name);
                 break;
             case 11:
                 lblPlayer11.setText(name);
                 break;
             case 12:
                 lblPlayer12.setText(name);
                 break;
             case 13:
                 lblPlayer13.setText(name);
                 break;
             case 14:
                 lblPlayer14.setText(name);
                 break;
             case 15:
                 lblPlayer15.setText(name);
                 break;
             case 16:
                 lblPlayer16.setText(name);
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

         countdown.stop();
         this.timeout = draftPickView.getTimeout();
         setTimeout(timeout);
         if (timeout != 0) {
             countdown.start();
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
         this.txtTimeRemaining.setText(text);
         if (s == 6 && !draftBooster.isEmptyGrid()) {
             AudioManager.playOnCountdown1();
         }
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
         this.lblMessage.setText(message);
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
     // previous booster and it's sequence number (pack number / pick number)
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
         if (!setCodes.isEmpty()) {
             return setCodes.get(packNo - 1);
         } else {
             return "";
         }
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
         draftLeftPane = new javax.swing.JPanel();
         btnQuitTournament = new javax.swing.JButton();
         lblPack1 = new javax.swing.JLabel();
         txtPack1 = new javax.swing.JTextField();
         chkPack1 = new javax.swing.JCheckBox();
         lblPack2 = new javax.swing.JLabel();
         txtPack2 = new javax.swing.JTextField();
         chkPack2 = new javax.swing.JCheckBox();
         lblPack3 = new javax.swing.JLabel();
         txtPack3 = new javax.swing.JTextField();
         chkPack3 = new javax.swing.JCheckBox();
         lblCardNo = new javax.swing.JLabel();
         txtCardNo = new javax.swing.JTextField();
         txtTimeRemaining = new javax.swing.JTextField();
         lblMessage = new javax.swing.JLabel();
         bigCard = new mage.client.cards.BigCard();
         jPanel1 = new javax.swing.JPanel();
         pnlLeft = new javax.swing.JPanel();
         lblPlayer01 = new javax.swing.JLabel();
         lblPlayer02 = new javax.swing.JLabel();
         lblPlayer03 = new javax.swing.JLabel();
         lblPlayer04 = new javax.swing.JLabel();
         lblPlayer05 = new javax.swing.JLabel();
         lblPlayer06 = new javax.swing.JLabel();
         lblPlayer07 = new javax.swing.JLabel();
         lblPlayer08 = new javax.swing.JLabel();
         lblTableImage = new javax.swing.JLabel();
         pnlRight = new javax.swing.JPanel();
         lblPlayer09 = new javax.swing.JLabel();
         lblPlayer10 = new javax.swing.JLabel();
         lblPlayer11 = new javax.swing.JLabel();
         lblPlayer12 = new javax.swing.JLabel();
         lblPlayer13 = new javax.swing.JLabel();
         lblPlayer14 = new javax.swing.JLabel();
         lblPlayer15 = new javax.swing.JLabel();
         lblPlayer16 = new javax.swing.JLabel();
         draftPicks = new mage.client.cards.CardsList();
         draftBooster = new mage.client.cards.DraftGrid();

         draftLeftPane.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
         draftLeftPane.setFocusable(false);
         draftLeftPane.setRequestFocusEnabled(false);
         draftLeftPane.setVerifyInputWhenFocusTarget(false);

         btnQuitTournament.setText("Quit Tournament");
         btnQuitTournament.addActionListener(evt -> btnQuitTournamentActionPerformed(evt));

         lblPack1.setText("Pack 1:");

         txtPack1.setEditable(false);
         txtPack1.setEnabled(false);
         txtPack1.setPreferredSize(new java.awt.Dimension(130, 22));

         lblPack2.setText("Pack 2:");

         txtPack2.setEditable(false);
         txtPack2.setEnabled(false);
         txtPack2.setPreferredSize(new java.awt.Dimension(130, 22));

         lblPack3.setText("Pack 3:");

         txtPack3.setEditable(false);
         txtPack3.setEnabled(false);
         txtPack3.setPreferredSize(new java.awt.Dimension(130, 22));

         lblCardNo.setText("Card #:");

         txtCardNo.setEditable(false);
         txtCardNo.setEnabled(false);

         txtTimeRemaining.setEditable(false);
         txtTimeRemaining.setForeground(java.awt.Color.red);
         txtTimeRemaining.setHorizontalAlignment(javax.swing.JTextField.CENTER);
         txtTimeRemaining.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

         lblMessage.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
         lblMessage.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
         lblMessage.setOpaque(true);

         bigCard.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

         jPanel1.setOpaque(false);
         jPanel1.setLayout(null);

         pnlLeft.setFocusable(false);
         pnlLeft.setMaximumSize(new java.awt.Dimension(80, 132));
         pnlLeft.setMinimumSize(new java.awt.Dimension(80, 132));
         pnlLeft.setOpaque(false);
         pnlLeft.setPreferredSize(new java.awt.Dimension(80, 132));
         pnlLeft.setRequestFocusEnabled(false);
         pnlLeft.setVerifyInputWhenFocusTarget(false);
         pnlLeft.setLayout(new java.awt.GridLayout(8, 1));

         lblPlayer01.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
         lblPlayer01.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
         lblPlayer01.setFocusable(false);
         lblPlayer01.setRequestFocusEnabled(false);
         lblPlayer01.setVerifyInputWhenFocusTarget(false);
         pnlLeft.add(lblPlayer01);
         lblPlayer01.getAccessibleContext().setAccessibleName("");

         lblPlayer02.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
         lblPlayer02.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
         lblPlayer02.setFocusable(false);
         lblPlayer02.setRequestFocusEnabled(false);
         lblPlayer02.setVerifyInputWhenFocusTarget(false);
         pnlLeft.add(lblPlayer02);

         lblPlayer03.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
         lblPlayer03.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
         lblPlayer03.setFocusable(false);
         lblPlayer03.setRequestFocusEnabled(false);
         lblPlayer03.setVerifyInputWhenFocusTarget(false);
         pnlLeft.add(lblPlayer03);

         lblPlayer04.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
         lblPlayer04.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
         lblPlayer04.setFocusable(false);
         lblPlayer04.setRequestFocusEnabled(false);
         lblPlayer04.setVerifyInputWhenFocusTarget(false);
         pnlLeft.add(lblPlayer04);

         lblPlayer05.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
         lblPlayer05.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
         lblPlayer05.setFocusable(false);
         lblPlayer05.setRequestFocusEnabled(false);
         lblPlayer05.setVerifyInputWhenFocusTarget(false);
         pnlLeft.add(lblPlayer05);

         lblPlayer06.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
         lblPlayer06.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
         lblPlayer06.setFocusable(false);
         lblPlayer06.setRequestFocusEnabled(false);
         lblPlayer06.setVerifyInputWhenFocusTarget(false);
         pnlLeft.add(lblPlayer06);

         lblPlayer07.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
         lblPlayer07.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
         lblPlayer07.setFocusable(false);
         lblPlayer07.setRequestFocusEnabled(false);
         lblPlayer07.setVerifyInputWhenFocusTarget(false);
         pnlLeft.add(lblPlayer07);

         lblPlayer08.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
         lblPlayer08.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
         lblPlayer08.setFocusable(false);
         lblPlayer08.setRequestFocusEnabled(false);
         lblPlayer08.setVerifyInputWhenFocusTarget(false);
         pnlLeft.add(lblPlayer08);

         jPanel1.add(pnlLeft);
         pnlLeft.setBounds(0, 5, 90, 136);

         lblTableImage.setBackground(new java.awt.Color(51, 102, 255));
         lblTableImage.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
         lblTableImage.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
         lblTableImage.setFocusable(false);
         lblTableImage.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
         lblTableImage.setOpaque(true);
         lblTableImage.setRequestFocusEnabled(false);
         lblTableImage.setVerifyInputWhenFocusTarget(false);
         jPanel1.add(lblTableImage);
         lblTableImage.setBounds(95, 5, 40, 136);

         pnlRight.setFocusable(false);
         pnlRight.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
         pnlRight.setMaximumSize(new java.awt.Dimension(80, 132));
         pnlRight.setMinimumSize(new java.awt.Dimension(80, 132));
         pnlRight.setOpaque(false);
         pnlRight.setPreferredSize(new java.awt.Dimension(80, 132));
         pnlRight.setRequestFocusEnabled(false);
         pnlRight.setVerifyInputWhenFocusTarget(false);
         pnlRight.setLayout(new java.awt.GridLayout(8, 1));

         lblPlayer09.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
         lblPlayer09.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
         lblPlayer09.setFocusable(false);
         lblPlayer09.setRequestFocusEnabled(false);
         lblPlayer09.setVerifyInputWhenFocusTarget(false);
         pnlRight.add(lblPlayer09);

         lblPlayer10.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
         lblPlayer10.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
         lblPlayer10.setFocusable(false);
         lblPlayer10.setRequestFocusEnabled(false);
         lblPlayer10.setVerifyInputWhenFocusTarget(false);
         pnlRight.add(lblPlayer10);

         lblPlayer11.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
         lblPlayer11.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
         lblPlayer11.setFocusable(false);
         lblPlayer11.setRequestFocusEnabled(false);
         lblPlayer11.setVerifyInputWhenFocusTarget(false);
         pnlRight.add(lblPlayer11);

         lblPlayer12.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
         lblPlayer12.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
         lblPlayer12.setFocusable(false);
         lblPlayer12.setRequestFocusEnabled(false);
         lblPlayer12.setVerifyInputWhenFocusTarget(false);
         pnlRight.add(lblPlayer12);

         lblPlayer13.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
         lblPlayer13.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
         lblPlayer13.setFocusable(false);
         lblPlayer13.setRequestFocusEnabled(false);
         lblPlayer13.setVerifyInputWhenFocusTarget(false);
         pnlRight.add(lblPlayer13);

         lblPlayer14.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
         lblPlayer14.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
         lblPlayer14.setFocusable(false);
         lblPlayer14.setRequestFocusEnabled(false);
         lblPlayer14.setVerifyInputWhenFocusTarget(false);
         pnlRight.add(lblPlayer14);

         lblPlayer15.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
         lblPlayer15.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
         lblPlayer15.setFocusable(false);
         lblPlayer15.setRequestFocusEnabled(false);
         lblPlayer15.setVerifyInputWhenFocusTarget(false);
         pnlRight.add(lblPlayer15);

         lblPlayer16.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
         lblPlayer16.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
         lblPlayer16.setFocusable(false);
         lblPlayer16.setRequestFocusEnabled(false);
         lblPlayer16.setVerifyInputWhenFocusTarget(false);
         pnlRight.add(lblPlayer16);

         jPanel1.add(pnlRight);
         pnlRight.setBounds(140, 5, 90, 136);

         javax.swing.GroupLayout draftLeftPaneLayout = new javax.swing.GroupLayout(draftLeftPane);
         draftLeftPane.setLayout(draftLeftPaneLayout);
         draftLeftPaneLayout.setHorizontalGroup(
                 draftLeftPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                         .addGroup(draftLeftPaneLayout.createSequentialGroup()
                                 .addGroup(draftLeftPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                         .addGroup(draftLeftPaneLayout.createSequentialGroup()
                                                 .addContainerGap()
                                                 .addGroup(draftLeftPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                         .addComponent(lblCardNo)
                                                         .addGroup(draftLeftPaneLayout.createSequentialGroup()
                                                                 .addGroup(draftLeftPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                                                         .addGroup(javax.swing.GroupLayout.Alignment.LEADING, draftLeftPaneLayout.createSequentialGroup()
                                                                                 .addComponent(lblPack2)
                                                                                 .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                                 .addComponent(txtPack2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                                                         .addGroup(javax.swing.GroupLayout.Alignment.LEADING, draftLeftPaneLayout.createSequentialGroup()
                                                                                 .addComponent(lblPack1)
                                                                                 .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                                 .addComponent(txtPack1, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                         .addGroup(javax.swing.GroupLayout.Alignment.LEADING, draftLeftPaneLayout.createSequentialGroup()
                                                                                 .addComponent(lblPack3)
                                                                                 .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                                 .addGroup(draftLeftPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                                         .addComponent(txtCardNo)
                                                                                         .addComponent(txtPack3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                                                                 .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                 .addGroup(draftLeftPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                         .addComponent(chkPack3)
                                                                         .addComponent(chkPack2)
                                                                         .addComponent(chkPack1)))
                                                         .addGroup(draftLeftPaneLayout.createSequentialGroup()
                                                                 .addComponent(btnQuitTournament)
                                                                 .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                 .addComponent(txtTimeRemaining, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                         .addGroup(draftLeftPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                                                 .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                 .addComponent(lblMessage, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 231, Short.MAX_VALUE))))
                                         .addComponent(bigCard, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                 .addGap(0, 0, Short.MAX_VALUE))
         );
         draftLeftPaneLayout.setVerticalGroup(
                 draftLeftPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                         .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, draftLeftPaneLayout.createSequentialGroup()
                                 .addGroup(draftLeftPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                         .addComponent(btnQuitTournament)
                                         .addComponent(txtTimeRemaining, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                                 .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                 .addGroup(draftLeftPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                         .addComponent(chkPack1)
                                         .addGroup(draftLeftPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                 .addComponent(lblPack1)
                                                 .addComponent(txtPack1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                 .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                 .addGroup(draftLeftPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                         .addComponent(lblPack2)
                                         .addComponent(txtPack2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                         .addComponent(chkPack2))
                                 .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                 .addGroup(draftLeftPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                         .addComponent(lblPack3)
                                         .addComponent(txtPack3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                         .addComponent(chkPack3))
                                 .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                 .addGroup(draftLeftPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                         .addComponent(lblCardNo)
                                         .addComponent(txtCardNo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                 .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                 .addComponent(lblMessage, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                 .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                 .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                                 .addComponent(draftLeftPane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                 .addGap(0, 0, 0)
                                 .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                         .addComponent(draftPicks, javax.swing.GroupLayout.DEFAULT_SIZE, 740, Short.MAX_VALUE)
                                         .addComponent(draftBooster, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
         );
         layout.setVerticalGroup(
                 layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                         .addComponent(draftLeftPane, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                         .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                 .addComponent(draftPicks, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                                 .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                 .addComponent(draftBooster, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
         );
     }// </editor-fold>//GEN-END:initComponents

     private void btnQuitTournamentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnQuitTournamentActionPerformed
         UserRequestMessage message = new UserRequestMessage("Confirm quit tournament", "Are you sure you want to quit the draft tournament?");
         message.setButton1("Yes", PlayerAction.CLIENT_QUIT_DRAFT_TOURNAMENT);
         message.setButton2("No", null);
         message.setTournamentId(draftId);
         MageFrame.getInstance().showUserRequestDialog(message);
     }//GEN-LAST:event_btnQuitTournamentActionPerformed

     // Variables declaration - do not modify//GEN-BEGIN:variables
     private mage.client.cards.BigCard bigCard;
     private javax.swing.JButton btnQuitTournament;
     private javax.swing.JCheckBox chkPack1;
     private javax.swing.JCheckBox chkPack2;
     private javax.swing.JCheckBox chkPack3;
     private mage.client.cards.DraftGrid draftBooster;
     private javax.swing.JPanel draftLeftPane;
     private mage.client.cards.CardsList draftPicks;
     private javax.swing.JPanel jPanel1;
     private javax.swing.JSeparator jSeparator1;
     private javax.swing.JLabel lblCardNo;
     private javax.swing.JLabel lblMessage;
     private javax.swing.JLabel lblPack1;
     private javax.swing.JLabel lblPack2;
     private javax.swing.JLabel lblPack3;
     private javax.swing.JLabel lblPlayer01;
     private javax.swing.JLabel lblPlayer02;
     private javax.swing.JLabel lblPlayer03;
     private javax.swing.JLabel lblPlayer04;
     private javax.swing.JLabel lblPlayer05;
     private javax.swing.JLabel lblPlayer06;
     private javax.swing.JLabel lblPlayer07;
     private javax.swing.JLabel lblPlayer08;
     private javax.swing.JLabel lblPlayer09;
     private javax.swing.JLabel lblPlayer10;
     private javax.swing.JLabel lblPlayer11;
     private javax.swing.JLabel lblPlayer12;
     private javax.swing.JLabel lblPlayer13;
     private javax.swing.JLabel lblPlayer14;
     private javax.swing.JLabel lblPlayer15;
     private javax.swing.JLabel lblPlayer16;
     private javax.swing.JLabel lblTableImage;
     private javax.swing.JPanel pnlLeft;
     private javax.swing.JPanel pnlRight;
     private javax.swing.JTextField txtCardNo;
     private javax.swing.JTextField txtPack1;
     private javax.swing.JTextField txtPack2;
     private javax.swing.JTextField txtPack3;
     private javax.swing.JTextField txtTimeRemaining;
     // End of variables declaration//GEN-END:variables

 }
