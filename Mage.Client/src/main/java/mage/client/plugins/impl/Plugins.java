package mage.client.plugins.impl;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import javax.swing.JComponent;
import mage.cards.MageCard;
import mage.cards.MagePermanent;
import mage.cards.action.ActionCallback;
import mage.client.MageFrame;
import mage.client.cards.BigCard;
import mage.client.cards.Card;
import mage.client.cards.Permanent;
import mage.client.dialog.PreferencesDialog;
import mage.client.plugins.MagePlugins;
import mage.client.plugins.adapters.MageActionCallback;
import mage.client.util.Config;
import mage.interfaces.PluginException;
import mage.interfaces.plugin.CardPlugin;
import mage.interfaces.plugin.CounterPlugin;
import mage.interfaces.plugin.ThemePlugin;
import mage.view.CardView;
import mage.view.PermanentView;
import net.xeoh.plugins.base.PluginManager;
import net.xeoh.plugins.base.impl.PluginManagerFactory;
import org.apache.log4j.Logger;
import org.mage.plugins.card.CardPluginImpl;
import static org.mage.plugins.card.utils.CardImageUtils.getImagesDir;
import org.mage.plugins.theme.ThemePluginImpl;

public enum Plugins implements MagePlugins {
    instance;
    public static final String PLUGINS_DIRECTORY = "plugins";

    private static final Logger LOGGER = Logger.getLogger(Plugins.class);
    private static PluginManager pm;

    private ThemePlugin themePlugin = null;
    private CardPlugin cardPlugin = null;
    private CounterPlugin counterPlugin = null;
    private static final MageActionCallback mageActionCallback = new MageActionCallback();
    private final Map<String, String> sortingOptions = new HashMap<>();

    @Override
    public void loadPlugins() {

        LOGGER.info("Loading plugins...");
        pm = PluginManagerFactory.createPluginManager();
        pm.addPluginsFrom(new File(PLUGINS_DIRECTORY + File.separator).toURI());
        this.cardPlugin = new CardPluginImpl();
        this.counterPlugin = pm.getPlugin(CounterPlugin.class);
        this.themePlugin = new ThemePluginImpl();
        LOGGER.info("Done.");
    }

    @Override
    public void shutdown() {
        if (pm != null) {
            pm.shutdown();
        }
    }

    @Override
    public void changeGUISize() {
        setGUISize();
        if (this.cardPlugin != null) {
            cardPlugin.changeGUISize();
        }
    }

    private void setGUISize() {

    }

    @Override
    public void updateGamePanel(Map<String, JComponent> ui) {
        if (MageFrame.isLite() || MageFrame.isGray() || themePlugin == null) {
            return;
        }
        themePlugin.applyInGame(ui);
    }

    @Override
    public JComponent updateTablePanel(Map<String, JComponent> ui) {
        if (MageFrame.isLite() || MageFrame.isGray() || themePlugin == null) {
            return null;
        }
        return themePlugin.updateTable(ui);
    }

    @Override
    public MagePermanent getMagePermanent(PermanentView card, BigCard bigCard, Dimension dimension, UUID gameId, boolean loadImage) {
        if (cardPlugin != null) {
            mageActionCallback.refreshSession();
            mageActionCallback.setCardPreviewComponent(bigCard);
            return cardPlugin.getMagePermanent(card, dimension, gameId, mageActionCallback, false, !MageFrame.isLite() && loadImage);
        } else {
            return new Permanent(card, bigCard, Config.dimensions, gameId);
        }
    }

    @Override
    public MageCard getMageCard(CardView card, BigCard bigCard, Dimension dimension, UUID gameId, boolean loadImage, boolean previewable) {
        if (cardPlugin != null) {
            if (previewable) {
                mageActionCallback.refreshSession();
                mageActionCallback.setCardPreviewComponent(bigCard);
            }
            return cardPlugin.getMageCard(card, dimension, gameId, mageActionCallback, false, !MageFrame.isLite() && loadImage);
        } else {
            return new Card(card, bigCard, Config.dimensions, gameId);
        }
    }

    @Override
    public boolean isCardPluginLoaded() {
        return this.cardPlugin != null;
    }

    @Override
    public int sortPermanents(Map<String, JComponent> ui, Map<UUID, MagePermanent> permanents, boolean topRow) {
        if (this.cardPlugin != null) {
            return this.cardPlugin.sortPermanents(ui, permanents, PreferencesDialog.getCachedValue("nonLandPermanentsInOnePile", "false").equals("true"), topRow);
        }
        return -1;
    }

    @Override
    public void downloadSymbols() {
        if (this.cardPlugin != null) {
            this.cardPlugin.downloadSymbols(getImagesDir());
        }
    }

    @Override
    public int getGamesPlayed() {
        if (this.counterPlugin != null) {
            synchronized (Plugins.class) {
                try {
                    return this.counterPlugin.getGamePlayed();
                } catch (PluginException e) {
                    LOGGER.fatal(e.getMessage());
                    throw new RuntimeException(e);
                }
            }
        }
        return -1;
    }

    @Override
    public void addGamesPlayed() {
        if (this.counterPlugin != null) {
            synchronized (Plugins.class) {
                try {
                    this.counterPlugin.addGamePlayed();
                } catch (PluginException e) {
                    LOGGER.fatal(e.getMessage());
                    throw new RuntimeException(e);
                }
            }
        }
    }

    @Override
    public boolean isCounterPluginLoaded() {
        return this.counterPlugin != null;
    }

    @Override
    public boolean isThemePluginLoaded() {
        return this.themePlugin != null;
    }

    @Override
    public void onAddCard(MagePermanent card, int count) {
        if (this.cardPlugin != null) {
            this.cardPlugin.onAddCard(card, count);
        }
    }

    @Override
    public void onRemoveCard(MagePermanent card, int count) {
        if (this.cardPlugin != null) {
            this.cardPlugin.onRemoveCard(card, count);
        }
    }

    @Override
    public JComponent getCardInfoPane() {
        if (this.cardPlugin != null) {
            return this.cardPlugin.getCardInfoPane();
        }
        return null;
    }

    @Override
    public BufferedImage getOriginalImage(CardView card) {
        if (this.cardPlugin != null) {
            return this.cardPlugin.getOriginalImage(card);
        }
        return null;
    }

    @Override
    public ActionCallback getActionCallback() {
        return mageActionCallback;
    }
}
