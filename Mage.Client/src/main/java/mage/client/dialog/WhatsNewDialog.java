package mage.client.dialog;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import mage.client.MageFrame;
import mage.utils.MageVersion;
import org.apache.log4j.Logger;
import org.w3c.dom.events.EventListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.net.*;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

/**
 * App GUI: show latest xmage news from the web page
 *
 * @author JayDi85
 */
public class WhatsNewDialog extends MageDialog {

    private static final Logger LOGGER = Logger.getLogger(WhatsNewDialog.class);
    private static final MageVersion CLIENT_VERSION = new MageVersion(WhatsNewDialog.class);

    // cookies tester: http://www.html-kit.com/tools/cookietester/
    private static final String WHATS_NEW_PAGE = "https://jaydi85.github.io/xmage-web-news/news.html";
    private static final String WHATS_NEW_VERSION_PAGE = "https://jaydi85.github.io/xmage-web-news/news_version.html"; // increment version=123 to auto-shown for all users
    private static final int WHATS_NEW_LOAD_TIMEOUT_SECS = 20; // timeout for page loading

    private final JFXPanel fxPanel;
    private WebView webView;
    private WebEngine engine;
    private boolean isPageReady = false;

    private final SwingWorker<Void, Void> backgroundWorker = new SwingWorker<Void, Void>() {
        @Override
        public Void doInBackground() {
            try {
                int maxWait = WHATS_NEW_LOAD_TIMEOUT_SECS;
                int currentWait = 0;
                while (!isPageReady) {
                    TimeUnit.SECONDS.sleep(1);
                    currentWait++;
                    if (currentWait > maxWait) {
                        LOGGER.error("Can't load news page: " + WHATS_NEW_PAGE);
                        break;
                    }
                }
            } catch (InterruptedException e) {
                LOGGER.error("Checking news was interrupted", e);
                Thread.currentThread().interrupt();
            }
            return null;
        }

        @Override
        public void done() {
            SwingUtilities.invokeLater(() -> {
                if (isPageReady) {
                    showDialog();
                }
            });
        }
    };

