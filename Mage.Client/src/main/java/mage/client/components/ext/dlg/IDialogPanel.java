package mage.client.components.ext.dlg;

import mage.client.components.ImageButton;
import org.jdesktop.swingx.JXPanel;

import javax.swing.*;
import java.awt.*;

/**
 * @author mw, noxx
 */
abstract public class IDialogPanel extends JXPanel {
    
    private DlgParams params;
    
    public DlgParams getDlgParams() {
        return params;
    }

    public void setDlgParams(DlgParams params) {
        this.params = params;
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
    
    /**
     * Default UID.
     */
    private static final long serialVersionUID = 1L;
}