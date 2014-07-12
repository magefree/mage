package mage.client.util.layout.impl;

import mage.cards.MagePermanent;
import mage.client.game.BattlefieldPanel;
import mage.client.plugins.impl.Plugins;
import mage.client.util.layout.CardLayoutStrategy;
import mage.view.PermanentView;

import javax.swing.*;
import java.awt.*;
import java.util.Map;
import java.util.UUID;

/**
 * Card layout for client version 1.3.0 and earlier.
 *
 * Save it here for a while.
 *
 * @author noxx
 */
public class OldCardLayoutStrategy implements CardLayoutStrategy {

    /**
     * This offset is used once to shift all attachments
     */
    private static final int ATTACHMENTS_DX_OFFSET = 8;

    /**
     * This offset is used for each attachment
     */
    private static final int ATTACHMENT_DX_OFFSET = 0;
    private static final int ATTACHMENT_DY_OFFSET = 10;

    @Override
    public void doLayout(JLayeredPane jLayeredPane, int width) {
        Map<UUID, MagePermanent> permanents = ((BattlefieldPanel) jLayeredPane).getPermanents();
        JLayeredPane jPanel = ((BattlefieldPanel) jLayeredPane).getMainPanel();

        int height = Plugins.getInstance().sortPermanents(((BattlefieldPanel) jLayeredPane).getUiComponentsList(), permanents.values());
        jPanel.setPreferredSize(new Dimension(width - 30, height));

        for (PermanentView permanent : ((BattlefieldPanel) jLayeredPane).getBattlefield().values()) {
            if (permanent.getAttachments() != null) {
                groupAttachments(jLayeredPane, jPanel, permanents, permanent);
            }
        }

    }

    private void groupAttachments(JLayeredPane jLayeredPane, JLayeredPane jPanel, Map<UUID, MagePermanent> permanents, PermanentView permanent) {
        MagePermanent perm = permanents.get(permanent.getId());
        if (perm == null) {
            return;
        }
        int position = jLayeredPane.getPosition(perm);
        perm.getLinks().clear();
        Rectangle r = perm.getBounds();
        if (!Plugins.getInstance().isCardPluginLoaded()) {
            for (UUID attachmentId: permanent.getAttachments()) {
                MagePermanent link = permanents.get(attachmentId);
                if (link != null) {
                    perm.getLinks().add(link);
                    r.translate(20, 20);
                    link.setBounds(r);
                    jLayeredPane.setPosition(link, ++position);
                }
            }
        } else {
            int index = permanent.getAttachments().size();
            for (UUID attachmentId: permanent.getAttachments()) {
                MagePermanent link = permanents.get(attachmentId);
                if (link != null) {
                    link.setBounds(r);
                    perm.getLinks().add(link);
                    if (index == 1) {
                        r.translate(ATTACHMENTS_DX_OFFSET, ATTACHMENT_DY_OFFSET); // do it once
                    } else {
                        r.translate(ATTACHMENT_DX_OFFSET, ATTACHMENT_DY_OFFSET);
                    }
                    perm.setBounds(r);
                    jLayeredPane.moveToFront(link);
                    jLayeredPane.moveToFront(perm);
                    jPanel.setComponentZOrder(link, index);
                    index--;
                }
            }
            jPanel.setComponentZOrder(perm, index);
        }

    }

    @Override
    public int getDefaultZOrder() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void onAdd(JLayeredPane jLayeredPane) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
