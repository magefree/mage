package mage.client.components;

import java.awt.Color;
import javax.swing.JEditorPane;
import javax.swing.SwingUtilities;
import org.mage.card.arcane.ManaSymbols;
import org.mage.card.arcane.UI;

/**
 * Component for displaying text in mage. Supports drawing mana symbols.
 *
 * @author nantuko
 */
public class MageTextArea extends JEditorPane {

    public MageTextArea() {
        UI.setHTMLEditorKit(this);
        setEditable(false);
        setBackground(new Color(0, 0, 0, 0)); // transparent background
        setFocusable(false);
        // setBorder(BorderFactory.createLineBorder(Color.red));
        // setSelectionColor(new Color(0, 0, 0, 0));
    }

    @Override
    public void setText(String text) {
        setText(text, 0);
    }

    public void setText(String text, final int panelWidth) {
        if (text == null) {
            return;
        }

        final StringBuilder buffer = new StringBuilder(512);
        // Dialog is a java logical font family, so it should work on all systems
        buffer.append("<html><body style='font-family:Dialog;font-size:");
        buffer.append(16);
        buffer.append("pt;margin:3px 3px 3px 3px;color: #FFFFFF'><b><center>");

        // Don't know what it does (easy italc?) but it bugs with multiple #HTML color codes (LevelX2)
        //text = text.replaceAll("#([^#]+)#", "<i>$1</i>");
        //text = text.replaceAll("\\s*//\\s*", "<hr width='50%'>");
        text = text.replace("\r\n", "<div style='font-size:5pt'></div>");

        final String basicText = ManaSymbols.replaceSymbolsWithHTML(text, ManaSymbols.Type.PAY);
        if (text.length() > 0) {
            buffer.append(basicText);
        }

        buffer.append("</b></center></body></html>");

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                String promptText = buffer.toString();
                MageTextArea.super.setText(promptText);
                // in case the text don't fit in the panel a tooltip with the text is added
                if (panelWidth > 0 && MageTextArea.this.getPreferredSize().getWidth() > panelWidth) {
//                    String tooltip = promptText
//                            .replace("color: #FFFFFF'>", "color: #111111'><p width='400'>")
//                            .replace("</body>", "</p></body>");
                    String tooltip = "<html><center><body style='font-family:Dialog;font-size:14;color: #FFFFFF'><p width='500'>" + basicText + "</p></body></html>";
                    MageTextArea.super.setToolTipText(tooltip);
                } else {
                    MageTextArea.super.setToolTipText(null);
                }
                setCaretPosition(0);
            }
        });
    }
}
