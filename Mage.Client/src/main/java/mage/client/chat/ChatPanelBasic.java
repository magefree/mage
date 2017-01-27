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
 * ChatPanel.java
 *
 * Created on 15-Dec-2009, 11:04:31 PM
 */
package mage.client.chat;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JTextField;
import mage.client.MageFrame;
import mage.client.SessionHandler;
import mage.client.dialog.PreferencesDialog;
import mage.client.util.GUISizeHelper;
import mage.view.ChatMessage.MessageColor;
import mage.view.ChatMessage.MessageType;
import org.mage.card.arcane.ManaSymbols;

/**
 *
 * @author BetaSteward_at_googlemail.com, nantuko
 */
public class ChatPanelBasic extends javax.swing.JPanel {

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
     *
     */
    protected ChatType chatType = ChatType.DEFAULT;

    public enum ChatType {

        DEFAULT, GAME, TABLES, TOURNAMENT
    }
    protected boolean startMessageDone = false;

    /**
     *
     * Creates new form ChatPanel
     *
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
        if (SessionHandler.joinChat(chatId)) {
            MageFrame.addChat(chatId, this);
        }
    }

    public void disconnect() {
        if (SessionHandler.getSession() != null) {
            SessionHandler.leaveChat(chatId);
            MageFrame.removeChat(chatId);
        }
    }

    Pattern profanityPattern = Pattern.compile(".*(1ab1a|1d1ot|13ch|13p3r|13sb1ans|13sbo|13s13|13sb1an|13sbo|13sy|1nbr3d|1nc3st|1njun|1ub3|\\Wbj|\\Wcum|\\Wdum|\\Wfag|\\Wfap|\\Wfuk|\\Wj1s|\\Wp3do|\\Wp33|\\Wpoo\\W|\\Wt1t|aho13|an1l1ngu|ana1|anus|ar3o1a|ar3o13|ary1an|ashat|asho13|asmast3r|asmunch|asw1p3|b1atch|b1gt1t|b1mbo|b1ow|b1tch|ba1ls|bab3|bang|barf|bastard|bawdy|b3an3r|b3ard3dc1am|b3ast1al1ty|b3atch|b3at3r|b3av3r|b3otch|b3yotch|bo1nk|bod1ly|bon3d|bon3r|bon3|boob|boot13|booty|bow31|br3ast|bugg3r|bukak3|bung|busty|butt|c11t|caca|cahon3|cam31to3|carp3tmunch3r|cawk|c3rv1x|ch1nc|ch1nk|chod3|co1tal|cockb1ock|cockho1st3r|cocknock3r|cocksmok3r|cocksuck3r|cock|condom|coon|corksuck3r|crabs|cums1ut|cumshot|cumsta1n|cun1l1ngus|cuntfac3|cunthunt3r|cunt|d1ck|d1k3|d1ldo|d1mw1t|d1ngl3|d1psh1p|dago|dam1t|damn1t|damn3d|damn|dawg13styl3|dog13styl3|dogysty13|dong|dop3y|douch3|drunk|dumas|dumbas|dumy|dyk3|3jacu1at3|3n1arg3m3nt|3r3ct1on|3r3ct|3rot1c|3xtacy|3xtasy|f.ck|f1osy|f1st3d|f1st1ng|f1sty|fa1gt|fa1g|fack|fag1t|fag3d|fagot|fag|fcuk|f31at1o|f31at3|f31ch1ng|f31ch3r|f31ch|f31tch3r|f31tch|foad|fobar|fond13|for3sk1n|fu.k|fudg3pack3r|fuk|g1ans|g1golo|ganja|ghay|gh3y|go1d3nshow3r|gonad|gook|gr1ngo|h1tl3r|handjob|hardon|hokah|hok3r|homo|honky|hoor|hootch|hoot3r|horny|hump1ng|hump3d|hump|hym3n|j1sm|j1s3d|j1sm|j1s|jackas|jackho13|jackof|j3rk3d|j3rkof|j3rk|junk13|junky|k1an|k1k3|k1nky|knob3nd|kyk3|mams|masa|mast3rba|masturba|max1|m3ns3s|m3nstruat|mfuck1ng|mofo|moron|moth3rf|mthrf|muff|n1gl|n1g|n1mrod|n1ny|n1pl3|nak3d|napa1m|napy|nas1|n3gro|noky|nympho|op1at3|op1um|ora1y|ora1|org13s|organ|orgasm|orgy|ovary|ovum|p1lowb1t3r|p1mp|p1nko|p1s3d|p1ssof|p1ss|pak1|pant13|panty|past13|pasty|p3ck3r|p3doph1l|p33p33|p3n1al|p3n1l3|p3n1s|p3n3trat1on|p3n3trat3|p3rv3rs1on|p3yot3|pha11c|phuck|po1ack|po1ock|poontang|poon|poop|porno|porn|pr1ck|pr1g|pron|pub1|pub3|punkas|punky|puss1|pussy|puto|qu1cky|qu1ck13|qu1m|qu3af|qu3ro|qu3rs|qu3r|r1mjob|r1tard|racy|rap1st|rap3d|rap3r|rap3|raunch|r31ch|r3cta1|r3ctum|r3ctus|r3tard|r3tar|rtard|rumpram3r|rump|s1av3|s13as|s1ut|sack|sad1s|scag|scant1ly|sch1ong|sch1so|scr3w|scrog|scrot|scrud|scum|s3aman|s3am3n|s3duc3|s3m3n|s3xua1|sh1t|skag|skank|sm3gma|smut|sn1p3r|snatch|sodom|sp1ck|sp1c|sp1k|sp3rm|spunk|st3amy|stfu|ston3d|str1p|strok3|stup1d|suck|sumofab1atch|t1nkl3|t1tfuck|tampon|tard|t3abag1ng|t3at|t3st1|t3st3|t3urd|thrust|tramp|trans|trashy|twat|ug1y|unw3d|ur1n3a|ut3rus|vag1na|vu1gar|vu1va|w1g3r|wang|wank3r|wank|w31n3r|w31rdo|w3dg13|w3n13|w3tback|w3w3|wh1t3y|wh1s|whor3|womb).*");
    Pattern profanity2Pattern = Pattern.compile(".*(1ab1a|1d1ot|13ch|13p3r|13sb1ans|13sbo|13s13|13sb1an|13sbo|13sy|1nbr3d|1nc3st|1njun|1ub3|\\Wbj|\\Wcum|\\Wdum|\\Wfag|\\Wfap|\\Wfuk|\\Wj1s|\\Wp3do|\\Wp3|\\Wpo\\W|\\Wt1t|aho13|an1l1ngu|ana1|anus|ar3o1a|ar3o13|ary1an|ashat|asho13|asmast3r|asmunch|asw1p3|b1atch|b1gt1t|b1mbo|b1ow|b1tch|ba1ls|bab3|bang|barf|bastard|bawdy|b3an3r|b3ard3dc1am|b3ast1al1ty|b3atch|b3at3r|b3av3r|b3otch|b3yotch|bo1nk|bod1ly|bon3d|bon3r|bon3|bob|bot13|boty|bow31|br3ast|bug3r|bukak3|bung|busty|but|c1t|caca|cahon3|cam31to3|carp3tmunch3r|cawk|c3rv1x|ch1nc|ch1nk|chod3|co1tal|cockb1ock|cockho1st3r|cocknock3r|cocksmok3r|cocksuck3r|cock|condom|con|corksuck3r|crabs|cums1ut|cumshot|cumsta1n|cun1l1ngus|cuntfac3|cunthunt3r|cunt|d1ck|d1k3|d1ldo|d1mw1t|d1ngl3|d1psh1p|dago|dam1t|damn1t|damn3d|damn|dawg13styl3|dog13styl3|dogysty13|dong|dop3y|douch3|drunk|dumas|dumbas|dumy|dyk3|3jacu1at3|3n1arg3m3nt|3r3ct1on|3r3ct|3rot1c|3xtacy|3xtasy|f.ck|f1osy|f1st3d|f1st1ng|f1sty|fa1gt|fa1g|fack|fag1t|fag3d|fagot|fag|fcuk|f31at1o|f31at3|f31ch1ng|f31ch3r|f31ch|f31tch3r|f31tch|foad|fobar|fond13|for3sk1n|fu.k|fudg3pack3r|fuk|g1ans|g1golo|ganja|ghay|gh3y|go1d3nshow3r|gonad|gr1ngo|h1tl3r|handjob|hardon|hokah|hok3r|homo|honky|hor|hotch|hot3r|horny|hump1ng|hump3d|hump|hym3n|j1sm|j1s3d|j1sm|j1s|jackas|jackho13|jackof|j3rk3d|j3rkof|j3rk|junk13|junky|k1an|k1k3|k1nky|knob3nd|kyk3|mams|masa|mast3rba|masturba|max1|m3ns3s|m3nstruat|mfuck1ng|mofo|moron|moth3rf|mthrf|muf|n1gl|n1g|n1mrod|n1ny|n1pl3|nak3d|napa1m|napy|nas1|n3gro|noky|nympho|op1at3|op1um|ora1y|ora1|org13s|organ|orgasm|orgy|ovary|ovum|p1lowb1t3r|p1mp|p1nko|p1s3d|p1sof|p1s|pak1|pant13|panty|past13|pasty|p3ck3r|p3doph1l|p3p3|p3n1al|p3n1l3|p3n1s|p3n3trat1on|p3n3trat3|p3rv3rs1on|p3yot3|pha1c|phuck|po1ack|po1ock|pontang|pon|pop|porno|porn|pr1ck|pr1g|pron|pub1|pub3|punkas|punky|pus1|pusy|puto|qu1cky|qu1ck13|qu1m|qu3af|qu3ro|qu3rs|qu3r|r1mjob|r1tard|racy|rap1st|rap3d|rap3r|rap3|raunch|r31ch|r3cta1|r3ctum|r3ctus|r3tard|r3tar|rtard|rumpram3r|rump|s1av3|s13as|s1ut|sack|sad1s|scag|scant1ly|sch1ong|sch1so|scr3w|scrog|scrot|scrud|scum|s3aman|s3am3n|s3duc3|s3m3n|s3xua1|sh1t|skag|skank|sm3gma|smut|sn1p3r|snatch|sodom|sp1ck|sp1c|sp1k|sp3rm|spunk|st3amy|stfu|ston3d|str1p|strok3|stup1d|suck|sumofab1atch|t1nkl3|t1tfuck|tampon|tard|t3abag1ng|t3at|t3st1|t3st3|t3urd|thrust|tramp|trans|trashy|twat|ug1y|unw3d|ur1n3a|ut3rus|vag1na|vu1gar|vu1va|w1g3r|wang|wank3r|wank|w31n3r|w31rdo|w3dg13|w3n13|w3tback|w3w3|wh1t3y|wh1s|whor3|womb).*");

    private boolean containsSwearing(String message, String level) {
        String orig = message;
        message = message.replaceAll("\\W", ".");
        message = "." + message + ".";

        message = message.toLowerCase();
        message = message.replaceAll("(.)(\\1{2,})", "$1");
        message = message.replaceAll("[@]", "a");
        message = message.replaceAll("[il]", "1");
        message = message.replaceAll("[e]", "3");
        message = message.replaceAll("[0]", "o");
        message = message.replaceAll("[5z]", "s");

        Matcher matchPattern = profanityPattern.matcher(message);
        if (matchPattern.find()) {
            System.out.println("message: Matched profanity:" + message);
            return true;
        }

        if (level.equals("2")) {
            message = message.replaceAll("\\.", "");
            matchPattern = profanity2Pattern.matcher(message);
            if (matchPattern.find()) {
                System.out.println("message: Matched profanity (level2):" + message);
                return true;
            }
        }
        return false;
    }

    /**
     * Display message in the chat. Use different colors for timestamp, username
     * and message.
     *
     * @param username message sender
     * @param message message itself
     * @param time timestamp
     * @param messageType
     * @param color Preferred color. Not used.
     */
    Pattern cardNamePattern = Pattern.compile(".*<font bgcolor=orange.*?</font>.*");

