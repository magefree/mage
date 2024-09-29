package mage.components.table;

import org.ocpsoft.prettytime.PrettyTime;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.util.Date;
import java.util.Locale;

/**
 * GUI: create time ago cell renderer for date values in the table's cell
 * <p>
 * Usage example:
 * tableTables.getColumnModel().getColumn(TablesTableModel.COLUMN_CREATED).setCellRenderer(TimeAgoTableCellRenderer.getInstance());
 *
 * @author JayDi85
 */
public class TimeAgoTableCellRenderer extends DefaultTableCellRenderer {

    static final PrettyTime timeFormatter;

    static {
        timeFormatter = new PrettyTime(Locale.ENGLISH);
        MageTable.fixTimeFormatter(timeFormatter);
    }

    private static final TimeAgoTableCellRenderer instance = new TimeAgoTableCellRenderer();

    public static TimeAgoTableCellRenderer getInstance() {
        return instance;
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        Date d = (Date) value;
        label.setText(timeFormatter.format(d));
        return label;
    }
}
