package mage.client.util.gui;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * Class for dealing with arrows in the game.
 *
 * @author nantuko, noxx
 */
public class ArrowBuilder {

    private static ArrowBuilder instance;

    static {
        instance = new ArrowBuilder();
    }

    public static ArrowBuilder getBuilder() {
        return instance;
    }

    /**
     * The top panel where arrow panels are added to.
     */
    private JPanel arrowsManagerPanel;

    /**
     * Stores arrow panels per game
     */
    private final Map<UUID, JPanel> arrowPanels = new HashMap<>();

    private final Map<UUID, Map<Type, List<Arrow>>> map = new HashMap<>();

    private int currentWidth;
    private int currentHeight;

    public enum Type {
        PAIRED, BANDED, SOURCE, TARGET, COMBAT, ENCHANT_PLAYERS
    }

    /**
     * Get the panel where all arrows are being drawn.
     * @return
     */
    public synchronized JPanel getArrowsManagerPanel() {
        if (arrowsManagerPanel == null) {
            arrowsManagerPanel = new JPanel();
            arrowsManagerPanel.setVisible(true);
            arrowsManagerPanel.setOpaque(false);
            arrowsManagerPanel.setLayout(null);
        }
        return arrowsManagerPanel;
    }

    private JPanel getArrowsPanel(UUID gameId) {
        if (!arrowPanels.containsKey(gameId)) {
            JPanel arrowPanel = new JPanel();
            arrowPanel.setVisible(true);
            arrowPanel.setOpaque(false);
            arrowPanel.setLayout(null);
            arrowPanel.setSize(currentWidth, currentHeight);
            arrowPanels.put(gameId, arrowPanel);
            getArrowsManagerPanel().add(arrowPanel);
            return arrowPanel;
        }
        return arrowPanels.get(gameId);
    }

    /**
     * Not synchronized method for arrows panel.
     * Doesn't create JPanel in case the panel doesn't exist.
     * Works faster.
     *
     * @return
     */
    /*public JPanel getPanelRef() {
        return arrowsManagerPanel;
    }*/

    /**
     * Adds new arrow.
     *
     * @param startX
     * @param startY
     * @param endX
     * @param endY
     * @param color
     */
    public void addArrow(UUID gameId, int startX, int startY, int endX, int endY, Color color, Type type) {
        JPanel p = getArrowsPanel(gameId);

        Arrow arrow = new Arrow();
        arrow.setColor(color);
        arrow.setArrowLocation(startX, startY, endX, endY);
        arrow.setBounds(0, 0, Math.max(startX, endX) + 40, Math.max(startY, endY) + 30); // 30 is offset for arrow heads (being cut otherwise)

        synchronized (map) {
            p.add(arrow);
            Map<Type, java.util.List<Arrow>> innerMap = map.computeIfAbsent(gameId, k -> new HashMap<>());
            java.util.List<Arrow> arrows = innerMap.computeIfAbsent(type, k -> new ArrayList<>());
            arrows.add(arrow);
        }

        p.revalidate();
        p.repaint();
    }

    /**
     * Removes all arrows from the screen.
     */
    public void removeAllArrows(UUID gameId) {
        synchronized (map) {
            if (map.containsKey(gameId)) {
                Map<Type, List<Arrow>> innerMap = map.get(gameId);
                JPanel p = getArrowsPanel(gameId);

                if (p != null && p.getComponentCount() > 0) {
                    p.removeAll();
                    p.revalidate();
                    p.repaint();
                }
                innerMap.clear();
                map.remove(gameId);
            }
        }
    }

    public void removeArrowsByType(UUID gameId, Type type) {
        if (map.containsKey(gameId)) {
            Map<Type, List<Arrow>> innerMap = map.get(gameId);
            java.util.List<Arrow> arrows = innerMap.get(type);
            if (arrows != null && !arrows.isEmpty()) {
                JPanel p = getArrowsPanel(gameId);
                synchronized (map) {
                    for (Arrow arrow : arrows) {
                        p.remove(arrow);
                    }
                    innerMap.put(type, new ArrayList<>());
                }
                p.revalidate();
                p.repaint();
            }
        }
    }

    public void setSize(int width, int height) {
        this.currentWidth = width;
        this.currentHeight = height;
        if (arrowsManagerPanel != null) {
            arrowsManagerPanel.setSize(width, height);
        }
        for (JPanel arrowPanel : arrowPanels.values()) {
            arrowPanel.setSize(width, height);
            arrowPanel.repaint();
        }
    }

    public void hideAllPanels() {
        for (JPanel arrowPanel : arrowPanels.values()) {
            arrowPanel.setVisible(false);
        }
    }

    public void showPanel(UUID gameId) {
        if (arrowPanels.containsKey(gameId)) {
            arrowPanels.get(gameId).setVisible(true);
        }
    }

}
