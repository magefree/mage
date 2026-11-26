package mage.client.components;

import java.awt.Color;
import javax.swing.SwingUtilities;
import mage.client.util.GUISizeHelper;
import org.mage.card.arcane.ManaSymbols;

/**
 * GUI: component for displaying text in feedback panel. Support html, mana symbols and popups.
 *
 * @author nantuko
 */
public class MageTextArea extends MageEditorPane {

    private String currentText;
    private int currentPanelWidth;
    private int currentFontSize;
    private Color currentColor;

    public MageTextArea() {
        super();
        setEditable(false);
        setBackground(new Color(0, 0, 0, 0)); // transparent background
        setForeground(Color.WHITE);
        setFocusable(false);
        enableHyperlinksAndCardPopups();
    }

    @Override
    public void setText(String text) {
        setText(text, 0);
    }

    public void setText(String text, final int panelWidth) {
        if (text == null) {
            return;
        }
        // Keep the cache, formatting and Swing updates on the same event thread.
        if (SwingUtilities.isEventDispatchThread()) {
            updateText(text, panelWidth);
        } else {
            SwingUtilities.invokeLater(() -> updateText(text, panelWidth));
        }
    }

    private void updateText(String text, int panelWidth) {
        int fontSize = GUISizeHelper.gameFeedbackPanelMainMessageFontSize;
        Color textColor = getForeground();
        if (textColor == null) {
            textColor = Color.WHITE;
        }
        if (text.equals(currentText) && panelWidth == currentPanelWidth
                && currentFontSize == fontSize && textColor.equals(currentColor)) {
            return;
        }

        currentText = text;
        currentPanelWidth = panelWidth;
        currentFontSize = fontSize;
        currentColor = textColor;

        // prepare text format as header and details texts

        final StringBuilder buffer = new StringBuilder();
        // Dialog is a java logical font family, so it should work on all systems
        buffer.append("<body style='font-family:Dialog;font-size:");
        buffer.append(fontSize);
        buffer.append("pt;margin:3px 3px 3px 3px;color: ");
        buffer.append(String.format("#%02x%02x%02x", currentColor.getRed(), currentColor.getGreen(), currentColor.getBlue()));
        buffer.append("'><b><center>");

        // Don't know what it does (easy italc?) but it bugs with multiple #HTML color codes (LevelX2)
        //text = text.replaceAll("#([^#]+)#", "<i>$1</i>");
        //text = text.replaceAll("\\s*//\\s*", "<hr width='50%'>");
        text = text.replace("\r\n", "<div style='font-size:5pt'></div>");

        final String basicText = ManaSymbols.replaceSymbolsWithHTML(text, ManaSymbols.Type.DIALOG);
        if (!text.isEmpty()) {
            buffer.append(basicText);
        }
        buffer.append("</b></center></body></html>");

        super.setText(buffer.toString());
        // in case the text don't fit in the panel a tooltip with the text is added
        if (panelWidth > 0 && getPreferredSize().getWidth() > panelWidth) {
            String tooltip = "<html><center><body style='font-family:Dialog;font-size:"
                    + fontSize
                    + "pt;color: #FFFFFF'><p width='500'>" + basicText + "</p></body></html>";
            super.setToolTipText(tooltip);
        } else {
            super.setToolTipText(null);
        }
        setCaretPosition(0);
    }
}
