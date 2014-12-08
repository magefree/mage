package org.mage.plugins.card.info;


import java.awt.Color;
import java.awt.Component;
import javax.swing.JEditorPane;
import javax.swing.SwingUtilities;
import mage.client.util.gui.GuiDisplayUtil;
import mage.client.util.gui.GuiDisplayUtil.TextLines;
import mage.components.CardInfoPane;
import mage.utils.ThreadUtils;
import mage.view.CardView;
import org.mage.card.arcane.UI;

/**
 * Card info pane for displaying card rules.
 * Supports drawing mana symbols.
 *
 * @author nantuko
 */
public class CardInfoPaneImpl extends JEditorPane implements CardInfoPane {

    private CardView currentCard;
    private int type;

    public CardInfoPaneImpl() {
        UI.setHTMLEditorKit(this);
        setEditable(false);
        setBackground(Color.white);
    }

    @Override
    public void setCard(final CardView card, final Component container) {
        if (card == null || isCurrentCard(card)) {
            return;
        }
        currentCard = card;

        ThreadUtils.threadPool.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    if (!card.equals(currentCard)) {
                        return;
                    }                    
                    
                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            if (!card.equals(currentCard)) {
                                return;
                            }
                            TextLines textLines = GuiDisplayUtil.getTextLinesfromCardView(card);
                            StringBuilder buffer = GuiDisplayUtil.getRulefromCardView(card, textLines);
                            resizeTooltipIfNeeded(container, textLines.basicTextLength, textLines.lines.size());
                            setText(buffer.toString());
                            setCaretPosition(0);
                        }
                    });

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void resizeTooltipIfNeeded(Component container, int ruleLength, int rules) {
        if (container == null) {
            return;
        }
        boolean makeBig = (rules > 5 || ruleLength > 350);
        if (makeBig && type == 0) {
            type = 1;
            container.setSize(
                org.mage.plugins.card.constants.Constants.TOOLTIP_WIDTH_MIN + org.mage.plugins.card.constants.Constants.TOOLTIP_BORDER_WIDTH,
                org.mage.plugins.card.constants.Constants.TOOLTIP_HEIGHT_MAX + org.mage.plugins.card.constants.Constants.TOOLTIP_BORDER_WIDTH
            );
            this.setSize(org.mage.plugins.card.constants.Constants.TOOLTIP_WIDTH_MIN, org.mage.plugins.card.constants.Constants.TOOLTIP_HEIGHT_MAX);
        } else if (!makeBig && type == 1) {
            type = 0;
            container.setSize(
                    org.mage.plugins.card.constants.Constants.TOOLTIP_WIDTH_MIN + org.mage.plugins.card.constants.Constants.TOOLTIP_BORDER_WIDTH,
                    org.mage.plugins.card.constants.Constants.TOOLTIP_HEIGHT_MIN + org.mage.plugins.card.constants.Constants.TOOLTIP_BORDER_WIDTH
            );
            this.setSize(org.mage.plugins.card.constants.Constants.TOOLTIP_WIDTH_MIN, org.mage.plugins.card.constants.Constants.TOOLTIP_HEIGHT_MIN);
        }
    }

    @Override
    public boolean isCurrentCard(CardView card) {
        return currentCard != null && card.equals(currentCard);
    }
}
