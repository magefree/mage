package mage.client.dialog;

import mage.cards.repository.RepositoryUtil;
import mage.choices.Choice;
import mage.choices.ChoiceImpl;
import mage.client.MageFrame;
import mage.client.SessionHandler;
import mage.client.preference.MagePreferences;
import mage.client.util.ClientDefaultSettings;
import mage.client.util.gui.countryBox.CountryItemEditor;
import mage.client.util.sets.ConstructedFormats;
import mage.remote.Connection;
import mage.utils.StreamUtils;
import org.apache.log4j.Logger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.*;
import java.util.List;
import java.util.*;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static mage.client.dialog.PreferencesDialog.*;

/**
 * App GUI: connection windows
 *
 * @author BetaSteward_at_googlemail.com
 */
public class ConnectDialog extends MageDialog {

    private static final Logger logger = Logger.getLogger(ConnectDialog.class);
    private Connection connection;
    private ConnectTask task;
    private final RegisterUserDialog registerUserDialog;
    private final ResetPasswordDialog resetPasswordDialog;

    private final ActionListener connectAction = evt -> btnConnectActionPerformed(evt);

    /**
     * Creates new form ConnectDialog
     */
    public ConnectDialog() {
        initComponents();

        this.txtServer.addActionListener(connectAction);
        this.txtPort.addActionListener(connectAction);
        this.txtUserName.addActionListener(connectAction);
        this.txtPassword.addActionListener(connectAction);

        registerUserDialog = new RegisterUserDialog(this);
        MageFrame.getDesktop().add(registerUserDialog, JLayeredPane.MODAL_LAYER);

        resetPasswordDialog = new ResetPasswordDialog(this);
        MageFrame.getDesktop().add(resetPasswordDialog, JLayeredPane.MODAL_LAYER);
    }

    public void showDialog() {
        String serverAddress = MagePreferences.getServerAddressWithDefault(ClientDefaultSettings.serverName);
        this.txtServer.setText(serverAddress);
        this.txtPort.setText(Integer.toString(MagePreferences.getServerPortWithDefault(ClientDefaultSettings.port)));
        this.txtUserName.setText(MagePreferences.getUserName(serverAddress));
        this.txtPassword.setText(MagePreferences.getPassword(serverAddress));
        this.chkAutoConnect.setSelected(Boolean.parseBoolean(MageFrame.getPreferences().get(KEY_CONNECT_AUTO_CONNECT, "false")));
        this.chkForceUpdateDB.setSelected(false); // has always to be set manually to force comparison

        String selectedFlag = MageFrame.getPreferences().get(KEY_CONNECT_FLAG, "world");
        // set the selected country/flag
        for (int i = 0; i < cbFlag.getItemCount(); i++) {
            String[] name = (String[]) cbFlag.getItemAt(i);
            if (name[1].equals(selectedFlag)) {
                cbFlag.setSelectedIndex(i);
                break;
            }
        }
        this.setModal(true);
        this.setLocation(50, 50);
        this.setVisible(true);
    }

    private void saveSettings() {
        String serverAddress = txtServer.getText().trim();
        MagePreferences.setServerAddress(serverAddress);
        MagePreferences.setServerPort(Integer.parseInt(txtPort.getText().trim()));
        MagePreferences.setUserName(serverAddress, txtUserName.getText().trim());
        MagePreferences.setPassword(serverAddress, String.valueOf(txtPassword.getPassword()).trim());
        MageFrame.getPreferences().put(KEY_CONNECT_AUTO_CONNECT, Boolean.toString(chkAutoConnect.isSelected()));

        // last settings for reconnect
        MagePreferences.saveLastServer();
    }
    
    private void setServerSettings(String address, String port, boolean needRegistration) {
        this.txtServer.setText(address);
        this.txtPort.setText(port);
        this.txtUserName.setText(MagePreferences.getUserName(address));
        if (needRegistration) {
            this.txtPassword.setText(MagePreferences.getPassword(address));
        } else {
            this.txtPassword.setText("");
        }
    }
    
