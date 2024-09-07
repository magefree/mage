package mage.client.dialog;

import com.google.gson.Gson;
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
import mage.client.remote.XmageURLConnection;
import mage.client.util.AppUtil;
import mage.client.util.GUISizeHelper;
import org.apache.log4j.Logger;
import org.w3c.dom.events.EventListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.lang.reflect.Type;
import java.net.*;
import java.util.List;
import java.util.stream.Collectors;

/**
 * App GUI: what's new dialog with latest news.
 * Uses system browser. Shows on app's start after page ready.
 *
 * @author JayDi85
 */
public class WhatsNewDialog extends MageDialog {

    private static final Logger LOGGER = Logger.getLogger(WhatsNewDialog.class);

    // cookies store tester: https://setcookie.net/
    public static final String WHATS_NEW_PAGE = "https://jaydi85.github.io/xmage-web-news/news.html";
    private static final String WHATS_NEW_VERSION_PAGE = "https://jaydi85.github.io/xmage-web-news/news_version.html"; // increment version=123 to auto-shown for all users
    private static final int WHATS_NEW_MAX_LOAD_TIMEOUT_SECS = 20; // timeout for page loading (example: no network)
    private static final boolean WHATS_NEW_DEBUG_ENABLE_CONTROLS = false; // default: false, enable it for debug/test

    private final JFXPanel fxPanel;
    private WebView webView;
    private WebEngine engine;
    private boolean isPageReady = false;

    private SwingWorker<Void, Void> lastWaitingWorker = null;

    public WhatsNewDialog() {
        initComponents();
        this.setDefaultCloseOperation(HIDE_ON_CLOSE);

        fxPanel = new JFXPanel();
        panelData.add(fxPanel);
        webView = null;
        engine = null;

        createWebView();
    }

