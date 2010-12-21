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
 * MageFrame.java
 *
 * Created on 15-Dec-2009, 9:11:37 PM
 */

package mage.client;

import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.Preferences;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JToolBar.Separator;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import mage.client.cards.CardsStorage;
import mage.client.components.MageComponents;
import mage.client.dialog.*;
import mage.client.plugins.impl.Plugins;
import mage.client.remote.Session;
import mage.client.util.EDTExceptionHandler;
import mage.client.util.gui.ArrowBuilder;
import mage.components.ImagePanel;
import mage.util.Logging;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class MageFrame extends javax.swing.JFrame {

	private final static Logger logger = Logging.getLogger(MageFrame.class.getName());

	private static Session session;
	private ConnectDialog connectDialog;
	private static CombatDialog combat;
	private static PickNumberDialog pickNumber;
	private static Preferences prefs = Preferences.userNodeForPackage(MageFrame.class);
	private JLabel title;
	private Rectangle titleRectangle;
	
	/**
	 * @return the session
	 */
	public static Session getSession() {
		return session;
	}

	public static JDesktopPane getDesktop() {
		return desktopPane;
	}

	public static Preferences getPreferences() {
		return prefs;
	}

    /** Creates new form MageFrame */
    public MageFrame() {

    	setTitle("Mage, version 0.5.1");
    	
		EDTExceptionHandler.registerExceptionHandler();
		addWindowListener(new WindowAdapter()
		{
			@Override
			public void windowClosing(WindowEvent e)
			{
				exitApp();
			}
		});

		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
			//MageSynthStyleFactory f = new MageSynthStyleFactory(SynthLookAndFeel.getStyleFactory());
			//SynthLookAndFeel.setStyleFactory(f);
		} catch (Exception ex) {
			logger.log(Level.SEVERE, null, ex);
		}

		Plugins.getInstance().loadPlugins();

		initComponents();
		setSize(1024,768);
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);

		session = new Session(this);
		connectDialog = new ConnectDialog();
		combat = new CombatDialog();
		pickNumber = new PickNumberDialog();
		desktopPane.add(connectDialog, JLayeredPane.POPUP_LAYER);
		desktopPane.add(combat, JLayeredPane.POPUP_LAYER);
		combat.hideDialog();
		desktopPane.add(pickNumber, JLayeredPane.POPUP_LAYER);
		session.getUI().addComponent(MageComponents.DESKTOP_PANE, desktopPane);

        JComponent cardInfoPane = Plugins.getInstance().getCardInfoPane();
        cardInfoPane.setSize(161, 221);
        cardInfoPane.setPreferredSize(new Dimension(161, 221));
        cardInfoPane.setVisible(false);
        session.getUI().addComponent(MageComponents.CARD_INFO_PANE, cardInfoPane);
        desktopPane.add(cardInfoPane, JLayeredPane.POPUP_LAYER);

		String filename = "/background.jpg";
		try {
			if (Plugins.getInstance().isThemePluginLoaded()) {
				Map<String, JComponent> ui = new HashMap<String, JComponent>();
				backgroundPane = (ImagePanel) Plugins.getInstance().updateTablePanel(ui);
			} else {
				InputStream is = this.getClass().getResourceAsStream(filename);
				BufferedImage background = ImageIO.read(is);
				backgroundPane = new ImagePanel(background, ImagePanel.SCALED);
			}
			backgroundPane.setSize(1024, 768);
			desktopPane.add(backgroundPane, JLayeredPane.DEFAULT_LAYER);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		filename = "/label-mage.png";
		try {
			InputStream is = this.getClass().getResourceAsStream(filename);
			
			float ratio = 1179.0f / 678.0f;
	    	titleRectangle = new Rectangle(640, (int)(640 / ratio));
	    	if (is != null) {
	    		BufferedImage image = ImageIO.read(is);
	    		//ImageIcon resized = new ImageIcon(image.getScaledInstance(titleRectangle.width, titleRectangle.height, java.awt.Image.SCALE_SMOOTH));
				title = new JLabel();
	    		title.setIcon(new ImageIcon(image));
	    		backgroundPane.setLayout(null);
				backgroundPane.add(title);
	    	}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		filename = "/icon-mage.png";
		try {
			InputStream is = this.getClass().getResourceAsStream(filename);
			
	    	if (is != null) {
	    		BufferedImage image = ImageIO.read(is);
	    		setIconImage(image);
	    	}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		desktopPane.add(ArrowBuilder.getArrowsPanel(), JLayeredPane.DRAG_LAYER);
		
		desktopPane.addComponentListener(new ComponentAdapter(){
			@Override
			public void componentResized(ComponentEvent e) {
				int width = ((JComponent)e.getSource()).getWidth();
				int height = ((JComponent)e.getSource()).getHeight();
				backgroundPane.setSize(width, height);
				JPanel arrowsPanel = ArrowBuilder.getArrowsPanelRef();
				if (arrowsPanel != null) arrowsPanel.setSize(width, height);
				if (title != null) {
					//title.setBorder(BorderFactory.createLineBorder(Color.red));
					title.setBounds((int)(width - titleRectangle.getWidth())/2, (int)(height - titleRectangle.getHeight())/2, titleRectangle.width, titleRectangle.height);
				}
			}
        });
		
		//TODO: move to plugin impl
		if (Plugins.getInstance().isCardPluginLoaded()) {
			Separator separator = new javax.swing.JToolBar.Separator();
			mageToolbar.add(separator);
			
	        JButton btnDownloadSymbols = new JButton("Symbols");
	        btnDownloadSymbols.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
	        btnDownloadSymbols.setFocusable(false);
	        btnDownloadSymbols.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
	        btnDownloadSymbols.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
	        btnDownloadSymbols.addActionListener(new java.awt.event.ActionListener() {
	            public void actionPerformed(java.awt.event.ActionEvent evt) {
	                btnSymbolsActionPerformed(evt);
	            }
	        });
	        mageToolbar.add(btnDownloadSymbols);
	        
	        separator = new javax.swing.JToolBar.Separator();
			mageToolbar.add(separator);
			
			JButton btnDownload = new JButton("Images");
			btnDownload.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
			btnDownload.setFocusable(false);
			btnDownload.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
			btnDownload.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
			btnDownload.addActionListener(new java.awt.event.ActionListener() {
	            public void actionPerformed(java.awt.event.ActionEvent evt) {
	                btnImagesActionPerformed(evt);
	            }
	        });
	        mageToolbar.add(btnDownload);
		}
		
		if (Plugins.getInstance().isCounterPluginLoaded()) {
			int i = Plugins.getInstance().getGamesPlayed();
			JLabel label = new JLabel("  Games played: " + String.valueOf(i));
			desktopPane.add(label, JLayeredPane.DEFAULT_LAYER + 1);
			label.setVisible(true);
			label.setForeground(Color.white);
			label.setBounds(0, 0, 180, 30);
		}
		
		session.getUI().addButton(MageComponents.TABLES_MENU_BUTTON, btnGames);
		
		SwingUtilities.invokeLater(new Runnable() {
            public void run() {
        		if (autoConnect())
        			enableButtons();
        		else
        			disableButtons();
            }
        });
    }
    
    private void  btnImagesActionPerformed(java.awt.event.ActionEvent evt) {
    	Plugins.getInstance().downloadImage(CardsStorage.getAllCards());
	}
    
    private void  btnSymbolsActionPerformed(java.awt.event.ActionEvent evt) {
    	if (JOptionPane.showConfirmDialog(null, "Do you want to download mana symbols?") == JOptionPane.OK_OPTION) {
    		Plugins.getInstance().downloadSymbols();
    	}
	}

	public void showGame(UUID gameId, UUID playerId) {
		this.tablesPane.hideTables();
		this.tablesPane.setVisible(false);
		this.gamePane.setVisible(true);
		this.gamePane.showGame(gameId, playerId);
	}

	public void watchGame(UUID gameId) {
		this.tablesPane.hideTables();
		this.tablesPane.setVisible(false);
		this.gamePane.setVisible(true);
		this.gamePane.watchGame(gameId);
	}

	public void replayGame() {
		this.tablesPane.hideTables();
		this.tablesPane.setVisible(false);
		this.gamePane.setVisible(true);
		this.gamePane.replayGame();
	}

	public static boolean connect(String userName, String serverName, int port) {
		return session.connect(userName, serverName, port);		
	}

	public boolean autoConnect() {
		boolean autoConnect = Boolean.parseBoolean(prefs.get("autoConnect", "false"));
		if (autoConnect) {
			String userName = prefs.get("userName", "");
			String server = prefs.get("serverAddress", "");
			int port = Integer.parseInt(prefs.get("serverPort", ""));
			try {
				setCursor(new Cursor(Cursor.WAIT_CURSOR));
				if (MageFrame.connect(userName, server, port)) {
					return true;
				}
				else {
					JOptionPane.showMessageDialog(rootPane, "Unable to connect to server");
				}
			}
			finally {
				setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}
		}
		return false;
	}

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        desktopPane = new javax.swing.JDesktopPane();
        tablesPane = new mage.client.table.TablesPane();
        gamePane = new mage.client.game.GamePane();
        deckEditorPane = new mage.client.deckeditor.DeckEditorPane();
        mageToolbar = new javax.swing.JToolBar();
        btnConnect = new javax.swing.JButton();
        jSeparator5 = new javax.swing.JToolBar.Separator();
        btnGames = new javax.swing.JButton();
        jSeparator4 = new javax.swing.JToolBar.Separator();
        btnDeckEditor = new javax.swing.JButton();
        jSeparator3 = new javax.swing.JToolBar.Separator();
        btnPreferences = new javax.swing.JButton();
        jSeparator2 = new javax.swing.JToolBar.Separator();
        btnAbout = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JToolBar.Separator();
        btnExit = new javax.swing.JButton();
        lblStatus = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        //setMinimumSize(new java.awt.Dimension(1024, 768));

        //desktopPane.setBackground(new java.awt.Color(204, 204, 204));
        tablesPane.setBounds(20, 10, 560, 440);
        desktopPane.add(tablesPane, javax.swing.JLayeredPane.DEFAULT_LAYER);
        try {
            tablesPane.setMaximum(true);
        } catch (java.beans.PropertyVetoException e1) {
            e1.printStackTrace();
        }
        gamePane.setBounds(20, 30, -1, -1);
        desktopPane.add(gamePane, javax.swing.JLayeredPane.DEFAULT_LAYER);
        try {
            gamePane.setMaximum(true);
        } catch (java.beans.PropertyVetoException e1) {
            e1.printStackTrace();
        }
        deckEditorPane.setBounds(140, 50, -1, -1);
        desktopPane.add(deckEditorPane, javax.swing.JLayeredPane.DEFAULT_LAYER);
        try {
            deckEditorPane.setMaximum(true);
        } catch (java.beans.PropertyVetoException e1) {
            e1.printStackTrace();
        }

        mageToolbar.setFloatable(false);
        mageToolbar.setRollover(true);

        btnConnect.setText("Connect");
        btnConnect.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btnConnect.setFocusable(false);
        btnConnect.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnConnect.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnConnect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConnectActionPerformed(evt);
            }
        });
        mageToolbar.add(btnConnect);
        mageToolbar.add(jSeparator4);

        btnGames.setText("Tables");
        btnGames.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btnGames.setFocusable(false);
        btnGames.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnGames.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnGames.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGamesActionPerformed(evt);
            }
        });
        mageToolbar.add(btnGames);
        mageToolbar.add(jSeparator3);

        btnDeckEditor.setText("Deck Editor");
        btnDeckEditor.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btnDeckEditor.setFocusable(false);
        btnDeckEditor.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnDeckEditor.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnDeckEditor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeckEditorActionPerformed(evt);
            }
        });
        mageToolbar.add(btnDeckEditor);
        mageToolbar.add(jSeparator2);
        
        btnPreferences.setText("Preferences");
        btnPreferences.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btnPreferences.setFocusable(false);
        btnPreferences.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnPreferences.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnPreferences.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPreferencesActionPerformed(evt);
            }
        });
        mageToolbar.add(btnPreferences);
        mageToolbar.add(jSeparator5);

        btnAbout.setText("About");
        btnAbout.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btnAbout.setFocusable(false);
        btnAbout.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnAbout.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnAbout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAboutActionPerformed(evt);
            }
        });
        mageToolbar.add(btnAbout);
        mageToolbar.add(jSeparator1);

        btnExit.setText("Exit");
        btnExit.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btnExit.setFocusable(false);
        btnExit.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnExit.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExitActionPerformed(evt);
            }
        });
        mageToolbar.add(btnExit);

        lblStatus.setText("Not connected ");
        mageToolbar.add(Box.createHorizontalGlue());
        mageToolbar.add(lblStatus);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(desktopPane, javax.swing.GroupLayout.DEFAULT_SIZE, 1144, Short.MAX_VALUE)
            .addComponent(mageToolbar, javax.swing.GroupLayout.DEFAULT_SIZE, 1144, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(mageToolbar, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(desktopPane, javax.swing.GroupLayout.DEFAULT_SIZE, 880, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

	private void btnDeckEditorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeckEditorActionPerformed
		this.gamePane.setVisible(false);
		this.tablesPane.setVisible(false);
		this.deckEditorPane.setVisible(true);
		this.deckEditorPane.showTables();
	}//GEN-LAST:event_btnDeckEditorActionPerformed

	private void btnPreferencesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeckEditorActionPerformed
        PhasesDialog.main(new String[]{});
	}
	
	private void btnGamesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGamesActionPerformed
		this.gamePane.setVisible(false);
		this.deckEditorPane.setVisible(false);
		this.tablesPane.setVisible(true);
		this.tablesPane.showTables();
	}//GEN-LAST:event_btnGamesActionPerformed

	private void btnExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExitActionPerformed
		exitApp();
	}//GEN-LAST:event_btnExitActionPerformed

	private void btnConnectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConnectActionPerformed
		if (session.isConnected()) {
			if (JOptionPane.showConfirmDialog(this, "Are you sure you want to disconnect?", "Confirm disconnect", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
				session.disconnect();
			}
		}
		else {
			connectDialog.showDialog();
		}
	}//GEN-LAST:event_btnConnectActionPerformed

	private void btnAboutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAboutActionPerformed
		AboutDialog aboutDialog = new AboutDialog();
		desktopPane.add(aboutDialog);
		aboutDialog.showDialog();
	}//GEN-LAST:event_btnAboutActionPerformed

	public void exitApp() {
		session.disconnect();
		Plugins.getInstance().shutdown();
		dispose();
		System.exit(0);
	}

	public void enableButtons() {
		btnConnect.setEnabled(true);
		btnConnect.setText("Disconnect");
		btnGames.setEnabled(true);
		btnDeckEditor.setEnabled(true);
	}

	public void disableButtons() {
		btnConnect.setEnabled(true);
		btnConnect.setText("Connect");
		btnGames.setEnabled(false);
		btnDeckEditor.setEnabled(true);
		this.tablesPane.setVisible(false);
		this.gamePane.setVisible(false);
		this.deckEditorPane.setVisible(false);
	}

	public static CombatDialog getCombatDialog() {
		return combat;
	}

	public static PickNumberDialog getPickNumberDialog() {
		return pickNumber;
	}

	static void renderSplashFrame(Graphics2D g) {
        g.setComposite(AlphaComposite.Clear);
        g.fillRect(120,140,200,40);
        g.setPaintMode();
        g.setColor(Color.white);
        g.drawString("Version 0.5", 560, 460);
    }
	
    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
    	final SplashScreen splash = SplashScreen.getSplashScreen();
    	if (splash != null) {
    		Graphics2D g = splash.createGraphics();
    		if (g != null) {
    			renderSplashFrame(g);
    		}
	    	splash.update();
    	}
    	
		Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
			public void uncaughtException(Thread t, Throwable e) {
				logger.log(Level.SEVERE, null, e);
			}
		});
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new MageFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAbout;
    private javax.swing.JButton btnConnect;
    private javax.swing.JButton btnDeckEditor;
    private javax.swing.JButton btnPreferences;
    private javax.swing.JButton btnExit;
    private javax.swing.JButton btnGames;
    private mage.client.deckeditor.DeckEditorPane deckEditorPane;
    private static javax.swing.JDesktopPane desktopPane;
    private mage.client.game.GamePane gamePane;
    private javax.swing.JToolBar.Separator jSeparator1;
    private javax.swing.JToolBar.Separator jSeparator2;
    private javax.swing.JToolBar.Separator jSeparator3;
    private javax.swing.JToolBar.Separator jSeparator4;
    private javax.swing.JToolBar.Separator jSeparator5;
    private javax.swing.JLabel lblStatus;
    private javax.swing.JToolBar mageToolbar;
    private mage.client.table.TablesPane tablesPane;
    // End of variables declaration//GEN-END:variables
    
	private static final long serialVersionUID = -9104885239063142218L;
    private ImagePanel backgroundPane;

	public void setStatusText(String status) {
		this.lblStatus.setText(status);
	}
}
