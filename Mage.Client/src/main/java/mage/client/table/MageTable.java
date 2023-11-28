package mage.client.table;

import mage.client.util.GUISizeHelper;
import org.apache.log4j.Logger;

import javax.swing.*;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import java.awt.event.MouseEvent;

/**
 * GUI: basic mage table for any data like game tables list, players list, etc
 *
 * @author JayDi85
 */
public class MageTable extends JTable {

    private static final Logger logger = Logger.getLogger(MageTable.class);
    private TableInfo tableInfo;

    public MageTable() {
        this(null);
    }

    public MageTable(TableInfo tableInfo) {
        this.tableInfo = tableInfo;
    }
    
    public void setTableInfo(TableInfo tableInfo) {
        this.tableInfo = tableInfo;
    }

    @Override
    public String getToolTipText(MouseEvent e) {
        // default tooltip for cells
        java.awt.Point p = e.getPoint();
        int viewRow = rowAtPoint(p);
        int viewCol = columnAtPoint(p);
        int modelRow = TablesUtil.getModelRowFromView(this, viewRow);
        int modelCol = this.convertColumnIndexToModel(viewCol);
        String tip = null;
        if (modelRow != -1 && modelCol != -1) {
            TableModel model = this.getModel();
            if (model instanceof TableModelWithTooltip) {
                tip = ((TableModelWithTooltip) model).getTooltipAt(modelRow, modelCol);
            } else {
                tip = model.getValueAt(modelRow, modelCol).toString();
            }
        }
        return GUISizeHelper.textToHtmlWithSize(tip, GUISizeHelper.tableFont);
    }

    @Override
    protected JTableHeader createDefaultTableHeader() {
        // default tooltip for headers
        return new JTableHeader(columnModel) {
            @Override
            public String getToolTipText(MouseEvent e) {
                // html tooltip
                java.awt.Point p = e.getPoint();
                int colIndex = columnModel.getColumnIndexAtX(p.x);
                TableColumn col = columnModel.getColumn(colIndex);
                if (colIndex < 0) {
                    return "";
                }
                int realIndex = col.getModelIndex();

                String tip;
                if (tableInfo != null) {
                    // custom hint from table info
                    tip = tableInfo.getColumnByIndex(realIndex).getHeaderHint();
                    if (tip == null) {
                        tip = tableInfo.getColumnByIndex(realIndex).getHeaderName();
                    }
                } else {
                    // default hint from header
                    tip = col.getHeaderValue().toString();
                }

                return GUISizeHelper.textToHtmlWithSize(tip, GUISizeHelper.tableFont);
            }
        };
    }
}
