
package mage.client.util.gui;

import java.awt.Component;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.util.EventObject;
import javax.swing.BorderFactory;
import javax.swing.DefaultCellEditor;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;
import mage.client.cards.CardsList;

/**
 *
 * @author LevelX2
 */
public class TableSpinnerEditor extends DefaultCellEditor {

    final JSpinner spinner;
    final JSpinner.DefaultEditor editor;
    final JTextField textField;
    boolean valueSet;
    private JTable table;
    private int lastRow = -1;
    private int currentRow = -1;
    private int lastOriginalHeigh;
    private int currentOriginalHeigh;
    private static final int NEEDED_HIGH = 24;
    final CardsList cardsList;

    // Initializes the spinner.
    public TableSpinnerEditor(CardsList cardsList) {
        super(new JTextField());
        this.cardsList = cardsList;
        spinner = new JSpinner();
        spinner.setBorder(BorderFactory.createEmptyBorder());
        spinner.setModel(new SpinnerNumberModel(0,0,999,1));
        editor = ((JSpinner.DefaultEditor) spinner.getEditor());
        textField = editor.getTextField();
        textField.setHorizontalAlignment(JTextField.LEFT);
        textField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent fe) {
                lastOriginalHeigh = currentOriginalHeigh;
                currentOriginalHeigh = 0;
                lastRow = currentRow;
                currentRow = -1;
                if (lastOriginalHeigh < NEEDED_HIGH) {
                    table.setRowHeight(lastRow, NEEDED_HIGH);
                }

                SwingUtilities.invokeLater(() -> {
                    if (valueSet) {
                        textField.setCaretPosition(1);
                    }
                });
            }

            @Override
            public void focusLost(FocusEvent fe) {
                resetRow();
                if (currentRow < 0) {
                    stopCellEditing();
                }                
            }
        });
        textField.addActionListener(ae -> stopCellEditing());
    }

    private synchronized void resetRow() {
        if (lastRow >= 0) {
            cardsList.handleSetNumber((Integer) spinner.getValue());
            table.setRowHeight(lastRow, lastOriginalHeigh);
            lastRow = -1;
        }
    }
    
    // Prepares the spinner component and returns it.
    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        if (this.table == null) {
            this.table = table;
        }
        currentOriginalHeigh = table.getRowHeight(row);
        currentRow = row;
        
        if (!valueSet) {
            spinner.setValue(value);
        }
        SwingUtilities.invokeLater(() -> textField.requestFocus());
        return spinner;
    }

    @Override
    public boolean isCellEditable(EventObject eo) {
        if (eo instanceof KeyEvent) {
            KeyEvent ke = (KeyEvent) eo;
            textField.setText(String.valueOf(ke.getKeyChar()));
            valueSet = true;
        } else {
            valueSet = false;
        }
        return true;
    }

    // Returns the spinners current value.
    @Override
    public Object getCellEditorValue() {
        return spinner.getValue();
    }

    @Override
    public boolean stopCellEditing() {
        try {
            editor.commitEdit();
            spinner.commitEdit();            
            resetRow();

        } catch (java.text.ParseException e) {
            JOptionPane.showMessageDialog(null,
                    "Invalid value, discarding.");
        }
        return super.stopCellEditing();
    }
}
