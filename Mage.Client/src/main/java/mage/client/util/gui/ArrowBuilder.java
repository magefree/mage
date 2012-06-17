package mage.client.util.gui;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Class for dealing with arrows in the game.
 * 
 * @author nantuko
 */
public class ArrowBuilder {
	
	private static JPanel arrowsPanel;
    
    private final static Map<Type, java.util.List<Arrow>> map = new HashMap<Type, java.util.List<Arrow>>();
    
    public enum Type {
        PAIRED, SOURCE, TARGET, COMBAT;
    }

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
	public static void addArrow(int startX, int startY, int endX, int endY, Color color, Type type) {
		JPanel p = getArrowsPanel();
		Arrow arrow = new Arrow();
		arrow.setColor(color);
		arrow.setArrowLocation(startX, startY, endX, endY);
		arrow.setBounds(0, 0, Math.max(startX, endX) + 40, Math.max(startY, endY) + 30); // 30 is offset for arrow heads (being cut otherwise)
        synchronized (map) {
		    p.add(arrow);
            java.util.List<Arrow> arrows = map.get(type);
            if (arrows == null) {
                arrows = new ArrayList<Arrow>();
                map.put(type, arrows);
            }
            arrows.add(arrow);
        }
		p.revalidate();
		p.repaint();
	}
	
	/**
	 * Removes all arrows from the screen.
	 */
	public static void removeAllArrows() {
		JPanel p = getArrowsPanel();
        synchronized (map) {
            if (p.getComponentCount() > 0) {
                p.removeAll();
                p.revalidate();
                p.repaint();
            }
            map.clear();
        }
	}

    public static void removeArrowsByType(Type type) {
        java.util.List<Arrow> arrows = map.get(type);
        if (arrows != null) {
            JPanel p = getArrowsPanel();
            if (arrows.size() > 0) {
                synchronized (map) {
                    for (Arrow arrow : arrows) {
                        p.remove(arrow);
                    }
                    map.put(type, new ArrayList<Arrow>());
                }
                p.revalidate();
                p.repaint();
            }
        }
    }

}
