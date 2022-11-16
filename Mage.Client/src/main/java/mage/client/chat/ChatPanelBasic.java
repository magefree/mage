package mage.client.chat;

import mage.client.MageFrame;
import mage.client.SessionHandler;
import mage.client.cards.BigCard;
import mage.client.dialog.PreferencesDialog;
import mage.client.util.audio.AudioManager;
import mage.client.util.GUISizeHelper;
import mage.client.util.IgnoreList;
import mage.view.ChatMessage.MessageColor;
import mage.view.ChatMessage.MessageType;
import org.mage.card.arcane.ManaSymbols;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import mage.view.ChatMessage;

/**
 * @author BetaSteward_at_googlemail.com, nantuko
 */
public class ChatPanelBasic extends javax.swing.JPanel {

    /**
     * Time formatter
     */
    protected final DateFormat timeFormatter = DateFormat.getTimeInstance(DateFormat.SHORT);

    protected UUID chatId;
    /**
     * Chat message color for opponents.
     */
    protected static final String OPPONENT_COLOR = "#FF7F50";
    /**
     * Chat message color for client player.
     */
    protected static final String MY_COLOR = "#7FFFD4";
    /**
     * Chat message color for timestamps.
     */
    protected static final String TIMESTAMP_COLOR = "#CCCC33";
    /**
     * Chat message color for messages.
     */
    protected static final String MESSAGE_COLOR = "White";
    /**
     * Chat message color for personal infos.
     */
    protected static final String USER_INFO_COLOR = "Yellow";
    /**
     * Chat message color for status infos.
     */
    protected static final String STATUS_COLOR = "#FFCC33";
    /**
     * Alpha value for transparency (255 = not transparent)
     */
    public static final int CHAT_ALPHA = 80;
    /**
     * This will be a chat that will be connected to {this} and will handle
     * redirected messages; Mostly used to redirect user messages to another
     * window.
     */
    protected ChatPanelBasic connectedChat;
    /**
     * Parent chat this chat connected to. Used to send messages using parent
     * chat as it is the only one connected to server.
     */
    protected ChatPanelBasic parentChatRef;
    /**
     * Selected extended view mode.
     */
    protected VIEW_MODE extendedViewMode = VIEW_MODE.NONE;

    public enum VIEW_MODE {

        NONE, GAME, CHAT
    }

    /**
     * Controls the output start messages as the chat panel is created
     */
    protected ChatType chatType = ChatType.DEFAULT;

    public enum ChatType {

        DEFAULT, GAME, TABLES, TOURNAMENT
    }

    protected boolean startMessageDone = false;

    /**
     * Creates new form ChatPanel
     */
    public ChatPanelBasic() {
        initComponents();
        setBackground(new Color(0, 0, 0, CHAT_ALPHA));
        changeGUISize(GUISizeHelper.chatFont);
        if (jScrollPaneTxt != null) {
            jScrollPaneTxt.setBackground(new Color(0, 0, 0, CHAT_ALPHA));
            jScrollPaneTxt.getViewport().setBackground(new Color(0, 0, 0, CHAT_ALPHA));
            jScrollPaneTxt.setViewportBorder(null);
        }

    }

    public void cleanUp() {
    }

    public void setGameData(UUID gameId, BigCard bigCard) {
        txtConversation.setGameData(gameId, bigCard);
    }

    public void changeGUISize(Font font) {
        txtConversation.setFont(font);
        txtMessage.setFont(font);
        if (jScrollPaneTxt != null) {
            jScrollPaneTxt.setFont(font);
            jScrollPaneTxt.getVerticalScrollBar().setPreferredSize(new Dimension(GUISizeHelper.scrollBarSize, 0));
            jScrollPaneTxt.getHorizontalScrollBar().setPreferredSize(new Dimension(0, GUISizeHelper.scrollBarSize));
        }
        int height = 30;
        if (font.getSize() > 20) {
            height = 30 + Math.min(font.getSize() - 10, 30);
        }
        txtMessage.setMinimumSize(new Dimension(20, height));
        txtMessage.setMaximumSize(new Dimension(txtMessage.getWidth(), height));
        txtMessage.setPreferredSize(new Dimension(txtMessage.getWidth(), height));
        txtMessage.setSize(new Dimension(txtMessage.getWidth(), height));
        if (connectedChat != null) {
            connectedChat.changeGUISize(font);
        }
    }

