package org.mage.card.arcane;

import mage.client.util.GUISizeHelper;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.StringTokenizer;

/**
 * @author JayDi85
 */
public final class ManaSymbolsCellRenderer extends DefaultTableCellRenderer {

    // base panel to render
    private JPanel renderPanel = new JPanel();

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                   boolean hasFocus, int row, int column) {

        // get table text cell settings
        DefaultTableCellRenderer baseRenderer = (DefaultTableCellRenderer) table.getDefaultRenderer(String.class);
        JLabel baseComp = (JLabel) baseRenderer.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

        // apply settings to mana panel from parent
        renderPanel.setOpaque(baseComp.isOpaque());
        renderPanel.setForeground(CardRendererUtils.copyColor(baseComp.getForeground()));
        renderPanel.setBackground(CardRendererUtils.copyColor(baseComp.getBackground()));
        renderPanel.setBorder(baseComp.getBorder());

        // icons size with margin
        int symbolWidth = GUISizeHelper.symbolTableSize;
        int symbolHorizontalMargin = 2;

        // create each mana symbol as child label
        String manaCost = (String) value;
        renderPanel.removeAll();
        renderPanel.setLayout(new BoxLayout(renderPanel, BoxLayout.X_AXIS));
        if (manaCost != null) {
            StringTokenizer tok = new StringTokenizer(manaCost, " ");
            while (tok.hasMoreTokens()) {
                String symbol = tok.nextToken();

                JLabel symbolLabel = new JLabel();
                //symbolLabel.setBorder(new LineBorder(new Color(150, 150, 150))); // debug
                symbolLabel.setBorder(new EmptyBorder(0, symbolHorizontalMargin, 0, 0));

                BufferedImage image = ManaSymbols.getSizedManaSymbol(symbol, symbolWidth);
                if (image != null) {
                    // icon
                    symbolLabel.setIcon(new ImageIcon(image));
                } else {
                    // text
                    symbolLabel.setText("{" + symbol + "}");
                    symbolLabel.setOpaque(baseComp.isOpaque());
                    symbolLabel.setForeground(baseComp.getForeground());
                    symbolLabel.setBackground(baseComp.getBackground());
                }

                renderPanel.add(symbolLabel);
            }
        }

        return renderPanel;
    }
}
