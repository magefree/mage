package mage.client.util.gui;

import com.google.common.collect.MapMaker;

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

    private static final ArrowBuilder instance;

    static {
        instance = new ArrowBuilder();
    }

    /**
     * Stores arrow panels per game
     */
    private final Map<UUID, JPanel> arrowPanels = new HashMap<>();
    private final Map<UUID, Map<Type, List<Arrow>>> map = new MapMaker().weakKeys().weakValues().makeMap();
    /**
     * The top panel where arrow panels are added to.
     */
    private JPanel arrowsManagerPanel;
    private int currentWidth;
    private int currentHeight;

    public static ArrowBuilder getBuilder() {
        return instance;
    }

    /**
     * Get the panel where all arrows are being drawn.
     *
     * @return
     */
    public JPanel getArrowsManagerPanel() {
        if (arrowsManagerPanel == null) {
            synchronized (ArrowBuilder.class) {
                if (arrowsManagerPanel == null) {
                    arrowsManagerPanel = new JPanel();
                    arrowsManagerPanel.setVisible(true);
                    arrowsManagerPanel.setOpaque(false);
                    arrowsManagerPanel.setLayout(null);
                }
            }
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
        p.add(arrow);
        Map<Type, List<Arrow>> innerMap = map.computeIfAbsent(gameId, k -> new HashMap<>());
        List<Arrow> arrows = innerMap.computeIfAbsent(type, k -> new ArrayList<>());
        arrows.add(arrow);
        p.revalidate();
        p.repaint();
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
     * Removes all arrows from the screen.
     */
    public void removeAllArrows(UUID gameId) {
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

    public void removeArrowsByType(UUID gameId, Type type) {
        if (map.containsKey(gameId)) {
            Map<Type, List<Arrow>> innerMap = map.get(gameId);
            java.util.List<Arrow> arrows = innerMap.get(type);
            if (arrows != null && !arrows.isEmpty()) {
                JPanel p = getArrowsPanel(gameId);
                for (Arrow arrow : arrows) {
                    p.remove(arrow);
                }
                innerMap.put(type, new ArrayList<>());
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

    public enum Type {
        PAIRED, SOURCE, TARGET, COMBAT, ENCHANT_PLAYERS
    }

}
