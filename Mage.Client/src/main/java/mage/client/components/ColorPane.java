package mage.client.components;

import javax.swing.*;
import javax.swing.text.AttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import java.awt.*;

/**
 * Enhanced {@link JTextPane} with text highlighting support.
 *
 * @author nantuko
 */
public class ColorPane extends JTextPane {

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

    public void append(Color color, String s) {
		if (color == null) {
			return;
		}

        try {
	    	setEditable(true);
	        
	        StyleContext sc = StyleContext.getDefaultStyleContext();
	        AttributeSet aset = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, color);

	        int len = getDocument().getLength();

	        setCaretPosition(len);
	        setCharacterAttributes(aset, false);
	        replaceSelection(s);

			setEditable(false);
        } catch (Exception e) {
        	e.printStackTrace();
        }
    }

	/**
	 * A little trick to paint black background under the text.
	 *
	 * @param g
	 */
	public void paintChildren(Graphics g) {
		super.paintComponent(g);
	}

	/**
	 * A little trick to paint black background under the text.
	 *
	 * @param g
	 */
	public void paintComponent(Graphics g) {
		super.paintChildren(g);
	}

}