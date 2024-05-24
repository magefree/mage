package mage.components.table;

import org.apache.log4j.Logger;
import org.ocpsoft.prettytime.PrettyTime;
import org.ocpsoft.prettytime.TimeFormat;
import org.ocpsoft.prettytime.units.JustNow;

import javax.swing.*;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.Locale;

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
        int modelRow = getModelRowFromView(this, viewRow);
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
        return textToHtmlWithSize(tip, this.getFont());
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

                return textToHtmlWithSize(tip, MageTable.this.getFont());
            }
        };
    }

    public static int getSelectedModelRow(JTable table) {
        return getModelRowFromView(table, table.getSelectedRow());
    }

    public static int getModelRowFromView(JTable table, int viewRow) {
        if (viewRow != -1 && viewRow < table.getModel().getRowCount()) {
            return table.convertRowIndexToModel(viewRow);
        }
        return -1;
    }

    public static int getViewRowFromModel(JTable table, int modelRow) {
        if (modelRow != -1 && modelRow < table.getModel().getRowCount()) {
            return table.convertRowIndexToView(modelRow);
        }
        return -1;
    }

    public static String textToHtmlWithSize(String text, Font font) {
        if (text != null && !text.toLowerCase(Locale.ENGLISH).startsWith("<html>")) {
            return "<html><p style=\"font-size: " + font.getSize() + "pt;\">" + text + "</p>";
        }
        return text;
    }

    public static void fixTimeFormatter(PrettyTime timeFormatter) {
        // TODO: remove after PrettyTime lib upgrade to v5
        // change default just now from 60 to 30 secs
        // see workaround for 4.0 versions: https://github.com/ocpsoft/prettytime/issues/152
        TimeFormat timeFormat = timeFormatter.removeUnit(JustNow.class);
        JustNow newJustNow = new JustNow();
        newJustNow.setMaxQuantity(1000L * 30L); // 30 seconds gap (show "just now" from 0 to 30 secs)
        timeFormatter.registerUnit(newJustNow, timeFormat);
    }
}
