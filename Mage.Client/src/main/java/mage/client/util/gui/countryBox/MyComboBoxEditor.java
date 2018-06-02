
package mage.client.util.gui.countryBox;

import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.plaf.basic.BasicComboBoxEditor;

/**
 *
 * @author LevelX2
 */

public class MyComboBoxEditor extends BasicComboBoxEditor {
    private final JLabel label;
    private final JPanel panel;
    private Object selectedItem;
     
    public MyComboBoxEditor() {
        label = new JLabel();
        panel = new JPanel();
                 
        label.setOpaque(false);
        label.setFont(new Font("Arial", Font.BOLD, 14));
        label.setForeground(Color.BLACK);
         
        panel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 2));
        panel.add(label);
        panel.setBackground(Color.BLUE);
    }
     
    @Override
    public Component getEditorComponent() {
        return this.panel;
    }
     
    @Override
    public Object getItem() {
        return '[' + this.selectedItem.toString() + ']';
    }
     
    @Override
    public void setItem(Object item) {
        this.selectedItem = item;
        label.setText(item.toString());
    }
     
}