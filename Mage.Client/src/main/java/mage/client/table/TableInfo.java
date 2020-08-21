package mage.client.table;

import java.util.ArrayList;
import java.util.List;

/**
 * @author JayDi85
 */
public class TableInfo {

    private final List<ColumnInfo> columns = new ArrayList<>();


    public TableInfo addColumn(Integer index, Integer width, Class colClass, String headerName, String headerHint) {
        this.columns.add(new ColumnInfo(index, width, colClass, headerName, headerHint));
        return this;
    }

    public int[] getColumnsWidth() {
        return this.columns.stream().mapToInt(ColumnInfo::getIndex).toArray();
    }

    public List<ColumnInfo> getColumns() {
        return this.columns;
    }

    public ColumnInfo getColumnByIndex(int index) {
        for (ColumnInfo col : this.columns) {
            if (col.getIndex().equals(index)) {
                return col;
            }
        }
        return null;
    }

    public ColumnInfo getColumnByName(String name) {
        for (ColumnInfo col : this.columns) {
            if (col.getHeaderName().equals(name)) {
                return col;
            }
        }
        return null;
    }
}
