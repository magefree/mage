package mage.client.util.gui;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;

/**
 * Class for dealing with arrows in the game.
 * 
 * @author nantuko
 */
public class ArrowBuilder {
	
	public static JPanel arrowsPanel;

	/**
	 * Get the panel where all arrows are being drawn.
	 * @return
	 */
	public static JPanel getArrowsPanel() {
		if (arrowsPanel == null) {
			synchronized (ArrowBuilder.class) {
				if (arrowsPanel == null) {
					arrowsPanel = new JPanel();
					arrowsPanel.setVisible(true);
					arrowsPanel.setOpaque(false);
					//arrowsPanel.setLayout(new BorderLayout());
					arrowsPanel.setLayout(null);
					//arrowsPanel.setBorder(BorderFactory.createLineBorder(Color.red));
				}
			}
		}
		return arrowsPanel;
	}
	
	/**
	 * Not synchronized method for arrows panel.
	 * Doesn't create JPanel in case the panel doesn't exist.
	 * Works faster.
	 * 
	 * @return
	 */
	public static JPanel getArrowsPanelRef() {
		return arrowsPanel;
	}

	/**
	 * Adds new arrow.
	 * 
	 * @param startX
	 * @param startY
	 * @param endX
	 * @param endY
	 * @param color
	 */
	public static void addArrow(int startX, int startY, int endX, int endY, Color color) {
		JPanel p = getArrowsPanel();
		Arrow arrow = new Arrow();
		arrow.setColor(color);
		arrow.setArrowLocation(startX, startY, endX, endY);
		arrow.setBounds(0, 0, Math.max(startX, endX) + 30, Math.max(startY, endY) + 30); // 30 is offset for arrow heads (being cut otherwise)
		//System.out.println("	Adding arrow (startX=" + startX + ", startY=" + startY +", endX=" + endX + ", endY=" + endY + ")");
		p.add(arrow);
		p.revalidate();
		p.repaint();
	}
	
	/**
	 * Removes all arrows from the screen.
	 */
	public static void removeAllArrows() {
		JPanel p = getArrowsPanel();
		if (p.getComponentCount() > 0) {
			p.removeAll();
			p.revalidate();
			p.repaint();
		}
	}

}
