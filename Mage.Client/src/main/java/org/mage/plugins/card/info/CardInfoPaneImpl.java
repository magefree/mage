package org.mage.plugins.card.info;

import java.awt.Component;
import javax.swing.JEditorPane;
import javax.swing.SwingUtilities;
import mage.client.util.GUISizeHelper;
import mage.client.util.gui.GuiDisplayUtil;
import mage.client.util.gui.GuiDisplayUtil.TextLines;
import mage.components.CardInfoPane;
import mage.view.CardView;
import org.mage.card.arcane.UI;

/**
 * Card info pane for displaying card rules. Supports drawing mana symbols.
 *
 * @author nantuko
 */
public class CardInfoPaneImpl extends JEditorPane implements CardInfoPane {

    public static final int TOOLTIP_WIDTH_MIN = 160;

    public static final int TOOLTIP_HEIGHT_MIN = 120;
    public static final int TOOLTIP_HEIGHT_MAX = 300;

    public static final int TOOLTIP_BORDER_WIDTH = 80;

    private CardView currentCard;
    private int type;

    private int addWidth;
    private int addHeight;
    private boolean setSize = false;

    public CardInfoPaneImpl() {
        UI.setHTMLEditorKit(this);
        setEditable(false);
        setGUISize();
    }

    public void changeGUISize() {
        setGUISize();
        this.revalidate();
        this.repaint();
    }

    private void setGUISize() {
        addWidth = 20 * GUISizeHelper.cardTooltipFontSize - 50;
        addHeight = 12 * GUISizeHelper.cardTooltipFontSize - 20;
        setSize = true;
    }

    @Override
    public void setCard(final CardView card, final Component container) {
        currentCard = card;

        try {
            SwingUtilities.invokeLater(() -> {
                TextLines textLines = GuiDisplayUtil.getTextLinesfromCardView(card);
                StringBuilder buffer = GuiDisplayUtil.getRulefromCardView(card, textLines);
                resizeTooltipIfNeeded(container, textLines.basicTextLength, textLines.lines.size());
                setText(buffer.toString());
                setCaretPosition(0);
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void resizeTooltipIfNeeded(Component container, int ruleLength, int rules) {
        if (container == null) {
            return;
        }
        boolean makeBig = (rules > 5 || ruleLength > 350);
        if (setSize) {
            if (makeBig) {
                type = 0;
            } else {
                type = 1;
            }
        }
        if (makeBig && type == 0) {
            type = 1;
            container.setSize(
                    addWidth + TOOLTIP_WIDTH_MIN + TOOLTIP_BORDER_WIDTH,
                    addHeight + TOOLTIP_HEIGHT_MAX + TOOLTIP_BORDER_WIDTH
            );
            this.setSize(addWidth + TOOLTIP_WIDTH_MIN,
                    addHeight + TOOLTIP_HEIGHT_MAX);
        } else if (!makeBig && type == 1) {
            type = 0;
            container.setSize(
                    addWidth + TOOLTIP_WIDTH_MIN + TOOLTIP_BORDER_WIDTH,
                    addHeight + TOOLTIP_HEIGHT_MIN + TOOLTIP_BORDER_WIDTH
            );
            this.setSize(addWidth + TOOLTIP_WIDTH_MIN,
                    addHeight + TOOLTIP_HEIGHT_MIN);
        }
    }

    @Override
    public boolean isCurrentCard(CardView card) {
        return currentCard != null && currentCard.equals(card);
    }
}
