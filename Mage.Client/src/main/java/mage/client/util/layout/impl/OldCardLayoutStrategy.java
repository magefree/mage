package mage.client.util.layout.impl;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.Map;
import java.util.UUID;
import javax.swing.JLayeredPane;
import mage.cards.MagePermanent;
import mage.client.game.BattlefieldPanel;
import mage.client.plugins.impl.Plugins;
import mage.client.util.layout.CardLayoutStrategy;
import mage.view.PermanentView;

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
    private static final int ATTACHMENTS_MIN_DX_OFFSET = 12;

    /**
     * This offset is used for each attachment
     */
    private static final int ATTACHMENT_MIN_DY_OFFSET = 12;

    final class AttachmentLayoutInfos {

        private int columns;
        private int attachments;

        public AttachmentLayoutInfos(int columns, int attachments) {
            this.columns = columns;
            this.attachments = attachments;
        }

        public int getColumns() {
            return columns;
        }

        public int getAttachments() {
            return attachments;
        }

        public void increaseAttachments() {
            attachments++;
        }

        public void increaseColumns() {
            columns++;
        }
    }

    @Override
    public void doLayout(BattlefieldPanel battlefieldPanel, int width) {
        Map<UUID, MagePermanent> permanents = battlefieldPanel.getPermanents();
        JLayeredPane mainPanel = battlefieldPanel.getMainPanel();

        // does the basic layout of rows and colums
        int height = Plugins.instance.sortPermanents(battlefieldPanel.getUiComponentsList(), permanents, battlefieldPanel.isTopPanelBattlefield());

        mainPanel.setPreferredSize(new Dimension(width - 30, height));

        for (PermanentView permanent : battlefieldPanel.getBattlefield().values()) {
            if (permanent.getAttachments() != null && !permanent.isAttachedTo()) { // Layout only permanents that are not attached to other permanents itself
                groupAttachments(battlefieldPanel, mainPanel, permanents, permanent);
            }
        }

    }

    private void groupAttachments(BattlefieldPanel battlefieldPanel, JLayeredPane mainPanel, Map<UUID, MagePermanent> permanents, PermanentView permanentWithAttachmentsView) {
        MagePermanent permWithAttachments = permanents.get(permanentWithAttachmentsView.getId());
        if (permWithAttachments == null) {
            return;
        }
        // Calculate how many vertical columns are needed and number of attachements
        AttachmentLayoutInfos attachmentLayoutInfos = calculateNeededNumberOfVerticalColumns(0, battlefieldPanel, permanents, permanentWithAttachmentsView);
        int position = battlefieldPanel.getPosition(permWithAttachments); // relative position within the layer
        // permWithAttachments.getLinks().clear();
        Rectangle rectangleBaseCard = permWithAttachments.getBounds();
        if (!Plugins.instance.isCardPluginLoaded()) {
            permWithAttachments.getLinks().clear();
            for (UUID attachmentId : permanentWithAttachmentsView.getAttachments()) {
                MagePermanent link = permanents.get(attachmentId);
                if (link != null) {
                    permWithAttachments.getLinks().add(link);
                    rectangleBaseCard.translate(20, 20);
                    link.setBounds(rectangleBaseCard);
                    battlefieldPanel.setPosition(link, ++position);
                }
            }
        } else {
            layoutAttachements(rectangleBaseCard.getX(), attachmentLayoutInfos.getColumns(),
                    attachmentLayoutInfos.getAttachments(), permanentWithAttachmentsView, permanents, battlefieldPanel, mainPanel, rectangleBaseCard);
            mainPanel.setComponentZOrder(permWithAttachments, 0);
        }

    }

    private void layoutAttachements(double baseX, // basic x position
            int maxCul, // number of attachments levels
            int ZOrder,
            PermanentView permanentWithAttachmentsView,
            Map<UUID, MagePermanent> permanents,
            BattlefieldPanel battlefieldPanel,
            JLayeredPane mainPanel,
            Rectangle rectangleBaseCard) {

        MagePermanent permWithAttachments = permanents.get(permanentWithAttachmentsView.getId());
        if (permWithAttachments == null) {
            return;
        }
        int col = getVerticalCul(permanentWithAttachmentsView, battlefieldPanel); // from right to left [2][1][0]
        int currentAttachmentCol = col + 1;
        permWithAttachments.getLinks().clear();
        int VerticalIndex = permanentWithAttachmentsView.getAttachments().size();
        for (UUID attachmentId : permanentWithAttachmentsView.getAttachments()) {
            PermanentView attachedPermanentView = battlefieldPanel.getBattlefield().get(attachmentId);
            if (attachedPermanentView != null && attachedPermanentView.getAttachments() != null && !attachedPermanentView.getAttachments().isEmpty()) {
                layoutAttachements(baseX, maxCul, ZOrder, attachedPermanentView, permanents, battlefieldPanel, mainPanel, rectangleBaseCard);
            }

            MagePermanent attachedPermanent = permanents.get(attachmentId);
            if (attachedPermanent != null) {
                // reset x position
                Point point = new Point();
                point.setLocation(baseX + (maxCul - currentAttachmentCol) * Math.max(permWithAttachments.getWidth() / 10, ATTACHMENTS_MIN_DX_OFFSET), rectangleBaseCard.getY());
                rectangleBaseCard.setLocation(point);

                attachedPermanent.setBounds(rectangleBaseCard); // set position first to the same as of the permanent it is attached to
                permWithAttachments.getLinks().add(attachedPermanent);
                int dyOffset = Math.max(permWithAttachments.getHeight() / 10, ATTACHMENT_MIN_DY_OFFSET); // calculate y offset
                if (VerticalIndex == 1) {
                    rectangleBaseCard.translate(Math.max(permWithAttachments.getWidth() / 10, ATTACHMENTS_MIN_DX_OFFSET), dyOffset); // do it once
                } else {
                    rectangleBaseCard.translate(0, dyOffset);
                }
                permWithAttachments.setBounds(rectangleBaseCard);
                battlefieldPanel.moveToFront(attachedPermanent);
                battlefieldPanel.moveToFront(permWithAttachments);
                mainPanel.setComponentZOrder(attachedPermanent, ZOrder--);
                VerticalIndex--;
            }
        }
    }

    private AttachmentLayoutInfos calculateNeededNumberOfVerticalColumns(int currentCol, BattlefieldPanel battlefieldPanel, Map<UUID, MagePermanent> permanents, PermanentView permanentWithAttachmentsView) {
        int maxCol = ++currentCol;
        int attachments = 0;
        for (UUID attachmentId : permanentWithAttachmentsView.getAttachments()) {
            PermanentView attachedPermanent = battlefieldPanel.getBattlefield().get(attachmentId);
            if (attachedPermanent != null) {
                attachments++;
                if (attachedPermanent.getAttachments() != null && !attachedPermanent.getAttachments().isEmpty()) {
                    AttachmentLayoutInfos attachmentLayoutInfos = calculateNeededNumberOfVerticalColumns(currentCol, battlefieldPanel, permanents, attachedPermanent);
                    if (attachmentLayoutInfos.getColumns() > maxCol) {
                        maxCol = attachmentLayoutInfos.getColumns();
                        attachments += attachmentLayoutInfos.getAttachments();
                    }
                }
            }
        }
        return new AttachmentLayoutInfos(maxCol, attachments);
    }

    // The root permanent is col 0. An attachment attached to the root is col 1. And an attachement attached to the first attachment is col 2. etc.
    private int getVerticalCul(PermanentView permanentView, BattlefieldPanel battlefieldPanel) {
        int cul = 0;
        if (permanentView.isAttachedTo()) {
            PermanentView attachedToPermanent = battlefieldPanel.getBattlefield().get(permanentView.getAttachedTo());
            if (attachedToPermanent != null) {
                cul = getVerticalCul(attachedToPermanent, battlefieldPanel);
            }
            cul++;
        }
        return cul;
    }

    @Override
    public int getDefaultZOrder() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void onAdd(BattlefieldPanel jLayeredPane
    ) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
