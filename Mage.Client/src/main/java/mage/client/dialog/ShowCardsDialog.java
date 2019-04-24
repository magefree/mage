

 /*
 * ShowCardsDialog.java
 *
 * Created on 3-Feb-2010, 8:59:11 PM
 */
package mage.client.dialog;

import java.awt.Component;
import java.awt.Point;
import java.io.Serializable;
import java.util.Map;
import java.util.UUID;
import javax.swing.JLayeredPane;
import javax.swing.JPopupMenu;
import javax.swing.SwingUtilities;
import mage.client.MageFrame;
import mage.client.cards.BigCard;
import mage.client.cards.CardArea;
import mage.client.util.Event;
import mage.client.util.Listener;
import mage.client.util.SettingsManager;
import mage.client.util.gui.GuiDisplayUtil;
import mage.game.events.PlayerQueryEvent.QueryType;
import mage.view.CardsView;
import org.mage.card.arcane.CardPanel;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class ShowCardsDialog extends MageDialog {

    // remember if this dialog was already auto positioned, so don't do it after the first time
    private boolean positioned;

    /**
     * Creates new form ShowCardsDialog
     */
    public ShowCardsDialog() {
        this.positioned = false;

        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        initComponents();

        this.setModal(false);

    }

    public void cleanUp() {
        cardArea.cleanUp();
        for (Component comp : cardArea.getComponents()) {
            if (comp instanceof CardPanel) {
                ((CardPanel) comp).cleanUp();
                cardArea.remove(comp);
            }
        }
    }

    @Override
    public void changeGUISize() {
        setGUISize();
        cardArea.changeGUISize();
    }

    private void setGUISize() {

    }

    public void loadCards(String name, CardsView showCards, BigCard bigCard,
            UUID gameId, boolean modal, Map<String, Serializable> options,
            JPopupMenu popupMenu, Listener<Event> eventListener) {
        this.title = name;
        this.setTitelBarToolTip(name);
        cardArea.clearCardEventListeners();
        cardArea.loadCards(showCards, bigCard, gameId);
        if (options != null) {
            if (options.containsKey("chosen")) {
                java.util.List<UUID> chosenCards = (java.util.List<UUID>) options.get("chosen");
                cardArea.selectCards(chosenCards);
            }
            if (options.containsKey("choosable")) {
                java.util.List<UUID> choosableCards = (java.util.List<UUID>) options.get("choosable");
                cardArea.markCards(choosableCards);
            }
            if (options.containsKey("queryType") && options.get("queryType") == QueryType.PICK_ABILITY) {
                cardArea.setPopupMenu(popupMenu);
            }
        }
        if (popupMenu != null) {
            this.cardArea.setPopupMenu(popupMenu);
        }
        if (eventListener != null) {
            this.cardArea.addCardEventListener(eventListener);
        }

        pack();

        this.revalidate();
        this.repaint();
        this.setModal(modal);

        // window settings
        if (this.isModal()){
            MageFrame.getDesktop().add(this, JLayeredPane.MODAL_LAYER);
        }else{
            MageFrame.getDesktop().add(this, JLayeredPane.PALETTE_LAYER);
        }

        SwingUtilities.invokeLater(() -> {
            if (!positioned) {
                int width = ShowCardsDialog.this.getWidth();
                int height = ShowCardsDialog.this.getHeight();
                if (width > 0 && height > 0) {
                    Point centered = SettingsManager.instance.getComponentPosition(width, height);
                    ShowCardsDialog.this.setLocation(centered.x, centered.y);
                    positioned = true;
                    GuiDisplayUtil.keepComponentInsideScreen(centered.x, centered.y, ShowCardsDialog.this);
                }
            }
            ShowCardsDialog.this.setVisible(true);
        });
    }

    private void initComponents() {

        cardArea = new CardArea();

        setClosable(true);
        setResizable(true);
        getContentPane().setLayout(new java.awt.BorderLayout());
        getContentPane().add(cardArea, java.awt.BorderLayout.CENTER);
        setGUISize();
        pack();
    }

    private CardArea cardArea;
}
