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

package mage.server.console;

import mage.remote.Connection;
import mage.remote.Connection.ProxyType;
import org.apache.log4j.Logger;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class ConnectDialog extends JDialog {

	private final static Logger logger = Logger.getLogger(ConnectDialog.class);
	private ConsoleFrame console;
	private Connection connection;
	private ConnectTask task;

    /** Creates new form ConnectDialog */
    public ConnectDialog() {
       initComponents();
		cbProxyType.setModel(new DefaultComboBoxModel(Connection.ProxyType.values()));
    }

	public void showDialog(ConsoleFrame console) {
		this.console = console;
		this.txtServer.setText(ConsoleFrame.getPreferences().get("serverAddress", "localhost"));
		this.txtPort.setText(ConsoleFrame.getPreferences().get("serverPort", Integer.toString(17171)));
		this.chkAutoConnect.setSelected(Boolean.parseBoolean(ConsoleFrame.getPreferences().get("autoConnect", "false")));
		this.txtProxyServer.setText(ConsoleFrame.getPreferences().get("proxyAddress", "localhost"));
		this.txtProxyPort.setText(ConsoleFrame.getPreferences().get("proxyPort", Integer.toString(17171)));
		this.cbProxyType.setSelectedItem(Connection.ProxyType.valueOf(ConsoleFrame.getPreferences().get("proxyType", "NONE").toUpperCase()));
		this.txtProxyUserName.setText(ConsoleFrame.getPreferences().get("proxyUsername", ""));
		this.txtPasswordField.setText(ConsoleFrame.getPreferences().get("proxyPassword", ""));
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
		this.repaint();
	}

	private void saveSettings() {
		ConsoleFrame.getPreferences().put("serverAddress", txtServer.getText());
		ConsoleFrame.getPreferences().put("serverPort", txtPort.getText());
		ConsoleFrame.getPreferences().put("autoConnect", Boolean.toString(chkAutoConnect.isSelected()));
		ConsoleFrame.getPreferences().put("proxyAddress", txtProxyServer.getText());
		ConsoleFrame.getPreferences().put("proxyPort", txtProxyPort.getText());
		ConsoleFrame.getPreferences().put("proxyType", cbProxyType.getSelectedItem().toString());
		ConsoleFrame.getPreferences().put("proxyUsername", txtProxyUserName.getText());
		char[] input = txtPasswordField.getPassword();
		ConsoleFrame.getPreferences().put("proxyPassword", new String(input));
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
        lblPassword = new javax.swing.JLabel();
        txtPassword = new javax.swing.JPasswordField();
        lblStatus = new javax.swing.JLabel();

        setTitle("Connect");

        lblServer.setLabelFor(txtServer);
        lblServer.setText("Server:");

        lblPort.setLabelFor(txtPort);
        lblPort.setText("Port:");

        txtPort.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                ConnectDialog.this.keyTyped(evt);
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

        lblProxyServer.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
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
                .addContainerGap()
                .addGroup(pnlProxyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblProxyServer, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblProxyPort))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlProxyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtProxyPort, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtProxyServer, javax.swing.GroupLayout.DEFAULT_SIZE, 334, Short.MAX_VALUE))
                .addContainerGap())
        );
        pnlProxyLayout.setVerticalGroup(
            pnlProxyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlProxyLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlProxyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblProxyServer)
                    .addComponent(txtProxyServer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlProxyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtProxyPort, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblProxyPort))
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
                .addGroup(pnlProxyAuthLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtPasswordField, javax.swing.GroupLayout.DEFAULT_SIZE, 335, Short.MAX_VALUE)
                    .addComponent(txtProxyUserName, javax.swing.GroupLayout.DEFAULT_SIZE, 335, Short.MAX_VALUE))
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
            .addComponent(pnlProxyAuth, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(pnlProxy, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        pnlProxySettingsLayout.setVerticalGroup(
            pnlProxySettingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlProxySettingsLayout.createSequentialGroup()
                .addComponent(pnlProxy, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(pnlProxyAuth, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        lblPassword.setLabelFor(txtPassword);
        lblPassword.setText("Password:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(lblStatus, javax.swing.GroupLayout.DEFAULT_SIZE, 271, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnConnect)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCancel))
                    .addComponent(pnlProxySettings, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblProxyType)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(chkAutoConnect, javax.swing.GroupLayout.DEFAULT_SIZE, 383, Short.MAX_VALUE)
                            .addComponent(cbProxyType, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lblPassword)
                            .addComponent(lblServer)
                            .addComponent(lblPort))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtPassword, javax.swing.GroupLayout.DEFAULT_SIZE, 359, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addComponent(txtPort, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(131, 131, 131))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(txtServer, javax.swing.GroupLayout.DEFAULT_SIZE, 288, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton1)))))
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
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblPassword))
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
                    .addComponent(btnConnect)
                    .addComponent(lblStatus))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

	private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
		ConsoleFrame.getPreferences().put("autoConnect", Boolean.toString(chkAutoConnect.isSelected()));
		if (task != null && !task.isDone())
			task.cancel(true);
		else
			this.setVisible(false);
	}//GEN-LAST:event_btnCancelActionPerformed

	private void btnConnectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConnectActionPerformed
		
		if (txtPassword.getPassword().length == 0) {
			JOptionPane.showMessageDialog(rootPane, "Please provide a password");
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
			txtPort.setText(ConsoleFrame.getPreferences().get("serverPort", Integer.toString(17171)));
			return;
		}

		setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
		connection = new Connection();
		connection.setHost(this.txtServer.getText());
		connection.setPort(Integer.valueOf(this.txtPort.getText()));
		connection.setPassword(new String(txtPassword.getPassword()));

        connection.setProxyType((ProxyType) this.cbProxyType.getSelectedItem());
        if (!this.cbProxyType.getSelectedItem().equals(ProxyType.NONE)) {
		    connection.setProxyHost(this.txtProxyServer.getText());
		    connection.setProxyPort(Integer.valueOf(this.txtProxyPort.getText()));
		    connection.setProxyUsername(this.txtProxyUserName.getText());
		    connection.setProxyPassword(new String(this.txtPasswordField.getPassword()));
        }

		logger.debug("connecting: " + connection.getProxyType() + " " + connection.getProxyHost() + " " + connection.getProxyPort());
		task = new ConnectTask();
		task.execute();

	}//GEN-LAST:event_btnConnectActionPerformed

	private class ConnectTask extends SwingWorker<Boolean, Void> {

		private boolean result = false;

		@Override
		protected Boolean doInBackground() throws Exception {
			lblStatus.setText("Connecting...");
			btnConnect.setEnabled(false);
			result = console.connect(connection);
			return result;
		}

		@Override
		protected void done() {
			try {
				get();
				setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
				btnConnect.setEnabled(true);
				if (result) {
					lblStatus.setText("");
					connected();
				}
				else {
					lblStatus.setText("Could not connect");
				}
			} catch (InterruptedException ex) {
				logger.fatal("Update Players Task error", ex);
			} catch (ExecutionException ex) {
				logger.fatal("Update Players Task error", ex);
			} catch (CancellationException ex) {}
		}
	}

	private void connected() {
		this.saveSettings();
		this.setVisible(false);
	}


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
    private javax.swing.JLabel lblPassword;
    private javax.swing.JLabel lblPort;
    private javax.swing.JLabel lblProxyPassword;
    private javax.swing.JLabel lblProxyPort;
    private javax.swing.JLabel lblProxyServer;
    private javax.swing.JLabel lblProxyType;
    private javax.swing.JLabel lblProxyUserName;
    private javax.swing.JLabel lblServer;
    private javax.swing.JLabel lblStatus;
    private javax.swing.JPanel pnlProxy;
    private javax.swing.JPanel pnlProxyAuth;
    private javax.swing.JPanel pnlProxySettings;
    private javax.swing.JPasswordField txtPassword;
    private javax.swing.JPasswordField txtPasswordField;
    private javax.swing.JTextField txtPort;
    private javax.swing.JTextField txtProxyPort;
    private javax.swing.JTextField txtProxyServer;
    private javax.swing.JTextField txtProxyUserName;
    private javax.swing.JTextField txtServer;
    // End of variables declaration//GEN-END:variables

}