    private void chooseAndSetServerSettingsFromOther() {
        BufferedReader in = null;
        Writer output = null;
        try {
            String serverUrl = PreferencesDialog.getCachedValue(KEY_CONNECTION_URL_SERVER_LIST, "http://xmage.de/files/server-list.txt");
            if (serverUrl.contains("xmage.info/files/")) {
                serverUrl = serverUrl.replace("xmage.info/files/", "xmage.de/files/"); // replace old URL if still saved
                PreferencesDialog.saveValue(KEY_CONNECTION_URL_SERVER_LIST, serverUrl);
            }
            URL serverListURL = new URL(serverUrl);

            Connection.ProxyType configProxyType = Connection.ProxyType.valueByText(PreferencesDialog.getCachedValue(PreferencesDialog.KEY_PROXY_TYPE, "None"));
            Proxy p = null;
            Proxy.Type type = Proxy.Type.DIRECT;
            switch (configProxyType) {
                case HTTP:
                    type = Proxy.Type.HTTP;
                    break;
                case SOCKS:
                    type = Proxy.Type.SOCKS;
                    break;
                case NONE:
                default:
                    p = Proxy.NO_PROXY;
                    break;
            }

            if (p == null || !p.equals(Proxy.NO_PROXY)) {
                try {
                    String address = PreferencesDialog.getCachedValue(PreferencesDialog.KEY_PROXY_ADDRESS, "");
                    Integer port = Integer.parseInt(PreferencesDialog.getCachedValue(PreferencesDialog.KEY_PROXY_PORT, "80"));
                    p = new Proxy(type, new InetSocketAddress(address, port));
                } catch (Exception ex) {
                    throw new RuntimeException("Gui_DownloadPictures : error 1 - " + ex);
                }
            }

            if (p == null) {
                JOptionPane.showMessageDialog(null, "Couldn't configure Proxy object!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            boolean URLNotFound = false;
            try {
                in = new BufferedReader(new InputStreamReader(serverListURL.openConnection(p).getInputStream()));
            } catch (SocketTimeoutException | FileNotFoundException | UnknownHostException ex) {
                logger.warn("Could not read serverlist from: " + serverListURL.toString());
                File f = new File("serverlist.txt");
                if (f.exists() && !f.isDirectory()) {
                    logger.info("Using buffered serverlist: serverlist.txt");
                    URLNotFound = true;
                    in = new BufferedReader(new FileReader("serverlist.txt"));
                }
            }
            List<String> servers = new ArrayList<>();
            if (in != null) {

                if (!URLNotFound) {
                    // write serverlist to be able to read if URL is not available
                    File file = new File("serverlist.txt");
                    if (file.exists() && !file.isDirectory()) {
                        file.delete();
                    }
                    output = new BufferedWriter(new FileWriter(file));
                }

                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    logger.debug("Found server: " + inputLine);
                    servers.add(inputLine);
                    if (output != null) {
                        output.append(inputLine).append('\n');

                    }
                }
            }
            if (servers.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Couldn't find any server.");
                return;
            }

            String selectedServer = (String) JOptionPane.showInputDialog(null,
                    "Choose XMage Public Server:", "Input",
                    JOptionPane.INFORMATION_MESSAGE, null, servers.toArray(),
                    servers.get(0));
            if (selectedServer != null) {
                String[] params = selectedServer.split(":");
                if (params.length == 3) {
                    setServerSettings(params[1], params[2], true);
                } else {
                    JOptionPane.showMessageDialog(null, "Wrong server data format.");
                }
            }
        } catch (Exception ex) {
            logger.error(ex, ex);
        } finally {
            StreamUtils.closeQuietly(in);
            StreamUtils.closeQuietly(output);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblServer = new javax.swing.JLabel();
        lblUserName = new javax.swing.JLabel();
        txtUserName = new javax.swing.JTextField();
        lblPassword = new javax.swing.JLabel();
        txtPassword = new javax.swing.JPasswordField();
        lblFlag = new javax.swing.JLabel();
        chkAutoConnect = new javax.swing.JCheckBox();
        chkForceUpdateDB = new javax.swing.JCheckBox();
        jProxySettingsButton = new javax.swing.JButton();
        btnConnect = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();
        lblStatus = new javax.swing.JLabel();
        btnRegister = new javax.swing.JButton();
        btnForgotPassword = new javax.swing.JButton();
        lblFastConnect = new javax.swing.JLabel();
        panelFlag = new javax.swing.JPanel();
        cbFlag = new mage.client.util.gui.countryBox.CountryComboBox();
        filler1 = new javax.swing.Box.Filler(new java.awt.Dimension(5, 0), new java.awt.Dimension(4, 0), new java.awt.Dimension(5, 32767));
        btnFlagSearch = new javax.swing.JButton();
        panelFast = new javax.swing.JPanel();
        btnFindMain = new javax.swing.JButton();
        btnFindLocal = new javax.swing.JButton();
        btnFindBeta = new javax.swing.JButton();
        btnFindUs = new javax.swing.JButton();
        btnFindOther = new javax.swing.JButton();
        btnFindEU = new javax.swing.JButton();
        panelServer = new javax.swing.JPanel();
        txtServer = new javax.swing.JTextField();
        txtPort = new javax.swing.JTextField();
        lblPort = new javax.swing.JLabel();
        btnCheckStatus = new javax.swing.JButton();
        filler2 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 50), new java.awt.Dimension(0, 50), new java.awt.Dimension(32767, 50));
        btnWhatsNew = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        setTitle("Connect to server");

        lblServer.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblServer.setLabelFor(txtServer);
        lblServer.setText("Server name:");

        lblUserName.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblUserName.setLabelFor(txtUserName);
        lblUserName.setText("Username:");

        lblPassword.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblPassword.setLabelFor(txtPassword);
        lblPassword.setText("Password:");

        lblFlag.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblFlag.setLabelFor(txtUserName);
        lblFlag.setText("User's flag:");

        chkAutoConnect.setText("Automatically connect to this server next time");
        chkAutoConnect.setToolTipText("<HTML>If active this connect dialog will not be shown if you choose to connect.<br>\nInstead XMage tries to connect to the last server you were connected to.");
        chkAutoConnect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkAutoConnectActionPerformed(evt);
            }
        });

        chkForceUpdateDB.setText("Force update of card database");
        chkForceUpdateDB.setToolTipText("<HTML>If active the comparison of the server cards database to the client database will be enforced.<br>If not, the comparison will only done if the database version of the client is lower than the version of the server.");
        chkForceUpdateDB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkForceUpdateDBActionPerformed(evt);
            }
        });

        jProxySettingsButton.setText("Proxy Settings...");
        jProxySettingsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jProxySettingsButtonActionPerformed(evt);
            }
        });

        btnConnect.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btnConnect.setText("Connect to server");
        btnConnect.setMargin(new java.awt.Insets(2, 2, 2, 2));
        btnConnect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConnectActionPerformed(evt);
            }
        });

        btnCancel.setText("Cancel");
        btnCancel.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnCancel.setMargin(new java.awt.Insets(2, 2, 2, 2));
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });

        btnRegister.setText("Register new user...");
        btnRegister.setToolTipText("<html>XMage now supports user authentication.<br>Register your account before you log in.<html>");
        btnRegister.setMargin(new java.awt.Insets(2, 2, 2, 2));
        btnRegister.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegisterActionPerformed(evt);
            }
        });

        btnForgotPassword.setText("Forgot password...");
        btnForgotPassword.setToolTipText("<html>You can reset your password if you have registered<br>your account with an email address.</html>");
        btnForgotPassword.setMargin(new java.awt.Insets(2, 2, 2, 2));
        btnForgotPassword.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnForgotPasswordActionPerformed(evt);
            }
        });

        lblFastConnect.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblFastConnect.setLabelFor(btnFindMain);
        lblFastConnect.setText("Connect to:");
        lblFastConnect.setName(""); // NOI18N

        panelFlag.setMinimumSize(new java.awt.Dimension(50, 33));
        panelFlag.setLayout(new javax.swing.BoxLayout(panelFlag, javax.swing.BoxLayout.LINE_AXIS));

        cbFlag.setEditable(true);
        cbFlag.setMaximumRowCount(16);
        cbFlag.setAlignmentX(0.0F);
        cbFlag.setMinimumSize(new java.awt.Dimension(50, 18));
        cbFlag.setPreferredSize(new java.awt.Dimension(278, 15));
        panelFlag.add(cbFlag);
        panelFlag.add(filler1);

        btnFlagSearch.setIcon(new javax.swing.ImageIcon(getClass().getResource("/buttons/search_24.png"))); // NOI18N
        btnFlagSearch.setToolTipText("Fast search your flag");
        btnFlagSearch.setAlignmentX(1.0F);
        btnFlagSearch.setPreferredSize(new java.awt.Dimension(23, 23));
        btnFlagSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFlagSearchActionPerformed(evt);
            }
        });
        panelFlag.add(btnFlagSearch);

        btnFindMain.setIcon(new javax.swing.ImageIcon(getClass().getResource("/flags/de.png"))); // NOI18N
        btnFindMain.setText("X");
        btnFindMain.setToolTipText("Connect to xmage.de (first Europe server, most popular, registration needs)");
        btnFindMain.setAlignmentY(0.0F);
        btnFindMain.setMargin(new java.awt.Insets(2, 2, 2, 2));
        btnFindMain.setMaximumSize(new java.awt.Dimension(42, 23));
        btnFindMain.setMinimumSize(new java.awt.Dimension(42, 23));
        btnFindMain.setPreferredSize(new java.awt.Dimension(23, 23));
        btnFindMain.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFindMainActionPerformed(evt);
            }
        });

        btnFindLocal.setText("LOCAL, AI");
        btnFindLocal.setToolTipText("Connect to localhost, AI enabled (run local server from launcher)");
        btnFindLocal.setAlignmentY(0.0F);
        btnFindLocal.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnFindLocal.setMargin(new java.awt.Insets(2, 2, 2, 2));
        btnFindLocal.setPreferredSize(new java.awt.Dimension(23, 23));
        btnFindLocal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFindLocalActionPerformed(evt);
            }
        });

        btnFindBeta.setText("BETA, AI");
        btnFindBeta.setToolTipText("Connect to BETA server, AI enabled (use any username without registration)");
        btnFindBeta.setAlignmentY(0.0F);
        btnFindBeta.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnFindBeta.setMargin(new java.awt.Insets(2, 2, 2, 2));
        btnFindBeta.setPreferredSize(new java.awt.Dimension(23, 23));
        btnFindBeta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFindBetaActionPerformed(evt);
            }
        });

        btnFindUs.setIcon(new javax.swing.ImageIcon(getClass().getResource("/flags/us.png"))); // NOI18N
        btnFindUs.setText("US");
        btnFindUs.setToolTipText("Connect to us.xmage.today (USA, use any username without registration)");
        btnFindUs.setAlignmentY(0.0F);
        btnFindUs.setMargin(new java.awt.Insets(2, 2, 2, 2));
        btnFindUs.setPreferredSize(new java.awt.Dimension(23, 23));
        btnFindUs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFindUsActionPerformed(evt);
            }
        });

        btnFindOther.setText("Other...");
        btnFindOther.setToolTipText("Choose server from full servers list");
        btnFindOther.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnFindOther.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFindOtherActionPerformed(evt);
            }
        });

        btnFindEU.setIcon(new javax.swing.ImageIcon(getClass().getResource("/flags/europeanunion.png"))); // NOI18N
        btnFindEU.setText("EU");
        btnFindEU.setToolTipText("Connect to eu.xmage.today (second Europe server, use any username without registration)");
        btnFindEU.setAlignmentY(0.0F);
        btnFindEU.setMargin(new java.awt.Insets(2, 2, 2, 2));
        btnFindEU.setMaximumSize(new java.awt.Dimension(42, 23));
        btnFindEU.setMinimumSize(new java.awt.Dimension(42, 23));
        btnFindEU.setPreferredSize(new java.awt.Dimension(23, 23));
        btnFindEU.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFindEUActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelFastLayout = new javax.swing.GroupLayout(panelFast);
        panelFast.setLayout(panelFastLayout);
        panelFastLayout.setHorizontalGroup(
            panelFastLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelFastLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(btnFindMain, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnFindEU, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnFindUs, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnFindBeta, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnFindLocal, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnFindOther, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );
        panelFastLayout.setVerticalGroup(
            panelFastLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelFastLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(panelFastLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnFindMain, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnFindLocal, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnFindUs, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnFindBeta, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnFindOther, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnFindEU, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, 0))
        );

        txtPort.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                ConnectDialog.this.keyTyped(evt);
            }
        });

        lblPort.setLabelFor(txtPort);
        lblPort.setText("Port:");

        btnCheckStatus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/flags/world.png"))); // NOI18N
        btnCheckStatus.setText("Check online status");
        btnCheckStatus.setToolTipText("Go to servers online statuses page");
        btnCheckStatus.setAlignmentY(0.0F);
        btnCheckStatus.setMargin(new java.awt.Insets(2, 2, 2, 2));
        btnCheckStatus.setPreferredSize(new java.awt.Dimension(23, 23));
        btnCheckStatus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCheckStatusActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelServerLayout = new javax.swing.GroupLayout(panelServer);
        panelServer.setLayout(panelServerLayout);
        panelServerLayout.setHorizontalGroup(
            panelServerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelServerLayout.createSequentialGroup()
                .addComponent(txtServer, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblPort)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtPort, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnCheckStatus, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );
        panelServerLayout.setVerticalGroup(
            panelServerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelServerLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(panelServerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtServer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtPort, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblPort)
                    .addComponent(btnCheckStatus, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        btnWhatsNew.setText("Show what's new");
        btnWhatsNew.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnWhatsNewActionPerformed(evt);
            }
        });

        jLabel1.setText("(use empty password for servers without registration)");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lblUserName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblServer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblFastConnect, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblPassword, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblFlag, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblStatus, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(chkForceUpdateDB, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(chkAutoConnect, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtUserName)
                    .addComponent(panelFlag, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnConnect, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnRegister, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnForgotPassword, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(panelFast, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelServer, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jProxySettingsButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnWhatsNew)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(txtPassword)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel1)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(filler2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelFast, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblFastConnect, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelServer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblServer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtUserName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblUserName, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(txtPassword)
                                .addComponent(jLabel1))
                            .addComponent(lblPassword, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addComponent(filler2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelFlag, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblFlag, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(chkAutoConnect)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(chkForceUpdateDB)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jProxySettingsButton, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnWhatsNew, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnRegister, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnForgotPassword, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(btnConnect, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnCancel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(23, 23, 23))
        );

        lblFastConnect.getAccessibleContext().setAccessibleName("Fast connect to:");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
        MageFrame.getPreferences().put("autoConnect", Boolean.toString(chkAutoConnect.isSelected()));
        MageFrame.getPreferences().put(KEY_CONNECT_FLAG, ((CountryItemEditor) cbFlag.getEditor()).getImageItem());
        if (task != null && !task.isDone()) {
            task.cancel(true);
        } else {
            this.hideDialog();
        }
    }//GEN-LAST:event_btnCancelActionPerformed

    private void btnConnectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConnectActionPerformed

        if (txtServer.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(rootPane, "Please provide a server address");
            return;
        }
        if (txtPort.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(rootPane, "Please provide a port number");
            return;
        }
        if (txtUserName.getText().isEmpty()) {
            JOptionPane.showMessageDialog(rootPane, "Please provide a user name");
            return;
        }
        // txtPassword is not checked here, because authentication might be disabled by the server config.
        if (Integer.parseInt(txtPort.getText()) < 1 || Integer.parseInt(txtPort.getText()) > 65535) {
            JOptionPane.showMessageDialog(rootPane, "Invalid port number");
            txtPort.setText(Integer.toString(MagePreferences.getServerPortWithDefault(ClientDefaultSettings.port)));
            return;
        }

        char[] input = new char[0];
        try {
            connection = new Connection();
            connection.setHost(this.txtServer.getText().trim());
            connection.setPort(Integer.parseInt(this.txtPort.getText().trim()));
            connection.setUsername(this.txtUserName.getText().trim());
            connection.setPassword(String.valueOf(this.txtPassword.getPassword()).trim());
            connection.setForceDBComparison(this.chkForceUpdateDB.isSelected() || RepositoryUtil.isDatabaseEmpty());
            String allMAC = "";
            try {
                allMAC = Connection.getMAC();
            } catch (SocketException ex) {
            }
            connection.setUserIdStr(System.getProperty("user.name") + ":" + System.getProperty("os.name") + ":" + MagePreferences.getUserNames() + ":" + allMAC);
            MageFrame.getPreferences().put(KEY_CONNECT_FLAG, ((CountryItemEditor) cbFlag.getEditor()).getImageItem());
            PreferencesDialog.setProxyInformation(connection);

            // pref settings
            MageFrame.getInstance().setUserPrefsToConnection(connection);

            logger.debug("connecting: " + connection.getProxyType() + ' ' + connection.getProxyHost() + ' ' + connection.getProxyPort());
            task = new ConnectTask();
            task.execute();
        } finally {
            Arrays.fill(input, '0');
        }

    }//GEN-LAST:event_btnConnectActionPerformed

    private void setConnectButtonsState(boolean enable) {
        btnConnect.setEnabled(enable);
        btnRegister.setEnabled(enable);
        btnForgotPassword.setEnabled(enable);
    }

    private class ConnectTask extends SwingWorker<Boolean, Void> {

        private boolean result = false;
        private String lastConnectError = "";

        private static final int CONNECTION_TIMEOUT_MS = 2100;

        @Override
        protected Boolean doInBackground() throws Exception {
            lblStatus.setText("Connecting...");
            setConnectButtonsState(false);
            result = MageFrame.connect(connection);
            lastConnectError = SessionHandler.getLastConnectError();
            return result;
        }

        @Override
        protected void done() {
            try {
                get(CONNECTION_TIMEOUT_MS, TimeUnit.MILLISECONDS);
                if (result) {
                    lblStatus.setText("");
                    connected();
                    MageFrame.getInstance().prepareAndShowTablesPane();
                } else {
                    lblStatus.setText("Could not connect: " + lastConnectError);
                }
            } catch (InterruptedException | ExecutionException ex) {
                logger.fatal("Update Players Task error", ex);
            } catch (CancellationException ex) {
                logger.info("Connect: canceled");
                lblStatus.setText("Connect was canceled");
            } catch (TimeoutException ex) {
                logger.fatal("Connection timeout: ", ex);
            } finally {
                MageFrame.stopConnecting();
                setConnectButtonsState(true);
            }
        }
    }

    private void connected() {
        this.saveSettings();
        this.hideDialog();
        ConstructedFormats.ensureLists();
    }

    private void keyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_keyTyped
        char c = evt.getKeyChar();
        if (!Character.isDigit(c)) {
            evt.consume();
        }
    }//GEN-LAST:event_keyTyped

    private void chkAutoConnectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkAutoConnectActionPerformed

        // TODO add your handling code here:
    }//GEN-LAST:event_chkAutoConnectActionPerformed

    private void findPublicServerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jProxySettingsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jProxySettingsButtonActionPerformed
        PreferencesDialog.main(new String[]{PreferencesDialog.OPEN_CONNECTION_TAB});
    }//GEN-LAST:event_jProxySettingsButtonActionPerformed

    private void chkForceUpdateDBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkForceUpdateDBActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_chkForceUpdateDBActionPerformed

    private void txtUserNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtUserNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtUserNameActionPerformed

    private void txtPasswordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPasswordActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPasswordActionPerformed

    private void btnRegisterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegisterActionPerformed
        registerUserDialog.showDialog();
    }//GEN-LAST:event_btnRegisterActionPerformed

    private void btnForgotPasswordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnForgotPasswordActionPerformed
        resetPasswordDialog.showDialog();
    }//GEN-LAST:event_btnForgotPasswordActionPerformed

    private void connectXmageDe(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFind1findPublicServerActionPerformed
        String serverAddress = "xmage.de";
        this.txtServer.setText(serverAddress);
        this.txtPort.setText("17171");
        // Update userName and password according to the chosen server.
        this.txtUserName.setText(MagePreferences.getUserName(serverAddress));
        this.txtPassword.setText(MagePreferences.getPassword(serverAddress));        // TODO add your handling code here:
    }//GEN-LAST:event_btnFind1findPublicServerActionPerformed

    private void connectLocalhost(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFind2findPublicServerActionPerformed
        String serverAddress = "localhost";
        this.txtServer.setText(serverAddress);
        this.txtPort.setText("17171");
        // Update userName and password according to the chosen server.
        this.txtUserName.setText(MagePreferences.getUserName(serverAddress));
        this.txtPassword.setText(MagePreferences.getPassword(serverAddress));

    }//GEN-LAST:event_btnFind2findPublicServerActionPerformed

    private void connectXmageus(java.awt.event.ActionEvent evt) {
        String serverAddress = "us.xmage.today";
        this.txtServer.setText(serverAddress);
        this.txtPort.setText("17171");
        // Update userName and password according to the chosen server.
        this.txtUserName.setText(MagePreferences.getUserName(serverAddress));
        this.txtPassword.setText(MagePreferences.getPassword(serverAddress));
    }

    private void connectBeta(java.awt.event.ActionEvent evt) {
        String serverAddress = "beta.xmage.today";
        this.txtServer.setText(serverAddress);
        this.txtPort.setText("17171");
        // Update userName and password according to the chosen server.
        this.txtUserName.setText(MagePreferences.getUserName(serverAddress));
        this.txtPassword.setText(MagePreferences.getPassword(serverAddress));
    }

    private void btnFlagSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFlagSearchActionPerformed
        doFastFlagSearch();
    }//GEN-LAST:event_btnFlagSearchActionPerformed

    private void btnCheckStatusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCheckStatusActionPerformed
        if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
            try {
                Desktop.getDesktop().browse(new URI("http://xmage.today/servers/"));
            } catch (Exception e) {
                //
            }
        }
    }//GEN-LAST:event_btnCheckStatusActionPerformed

    private void btnWhatsNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnWhatsNewActionPerformed
        MageFrame.showWhatsNewDialog();
    }//GEN-LAST:event_btnWhatsNewActionPerformed

    private void btnFindMainActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFindMainActionPerformed
        setServerSettings("xmage.de", "17171", true);
    }//GEN-LAST:event_btnFindMainActionPerformed

    private void btnFindEUActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFindEUActionPerformed
        setServerSettings("eu.xmage.today", "17171", false);
    }//GEN-LAST:event_btnFindEUActionPerformed

    private void btnFindUsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFindUsActionPerformed
        setServerSettings("us.xmage.today", "17171", false);
    }//GEN-LAST:event_btnFindUsActionPerformed

    private void btnFindBetaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFindBetaActionPerformed
        setServerSettings("beta.xmage.today", "17171", false);
    }//GEN-LAST:event_btnFindBetaActionPerformed

    private void btnFindLocalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFindLocalActionPerformed
        setServerSettings("localhost", "17171", false);
    }//GEN-LAST:event_btnFindLocalActionPerformed

    private void btnFindOtherActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFindOtherActionPerformed
        chooseAndSetServerSettingsFromOther();
    }//GEN-LAST:event_btnFindOtherActionPerformed

    private void doFastFlagSearch() {
        Choice choice = new ChoiceImpl(false);

        // collect data from country combobox String[name][code]
        Map<String, String> choiceItems = new LinkedHashMap<>();
        DefaultComboBoxModel flagModel = (DefaultComboBoxModel) cbFlag.getModel();
        String[] flagItem;

        for (int i = 0; i < flagModel.getSize(); i++) {
            flagItem = (String[]) flagModel.getElementAt(i);
            choiceItems.put(flagItem[1], flagItem[0]);
        }

        choice.setKeyChoices(choiceItems);
        choice.setMessage("Select your country");

        // current selection value restore
        String needSelectValue = null;
        flagItem = (String[]) flagModel.getSelectedItem();
        if (flagItem != null) {
            needSelectValue = flagItem[1];
        }

        // ask for new value
        PickChoiceDialog dlg = new PickChoiceDialog();
        dlg.setWindowSize(300, 500);
        dlg.showDialog(choice, needSelectValue);
        if (choice.isChosen()) {
            flagItem = new String[2];
            flagItem[0] = choice.getChoiceValue();
            flagItem[1] = choice.getChoiceKey();
            flagModel.setSelectedItem(flagItem);
        }
    }

    public String getServer() {
        return this.txtServer.getText();
    }

    public String getPort() {
        return this.txtPort.getText();
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnCheckStatus;
    private javax.swing.JButton btnConnect;
    private javax.swing.JButton btnFindBeta;
    private javax.swing.JButton btnFindEU;
    private javax.swing.JButton btnFindLocal;
    private javax.swing.JButton btnFindMain;
    private javax.swing.JButton btnFindOther;
    private javax.swing.JButton btnFindUs;
    private javax.swing.JButton btnFlagSearch;
    private javax.swing.JButton btnForgotPassword;
    private javax.swing.JButton btnRegister;
    private javax.swing.JButton btnWhatsNew;
    private mage.client.util.gui.countryBox.CountryComboBox cbFlag;
    private javax.swing.JCheckBox chkAutoConnect;
    private javax.swing.JCheckBox chkForceUpdateDB;
    private javax.swing.Box.Filler filler1;
    private javax.swing.Box.Filler filler2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JButton jProxySettingsButton;
    private javax.swing.JLabel lblFastConnect;
    private javax.swing.JLabel lblFlag;
    private javax.swing.JLabel lblPassword;
    private javax.swing.JLabel lblPort;
    private javax.swing.JLabel lblServer;
    private javax.swing.JLabel lblStatus;
    private javax.swing.JLabel lblUserName;
    private javax.swing.JPanel panelFast;
    private javax.swing.JPanel panelFlag;
    private javax.swing.JPanel panelServer;
    private javax.swing.JPasswordField txtPassword;
    private javax.swing.JTextField txtPort;
    private javax.swing.JTextField txtServer;
    private javax.swing.JTextField txtUserName;
    // End of variables declaration//GEN-END:variables

}
