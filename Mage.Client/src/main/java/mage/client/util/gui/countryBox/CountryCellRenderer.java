
package mage.client.util.gui.countryBox;

import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author LevelX2
 */
public class CountryCellRenderer extends DefaultTableCellRenderer {

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        if (value == null || ((String) value).isEmpty()) {
            value = "world";
        }
        label.setToolTipText(CountryUtil.getCountryName((String) value));
        label.setIcon(CountryUtil.getCountryFlagIcon((String) value));
        label.setText("");
        return label;
    }
}
