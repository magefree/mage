/*
 *  Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
 * 
 *  Redistribution and use in source and binary forms, with or without modification, are
 *  permitted provided that the following conditions are met:
 * 
 *     1. Redistributions of source code must retain the above copyright notice, this list of
 *        conditions and the following disclaimer.
 * 
 *     2. Redistributions in binary form must reproduce the above copyright notice, this list
 *        of conditions and the following disclaimer in the documentation and/or other materials
 *        provided with the distribution.
 * 
 *  THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
 *  WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 *  FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
 *  CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 *  CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 *  SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 *  ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 *  NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 *  ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 * 
 *  The views and conclusions contained in the software and documentation are those of the
 *  authors and should not be interpreted as representing official policies, either expressed
 *  or implied, of BetaSteward_at_googlemail.com.
 */
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
