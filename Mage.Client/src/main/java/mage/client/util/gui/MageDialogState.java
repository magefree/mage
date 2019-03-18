
package mage.client.util.gui;

import java.awt.Dimension;
import mage.client.dialog.MageDialog;

/**
 *  Holds the position and size of MageDialogs.
 * 
 * @author LevelX2
 */
public class MageDialogState {

    final Dimension dimension;
    final int xPos;
    final int yPos;

    public MageDialogState(MageDialog mageDialog) {
        this.dimension = mageDialog.getSize();
        this.xPos = mageDialog.getX();
        this.yPos = mageDialog.getY();
    }

    public boolean setStateToDialog(MageDialog mageDialog) {
        mageDialog.setSize(dimension);
        mageDialog.setLocation(xPos, yPos);
        GuiDisplayUtil.keepComponentInsideScreen(xPos, yPos, mageDialog);
        return true;
    }
}