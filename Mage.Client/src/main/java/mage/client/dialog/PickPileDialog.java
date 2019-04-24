package mage.client.dialog;

import mage.client.MageFrame;
import mage.client.cards.BigCard;
import mage.client.cards.CardArea;
import mage.view.CardsView;
import org.mage.card.arcane.CardPanel;

import javax.swing.*;
import java.awt.*;
import java.util.UUID;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class PickPileDialog extends MageDialog {

    private final CardArea pile1;
    private final CardArea pile2;

    private boolean pickedPile1 = false;

    /**
     * Create the frame.
     */
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

    public void cleanUp() {
        for (Component comp : pile1.getComponents()) {
            if (comp instanceof CardPanel) {
                ((CardPanel) comp).cleanUp();
                pile1.remove(comp);
            }
        }
        for (Component comp : pile2.getComponents()) {
            if (comp instanceof CardPanel) {
                ((CardPanel) comp).cleanUp();
                pile2.remove(comp);
            }
        }
    }

    public void loadCards(String name, CardsView pile1, CardsView pile2, BigCard bigCard, UUID gameId) {
        this.title = name;
        this.pile1.loadCardsNarrow(pile1, bigCard, gameId);
        this.pile2.loadCardsNarrow(pile2, bigCard, gameId);

        this.setModal(true);
        pack();

        // windows settings
        MageFrame.getDesktop().remove(this);
        if (this.isModal()) {
            MageFrame.getDesktop().add(this, JLayeredPane.MODAL_LAYER);
        } else {
            MageFrame.getDesktop().add(this, JLayeredPane.PALETTE_LAYER);
        }
        this.makeWindowCentered();

        this.revalidate(); // TODO: remove?
        this.repaint(); // TODO: remove?

        this.setVisible(true);
    }

    private void btnPile1ActionPerformed(java.awt.event.ActionEvent evt) {
        pickedPile1 = true;
        this.hideDialog();
    }

    private void btnPile2ActionPerformed(java.awt.event.ActionEvent evt) {
        pickedPile1 = false;
        this.hideDialog();
    }

    public boolean isPickedPile1() {
        return this.pickedPile1;
    }
}