    public ChatType getChatType() {
        return chatType;
    }

    public void setChatType(ChatType chatType) {
        this.chatType = chatType;
    }

    public boolean isStartMessageDone() {
        return startMessageDone;
    }

    public void setStartMessageDone(boolean startMessageDone) {
        this.startMessageDone = startMessageDone;
    }

    public void connect(UUID chatId) {
        this.chatId = chatId;
        SessionHandler.joinChat(chatId);
        MageFrame.addChat(chatId, this);
    }

    public void disconnect() {
        if (chatId != null && parentChatRef == null && SessionHandler.isConnected()) {
            SessionHandler.leaveChat(chatId);
            MageFrame.removeChat(chatId);
        }
    }
    
    public void receiveMessage(ChatMessage message) {
        // play the to the message connected sound
        if (message.getSoundToPlay() != null) {
            switch (message.getSoundToPlay()) {
                case PlayerLeft:
                    AudioManager.playPlayerLeft();
                    break;
                case PlayerQuitTournament:
                    AudioManager.playPlayerQuitTournament();
                    break;
                case PlayerSubmittedDeck:
                    AudioManager.playPlayerSubmittedDeck();
                    break;
                case PlayerWhispered:
                    AudioManager.playPlayerWhispered();
                    break;
            }
        }
        // send start message to chat if not done yet
        if (!isStartMessageDone()) {
            createChatStartMessage();
        }
        receiveMessage(message.getUsername(), message.getMessage(), message.getTime(), message.getTurnInfo(), message.getMessageType(), message.getColor());
    }

