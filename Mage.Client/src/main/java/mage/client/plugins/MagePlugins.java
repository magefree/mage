package mage.client.plugins;

import mage.cards.MageCard;
import mage.cards.MagePermanent;
import mage.cards.action.ActionCallback;
import mage.client.cards.BigCard;
import mage.view.CardView;
import mage.view.PermanentView;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Collection;
import java.util.Map;
import java.util.UUID;

public interface MagePlugins {
    void loadPlugins();
    void shutdown();
    void updateGamePanel(Map<String, JComponent> ui);
    JComponent updateTablePanel(Map<String, JComponent> ui);
    MagePermanent getMagePermanent(PermanentView card, BigCard bigCard, Dimension dimension, UUID gameId, boolean loadImage);
    MageCard getMageCard(CardView card, BigCard bigCard, Dimension dimension, UUID gameId, boolean loadImage);
    boolean isThemePluginLoaded();
    boolean isCardPluginLoaded();
    boolean isCounterPluginLoaded();
    int sortPermanents(Map<String, JComponent> ui, Collection<MagePermanent> permanents);
    void downloadSymbols();
    int getGamesPlayed();
    void addGamesPlayed();
    Image getManaSymbolImage(String symbol);
    void onAddCard(MagePermanent card, int count);
    void onRemoveCard(MagePermanent card, int count);
    JComponent getCardInfoPane();
    BufferedImage getOriginalImage(CardView card);
    ActionCallback getActionCallback();
}
