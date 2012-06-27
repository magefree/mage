/*
* Copyright 2011 BetaSteward_at_googlemail.com. All rights reserved.
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
 * PreferencesDialog.java
 *
 * Created on 26.06.2011, 16:35:40
 */
package mage.client.dialog;

import mage.client.MageFrame;
import mage.client.util.Config;
import mage.client.util.ImageHelper;
import mage.client.util.gui.BufferedImageBuilder;
import mage.remote.Connection;
import org.apache.log4j.Logger;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

import static mage.client.util.PhaseManager.*;

/**
 * Preferences dialog.
 *
 * @author nantuko
 */
public class PreferencesDialog extends javax.swing.JDialog {

    public static final String KEY_HAND_USE_BIG_CARDS = "handUseBigCards";
    public static final String KEY_SHOW_TOOLTIPS_ANY_ZONE = "showTooltipsInAnyZone";
    public static final String KEY_PERMANENTS_IN_ONE_PILE = "nonLandPermanentsInOnePile";
    public static final String KEY_CARD_IMAGES_USE_DEFAULT = "cardImagesUseDefault";
    public static final String KEY_CARD_IMAGES_PATH = "cardImagesPath";
    public static final String KEY_CARD_IMAGES_CHECK = "cardImagesCheck";

    public static final String KEY_PROXY_ADDRESS = "proxyAddress";
    public static final String KEY_PROXY_PORT = "proxyPort";
    public static final String KEY_PROXY_USERNAME = "proxyUsername";
    public static final String KEY_PROXY_REMEMBER = "proxyRemember";
    public static final String KEY_PROXY_TYPE = "proxyType";
    public static final String KEY_PROXY_PSWD = "proxyPassword";

    public static final String KEY_AVATAR = "selectedId";

    private static Map<String, String> cache = new HashMap<String, String>();
    private static final Boolean UPDATE_CACHE_POLICY = Boolean.TRUE;

    public static final String OPEN_CONNECTION_TAB = "Open-Connection-Tab";

    private static final transient Logger log = Logger.getLogger(PreferencesDialog.class);

    public static final int DEFAULT_AVATAR_ID = 51;
    private static int selectedId = DEFAULT_AVATAR_ID;
    private static Set<Integer> availableAvatars = new HashSet<Integer>();
    private static Map<Integer, JPanel> panels = new HashMap<Integer, JPanel>();

    private static final Border GREEN_BORDER = BorderFactory.createLineBorder(Color.GREEN, 3);
    private static final Border BLACK_BORDER = BorderFactory.createLineBorder(Color.BLACK, 3);

    static {
        availableAvatars.add(51);
        availableAvatars.add(13);
        availableAvatars.add(9);
        availableAvatars.add(53);
        availableAvatars.add(10);
        availableAvatars.add(39);
        availableAvatars.add(19);
        availableAvatars.add(30);
        availableAvatars.add(25);

        availableAvatars.add(22);
        availableAvatars.add(77);
        availableAvatars.add(62);
    }

    private final JFileChooser fc = new JFileChooser();

    {
        fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
    }

