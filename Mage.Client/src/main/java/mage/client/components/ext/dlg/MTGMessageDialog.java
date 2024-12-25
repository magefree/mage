package mage.client.components.ext.dlg;

import mage.client.components.ext.MessageDialogType;
import mage.client.components.ext.dlg.impl.ChoiceDialog;

import java.awt.*;

public class MTGMessageDialog implements MTGDialog {
    @Override
    public Color initialize(DialogContainer container, DlgParams params, int X_OFFSET, int Y_OFFSET) {
        container.setAlpha(0);
        if (params.type == MessageDialogType.WARNING) {
            return new Color(255, 0, 0, 90);
        } else {
            return new Color(0, 0, 0, 90);
        }

    }
}
