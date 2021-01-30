package mage.client.components.ext.dlg;

import mage.client.components.ImageButton;
import mage.client.util.ClientDefaultSettings;
import org.jdesktop.swingx.JXPanel;

import javax.swing.*;
import java.awt.*;

/**
 * Game GUI: part of the old dialog system
 *
 * @author mw, noxx, JayDi85
 */
public abstract class IDialogPanel extends JXPanel {

    private final DlgParams params;
    private Dimension cardDimension;

    public DlgParams getDlgParams() {
        return params;
    }

    public IDialogPanel(DlgParams params) {
        super();
        this.params = params;
    }

    protected void updateSize(int newWidth, int newHeight) {
        Rectangle r0 = getBounds();
        r0.width = newWidth;
        r0.height = newHeight;
        setBounds(r0);
    }

    /**
     * Make inner component transparent.
     */
    protected void makeTransparent() {
        setOpaque(false);

        for (int i = 0; i < getComponentCount(); i++) {
            Component c = getComponent(i);
            if (c instanceof AbstractButton && !(c instanceof JButton)) {
                ((AbstractButton) c).setContentAreaFilled(false);
            } else if (c instanceof ImageButton) {
                ((AbstractButton) c).setContentAreaFilled(false);
            }
        }
    }

    protected void makeTransparent(JLayeredPane jLayeredPane) {
        setOpaque(false);

        for (int i = 0; i < getComponentCount(); i++) {
            Component c = jLayeredPane.getComponent(i);
            if (c instanceof AbstractButton && !(c instanceof JButton)) {
                ((AbstractButton) c).setContentAreaFilled(false);
            } else if (c instanceof ImageButton) {
                ((AbstractButton) c).setContentAreaFilled(false);
            }
        }
    }

    protected Dimension getCardDimension() {
        if (cardDimension == null) {
            cardDimension = new Dimension(ClientDefaultSettings.dimensions.getFrameWidth(), ClientDefaultSettings.dimensions.getFrameHeight());
        }
        return cardDimension;
    }

    /**
     * Default UID.
     */
    private static final long serialVersionUID = 1L;
}