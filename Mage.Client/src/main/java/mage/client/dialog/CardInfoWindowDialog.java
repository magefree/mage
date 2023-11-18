package mage.client.dialog;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyVetoException;
import java.util.*;
import javax.swing.*;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
import javax.swing.plaf.basic.BasicInternalFrameUI;

import mage.cards.MageCard;
import mage.client.cards.BigCard;
import mage.client.components.MageDesktopIconifySupport;
import mage.client.util.GUISizeHelper;
import mage.client.util.ImageHelper;
import mage.client.util.SettingsManager;
import mage.client.util.gui.GuiDisplayUtil;
import mage.constants.CardType;
import mage.util.RandomUtil;
import mage.view.CardView;
import mage.view.CardsView;
import mage.view.ExileView;
import mage.view.SimpleCardsView;
import org.apache.log4j.Logger;
import org.mage.plugins.card.utils.impl.ImageManagerImpl;

/**
 * Game GUI: popup windows with title like reveal, graveyard
 *
 * @author BetaSteward_at_googlemail.com, JayDi85
 */
public class CardInfoWindowDialog extends MageDialog implements MageDesktopIconifySupport {

    private static final Logger LOGGER = Logger.getLogger(CardInfoWindowDialog.class);

    public enum ShowType {
        REVEAL, REVEAL_TOP_LIBRARY, LOOKED_AT, EXILE, GRAVEYARD, COMPANION, SIDEBOARD, OTHER
    }

    private final ShowType showType;
    private boolean positioned;
    private final String name;

    public CardInfoWindowDialog(ShowType showType, String name) {
        this.name = name;
        this.title = name;
        this.showType = showType;
        this.positioned = false;
        initComponents();

        this.setModal(false);
        switch (this.showType) {
            case LOOKED_AT:
                this.setFrameIcon(new ImageIcon(ImageManagerImpl.instance.getLookedAtImage()));
                this.setClosable(true);
                break;
            case REVEAL:
                this.setFrameIcon(new ImageIcon(ImageManagerImpl.instance.getRevealedImage()));
                this.setClosable(true);
                break;
            case REVEAL_TOP_LIBRARY:
                this.setFrameIcon(new ImageIcon(ImageHelper.getImageFromResources("/info/library.png")));
                this.setClosable(true);
                break;
            case GRAVEYARD:
                this.setFrameIcon(new ImageIcon(ImageHelper.getImageFromResources("/info/grave.png")));
                this.setClosable(true);
                this.setDefaultCloseOperation(HIDE_ON_CLOSE);
                this.addInternalFrameListener(new InternalFrameAdapter() {
                    @Override
                    public void internalFrameClosing(InternalFrameEvent e) {
                        CardInfoWindowDialog.this.hideDialog();
                    }
                });
                break;
            case SIDEBOARD:
                this.setFrameIcon(new ImageIcon(ImageHelper.getImageFromResources("/info/library.png")));
                this.setClosable(true);
                this.setDefaultCloseOperation(HIDE_ON_CLOSE);
                this.addInternalFrameListener(new InternalFrameAdapter() {
                    @Override
                    public void internalFrameClosing(InternalFrameEvent e) {
                        CardInfoWindowDialog.this.hideDialog();
                    }
                });
                break;
            case EXILE:
                this.setFrameIcon(new ImageIcon(ImageManagerImpl.instance.getExileImage()));
                break;
            case COMPANION:
                this.setFrameIcon(new ImageIcon(ImageManagerImpl.instance.getTokenIconImage()));
                this.setClosable(false);
                break;
            case OTHER:
            default:
            // no icon yet
        }
        this.setTitelBarToolTip(name);
        setGUISize();
    }

    public void cleanUp() {
        cards.cleanUp();
    }

    @Override
    public void changeGUISize() {
        setGUISize();
        this.validate();
        this.repaint();
    }

    private void setGUISize() {
        cards.setCardDimension(GUISizeHelper.otherZonesCardDimension);
        cards.changeGUISize();
    }

