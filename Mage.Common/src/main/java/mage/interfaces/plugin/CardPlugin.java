package mage.interfaces.plugin;

import mage.cards.MagePermanent;
import mage.cards.action.ActionCallback;
import mage.view.CardView;
import mage.view.PermanentView;
import net.xeoh.plugins.base.Plugin;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Map;
import java.util.UUID;

/**
 * Interface for card plugins
 *
 * @author nantuko
 * @version 0.1 31.10.2010 #getMagePermanent, #sortPermanents
 */
public interface CardPlugin extends Plugin {

    MagePermanent getMagePermanent(PermanentView permanent, Dimension dimension, UUID gameId, ActionCallback callback, boolean canBeFoil, boolean loadImage, int renderMode);

    MagePermanent getMageCard(CardView permanent, Dimension dimension, UUID gameId, ActionCallback callback, boolean canBeFoil, boolean loadImage, int renderMode);

    int sortPermanents(Map<String, JComponent> ui, Map<UUID, MagePermanent> cards, boolean nonPermanentsOwnRow, boolean topPanel);

    /**
     * Download various symbols (mana, tap, set).
     *
     * @param imagesDir Path to check in and store symbols to. Can't be null.
     */
    void downloadSymbols(String imagesDir);

    void onAddCard(MagePermanent card, int count);

    void onRemoveCard(MagePermanent card, int count);

    JComponent getCardInfoPane();

    BufferedImage getOriginalImage(CardView card);

    void changeGUISize();
}
