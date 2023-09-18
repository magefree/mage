package mage.client.plugins.impl;

import mage.abilities.icon.CardIconRenderSettings;
import mage.cards.MageCard;
import mage.cards.action.ActionCallback;
import mage.client.MageFrame;
import mage.client.cards.BigCard;
import mage.client.dialog.PreferencesDialog;
import mage.client.plugins.MagePlugins;
import mage.client.plugins.adapters.MageActionCallback;
import mage.interfaces.PluginException;
import mage.interfaces.plugin.CardPlugin;
import mage.interfaces.plugin.CounterPlugin;
import mage.interfaces.plugin.ThemePlugin;
import mage.view.CardView;
import mage.view.PermanentView;
import net.xeoh.plugins.base.PluginManager;
import net.xeoh.plugins.base.impl.PluginManagerFactory;
import net.xeoh.plugins.base.util.uri.ClassURI;
import org.apache.log4j.Logger;
import org.mage.card.arcane.MageLayer;
import org.mage.plugins.card.CardPluginImpl;
import org.mage.plugins.theme.ThemePluginImpl;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.mage.plugins.card.utils.CardImageUtils.getImagesDir;

public enum Plugins implements MagePlugins {
    instance;
    public static final String PLUGINS_DIRECTORY = "plugins";

    private static final Logger LOGGER = Logger.getLogger(Plugins.class);
    private static PluginManager pm;

    private ThemePlugin themePlugin = null;
    private CardPlugin cardPlugin = null; // required by game
    private CounterPlugin counterPlugin = null;
    private static final MageActionCallback mageActionCallback = new MageActionCallback();
    private final Map<String, String> sortingOptions = new HashMap<>();

    @Override
    public void loadPlugins() {
        LOGGER.info("Loading plugins...");
        pm = PluginManagerFactory.createPluginManager();
        pm.addPluginsFrom(new File(PLUGINS_DIRECTORY + File.separator).toURI());
        pm.addPluginsFrom(new ClassURI(CardPluginImpl.class).toURI());
        pm.addPluginsFrom(new ClassURI(ThemePluginImpl.class).toURI());

        this.cardPlugin = pm.getPlugin(CardPlugin.class);
        this.counterPlugin = pm.getPlugin(CounterPlugin.class);
        this.themePlugin = pm.getPlugin(ThemePlugin.class);
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
        if (this.cardPlugin != null) {
            cardPlugin.changeGUISize();
        }
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
    public MageCard getMagePermanent(PermanentView card, BigCard bigCard, CardIconRenderSettings cardIconRenderSettings, Dimension dimension, UUID gameId, boolean loadImage, int renderMode, boolean needFullPermanentRender) {
        MageCard mageCard;
        if (cardPlugin == null) {
            throw new IllegalArgumentException("Card's plugin must be loaded");
        }
        mageActionCallback.refreshSession();
        mageActionCallback.setCardPreviewComponent(bigCard);
        mageCard = cardPlugin.getMagePermanent(card, dimension, gameId, mageActionCallback, false, !MageFrame.isLite() && loadImage, renderMode, needFullPermanentRender);

        return createLayeredCard(mageCard, dimension, cardIconRenderSettings);
    }

    @Override
    public MageCard getMageCard(CardView card, BigCard bigCard, CardIconRenderSettings cardIconRenderSettings, Dimension dimension, UUID gameId, boolean loadImage, boolean previewable, int renderMode, boolean needFullPermanentRender) {
        // Card icons panels must be put outside of the card like MTG Arena.
        // So for compatibility purposes: keep free space for icons and change card dimention
        MageCard mageCard;
        if (cardPlugin == null) {
            throw new IllegalArgumentException("Card's plugin must be loaded");
        }
        if (previewable) {
            mageActionCallback.refreshSession();
            mageActionCallback.setCardPreviewComponent(bigCard);
        }
        mageCard = cardPlugin.getMageCard(card, dimension, gameId, mageActionCallback, false, !MageFrame.isLite() && loadImage, renderMode, needFullPermanentRender);
        return createLayeredCard(mageCard, dimension, cardIconRenderSettings);
    }

    private MageCard createLayeredCard(MageCard mageCard, Dimension dimension, CardIconRenderSettings cardIconRenderSettings) {
        MageLayer mageLayer = new MageLayer(mageCard, cardIconRenderSettings);
        mageLayer.setCardBounds(0, 0, dimension.width, dimension.height); // default size
        return mageLayer;
    }

    @Override
    public boolean isCardPluginLoaded() {
        return this.cardPlugin != null;
    }

    @Override
    public int sortPermanents(Map<String, JComponent> ui, Map<UUID, MageCard> cards, boolean topRow) {
        if (this.cardPlugin != null) {
            return this.cardPlugin.sortPermanents(ui, cards, PreferencesDialog.getCachedValue("nonLandPermanentsInOnePile", "false").equals("true"), topRow);
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
    public void onAddCard(MageCard card, int count) {
        if (this.cardPlugin != null) {
            this.cardPlugin.onAddCard(card, count);
        }
    }

    @Override
    public void onRemoveCard(MageCard card, int count) {
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
