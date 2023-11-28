package mage.client.components;

import java.awt.Color;
import javax.swing.JEditorPane;
import javax.swing.SwingUtilities;
import mage.client.util.GUISizeHelper;
import org.mage.card.arcane.ManaSymbols;
import org.mage.card.arcane.UI;

/**
 * GUI: component for displaying text in feedback panel. Support html, mana symbols and popups.
 *
 * @author nantuko
 */
public class MageTextArea extends MageEditorPane {

    private String currentText;
    private int currentPanelWidth;

    public MageTextArea() {
        super();
        setEditable(false);
        setBackground(new Color(0, 0, 0, 0)); // transparent background
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
        if (text.equals(currentText) && panelWidth == currentPanelWidth) {
            return;
        }

        currentText = text;
        currentPanelWidth = panelWidth;

        // prepare text format as header and details texts

        final StringBuilder buffer = new StringBuilder(512);
        // Dialog is a java logical font family, so it should work on all systems
        buffer.append("<body style='font-family:Dialog;font-size:");
        buffer.append(GUISizeHelper.gameDialogAreaFontSizeBig);
        buffer.append("pt;margin:3px 3px 3px 3px;color: #FFFFFF'><b><center>");

        // Don't know what it does (easy italc?) but it bugs with multiple #HTML color codes (LevelX2)
        //text = text.replaceAll("#([^#]+)#", "<i>$1</i>");
        //text = text.replaceAll("\\s*//\\s*", "<hr width='50%'>");
        text = text.replace("\r\n", "<div style='font-size:5pt'></div>");

        final String basicText = ManaSymbols.replaceSymbolsWithHTML(text, ManaSymbols.Type.DIALOG);
        if (!text.isEmpty()) {
            buffer.append(basicText);
        }
        buffer.append("</b></center></body></html>");

        SwingUtilities.invokeLater(() -> {
            String promptText = buffer.toString();
            MageTextArea.super.setText(promptText);
            // in case the text don't fit in the panel a tooltip with the text is added
            if (panelWidth > 0 && MageTextArea.this.getPreferredSize().getWidth() > panelWidth) {
                String tooltip = "<html><center><body style='font-family:Dialog;font-size:"
                        + GUISizeHelper.gameDialogAreaFontSizeBig
                        + ";color: #FFFFFF'><p width='500'>" + basicText + "</p></body></html>";
                MageTextArea.super.setToolTipText(tooltip);
            } else {
                MageTextArea.super.setToolTipText(null);
            }
            setCaretPosition(0);
        });
    }
}