    Pattern profanityPattern = Pattern.compile(".*(1ab1a|1d1ot|13p3r|13sb1ans|13sbo|13s13|13sb1an|13sbo|13sy|1nbr3d|1nc3st|1njun|1ub3|\\Wbj|\\Wcum|\\Wdum|\\Wfag|\\Wfap|\\W[sf]uk|\\Wj1s|\\Wp3do|\\Wp33|\\Wpoo\\W|\\Wt1t|aho13|an1ngu|ana1|anus|ar3o1a|ar3o13|ary1an|axyx|axyxhat|axyxho13|axyxmast3r|axyxmunch|axyxw1p3|b1atch|b1gt1t|b1mbo|b1ow|b1tch|ba1s|bab3|bang|barf|bastard|bawdy|b3an3r|b3ard3dc1am|b3ast1a1ty|b3atch|b3at3r|b3av3r|b3otch|b3yotch|bo1nk|bod1y|bon3d|bon3r|bon3|bob|bot13|boty|bow31|br3ast|bug3r|bukak3|bung|busty|buxyx|c1t|caca|cahon3|cam31to3|carp3tmunch3r|cawk|c3rv1x|ch1nc|ch1nk|chod3|co1ta1|cockb1ock|cockho1st3r|cocknock3r|cocksmok3r|cocksuck3r|cock|condom|corksuck3r|crabs|cums1ut|cumshot|cumsta1n|cnt|cun1ngus|cuntfac3|cunthunt3r|cunt|d1ck|d1k3|d1do|d1mw1t|d1ng13|d1psh1p|dago|dam1t|damn1t|damn3d|damn|dawg13sty13|dog13sty13|dogysty13|dong|dop3y|douch3|drunk|dumb|dumas|dum|dumbas|dumy|dyk3|3jacu1at3|3n1arg3m3nt|3r3ct1on|3r3ct|3rot1c|3xtacy|3xtasy|f.ck|f1osy|f1st3d|f1st1ng|f1sty|fa1gt|fa1g|fack|fag1t|fag3d|fagot|fag|[sf]cuk|f31at1o|f31at3|f31ch1ng|f31ch3r|f31ch|f31tch3r|f31tch|foad|fobar|fond13|for3sk1n|fu.k|fudg3pack3r|[sf]uk|g1ans|g1go1o|ganja|ghay|gh3y|go1d3nshow3r|gonad|gok|gr1ngo|h1t13r|handjob|hardon|hokah|hok3r|homo|honky|hor|hotch|hot3r|horny|hump1ng|hump3d|hump|hym3n|j1sm|j1s3d|j1sm|j1s|jackas|jackho13|jackof|j3rk3d|j3rkof|j3rk|junk13|junky|k1an|k1k3|k1nky|knob3nd|kyk3|mams|masa|mast3rba|masturba|max1|m3ns3s|m3nstruat|m[sf]uck1ng|mofo|moron|moth3rf|mthrf|muf|n1ger|n1ga|n1mrod|n1ny|n1p13|nak3d|napa1m|napy|nas1|n3gro|noky|nympho|op1at3|op1um|ora1y|ora1|org13s|organ|orgasm|orgy|ovary|ovum|p1owb1t3r|p1mp|p1nko|p1s3d|p1sof|p1s|pak1|pant13|panty|past13|pasty|p3ck3r|p3doph1|p3p3|p3n1a1|p3n13|p3n1s|p3n3trat1on|p3n3trat3|p3rv3rs1on|p3yot3|pha1c|phuck|po1ack|po1ock|pontang|pop|pr1ck|pr1g|pron|pub1|pub3|punkas|punky|pus1|pusy|puto|qu1cky|qu1ck13|qu1m|qu3af|qu3ro|qu3rs|qu3r|r1mjob|r1tard|racy|rap1st|rap3d|rap3r|rap3|raunch|r31ch|r3cta1|r3ctum|r3ctus|r3tard|r3tar|rtard|rumpram3r|rump|s1av3|s13as|s1ut|sack|sad1s|scag|sch1ong|sch1so|scr3w|scrog|scrot|scrud|scum|s3aman|s3am3n|s3duc3|s3m3n|s3xua1|sh1t|skag|skank|sm3gma|smut|sn1p3r|snatch|sodom|sp1ck|sp1c|sp1k|sp3rm|spunk|st3amy|stfu|ston3d|str1p|strok3|stup1d|suck|sumofab1atch|t1nk13|t1t[sf]uck|tampon|tard|t3abag1ng|t3at|t3st1|t3st3|t3urd|thrust|tramp|trans|trashy|twat|ug1y|unw3d|ur1n3a|ut3rus|vag1na|vu1gar|vu1va|w1g3r|wang|wank3r|wank|w31n3r|w31rdo|w3dg13|w3n13|w3tback|w3w3|wh1t3y|wh1s|whor3).*");
    Pattern profanity2Pattern = Pattern.compile(".*(1ab1a|1d1ot|13p3r|13sb1ans|13sbo|13s13|13sb1an|13sbo|13sy|1nbr3d|1nc3st|1njun|1ub3|\\Wbj|\\Wcum|\\Wdum|\\Wfag|\\Wfap|\\W[sf]uk|\\Wj1s|\\Wp3do|\\Wp3|\\Wpo\\W|\\Wt1t|aho13|an1ngu|ana1|anus|ar3o1a|ar3o13|ary1an|axyx|axyxhat|axyxho13|axyxmast3r|axyxmunch|axyxw1p3|b1atch|b1gt1t|b1mbo|b1ow|b1tch|ba1s|bab3|bang|barf|bastard|bawdy|b3an3r|b3ard3dc1am|b3ast1a1ty|b3atch|b3at3r|b3av3r|b3otch|b3yotch|bo1nk|bod1y|bon3d|bon3r|bon3|bob|bot13|boty|bow31|br3ast|bug3r|bukak3|bung|busty|buxyx|c1t|caca|cahon3|cam31to3|carp3tmunch3r|cawk|c3rv1x|ch1nc|ch1nk|chod3|co1ta1|cockb1ock|cockho1st3r|cocknock3r|cocksmok3r|cocksuck3r|cock|condom|corksuck3r|crabs|cums1ut|cumshot|cumsta1n|cnt|cun1ngus|cuntfac3|cunthunt3r|cunt|d1ck|d1k3|d1do|d1mw1t|d1ng13|d1psh1p|dago|dam1t|damn1t|damn3d|damn|dawg13sty13|dog13sty13|dogysty13|dong|dop3y|douch3|drunk|dumb|dum|dumas|dumbas|dumy|dyk3|3jacu1at3|3n1arg3m3nt|3r3ct1on|3r3ct|3rot1c|3xtacy|3xtasy|f.ck|f1osy|f1st3d|f1st1ng|f1sty|fa1gt|fa1g|fack|fag1t|fag3d|fagot|fag|[sf]cuk|f31at1o|f31at3|f31ch1ng|f31ch3r|f31ch|f31tch3r|f31tch|foad|fobar|fond13|for3sk1n|fu.k|fudg3pack3r|[sf]uk|g1ans|g1go1o|ganja|ghay|gh3y|go1d3nshow3r|gonad|gr1ngo|h1t13r|handjob|hardon|hokah|hok3r|homo|honky|hor|hotch|hot3r|horny|hump1ng|hump3d|hump|hym3n|j1sm|j1s3d|j1sm|j1s|jackas|jackho13|jackof|j3rk3d|j3rkof|j3rk|junk13|junky|k1an|k1k3|k1nky|knob3nd|kyk3|mams|masa|mast3rba|masturba|max1|m3ns3s|m3nstruat|m[sf]uck1ng|mofo|moron|moth3rf|mthrf|muf|n1ga|n1ger|n1mrod|n1ny|n1p13|nak3d|napa1m|napy|nas1|n3gro|noky|nympho|op1at3|op1um|ora1y|ora1|org13s|organ|orgasm|orgy|ovary|ovum|p1owb1t3r|p1mp|p1nko|p1s3d|p1sof|p1s|pak1|pant13|panty|past13|pasty|p3ck3r|p3doph1|p3p3|p3n1a1|p3n13|p3n1s|p3n3trat1on|p3n3trat3|p3rv3rs1on|p3yot3|pha1c|phuck|po1ack|po1ock|pontang|pop|porno|porn|pr1ck|pr1g|pron|pub1|pub3|punkas|punky|pus1|pusy|puto|qu1cky|qu1ck13|qu1m|qu3af|qu3ro|qu3rs|qu3r|r1mjob|r1tard|racy|rap1st|rap3d|rap3r|rap3|raunch|r31ch|r3cta1|r3ctum|r3ctus|r3tard|r3tar|rtard|rumpram3r|rump|s1av3|s13as|s1ut|sack|sad1s|scag|sch1ong|sch1so|scr3w|scrog|scrot|scrud|scum|s3aman|s3am3n|s3duc3|s3m3n|s3xua1|sh1t|skag|skank|sm3gma|smut|sn1p3r|snatch|sodom|sp1ck|sp1c|sp1k|sp3rm|spunk|st3amy|stfu|ston3d|str1p|strok3|stup1d|suck|sumofab1atch|t1nk13|t1t[sf]uck|tampon|tard|t3abag1ng|t3at|t3st1|t3st3|t3urd|thrust|tramp|trans|trashy|twat|ug1y|unw3d|ur1n3a|ut3rus|vag1na|vu1gar|vu1va|w1g3r|wang|wank3r|wank|w31n3r|w31rdo|w3dg13|w3n13|w3tback|w3w3|wh1t3y|wh1s|whor3).*");

