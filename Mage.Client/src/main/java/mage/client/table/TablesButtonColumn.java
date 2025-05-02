package mage.client.table;

import mage.client.util.GUISizeHelper;

import javax.swing.*;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class TablesButtonColumn extends AbstractCellEditor implements TableCellRenderer, TableCellEditor, ActionListener, MouseListener {

    private final JTable table;
    private final Action action;
    private final JButton renderButton;
    private final JButton editButton;
    private String text;
    private boolean isButtonColumnEditor;

    public TablesButtonColumn(JTable table, Action action, int column) {
        super();
        this.table = table;
        this.action = action;
        renderButton = new JButton();

        editButton = new JButton();
        editButton.setFocusPainted(false);
        editButton.addActionListener(this);
        setGUISize();

        TableColumnModel columnModel = table.getColumnModel();
        columnModel.getColumn(column).setCellRenderer(this);
        columnModel.getColumn(column).setCellEditor(this);
        table.addMouseListener(this);
    }

    public void changeGUISize() {
        setGUISize();
    }

    private void setGUISize() {
        renderButton.setFont(GUISizeHelper.tableFont);
        editButton.setFont(GUISizeHelper.tableFont);
    }

    @Override
    public Object getCellEditorValue() {
        return text;
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        if (hasFocus) {
            renderButton.setForeground(table.getForeground());
            renderButton.setBackground(UIManager.getColor("Button.background"));
        } else if (isSelected) {
            renderButton.setForeground(table.getSelectionForeground());
            renderButton.setBackground(table.getSelectionBackground());
        } else {
            renderButton.setForeground(table.getForeground());
            renderButton.setBackground(UIManager.getColor("Button.background"));
        }

        renderButton.setText((value == null) ? "" : value.toString());
        return renderButton;
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        text = (value == null) ? "" : value.toString();
        editButton.setText(text);
        return editButton;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (table.getRowCount() > 0 && table.getRowCount() >= table.getEditingRow() && table.getEditingRow() >= 0) {
            int row = table.convertRowIndexToModel(table.getEditingRow());
            fireEditingStopped();
            ActionEvent event = new ActionEvent(table, ActionEvent.ACTION_PERFORMED, TablesUtil.getSearchIdFromTable(table, row));
            action.actionPerformed(event);
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (!SwingUtilities.isLeftMouseButton(e)) {
            return;
        }
        if (table.isEditing() && table.getCellEditor() == this) {
            isButtonColumnEditor = true;
        }
    }

    @Override
    public void mouseReleased(MouseEvent arg0) {
        if (isButtonColumnEditor && table.isEditing()) {
            table.getCellEditor().stopCellEditing();
        }

        isButtonColumnEditor = false;
    }

    @Override
    public void mouseClicked(MouseEvent arg0) {
    }

    @Override
    public void mouseEntered(MouseEvent arg0) {
    }

    @Override
    public void mouseExited(MouseEvent arg0) {
    }

}
