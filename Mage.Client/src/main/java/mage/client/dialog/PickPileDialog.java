package mage.client.dialog;

import mage.cards.MageCard;
import mage.client.MageFrame;
import mage.client.cards.BigCard;
import mage.client.cards.CardArea;
import mage.view.CardsView;

import javax.swing.*;
import java.awt.*;
import java.util.UUID;

/**
 * Game GUI: pile choose dialog (one from two)
 *
 * @author BetaSteward_at_googlemail.com, JayDi85
 */
public class PickPileDialog extends MageDialog {

    private final CardArea pile1;
    private final CardArea pile2;

    private boolean pickedPile1 = false;
    private boolean pickedOK = false;
    private PickPileCallback callback = null;

    public PickPileDialog() {
        getContentPane().setLayout(new BorderLayout(0, 0));

        JPanel panel = new JPanel();
        getContentPane().add(panel, BorderLayout.WEST);
        panel.setLayout(new BorderLayout(0, 0));

        pile1 = new CardArea();
        panel.add(pile1, BorderLayout.CENTER);

        JButton btnChoosePile1 = new JButton("Pile 1");
        btnChoosePile1.addActionListener(e -> btnPile1ActionPerformed(e));
        panel.add(btnChoosePile1, BorderLayout.NORTH);

        JPanel panel_1 = new JPanel();
        getContentPane().add(panel_1, BorderLayout.EAST);
        panel_1.setLayout(new BorderLayout(0, 0));

        pile2 = new CardArea();
        panel_1.add(pile2, BorderLayout.CENTER);

        JButton btnChoosePile2 = new JButton("Pile 2");
        btnChoosePile2.addActionListener(e -> btnPile2ActionPerformed(e));
        panel_1.add(btnChoosePile2, BorderLayout.NORTH);
    }

    public interface PickPileCallback {
        void onChoiceDone();
    }

    public void showDialog(String name, CardsView pile1, CardsView pile2, BigCard bigCard, UUID gameId, PickPileCallback callback) {
        this.pickedOK = false;
        this.pickedPile1 = false;
        this.title = name;
        this.pile1.loadCardsNarrow(pile1, bigCard, gameId);
        this.pile2.loadCardsNarrow(pile2, bigCard, gameId);
        this.callback = callback;

        this.setModal(true);
        pack();

        // windows settings
        MageFrame.getDesktop().remove(this);
        MageFrame.getDesktop().add(this, this.isModal() ? JLayeredPane.MODAL_LAYER : JLayeredPane.PALETTE_LAYER);
        this.makeWindowCentered();

        this.setVisible(true);
    }

    public void cleanUp() {
        for (Component comp : pile1.getComponents()) {
            if (comp instanceof MageCard) {
                ((MageCard) comp).cleanUp();
                pile1.remove(comp);
            }
        }
        for (Component comp : pile2.getComponents()) {
            if (comp instanceof MageCard) {
                ((MageCard) comp).cleanUp();
                pile2.remove(comp);
            }
        }
    }

    private void doClose() {
        this.hideDialog();
        if (this.callback != null) {
            this.callback.onChoiceDone();
        }
        this.cleanUp();
        this.removeDialog();
    }

    private void btnPile1ActionPerformed(java.awt.event.ActionEvent evt) {
        pickedPile1 = true;
        pickedOK = true;
        doClose();
    }

    private void btnPile2ActionPerformed(java.awt.event.ActionEvent evt) {
        pickedPile1 = false;
        pickedOK = true;
        doClose();
    }

    public boolean isPickedPile1() {
        return this.pickedPile1;
    }
    public boolean isPickedOK() {
        return this.pickedOK;
    }
}
