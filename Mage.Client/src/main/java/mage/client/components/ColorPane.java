package mage.client.components;

import javax.swing.*;
import java.awt.*;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;

/**
 * Enhanced {@link JTextPane} with text highlighting support.
 *
 * @author nantuko
 */
public class ColorPane extends JTextPane {

    HTMLEditorKit kit = new HTMLEditorKit();
    HTMLDocument doc = new HTMLDocument();
    
    public ColorPane() {
        this.setEditorKit(kit);
        this.setDocument(doc);        
    }
    /**
     * This method solves the known issue with Nimbus LAF background transparency and background color.
     * @param color
     */
    public void setExtBackgroundColor(Color color) {
        setBackground(new Color(0,0,0,0));
        JPanel jPanel = new JPanel();
        jPanel.setBackground(color);
        setLayout(new BorderLayout());
        add(jPanel);
    }

    @Override
    public void setText(String string) {
        super.setText(string); //To change body of generated methods, choose Tools | Templates.
    }

    public void append(String s) {
        try {
            kit.insertHTML(doc, doc.getLength(), s, 0, 0, null);
            
//            setEditable(true);
//            
//            StyleContext sc = StyleContext.getDefaultStyleContext();
//            AttributeSet aset = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, color);
//
//            int len = getDocument().getLength();
//
//            setCaretPosition(len);
//            setCharacterAttributes(aset, false);
//            replaceSelection(s);
//
//            setEditable(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * A little trick to paint black background under the text.
     *
     * @param g
     */
    @Override
    public void paintChildren(Graphics g) {
        super.paintComponent(g);
    }

    /**
     * A little trick to paint black background under the text.
     *
     * @param g
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintChildren(g);
    }

}