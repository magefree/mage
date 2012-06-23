package mage.interfaces.plugin;

import mage.cards.Card;
import mage.cards.MagePermanent;
import mage.cards.action.ActionCallback;
import mage.view.CardView;
import mage.view.PermanentView;
import net.xeoh.plugins.base.Plugin;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

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
    int sortPermanents(Map<String, JComponent> ui, Collection<MagePermanent> cards, Map<String, String> options);

    /**
     * Check for new images.
     *
     * @param allCards Set of cards to check images for.
     * @param imagesPath Path to check in. Can be null, in such case default path should be used.
     */
    public boolean newImages(Set<Card> allCards, String imagesPath);

    /**
     * Download images.
     *
     * @param allCards Set of cards to download images for.
     * @param imagesPath Path to check in and store images to. Can be null, in such case default path should be used.
     */
    void downloadImages(Set<Card> allCards, String imagesPath);

    /**
     * Download various symbols (mana, tap, set).
     *
     * @param imagesPath Path to check in and store symbols to. Can be null, in such case default path should be used.
     */
    void downloadSymbols(String imagesPath);

    Image getManaSymbolImage(String symbol);
    void onAddCard(MagePermanent card, int count);
    void onRemoveCard(MagePermanent card, int count);
    JComponent getCardInfoPane();
    BufferedImage getOriginalImage(CardView card);
}
