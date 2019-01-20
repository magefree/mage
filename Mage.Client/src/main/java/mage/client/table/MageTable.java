package mage.client.table;

import mage.client.util.GUISizeHelper;

import javax.swing.*;
import javax.swing.table.JTableHeader;
import java.awt.event.MouseEvent;

/**
 * @author JayDi85
 */
public class MageTable extends JTable {

    private TableInfo tableInfo;

    public MageTable(TableInfo tableInfo) {
        this.tableInfo = tableInfo;
    }

    @Override
    public String getToolTipText(MouseEvent e) {
        // default tooltip for cells
        String tip = null;
        java.awt.Point p = e.getPoint();
        int rowIndex = rowAtPoint(p);
        rowIndex = getRowSorter().convertRowIndexToModel(rowIndex);
        int colIndex = columnAtPoint(p);

        try {
            tip = getValueAt(rowIndex, colIndex).toString();
        } catch (RuntimeException e1) {
            //catch null pointer exception if mouse is over an empty line
        }

        return GUISizeHelper.textToHtmlWithSize(tip, GUISizeHelper.tableFont);
    }

    @Override
    protected JTableHeader createDefaultTableHeader() {
        // default tooltip for headers
        return new JTableHeader(columnModel) {
            public String getToolTipText(MouseEvent e) {
                // html tooltip
                java.awt.Point p = e.getPoint();
                int index = columnModel.getColumnIndexAtX(p.x);
                int realIndex = columnModel.getColumn(index).getModelIndex();

                String tip = tableInfo.getColumnByIndex(realIndex).getHeaderHint();
                if (tip == null) {
                    tip = tableInfo.getColumnByIndex(realIndex).getHeaderName();
                }

                return GUISizeHelper.textToHtmlWithSize(tip, GUISizeHelper.tableFont);
            }
        };
    }
}