    public void loadCards(ExileView exile, BigCard bigCard, UUID gameId) {
        boolean changed = cards.loadCards(exile, bigCard, gameId, true);
        String titel = name + " (" + exile.size() + ')';
        setTitle(titel);
        this.setTitelBarToolTip(titel);
        if (!exile.isEmpty()) {
            show();
            if (changed) {
                try {
                    this.setIcon(false);
                } catch (PropertyVetoException ex) {
                    LOGGER.error(null, ex);
                }
            }
        } else {
            this.hideDialog();
        }
    }

    public void loadCards(SimpleCardsView showCards, BigCard bigCard, UUID gameId) {
        cards.loadCards(showCards, bigCard, gameId);
        showAndPositionWindow();
    }

    public void loadCards(CardsView showCards, BigCard bigCard, UUID gameId) {
        loadCards(showCards, bigCard, gameId, true);
    }

    public void loadCards(CardsView showCards, BigCard bigCard, UUID gameId, boolean revertOrder) {
        cards.loadCards(showCards, bigCard, gameId, revertOrder);

        // additional info for grave windows
        if (showType == ShowType.GRAVEYARD) {
            int qty = qtyCardTypes(showCards);
            String newTitle = name + "'s graveyard (" + showCards.size() + ")  -  " + qty + ((qty == 1) ? " card type" : " card types");
            setTitle(newTitle);
            this.setTitelBarToolTip(newTitle);
        }

        // additional info for sideboard window
        if (showType == ShowType.SIDEBOARD) {
            String newTitle = name + "'s sideboard";
            setTitle(newTitle);
            this.setTitelBarToolTip(newTitle);
        }

        showAndPositionWindow();
    }

    /**
     * For GUI: get mage card components for update (example: change playable status)
     * Warning, do not change the list
     *
     * @return
     */
    public Map<UUID, MageCard> getMageCardsForUpdate() {
        return this.cards.getMageCardsForUpdate();
    }

    @Override
    public void show() {
        // hide empty exile windows
        if (showType == ShowType.EXILE) {
            if (cards == null || cards.getNumberOfCards() == 0) {
                return;
            }
        }

        super.show();

        // auto-position on first usage
        if (positioned) {
            showAndPositionWindow();
        }
    }

    private void showAndPositionWindow() {
        SwingUtilities.invokeLater(() -> {
            int width = CardInfoWindowDialog.this.getWidth();
            int height = CardInfoWindowDialog.this.getHeight();
            if (width > 0 && height > 0) {
                Point centered = SettingsManager.instance.getComponentPosition(width, height);
                if (!positioned) {
                    // starting position
                    // little randomize to see multiple opened windows
                    int xPos = centered.x / 2 + RandomUtil.nextInt(50);
                    int yPos = centered.y / 2 + RandomUtil.nextInt(50);
                    CardInfoWindowDialog.this.setLocation(xPos, yPos);
                    show();
                    positioned = true;
                }
                GuiDisplayUtil.keepComponentInsideFrame(centered.x, centered.y, CardInfoWindowDialog.this);
            }
        });
    }

    private int qtyCardTypes(mage.view.CardsView cardsView) {
        Set<String> cardTypesPresent = new LinkedHashSet<String>() {
        };
        for (CardView card : cardsView.values()) {
            Set<CardType> cardTypes = EnumSet.noneOf(CardType.class);
            cardTypes.addAll(card.getCardTypes());
            for (CardType cardType : cardTypes) {
                cardTypesPresent.add(cardType.toString());
            }
        }
        if (cardTypesPresent.isEmpty()) {
            return 0;
        } else {
            return cardTypesPresent.size();
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        cards = new mage.client.cards.Cards();

        setIconifiable(true);
        setResizable(true);
        setPreferredSize(new Dimension((int) Math.round(GUISizeHelper.otherZonesCardDimension.width * 1.4),
                (int) Math.round(GUISizeHelper.otherZonesCardDimension.height * 1.4)));
        getContentPane().setLayout(new java.awt.BorderLayout());
        getContentPane().add(cards, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private mage.client.cards.Cards cards;
    // End of variables declaration//GEN-END:variables

}
