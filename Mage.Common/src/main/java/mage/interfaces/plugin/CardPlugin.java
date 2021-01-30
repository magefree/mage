package mage.interfaces.plugin;

import mage.cards.MageCard;
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
 */
public interface CardPlugin extends Plugin {

    MageCard getMagePermanent(PermanentView permanent, Dimension dimension, UUID gameId, ActionCallback callback,
                              boolean canBeFoil, boolean loadImage, int renderMode, boolean needFullPermanentRender);

    MageCard getMageCard(CardView permanent, Dimension dimension, UUID gameId, ActionCallback callback,
                         boolean canBeFoil, boolean loadImage, int renderMode, boolean needFullPermanentRender);

    int sortPermanents(Map<String, JComponent> ui, Map<UUID, MageCard> cards, boolean nonPermanentsOwnRow, boolean topPanel);

    /**
     * Download various symbols (mana, tap, set).
     *
     * @param imagesDir Path to check in and store symbols to. Can't be null.
     */
    void downloadSymbols(String imagesDir);

    /**
     * Uses for show/hide animation on the battlefield
     *
     * @param card
     * @param count
     */
    void onAddCard(MageCard card, int count);

    /**
     * Uses for show/hide animation on the battlefield
     *
     * @param card
     * @param count
     */
    void onRemoveCard(MageCard card, int count);

    JComponent getCardInfoPane();

    BufferedImage getOriginalImage(CardView card);

    void changeGUISize();
}
