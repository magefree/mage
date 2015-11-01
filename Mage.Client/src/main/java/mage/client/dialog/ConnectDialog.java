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
 * ConnectDialog.java
 *
 * Created on 20-Jan-2010, 9:37:07 PM
 */
package mage.client.dialog;

import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.Writer;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;
import mage.client.MageFrame;
import static mage.client.dialog.PreferencesDialog.KEY_CONNECTION_URL_SERVER_LIST;
import static mage.client.dialog.PreferencesDialog.KEY_CONNECT_AUTO_CONNECT;
import static mage.client.dialog.PreferencesDialog.KEY_CONNECT_FLAG;
import mage.client.util.Config;
import mage.client.util.gui.countryBox.CountryItemEditor;
import mage.remote.Connection;
import mage.remote.Connection.ProxyType;
import org.apache.log4j.Logger;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class ConnectDialog extends MageDialog {

    private static final Logger logger = Logger.getLogger(ConnectDialog.class);
    private Connection connection;
    private ConnectTask task;

    private final ActionListener connectAction = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent evt) {
            btnConnectActionPerformed(evt);
        }
    };

    /**
     * Creates new form ConnectDialog
     */
    public ConnectDialog() {
        initComponents();

        this.txtUserName.addActionListener(connectAction);
        this.txtServer.addActionListener(connectAction);
        this.txtPort.addActionListener(connectAction);
    }

    public void showDialog() {
        this.txtServer.setText(MageFrame.getPreferences().get("serverAddress", Config.serverName));
        this.txtPort.setText(MageFrame.getPreferences().get("serverPort", Integer.toString(Config.port)));
        this.txtUserName.setText(MageFrame.getPreferences().get("userName", ""));
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
        MageFrame.getPreferences().put("serverAddress", txtServer.getText().trim());
        MageFrame.getPreferences().put("serverPort", txtPort.getText().trim());
        MageFrame.getPreferences().put("userName", txtUserName.getText().trim());
        MageFrame.getPreferences().put(KEY_CONNECT_AUTO_CONNECT, Boolean.toString(chkAutoConnect.isSelected()));
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
        txtServer = new javax.swing.JTextField();
        btnFind = new javax.swing.JButton();
        lblPort = new javax.swing.JLabel();
        txtPort = new javax.swing.JTextField();
        lblUserName = new javax.swing.JLabel();
        txtUserName = new javax.swing.JTextField();
        lblFlag = new javax.swing.JLabel();
        cbFlag = new mage.client.util.gui.countryBox.CountryComboBox();
        chkAutoConnect = new javax.swing.JCheckBox();
        chkForceUpdateDB = new javax.swing.JCheckBox();
        jProxySettingsButton = new javax.swing.JButton();
        btnConnect = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();
        lblStatus = new javax.swing.JLabel();

        setTitle("Connect to server");
        setNormalBounds(new java.awt.Rectangle(100, 100, 410, 307));

        lblServer.setLabelFor(txtServer);
        lblServer.setText("Server:");

        btnFind.setText("Find...");
        btnFind.setToolTipText("Shows the list of public servers");
        btnFind.setName("findServerBtn"); // NOI18N
        btnFind.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                findPublicServerActionPerformed(evt);
            }
        });

        lblPort.setLabelFor(txtPort);
        lblPort.setText("Port:");

        txtPort.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                ConnectDialog.this.keyTyped(evt);
            }
        });

        lblUserName.setLabelFor(txtUserName);
        lblUserName.setText("User name:");

        lblFlag.setLabelFor(txtUserName);
        lblFlag.setText("User flag:");

        cbFlag.setEditable(true);

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

        btnConnect.setText("Connect");
        btnConnect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConnectActionPerformed(evt);
            }
        });

        btnCancel.setText("Cancel");
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnConnect)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCancel))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(lblPort)
                                .addComponent(lblServer)
                                .addComponent(lblUserName))
                            .addComponent(lblFlag, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblStatus, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jProxySettingsButton)
                            .addComponent(cbFlag, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(txtServer, javax.swing.GroupLayout.DEFAULT_SIZE, 286, Short.MAX_VALUE)
                                    .addComponent(txtPort, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtUserName))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnFind))
                            .addComponent(chkForceUpdateDB, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(chkAutoConnect, javax.swing.GroupLayout.DEFAULT_SIZE, 358, Short.MAX_VALUE))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblServer)
                    .addComponent(txtServer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnFind))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtPort, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblPort))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtUserName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblUserName))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lblFlag, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cbFlag, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(5, 5, 5)
                .addComponent(chkAutoConnect)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(chkForceUpdateDB)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jProxySettingsButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 50, Short.MAX_VALUE)
                .addComponent(lblStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnConnect)
                    .addComponent(btnCancel))
                .addContainerGap())
        );

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

        if (txtUserName.getText().isEmpty()) {
            JOptionPane.showMessageDialog(rootPane, "Please provide a user name");
            return;
        }
        if (txtServer.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(rootPane, "Please provide a server address");
            return;
        }
        if (txtPort.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(rootPane, "Please provide a port number");
            return;
        }
        if (Integer.valueOf(txtPort.getText()) < 1 || Integer.valueOf(txtPort.getText()) > 65535) {
            JOptionPane.showMessageDialog(rootPane, "Invalid port number");
            txtPort.setText(MageFrame.getPreferences().get("serverPort", Integer.toString(Config.port)));
            return;
        }

        char[] input = new char[0];
        try {
            setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            connection = new Connection();
            connection.setHost(this.txtServer.getText().trim());
            connection.setPort(Integer.valueOf(this.txtPort.getText().trim()));
            connection.setUsername(this.txtUserName.getText().trim());
            connection.setForceDBComparison(this.chkForceUpdateDB.isSelected());
            MageFrame.getPreferences().put(KEY_CONNECT_FLAG, ((CountryItemEditor) cbFlag.getEditor()).getImageItem());

            ProxyType configProxyType = Connection.ProxyType.valueByText(PreferencesDialog.getCachedValue(PreferencesDialog.KEY_PROXY_TYPE, "None"));

            if (configProxyType != null) {
                connection.setProxyType(configProxyType);
                if (!configProxyType.equals(ProxyType.NONE)) {
                    String host = PreferencesDialog.getCachedValue(PreferencesDialog.KEY_PROXY_ADDRESS, "");
                    String port = PreferencesDialog.getCachedValue(PreferencesDialog.KEY_PROXY_PORT, "");
                    if (!host.isEmpty() && !port.isEmpty()) {
                        connection.setProxyHost(host);
                        connection.setProxyPort(Integer.valueOf(port));
                        String username = PreferencesDialog.getCachedValue(PreferencesDialog.KEY_PROXY_USERNAME, "");
                        connection.setProxyUsername(username);
                        if (PreferencesDialog.getCachedValue(PreferencesDialog.KEY_PROXY_REMEMBER, "false").equals("true")) {
                            String password = PreferencesDialog.getCachedValue(PreferencesDialog.KEY_PROXY_PSWD, "");
                            connection.setProxyPassword(password);
                        }
                    } else {
                        logger.warn("host or\\and port are empty: host=" + host + ", port=" + port);
                    }
                }
            }

            // pref settings
            MageFrame.getInstance().setUserPrefsToConnection(connection);

            logger.debug("connecting: " + connection.getProxyType() + " " + connection.getProxyHost() + " " + connection.getProxyPort());
            task = new ConnectTask();
            task.execute();
        } finally {
            Arrays.fill(input, '0');
        }

    }//GEN-LAST:event_btnConnectActionPerformed

    private class ConnectTask extends SwingWorker<Boolean, Void> {

        private boolean result = false;

        private static final int CONNECTION_TIMEOUT_MS = 2100;

        @Override
        protected Boolean doInBackground() throws Exception {
            lblStatus.setText("Connecting...");
            btnConnect.setEnabled(false);
            result = MageFrame.connect(connection);
            return result;
        }

        @Override
        protected void done() {
            try {
                get(CONNECTION_TIMEOUT_MS, TimeUnit.MILLISECONDS);
                setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                if (result) {
                    lblStatus.setText("");
                    connected();
                    MageFrame.getInstance().showGames(false);
                } else {
                    lblStatus.setText("Could not connect");
                }
            } catch (InterruptedException ex) {
                logger.fatal("Update Players Task error", ex);
            } catch (ExecutionException ex) {
                logger.fatal("Update Players Task error", ex);
            } catch (CancellationException ex) {
                logger.info("Connect was canceled");
                lblStatus.setText("Connect was canceled");
            } catch (TimeoutException ex) {
                logger.fatal("Connection timeout: ", ex);
            } finally {
                MageFrame.stopConnecting();
                btnConnect.setEnabled(true);
            }
        }
    }

    private void connected() {
        this.saveSettings();
        this.hideDialog();
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
        BufferedReader in = null;
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
                Writer output = null;
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
                if (output != null) {
                    output.close();
                }
                in.close();
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
                    this.txtServer.setText(params[1]);
                    this.txtPort.setText(params[2]);
                } else {
                    JOptionPane.showMessageDialog(null, "Wrong server data format.");
                }
            }

        } catch (Exception ex) {
            logger.error(ex, ex);
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (Exception e) {
                }
            }
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jProxySettingsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jProxySettingsButtonActionPerformed
        PreferencesDialog.main(new String[]{PreferencesDialog.OPEN_CONNECTION_TAB});
    }//GEN-LAST:event_jProxySettingsButtonActionPerformed

    private void chkForceUpdateDBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkForceUpdateDBActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_chkForceUpdateDBActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnConnect;
    private javax.swing.JButton btnFind;
    private mage.client.util.gui.countryBox.CountryComboBox cbFlag;
    private javax.swing.JCheckBox chkAutoConnect;
    private javax.swing.JCheckBox chkForceUpdateDB;
    private javax.swing.JButton jProxySettingsButton;
    private javax.swing.JLabel lblFlag;
    private javax.swing.JLabel lblPort;
    private javax.swing.JLabel lblServer;
    private javax.swing.JLabel lblStatus;
    private javax.swing.JLabel lblUserName;
    private javax.swing.JTextField txtPort;
    private javax.swing.JTextField txtServer;
    private javax.swing.JTextField txtUserName;
    // End of variables declaration//GEN-END:variables

}