    private final SwingWorker<Void, Void> backgroundUpdatesWorker = new SwingWorker<Void, Void>() {

        private String newVersion = "";

        @Override
        public Void doInBackground() {
            try {
                // download version
                URLConnection connection = new URL(WHATS_NEW_VERSION_PAGE).openConnection();
                connection.setRequestProperty("user-agent", "xmage");
                InputStream download = connection.getInputStream();
                Scanner s = new Scanner(download).useDelimiter("\\A");
                String result = s.hasNext() ? s.next() : "";

                // check version (default: always have new version)
                if (result.startsWith("version=")) {
                    newVersion = result.substring("version=".length());
                }
            } catch (MalformedURLException e) {
                LOGGER.error("Checking updates got wrong url " + WHATS_NEW_VERSION_PAGE, e);
            } catch (IOException e) {
                LOGGER.error("Checking updates got error", e);
            }
            return null;
        }

        @Override
        public void done() {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    String oldVersion = PreferencesDialog.getCachedValue(PreferencesDialog.KEY_NEWS_PAGE_LAST_VERSION, "1");

                    boolean isHaveUpdates = newVersion.isEmpty() || !newVersion.equals(oldVersion);
                    if (isHaveUpdates) {
                        PreferencesDialog.saveValue(PreferencesDialog.KEY_NEWS_PAGE_LAST_VERSION, newVersion);
                        loadURL(WHATS_NEW_PAGE);
                        backgroundWorker.execute();
                    }
                }
            });
        }
    };

    public WhatsNewDialog() {
        initComponents();
        this.setDefaultCloseOperation(HIDE_ON_CLOSE);

        fxPanel = new JFXPanel();
        panelData.add(fxPanel);
        webView = null;
        engine = null;

        createWebView();
    }

    public void checkUpdatesAndShow(boolean forceToShowPage) {
        // lazy loading in background
        // shows it on page ready or by force

        isPageReady = false;
        if (forceToShowPage) {
            // direct open
            loadURL(WHATS_NEW_PAGE);
            if (!backgroundUpdatesWorker.isDone()) backgroundUpdatesWorker.cancel(true);
            if (!backgroundWorker.isDone()) backgroundWorker.cancel(true);

            showDialog();
        } else {
            // checks version -> loads on new
            backgroundUpdatesWorker.execute();
        }
    }

    private void showDialog() {
        this.setModal(true);
        this.setResizable(true);
        getRootPane().setDefaultButton(buttonCancel);
        this.setSize(800, 600);

        // windows settings
        MageFrame.getDesktop().remove(this);
        if (this.isModal()) {
            MageFrame.getDesktop().add(this, JLayeredPane.MODAL_LAYER);
        } else {
            MageFrame.getDesktop().add(this, JLayeredPane.PALETTE_LAYER);
        }
        this.makeWindowCentered();

        // Close on "ESC"
        registerKeyboardAction(e -> onCancel(), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        this.setVisible(true);
    }


    /**
     * Store cookies in preferences
     */
    private class PersistentCookieStore implements CookieStore, Runnable {
        private final CookieStore store;

        public PersistentCookieStore() {
            store = new CookieManager().getCookieStore();

            // restore data
            loadFromPrefs();

            // save data on app close
            Runtime.getRuntime().addShutdownHook(new Thread(this));
        }

        private void loadFromPrefs() {
            String sourceValue = PreferencesDialog.getCachedValue(PreferencesDialog.KEY_NEWS_PAGE_COOKIES, "");

            Gson gson = new GsonBuilder().create();
            List<HttpCookie> httpCookies;
            Type type = new TypeToken<List<HttpCookie>>() {
            }.getType();
            try {
                httpCookies = gson.fromJson(sourceValue, type);
                if (httpCookies != null) {
                    for (HttpCookie cookie : httpCookies) {
                        store.add(URI.create(cookie.getDomain()), cookie);
                    }
                }
            } catch (Exception e) {
                LOGGER.error("Wrong news page cookies", e);
            }
        }

        private void saveToPrefs() {
            List<HttpCookie> httpCookies = store.getCookies();
            Gson gson = new GsonBuilder().create();
            String destValue = gson.toJson(httpCookies);
            PreferencesDialog.saveValue(PreferencesDialog.KEY_NEWS_PAGE_COOKIES, destValue);
        }

        @Override
        public void run() {
            saveToPrefs();
        }

        @Override
        public void add(URI uri, HttpCookie cookie) {
            store.add(uri, cookie);
        }

        @Override
        public List<HttpCookie> get(URI uri) {
            return store.get(uri);
        }

        @Override
        public List<HttpCookie> getCookies() {
            return store.getCookies();
        }

        @Override
        public List<URI> getURIs() {
            return store.getURIs();
        }

        @Override
        public boolean remove(URI uri, HttpCookie cookie) {
            return store.remove(uri, cookie);
        }

        @Override
        public boolean removeAll() {
            return store.removeAll();
        }
    }

    private void createWebView() {

        // init web engine and events
        // https://docs.oracle.com/javafx/2/swing/swing-fx-interoperability.htm

        Platform.runLater(new Runnable() {
            @Override
            public void run() {

                webView = new WebView();
                engine = webView.getEngine();
                engine.setUserAgent(engine.getUserAgent() + " XMage/" + CLIENT_VERSION.toString(false));
                webView.contextMenuEnabledProperty().setValue(false);

                CookieManager cookieManager = new CookieManager(new PersistentCookieStore(), CookiePolicy.ACCEPT_ALL);
                CookieHandler.setDefault(cookieManager);

                // on error
                engine.getLoadWorker().exceptionProperty().addListener(new ChangeListener<Throwable>() {
                    @Override
                    public void changed(ObservableValue<? extends Throwable> o, Throwable old, final Throwable value) {
                        if (engine.getLoadWorker().getState() == Worker.State.FAILED) {
                            SwingUtilities.invokeLater(new Runnable() {
                                @Override
                                public void run() {
                                    LOGGER.error("Can't load news page: " + (value != null ? value.getMessage() : "null"), value);
                                }
                            });
                        }
                    }
                });

                // on completed
                engine.getLoadWorker().stateProperty().addListener(new ChangeListener<Worker.State>() {
                    @Override
                    public void changed(ObservableValue ov, Worker.State oldState, Worker.State newState) {
                        if (newState == Worker.State.SUCCEEDED) {

                            // 1. replace urls with custom click processing
                            // all classes from org.w3c.dom
                            org.w3c.dom.events.EventListener listener = new EventListener() {
                                @Override
                                public void handleEvent(org.w3c.dom.events.Event ev) {
                                    String href = ((org.w3c.dom.Element) ev.getTarget()).getAttribute("href");
                                    ev.preventDefault();

                                    SwingUtilities.invokeLater(new Runnable() {
                                        @Override
                                        public void run() {
                                            if (Desktop.isDesktopSupported()) {
                                                Desktop desktop = Desktop.getDesktop();
                                                try {
                                                    URI uri = new URI(href);
                                                    desktop.browse(uri);
                                                } catch (IOException | URISyntaxException ex) {
                                                    // do nothing
                                                }
                                            }
                                        }
                                    });
                                }
                            };
                            org.w3c.dom.Document doc = engine.getDocument();
                            org.w3c.dom.NodeList listA = doc.getElementsByTagName("a");
                            for (int i = 0; i < listA.getLength(); i++) {
                                ((org.w3c.dom.events.EventTarget) listA.item(i)).addEventListener("click", listener, false);
                            }

                            // 2. page can be shown
                            SwingUtilities.invokeLater(new Runnable() {
                                @Override
                                public void run() {
                                    isPageReady = true;
                                }
                            });
                        }
                    }
                });

                fxPanel.setScene(new Scene(webView));
            }
        });
    }

    public void loadURL(final String url) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                String tmp = toURL(url);
                if (url == null) {
                    tmp = toURL("http://" + url);
                }
                engine.load(tmp);
            }
        });
    }

    private static String toURL(String str) {
        try {
            return new URL(str).toExternalForm();
        } catch (MalformedURLException exception) {
            return null;
        }
    }


    private void onCancel() {
        this.hideDialog();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonCancel = new javax.swing.JButton();
        buttonRefresh = new javax.swing.JButton();
        panelData = new javax.swing.JPanel();

        buttonCancel.setText("Close");
        buttonCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonCancelActionPerformed(evt);
            }
        });

        buttonRefresh.setText("Refresh");
        buttonRefresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonRefreshActionPerformed(evt);
            }
        });

        panelData.setLayout(new java.awt.BorderLayout());

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(panelData, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(buttonRefresh, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(buttonCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addContainerGap())
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(panelData, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(buttonCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(buttonRefresh, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void buttonCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonCancelActionPerformed
        onCancel();
    }//GEN-LAST:event_buttonCancelActionPerformed

    private void buttonRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonRefreshActionPerformed
        loadURL(WHATS_NEW_PAGE);
    }//GEN-LAST:event_buttonRefreshActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton buttonCancel;
    private javax.swing.JButton buttonRefresh;
    private javax.swing.JPanel panelData;
    // End of variables declaration//GEN-END:variables
}
