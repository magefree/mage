
package mage.client.util.gui.countryBox;

import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;

 
/**
 * Customer renderer for JComboBox
 * @author www.codejava.net
 *
 */

public class CountryItemRenderer extends JPanel implements ListCellRenderer {
    private final JLabel labelItem = new JLabel();
     
    public CountryItemRenderer() {
        setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.weightx = 1.0;
        constraints.insets = new Insets(0, 0, 0, 0);
         
        labelItem.setOpaque(true);
        labelItem.setHorizontalAlignment(JLabel.LEFT);
         
        add(labelItem, constraints);
        setBackground(Color.LIGHT_GRAY);
    }
     
    @Override
    public Component getListCellRendererComponent(JList list, Object value,
            int index, boolean isSelected, boolean cellHasFocus) {
        String[] countryItem = (String[]) value;
 
        // set country name
        labelItem.setText(countryItem[0]);
         
        // set country flag
        labelItem.setIcon(new ImageIcon(countryItem[1]));
         
        if (isSelected) {
            labelItem.setBackground(Color.BLUE);
            labelItem.setForeground(Color.YELLOW);
        } else {
            labelItem.setForeground(Color.BLACK);
            labelItem.setBackground(Color.LIGHT_GRAY);
        }
         
        return this;
    }
 
}