    /** Creates new form PreferencesDialog */
    public PreferencesDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        imageFolderPath.setEditable(false);
        cbProxyType.setModel(new DefaultComboBoxModel(Connection.ProxyType.values()));
        addAvatars();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        showToolTipsInAnyZone = new javax.swing.JCheckBox();
        displayBigCardsInHand = new javax.swing.JCheckBox();
        jPanel7 = new javax.swing.JPanel();
        nonLandPermanentsInOnePile = new javax.swing.JCheckBox();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        checkBoxUpkeepYou = new javax.swing.JCheckBox();
        checkBoxDrawYou = new javax.swing.JCheckBox();
        checkBoxMainYou = new javax.swing.JCheckBox();
        checkBoxBeforeCYou = new javax.swing.JCheckBox();
        checkBoxEndOfCYou = new javax.swing.JCheckBox();
        checkBoxMain2You = new javax.swing.JCheckBox();
        checkBoxEndTurnYou = new javax.swing.JCheckBox();
        checkBoxUpkeepOthers = new javax.swing.JCheckBox();
        checkBoxDrawOthers = new javax.swing.JCheckBox();
        checkBoxMainOthers = new javax.swing.JCheckBox();
        checkBoxBeforeCOthers = new javax.swing.JCheckBox();
        checkBoxEndOfCOthers = new javax.swing.JCheckBox();
        checkBoxMain2Others = new javax.swing.JCheckBox();
        checkBoxEndTurnOthers = new javax.swing.JCheckBox();
        jPanel4 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        useDefaultImageFolder = new javax.swing.JCheckBox();
        imageFolderPath = new javax.swing.JTextField();
        browseButton = new javax.swing.JButton();
        checkForNewImages = new javax.swing.JCheckBox();
        jPanel6 = new javax.swing.JPanel();
        lblProxyType = new javax.swing.JLabel();
        cbProxyType = new javax.swing.JComboBox();
        pnlProxySettings = new javax.swing.JPanel();
        pnlProxy = new javax.swing.JPanel();
        lblProxyServer = new javax.swing.JLabel();
        txtProxyServer = new javax.swing.JTextField();
        lblProxyPort = new javax.swing.JLabel();
        txtProxyPort = new javax.swing.JTextField();
        lblProxyUserName = new javax.swing.JLabel();
        txtProxyUserName = new javax.swing.JTextField();
        lblProxyPassword = new javax.swing.JLabel();
        txtPasswordField = new javax.swing.JPasswordField();
        rememberPswd = new javax.swing.JCheckBox();
        jLabel11 = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jPanel9 = new javax.swing.JPanel();
        jPanel10 = new javax.swing.JPanel();
        jPanel13 = new javax.swing.JPanel();
        jPanel11 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        jPanel12 = new javax.swing.JPanel();
        jPanel14 = new javax.swing.JPanel();
        jPanel15 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        jPanel16 = new javax.swing.JPanel();
        jPanel17 = new javax.swing.JPanel();
        jPanel18 = new javax.swing.JPanel();
        jPanel19 = new javax.swing.JPanel();
        jPanel20 = new javax.swing.JPanel();
        jPanel21 = new javax.swing.JPanel();
        saveButton = new javax.swing.JButton();
        exitButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Preferences");

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Hand"));

