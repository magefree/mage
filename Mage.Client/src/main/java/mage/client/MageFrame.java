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

import mage.Constants;
import mage.cards.Card;
import mage.cards.decks.Deck;
import mage.client.cards.CardsStorage;
import mage.client.components.MageComponents;
import mage.client.components.MageJDesktop;
import mage.client.components.MageRoundPane;
import mage.client.components.arcane.ManaSymbols;
import mage.client.constants.Constants.DeckEditorMode;
import mage.client.deckeditor.collection.viewer.CollectionViewerPane;
import mage.client.dialog.*;
import mage.client.plugins.impl.Plugins;
import mage.client.util.EDTExceptionHandler;
import mage.client.util.gui.ArrowBuilder;
import mage.components.ImagePanel;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.JToolBar.Separator;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.beans.PropertyVetoException;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.List;
import java.util.prefs.Preferences;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import mage.client.chat.ChatPanel;
import mage.client.components.MageUI;
import mage.client.deckeditor.DeckEditorPane;
import mage.client.draft.DraftPane;
import mage.client.draft.DraftPanel;
import mage.client.game.GamePane;
import mage.client.game.GamePanel;
import mage.client.remote.CallbackClientImpl;
import mage.client.table.TablesPane;
import mage.client.tournament.TournamentPane;
import mage.client.tournament.TournamentPanel;
import mage.game.match.MatchOptions;
import mage.interfaces.MageClient;
import mage.interfaces.callback.CallbackClient;
import mage.interfaces.callback.ClientCallback;
import mage.remote.Connection;
import mage.remote.Connection.ProxyType;
import mage.remote.Session;
import mage.utils.MageVersion;
import mage.sets.Sets;
import mage.view.TableView;
import org.apache.log4j.Logger;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class MageFrame extends javax.swing.JFrame implements MageClient {

    private final static Logger logger = Logger.getLogger(MageFrame.class);
	private final static String liteModeArg = "-lite";

    private static Session session;
    private ConnectDialog connectDialog;
	private static CallbackClient callbackClient;
    private static Preferences prefs = Preferences.userNodeForPackage(MageFrame.class);
    private JLabel title;
    private Rectangle titleRectangle;
	private final static MageVersion version = new MageVersion(0, 7, 4, "beta-2");
	private UUID clientId;
	private static MagePane activeFrame;
	private static boolean liteMode = false;

	private static Map<UUID, ChatPanel> chats = new HashMap<UUID, ChatPanel>();
	private static Map<UUID, GamePanel> games = new HashMap<UUID, GamePanel>();
	private static Map<UUID, DraftPanel> drafts = new HashMap<UUID, DraftPanel>();
	private static Map<UUID, TournamentPanel> tournaments = new HashMap<UUID, TournamentPanel>();
	private static MageUI ui = new MageUI();

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
	
	public static boolean isLite() {
		return liteMode;
	}

	@Override
	public MageVersion getVersion() {
		return version;
	}

    /**
     * Creates new form MageFrame
     */
    public MageFrame() {

        setTitle("Mage, version " + version);
		clientId = UUID.randomUUID();
		
        EDTExceptionHandler.registerExceptionHandler();
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                exitApp();
            }
        });

        try {
            UIManager.put("desktop", new Color(0, 0, 0, 0));
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
            //MageSynthStyleFactory f = new MageSynthStyleFactory(SynthLookAndFeel.getStyleFactory());
            //SynthLookAndFeel.setStyleFactory(f);
        } catch (Exception ex) {
            logger.fatal(null, ex);
        }

		ManaSymbols.loadImages();
        Plugins.getInstance().loadPlugins();

        initComponents();
        setSize(1024, 768);
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);

        session = new Session(this);
		callbackClient = new CallbackClientImpl(this);
        connectDialog = new ConnectDialog();
        desktopPane.add(connectDialog, JLayeredPane.POPUP_LAYER);
        ui.addComponent(MageComponents.DESKTOP_PANE, desktopPane);

		try {
			tablesPane = new TablesPane();
			desktopPane.add(tablesPane, javax.swing.JLayeredPane.DEFAULT_LAYER);
			tablesPane.setMaximum(true);

			collectionViewerPane = new CollectionViewerPane();
			desktopPane.add(collectionViewerPane, javax.swing.JLayeredPane.DEFAULT_LAYER);
			collectionViewerPane.setMaximum(true);

		} catch (PropertyVetoException ex) {
			logger.fatal(null, ex);
		}

		addTooltipContainer();
        setBackground();
        addMageLabel();
        setAppIcon();

		//PlayerPanelNew n = new PlayerPanelNew();
		//n.setBounds(100,100,100,300);
		//n.setVisible(true);
		//backgroundPane.add(n);

        desktopPane.add(ArrowBuilder.getArrowsPanel(), JLayeredPane.DRAG_LAYER);

        desktopPane.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                int width = ((JComponent) e.getSource()).getWidth();
                int height = ((JComponent) e.getSource()).getHeight();
				if (!liteMode)
					backgroundPane.setSize(width, height);
                JPanel arrowsPanel = ArrowBuilder.getArrowsPanelRef();
                if (arrowsPanel != null) arrowsPanel.setSize(width, height);
                if (title != null) {
                    //title.setBorder(BorderFactory.createLineBorder(Color.red));
                    title.setBounds((int) (width - titleRectangle.getWidth()) / 2, (int) (height - titleRectangle.getHeight()) / 2, titleRectangle.width, titleRectangle.height);
                }
            }
        });

		mageToolbar.add(new javax.swing.JToolBar.Separator());
		mageToolbar.add(createWindowsButton());
		
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

        ui.addButton(MageComponents.TABLES_MENU_BUTTON, btnGames);

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
	            disableButtons();
                if (autoConnect())
                    enableButtons();
                else {
					connectDialog.showDialog();
				}
            }
        });
    }

    private void addTooltipContainer() {
        final JEditorPane cardInfoPane = (JEditorPane) Plugins.getInstance().getCardInfoPane();
		if (cardInfoPane == null) {
			return;
		}
        cardInfoPane.setSize(320, 201);
        cardInfoPane.setLocation(40, 40);
        cardInfoPane.setBackground(new Color(0, 0, 0, 0));

        MageRoundPane popupContainer = new MageRoundPane();
        popupContainer.setLayout(null);

        popupContainer.add(cardInfoPane);
        popupContainer.setVisible(false);
        popupContainer.setBounds(0, 0, 320 + 80, 201 + 80);

        desktopPane.add(popupContainer, JLayeredPane.POPUP_LAYER);

        ui.addComponent(MageComponents.CARD_INFO_PANE, cardInfoPane);
        ui.addComponent(MageComponents.POPUP_CONTAINER, popupContainer);
    }

    private void setBackground() {
		if (liteMode)
			return;
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
    }

    private void addMageLabel() {
		if (liteMode)
			return;
        String filename = "/label-mage.png";
        try {
            InputStream is = this.getClass().getResourceAsStream(filename);

            float ratio = 1179.0f / 678.0f;
            titleRectangle = new Rectangle(640, (int) (640 / ratio));
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
    }

    private void setAppIcon() {
        String filename = "/icon-mage.png";
        try {
            InputStream is = this.getClass().getResourceAsStream(filename);

            if (is != null) {
                BufferedImage image = ImageIO.read(is);
                setIconImage(image);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

	private AbstractButton createWindowsButton() {
		final JToggleButton windowButton = new JToggleButton("Windows");
		windowButton.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					createAndShowMenu((JComponent) e.getSource(), windowButton);
				}
			}
		});
		windowButton.setFocusable(false);
		windowButton.setHorizontalTextPosition(SwingConstants.LEADING);
		return windowButton;
	}

	private void createAndShowMenu(final JComponent component, final AbstractButton windowButton) {
		JPopupMenu menu = new JPopupMenu();
		JInternalFrame[] windows = desktopPane.getAllFramesInLayer(javax.swing.JLayeredPane.DEFAULT_LAYER);
		MagePaneMenuItem menuItem;

		for (int i = 0; i < windows.length; i++) {
			MagePane window = (MagePane) windows[i];
			if (window.isVisible()) {
				menuItem = new MagePaneMenuItem(window);
				menuItem.setState(i == 0);
				menuItem.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent ae) {
						MagePane frame = ((MagePaneMenuItem) ae.getSource()).getFrame();
						setActive(frame);
					}
				});
				menuItem.setIcon(window.getFrameIcon());
				menu.add(menuItem);
			}
		}

		menu.addPopupMenuListener(new PopupMenuListener() {
			public void popupMenuWillBecomeVisible(PopupMenuEvent e) { }

			public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
				windowButton.setSelected(false);
			}

			public void popupMenuCanceled(PopupMenuEvent e) {
				windowButton.setSelected(false);
			}
		});

		menu.show(component, 0, component.getHeight());
	}

    private void btnImagesActionPerformed(java.awt.event.ActionEvent evt) {
        HashSet<Card> cards = new HashSet<Card>(CardsStorage.getAllCards());
        List<Card> notImplemented = CardsStorage.getNotImplementedCards();
        cards.addAll(notImplemented);
        Plugins.getInstance().downloadImage(cards);
    }

    private void btnSymbolsActionPerformed(java.awt.event.ActionEvent evt) {
        if (JOptionPane.showConfirmDialog(null, "Do you want to download mana symbols?") == JOptionPane.OK_OPTION) {
            Plugins.getInstance().downloadSymbols();
        }
    }

	public static void setActive(MagePane frame) {
		if (frame == null)
			return;
		logger.debug("Setting " + frame.getTitle() + " active");
		if (activeFrame != null)
			activeFrame.deactivated();
		activeFrame = frame;
		activeFrame.toFront();
		try {
			activeFrame.setSelected(true);
		} catch (PropertyVetoException ex) {}
		activeFrame.activated();
	}
	
	public static void deactivate(MagePane frame) {
		frame.setVisible(false);
		MagePane topmost = getTopMost(frame);
		if (activeFrame != frame)
			frame.deactivated();
		setActive(topmost);
	}
	
	private static MagePane getTopMost(MagePane exclude) {
		MagePane topmost = null;
		int best = Integer.MAX_VALUE;
		for (JInternalFrame frame: desktopPane.getAllFramesInLayer(JLayeredPane.DEFAULT_LAYER)) {
			if (frame.isVisible()) {
				int z = desktopPane.getComponentZOrder(frame);
				if (z < best) {
					best = z;
					topmost = (MagePane) frame;
				}
			}
		}
		return topmost;
	}
	
    public void showGame(UUID gameId, UUID playerId) {
		try {
			GamePane gamePane = new GamePane();
			desktopPane.add(gamePane, JLayeredPane.DEFAULT_LAYER);
			gamePane.setMaximum(true);
			gamePane.setVisible(true);
			gamePane.showGame(gameId, playerId);
			setActive(gamePane);
		} catch (PropertyVetoException ex) {}
    }

    public void watchGame(UUID gameId) {
		try {
			GamePane gamePane = new GamePane();
			desktopPane.add(gamePane, JLayeredPane.DEFAULT_LAYER);
			gamePane.setMaximum(true);
			gamePane.setVisible(true);
			gamePane.watchGame(gameId);
			setActive(gamePane);
		} catch (PropertyVetoException ex) {}
    }

    public void replayGame(UUID gameId) {
		try {
			GamePane gamePane = new GamePane();
			desktopPane.add(gamePane, JLayeredPane.DEFAULT_LAYER);
			gamePane.setMaximum(true);
			gamePane.setVisible(true);
			gamePane.replayGame(gameId);
			setActive(gamePane);
		} catch (PropertyVetoException ex) {}
    }

	public void showDraft(UUID draftId) {
		try {
			DraftPane draftPane = new DraftPane();
			desktopPane.add(draftPane, JLayeredPane.DEFAULT_LAYER);
			draftPane.setMaximum(true);
			draftPane.setVisible(true);
			draftPane.showDraft(draftId);
			setActive(draftPane);
		} catch (PropertyVetoException ex) {}
	}

 	public void showTournament(UUID tournamentId) {
		try {
			TournamentPane tournamentPane = new TournamentPane();
			desktopPane.add(tournamentPane, JLayeredPane.DEFAULT_LAYER);
			tournamentPane.setMaximum(true);
			tournamentPane.setVisible(true);
			tournamentPane.showTournament(tournamentId);
			setActive(tournamentPane);
		} catch (PropertyVetoException ex) {}
	}

	public static boolean connect(Connection connection) {
        return session.connect(connection);
    }

    public boolean autoConnect() {
        boolean autoConnect = Boolean.parseBoolean(prefs.get("autoConnect", "false"));
        if (autoConnect) {
            String userName = prefs.get("userName", "");
            String server = prefs.get("serverAddress", "");
            int port = Integer.parseInt(prefs.get("serverPort", ""));
            String proxyServer = prefs.get("proxyAddress", "");
            int proxyPort = Integer.parseInt(prefs.get("proxyPort", ""));
			ProxyType proxyType = Connection.ProxyType.valueByText(prefs.get("proxyType", "None"));
	        String proxyUsername = prefs.get("proxyUsername", "");
	        String proxyPassword = prefs.get("proxyPassword", "");

            try {
                setCursor(new Cursor(Cursor.WAIT_CURSOR));
				Connection connection = new Connection();
				connection.setUsername(userName);
				connection.setHost(server);
				connection.setPort(port);
				connection.setProxyType(proxyType);
				connection.setProxyHost(proxyServer);
				connection.setProxyPort(proxyPort);
				connection.setProxyUsername(proxyUsername);
	            connection.setProxyPassword(proxyPassword);
				logger.debug("connecting (auto): " + proxyType + " " + proxyServer + " " + proxyPort + " " + proxyUsername);
                if (MageFrame.connect(connection)) {
                    return true;
                } else {
                    showMessage("Unable to connect to server");
                }
            } finally {
                setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
        }
        return false;
    }

    /**
     * This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        desktopPane = new MageJDesktop();
        mageToolbar = new javax.swing.JToolBar();
        btnConnect = new javax.swing.JButton();
        jSeparator4 = new javax.swing.JToolBar.Separator();
        btnGames = new javax.swing.JButton();
        jSeparator3 = new javax.swing.JToolBar.Separator();
        btnDeckEditor = new javax.swing.JButton();
        jSeparator2 = new javax.swing.JToolBar.Separator();
        btnCollectionViewer = new javax.swing.JButton();
        jSeparator5 = new javax.swing.JToolBar.Separator();
        btnPreferences = new javax.swing.JButton();
        jSeparator6 = new javax.swing.JToolBar.Separator();
        btnAbout = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JToolBar.Separator();
        btnExit = new javax.swing.JButton();
        lblStatus = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(1024, 768));

        desktopPane.setBackground(new java.awt.Color(204, 204, 204));

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

        btnGames.setText("Games");
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

        btnCollectionViewer.setText("Collection Viewer");
        btnCollectionViewer.setFocusable(false);
        btnCollectionViewer.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnCollectionViewer.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnCollectionViewer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCollectionViewerActionPerformed(evt);
            }
        });
        mageToolbar.add(btnCollectionViewer);
        mageToolbar.add(jSeparator5);

        btnPreferences.setText("Preferences");
        btnPreferences.setFocusable(false);
        btnPreferences.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnPreferences.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnPreferences.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPreferencesActionPerformed(evt);
            }
        });
        mageToolbar.add(btnPreferences);
        mageToolbar.add(jSeparator6);

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
		showDeckEditor(DeckEditorMode.Constructed, null, null, 0);
    }//GEN-LAST:event_btnDeckEditorActionPerformed

	private void btnChallengesActionPerformed(java.awt.event.ActionEvent evt) {
				TableView table;
		try {
			MatchOptions options = new MatchOptions("1", "Two Player Duel");
			options.getPlayerTypes().add("Human");
			options.getPlayerTypes().add("Computer - default");
			options.setDeckType("Limited");
			options.setAttackOption(Constants.MultiplayerAttackOption.LEFT);
			options.setRange(Constants.RangeOfInfluence.ALL);
			options.setWinsNeeded(1);

			//TODO: maybe we should have separate room id for quests (so they won't be visible in main tables list)
			UUID roomId = MageFrame.getSession().getMainRoomId();

			table = session.createTable(roomId,	options);
			session.joinTable(roomId, table.getTableId(), "Human", "Human", 1, Sets.loadDeck("UW Control.dck"));
			session.joinTable(roomId, table.getTableId(), "Computer", "Computer - default", 1, Sets.loadDeck("UW Control.dck"));

			//hideTables();
			session.startChallenge(roomId, table.getTableId(), UUID.randomUUID());
		} catch (Exception ex) {
			//handleError(ex);
		}
	}

    private void btnGamesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGamesActionPerformed
        this.tablesPane.setVisible(true);
        this.tablesPane.showTables();
		setActive(tablesPane);
    }//GEN-LAST:event_btnGamesActionPerformed

    private void btnExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExitActionPerformed
        exitApp();
    }//GEN-LAST:event_btnExitActionPerformed

    private void btnConnectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConnectActionPerformed
        if (session.isConnected()) {
            if (JOptionPane.showConfirmDialog(this, "Are you sure you want to disconnect?", "Confirm disconnect", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                session.disconnect(false);
				showMessage("You have disconnected");
            }
        } else {
            connectDialog.showDialog();
        }
    }//GEN-LAST:event_btnConnectActionPerformed

    private void btnAboutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAboutActionPerformed
        AboutDialog aboutDialog = new AboutDialog();
        desktopPane.add(aboutDialog, JLayeredPane.POPUP_LAYER);
        aboutDialog.showDialog(version);
    }//GEN-LAST:event_btnAboutActionPerformed

	private void btnCollectionViewerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCollectionViewerActionPerformed
		showCollectionViewer();
	}//GEN-LAST:event_btnCollectionViewerActionPerformed

	private void btnPreferencesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPreferencesActionPerformed
		//PhasesDialog.main(new String[]{});
		PreferencesDialog.main(new String[]{});
	}//GEN-LAST:event_btnPreferencesActionPerformed

    public void exitApp() {
        session.disconnect(false);
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
    }

	public void hideTables() {
		this.tablesPane.hideTables();
	}

	public void hideGames() {
		JInternalFrame[] windows = desktopPane.getAllFramesInLayer(JLayeredPane.DEFAULT_LAYER);
		for (JInternalFrame window: windows) {
			if (window instanceof GamePane) {
				GamePane gamePane = (GamePane) window;
				gamePane.hideGame();
			}
		}
	}

    public void showDeckEditor(DeckEditorMode mode, Deck deck, UUID tableId, int time) {
		try {
			DeckEditorPane deckEditorPane = new DeckEditorPane();
			desktopPane.add(deckEditorPane, JLayeredPane.DEFAULT_LAYER);
			deckEditorPane.setMaximum(true);
			deckEditorPane.setVisible(true);
			deckEditorPane.show(mode, deck, tableId, time);
			setActive(deckEditorPane);
		} catch (PropertyVetoException ex) {
			logger.fatal(null, ex);
		}
    }

    public void showCollectionViewer() {
        this.collectionViewerPane.setVisible(true);
		setActive(collectionViewerPane);
    }

    static void renderSplashFrame(Graphics2D g) {
        g.setComposite(AlphaComposite.Clear);
        g.fillRect(120, 140, 200, 40);
        g.setPaintMode();
        g.setColor(Color.white);
        g.drawString("Version 0.6.1", 560, 460);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(final String args[]) {

        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            public void uncaughtException(Thread t, Throwable e) {
                logger.fatal(null, e);
            }
        });
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
				for (String arg: args) {
					if (arg.startsWith(liteModeArg)) {
						liteMode = true;
					}
				}
				if (!liteMode) {
					final SplashScreen splash = SplashScreen.getSplashScreen();
					if (splash != null) {
						Graphics2D g = splash.createGraphics();
						if (g != null) {
							renderSplashFrame(g);
						}
						splash.update();
					}
				}
                new MageFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAbout;
    private javax.swing.JButton btnCollectionViewer;
    private javax.swing.JButton btnConnect;
    private javax.swing.JButton btnDeckEditor;
    private javax.swing.JButton btnExit;
    private javax.swing.JButton btnGames;
    private javax.swing.JButton btnPreferences;
    private static javax.swing.JDesktopPane desktopPane;
    private javax.swing.JToolBar.Separator jSeparator1;
    private javax.swing.JToolBar.Separator jSeparator2;
    private javax.swing.JToolBar.Separator jSeparator3;
    private javax.swing.JToolBar.Separator jSeparator4;
    private javax.swing.JToolBar.Separator jSeparator5;
    private javax.swing.JToolBar.Separator jSeparator6;
    private javax.swing.JLabel lblStatus;
    private javax.swing.JToolBar mageToolbar;
    // End of variables declaration//GEN-END:variables

    private static final long serialVersionUID = -9104885239063142218L;
    private ImagePanel backgroundPane;
	private TablesPane tablesPane;
	private CollectionViewerPane collectionViewerPane;

    public void setStatusText(String status) {
        this.lblStatus.setText(status);
    }

	public static MageUI getUI() {
		return ui;
	}
	
	public static ChatPanel getChat(UUID chatId) {
		return chats.get(chatId);
	}

	public static void addChat(UUID chatId, ChatPanel chatPanel) {
		chats.put(chatId, chatPanel);
	}

	public static GamePanel getGame(UUID gameId) {
		return games.get(gameId);
	}

	public static void addGame(UUID gameId, GamePanel gamePanel) {
		games.put(gameId, gamePanel);
	}

	public static DraftPanel getDraft(UUID draftId) {
		return drafts.get(draftId);
	}

	public static void addDraft(UUID draftId, DraftPanel draftPanel) {
		drafts.put(draftId, draftPanel);
	}

	public static void addTournament(UUID tournamentId, TournamentPanel tournament) {
		tournaments.put(tournamentId, tournament);
	}
	
	@Override
	public UUID getId() {
		return clientId;
	}

	@Override
	public void connected(final String message) {
		if (SwingUtilities.isEventDispatchThread()) {
			setStatusText(message);
			enableButtons();			
		}
		else {
			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					setStatusText(message);
					enableButtons();
				}
			});
		}
	}

	@Override
	public void disconnected() {
		if (SwingUtilities.isEventDispatchThread()) {
			setStatusText("Not connected");
			disableButtons();
			hideGames();
			hideTables();
		}
		else {
			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					setStatusText("Not connected");
					disableButtons();
					hideGames();
					hideTables();
				}
			});
		}
	}

	@Override
	public void showMessage(final String message) {
		if (SwingUtilities.isEventDispatchThread()) {
			JOptionPane.showMessageDialog(desktopPane, message);
		}
		else {
			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					JOptionPane.showMessageDialog(desktopPane, message);
				}
			});
		}
	}

	@Override
	public void showError(final String message) {
		if (SwingUtilities.isEventDispatchThread()) {
			JOptionPane.showMessageDialog(desktopPane, message, "Error", JOptionPane.ERROR_MESSAGE);
		}
		else {
			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					JOptionPane.showMessageDialog(desktopPane, message, "Error", JOptionPane.ERROR_MESSAGE);
				}
			});
		}
	}
	
	@Override
	public void processCallback(ClientCallback callback) {
		callbackClient.processCallback(callback);
	}

}

class MagePaneMenuItem extends JCheckBoxMenuItem {
	private MagePane frame;

	public MagePaneMenuItem(MagePane frame) {
		super(frame.getTitle());
		this.frame = frame;
	}

	public MagePane getFrame() {
		return frame;
	}
}