    private boolean containsSwearing(String message, String level) {

        if (level.equals("0")) {
            return false;
        }
        message = '.' + message + '.';

        message = message.toLowerCase(Locale.ENGLISH);
        message = message.replaceAll("[a@]([s5][s5]+)", "axyx");
        message = message.replaceAll("b.([t\\+][t\\+]+)", "buxyx");
        message = message.replaceAll("(.)(\\1{1,})", "$1");
        message = message.replaceAll("[@]", "a");
        message = message.replaceAll("[il]", "1");
        message = message.replaceAll("[e]", "3");
        message = message.replaceAll("[0]", "o");
        message = message.replaceAll("[5z]", "s");
        message = message.replaceAll("\\W", ".");
        message = message.replaceAll("(.)(\\1{1,})", "$1");
        message = message.replaceAll("\\.", "");

        Matcher matchPattern = profanityPattern.matcher(message);
        if (matchPattern.find()) {
            return true;
        }

        if (level.equals("2")) {
            message = message.replaceAll("\\.", "");
            message = '.' + message + '.';
            matchPattern = profanity2Pattern.matcher(message);
            return matchPattern.find();
        }
        return false;
    }

    Pattern cardNamePattern = Pattern.compile(".*<font bgcolor=orange.*?</font>.*");

    /**
     * Display message in the chat. Use different colors for timestamp, username
     * and message.
     *
     * @param username    message sender
     * @param message     message itself
     * @param time        timestamp
     * @param turnInfo    game turn info, can be null for non game messages
     * @param messageType
     * @param color       Preferred color. Not used.
     */
    protected void receiveMessage(String username, String message, Date time, String turnInfo, MessageType messageType, MessageColor color) {
        StringBuilder text = new StringBuilder();
        if (time != null) {
            text.append(getColoredText(TIMESTAMP_COLOR, timeFormatter.format(time) + getTurnInfoPart(turnInfo) + ": "));
        }
        String userColor;
        String textColor;
        String userSeparator = " ";
        switch (messageType) {
            case STATUS: // a message to all chat user
                textColor = STATUS_COLOR;
                userColor = STATUS_COLOR;
                break;
            case USER_INFO: // a personal message
                textColor = USER_INFO_COLOR;
                userColor = USER_INFO_COLOR;
                break;
            default:
                userColor = SessionHandler.getUserName().equals(username) ? MY_COLOR : OPPONENT_COLOR;
                textColor = MESSAGE_COLOR;
                userSeparator = ": ";
        }
        if (color == MessageColor.ORANGE) {
            textColor = "Orange";
        }
        if (color == MessageColor.YELLOW) {
            textColor = "Yellow";
        }
        if (messageType == MessageType.WHISPER_FROM) {
            if (username.equalsIgnoreCase(SessionHandler.getUserName())) {
                if (message.toLowerCase(Locale.ENGLISH).startsWith("profanity 0")) {
                    PreferencesDialog.saveValue(PreferencesDialog.KEY_GAME_USE_PROFANITY_FILTER, "0");
                } else if (message.toLowerCase(Locale.ENGLISH).startsWith("profanity 1")) {
                    PreferencesDialog.saveValue(PreferencesDialog.KEY_GAME_USE_PROFANITY_FILTER, "1");
                } else if (message.toLowerCase(Locale.ENGLISH).startsWith("profanity 2")) {
                    PreferencesDialog.saveValue(PreferencesDialog.KEY_GAME_USE_PROFANITY_FILTER, "2");
                }
            }
            username = "Whisper from " + username;
        }
        if (messageType == MessageType.WHISPER_TO) {
            username = "Whisper to " + username;
        }

        Matcher matchPattern = cardNamePattern.matcher(message);
        String messageToTest = message;
        while (matchPattern.find()) {
            messageToTest = message.replaceFirst("<font bgcolor=orange.*?</font>", "");
        }

        if (messageType == MessageType.USER_INFO || messageType == MessageType.GAME || messageType == MessageType.STATUS
                || PreferencesDialog.getCachedValue(PreferencesDialog.KEY_GAME_USE_PROFANITY_FILTER, "0").equals("0")
                || !PreferencesDialog.getCachedValue(PreferencesDialog.KEY_GAME_USE_PROFANITY_FILTER, "0").equals("0") && !containsSwearing(messageToTest, PreferencesDialog.getCachedValue(PreferencesDialog.KEY_GAME_USE_PROFANITY_FILTER, "0"))) {
            if (username != null && !username.isEmpty()) {
                text.append(getColoredText(userColor, username + userSeparator));
            }
            text.append(getColoredText(textColor, ManaSymbols.replaceSymbolsWithHTML(message, ManaSymbols.Type.CHAT)));
            this.txtConversation.append(text.toString());
        } else if (PreferencesDialog.getCachedValue(PreferencesDialog.KEY_GAME_USE_PROFANITY_FILTER, "0").equals("1")) {
            if (username != null && !username.isEmpty()) {
                text.append(getColoredText("black", username + userSeparator));
            }
            text.append(getColoredText(textColor, ManaSymbols.replaceSymbolsWithHTML("<font color=black size=-2>" + message + "</font> <font size=-2>Profanity detected.  Type: <font color=green>/w " + SessionHandler.getUserName() + " profanity 0</font>' to turn the filter off</font></font>", ManaSymbols.Type.CHAT)));
            this.txtConversation.append(text.toString());
        } else if (PreferencesDialog.getCachedValue(PreferencesDialog.KEY_GAME_USE_PROFANITY_FILTER, "0").equals("2")) {
            text.append(getColoredText(textColor, ManaSymbols.replaceSymbolsWithHTML("<font color=black size=-2>" + username + ": Profanity detected.  To make it less strict, type: </font> <font color=green size=-2>/w " + SessionHandler.getUserName() + " profanity 1</font>", ManaSymbols.Type.CHAT)));
            this.txtConversation.append(text.toString());
        }
    }
    