        showToolTipsInAnyZone.setSelected(true);
        showToolTipsInAnyZone.setText("Show tooltips");
        showToolTipsInAnyZone.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                showToolTipsInAnyZoneActionPerformed(evt);
            }
        });

        displayBigCardsInHand.setText("Use big images (for high resolution screens)");
        displayBigCardsInHand.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                displayBigCardsInHandActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(showToolTipsInAnyZone)
                    .addComponent(displayBigCardsInHand))
                .addContainerGap(161, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(showToolTipsInAnyZone)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(displayBigCardsInHand)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Battlefield"));

        nonLandPermanentsInOnePile.setSelected(true);
        nonLandPermanentsInOnePile.setLabel("Put non-land permanents in one pile");
        nonLandPermanentsInOnePile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nonLandPermanentsInOnePileActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(nonLandPermanentsInOnePile)
                .addContainerGap(199, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addComponent(nonLandPermanentsInOnePile)
                .addContainerGap(30, Short.MAX_VALUE))
        );

        nonLandPermanentsInOnePile.getAccessibleContext().setAccessibleName("nonLandPermanentsInOnePile");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel7, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(84, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Main", jPanel1);

        jLabel1.setText("Choose phases MAGE will stop on:");

        jLabel2.setText("Upkeep:");

        jLabel3.setText("Draw:");

        jLabel4.setText("Main:");

        jLabel5.setText("Before combat:");

        jLabel6.setText("End of combat:");

        jLabel7.setText("Main 2:");

        jLabel8.setText("End of turn:");

        jLabel9.setText("Your turn");

        jLabel10.setText("Opponent(s) turn");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel5)
                                    .addComponent(jLabel6)
                                    .addComponent(jLabel7)
                                    .addComponent(jLabel8))
                                .addGap(77, 77, 77)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addGap(2, 2, 2)
                                        .addComponent(jLabel9)
                                        .addGap(32, 32, 32)
                                        .addComponent(jLabel10))
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addGap(13, 13, 13)
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(checkBoxDrawYou)
                                            .addComponent(checkBoxUpkeepYou)
                                            .addComponent(checkBoxMainYou)
                                            .addComponent(checkBoxBeforeCYou)
                                            .addComponent(checkBoxEndOfCYou)
                                            .addComponent(checkBoxMain2You)
                                            .addComponent(checkBoxEndTurnYou))
                                        .addGap(78, 78, 78)
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(checkBoxUpkeepOthers)
                                            .addComponent(checkBoxBeforeCOthers)
                                            .addComponent(checkBoxMainOthers)
                                            .addComponent(checkBoxEndOfCOthers)
                                            .addComponent(checkBoxDrawOthers)
                                            .addComponent(checkBoxMain2Others)
                                            .addComponent(checkBoxEndTurnOthers)))))
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel1)))
                .addContainerGap(100, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(checkBoxUpkeepOthers))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addGap(20, 20, 20))
                            .addComponent(jLabel9))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(checkBoxUpkeepYou)
                            .addComponent(jLabel2))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel3)
                    .addComponent(checkBoxDrawYou)
                    .addComponent(checkBoxDrawOthers))
                .addGap(4, 4, 4)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel4)
                    .addComponent(checkBoxMainYou)
                    .addComponent(checkBoxMainOthers))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(checkBoxBeforeCYou, javax.swing.GroupLayout.Alignment.TRAILING)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(checkBoxBeforeCOthers)))
                .addGap(7, 7, 7)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel6)
                    .addComponent(checkBoxEndOfCYou)
                    .addComponent(checkBoxEndOfCOthers))
                .addGap(9, 9, 9)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel7)
                    .addComponent(checkBoxMain2You)
                    .addComponent(checkBoxMain2Others))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(checkBoxEndTurnYou)
                    .addComponent(jLabel8)
                    .addComponent(checkBoxEndTurnOthers))
                .addContainerGap(24, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Phases", jPanel2);

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Card images location:"));

        useDefaultImageFolder.setText("use default");
        useDefaultImageFolder.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                useDefaultImageFolderActionPerformed(evt);
            }
        });

        browseButton.setText("Browse...");
        browseButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                browseButtonActionPerformed(evt);
            }
        });

        checkForNewImages.setText("check for new images on startup");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(useDefaultImageFolder))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addComponent(imageFolderPath, javax.swing.GroupLayout.PREFERRED_SIZE, 284, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(browseButton))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(checkForNewImages)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(useDefaultImageFolder)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(imageFolderPath, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(browseButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(checkForNewImages)
                .addContainerGap(45, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(92, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Images", jPanel4);

        lblProxyType.setText("Proxy:");

        cbProxyType.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbProxyTypeActionPerformed(evt);
            }
        });

        pnlProxySettings.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        lblProxyServer.setText("Server:");

        lblProxyPort.setText("Port:");

        txtProxyPort.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtProxyPortkeyTyped(evt);
            }
        });

        lblProxyUserName.setText("User Name:");

        lblProxyPassword.setText("Password:");

        txtPasswordField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPasswordFieldActionPerformed(evt);
            }
        });

        rememberPswd.setText("Remember Password");
        rememberPswd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rememberPswdActionPerformed(evt);
            }
        });

        jLabel11.setFont(new java.awt.Font("Tahoma", 2, 10));
        jLabel11.setText("Note: password won't be encrypted!");

        javax.swing.GroupLayout pnlProxyLayout = new javax.swing.GroupLayout(pnlProxy);
        pnlProxy.setLayout(pnlProxyLayout);
        pnlProxyLayout.setHorizontalGroup(
            pnlProxyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlProxyLayout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addComponent(rememberPswd)
                .addGap(18, 18, 18)
                .addComponent(jLabel11)
                .addGap(34, 34, 34))
            .addGroup(pnlProxyLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlProxyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblProxyPort)
                    .addComponent(lblProxyPassword)
                    .addComponent(lblProxyServer)
                    .addComponent(lblProxyUserName))
                .addGap(19, 19, 19)
                .addGroup(pnlProxyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtProxyPort, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pnlProxyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(txtPasswordField, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(txtProxyUserName, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(txtProxyServer, javax.swing.GroupLayout.DEFAULT_SIZE, 295, Short.MAX_VALUE))
                .addContainerGap())
        );
        pnlProxyLayout.setVerticalGroup(
            pnlProxyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlProxyLayout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addGroup(pnlProxyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtProxyServer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblProxyServer))
                .addGap(8, 8, 8)
                .addGroup(pnlProxyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblProxyPort)
                    .addComponent(txtProxyPort, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlProxyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtProxyUserName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblProxyUserName))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlProxyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtPasswordField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblProxyPassword))
                .addGap(18, 18, 18)
                .addGroup(pnlProxyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rememberPswd)
                    .addComponent(jLabel11))
                .addContainerGap(25, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout pnlProxySettingsLayout = new javax.swing.GroupLayout(pnlProxySettings);
        pnlProxySettings.setLayout(pnlProxySettingsLayout);
        pnlProxySettingsLayout.setHorizontalGroup(
            pnlProxySettingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlProxySettingsLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pnlProxy, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        pnlProxySettingsLayout.setVerticalGroup(
            pnlProxySettingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlProxySettingsLayout.createSequentialGroup()
                .addComponent(pnlProxy, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(40, 40, 40)
                        .addComponent(lblProxyType)
                        .addGap(18, 18, 18)
                        .addComponent(cbProxyType, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(pnlProxySettings, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblProxyType)
                    .addComponent(cbProxyType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlProxySettings, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(40, Short.MAX_VALUE))
        );

        pnlProxySettings.getAccessibleContext().setAccessibleDescription("");

        jTabbedPane1.addTab("Connection", jPanel6);

        jPanel10.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        jPanel13.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        jPanel11.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel12.setText("Choose your avatar:");

        jPanel12.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        jPanel14.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        jPanel15.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel13.setText("New avatars:");

        jPanel16.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        jPanel17.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));

        javax.swing.GroupLayout jPanel17Layout = new javax.swing.GroupLayout(jPanel17);
        jPanel17.setLayout(jPanel17Layout);
        jPanel17Layout.setHorizontalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel17Layout.setVerticalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        jPanel18.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));

        javax.swing.GroupLayout jPanel18Layout = new javax.swing.GroupLayout(jPanel18);
        jPanel18.setLayout(jPanel18Layout);
        jPanel18Layout.setHorizontalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel18Layout.setVerticalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        jPanel19.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));

        javax.swing.GroupLayout jPanel19Layout = new javax.swing.GroupLayout(jPanel19);
        jPanel19.setLayout(jPanel19Layout);
        jPanel19Layout.setHorizontalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel19Layout.setVerticalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        jPanel20.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));

        javax.swing.GroupLayout jPanel20Layout = new javax.swing.GroupLayout(jPanel20);
        jPanel20.setLayout(jPanel20Layout);
        jPanel20Layout.setHorizontalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel20Layout.setVerticalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        jPanel21.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));

        javax.swing.GroupLayout jPanel21Layout = new javax.swing.GroupLayout(jPanel21);
        jPanel21.setLayout(jPanel21Layout);
        jPanel21Layout.setHorizontalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel21Layout.setVerticalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel12))
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel9Layout.createSequentialGroup()
                                .addGap(30, 30, 30)
                                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jPanel19, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(33, 33, 33)
                                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jPanel20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel9Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel9Layout.createSequentialGroup()
                                        .addGap(20, 20, 20)
                                        .addComponent(jPanel16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(33, 33, 33)
                                        .addComponent(jPanel17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jLabel13))))
                        .addGap(32, 32, 32)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel21, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(29, Short.MAX_VALUE))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel12)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(23, 23, 23)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel21, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addComponent(jLabel13)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(25, 25, 25))
        );

        jScrollPane1.setViewportView(jPanel9);

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 434, Short.MAX_VALUE)
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 264, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Avatars", jPanel8);

        saveButton.setLabel("Save");
        saveButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveButtonActionPerformed(evt);
            }
        });

        exitButton.setLabel("Exit");
        exitButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 439, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(311, Short.MAX_VALUE)
                .addComponent(saveButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(exitButton, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 292, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(exitButton)
                    .addComponent(saveButton))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void saveButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveButtonActionPerformed
        Preferences prefs = MageFrame.getPreferences();
        save(prefs, dialog.checkBoxUpkeepYou, UPKEEP_YOU);
        save(prefs, dialog.checkBoxDrawYou, DRAW_YOU);
        save(prefs, dialog.checkBoxMainYou, MAIN_YOU);
        save(prefs, dialog.checkBoxBeforeCYou, BEFORE_COMBAT_YOU);
        save(prefs, dialog.checkBoxEndOfCYou, END_OF_COMBAT_YOU);
        save(prefs, dialog.checkBoxMain2You, MAIN_2_YOU);
        save(prefs, dialog.checkBoxEndTurnYou, END_OF_TURN_YOU);

        save(prefs, dialog.checkBoxUpkeepOthers, UPKEEP_OTHERS);
        save(prefs, dialog.checkBoxDrawOthers, DRAW_OTHERS);
        save(prefs, dialog.checkBoxMainOthers, MAIN_OTHERS);
        save(prefs, dialog.checkBoxBeforeCOthers, BEFORE_COMBAT_OTHERS);
        save(prefs, dialog.checkBoxEndOfCOthers, END_OF_COMBAT_OTHERS);
        save(prefs, dialog.checkBoxMain2Others, MAIN_2_OTHERS);
        save(prefs, dialog.checkBoxEndTurnOthers, END_OF_TURN_OTHERS);
        save(prefs, dialog.displayBigCardsInHand, KEY_HAND_USE_BIG_CARDS, "true", "false", UPDATE_CACHE_POLICY);
        save(prefs, dialog.showToolTipsInAnyZone, KEY_SHOW_TOOLTIPS_ANY_ZONE, "true", "false", UPDATE_CACHE_POLICY);
        save(prefs, dialog.nonLandPermanentsInOnePile, KEY_PERMANENTS_IN_ONE_PILE, "true", "false", UPDATE_CACHE_POLICY);

        // connection
        save(prefs, dialog.cbProxyType, KEY_PROXY_TYPE);
        save(prefs, dialog.txtProxyServer, KEY_PROXY_ADDRESS);
        save(prefs, dialog.txtProxyPort, KEY_PROXY_PORT);
        save(prefs, dialog.txtProxyUserName, KEY_PROXY_USERNAME);
        save(prefs, dialog.rememberPswd, KEY_PROXY_REMEMBER, "true", "false", UPDATE_CACHE_POLICY);
        if (dialog.rememberPswd.isSelected()) {
            char[] input = txtPasswordField.getPassword();
            prefs.put(KEY_PROXY_PSWD, new String(input));
        }

        if (availableAvatars.contains(selectedId)) {
            prefs.put(KEY_AVATAR, String.valueOf(selectedId));
            updateCache(KEY_AVATAR, String.valueOf(selectedId));
        }

        // images
        saveImagesPath(prefs);

        try {
            prefs.flush();
        } catch (BackingStoreException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error: couldn't save phase stops. Please try again.");
        }

        dialog.setVisible(false);
    }//GEN-LAST:event_saveButtonActionPerformed

    private void exitButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitButtonActionPerformed
        dialog.setVisible(false);
    }//GEN-LAST:event_exitButtonActionPerformed

    private void showToolTipsInAnyZoneActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_showToolTipsInAnyZoneActionPerformed
    }//GEN-LAST:event_showToolTipsInAnyZoneActionPerformed

    private void displayBigCardsInHandActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_displayBigCardsInHandActionPerformed
    }//GEN-LAST:event_displayBigCardsInHandActionPerformed

    private void useDefaultImageFolderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_useDefaultImageFolderActionPerformed
        if (useDefaultImageFolder.isSelected()) {
            useDefaultPath();
        } else {
            useConfigurablePath();
        }
    }//GEN-LAST:event_useDefaultImageFolderActionPerformed

    private void useDefaultPath() {
        imageFolderPath.setText("./plugins/images/");
        imageFolderPath.setEnabled(false);
        browseButton.setEnabled(false);
    }

    private void useConfigurablePath() {
        String path = cache.get(KEY_CARD_IMAGES_PATH);
        dialog.imageFolderPath.setText(path);
        imageFolderPath.setEnabled(true);
        browseButton.setEnabled(true);
    }

    private void browseButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_browseButtonActionPerformed
        int returnVal = fc.showOpenDialog(PreferencesDialog.this);

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            imageFolderPath.setText(file.getAbsolutePath());
        }
    }//GEN-LAST:event_browseButtonActionPerformed

    private void cbProxyTypeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbProxyTypeActionPerformed
        this.showProxySettings();
    }//GEN-LAST:event_cbProxyTypeActionPerformed

    private void txtPasswordFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPasswordFieldActionPerformed
    }//GEN-LAST:event_txtPasswordFieldActionPerformed

    private void txtProxyPortkeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtProxyPortkeyTyped
    }//GEN-LAST:event_txtProxyPortkeyTyped

    private void nonLandPermanentsInOnePileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nonLandPermanentsInOnePileActionPerformed
    }//GEN-LAST:event_nonLandPermanentsInOnePileActionPerformed

    private void rememberPswdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rememberPswdActionPerformed
    }//GEN-LAST:event_rememberPswdActionPerformed

    private void showProxySettings() {
        if (cbProxyType.getSelectedItem() == Connection.ProxyType.SOCKS) {
            this.pnlProxy.setVisible(true);
            this.pnlProxySettings.setVisible(true);
        }
        else if (cbProxyType.getSelectedItem() == Connection.ProxyType.HTTP) {
            this.pnlProxy.setVisible(true);
            this.pnlProxySettings.setVisible(true);
        }
        else if (cbProxyType.getSelectedItem() == Connection.ProxyType.NONE) {
            this.pnlProxy.setVisible(false);
            this.pnlProxySettings.setVisible(false);
        }
        this.pack();
        this.repaint();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        int param = 0;
        if (args.length > 0) {
            String param1 = args[0];
            if (param1.equals(OPEN_CONNECTION_TAB)) {
                param = 3;
            }
        }
        final int openedTab = param;
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                if (!dialog.isVisible()) {
                    Preferences prefs = MageFrame.getPreferences();

                    // Phases
                    loadPhases(prefs);

                    // Connection
                    loadProxySettings(prefs);

                    // Selected avatar
                    loadSelectedAvatar(prefs);

                    // Images
                    loadImagesPath(prefs);

                    // open specified tab before displaying
                    openTab(openedTab);

                    dialog.setLocation(300, 200);
                    dialog.reset();

                    dialog.setVisible(true);
                } else {
                    dialog.requestFocus();
                }
            }
        });
    }

    private static void loadPhases(Preferences prefs) {
        load(prefs, dialog.checkBoxUpkeepYou, UPKEEP_YOU);
        load(prefs, dialog.checkBoxDrawYou, DRAW_YOU);
        load(prefs, dialog.checkBoxMainYou, MAIN_YOU);
        load(prefs, dialog.checkBoxBeforeCYou, BEFORE_COMBAT_YOU);
        load(prefs, dialog.checkBoxEndOfCYou, END_OF_COMBAT_YOU);
        load(prefs, dialog.checkBoxMain2You, MAIN_2_YOU);
        load(prefs, dialog.checkBoxEndTurnYou, END_OF_TURN_YOU);

        load(prefs, dialog.checkBoxUpkeepOthers, UPKEEP_OTHERS);
        load(prefs, dialog.checkBoxDrawOthers, DRAW_OTHERS);
        load(prefs, dialog.checkBoxMainOthers, MAIN_OTHERS);
        load(prefs, dialog.checkBoxBeforeCOthers, BEFORE_COMBAT_OTHERS);
        load(prefs, dialog.checkBoxEndOfCOthers, END_OF_COMBAT_OTHERS);
        load(prefs, dialog.checkBoxMain2Others, MAIN_2_OTHERS);
        load(prefs, dialog.checkBoxEndTurnOthers, END_OF_TURN_OTHERS);
        load(prefs, dialog.displayBigCardsInHand, KEY_HAND_USE_BIG_CARDS, "true");
        load(prefs, dialog.showToolTipsInAnyZone, KEY_SHOW_TOOLTIPS_ANY_ZONE, "true");
        load(prefs, dialog.nonLandPermanentsInOnePile, KEY_PERMANENTS_IN_ONE_PILE, "true");
    }

    private static void loadProxySettings(Preferences prefs) {
        dialog.cbProxyType.setSelectedItem(Connection.ProxyType.valueOf(MageFrame.getPreferences().get(KEY_PROXY_TYPE, "NONE").toUpperCase()));

        load(prefs, dialog.txtProxyServer, KEY_PROXY_ADDRESS, Config.serverName);
        load(prefs, dialog.txtProxyPort, KEY_PROXY_PORT, Integer.toString(Config.port));
        load(prefs, dialog.txtProxyUserName, KEY_PROXY_USERNAME, "");
        load(prefs, dialog.rememberPswd, KEY_PROXY_REMEMBER, "true", "false");
        if (dialog.rememberPswd.isSelected()) {
            load(prefs, dialog.txtPasswordField, KEY_PROXY_PSWD, "");
        }
    }

    private static void loadSelectedAvatar(Preferences prefs) {
        getSelectedAvatar();
        dialog.setSelectedId(selectedId);
    }

    public static int getSelectedAvatar() {
        try {
            selectedId = Integer.valueOf(MageFrame.getPreferences().get(KEY_AVATAR, String.valueOf(DEFAULT_AVATAR_ID)));
        } catch (NumberFormatException n) {
            selectedId = DEFAULT_AVATAR_ID;
        } finally {
            if (!availableAvatars.contains(selectedId)) {
                selectedId = DEFAULT_AVATAR_ID;
            }
        }
        return selectedId;
    }

    private static void openTab(int index) {
        try {
            if (index > 0) {
                dialog.jTabbedPane1.setSelectedIndex(3);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void loadImagesPath(Preferences prefs) {
        String prop = prefs.get(KEY_CARD_IMAGES_USE_DEFAULT, "true");
        if (prop.equals("true")) {
            dialog.useDefaultImageFolder.setSelected(true);
            dialog.useDefaultPath();
        } else {
            dialog.useDefaultImageFolder.setSelected(false);
            dialog.useConfigurablePath();
            String path = prefs.get(KEY_CARD_IMAGES_PATH, "");
            dialog.imageFolderPath.setText(path);
            updateCache(KEY_CARD_IMAGES_PATH, path);
        }
        dialog.checkForNewImages.setSelected(Boolean.parseBoolean(prefs.get(KEY_CARD_IMAGES_CHECK, "true")));
        updateCache(KEY_CARD_IMAGES_CHECK, Boolean.toString(dialog.checkForNewImages.isSelected()));
    }

     private static void saveImagesPath(Preferences prefs) {
        if (dialog.useDefaultImageFolder.isSelected()) {
            prefs.put(KEY_CARD_IMAGES_USE_DEFAULT, "true");
            updateCache(KEY_CARD_IMAGES_USE_DEFAULT, "true");
        } else {
            prefs.put(KEY_CARD_IMAGES_USE_DEFAULT, "false");
            updateCache(KEY_CARD_IMAGES_USE_DEFAULT, "false");
            String path = dialog.imageFolderPath.getText();
            prefs.put(KEY_CARD_IMAGES_PATH, path);
            updateCache(KEY_CARD_IMAGES_PATH, path);
        }
        prefs.put(KEY_CARD_IMAGES_CHECK, Boolean.toString(dialog.checkForNewImages.isSelected()));
        updateCache(KEY_CARD_IMAGES_CHECK, Boolean.toString(dialog.checkForNewImages.isSelected()));
    }

    private static void load(Preferences prefs, JCheckBox checkBox, String propName, String yesValue) {
        String prop = prefs.get(propName, yesValue);
        checkBox.setSelected(prop.equals(yesValue));
    }

    private static void load(Preferences prefs, JCheckBox checkBox, String propName, String yesValue, String defaultValue) {
        String prop = prefs.get(propName, defaultValue);
        checkBox.setSelected(prop.equals(yesValue));
    }

    private static void load(Preferences prefs, JTextField field, String propName, String defaultValue) {
        String prop = prefs.get(propName, defaultValue);
        field.setText(prop);
    }

    private static void load(Preferences prefs, JComboBox field, String propName, String defaultValue) {
        String prop = prefs.get(propName, defaultValue);
        field.setSelectedItem(prop);
    }

    private static void load(Preferences prefs, JCheckBox checkBox, String propName) {
        load(prefs, checkBox, propName, PHASE_ON);
    }

    private static void save(Preferences prefs, JCheckBox checkBox, String propName) {
        save(prefs, checkBox, propName, PHASE_ON, PHASE_OFF, false);
    }

    private static void save(Preferences prefs, JCheckBox checkBox, String propName, String yesValue, String onValue, boolean updateCache) {
        prefs.put(propName, checkBox.isSelected() ? yesValue : onValue);
        if (updateCache) {
            updateCache(propName, checkBox.isSelected() ? yesValue : onValue);
        }
    }

    private static void save(Preferences prefs, JTextField textField, String propName) {
        prefs.put(propName, textField.getText().trim());
        updateCache(propName, textField.getText().trim());
    }

    private static void save(Preferences prefs, JComboBox comboBox, String propName) {
        prefs.put(propName, comboBox.getSelectedItem().toString().trim());
        updateCache(propName, comboBox.getSelectedItem().toString().trim());
    }

    public void reset() {
        jTabbedPane1.setSelectedIndex(0);
    }

    public static String getCachedValue(String key, String def) {
        if (cache.containsKey(key)) {
            return cache.get(key);
        } else {
            Preferences prefs = MageFrame.getPreferences();
            String value = prefs.get(key, def);
            if (value == null) return null;
            cache.put(key, value);
            return value;
        }
    }

    private static void updateCache(String key, String value) {
        cache.put(key, value);
    }

    private void addAvatars() {
        try {
            addAvatar(jPanel10, 51, true, false);
            addAvatar(jPanel13, 13, false, false);
            addAvatar(jPanel11, 9,  false, false);
            addAvatar(jPanel12, 53, false, false);
            addAvatar(jPanel14, 10, false, false);
            addAvatar(jPanel15, 39, false, false);
            addAvatar(jPanel19, 19, false, false);
            addAvatar(jPanel20, 30, false, false);
            addAvatar(jPanel21, 25, false, false);

            addAvatar(jPanel16, 22, false, false);
            addAvatar(jPanel17, 77, false, false);
            addAvatar(jPanel18, 62, false, false);
        } catch (Exception e) {
            log.error(e, e);
        }
    }

    public void setSelectedId(int id) {
        if (availableAvatars.contains(id)) {
            for (JPanel panel : panels.values()) {
                panel.setBorder(BLACK_BORDER);
            }
            this.selectedId = id;
            panels.get(this.selectedId).setBorder(GREEN_BORDER);
        }
    }

    private void addAvatar(JPanel jPanel, final int id, boolean selected, boolean locked) {
        String path = "/avatars/" + String.valueOf(id) + ".jpg";
        panels.put(id, jPanel);
        Image image = ImageHelper.getImageFromResources(path);
        Rectangle r = new Rectangle(90, 90);
        BufferedImage bufferedImage;
        if (!locked) {
            bufferedImage = BufferedImageBuilder.bufferImage(image, BufferedImage.TYPE_INT_ARGB);
        } else {
            bufferedImage = BufferedImageBuilder.bufferImage(image, BufferedImage.TYPE_INT_ARGB, new Color(150, 150, 150, 170));
        }
        BufferedImage resized = ImageHelper.getResizedImage(bufferedImage, r);
        final JLabel jLabel = new JLabel();
        jLabel.setIcon(new ImageIcon(resized));
        if (selected) {
            jPanel.setBorder(GREEN_BORDER);
        } else {
            jPanel.setBorder(BLACK_BORDER);
        }
        jPanel.setLayout(new BorderLayout());
        jPanel.add(jLabel);
        if (!locked) {
            jLabel.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    if (selectedId != id) {
                        setSelectedId(id);
                        MageFrame.getSession().updateAvatar(id);
                    }
                }
            });
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton browseButton;
    private javax.swing.JComboBox cbProxyType;
    private javax.swing.JCheckBox checkBoxBeforeCOthers;
    private javax.swing.JCheckBox checkBoxBeforeCYou;
    private javax.swing.JCheckBox checkBoxDrawOthers;
    private javax.swing.JCheckBox checkBoxDrawYou;
    private javax.swing.JCheckBox checkBoxEndOfCOthers;
    private javax.swing.JCheckBox checkBoxEndOfCYou;
    private javax.swing.JCheckBox checkBoxEndTurnOthers;
    private javax.swing.JCheckBox checkBoxEndTurnYou;
    private javax.swing.JCheckBox checkBoxMain2Others;
    private javax.swing.JCheckBox checkBoxMain2You;
    private javax.swing.JCheckBox checkBoxMainOthers;
    private javax.swing.JCheckBox checkBoxMainYou;
    private javax.swing.JCheckBox checkBoxUpkeepOthers;
    private javax.swing.JCheckBox checkBoxUpkeepYou;
    private javax.swing.JCheckBox checkForNewImages;
    private javax.swing.JCheckBox displayBigCardsInHand;
    private javax.swing.JButton exitButton;
    private javax.swing.JTextField imageFolderPath;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel20;
    private javax.swing.JPanel jPanel21;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JLabel lblProxyPassword;
    private javax.swing.JLabel lblProxyPort;
    private javax.swing.JLabel lblProxyServer;
    private javax.swing.JLabel lblProxyType;
    private javax.swing.JLabel lblProxyUserName;
    private javax.swing.JCheckBox nonLandPermanentsInOnePile;
    private javax.swing.JPanel pnlProxy;
    private javax.swing.JPanel pnlProxySettings;
    private javax.swing.JCheckBox rememberPswd;
    private javax.swing.JButton saveButton;
    private javax.swing.JCheckBox showToolTipsInAnyZone;
    private javax.swing.JPasswordField txtPasswordField;
    private javax.swing.JTextField txtProxyPort;
    private javax.swing.JTextField txtProxyServer;
    private javax.swing.JTextField txtProxyUserName;
    private javax.swing.JCheckBox useDefaultImageFolder;
    // End of variables declaration//GEN-END:variables

    private static final PreferencesDialog dialog = new PreferencesDialog(new javax.swing.JFrame(), true);

    static {
        dialog.setResizable(false);
    }
}
