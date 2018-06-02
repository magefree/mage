
package mage.client.util.gui.countryBox;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

/**
 *
 * @author LevelX2
 */
public class MyComboBoxRenderer extends JLabel implements ListCellRenderer {
 
    public MyComboBoxRenderer() {
        setOpaque(true);
        setFont(new Font("Arial", Font.BOLD /*| Font.ITALIC*/, 14));
        setBackground(Color.BLUE);
        setForeground(Color.YELLOW);
    }
     
    @Override
    public Component getListCellRendererComponent(JList list, Object value,
            int index, boolean isSelected, boolean cellHasFocus) {

        String[] val = (String[]) value;
        setText(val[0]);

        return this;
    }
}