    private void createChatStartMessage() {
        setStartMessageDone(true);
        switch (getChatType()) {
            case GAME:
                receiveMessage("", new StringBuilder()
                                .append("HOTKEYS:")
                                .append("<br/>Turn mousewheel up (ALT-e) - enlarge image of card the mousepointer hovers over")
                                .append("<br/>Turn mousewheel down (ALT-s) - enlarge original/alternate image of card the mousepointer hovers over")
                                .append("<br/><b>")
                                .append(KeyEvent.getKeyText(PreferencesDialog.getCurrentControlKey(PreferencesDialog.KEY_CONTROL_CONFIRM)))
                                .append("</b> - Confirm \"Ok\", \"Yes\" or \"Done\" button")
                                .append("<br/><b>")
                                .append(KeyEvent.getKeyText(PreferencesDialog.getCurrentControlKey(PreferencesDialog.KEY_CONTROL_NEXT_TURN)))
                                .append("</b> - Skip current turn but stop on declare attackers/blockers and something on the stack")
                                .append("<br/><b>")
                                .append(KeyEvent.getKeyText(PreferencesDialog.getCurrentControlKey(PreferencesDialog.KEY_CONTROL_END_STEP)))
                                .append("</b> - Skip to next end step but stop on declare attackers/blockers and something on the stack")
                                .append("<br/><b>")
                                .append(KeyEvent.getKeyText(PreferencesDialog.getCurrentControlKey(PreferencesDialog.KEY_CONTROL_SKIP_STEP)))
                                .append("</b> - Skip current turn but stop on declare attackers/blockers")
                                .append("<br/><b>")
                                .append(KeyEvent.getKeyText(PreferencesDialog.getCurrentControlKey(PreferencesDialog.KEY_CONTROL_MAIN_STEP)))
                                .append("</b> - Skip to next main phase but stop on declare attackers/blockers and something on the stack")
                                .append("<br/><b>")
                                .append(KeyEvent.getKeyText(PreferencesDialog.getCurrentControlKey(PreferencesDialog.KEY_CONTROL_YOUR_TURN)))
                                .append("</b> - Skip everything until your next turn")
                                .append("<br/><b>")
                                .append(KeyEvent.getKeyText(PreferencesDialog.getCurrentControlKey(PreferencesDialog.KEY_CONTROL_PRIOR_END)))
                                .append("</b> - Skip everything until the end step just prior to your turn")
                                .append("<br/><b>")
                                .append(KeyEvent.getKeyText(PreferencesDialog.getCurrentControlKey(PreferencesDialog.KEY_CONTROL_CANCEL_SKIP)))
                                .append("</b> - Undo F4/F5/F7/F9/F11")
                                .append("<br/><b>")
                                .append(KeyEvent.getKeyText(PreferencesDialog.getCurrentControlKey(PreferencesDialog.KEY_CONTROL_SWITCH_CHAT)))
                                .append("</b> - Switch in/out to chat text field")
                                /*
                                .append("<br/><b>")
                                .append(KeyEvent.getKeyText(PreferencesDialog.getCurrentControlKey(PreferencesDialog.KEY_CONTROL_TOGGLE_MACRO)))
                                .append("</b> - Toggle recording a sequence of actions to repeat. Will not pause if interrupted and can fail if a selected card changes such as when scrying top card to bottom.")
                                .append("<br/><b>").append(System.getProperty("os.name").contains("Mac OS X") ? "Cmd" : "Ctrl").append(" + click</b> - Hold priority while casting a spell or activating an ability")
                                 */
                                .append("<br/>")
                                .append("<br/>")
                                .append("CHAT COMMANDS:")
                                .append("<br/>").append("<b>/h username </b> - show player's stats (history)")
                                .append("<br/>").append("<b>/w username message</b> - send private message to player (whisper)")
                                .append("<br/>").append("<b>/pings</b> - show players and watchers ping")
                                .append("<br/>").append("<b>/fix</b> - fix freezed game")
                                .toString(),
                        null, null, MessageType.USER_INFO, ChatMessage.MessageColor.BLUE);
                break;
            case TOURNAMENT:
                receiveMessage("", "On this panel you can see the players, their state and the results of the games of the tournament. Also you can chat with the competitors of the tournament.",
                        null, null, MessageType.USER_INFO, ChatMessage.MessageColor.BLUE);
                break;
            case TABLES:
                String serverAddress = SessionHandler.getServerHostname().orElse("");
                receiveMessage("", new StringBuilder("Download card images by using the \"Images\" main menu.")
                                .append("<br/>Download icons and symbols by using the \"Symbols\" main menu.")
                                .append("<br/>\\list - show a list of available chat commands.")
                                .append("<br/>").append(IgnoreList.usage(serverAddress))
                                .append("<br/>Type <font color=green>\\w yourUserName profanity 0 (or 1 or 2)</font> to turn off/on the profanity filter").toString(),
                        null, null, MessageType.USER_INFO, ChatMessage.MessageColor.BLUE);
                break;
            default:
                break;

        }
    }

