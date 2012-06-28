package mage.client.components;

import org.mage.card.arcane.ManaSymbols;
import org.mage.card.arcane.UI;

import javax.swing.*;
import java.awt.*;

/**
 * Component for displaying text in mage.
 * Supports drawing mana symbols.
 *
 * @author nantuko
 */
public class MageTextArea extends JEditorPane {

    public MageTextArea() {
        UI.setHTMLEditorKit(this);
        setEditable(false);
        setBackground(new Color(0, 0, 0, 0)); // transparent background
        setFocusable(false);
        //setBorder(BorderFactory.createLineBorder(Color.red));
        //setSelectionColor(new Color(0, 0, 0, 0));
    }

    public void setText(String text) {
        setText(text, 16);
    }

    public void setText(String text, int fontSize) {
        if (text == null) return;

        String fontFamily = "times";

        final StringBuilder buffer = new StringBuilder(512);
        buffer.append("<html><body style='font-family:");
        buffer.append(fontFamily);
        buffer.append(";font-size:");
        buffer.append(fontSize);
        buffer.append("pt;margin:3px 3px 3px 3px;color: #FFFFFF'><b><center>");

        text = text.replaceAll("#([^#]+)#", "<i>$1</i>");
        text = text.replaceAll("\\s*//\\s*", "<hr width='50%'>");
        text = text.replace("\r\n", "<div style='font-size:5pt'></div>");
        //text += "<br>";

        if (text.length() > 0) {
            //buffer.append("<br>");
            //text = text.replaceAll("\\{this\\}", card.getName());
            //text = text.replaceAll("\\{source\\}", card.getName());
            buffer.append(ManaSymbols.replaceSymbolsWithHTML(text, ManaSymbols.Type.PAY));
        }

        buffer.append("</b></center></body></html>");

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                MageTextArea.super.setText(buffer.toString());
                //System.out.println(buffer.toString());
                setCaretPosition(0);
            }
        });
    }
}
