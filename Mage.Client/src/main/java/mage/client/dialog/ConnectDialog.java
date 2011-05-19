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
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.swing.DefaultComboBoxModel;

import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import mage.client.MageFrame;
import mage.client.util.Config;
import mage.utils.Connection;
import mage.utils.Connection.ProxyType;

import org.apache.log4j.Logger;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class ConnectDialog extends MageDialog {

	private final static Logger logger = Logger.getLogger(ConnectDialog.class);
	

    /** Creates new form ConnectDialog */
    public ConnectDialog() {
       initComponents();
		cbProxyType.setModel(new DefaultComboBoxModel(Connection.ProxyType.values()));
    }

	public void showDialog() {
		this.txtServer.setText(MageFrame.getPreferences().get("serverAddress", Config.serverName));
		this.txtPort.setText(MageFrame.getPreferences().get("serverPort", Integer.toString(Config.port)));
		this.txtUserName.setText(MageFrame.getPreferences().get("userName", ""));
		this.chkAutoConnect.setSelected(Boolean.parseBoolean(MageFrame.getPreferences().get("autoConnect", "false")));
		this.txtProxyServer.setText(MageFrame.getPreferences().get("proxyAddress", Config.serverName));
		this.txtProxyPort.setText(MageFrame.getPreferences().get("proxyPort", Integer.toString(Config.port)));
		this.cbProxyType.setSelectedItem(Connection.ProxyType.valueOf(MageFrame.getPreferences().get("proxyType", "NONE").toUpperCase()));
		this.txtProxyUserName.setText(MageFrame.getPreferences().get("proxyUsername", ""));
		this.txtPasswordField.setText(MageFrame.getPreferences().get("proxyPassword", ""));
		this.showProxySettings();
		this.setModal(true);
		this.setLocation(50, 50);
		this.setVisible(true);
	}

	private void showProxySettings() {
		if (cbProxyType.getSelectedItem() == Connection.ProxyType.SOCKS) {
			this.pnlProxy.setVisible(true);
			this.pnlProxyAuth.setVisible(false);
			this.pnlProxySettings.setVisible(true);
		}
		else if (cbProxyType.getSelectedItem() == Connection.ProxyType.HTTP) {
			this.pnlProxy.setVisible(true);
			this.pnlProxyAuth.setVisible(true);
			this.pnlProxySettings.setVisible(true);
		}
		else if (cbProxyType.getSelectedItem() == Connection.ProxyType.NONE) {
			this.pnlProxy.setVisible(false);
			this.pnlProxyAuth.setVisible(false);
			this.pnlProxySettings.setVisible(false);
		}
		this.pack();
		this.revalidate();
		this.repaint();
	}

	private void saveSettings() {
		MageFrame.getPreferences().put("serverAddress", txtServer.getText());
		MageFrame.getPreferences().put("serverPort", txtPort.getText());
		MageFrame.getPreferences().put("userName", txtUserName.getText());
		MageFrame.getPreferences().put("autoConnect", Boolean.toString(chkAutoConnect.isSelected()));
		MageFrame.getPreferences().put("proxyAddress", txtProxyServer.getText());
		MageFrame.getPreferences().put("proxyPort", txtProxyPort.getText());
		MageFrame.getPreferences().put("proxyType", cbProxyType.getSelectedItem().toString());
		MageFrame.getPreferences().put("proxyUsername", txtProxyUserName.getText());
		char[] input = txtPasswordField.getPassword();
		MageFrame.getPreferences().put("proxyPassword", new String(input));
		Arrays.fill(input, '0');
	}

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        txtServer = new javax.swing.JTextField();
        lblServer = new javax.swing.JLabel();
        lblPort = new javax.swing.JLabel();
        txtPort = new javax.swing.JTextField();
        txtUserName = new javax.swing.JTextField();
        lblUserName = new javax.swing.JLabel();
        btnConnect = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();
        chkAutoConnect = new javax.swing.JCheckBox();
        jButton1 = new javax.swing.JButton();
        cbProxyType = new javax.swing.JComboBox();
        lblProxyType = new javax.swing.JLabel();
        pnlProxySettings = new javax.swing.JPanel();
        pnlProxy = new javax.swing.JPanel();
        lblProxyServer = new javax.swing.JLabel();
        txtProxyServer = new javax.swing.JTextField();
        lblProxyPort = new javax.swing.JLabel();
        txtProxyPort = new javax.swing.JTextField();
        pnlProxyAuth = new javax.swing.JPanel();
        lblProxyUserName = new javax.swing.JLabel();
        txtProxyUserName = new javax.swing.JTextField();
        lblProxyPassword = new javax.swing.JLabel();
        txtPasswordField = new javax.swing.JPasswordField();

        setTitle("Connect");
        setNormalBounds(new java.awt.Rectangle(100, 100, 410, 307));

        lblServer.setLabelFor(txtServer);
        lblServer.setText("Server:");

        lblPort.setLabelFor(txtPort);
        lblPort.setText("Port:");

        txtPort.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                ConnectDialog.this.keyTyped(evt);
            }
        });

        lblUserName.setLabelFor(txtUserName);
        lblUserName.setText("User Name:");

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

        chkAutoConnect.setText("Automatically connect to this server next time");
        chkAutoConnect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkAutoConnectActionPerformed(evt);
            }
        });

        jButton1.setText("Find...");
        jButton1.setToolTipText("Find public server");
        jButton1.setName("findServerBtn"); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                findPublicServerActionPerformed(evt);
            }
        });

        cbProxyType.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbProxyTypeActionPerformed(evt);
            }
        });

        lblProxyType.setLabelFor(cbProxyType);
        lblProxyType.setText("Proxy:");

        pnlProxySettings.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        pnlProxySettings.setMinimumSize(new java.awt.Dimension(0, 0));

        lblProxyServer.setLabelFor(txtProxyServer);
        lblProxyServer.setText("Server:");

        lblProxyPort.setLabelFor(txtProxyPort);
        lblProxyPort.setText("Port:");

        txtProxyPort.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtProxyPortkeyTyped(evt);
            }
        });

        javax.swing.GroupLayout pnlProxyLayout = new javax.swing.GroupLayout(pnlProxy);
        pnlProxy.setLayout(pnlProxyLayout);
        pnlProxyLayout.setHorizontalGroup(
            pnlProxyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlProxyLayout.createSequentialGroup()
                .addContainerGap(28, Short.MAX_VALUE)
                .addGroup(pnlProxyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblProxyPort)
                    .addComponent(lblProxyServer))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlProxyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtProxyPort, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtProxyServer, javax.swing.GroupLayout.DEFAULT_SIZE, 360, Short.MAX_VALUE))
                .addContainerGap())
        );
        pnlProxyLayout.setVerticalGroup(
            pnlProxyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlProxyLayout.createSequentialGroup()
                .addGroup(pnlProxyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtProxyServer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblProxyServer))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlProxyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblProxyPort)
                    .addComponent(txtProxyPort, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        lblProxyUserName.setLabelFor(txtProxyUserName);
        lblProxyUserName.setText("User Name:");

        lblProxyPassword.setText("Password:");

        txtPasswordField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPasswordFieldActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlProxyAuthLayout = new javax.swing.GroupLayout(pnlProxyAuth);
        pnlProxyAuth.setLayout(pnlProxyAuthLayout);
        pnlProxyAuthLayout.setHorizontalGroup(
            pnlProxyAuthLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlProxyAuthLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlProxyAuthLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblProxyPassword)
                    .addComponent(lblProxyUserName))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlProxyAuthLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(txtPasswordField)
                    .addComponent(txtProxyUserName, javax.swing.GroupLayout.DEFAULT_SIZE, 358, Short.MAX_VALUE))
                .addContainerGap())
        );
        pnlProxyAuthLayout.setVerticalGroup(
            pnlProxyAuthLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlProxyAuthLayout.createSequentialGroup()
                .addGroup(pnlProxyAuthLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtProxyUserName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblProxyUserName))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 8, Short.MAX_VALUE)
                .addGroup(pnlProxyAuthLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtPasswordField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblProxyPassword))
                .addContainerGap())
        );

        javax.swing.GroupLayout pnlProxySettingsLayout = new javax.swing.GroupLayout(pnlProxySettings);
        pnlProxySettings.setLayout(pnlProxySettingsLayout);
        pnlProxySettingsLayout.setHorizontalGroup(
            pnlProxySettingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlProxy, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(pnlProxyAuth, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        pnlProxySettingsLayout.setVerticalGroup(
            pnlProxySettingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlProxySettingsLayout.createSequentialGroup()
                .addComponent(pnlProxy, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 26, Short.MAX_VALUE)
                .addComponent(pnlProxyAuth, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lblProxyType)
                            .addComponent(lblPort)
                            .addComponent(lblServer)
                            .addComponent(lblUserName))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(txtPort, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(131, 131, 131))
                            .addComponent(txtUserName, javax.swing.GroupLayout.DEFAULT_SIZE, 382, Short.MAX_VALUE)
                            .addComponent(chkAutoConnect, javax.swing.GroupLayout.DEFAULT_SIZE, 382, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(txtServer, javax.swing.GroupLayout.DEFAULT_SIZE, 311, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton1))
                            .addComponent(cbProxyType, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(btnConnect)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCancel))
                    .addComponent(pnlProxySettings, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblServer)
                    .addComponent(txtServer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtPort, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblPort))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtUserName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblUserName))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(chkAutoConnect)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbProxyType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblProxyType))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlProxySettings, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCancel)
                    .addComponent(btnConnect))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

	private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
		MageFrame.getPreferences().put("autoConnect", Boolean.toString(chkAutoConnect.isSelected()));
		this.setVisible(false);
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
		if (Integer.valueOf(txtPort.getText()) < 1 || Integer.valueOf(txtPort.getText()) > 65535 ) {
			JOptionPane.showMessageDialog(rootPane, "Invalid port number");
			txtPort.setText(MageFrame.getPreferences().get("serverPort", Integer.toString(Config.port)));
			return;
		}

		char[] input = new char[0];
		try {
			setCursor(new Cursor(Cursor.WAIT_CURSOR));
			Connection connection = new Connection();
			connection.setHost(this.txtServer.getText());
			connection.setPort(Integer.valueOf(this.txtPort.getText()));
			connection.setUsername(this.txtUserName.getText());
			connection.setProxyType((ProxyType) this.cbProxyType.getSelectedItem());
			connection.setProxyHost(this.txtProxyServer.getText());
			connection.setProxyPort(Integer.valueOf(this.txtProxyPort.getText()));
			connection.setProxyUsername(this.txtProxyUserName.getText());
			input = txtPasswordField.getPassword();
			connection.setProxyPassword(new String(input));
			logger.debug("connecting: " + connection.getProxyType() + " " + connection.getProxyHost() + " " + connection.getProxyPort());
			if (MageFrame.connect(connection)) {
				this.saveSettings();
				this.setVisible(false);
			}
		}
		finally {
			setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			Arrays.fill(input, '0');
		}

	}//GEN-LAST:event_btnConnectActionPerformed

	private void keyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_keyTyped
		char c = evt.getKeyChar();
		if (!Character.isDigit(c))
			evt.consume();
	}//GEN-LAST:event_keyTyped

	private void chkAutoConnectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkAutoConnectActionPerformed
		
		// TODO add your handling code here:
	}//GEN-LAST:event_chkAutoConnectActionPerformed

	private void txtProxyPortkeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtProxyPortkeyTyped
		// TODO add your handling code here:
	}//GEN-LAST:event_txtProxyPortkeyTyped

    private void findPublicServerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
    	BufferedReader in = null;
    	try {
			URL serverListURL = new URL("http://mage.googlecode.com/files/server-list.txt");
			in = new BufferedReader(new InputStreamReader(serverListURL.openStream()));
			
			List<String> servers = new ArrayList<String>();
			String inputLine;
			while ((inputLine = in.readLine()) != null) {
				System.out.println("Found server: "+inputLine);
				servers.add(inputLine);
			}
			
			if (servers.size() == 0) {
				JOptionPane.showMessageDialog(null, "Couldn't find any server.");
				return;
			}

			String selectedServer = (String) JOptionPane.showInputDialog(null,
					"Choose MAGE Public Server:", "Input",
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

			in.close();
		} catch(Exception ex) {
			logger.error(ex,ex);
		} finally {
			if (in != null) try { in.close(); } catch (Exception e) {}
		}
    }//GEN-LAST:event_jButton1ActionPerformed

	private void cbProxyTypeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbProxyTypeActionPerformed
		this.showProxySettings();
	}//GEN-LAST:event_cbProxyTypeActionPerformed

        private void txtPasswordFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPasswordFieldActionPerformed
            // TODO add your handling code here:
        }//GEN-LAST:event_txtPasswordFieldActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnConnect;
    private javax.swing.JComboBox cbProxyType;
    private javax.swing.JCheckBox chkAutoConnect;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel lblPort;
    private javax.swing.JLabel lblProxyPassword;
    private javax.swing.JLabel lblProxyPort;
    private javax.swing.JLabel lblProxyServer;
    private javax.swing.JLabel lblProxyType;
    private javax.swing.JLabel lblProxyUserName;
    private javax.swing.JLabel lblServer;
    private javax.swing.JLabel lblUserName;
    private javax.swing.JPanel pnlProxy;
    private javax.swing.JPanel pnlProxyAuth;
    private javax.swing.JPanel pnlProxySettings;
    private javax.swing.JPasswordField txtPasswordField;
    private javax.swing.JTextField txtPort;
    private javax.swing.JTextField txtProxyPort;
    private javax.swing.JTextField txtProxyServer;
    private javax.swing.JTextField txtProxyUserName;
    private javax.swing.JTextField txtServer;
    private javax.swing.JTextField txtUserName;
    // End of variables declaration//GEN-END:variables

}
