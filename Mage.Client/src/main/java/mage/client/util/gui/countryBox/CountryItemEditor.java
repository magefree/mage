
package mage.client.util.gui.countryBox;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicComboBoxEditor;


/**
 * Editor for JComboBox
 * @author wwww.codejava.net, JayDi85
 *
 */
public class CountryItemEditor extends BasicComboBoxEditor {
    private final JPanel panel = new JPanel();
    private final JLabel labelItem = new JLabel();
    private String[] editValue = new String[2];
     
    public CountryItemEditor() {
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        panel.setBackground(new Color(0, 100,190, 255));

        panel.add(labelItem);
        labelItem.setAlignmentX(Component.LEFT_ALIGNMENT);
        labelItem.setMaximumSize(new Dimension(Short.MAX_VALUE, Short.MAX_VALUE));
        labelItem.setBorder(new EmptyBorder(0, 5, 0, 0));
        labelItem.setOpaque(false);
        labelItem.setForeground(Color.WHITE);

        editValue = null;
    }
     
    @Override
    public Component getEditorComponent() {
        return this.panel;
    }
     
    @Override
    public Object getItem() {
        return this.editValue;
    }
     
    public String getImageItem() {
        return this.editValue[1];
    }

    @Override
    public void setItem(Object item) {
        if (!(item instanceof String[])) {
            return;
        }

        String[] newItem = (String[])item;

        editValue = new String[2];
        editValue[0] = newItem[0];
        editValue[1] = newItem[1];

        labelItem.setText(editValue[0]);
        labelItem.setIcon(new ImageIcon(getClass().getResource("/flags/"+ editValue[1] + ".png")));
    }  
}