    String getTurnInfoPart(String turnInfo) {
        boolean canUse = Boolean.parseBoolean(PreferencesDialog.getCachedValue(PreferencesDialog.KEY_GAME_LOG_SHOW_TURN_INFO, "true"));
        return (turnInfo == null || !canUse ? "" : ", " + turnInfo);
    }

    protected String getColoredText(String color, String text) {
        return "<font color='" + color + "'>" + text + "</font>";
    }

    public String getText() {
        return txtConversation.getText();
    }

    public ChatPanelBasic getConnectedChat() {
        return connectedChat;
    }

    public void setConnectedChat(ChatPanelBasic connectedChat) {
        this.connectedChat = connectedChat;
    }

    public void setParentChat(ChatPanelBasic parentChatRef) {
        this.parentChatRef = parentChatRef;
    }

    public ChatPanelBasic getParentChatRef() {
        return parentChatRef;
    }

    public void setParentChatRef(ChatPanelBasic parentChatRef) {
        this.parentChatRef = parentChatRef;
    }

    public void disableInput() {
        this.txtMessage.setVisible(false);
    }

    public JTextField getTxtMessageInputComponent() {
        return this.txtMessage;
    }

    public void useExtendedView(VIEW_MODE extendedViewMode) {
        this.extendedViewMode = extendedViewMode;
        int alpha = 255;
        switch (chatType) {
            case GAME:
            case TABLES:
            case DEFAULT:
                alpha = CHAT_ALPHA;
        }
        this.txtConversation.setExtBackgroundColor(new Color(0, 0, 0, alpha)); // Alpha = 255 not transparent
        this.txtConversation.setSelectionColor(Color.LIGHT_GRAY);
        this.jScrollPaneTxt.setOpaque(alpha == 255);
        this.jScrollPaneTxt.getViewport().setOpaque(chatType != ChatType.TABLES);
    }

