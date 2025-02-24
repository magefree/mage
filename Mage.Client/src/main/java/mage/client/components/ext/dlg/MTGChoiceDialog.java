package mage.client.components.ext.dlg;

import mage.client.components.ext.dlg.impl.ChoiceDialog;

import java.awt.*;

public class MTGChoiceDialog implements MTGDialog {
    @Override
    public Color initialize(DialogContainer container, DlgParams params, int X_OFFSET, int Y_OFFSET) {
        container.setAlpha(0);
        ChoiceDialog dlg = new ChoiceDialog(params, "Choose");
        container.add(dlg);
        dlg.setLocation(X_OFFSET + 10, Y_OFFSET + 10);
        dlg.updateSize(params.rect.width - 80, params.rect.height - 80);
        return new Color(0, 0, 0, 110);
    }
}
