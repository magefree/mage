package mage.client.util.layout.impl;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.Map;
import java.util.UUID;
import javax.swing.JLayeredPane;

import mage.cards.MageCard;
import mage.client.game.BattlefieldPanel;
import mage.client.plugins.impl.Plugins;
import mage.client.util.layout.CardLayoutStrategy;
import mage.view.PermanentView;

/**
 * Card layouts code
 *
 * @author noxx, JayDi85
 */
public class CardLayoutStrategyImpl implements CardLayoutStrategy {

    private static final int ATTACHMENTS_OFFSET_ALL_X = 12; // offset to shift all attachments
    private static final int ATTACHMENTS_OFFSET_SINGLE_Y = 12;
    private static final int ATTACHMENTS_MAX_COLUMNS = 10; // after 10 attachments no more offsets (will be "hide")

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
    public void doLayout(BattlefieldPanel battlefieldPanel, int battlefieldWidth) {
        Map<UUID, MageCard> cards = battlefieldPanel.getPermanentPanels();
        JLayeredPane mainPanel = battlefieldPanel.getMainPanel();

        // does the basic layout of rows and colums
        int height = Plugins.instance.sortPermanents(battlefieldPanel.getUiComponentsList(), cards, battlefieldPanel.isTopPanelBattlefield());

        mainPanel.setPreferredSize(new Dimension(battlefieldWidth - 30, height));

        for (PermanentView permanent : battlefieldPanel.getBattlefield().values()) {
            if (permanent.getAttachments() != null && !permanent.isAttachedTo()) { // Layout only permanents that are not attached to other permanents itself
                groupAttachments(battlefieldPanel, mainPanel, cards, permanent);
            }
        }
    }

    private void groupAttachments(BattlefieldPanel battlefieldPanel, JLayeredPane mainPanel, Map<UUID, MageCard> cards, PermanentView permanentWithAttachmentsView) {
        MageCard cardWithAttachments = cards.get(permanentWithAttachmentsView.getId());
        if (cardWithAttachments == null) {
            return;
        }
        // calculate how many vertical columns are needed and number of attachements
        AttachmentLayoutInfos attachmentLayoutInfos = calculateNeededNumberOfVerticalColumns(0, battlefieldPanel, cards, permanentWithAttachmentsView);
        // group by columns
        layoutAttachements(
                cardWithAttachments.getCardLocation().getCardX(),
                attachmentLayoutInfos.getColumns(),
                attachmentLayoutInfos.getAttachments(),
                permanentWithAttachmentsView,
                cards,
                battlefieldPanel,
                mainPanel,
                cardWithAttachments.getCardLocation().getCardBounds()
        );
        mainPanel.setComponentZOrder(cardWithAttachments, 0);

    }

    private void layoutAttachements(int startingCardX,
            int maxColumnLevels,
            int ZOrder,
            PermanentView permanentWithAttachmentsView,
            Map<UUID, MageCard> cards,
            BattlefieldPanel battlefieldPanel,
            JLayeredPane mainPanel,
            Rectangle lastAttachmentRect
    ) {
        // put attachments to the next level (take lastAttachmentRect and apply offsets)
        MageCard cardWithAttachments = cards.get(permanentWithAttachmentsView.getId());
        if (cardWithAttachments == null) {
            return;
        }
        int col = getVerticalCul(permanentWithAttachmentsView, battlefieldPanel); // from right to left [2][1][0]
        int currentAttachmentCol = col + 1;
        cardWithAttachments.getLinks().clear();
        int verticalIndex = permanentWithAttachmentsView.getAttachments().size();
        for (UUID attachmentId : permanentWithAttachmentsView.getAttachments()) {
            // put child attachments of the attachment
            PermanentView attachedPermanentView = battlefieldPanel.getBattlefield().get(attachmentId);
            if (attachedPermanentView != null && attachedPermanentView.getAttachments() != null && !attachedPermanentView.getAttachments().isEmpty()) {
                layoutAttachements(startingCardX, maxColumnLevels, ZOrder, attachedPermanentView, cards, battlefieldPanel, mainPanel, lastAttachmentRect);
            }

            // put attachment
            MageCard attachedCard = cards.get(attachmentId);
            if (attachedCard != null) {
                // x position
                Point point = new Point();
                point.setLocation(startingCardX + (maxColumnLevels - currentAttachmentCol) * Math.max(cardWithAttachments.getCardLocation().getCardWidth() / ATTACHMENTS_MAX_COLUMNS, ATTACHMENTS_OFFSET_ALL_X), lastAttachmentRect.getY());
                lastAttachmentRect.setLocation(point);

                // set position first to the same as of the permanent it is attached to
                attachedCard.setCardLocation(lastAttachmentRect.x, lastAttachmentRect.y);

                // y position
                cardWithAttachments.getLinks().add(attachedCard);
                int dyOffset = Math.max(cardWithAttachments.getCardLocation().getCardHeight() / ATTACHMENTS_MAX_COLUMNS, ATTACHMENTS_OFFSET_SINGLE_Y);
                if (verticalIndex == 1) {
                    lastAttachmentRect.translate(Math.max(cardWithAttachments.getCardLocation().getCardWidth() / ATTACHMENTS_MAX_COLUMNS, ATTACHMENTS_OFFSET_ALL_X), dyOffset);
                } else {
                    lastAttachmentRect.translate(0, dyOffset);
                }
                cardWithAttachments.setCardLocation(lastAttachmentRect.x, lastAttachmentRect.y);

                battlefieldPanel.moveToFront(attachedCard);
                battlefieldPanel.moveToFront(cardWithAttachments);
                mainPanel.setComponentZOrder(attachedCard, ZOrder--);
                verticalIndex--;
            }
        }
    }

    private AttachmentLayoutInfos calculateNeededNumberOfVerticalColumns(int currentCol, BattlefieldPanel battlefieldPanel, Map<UUID, MageCard> cards, PermanentView permanentWithAttachmentsView) {
        int maxCol = ++currentCol;
        int attachments = 0;
        for (UUID attachmentId : permanentWithAttachmentsView.getAttachments()) {
            PermanentView attachedPermanent = battlefieldPanel.getBattlefield().get(attachmentId);
            if (attachedPermanent != null) {
                attachments++;
                if (attachedPermanent.getAttachments() != null && !attachedPermanent.getAttachments().isEmpty()) {
                    AttachmentLayoutInfos attachmentLayoutInfos = calculateNeededNumberOfVerticalColumns(currentCol, battlefieldPanel, cards, attachedPermanent);
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