    public void clear() {
        this.txtConversation.setText("");
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPaneTxt = new javax.swing.JScrollPane();
        txtConversation = new mage.client.components.ColorPane();
        txtMessage = new javax.swing.JTextField();

        jScrollPaneTxt.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jScrollPaneTxt.setPreferredSize(new java.awt.Dimension(32767, 32767));

        txtConversation.setEditable(false);
        txtConversation.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        txtConversation.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        txtConversation.setFocusCycleRoot(false);
        txtConversation.setMargin(new java.awt.Insets(2, 2, 2, 2));
        txtConversation.setOpaque(false);
        jScrollPaneTxt.setViewportView(txtConversation);

        txtMessage.setMaximumSize(new java.awt.Dimension(5000, 70));
        txtMessage.setMinimumSize(new java.awt.Dimension(6, 70));
        txtMessage.setName(""); // NOI18N
        txtMessage.setPreferredSize(new java.awt.Dimension(6, 70));
        txtMessage.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtMessageKeyTyped(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jScrollPaneTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addComponent(txtMessage, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(jScrollPaneTxt, javax.swing.GroupLayout.DEFAULT_SIZE, 180, Short.MAX_VALUE)
                                .addGap(0, 0, 0)
                                .addComponent(txtMessage, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
    }// </editor-fold>//GEN-END:initComponents

    public void handleKeyTyped(java.awt.event.KeyEvent evt) {
        if (evt.getKeyChar() == KeyEvent.VK_ENTER) {
            if (parentChatRef != null) {
                SessionHandler.sendChatMessage(parentChatRef.chatId, this.txtMessage.getText());
            } else {
                SessionHandler.sendChatMessage(chatId, this.txtMessage.getText());
            }
            this.txtMessage.setText("");
            this.txtMessage.repaint();
        }
    }

    public void enableHyperlinks() {
        txtConversation.enableHyperlinks();
    }

    private void txtMessageKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtMessageKeyTyped
        handleKeyTyped(evt);
    }//GEN-LAST:event_txtMessageKeyTyped

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPaneTxt;
    private mage.client.components.ColorPane txtConversation;
    private javax.swing.JTextField txtMessage;
    // End of variables declaration//GEN-END:variables
}
