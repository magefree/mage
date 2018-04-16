package mage.interfaces.plugin;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Map;
import java.util.UUID;
import javax.swing.*;
import mage.cards.MagePermanent;
import mage.cards.action.ActionCallback;
import mage.view.CardView;
import mage.view.PermanentView;
import net.xeoh.plugins.base.Plugin;

/**
 * Interface for card plugins
 *
 * @version 0.6 17.07.2011 added options to #sortPermanents
 * @version 0.3 21.11.2010 #getMageCard
 * @version 0.2 07.11.2010 #downloadImages
 * @version 0.1 31.10.2010 #getMagePermanent, #sortPermanents
 * @author nantuko
 */
public interface CardPlugin extends Plugin {

    MagePermanent getMagePermanent(PermanentView permanent, Dimension dimension, UUID gameId, ActionCallback callback, boolean canBeFoil, boolean loadImage);

    MagePermanent getMageCard(CardView permanent, Dimension dimension, UUID gameId, ActionCallback callback, boolean canBeFoil, boolean loadImage);

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
