package mage.client.components.ext;

import org.mage.card.arcane.UI;

import javax.swing.*;
import java.awt.*;

/**
 * @author noxx
 */
public class MageFloatPane extends JEditorPane {

    public MageFloatPane() {
        UI.setHTMLEditorKit(this);
        setEditable(false);
        setBackground(Color.white);
        JButton jb = new JButton("Done");
        jb.setLocation(50, 50);
        jb.setSize(100, 50);
        add(jb);
    }

    public void setCard(final String text) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                setText(text);
                setCaretPosition(0);
            }
        });
    }
}