    private void showDialog() {
        this.setModal(true);
        this.setResizable(true);

        getRootPane().setDefaultButton(buttonCancel);
        this.setSize(GUISizeHelper.dialogGuiScaleSize(new Dimension(800, 600)));

        // windows settings
        MageFrame.getDesktop().remove(this);
        MageFrame.getDesktop().add(this, this.isModal() ? JLayeredPane.MODAL_LAYER : JLayeredPane.PALETTE_LAYER);
        this.makeWindowCentered();

        // Close on "ESC"
        registerKeyboardAction(e -> onCancel(), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        this.setVisible(true);
    }

    private final SwingWorker<Void, Void> checkUpdatesWorker = new SwingWorker<Void, Void>() {
        private String newVersion = "";

        @Override
        public Void doInBackground() {
            // download version
            String newsVersion = XmageURLConnection.downloadText(WHATS_NEW_VERSION_PAGE);
            if (newsVersion.startsWith("version=")) {
                newVersion = newsVersion.substring("version=".length());
            }
            return null;
        }

        @Override
        public void done() {
            SwingUtilities.invokeLater(() -> {
                String oldVersion = PreferencesDialog.getCachedValue(PreferencesDialog.KEY_NEWS_PAGE_LAST_VERSION, "1");

                boolean isHaveUpdates = newVersion.isEmpty() || !newVersion.equals(oldVersion);
                if (isHaveUpdates) {
                    PreferencesDialog.saveValue(PreferencesDialog.KEY_NEWS_PAGE_LAST_VERSION, newVersion);
                    startBrowser(WHATS_NEW_PAGE);
                    startWaitingWorker();
                }
            });
        }
    };

    private void startWaitingWorker() {
        // wait page ready and open it on complete
        if (this.lastWaitingWorker != null) {
            this.lastWaitingWorker.cancel(true);
        }

        this.lastWaitingWorker = new SwingWorker<Void, Void>() {
            @Override
            public Void doInBackground() {
                // waiting page loading
                int waitedSecs = 0;
                while (!isPageReady && waitedSecs <= WHATS_NEW_MAX_LOAD_TIMEOUT_SECS) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        return null;
                    }

                    waitedSecs++;
                }
                return null;
            }

            @Override
            public void done() {
                if (isPageReady) {
                    SwingUtilities.invokeLater(() -> {
                        showDialog();
                    });
                }
            }
        };
        this.lastWaitingWorker.execute();
    }

    public void checkUpdatesAndShow(boolean forceToShowPage) {
        // lazy loading in background
        // shows it on page ready or by force

        if (!forceToShowPage) {
            // checks version -> start loading -> show on isPageReady
            checkUpdatesWorker.execute();
            return;
        }

        // direct open
        if (isPageReady) {
            SwingUtilities.invokeLater(() -> {
                showDialog();
            });
        } else {
            checkUpdatesWorker.cancel(true);
            startBrowser(WHATS_NEW_PAGE);
            startWaitingWorker();
        }
    }


    /**
     * Store cookies in preferences
     */
    private static class PersistentCookieStore implements CookieStore, Runnable {
        private final CookieStore store;

        public PersistentCookieStore() {
            // improved store with save/load feature
            store = new CookieManager().getCookieStore();

            // load on startup
            loadFromPrefs();

            // save on app close
            Runtime.getRuntime().addShutdownHook(new Thread(this));
        }

        private void saveToPrefs() {
            // convert cookie to version 1, so it will get full data before save
            // example: xxx
            List<String> v1Cookies = store.getCookies().stream()
                    .peek(c -> c.setVersion(1))
                    .map(HttpCookie::toString)
                    .collect(Collectors.toList());
            PreferencesDialog.saveValue(PreferencesDialog.KEY_NEWS_PAGE_COOKIES, new Gson().toJson(v1Cookies));
        }

        private void loadFromPrefs() {
            Type type = new TypeToken<List<String>>() {
            }.getType();
            try {
                String savedData = PreferencesDialog.getCachedValue(PreferencesDialog.KEY_NEWS_PAGE_COOKIES, "");
                List<String> savedCookies = new Gson().fromJson(savedData, type);
                if (savedCookies != null) {
                    savedCookies.forEach(savedCookie -> {
                        // load as version 1, but convert back to version 0 for compatibility
                        List<HttpCookie> v1Cookies = HttpCookie.parse("set-cookie2:" + savedCookie.replace("$", ""));
                        v1Cookies.forEach(realCookie -> {
                            realCookie.setVersion(0);
                            store.add(URI.create(realCookie.getDomain()), realCookie);
                        });
                    });
                }
            } catch (Exception e) {
                LOGGER.error("News page: catch broken cookies", e);
            }
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

        // workaround for empty dialog on 2+ opens - keep jfx thread alive (by default it exits on parent window close)
        // see https://stackoverflow.com/a/32104851
        Platform.setImplicitExit(false);

        Platform.runLater(() -> {
            webView = new WebView();
            engine = webView.getEngine();
            engine.setJavaScriptEnabled(true);
            engine.setUserAgent(engine.getUserAgent() + " " + XmageURLConnection.getDefaultUserAgent()); // keep system user-agent too
            if (!WHATS_NEW_DEBUG_ENABLE_CONTROLS) {
                webView.contextMenuEnabledProperty().setValue(false);
            }

            CookieManager cookieManager = new CookieManager(new PersistentCookieStore(), CookiePolicy.ACCEPT_ALL);
            CookieHandler.setDefault(cookieManager);

            // on error
            engine.getLoadWorker().exceptionProperty().addListener(new ChangeListener<Throwable>() {
                @Override
                public void changed(ObservableValue<? extends Throwable> o, Throwable old, final Throwable value) {
                    if (engine.getLoadWorker().getState() == Worker.State.FAILED
                            || engine.getLoadWorker().getState() == Worker.State.CANCELLED) {
                        LOGGER.error("News page: can't load page", value);
                    }
                }
            });

            // on completed
            engine.getLoadWorker().stateProperty().addListener(new ChangeListener<Worker.State>() {
                @Override
                public void changed(ObservableValue ov, Worker.State oldState, Worker.State newState) {
                    if (newState != Worker.State.SUCCEEDED) {
                        return;
                    }

                    if (!WHATS_NEW_DEBUG_ENABLE_CONTROLS) {
                        // 1. open all page links in real browser, not build-in window
                        EventListener listener = new EventListener() {
                            @Override
                            public void handleEvent(org.w3c.dom.events.Event ev) {
                                String href = ((org.w3c.dom.Element) ev.getTarget()).getAttribute("href");
                                ev.preventDefault();

                                // open browser (must check href on null anyway)
                                if (href != null && href.startsWith("http")) {
                                    SwingUtilities.invokeLater(() -> AppUtil.openUrlInSystemBrowser(href));
                                }
                            }
                        };
                        org.w3c.dom.Document doc = engine.getDocument();
                        org.w3c.dom.NodeList listA = doc.getElementsByTagName("a");
                        for (int i = 0; i < listA.getLength(); i++) {
                            ((org.w3c.dom.events.EventTarget) listA.item(i)).addEventListener("click", listener, false);
                        }
                    }

                    // 2. all done, build-in browser ready to show
                    isPageReady = true;
                }
            });

            fxPanel.setScene(new Scene(webView));
        });
    }

    public void startBrowser(final String startingUrl) {
        Platform.runLater(() -> {
            String link = startingUrl;
            if (!link.startsWith("http")) {
                link = "http://" + link;
            }
            engine.load(link);
        });
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

        buttonCancel = new JButton();
        buttonRefresh = new JButton();
        panelData = new JPanel();

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

        panelData.setLayout(new BorderLayout());

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(panelData, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(buttonRefresh, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(buttonCancel, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE)))
                                .addContainerGap())
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(panelData, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(buttonCancel, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(buttonRefresh, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))
                                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void buttonCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonCancelActionPerformed
        onCancel();
    }//GEN-LAST:event_buttonCancelActionPerformed

    private void buttonRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonRefreshActionPerformed
        startBrowser(WHATS_NEW_PAGE);
    }//GEN-LAST:event_buttonRefreshActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private JButton buttonCancel;
    private JButton buttonRefresh;
    private JPanel panelData;
    // End of variables declaration//GEN-END:variables
}