    public void receiveMessage(String username, String message, String time, MessageType messageType, MessageColor color) {
        StringBuilder text = new StringBuilder();
        if (time != null) {
            text.append(getColoredText(TIMESTAMP_COLOR, time + ": "));
            //this.txtConversation.append(TIMESTAMP_COLOR, time + " ");
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
                if (parentChatRef != null) {
                    userColor = SessionHandler.getUserName().equals(username) ? MY_COLOR : OPPONENT_COLOR;
                } else {
                    userColor = SessionHandler.getUserName().equals(username) ? MY_COLOR : OPPONENT_COLOR;
                }
                textColor = MESSAGE_COLOR;
                userSeparator = ": ";
        }
        if (color.equals(MessageColor.ORANGE)) {
            textColor = "Orange";
        }
        if (color.equals(MessageColor.YELLOW)) {
            textColor = "Yellow";
        }
        if (messageType == MessageType.WHISPER) {
            if (username.equalsIgnoreCase("Whisper from " + SessionHandler.getUserName())) {
                if (message.toLowerCase().startsWith("profanity 0")) {
                    PreferencesDialog.saveValue(PreferencesDialog.KEY_GAME_USE_PROFANITY_FILTER, "0");
                } else if (message.toLowerCase().startsWith("profanity 1")) {
                    PreferencesDialog.saveValue(PreferencesDialog.KEY_GAME_USE_PROFANITY_FILTER, "1");
                } else if (message.toLowerCase().startsWith("profanity 2")) {
                    PreferencesDialog.saveValue(PreferencesDialog.KEY_GAME_USE_PROFANITY_FILTER, "2");
                }
            }
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
        this.jScrollPaneTxt.getViewport().setOpaque(!chatType.equals(ChatType.TABLES));
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
